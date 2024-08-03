/**
 * Copyright (C) 2024 Urban Compass, Inc.
 */
package com.denglitong.drools_demo.entity;

import java.util.StringJoiner;

public class User {
    private String type;

    public User(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .toString();
    }
}
