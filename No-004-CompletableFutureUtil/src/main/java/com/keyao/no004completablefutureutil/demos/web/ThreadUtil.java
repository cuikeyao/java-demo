package com.keyao.no004completablefutureutil.demos.web;

public class ThreadUtil {
    public static void sleep(int s) {
        try {
            Thread.sleep(s * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
