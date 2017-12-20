package com.zhouwei.anything.activity;

import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zhouwei.anything.R;
import com.zhouwei.anything.Util;
import com.zhouwei.anything.bean.Person;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_test);

        String json = "{\"age\":112,\"age1\":113,\"age2\":115,\"age3\":114}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            Person person = Util.jsonObjectToBean(jsonObject, Person.class);
            Log.i("AAAA", person.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
