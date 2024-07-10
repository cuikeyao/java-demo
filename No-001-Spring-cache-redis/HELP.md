## Spring cache基于redis

### 1. 引入依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
### 2. 测试表
```
    DROP TABLE IF EXISTS `user`;
    CREATE TABLE `user`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) DEFAULT NULL,
    `age` int DEFAULT NULL,
    PRIMARY KEY (`id`)
    )
```


### 3. 添加注解
1. `@EnableCaching`启动类上添加
2. `@Cacheable`有缓存数据则返回缓存数据，没有缓存数据则调用方法
3. `@CachePut`将方法的返回值放到缓存中
4. `@CacheEvict`删除缓存数据

