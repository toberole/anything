package com.zhouwei.anything.bean;

/**
 * Created by zhouwei on 2017/12/14.
 */

public class Person {
    private int age = -2;
    private float age1 = -2;
    private double age2 = -2;
    private long age3 = -2;

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", age1=" + age1 +
                ", age2=" + age2 +
                ", age3=" + age3 +
                '}';
    }
}
