package com.denglitong.guava_demo.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/2
 */
public class CollectionStaticMethodDemo {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        list.add(6);
        System.out.println(list);

        Set<Integer> set = Sets.newHashSet(3, 4, 5, 2, 3, 4);
        System.out.println(set);

        Map<String, Integer> map = Maps.newLinkedHashMap();
        map.put("hello", 5);
        System.out.println(map);

        List<Integer> list1 = Lists.newArrayListWithCapacity(100);
        List<Integer> list2 = Lists.newArrayListWithExpectedSize(100);
        Set<String> set1 = Sets.newHashSetWithExpectedSize(100);
    }
}
