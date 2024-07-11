package com.keyao.no015flinkcdc.demos.regularinnerjoin;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.time.ZoneId;

// 双流join inner join
public class PersonAddressInnerJoin {
    public static void main(String[] args) {
        // 创建执行环境
        var settings = EnvironmentSettings.newInstance().inStreamingMode().build();
        var tEnv = TableEnvironment.create(settings);

        // 指定国内的时区
        tEnv.getConfig().setLocalTimeZone(ZoneId.of("Asia/Shanghai"));

        // 人员表
        var personTableSql = """
            create table person (
                id bigint NOT NULL,
                name varchar(255) NOT NULL,
                age int NOT NULL
            ) WITH (
                'connector' = 'kafka',
                'topic' = 'person-topic',
                'properties.bootstrap.servers'='192.168.56.10:9092',
                'properties.group.id' = 'person-address',
                'scan.startup.mode' = 'latest-offset',
                'format' = 'json',
                'json.fail-on-missing-field' = 'false',
                'json.ignore-parse-errors' = 'true'
            )
        """;
        tEnv.executeSql(personTableSql);

        // 地址表
        var addressTableSql = """
            create table address (
                id bigint NOT NULL,
                address varchar(512) NOT NULL
            ) WITH (
                'connector' = 'kafka',
                'topic' = 'address-topic',
                'properties.bootstrap.servers'='192.168.56.10:9092',
                'properties.group.id' = 'address-address',
                'scan.startup.mode' = 'latest-offset',
                'format' = 'json',
                'json.fail-on-missing-field' = 'false',
                'json.ignore-parse-errors' = 'true'
            )
        """;
        tEnv.executeSql(addressTableSql);

        // 结果表
        var resTableSql = """
            create table person_address (
                id bigint NOT NULL,
                name varchar(255) NOT NULL,
                age int NOT NULL,
                address varchar(512),
                PRIMARY KEY (id) NOT ENFORCED
            ) with (
                'connector' = 'elasticsearch-7',
                'hosts' = '192.168.56.10:9200',
                'index' = 'person_address'
            )
            """;
        tEnv.executeSql(resTableSql);

        // 关联person表和address表
        var joinSql = """
            insert into person_address
            select
                p.id,
                p.name,
                p.age,
                a.address
            from person as p
            inner join address as a on p.id = a.id
            """;
        tEnv.executeSql(joinSql);
    }
}
