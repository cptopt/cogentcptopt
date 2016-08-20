package com.cogent.harikrishna.cogentcptopt.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
public class EditAddress extends Activity
{
    private static final String TAG = EditAddress.class.getSimpleName();
    private Button save;
    private JSONObject jsonObject1,jsonObject2 = null;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;
    private InputStream inputStream = null;
    URL url = null;
    String msg;

    String finalJson,empId,mail;

    EditText adressLine,adreesLine1,streetName,streetName1,pincode,pincode1;
    Button submit;
    AutoCompleteTextView pState,pCountry,tState,tCountry;
    String adress,adress1,street,street1,pin,pin1,country,country1,state,state1;
    private String jsonaddr1,jsonaddr2,jsonstreet1,jsonstreet2,jsoncountry1,jsoncountry2,jsonstate1,jsonstate2,jsonpin1,jsonpin2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editaddress);
        new GettingAddressDetailsJSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/DisplayEmployeeAddresses");
//        Intent i=getIntent();
//        Bundle extras=i.getExtras();
//        adress=extras.getString("A1");
//        Log.e(TAG,"value"+adress);
//        adress1=extras.getString("A2");
//        street=extras.getString("ST1");
//        street1=extras.getString("ST2");
//        country=extras.getString("C1");
//        country1=extras.getString("C2");
//        state=extras.getString("S1");
//        state1=extras.getString("S2");
//        pin=extras.getString("P1");
//        pin1=extras.getString("P2");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mail  = prefs.getString("EMAILID","");
        empId = prefs.getString("EMPID","");
        adressLine  = (EditText)findViewById(R.id.addrline);

        adreesLine1 = (EditText)findViewById(R.id.addrline1);

        streetName = (EditText)findViewById(R.id.streetname);

        streetName1 = (EditText)findViewById(R.id.streetname1);

        pincode = (EditText)findViewById(R.id.pincode);
        // pincode.setText(pin);
        pincode1 = (EditText)findViewById(R.id.pincode1);
        // pincode1.setText(pin1);
        pState = (AutoCompleteTextView)findViewById(R.id.statespinner);
        //   pState.setText(state1);
        pCountry = (AutoCompleteTextView)findViewById(R.id.countriesSpinner);
        //   pCountry.setText(country1);
        tState = (AutoCompleteTextView)findViewById(R.id.statespinner1);
        //   tState.setText(state);
        tCountry = (AutoCompleteTextView)findViewById(R.id.countriesSpinner1);
        //  tCountry.setText(country);
        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new EditAddressJson().execute("http://10.80.15.119:8080/OptnCpt/rest/service/storeEmployeeAddresses");

                    }
                });

    }
    private class EditAddressJson extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            adress=adressLine.getText().toString();
            adress1=adreesLine1.getText().toString();
            street=streetName.getText().toString();
            street1=streetName1.getText().toString();
            pin=pincode.getText().toString();
            pin1=pincode1.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                jsonObject1 = new JSONObject();
                jsonObject1.put("addressLine1", "" + adress);
                jsonObject1.put("addressLine2", "" + adress1);
                jsonObject1.put("streetName1", "" + street);
                jsonObject1.put("streetName2", "" + street1);
                jsonObject1.put("state1", "" +"texas");
                jsonObject1.put("state2", "" + "hyderabad");
                jsonObject1.put("country1", "" + "UnitedStates");
                jsonObject1.put("country2", "" + "India");
                jsonObject1.put("pincode1", "" + pin);
                jsonObject1.put("pincode2", "" + pin1);
                jsonObject1.put("employeeId", "" + empId);
                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("employeeaddressdetails", "" + jsonObj);
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
                    if (str.equals("updated successfully")) {
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

                    } else
                        msg=(String)jobj.get("err_msg");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.onPostExecute(result);
        }
    }
    private class GettingAddressDetailsJSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("employeeId", "" + empId);
                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("empaddrdetails", "" + jsonObj);
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
                    Log.e(TAG, "Response Json:" + parentObj);
                    String str = (String) parentObj.get("addrDetails");
                    JSONObject childobj=new JSONObject(str);
                    if (childobj.has("addressLine1"))
                    {
                        jsonaddr1 = childobj.getString("addressLine1");
                        Log.e(TAG, "address1: " + jsonaddr1);
                        jsonaddr2=childobj.getString("addressLine2");
                        Log.e(TAG, "address2: " + jsonaddr2);
                        jsonstreet1=childobj.getString("streetName1");
                        jsonstreet2=childobj.getString("streetName2");
                        jsoncountry1=childobj.getString("country1");
                        jsoncountry2=childobj.getString("country2");
                        jsonstate1=childobj.getString("state1");
                        jsonstate2=childobj.getString("state2");
                        jsonpin1=childobj.getString("pincode1");
                        jsonpin2=childobj.getString("pincode2");
                        adressLine.setText(jsonaddr1);
                        adreesLine1.setText(jsonaddr2);
                        streetName.setText(jsonstreet1);
                        streetName1.setText(jsonstreet2);
                        pincode.setText(jsonpin1);
                        pincode1.setText(jsonpin2);
                        pState.setText(jsoncountry2);
                        pCountry.setText(jsoncountry1);
                        tState.setText(jsonstate1);
                        tCountry.setText(jsoncountry1);
//                    if (str.equals("true")) {
//                        Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
//  }

//                      //  Intent i=new Intent(EditDetails.this,EditAddress.class);
//                        i.putExtra("A1",addr1);
//                        i.putExtra("A2",addr2);
//                        i.putExtra("S1",state1);
//                        i.putExtra("S2",state2);
//                        i.putExtra("P1",pin1);
//                        i.putExtra("C1",country1);
//                        i.putExtra("C2",country2);
//                        i.putExtra("ST1",street1);
//                        i.putExtra("ST2",street2);
//                        i.putExtra("P2",pin2);
//                        startActivity(i);
                    }else {
                        adressLine.setText("");
                        adreesLine1.setText("");
                        streetName.setText("");
                        streetName1.setText("");
                        pincode.setText("");
                        pincode1.setText("");
                        pState.setText("");
                        pCountry.setText("");
                        tState.setText("");
                        tCountry.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {


                Toast.makeText(getApplicationContext(), "Server Failed", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}

