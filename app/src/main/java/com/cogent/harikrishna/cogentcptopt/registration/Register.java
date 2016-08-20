package com.cogent.harikrishna.cogentcptopt.registration;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.R;
import com.cogent.harikrishna.cogentcptopt.adapterClasses.Signinadapter;

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
public class Register extends Fragment {
    private static final String TAG = Register.class.getSimpleName();
    private Button register;
    private JSONObject jsonObject1,jsonObject2 = null;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;
    private InputStream inputStream = null;
    URL url = null;
    EditText fullanme, emailAddress, password1, password2;
    String name, mail, password, confrmpaswrd;
    String finalJson,errmsg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register, container, false);
        fullanme = (EditText) view.findViewById(R.id.fullname);
        emailAddress = (EditText) view.findViewById(R.id.emailAddress);
        password1 = (EditText) view.findViewById(R.id.password1);
        password2 = (EditText) view.findViewById(R.id.password2);
        register = (Button) view.findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = fullanme.getText().toString();
                mail = emailAddress.getText().toString();
                password = password1.getText().toString();
                confrmpaswrd = password2.getText().toString();

                if (emailAddress.length() == 0 && password1.length() == 0 && password2.length() == 0 && fullanme.length() == 0) {
                    fullanme.setError("FullName cannot be Blank");
                    emailAddress.setError("Email Id required for registration");
                    password1.setError("Password required");
                    password2.setError("password required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.getText().toString()).matches()) {
                    emailAddress.setError("Invalid Email Id");
                } else if (password.length() < 8 || password.length() > 15) {
                    password1.setError("password should be 8-15 charactes");
                    password2.setError("password should be 8-15 charactes");
                } else if (!password.trim().equals(confrmpaswrd.trim())) {
                    password1.setError("password and confirm password must be same");
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("EMAILID",mail);
                    editor.commit();
                    String str=preferences.getString("EMAILID","");
                    Log.e(TAG, "Preferences string: "+str );

                    new JSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/registration");


                }

            }
        });

        return view;
    }


    private class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("name", "" + name);
                jsonObject1.put("mail", "" + mail);
                jsonObject1.put("password", "" + password);
                jsonObject1.put("conpassword", "" + confrmpaswrd);


                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("registerObject", "" + jsonObj);
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
                    JSONObject jobj = new JSONObject(finalJson);
                    Log.e(TAG, "Response Json: " + jobj);
                    String str = (String) jobj.get("status");
                    if (str.equals("true")) {
                        String msg=jobj.getString("msg");
                        Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();

                    } else
                        errmsg=jobj.getString("err_msg");
                    Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }

}
