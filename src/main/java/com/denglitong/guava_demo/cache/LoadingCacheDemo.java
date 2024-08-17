package com.denglitong.guava_demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.concurrent.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/3
 */
public class LoadingCacheDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats() // 开启统计功能：缓存命中率、加载新值平均时间、缓存项被回收的总数
                .build(new CacheLoader<Key, Graph>() {
                    @Override
                    public Graph load(Key key) throws Exception {
                        return createExpensiveGraph(key);
                    }

                    @Override
                    public ListenableFuture<Graph> reload(Key key, Graph oldValue) throws Exception {
                        if (isNeverNeedsRefresh(key)) {
                            return Futures.immediateFuture(oldValue);
                        } else {
                            // asynchronous
                            ListenableFutureTask<Graph> task = ListenableFutureTask.create(new Callable<Graph>() {
                                @Override
                                public Graph call() throws Exception {
                                    return getGraphFromDatabase(key);
                                }
                            });
                            executor.execute(task);
                            return task;
                        }
                    }
                });

        // try {
        //     graphs.get(new Key());
        // } catch (ExecutionException e) {
        //     throw new RuntimeException(e);
        // }

        // graphs.getUnchecked(new Key());

        try {
            Key key = new Key();
            // get-if-absent-compute-put-return
            graphs.get(key, new Callable<Graph>() {
                @Override
                public Graph call() throws Exception {
                    return doThingsHardWay(key);
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        Key key = new Key();
        Graph value = new Graph();

        graphs.invalidate(key);

    }

    private static Graph getGraphFromDatabase(Key key) {
        // TODO
        return null;
    }

    private static boolean isNeverNeedsRefresh(Key key) {
        // TODO
        return false;
    }

    private static Graph doThingsHardWay(Key key) {
        // TODO
        return null;
    }

    private static Graph createExpensiveGraph(Key key) {
        // TODO
        return null;
    }

    static class Key {
        // TODO
    }

    static class Graph {
        // TODO
    }
}
