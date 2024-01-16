package com.example.cio_machine_app;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class websocket_client {
//    String url = ;

    Splash splash_context;
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url("ws://192.168.4.1:81").build();
    WebSocketListener websocket_listner = new WebSocketListener() {
        @Override
        public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            System.out.println("websocket close");
        }

        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
//            System.out.println("websocket closing");
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            System.out.println("websocket failure");
            if(Static_class.curr_main!=null){
                System.out.println("curr_main not null");
                Static_class.error_no = R.string.connx_error;
                Static_class.curr_main.error_activity();
                return;
            }
            splash_context.error_activity();
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            System.out.println(text);
            Static_class.curr_ssid=get_connected_ssid(text);
            Static_class.ssid_names = get_ssid_list(text);
            splash_context.main_activity();
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {

        }

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
//            curr_websocket = webSocket;
            webSocket.send("0");
//            System.out.println("open");

        }
    };

    public void connect(Splash _context){
        this.splash_context = _context;
        Static_class.curr_websocket  = client.newWebSocket(this.request,this.websocket_listner);
    }

    public String get_connected_ssid(String ssid_s){
        int length_str = ssid_s.length();
        int colon_chr =ssid_s.indexOf(":");
        if(colon_chr!=-1){
            String connected_ssid =ssid_s.substring(colon_chr + 1,length_str);
            return connected_ssid;
        }
        return "";
    }
    public List<String> get_ssid_list(String ssid_s){
        List<String> temp_list = new ArrayList<>();
        if(ssid_s.length()>0){
            int colon_chr =ssid_s.indexOf(":");
            if(colon_chr!=-1){
                ssid_s =ssid_s.substring(0,colon_chr);
            }
            String[] temp_str = ssid_s.split(",");
            temp_list = Arrays.asList(temp_str);
        }
        return temp_list;
    }
}
