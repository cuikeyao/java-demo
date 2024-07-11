package com.keyao.no015flinkcdc.demos.regularinnerjoin;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class PersonJob {
    public static void main(String[] args) {
        // 创建 Flink 执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 启用检查点，以实现容错处理，确保数据处理的Exactly-Once语义
        env.enableCheckpointing(60000);
        // 设置并行度为1
        env.setParallelism(1);
        // 创建 Table 环境
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        // 人员表
        var cdcSourceDDL = """
            create table person (
                id bigint NOT NULL,
                name STRING NOT NULL,
                age int NOT NULL,
                PRIMARY KEY(id) NOT ENFORCED
            ) WITH (
                'connector' = 'mysql-cdc',
                'hostname' = '192.168.56.10',
                'port' = '3306',
                'username' = 'root',
                'password' = '123456',
                -- 'scan.startup.mode' = 'earliest-offset',
                'database-name' = 'java_demo',
                'table-name' = 'person'
            )
        """;
        tableEnv.executeSql(cdcSourceDDL);

        // kafka sink表
        var kafkaSinkDDL = """
            create table kafkaSink (
                id bigint NOT NULL,
                name STRING NOT NULL,
                age int NOT NULL,
                PRIMARY KEY(id) NOT ENFORCED
            ) with (
                'connector' = 'upsert-kafka',
                'topic' = 'person-topic',
                'key.format' = 'json',
                'value.format' = 'json',
                'properties.bootstrap.servers' = '192.168.56.10:9092',
                'sink.parallelism' = '1'
            )
            """;
        tableEnv.executeSql(kafkaSinkDDL);


        Table table = tableEnv.sqlQuery("select * from person");

        table.executeInsert("kafkaSink");
    }
}
