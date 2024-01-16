package com.example.cio_machine_app;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    Animation scale_up;
    ViewFlipper view_flip;
    ListView list_view ;
    TextView ssid_title;
    EditText user_id,hs_pass,hs_re_pass,uri_edit_text;
    Button add_user_btn,change_wifi_btn,change_pass_btn,user_submit_btn,hs_pass_sub_btn,change_uri_btn
            ,uri_sub_btn;
//    = new ArrayList<>(Arrays.asList("Tp-link","Huawei-mpsv","itx-solution.com","Hacker","danger"));
//    List<String> ssid_names ;
//    String curr_str = "Huawei-mpsv";
    websocket_client w_c = new websocket_client();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Static_class.curr_main = this;
        hs_pass = findViewById(R.id.hs_password);
        hs_re_pass = findViewById(R.id.hs_re_password);
        hs_pass_sub_btn = findViewById(R.id.hs_sub_btn);
        user_id = findViewById(R.id.user_id);
        user_submit_btn =findViewById(R.id.user_id_submit_btn);
        add_user_btn = findViewById(R.id.add_user_btn);
        change_pass_btn = findViewById(R.id.change_pass_btn);
        change_wifi_btn = findViewById(R.id.change_wifi_btn);
        view_flip = findViewById(R.id.view_filp);
        list_view = findViewById(R.id.list_view);
        ssid_title = findViewById(R.id.ssid_title);
        change_uri_btn = findViewById(R.id.change_uri_btn);
        uri_sub_btn = findViewById(R.id.uri_submit_btn);
        uri_edit_text = findViewById(R.id.uri_edit_text);
        scale_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_up);
        list_itemAdapter adapter = new list_itemAdapter(this, Static_class.ssid_names, Static_class.curr_ssid, new list_itemAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                open_dialog(position);
            }
        });
        list_view.setAdapter(adapter);
        ssid_title.setText(Static_class.curr_ssid);

        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_user_btn.startAnimation(scale_up);
                view_flip.setDisplayedChild(1);
            }
        });

        change_wifi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_wifi_btn.startAnimation(scale_up);
                view_flip.setDisplayedChild(0);
            }
        });

        change_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_pass_btn.startAnimation(scale_up);
                view_flip.setDisplayedChild(2);
            }
        });

        change_uri_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_uri_btn.startAnimation(scale_up);
                view_flip.setDisplayedChild(3);
            }
        });

        uri_sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = String.valueOf(uri_edit_text.getText());
                int dotCount = countCharacter(temp, '.');
                int colonCount = countCharacter(temp,':');
                if(dotCount==3 && colonCount==1){
                    String data = "4,"+temp;
                    Static_class.curr_websocket.send(data);
                    uri_edit_text.setText("");
                    return;
                }else{
                    Toast.makeText(MainActivity.this, R.string.wrong_format, Toast.LENGTH_SHORT).show();
                }

            }
        });

        user_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = String.valueOf(user_id.getText());
                if(temp.length()==0) {
                    Toast.makeText(MainActivity.this, "please enter the ID first than Submit", Toast.LENGTH_SHORT).show();
                    return;
                }
                user_id.setText("");
                String send_str = "3,"+temp;
                Static_class.curr_websocket.send(send_str);
            }
        });

        hs_pass_sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = String.valueOf(hs_pass.getText());
                String re_pass = String.valueOf(hs_re_pass.getText());

                if(!pass.equals(re_pass)){
                    Toast.makeText(MainActivity.this,"Password not same",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pass.length()<8 || pass.length()>50){
                    Toast.makeText(MainActivity.this,"Password length must reside between 8 to 50 character",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pass.indexOf(",")!=-1){
                    Toast.makeText(MainActivity.this,"Please remove the ',' comma",Toast.LENGTH_SHORT).show();
                    return;
                }
                hs_pass.setText("");
                hs_re_pass.setText("");
                String temp = "2,"+pass;
                Static_class.curr_websocket.send(temp);
            }
        });


    }
    public void open_dialog(int position){
        custom_dialog dialog = new custom_dialog(Static_class.ssid_names.get(position));
        dialog.show(getSupportFragmentManager(),"custom_dialog");
    }
    public void error_activity(){
        Intent i = new Intent(this,Error_Activity.class);
        startActivity(i);
        finish();

    }
    public int countCharacter(String input, char character) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == character) {
                count++;
            }
        }

        return count;
    }
}