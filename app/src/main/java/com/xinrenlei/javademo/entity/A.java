package com.xinrenlei.javademo.entity;

import java.lang.reflect.Constructor;

/**
 * Auth：yujunyao
 * Since: 2020/8/21 11:00 AM
 * Email：yujunyao@xinrenlei.net
 */

public class A {

    public static void main(String[] args) {
        B b = new A.B();

        Constructor[] constructors = B.class.getDeclaredConstructors();
        System.out.println("constructors count:" + constructors.length);
        for (Constructor c : constructors) {
            System.out.println(c);
        }

    }

    private static class B {

    }

}
