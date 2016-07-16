package com.cogent.harikrishna.cogentcptopt.tabsfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cogent.harikrishna.cogentcptopt.R;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Profile extends Fragment {
    Button resume, signin, editpf, recommended, tc, policy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        //  Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.popup);
        resume = (Button) view.findViewById(R.id.resume);
        editpf = (Button) view.findViewById(R.id.editprofile);
        recommended = (Button) view.findViewById(R.id.recmnd);
        tc = (Button) view.findViewById(R.id.tC);
        policy = (Button) view.findViewById(R.id.privacy);
        signin = (Button) view.findViewById(R.id.Sign);
        signin.setTag(1);
        signin.setText("SignIn");
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int status = (Integer) v.getTag();
                        if (status == 1) {
                            Intent i = new Intent(getActivity(), Signin.class);
                            startActivity(i);
                            signin.setText("SignOut");
                            signin.setTag(0);

                        } else {
                            signin.setText("SignIn");
                            signin.setTag(1);
                        }
                    }
                });
        return view;
    }
}
