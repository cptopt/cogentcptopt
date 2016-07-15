package com.cogent.harikrishna.cogentcptopt.adapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cogent.harikrishna.cogentcptopt.R;

/**
 * Created by Hari Krishna on 7/15/2016.
 */
public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private  final int[] Imageid;

    public CustomGrid(Context c,String[] web,int[] Imageid){
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View gv;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            gv = new View(mContext);
            gv = inflater.inflate(R.layout.gridsingle,null);
            TextView textView = (TextView) gv.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) gv.findViewById(R.id.img);
            Animation animation1 = AnimationUtils.loadAnimation(mContext,R.anim.popup);
            imageView.startAnimation(animation1);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
        } else {

            gv = (View) convertView;
        }
        return gv;
    }

}