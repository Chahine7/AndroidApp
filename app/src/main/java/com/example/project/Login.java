package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    Button btn;
    EditText login;
    EditText psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn =  findViewById(R.id.ok);
        login =  findViewById(R.id.loginv);
        psw = findViewById(R.id.passwordv);
Button register = (Button) findViewById(R.id.add);
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent t = new Intent(Login.this, Register.class);
        startActivity(t);
    }
});


        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String userName=login.getText().toString();
                String pwd=psw.getText().toString();

                ///////////////////////////////////////////////////////
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

              //  String url="http://172.16.110.40/project/user.php";
                String url="http://192.168.3.57/project/user.php";

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("username", userName));
                params.add(new BasicNameValuePair("pwd", pwd));
                HttpPost httppost=new HttpPost(url);
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("ggggg", url);
                User u = null;

                try {
                    HttpClient httpclient=new DefaultHttpClient();
                    Log.d("gggggg", "rrrrr");
                    HttpResponse response=httpclient.execute(httppost);


                    InputStream inputStream = response.getEntity().getContent();
                    String result=null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!= null){
                        sb.append(line+"\n");
                    }
                    inputStream.close();
                    result=sb.toString();
                    Log.d("ggggg",result);
                    //res.setText(result);

                    JSONArray jArray=new JSONArray(result);// this statment gives error@Override

                    for(int i=0;i<jArray.length();i++){
                        JSONObject json=jArray.getJSONObject(i);
                        Log.d("gggg", json.getString("login"));
                        Log.d("gggg", json.getString("pwd"));
                        u=new User(json.getInt("id"), json.getString("name"), json.getString("lastname"), json.getString("login"), json.getString("pwd"), json.getString("type"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
                if(u==null){



                    AlertDialog.Builder boite;
                    boite = new AlertDialog.Builder(Login.this);
                    boite.setTitle("ERROR");
                    boite.setIcon(R.drawable.alert);
                    boite.setMessage("Verifier votre login et mot de passe");
                    boite.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            login =  findViewById(R.id.loginv);
                            psw =  findViewById(R.id.passwordv);
                            login.setText("");
                            psw.setText("");
                        }
                    });
                    boite.show();
                } else
                {

if(u.getType().equals("Technicien")) {
    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
    Intent t = new Intent(Login.this, TechAct.class);
    t.putExtra("login", login.getText().toString());
    startActivity(t);

}else {
    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
    Intent t = new Intent(Login.this, ChefAct.class);
    t.putExtra("login", login.getText().toString());
    startActivity(t);
}

                }
            }
        });
    }
}
