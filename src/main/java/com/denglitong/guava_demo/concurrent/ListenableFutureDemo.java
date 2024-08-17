package com.denglitong.guava_demo.concurrent;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Futures.transform(ListenableFuture, AsyncFunction, Executor)
 * Futures.transform(ListenableFuture, Function, Executor)
 * Futures.allAsList(Iterable<ListenableFuture<V>>)
 * Futures.successfulAsList(Iterable<ListenableFuture<V>>)
 *
 * @author litong.deng@foxmail.com
 * @date 2021/10/5
 */
public class ListenableFutureDemo {

    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
                Executors.newFixedThreadPool(10));

        ListenableFuture explosion = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("listenable future call");
                return null;
            }
        });

        FutureCallback callback = new FutureCallback() {
            @Override
            public void onSuccess(@Nullable Object result) {
                System.out.println("future callback onSuccess");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("future callback onFailure");
            }
        };

        Futures.addCallback(explosion, callback, executorService);

        executorService.shutdown();
        System.out.println("shutdown? " + executorService.isShutdown());
    }
}
