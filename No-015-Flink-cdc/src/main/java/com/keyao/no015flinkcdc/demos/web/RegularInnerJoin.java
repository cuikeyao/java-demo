package com.keyao.no015flinkcdc.demos.web;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.time.ZoneId;

// 双流join inner join
public class RegularInnerJoin {
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
                'connector'='mysql-cdc',
                'hostname' = '192.168.56.10',
                'port' = '3306',
                'username' = 'root',
                'password' = '123456',
                'database-name' = 'java_demo',
                'scan.startup.mode' = 'earliest-offset',
                'table-name' = 'person',
                'scan.incremental.snapshot.enabled' = 'true',
                'scan.incremental.snapshot.chunk.key-column' = 'id'
            )
        """;
        tEnv.executeSql(personTableSql);

        // 地址表
        var addressTableSql = """
            create table address (
                id bigint NOT NULL,
                address varchar(512) NOT NULL
            ) WITH (
                'connector'='mysql-cdc',
                'hostname' = '192.168.56.10',
                'port' = '3306',
                'username' = 'root',
                'password' = '123456',
                'database-name' = 'java_demo',
                'scan.startup.mode' = 'earliest-offset',
                'table-name' = 'address',
                'scan.incremental.snapshot.enabled' = 'true',
                'scan.incremental.snapshot.chunk.key-column' = 'id'
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
