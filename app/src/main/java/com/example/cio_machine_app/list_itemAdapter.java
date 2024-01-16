package com.example.cio_machine_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class list_itemAdapter extends BaseAdapter {
    List<String> ssid_name;
    Animation scale_up;
    String curr_ssid;
    Context context;
    ButtonClickListener buttonClickListener;

    // Interface to define the button click listener
    public interface ButtonClickListener {
        void onButtonClick(int position);
    }

    list_itemAdapter(Context context, List<String> ssid_names, String curr_ssid, ButtonClickListener listner){
        this.context = context;
        this.curr_ssid = curr_ssid;
        this.ssid_name = ssid_names;
        this.buttonClickListener = listner;
        scale_up = AnimationUtils.loadAnimation(context,R.anim.scale_up);
    }

    @Override
    public int getCount() {
        return ssid_name.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        TextView text = convertView.findViewById(R.id.ssid_name);
        Button button = convertView.findViewById(R.id.connect_btn);
        text.setText(ssid_name.get(i));
        if(ssid_name.get(i)==this.curr_ssid){
            button.setEnabled(false);
            button.setBackgroundColor(R.color.black);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the listener that the button is clicked
                if (buttonClickListener != null) {
                    button.startAnimation(scale_up);
                    buttonClickListener.onButtonClick(i);
                }
            }
        });

        return convertView;
    }
}

