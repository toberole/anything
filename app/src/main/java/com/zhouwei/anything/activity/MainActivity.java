package com.zhouwei.anything.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhouwei.anything.R;
import com.zhouwei.anything.bean.Person;
import com.zhouwei.anything.xxorm.DBEngine;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private android.widget.Button add;
    private android.widget.Button delete;
    private android.widget.Button update;
    private android.widget.Button get;
    private Button getPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getPart = (Button) findViewById(R.id.getPart);
        this.get = (Button) findViewById(R.id.get);
        this.update = (Button) findViewById(R.id.update);
        this.delete = (Button) findViewById(R.id.delete);
        this.add = (Button) findViewById(R.id.add);


        get.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        getPart.setOnClickListener(this);

        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.add) {
            add();
        } else if (id == R.id.get) {
            get();
        } else if (id == R.id.delete) {
            delete();
        } else if (id == R.id.update) {
            update();
        } else if (id == R.id.getPart) {
            getPart();
        }
    }

    private void add() {
        Log.i("AAAA", "==add==");
        Person person = new Person();
        person.setAge(11);
        person.setName("xiaohon");
        DBEngine.getInstance().save(Person.class, person.save());
    }

    private void get() {
        Log.i("AAAA", "==get==");
        List<Person> ps = DBEngine.getInstance().get(Person.class, null, null, null, null, null);
        if (null != ps) {
            for (int i = 0; i < ps.size(); i++) {
                Log.i("AAAA", "person: " + ps.get(i));
            }
        }
    }

    private void getPart() {
        List<Person> ps = DBEngine.getInstance().get(Person.class, 15, 3);
        if (null != ps) {
            for (int i = 0; i < ps.size(); i++) {
                Log.i("AAAA", "person: " + ps.get(i));
            }
        }
    }

    private void delete() {
        Log.i("AAAA", "==delete==");
        DBEngine.getInstance().delete(Person.class, null, null);
    }

    private void update() {
        Log.i("AAAA", "==update==");
        ContentValues values = new ContentValues();
        values.put("name", "XXORM");
        String whereClause = "age = ?";
        String[] whereArgs = new String[]{11 + ""};
        DBEngine.getInstance().update(Person.class, values, whereClause, whereArgs);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
