package com.denglitong.guava_demo.concurrent;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/7
 */
public class ServiceManagerTest {

    private static class NoOpService extends AbstractService {
        @Override
        protected void doStart() {
            notifyStarted();
        }

        @Override
        protected void doStop() {
            notifyStopped();
        }
    }

    private static class NoOpDelayedService extends NoOpService {
        private long delay;

        public NoOpDelayedService(long delay) {
            this.delay = delay;
        }

        @Override
        protected void doStart() {
            new Thread() {
                @Override
                public void run() {
                    Uninterruptibles.sleepUninterruptibly(delay, TimeUnit.MILLISECONDS);
                    notifyStarted();
                }
            }.start();
        }

        @Override
        protected void doStop() {
            new Thread(() -> {
                Uninterruptibles.sleepUninterruptibly(delay, TimeUnit.MILLISECONDS);
                notifyStopped();
            }).start();
        }
    }

    public static void main(String[] args) {
        Service a = new NoOpDelayedService(150);
        Service b = new NoOpDelayedService(353);
        ServiceManager serviceManager = new ServiceManager(asList(a, b));
        serviceManager.startAsync().awaitHealthy();

        ImmutableMap<Service, Long> startupTimes = serviceManager.startupTimes();
        System.out.println(startupTimes.size()); // 2
        System.out.println(startupTimes.get(a)); // >= 150
        System.out.println(startupTimes.get(b)); // >= 353

    }
}
