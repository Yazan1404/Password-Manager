package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PasswortGenerieren extends AppCompatActivity {

    EditText edit_pin;
    Button btngenerate, btnChange;
    TextView showgeneratedpassword;

    public String gPassword;
    public static String PIN = "";
    public static final String SHARED_PREFS = "shared_prefs";

    public ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwort_generieren);

        btngenerate = (Button) findViewById(R.id.btngenerate);
        showgeneratedpassword = (TextView) findViewById(R.id.showgeneratedpassword);
        btnChange = (Button) findViewById(R.id.btnchange);
        edit_pin = (EditText) findViewById(R.id.newPin);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        loadPIN();

        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showgeneratedpassword.setText(generatePasswort(12));
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity._pin = edit_pin.getText().toString();
                savePIN();
                edit_pin.setText("");
            }
        });

        showgeneratedpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gPassword = showgeneratedpassword.getText().toString();
                if (!gPassword.equals("")) {
                    ClipData clipData = ClipData.newPlainText("text", gPassword);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(PasswortGenerieren.this, "copied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String generatePasswort(int length) {
        char[] chars = "ABCDEFGHIGKLMNOPQRSTUVWSYZabcdefghigklmnopqrstuvwsyz$%&/()=?*'#€µ".toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void savePIN() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PIN, edit_pin.getText().toString());
        editor.apply();
    }

    public void loadPIN() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        MainActivity._pin = sharedPreferences.getString(PIN, "");
    }
}
