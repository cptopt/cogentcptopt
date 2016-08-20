package com.cogent.harikrishna.cogentcptopt.registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.MainActivity;
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
 * Created by Hari Krishna on 7/15/2016.
 */
public class Login extends Fragment {
    private static final String TAG = Login.class.getSimpleName();
    EditText emailId, password,sample;
    Button submit;
    TextView textView;
    String empid,errmsg;
    String loginid,loginpassword,mailtxt;
    String finalJson;
    JSONObject jsonObject1,jsonObject2 = null;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login,container,false);

        emailId = (EditText) view.findViewById(R.id.emailId);
        password = (EditText) view.findViewById(R.id.password);
        submit = (Button) view.findViewById(R.id.submit);
        textView = (TextView) view.findViewById(R.id.textView);




        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Enter Your Mail Id");
                        final EditText input=new EditText(getContext());
                        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        builder.setView(input);
                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mailtxt=input.getText().toString();
                                // sample.setText(mailtxt);
                                new ForgotPassTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/recoverpassword");
                            }
                        });
                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
//                LayoutInflater li=LayoutInflater.from(getContext());
//                View mview=li.inflate(R.layout.forgotpassword,(ViewGroup)getView(),false);
//                AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
//                adb.setView(mview);
//               final EditText forgotmail= (EditText) view.findViewById(R.id.email);
//                //forgPassEmail=forgotmail.getText().toString();
//                adb.setCancelable(false)
//                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                sample.setText(forgotmail.getText().toString());
//                               // new ForgotPassTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/recoverpassword");
//                               // forgPassEmail=forgotmail.getText().toString();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog dialog=adb.create();
//                dialog.show();
//
                    }
                });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginid=emailId.getText().toString();
                loginpassword=password.getText().toString();

                if(emailId.length()==0 && password.length()==0) {
                    emailId.setError("Email cannot be blank");
                    //emailId.setError(Html.fromHtml("<font color='green'>Hello</font>"));
                    password.setError("Password cannot be blank");
                    //password.setError(Html.fromHtml("<font color='green'>Hello</font>"));
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches()){
                    emailId.setError("Invalid Email Address");
                }else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    editor = preferences.edit();
                    editor.putString("EMAILID",loginid);
                    editor.commit();
                    String str=preferences.getString("EMAILID","");
                    Log.e(TAG, "Preferences string: "+str );
                    new LoginServerTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/login");


                }


            }
        });
        return view;
    }


    public class LoginServerTask extends AsyncTask<String, String, String> {

        HttpURLConnection connection = null;
        BufferedReader bufferedReader;
        URL url;
        InputStream inputStream;

        @Override
        protected String doInBackground(String... params) {



            try {


                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();


                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("loginid","" + loginid);
                jsonObject1.put("loginpassword","" + loginpassword);



                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: "+jsonObj );
                //Header
                //connection.setRequestProperty("jsonobject",""+jsonObject1.toString());
                connection.setRequestProperty("loginObject", ""+jsonObj);
                connection.connect();
                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }


                finalJson = buffer.toString();
                Log.e(TAG, "JSON Object"+finalJson );
                // JSONObject parentObject=new JSONObject(finalJson);
                // JSONArray parentArray=parentObject.getJSONArray("worldpopulation");
                // JSONObject lastObject=parentArray.getJSONObject(1);
//                JSONArray parentArr=new JSONArray(finalJson);
//                JSONObject lastObject=parentArr.getJSONObject(1);
//                String result=lastObject.getString("status");


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
            if (finalJson!=null){
                try {
                    JSONObject jobj = new JSONObject(finalJson);
                    Log.e(TAG, "Response Json: "+jobj );
                    String str = (String) jobj.get("status");
                    if (str.equals("true")) {

                        String msg=jobj.getString("msg");
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        empid=(String)jobj.get("employeeId");
                        Log.e(TAG, "EmployeeId: "+empid );
                        editor.putString("EMPID",empid);
                        editor.commit();
                        Intent i=new Intent(getActivity(),MainActivity.class);
                        startActivity(i);
//                        startActivity(new Intent(getActivity(), MainActivity.class));
//                        Fragment frag = new Login();
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.replace(R.id.register_frag,frag);
//
                    }else
                        errmsg=(String)jobj.get("err_msg");
                    Log.e(TAG, "ErrorMsg: "+errmsg );
                    Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
            emailId.setText("");
            password.setText("");
            super.onPostExecute(result);
        }
    }

    public class ForgotPassTask extends AsyncTask<String,String,String>{

        HttpURLConnection connection = null;
        BufferedReader bufferedReader;
        URL url;
        InputStream inputStream;


        @Override
        protected String doInBackground(String... params) {

            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                jsonObject2 = new JSONObject();
                jsonObject2.put("mail", ""+mailtxt);

                String jsonObj2 = jsonObject2.toString();

                connection.setRequestProperty("forgotpassworddetails", ""+jsonObj2);
                connection.connect();
                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }


                finalJson = buffer.toString();
                Log.e(TAG, "RESPONSE FROM SERVER IS: "+finalJson);
                return finalJson;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (finalJson!=null){
                try {
                    JSONObject jsonobj=new JSONObject(finalJson);
                    Log.e(TAG, "JSON OBJECT FROM SERVER: "+jsonobj);
                    String str=jsonobj.getString("status");
                    if (str.equals("true"))
                    {
                        Toast.makeText(getActivity(),"send to server",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),"invalid",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.onPostExecute(result);
        }
    }
}
