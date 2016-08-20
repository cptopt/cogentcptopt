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
import com.cogent.harikrishna.cogentcptopt.registration.Register;

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
public class Communication extends Activity
{
    private static final String TAG = Register.class.getSimpleName();
    private JSONObject jsonObject1,jsonObject2 = null;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;
    private InputStream inputStream = null;
    URL url = null;
    String finalJson,errmsg;
    String mail,empid;
    RadioButton jobmailon,jobmailoff,notificationmailon,notificationmailoff,clientmailon,clientmailoff,paidmailon,paidmailoff,
            notificationsmson,notificationsmsoff,clientsmson,clientsmsoff,paidsmson,paidsmsoff;

    String jsonmail,jsonnotificationalert,jsonclient,jsonpaidservices,jsonnotification1,jsonclient1,jsonpaidservices1;
    RadioGroup mailalerts,mailimpnotifications,
            mailclient,mailpaidservices,smsalerts,smsimpnotifications,smsclient,smspaidservices;
    String selectedmailalert,selectedmailnotification,selectedmailclient,selectedmailpaidservices,
            selectedsmsalert,selectedsmsnotification,selectedsmsclient,selectedsmspaidservices;
    Button savechanges;
    String alertstatus,notificationstaus,clientstatus,paidservices,notificationstaus1,clientstatus1,paidservices1;
    int visibilitystatus1,visibilitystatus2,visibilitystatus3,visibilitystatus4,visibilitystatus5,visibilitystatus6,visibilitystatus7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);
        new GettingCommunicationJSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/communicationSettings");
        savechanges=(Button)findViewById(R.id.communicationsavechanges);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mail  = prefs.getString("EMAILID","");
        empid=prefs.getString("EMPID","");
        jobmailon= (RadioButton) findViewById(R.id.onbuttonE1);
        jobmailoff=(RadioButton) findViewById(R.id.offButtonE1);
        notificationmailon= (RadioButton) findViewById(R.id.onbuttonE2);
        notificationmailoff=(RadioButton) findViewById(R.id.offbuttonE2);
        clientmailon= (RadioButton) findViewById(R.id.onbuttonE3);
        clientmailoff=(RadioButton) findViewById(R.id.offbuttonE3);
        paidmailon= (RadioButton) findViewById(R.id.onbuttonE4);
        paidmailoff=(RadioButton) findViewById(R.id.offbuttonE4);
        notificationsmson= (RadioButton) findViewById(R.id.onbuttonS1);
        notificationsmsoff=(RadioButton) findViewById(R.id.offbuttonS1);
        clientsmson= (RadioButton) findViewById(R.id.onbuttonS2);
        clientsmsoff=(RadioButton) findViewById(R.id.offbuttonS2);
        paidsmson= (RadioButton) findViewById(R.id.onbuttonS3);
        paidsmsoff=(RadioButton) findViewById(R.id.offbuttonS3);


        mailalerts=(RadioGroup)findViewById(R.id.jobalertgrp);
        mailimpnotifications=(RadioGroup)findViewById(R.id.impNotifctngrp);
        mailclient=(RadioGroup)findViewById(R.id.clientgrp);
        mailpaidservices=(RadioGroup)findViewById(R.id.paidgrp);
        //     smsalerts=(RadioGroup)findViewById(R.id.jobalertgrp1);
        smsimpnotifications=(RadioGroup)findViewById(R.id.impNotifctngrp1);
        smsclient=(RadioGroup)findViewById(R.id.clientgrp1);
        smspaidservices=(RadioGroup)findViewById(R.id.paidgrp1);

        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private class CommunicationJSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            selectedmailalert=((RadioButton)findViewById(mailalerts.getCheckedRadioButtonId())).getText().toString();
            selectedmailnotification=((RadioButton)findViewById(mailimpnotifications.getCheckedRadioButtonId())).getText().toString();
            selectedmailclient=((RadioButton)findViewById(mailclient.getCheckedRadioButtonId())).getText().toString();
            selectedmailpaidservices=((RadioButton)findViewById(mailpaidservices.getCheckedRadioButtonId())).getText().toString();
            //       selectedsmsalert=((RadioButton)findViewById(smsalerts.getCheckedRadioButtonId())).getText().toString();
            selectedsmsnotification=((RadioButton)findViewById(smsimpnotifications.getCheckedRadioButtonId())).getText().toString();
            selectedsmsclient=((RadioButton)findViewById(smsclient.getCheckedRadioButtonId())).getText().toString();
            selectedsmspaidservices=((RadioButton)findViewById(smspaidservices.getCheckedRadioButtonId())).getText().toString();

//            Log.e(TAG, "Visibilitystatus: " + selectedmailalert);
//        alert
            if (selectedmailalert.equals("on"))
            {
                visibilitystatus1=1;
            }
            else
            {
                visibilitystatus1=0;
            }
            alertstatus=Integer.toString(visibilitystatus1);
            Log.e(TAG, "Visibilitystatus: " + alertstatus);
            //notification
            if (selectedmailnotification.equals("on"))
            {


                visibilitystatus2=1;
            }
            else
            {
                visibilitystatus2=0;
            }
            notificationstaus=Integer.toString(visibilitystatus2);
            Log.e(TAG, "Visibilitystatus: " + notificationstaus);
            //client
            if (selectedmailclient.equals("on"))
            {


                visibilitystatus3=1;
            }
            else
            {
                visibilitystatus3=0;
            }
            clientstatus=Integer.toString(visibilitystatus3);
            //paidservices
            if (selectedmailpaidservices.equals("on"))
            {


                visibilitystatus4=1;
            }
            else
            {
                visibilitystatus4=0;
            }
            paidservices=Integer.toString(visibilitystatus4);
            //sms
            if (selectedsmsnotification.equals("on"))
            {


                visibilitystatus5=1;
            }
            else
            {
                visibilitystatus5=0;
            }
            notificationstaus1=Integer.toString(visibilitystatus5);
            if (selectedsmsclient.equals("on"))
            {


                visibilitystatus6=1;
            }
            else
            {
                visibilitystatus6=0;
            }
            clientstatus1=Integer.toString(visibilitystatus6);
            if (selectedsmspaidservices.equals("on"))
            {


                visibilitystatus7=1;
            }
            else
            {
                visibilitystatus7=0;
            }
            paidservices1=Integer.toString(visibilitystatus7);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("jobAlert", "" + alertstatus);
                jsonObject1.put("notificationMailAlert", "" +notificationstaus);
                jsonObject1.put("notificationcallorSMSAlert", "" + notificationstaus1);
                jsonObject1.put("clientMailAlert", "" + clientstatus);
                jsonObject1.put("clientcallorSMSAlert", "" + clientstatus1);
                jsonObject1.put("paidMailAlert", "" + paidservices);
                jsonObject1.put("paidcallorSMSAlert", "" + paidservices1);
                jsonObject1.put("employeeId",""+ empid );


                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("communicationDetails", "" + jsonObj);
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
                        String msg=jobj.getString("msg");
                        Toast.makeText(getApplication(),msg, Toast.LENGTH_SHORT).show();

                    } else
                        errmsg=jobj.getString("err_msg");
                    Toast.makeText(getApplication(), errmsg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }

    private class GettingCommunicationJSONTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
//            mailalerts=(RadioGroup)findViewById(R.id.jobalertgrp);
//            mailimpnotifications=(RadioGroup)findViewById(R.id.impNotifctngrp);
//            mailclient=(RadioGroup)findViewById(R.id.clientgrp);
//            mailpaidservices=(RadioGroup)findViewById(R.id.paidgrp);
//            //     smsalerts=(RadioGroup)findViewById(R.id.jobalertgrp1);
//            smsimpnotifications=(RadioGroup)findViewById(R.id.impNotifctngrp1);
//            smsclient=(RadioGroup)findViewById(R.id.clientgrp1);
//            smspaidservices=(RadioGroup)findViewById(R.id.paidgrp1);
//
//            jobmailon= (RadioButton) findViewById(R.id.onbuttonE1);
//            jobmailoff=(RadioButton) findViewById(R.id.offButtonE1);
//            notificationmailon= (RadioButton) findViewById(R.id.onbuttonE2);
//            notificationmailoff=(RadioButton) findViewById(R.id.offbuttonE2);
//            clientmailon= (RadioButton) findViewById(R.id.onbuttonE3);
//            clientmailoff=(RadioButton) findViewById(R.id.offbuttonE3);
//            paidmailon= (RadioButton) findViewById(R.id.onbuttonE4);
//            paidmailoff=(RadioButton) findViewById(R.id.offbuttonE4);
//            notificationsmson= (RadioButton) findViewById(R.id.onbuttonS1);
//            notificationsmsoff=(RadioButton) findViewById(R.id.offbuttonS1);
//            clientsmson= (RadioButton) findViewById(R.id.onbuttonS2);
//            clientsmsoff=(RadioButton) findViewById(R.id.offbuttonS2);
//            paidsmson= (RadioButton) findViewById(R.id.onbuttonS3);
//            paidsmsoff=(RadioButton) findViewById(R.id.offbuttonS3);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("employeeId", "" + empid);



                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("communicationDetails", "" + jsonObj);
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

            } catch (IOException | JSONException e) {
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
                    JSONObject parentObj = new JSONObject(finalJson);
                    Log.e(TAG, "Response Json: " + parentObj);
                    String str = (String) parentObj.get("communicationDetailsJSON");
                    JSONObject childobj=new JSONObject(str);
                    jsonmail=childobj.getString("jobMailAlert");
                    Log.e(TAG, "Response Json: " + jsonmail);
                    jsonnotificationalert=childobj.getString("notificationMailAlert");
                    Log.e(TAG, "Responsenotificaton: " + jsonnotificationalert);
                    jsonclient=childobj.getString("clientMailAlert");
                    jsonpaidservices=childobj.getString("paidMailAlert");
                    jsonnotification1=childobj.getString("notificationcallorSMSAlert");
                    jsonclient1=childobj.getString("clientcallorSMSAlert");
                    jsonpaidservices1=childobj.getString("paidcallorSMSAlert");
                    if (jsonmail.equals("1"))
                    {
                        mailalerts.check(R.id.onbuttonE1);
                        jobmailon.setChecked(true);
                    }else
                    {
                        mailalerts.check(R.id.offButtonE1);
                        jobmailoff.setChecked(true);
                    }
                    if (jsonnotificationalert.equals("1"))
                    {

                        mailimpnotifications.check(R.id.onbuttonE2);
                        notificationmailon.setChecked(true);
                    }else
                    {
                        mailimpnotifications.check(R.id.offbuttonE2);
                        notificationmailoff.setChecked(true);
                    }
                    if (jsonclient.equals("1"))
                    {
                        mailclient.check(R.id.onbuttonE3);
                        clientmailon.setChecked(true);
                    }else
                    {
                        mailclient.check(R.id.offbuttonE3);
                        clientmailoff.setChecked(true);
                    }
                    if (jsonpaidservices.equals("1"))
                    {
                        mailpaidservices.check(R.id.onbuttonE4);
                        paidmailon.setChecked(true);
                    }
                    else
                    {
                        mailpaidservices.check(R.id.offbuttonE4);
                        paidmailoff.setChecked(true);
                    }
                    if (jsonnotification1.equals("1"))
                    {
                        smsimpnotifications.check(R.id.onbuttonS1);
                        notificationsmson.setChecked(true);
                    }
                    else
                    {
                        smsimpnotifications.check(R.id.offbuttonS1);
                        notificationmailoff.setChecked(true);
                    }
                    if (jsonclient1.equals("1"))
                    {
                        smsclient.check(R.id.onbuttonS2);
                        clientsmson.setChecked(true);
                    }
                    else
                    {
                        smsclient.check(R.id.offbuttonS2);
                        clientsmsoff.setChecked(true);
                    }
                    if (jsonpaidservices1.equals("1"))
                    {
                        smspaidservices.check(R.id.onbuttonS3);
                        paidsmson.setChecked(true);
                    }else
                    {
                        smspaidservices.check(R.id.offbuttonS3);
                        paidsmsoff.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}

