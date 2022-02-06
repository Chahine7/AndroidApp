package com.example.project;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    String[] type =  {"Chef","Technicien"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        autoCompleteTxt = findViewById(R.id.etat);

        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,type);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"type: "+type,Toast.LENGTH_SHORT).show();
            }
        });


        Button register=(Button) findViewById(R.id.add);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login=(EditText)findViewById(R.id.login1);
                EditText pwd=(EditText)findViewById(R.id.pwd1);
                EditText name=(EditText)findViewById(R.id.desc);
                EditText lastName=(EditText)findViewById(R.id.lastname);


                String l=login.getText().toString();
                String pw=pwd.getText().toString();
                String n=name.getText().toString();
                String p=lastName.getText().toString();
                String t = autoCompleteTxt.getText().toString();

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

               // String url="http://172.16.110.40/project/addUser.php";
                String url="http://192.168.3.57/project/addUser.php";

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("login", l));
                params.add(new BasicNameValuePair("pwd", pw));
                params.add(new BasicNameValuePair("name", n));
                params.add(new BasicNameValuePair("lastname", p));
                params.add(new BasicNameValuePair("type", t));


                HttpPost httppost=new HttpPost(url);
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {

                    HttpClient httpclient=new DefaultHttpClient();
                    //HttpPost httppost=new HttpPost(url);
                    Log.d("gggggg", "rrrrr");
                    HttpResponse response=httpclient.execute(httppost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);

            }

        });


    }
}