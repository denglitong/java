/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.inside_the_jvm.chapter2;

import java.util.HashSet;
import java.util.Set;

/**
 * VM Args: -XX:PermSize=6M -XX:MaxPermSize=6M
 * <p>
 * Need to run at JDK 6
 */
public class RuntimeConstantPoolOOMInJDK6 {

  public static void main(String[] args) {
    Set<String> set = new HashSet<String>();
    short i = 0;
    while (true) {
      set.add(String.valueOf(i++).intern());
    }
  }
}
