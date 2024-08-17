/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.inside_the_jvm.chapter3;

public class FinalizeEscapeGC {

  public static FinalizeEscapeGC SAVE_HOOK = null;

  public void isActive() {
    System.out.println("Yes, I am still alive.");
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("Finalize method executed.");
    FinalizeEscapeGC.SAVE_HOOK = this;
  }

  public static void main(String[] args) throws InterruptedException {
    SAVE_HOOK = new FinalizeEscapeGC();

    // 对象第一次在 finalize() 里面拯救了自己
    SAVE_HOOK = null;
    System.gc();
    // 因为 finalize() 方法优先级较低，暂停 0.5s 以等待它触发执行
    Thread.sleep(500);

    // Finalize method executed.

    if (SAVE_HOOK != null) {
      // Yes, I am still alive.
      SAVE_HOOK.isActive();
    } else {
      System.out.println("No, I am dead.");
    }

    // 第二次 GC，但是 finalize() 只会触发一次，所以对象会被回收掉
    SAVE_HOOK = null;
    System.gc();
    Thread.sleep(500);
    if (SAVE_HOOK != null) {
      SAVE_HOOK.isActive();
    } else {
      // No, I am dead.
      System.out.println("No, I am dead.");
    }
  }
}
