package com.keyao.no015flinkcdc.demos.web;

import com.alibaba.fastjson2.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Log4j2
public class Main {

    public static void main(String[] args) throws Exception {

        // 创建 Flink 执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 创建 Table 环境
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        // 配置 MySQL CDC 源表
        String sourceDDL = """
            CREATE TABLE products (
                 id INT NOT NULL,
                 name STRING,
                 description STRING,
                 PRIMARY KEY (id) NOT ENFORCED
            ) WITH (
                 'connector' = 'mysql-cdc',
                 'hostname' = '192.168.56.10',
                 'port' = '3306',
                 'username' = 'root',
                 'password' = '123456',
                 'database-name' = 'java_demo',
                 'table-name' = 'products'
            )
        """;

        tableEnv.executeSql(sourceDDL);

        // 获取 MySQL CDC 源表的 DataStream
        Table table = tableEnv.sqlQuery("SELECT id,name,description FROM products");
        DataStream<Row> rowDataStream = tableEnv.toChangelogStream(table);

        // 数据清洗和处理
        DataStream<Products> dataStream = rowDataStream.map((MapFunction<Row, Products>) row -> {
            int id = (int) row.getField(0);
            String name = (String) row.getField(1);
            String description = (String) row.getField(2);
            RowKind rowKind = row.getKind();
            Products products = Products.builder().id(id).name(name).description(description).rowKind(rowKind).build();

            // 打印日志
            switch (rowKind) {
                case INSERT -> log.info(() -> "INSERT: " + JSON.toJSONString(products));
                case UPDATE_BEFORE -> log.info(() -> "UPDATE BEFORE: " + JSON.toJSONString(products));
                case UPDATE_AFTER -> log.info(() -> "UPDATE AFTER: " + JSON.toJSONString(products));
                case DELETE -> log.info(() -> "DELETE: " + JSON.toJSONString(products));
                default -> throw new IllegalStateException("Unexpected value: " + rowKind);
            }

            return products;
        });

        // 配置 Elasticsearch Sink
        List<HttpHost> httpHosts = List.of(new HttpHost("192.168.56.10", 9200, "http"));
        ElasticsearchSink.Builder<Products> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts,
                (Products products, RuntimeContext ctx, RequestIndexer indexer) -> {
                    Map<String, Object> map = new TreeMap<>();
                    map.put("id", products.getId());
                    map.put("name", products.getName());
                    map.put("description", products.getDescription());

                    if (products.getRowKind() == RowKind.INSERT || products.getRowKind() == RowKind.UPDATE_AFTER) {
                        IndexRequest indexRequest = Requests.indexRequest("products")
                                .id(String.valueOf(products.getId()))
                                .source(map);
                        indexer.add(indexRequest);
                    } else if (products.getRowKind() == RowKind.DELETE) {
                        DeleteRequest deleteRequest = Requests.deleteRequest("products")
                                .id(String.valueOf(products.getId()));
                        indexer.add(deleteRequest);
                    }
                });

        // 设置批量处理参数
        esSinkBuilder.setBulkFlushMaxActions(1);

        // 添加 Elasticsearch Sink 到 DataStream
        dataStream.addSink(esSinkBuilder.build());

        // 执行 Flink 程序
        env.execute("Flink MySQL CDC Example");
    }
}
