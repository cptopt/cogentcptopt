package com.cogent.harikrishna.cogentcptopt.tabsfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cogent.harikrishna.cogentcptopt.Activities.EditDetails;
import com.cogent.harikrishna.cogentcptopt.Activities.Fab;
import com.cogent.harikrishna.cogentcptopt.R;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Profile extends Fragment {
    Button profile,signin,editpf,recommended,tc,policy;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile,container,false);
        //  Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.popup);
        profile=(Button)view.findViewById(R.id.myprofile);
        editpf=(Button)view.findViewById(R.id.editprofile);
        recommended=(Button)view.findViewById(R.id.recmnd);
        tc=(Button)view.findViewById(R.id.tC);
        policy=(Button)view.findViewById(R.id.privacy);
        signin=(Button)view.findViewById(R.id.Sign);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Fab.class);
                startActivity(i);
            }
        });
        signin.setOnClickListener(


                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent i=new Intent(getActivity(),Signin.class);
                        signin.setEnabled(false);
                        startActivity(i);




                    }
                });
        editpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getActivity(),EditDetails.class);
                startActivity(i);
            }
        });

        return view;
    }


}