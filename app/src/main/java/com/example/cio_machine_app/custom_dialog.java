package com.example.cio_machine_app;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class custom_dialog extends AppCompatDialogFragment {
    String title;
    EditText wifi_pass_text;
    custom_dialog(String _title){
        this.title = _title;
    }
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.wifi_config_layout,null);
        wifi_pass_text = view.findViewById(R.id.wifi_password);
        builder.setView(view)
                .setTitle(this.title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = String.valueOf(wifi_pass_text.getText());
                        if(pass.length()<8 || pass.length()>=63) {
                            Toast toast = Toast.makeText(Static_class.curr_main, R.string.pass_len_note, Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }else if(pass.indexOf(",")!=-1){
                            Toast.makeText(Static_class.curr_main,"Please remove the ',' comma",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String temp;
                        temp = "1,"+title+","+pass;
                        Static_class.curr_websocket.send(temp);
                    }
                });
        return builder.create();
    }
}
