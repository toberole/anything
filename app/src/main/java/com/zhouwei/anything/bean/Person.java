package com.zhouwei.anything.bean;

import android.content.ContentValues;

import com.zhouwei.anything.xxorm.Sprite;

/**
 * Created by zhouwei on 2017/12/14.
 */

public class Person implements Sprite {
    private int id;
    private String name;
    private int age;

    public Person() {

    }

    public Person(ContentValues values) {
        try {
            setId(Integer.parseInt(String.valueOf(values.get("id"))));
            setName(String.valueOf(values.get("name")));
            setAge(Integer.parseInt(String.valueOf(values.get("age"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ContentValues save() {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
