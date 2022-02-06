package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Details extends AppCompatActivity {
    String[] etat = {"Pending", "Done"};
    AutoCompleteTextView ac;
    ArrayAdapter<String> arrayAdapter;
    TextView desc, add, date, etatD;
    Button cs,gi;
    String id;
    Button expand, inter,expand1;
    LinearLayout expandable, expandable1;
    EditText descI;
    CardView card, card1;
    ListView lv;
    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        desc = findViewById(R.id.descD);
        add = findViewById(R.id.addD);
        date = findViewById(R.id.dateD);
        cs = findViewById(R.id.changeState);
        gi = findViewById(R.id.getInterv);
        etatD = findViewById(R.id.etatD);
        expand = findViewById(R.id.add_inter);
        expand1 = findViewById(R.id.show_interv);
        expandable = findViewById(R.id.expandable_view);
        expandable1 = findViewById(R.id.expandable_view1);
        card = findViewById(R.id.cardview_expandable);
        card1 = findViewById(R.id.cardview_expandable1);

        lv=findViewById(R.id.list_interv);

        inter= findViewById(R.id.inter);
        descI = findViewById(R.id.descinter);
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            id = extra.getString("id");
            Log.d("ggg", id);
        }

        //String etat = ac.getText().toString();

        ///////////////////////////////////////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

          String url="http://192.168.3.57/project/one.php";
        //String url = "http://192.168.10.57/project/one.php";
        //  String url="http://172.16.110.10/user.php?username="+userName+"&pwd="+pwd;

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", id));

        HttpPost httppost = new HttpPost(url);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("ggggg", url);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            Log.d("gggggg", "rrrrr");
            HttpResponse response = httpclient.execute(httppost);


            InputStream inputStream = response.getEntity().getContent();
            String result = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            result = sb.toString();
            Log.d("ggggg", result);
            //res.setText(result);

            JSONArray jArray = new JSONArray(result);// this statment gives error@Override

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject json = jArray.getJSONObject(i);

                desc.setText(json.getString("description"));
                add.setText(json.getString("gps"));
                date.setText(json.getString("dateI"));
                etatD.setText(json.getString("etat"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ce = etatD.getText().toString();

                //String url = "http://192.168.10.57/project/editState.php";
                String url = "http://192.168.3.57/project/editState.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("id", id));
                params.add(new BasicNameValuePair("etat", ce));
                HttpPost httppost = new HttpPost(url);
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    //HttpPost httppost=new HttpPost(url);
                    Log.d("gggggg", "rrrrr");
                    HttpResponse response = httpclient.execute(httppost);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                    Intent in = new Intent(Details.this, ChefAct.class);
                //                 startActivity(in);

                TextView state = (TextView) findViewById(R.id.etatD);

                if (ce.equals("Pending")) {
                    state.setText("Done");
                } else {

                    state.setText("Pending");
                }

            }

        });
        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d = descI.getText().toString();
                Log.d("ggg",d);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                // String url = "http://172.16.110.40/project/addIssue.php";
              //  String url="http://192.168.10.57/project/intervention.php";
                String url="http://192.168.3.57/project/intervention.php";
                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("id_issue",id));
                params.add(new BasicNameValuePair("desc_inter", d));
                HttpPost httppost = new HttpPost(url);
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    //HttpPost httppost=new HttpPost(url);
                    Log.d("gggggg", "rrrrr");
                    HttpResponse response = httpclient.execute(httppost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Details.this, ChefAct.class);
                startActivity(i);
            }
        });

        gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                //  String url="http://172.16.110.40/project/user.php";
                String url = "http://192.168.3.57/project/allInterv.php";
                //  String url="http://172.16.110.10/user.php?username="+userName+"&pwd="+pwd;

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("id_issue", id));

                HttpPost httppost = new HttpPost(url);
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("ggggg", url);

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.d("gggggg", "rrrrr");
                    HttpResponse response = httpclient.execute(httppost);


                    InputStream inputStream = response.getEntity().getContent();
                    String result = null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    result = sb.toString();
                    Log.d("ggggg", result);
                    //res.setText(result);

                    JSONArray jArray = new JSONArray(result);// this statment gives error@Override

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject json = jArray.getJSONObject(i);

                        HashMap<String,String> m = new HashMap<String, String>();

                        m.put("desc_inter",json.getString("desc_inter"));

                        m.put("date_inter",json.getString("date_inter"));

                        values.add(m);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
                SimpleAdapter adapter= new SimpleAdapter(Details.this,values,R.layout.interv, new String[]{"desc_inter","date_inter"},new int[]{ R.id.description_inter, R.id.dateInter});
                lv.setAdapter(adapter);
            }
        });

    }

    public void showMore(View view) {
        if (expandable.getVisibility() == View.GONE) {
            //  expand.setText("show less");
            TransitionManager.beginDelayedTransition(card, new AutoTransition());
            expandable.setVisibility(View.VISIBLE);
        } else {
            //   expand.setText("show more");
            TransitionManager.beginDelayedTransition(card, new AutoTransition());
            expandable.setVisibility(View.GONE);

        }
    }

    public void addMore(View view)
    {
        if (expandable1.getVisibility() == View.GONE) {
            //  expand1.setText("show less");
            TransitionManager.beginDelayedTransition(card1, new AutoTransition());
            expandable1.setVisibility(View.VISIBLE);
        } else {
            //   expand.setText("show more");
            TransitionManager.beginDelayedTransition(card1, new AutoTransition());
            expandable1.setVisibility(View.GONE);

        }
    }
}