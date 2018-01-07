package com.privat.ritz.silvan.mymqtttest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {
    static boolean mqttConnected= false;
    RadioButton ledHeizung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUIElements();
        registerClickListener();
    }

    private void getUIElements(){
        ledHeizung = (RadioButton) findViewById(R.id.ledHeizungsstatus);
    }

    private void registerClickListener(){
        Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttConnectionClicked();
            }
        }));
    }

    private void registerTopics(){
        ledHeizung.setBackgroundColor(Color.GREEN);
    }

    private void mqttConnectionClicked(){
        if(!mqttConnected){
            mqttConnect();
        }else{
            mqttDisconnect();
        }
    }

    public void mqttDisconnect(){
        //todo:implement
    }

    public void mqttConnect(){
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("mqttConnect", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("mqttConnect", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        registerTopics();
    }


}

