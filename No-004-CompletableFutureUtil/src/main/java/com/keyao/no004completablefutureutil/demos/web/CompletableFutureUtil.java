package com.keyao.no004completablefutureutil.demos.web;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletableFutureUtil {

    /**
     * 线程池直接这样写了先，真实业务中不是这样搞的哈！
     */
    private final static ExecutorService executorService = Executors.newFixedThreadPool(4);

    /**
     * 创建并行任务并执行
     *
     * @param list            数据源
     * @param api             API调用逻辑
     * @param exceptionHandle 异常处理逻辑
     * @param <S>             数据源类型
     * @param <T>             程序返回类型
     * @return 处理结果列表
     */
    public <S, T> List<T> parallelFutureJoin(Collection<S> list, Function<S, T> api, BiFunction<Throwable, S, T> exceptionHandle) {
        //规整所有任务
        List<CompletableFuture<T>> collectFuture = list.stream()
                .map(s -> this.createFuture(() -> api.apply(s), e -> exceptionHandle.apply(e, s))).collect(Collectors.toList());
        //汇总所有任务，并执行join，全部执行完成后，统一返回
        return collectFuture.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 创建单个CompletableFuture任务
     *
     * @param logic           任务逻辑
     * @param exceptionHandle 异常处理
     * @param <T>             类型
     * @return 任务
     */
    public <T> CompletableFuture<T> createFuture(Supplier<T> logic, Function<Throwable, T> exceptionHandle) {
        return CompletableFuture.supplyAsync(logic, executorService).exceptionally(exceptionHandle);
    }

}
