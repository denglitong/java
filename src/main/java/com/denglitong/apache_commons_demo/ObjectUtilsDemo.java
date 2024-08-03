package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/19
 */
public class ObjectUtilsDemo {

    public static void main(String[] args) {
        Object obj1 = 1, obj2 = 2;
        System.out.println(ObjectUtils.allNotNull(obj1, obj2));
        System.out.println(ObjectUtils.allNotNull(obj1, null));

        System.out.println(ObjectUtils.allNull(obj1, null));
        System.out.println(ObjectUtils.allNull(null, null));

        System.out.println(ObjectUtils.anyNull(obj1, obj2));
        System.out.println(ObjectUtils.anyNull(obj1, null));
        System.out.println(ObjectUtils.anyNull(null, null));
        System.out.println(ObjectUtils.anyNotNull(obj1, null));
        System.out.println(ObjectUtils.anyNotNull(null, null));

        Object obj3 = ObjectUtils.clone(obj1);
        System.out.println(obj1 == obj3);
        System.out.println(obj3); // null, 需要 Object implements Cloneable

        NullCloneable nullCloneable = new NullCloneable(1);
        System.out.println(nullCloneable);
        NullCloneable nullCloneable1 = ObjectUtils.clone(nullCloneable);
        System.out.println(nullCloneable1); // null, 需要 Object implements Cloneable
        NullCloneable nullCloneable2 = ObjectUtils.cloneIfPossible(nullCloneable);
        System.out.println(nullCloneable2); // nullCloneable2 === nullCloneable

        MyCloneable myCloneable = new MyCloneable(2);
        System.out.println(myCloneable);
        MyCloneable myCloneable1 = ObjectUtils.clone(myCloneable);
        System.out.println(myCloneable1);
        System.out.println(ObjectUtils.cloneIfPossible(myCloneable)); // another obj

        System.out.println(ObjectUtils.defaultIfNull(null, "*")); // "*"
        System.out.println(ObjectUtils.defaultIfNull("abc", "*")); // "abc"
        System.out.println(ObjectUtils.defaultIfNull(Boolean.TRUE, "*")); // true

        System.out.println(ObjectUtils.firstNonNull(null, null, 2, 1, 3)); // 2

        System.out.println(ObjectUtils.identityToString(myCloneable1));

        System.out.println(ObjectUtils.isEmpty(null)); // true
        System.out.println(ObjectUtils.isEmpty("")); // true
        System.out.println(ObjectUtils.isEmpty(new int[]{})); // true
        System.out.println(ObjectUtils.isEmpty(0)); // false
        System.out.println(ObjectUtils.isEmpty(false)); // false
        System.out.println(ObjectUtils.isNotEmpty(myCloneable)); // true

        System.out.println(ObjectUtils.max(new Comparable[]{1, 2, 3, 4, 5})); // 5
        System.out.println(ObjectUtils.median(new Comparable[]{1, 2, 3, 4, 5})); // 3
        System.out.println(ObjectUtils.median(new Comparable[]{1, 2, 3, 4})); // 2
        System.out.println(ObjectUtils.min(new Comparable[]{1, 2, 3, 4, 4})); // 1

        System.out.println(ObjectUtils.notEqual(myCloneable, myCloneable1)); // true
        System.out.println(ObjectUtils.notEqual(1, 1)); // false

        System.out.println(ObjectUtils.requireNonEmpty(myCloneable));
        // System.out.println(ObjectUtils.requireNonEmpty((MyCloneable)null)); // NullPointerException
        // System.out.println(ObjectUtils.requireNonEmpty((MyCloneable)null, "Object is null")); // NullPointerException

        // 作为 null 的占位符，用以区分 Map.get() 返回 null 时是值为 null，还是不包含键时的返回 null
        // Map.put(key, ObjectUtils.NULL)
        System.out.println(ObjectUtils.NULL);
    }

    public static class NullCloneable {
        int value;

        public NullCloneable(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static class MyCloneable implements Cloneable {
        int value;

        public MyCloneable(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @Override
        public MyCloneable clone() {
            try {
                MyCloneable clone = (MyCloneable)super.clone();
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
