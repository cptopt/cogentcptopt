package com.cogent.harikrishna.cogentcptopt.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by madhu on 20-Aug-16.
 */
public class AddProject extends Activity {
    Button savechanges,cancel;
    private static final String TAG = Register.class.getSimpleName();
    private JSONObject jsonObject1,jsonObject2 = null;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;
    private InputStream inputStream = null;
    URL url = null;
    RadioGroup empType;
    EditText client,projecttitle,fromdate,todate,prolocation,prodescrptn,role,teamsize,skillsused;
    final Calendar myCalendar=Calendar.getInstance();
    SimpleDateFormat sdf;
    String format,clienttxt,titletxt,fromtxt,totxt,proloctxt,projectdescr,roletxt,skillstxt,teamsizetxt,finalJson;
    String radiovalue,mail,empId,msg,errmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_add);
        empType=(RadioGroup)findViewById(R.id.rGroup);
        client = (EditText)findViewById(R.id.client);
        projecttitle = (EditText)findViewById(R.id.protitle);
        fromdate = (EditText)findViewById(R.id.fromdate);
        fromdate.setFocusable(false);
        todate = (EditText)findViewById(R.id.todate);
        todate.setFocusable(false);
        prolocation = (EditText)findViewById(R.id.prolocation);
        prodescrptn = (EditText)findViewById(R.id.prodescrptn);
        role = (EditText)findViewById(R.id.role);
        teamsize = (EditText)findViewById(R.id.teamsize);
        skillsused = (EditText)findViewById(R.id.skills);
//
//        fulltime = (RadioButton)findViewById(R.id.fulltime);
//        parttime = (RadioButton)findViewById(R.id.parttime);
//        contract = (RadioButton)findViewById(R.id.contract);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mail  = prefs.getString("EMAILID","");
        empId = prefs.getString("EMPID","");
        savechanges = (Button)findViewById(R.id.savechanges);
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectdescr=prodescrptn.getText().toString();
                if (prodescrptn == null){
                    Toast.makeText(getApplicationContext(),"Project Description Can't Be Blank",Toast.LENGTH_LONG).show();
                }else if (projectdescr.length()<20&&projectdescr.length()>2000){

                    Toast.makeText(getApplicationContext(),"Enter Atleast 20 Characters",Toast.LENGTH_LONG).show();
                }else
                    new AddProjectJSONTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/saveEmpProjectDetails");
            }
        });

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Intent i=new Intent(getApplicationContext(),Xtras.class);
                //  startActivity(i);
                finish();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                UpdateLabel();
            }
        };
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                UpdateLabel1();
            }
        };
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProject.this,date,
                        myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();



            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProject.this,date1,
                        myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


    }
    private void UpdateLabel() {
        format = "dd/MM/yy";
        sdf = new SimpleDateFormat(format, Locale.US);
        fromdate.setText(sdf.format(myCalendar.getTime()));
    }
    private void UpdateLabel1() {
        format = "dd/MM/yy";
        sdf = new SimpleDateFormat(format, Locale.US);
        todate.setText(sdf.format(myCalendar.getTime()));
    }
    private class AddProjectJSONTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            radiovalue=((RadioButton)findViewById(empType.getCheckedRadioButtonId())).getText().toString();
            clienttxt=client.getText().toString();
            titletxt=projecttitle.getText().toString();
            fromtxt=fromdate.getText().toString();
            totxt=todate.getText().toString();
            proloctxt=prolocation.getText().toString();
            roletxt=role.getText().toString();
            teamsizetxt=teamsize.getText().toString();
            skillstxt=skillsused.getText().toString();


            super.onPreExecute();
        }

        protected String doInBackground(String... params) {



            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                jsonObject1 = new JSONObject();
                jsonObject1.put("clientName", "" + clienttxt);
                jsonObject1.put("projectTitle", "" + titletxt);
                jsonObject1.put("fromDate", "" + fromtxt);
                jsonObject1.put("toDate", "" + totxt);
                jsonObject1.put("projectLocation", "" + proloctxt);
                jsonObject1.put("employmentType", "" + radiovalue);
                jsonObject1.put("projectDescription", "" + projectdescr);
                jsonObject1.put("role", "" + roletxt);
                jsonObject1.put("teamSize", "" + teamsizetxt);
                jsonObject1.put("skillsUsed", "" + skillstxt);
                jsonObject1.put("mail", "" + mail);
                jsonObject1.put("employeeId", "" + empId);

                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("projectDetails", "" + jsonObj);
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
                        msg = jobj.getString("msg");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    } else
                        errmsg = jobj.getString("err_msg");
                    Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }
}

