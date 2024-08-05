package com.denglitong.category_articles_backend.util;

import org.junit.jupiter.api.Order;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Ordering;
import org.junit.runner.manipulation.Sorter;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/30
 */
public class OrderAnnotation extends Sorter implements Ordering.Factory {

    public OrderAnnotation() {
        super(COMPARATOR);
    }

    @Override
    public Ordering create(Context context) {
        return this;
    }

    private static final Comparator<Description> COMPARATOR =
            Comparator.comparingInt(description -> Optional.ofNullable(description.getAnnotation(Order.class))
                    .map(Order::value)
                    .orElse(Order.DEFAULT));
}
