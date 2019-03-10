package com.snakegj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class Options extends AppCompatActivity {

    private Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        btnJouer = findViewById(R.id.btnSon);
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnJouer.getText().equals("On")) {
                    Menu.getPlayer().stop();
                    btnJouer.setText("Off");
                }
                else {
                    try {
                        Menu.getPlayer().prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Menu.getPlayer().seekTo(0);
                    Menu.getPlayer().start();
                    btnJouer.setText("On");
                }
            }
        });
    }
}
