package com.cogent.harikrishna.cogentcptopt.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cogent.harikrishna.cogentcptopt.R;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Login extends Fragment {
    EditText emailId, password;
    Button submit;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,container,false);

        emailId = (EditText) view.findViewById(R.id.emailId);
        password = (EditText) view.findViewById(R.id.password);
        submit = (Button) view.findViewById(R.id.submit);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.cogentdatasolutions.com";
                Intent i = new Intent();
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailId.length()==0 && password.length()==0) {
                    emailId.setError("Email cannot be blank");
                    //emailId.setError(Html.fromHtml("<font color='green'>Hello</font>"));
                    password.setError("Password cannot be blank");
                    //password.setError(Html.fromHtml("<font color='green'>Hello</font>"));
                } else if(!Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches()){
                    emailId.setError("Invalid Email Address");
                }


            }
        });
        return view;
    }


}
