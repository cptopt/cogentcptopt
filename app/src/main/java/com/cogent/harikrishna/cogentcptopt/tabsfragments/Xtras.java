package com.cogent.harikrishna.cogentcptopt.tabsfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.R;
import com.cogent.harikrishna.cogentcptopt.adapterClasses.CustomGrid;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class Xtras extends Fragment {

    GridView grid;

    String[]web = {"Add Review","Add Salary","Add Interview","Add Photo","Feedback","Refer a friend"};
    //int[] imageId = {R.drawable.list,R.drawable.dollar,R.drawable.list,R.drawable.pic,R.drawable.mail,R.drawable.group };

    int[] imageId = {
            R.drawable.e,
            R.drawable.f,
            R.drawable.c,
            R.drawable.d,
            R.drawable.b,
            R.drawable.a
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xtras,container,false);
        CustomGrid adapter = new CustomGrid(getActivity(),web,imageId);
        grid = (GridView)view.findViewById(R.id.gv);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"You Clicked at"+web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
