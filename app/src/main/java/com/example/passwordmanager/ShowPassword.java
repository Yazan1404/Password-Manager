package com.example.passwordmanager;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

public class ShowPassword extends AppCompatActivity {

    int value = 0;
    Button btnSave, btnLoad;
    TextView tvShowPassword;
    EditText edPasswortEingeben;
    public String filename = "passwort.xml", fileContent = "Chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_password);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnSave = (Button) findViewById(R.id.btnSave);
        tvShowPassword = (TextView) findViewById(R.id.txShowPassword);
        edPasswortEingeben = (EditText) findViewById(R.id.edPassworteingeben);

        Intent intent = getIntent();
        value = intent.getIntExtra("positionValue", 0);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
    }

    public void load() {

    }

    public void save() {

    }
}