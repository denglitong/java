/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.drools_demo.entity;

import java.util.StringJoiner;

public class Greeting {

  public static final int HELLO = 0;
  public static final int GOODBYE = 1;

  private String name;
  private int status;

  public Greeting(String name, int status) {
    this.name = name;
    this.status = status;
  }

  public void speak() {
    switch (status) {
      case HELLO:
        System.out.printf("Nice to meet you, %s!%n", name);
        break;
      case GOODBYE:
        System.out.printf("See you later, %s!%n", name);
        break;
    }
  }

  public String getName() {
    return name;
  }

  public Greeting setName(String name) {
    this.name = name;
    return this;
  }

  public int getStatus() {
    return status;
  }

  public Greeting setStatus(int status) {
    this.status = status;
    return this;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Greeting.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("status=" + status)
            .toString();
  }
}
