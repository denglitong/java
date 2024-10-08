/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.inside_the_jvm.chapter2;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {

  private static final int _1MB = 1024 * 1024;

  public static void main(String[] args) throws Exception {
    Field unsafeField = Unsafe.class.getDeclaredFields()[0];
    unsafeField.setAccessible(true);
    Unsafe unsafe = (Unsafe)unsafeField.get(null);
    while (true) {
      unsafe.allocateMemory(_1MB);
    }
  }
}
