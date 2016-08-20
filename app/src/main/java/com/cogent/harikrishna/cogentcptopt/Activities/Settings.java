package com.cogent.harikrishna.cogentcptopt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cogent.harikrishna.cogentcptopt.R;

/**
 * Created by madhu on 20-Aug-16.
 */
public class Settings extends Activity
{
    Button visibility,communication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        visibility= (Button) findViewById(R.id.visibility);
        communication= (Button) findViewById(R.id.communications);
        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.this,VisibilitySettings.class);
                startActivity(i);
                finish();
            }
        });
        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.this,Communication.class);
                startActivity(i);
                finish();
            }
        });
    }

}

