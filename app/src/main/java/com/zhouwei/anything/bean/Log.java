package com.zhouwei.anything.bean;

import android.content.ContentValues;

import com.zhouwei.anything.xxorm.Sprite;

/**
 * Created by zhouwei on 2017/12/22.
 */

public class Log implements Sprite {
    private String tag;
    private String msg;
    private String time;
    private String thread;

    public Log() {
    }

    public Log(ContentValues values) {
        try {
            setTag(String.valueOf(values.get("tag")));
            setMsg(String.valueOf(values.get("msg")));
            setTime(String.valueOf(values.get("time")));
            setThread(String.valueOf(values.get("thread")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    @Override
    public ContentValues save() {
        ContentValues values = new ContentValues();
        values.put("tag", tag);
        values.put("msg", tag);
        values.put("time", time);
        values.put("thread", thread);
        return values;
    }

    @Override
    public String toString() {
        return "Log{" +
                "tag='" + tag + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", thread='" + thread + '\'' +
                '}';
    }
}
