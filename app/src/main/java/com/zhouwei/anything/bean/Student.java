package com.zhouwei.anything.bean;

import android.content.ContentValues;

import com.zhouwei.anything.xxorm.Sprite;

/**
 * Created by zhouwei on 2017/12/21.
 */

public class Student implements Sprite {
    private String name;
    private int age;
    private int id;

    public Student() {
    }

    public Student(ContentValues values) {
        try {
            setAge(Integer.parseInt(String.valueOf(values.get("age"))));
            setName(String.valueOf(values.get("name")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ContentValues save() {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("id", id);
        values.put("age", age);
        return values;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
