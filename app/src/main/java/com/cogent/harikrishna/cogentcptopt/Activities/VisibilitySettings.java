package com.cogent.harikrishna.cogentcptopt.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by madhu on 20-Aug-16.
 */
public class VisibilitySettings extends Activity
{
    Button save;
    RadioGroup visibilitygrp;
    String selectedradio,msg,errmsg;
    private static final String TAG = VisibilitySettings.class.getSimpleName();
    String finalJson,mail,status;
    private JSONObject jsonObject1,jsonObject2 = null;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;
    private InputStream inputStream = null;
    URL url = null;
    int visibilitystatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visibility);
        save=(Button)findViewById(R.id.save);
        visibilitygrp=(RadioGroup)findViewById(R.id.visibilitygrp);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mail  = prefs.getString("EMAILID","");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VisibilityJSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/VisibilitySettings");
                //  Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
    private class VisibilityJSONTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            selectedradio=((RadioButton)findViewById(visibilitygrp.getCheckedRadioButtonId())).getText().toString();
            Log.e(TAG, "Visibilitystatus: " + selectedradio);
            if (selectedradio.equals("visible as Active"))
            {
                visibilitystatus=1;
            }
            else
            {
                visibilitystatus=0;
            }
            status=Integer.toString(visibilitystatus);
            Log.e(TAG, "Visibilitystatus: " + status);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("visibilityStatus", "" + status);
                jsonObject1.put("mail", "" + mail);



                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("visibilityDetails", "" + jsonObj);
                connection.connect();
                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }


                finalJson = buffer.toString();
                Log.e(TAG, "JSON Object" + finalJson);


                return finalJson;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (finalJson != null) {
                try {
                    JSONObject jobj = new JSONObject(finalJson);
                    Log.e(TAG, "Response Json: " + jobj);
                    String str = (String) jobj.get("status");
                    if (str.equals("true")) {
                        msg=jobj.getString("msg");
                        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();

                    } else
                        errmsg=jobj.getString("err_msg");
                    Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}
