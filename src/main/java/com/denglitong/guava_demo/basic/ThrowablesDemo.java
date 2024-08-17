package com.denglitong.guava_demo.basic;

import com.google.common.base.Throwables;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/1
 */
public class ThrowablesDemo {

    public static void main(String[] args)
            throws MySQLException, MyIOException {
        // throwError();
        // throwRuntimeException();

        try {
            throwMyIOException();
        } catch (Throwable t) {
            Throwable throwable = Throwables.getRootCause(t);
            // com.denglitong.basic.ThrowablesDemo$MyIOException: dead io exception
            System.out.println(throwable);

            Throwables.getCausalChain(t).forEach(System.out::println);

            System.out.println(Throwables.getStackTraceAsString(t));
        }
    }

    private static void throwError() {
        try {
            throw new Error("烫烫烫烫烫烫烫烫烫烫烫");
        } catch (Throwable t) {
            // java.lang.Error: 烫烫烫烫烫烫烫烫烫烫烫
            Throwables.propagate(t);
        }
    }

    private static void throwRuntimeException() {
        try {
            throw new MyException("user-agent exception");
        } catch (Throwable t) {
            // java.lang.RuntimeException: xxx$MyException: user-agent exception
            Throwables.propagate(t);
        }
    }

    private static void throwMyIOException() throws MySQLException, MyIOException {
        try {
            throw new MyIOException("dead io exception");
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t); // t 为 Error/RuntimeException 才抛出
            Throwables.propagateIfInstanceOf(t, MySQLException.class);
            // com.denglitong.basic.ThrowablesDemo$MyIOException: dead io exception
            // Throwables.propagateIfInstanceOf(t, MyIOException.class);
            // com.denglitong.basic.ThrowablesDemo$MyIOException: dead io exception
            Throwables.propagateIfPossible(t, MyIOException.class);
            Throwables.propagate(t);
        }
    }

    private static class MyException extends Exception {
        public MyException(String message) {
            super(message);
        }
    }

    private static class MyIOException extends Exception {
        public MyIOException(String message) {
            super(message);
        }
    }

    private static class MySQLException extends Exception {
        public MySQLException(String message) {
            super(message);
        }
    }
}
