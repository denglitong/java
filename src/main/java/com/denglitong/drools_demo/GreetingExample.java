/**
 * Copyright (C) 2022 Urban Compass, Inc.
 */
package com.denglitong.drools_demo;

import java.util.ArrayList;

import com.denglitong.drools_demo.entity.Greeting;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static com.denglitong.drools_demo.entity.Greeting.GOODBYE;
import static com.denglitong.drools_demo.entity.Greeting.HELLO;

public class GreetingExample {

  private static final String KSESSION_NAME = "greeting";

  public static void main(String[] args) {
    KieServices ks = KieServices.Factory.get();
    KieContainer kc = ks.getKieClasspathContainer();
    execute(kc);
  }

  public static void execute(KieContainer kc) {
    // 获取KieSession，KieSession用来执行规则引擎
    KieSession ksession = kc.newKieSession(KSESSION_NAME);
    ksession.setGlobal("greetingList", new ArrayList<Greeting>());

    final Greeting hello = new Greeting("Jack", HELLO);
    final Greeting goodbye = new Greeting("Tom", GOODBYE);

    ksession.insert(hello);
    ksession.insert(goodbye);

    ksession.fireAllRules();

    ksession.dispose();
  }
}
