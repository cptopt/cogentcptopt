package com.cogent.harikrishna.cogentcptopt.registration;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.R;
import com.cogent.harikrishna.cogentcptopt.adapterClasses.Signinadapter;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Register extends Fragment {
    private static final String TAG = Register.class.getSimpleName();
    private static final String PACKAGE = "com.cogentdatasolutions.project1";
    Button register;


    EditText fullname,emailAddress, password1, password2;

    //ImageButton ln_login,fb_login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register,container,false);

        fullname = (EditText) view.findViewById(R.id.fullname);
        emailAddress = (EditText) view.findViewById(R.id.emailAddress);
        password1 = (EditText) view.findViewById(R.id.password1);
        password2 = (EditText) view.findViewById(R.id.password2);
        register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password1.getText().toString();
                if (fullname.length() == 0 && emailAddress.length() == 0 && password1.length() == 0 && password2.length() == 0) {
                    fullname.setError("First Mention Your Name");
                    emailAddress.setError("Email Id required for registration");
                    password1.setError("Password required");
                    password2.setError("password required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.getText().toString()).matches()) {
                    emailAddress.setError("Invalid Email Id");
                } else if (pass.length() < 8 || pass.length() > 15) {
                    password1.setError("Password Should Contain 8-15 Characters");
                    password2.setError("Password Should Contain 8-15 Characters");
                } else if (!(password1.getText().toString().equals(password2.getText().toString()))) {
                    password1.setError("Password and Re-Enter password Must Be Same");
                } else {
                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
