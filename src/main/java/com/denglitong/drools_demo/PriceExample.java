/**
 * Copyright (C) 2024 Urban Compass, Inc.
 */
package com.denglitong.drools_demo;

import com.denglitong.drools_demo.entity.Product;
import com.denglitong.drools_demo.entity.User;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class PriceExample {
    private static final String KSESSION_NAME = "price";

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        execute(kc);
    }

    public static void execute(KieContainer kc) {
        // 获取KieSession，KieSession用来执行规则引擎
        KieSession ksession = kc.newKieSession(KSESSION_NAME);

        Product product1 = new Product("Electronics", 5, 100.0);
        Product product2 = new Product("Clothing", 150, 100.0);
        User user1 = new User("VIP");

        ksession.insert(product1);
        ksession.insert(product2);
        ksession.insert(user1);

        ksession.fireAllRules();

        // 81 = 100 * 0.9 * 0.9
        System.out.printf("User %s Product 1 final price: %.2f\n", user1.getType(), product1.getFinalPrice());
        // 88 = 100 * 1.1 * 0.8
        System.out.printf("User %s Product 2 final price: %.2f\n", user1.getType(), product2.getFinalPrice());

        // 删除会话中的数据
        ksession.delete(ksession.getFactHandle(product1));
        ksession.delete(ksession.getFactHandle(product2));
        ksession.delete(ksession.getFactHandle(user1));

        // 重置产品价格
        product1.setFinalPrice(product1.getBasePrice());
        product2.setFinalPrice(product2.getBasePrice());

        User user2 = new User("Normal");

        ksession.insert(user2);
        ksession.insert(product1);
        ksession.insert(product2);
        ksession.fireAllRules();

        // 90 = 100 * 0.9
        System.out.printf("User %s Product 1 final price: %.2f\n", user2.getType(), product1.getFinalPrice());
        // 110 = 100 * 1.1
        System.out.printf("User %s Product 2 final price: %.2f\n", user2.getType(), product2.getFinalPrice());

        // 按分组可优先执行不同的规则
        // ksession.getAgenda().getAgendaGroup("stock").setFocus();
        // ksession.fireAllRules();
        // ksession.getAgenda().getAgendaGroup("vip").setFocus();
        // ksession.fireAllRules();

        // 释放资源
        ksession.dispose();
    }
}
