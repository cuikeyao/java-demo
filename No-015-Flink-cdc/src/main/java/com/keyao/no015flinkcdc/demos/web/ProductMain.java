package com.keyao.no015flinkcdc.demos.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.cdc.connectors.mysql.source.MySqlSource;
import org.apache.flink.cdc.connectors.mysql.table.StartupOptions;
import org.apache.flink.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.elasticsearch.sink.Elasticsearch7SinkBuilder;
import org.apache.flink.connector.elasticsearch.sink.ElasticsearchEmitter;
import org.apache.flink.connector.elasticsearch.sink.RequestIndexer;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.elasticsearch.sink.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ProductMain {
    public static void main(String[] args) throws Exception {
        System.setProperty("log.file", "C://flink-cdc/log");

        Configuration configuration = new Configuration();
        configuration.setInteger(RestOptions.PORT, 8081);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(configuration);
        env.setParallelism(1);
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        env.enableCheckpointing(5000L);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500L);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.setStateBackend(new FsStateBackend("file:///C://flink-cdc/checkpoints"));
        env.getCheckpointConfig().setCheckpointTimeout(60000L);
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(10);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.of(5, TimeUnit.SECONDS)));

        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("192.168.56.10")
                .port(3306)
                .username("root")
                .password("123456")
                .databaseList("java_demo")
                .tableList("java_demo.products")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.initial())
                .includeSchemaChanges(true)
                .heartbeatInterval(Duration.ofSeconds(10L))
                .debeziumProperties(getJdbcProperties())
                .build();

        ElasticsearchSink<String> elasticsearchSink = new Elasticsearch7SinkBuilder<String>()
                .setHosts(new HttpHost("192.168.56.10", 9200, "http"))
                .setEmitter((ElasticsearchEmitter<String>) (json, context, requestIndexer) -> send(json, requestIndexer))
                .setBulkFlushMaxActions(1)
                .build();

        // 创建DataStream
        DataStream<String> sourceStream = env.fromSource(mySqlSource,
                WatermarkStrategy.noWatermarks(), "MySQL Source")
                .setParallelism(1);

        // 将DataStream写入到Elasticsearch
        sourceStream.sinkTo(elasticsearchSink);

        // 启动Flink作业
        env.execute("FlinkCDC");
    }

    private static void send(String json, RequestIndexer requestIndexer) {
        // {"before":null,"after":{"id":10,"name":"董文","description":"中国大学"},"source":{"version":"1.9.7.Final","connector":"mysql","name":"mysql_binlog_source","ts_ms":0,"snapshot":"false","db":"java_demo","sequence":null,"table":"products","server_id":0,"gtid":null,"file":"","pos":0,"row":0,"thread":null,"query":null},"op":"r","ts_ms":1720998103072,"transaction":null}
        String op = JsonUtil.getValue(json, "op", String.class);
        if (op == null) {
            return;
        }
        Map<String, Object> map;
        switch (op) {
            case "r":
            case "c":
            case "u":
                map = JsonUtil.getValue(json, "after", Map.class);
                IndexRequest indexRequest = Requests.indexRequest("products")
                        .id(String.valueOf(map.get("id")))
                        .source(map);
                requestIndexer.add(indexRequest);
                break;
            case "d":
                map = JsonUtil.getValue(json, "before", Map.class);
                DeleteRequest deleteRequest = Requests.deleteRequest("products")
                        .id(String.valueOf(map.get("id")));
                requestIndexer.add(deleteRequest);
                break;
            default:
                // 可以在这里处理其他情况，比如抛出异常或打印错误信息
                log.warn("Invalid operation: " + op);
        }
    }

    private static Properties getJdbcProperties() {
        Properties properties = new Properties();
        properties.setProperty("connectionTimeZone", "Asia/Shanghai"); // 设置 server-time-zone
        return properties;
    }
}
