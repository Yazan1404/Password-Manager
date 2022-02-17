package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button _btnOne, _btnTwo, _btnThree, _btnFour,
            _btnFive, _btnSix, _btnSeven, _btnEight,
            _btnNine, _btnZero, btnConfirm, btngenerieren, btnPIN;
    EditText _edPasswort;
    ImageButton _btnBackspace;
    public static String _pin = "0000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAuthentification();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);

        _pin = sharedPreferences.getString(PasswortGenerieren.PIN, "");

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        _edPasswort = (EditText) findViewById(R.id.edPasswort);

        _btnZero = findViewById(R.id.btnZero);
        _btnOne = findViewById(R.id.btnOne);
        _btnTwo = findViewById(R.id.btnTwo);
        _btnThree = findViewById(R.id.btnThree);
        _btnFour = findViewById(R.id.btnFour);
        _btnFive = findViewById(R.id.btnFive);
        _btnSix = findViewById(R.id.btnSix);
        _btnSeven = findViewById(R.id.btnSeven);
        _btnEight = findViewById(R.id.btnEight);
        _btnNine = findViewById(R.id.btnNine);
        btngenerieren = findViewById(R.id.btngenerieren);
        _btnBackspace = findViewById(R.id.btnBackspace);
        btnPIN = findViewById(R.id.btnSettings);

        _btnZero.setOnClickListener(this);
        _btnOne.setOnClickListener(this);
        _btnTwo.setOnClickListener(this);
        _btnThree.setOnClickListener(this);
        _btnFour.setOnClickListener(this);
        _btnFive.setOnClickListener(this);
        _btnSix.setOnClickListener(this);
        _btnSeven.setOnClickListener(this);
        _btnEight.setOnClickListener(this);
        _btnNine.setOnClickListener(this);
        _btnBackspace.setOnClickListener(this);
        btngenerieren.setOnClickListener(this);
        btnPIN.setOnClickListener(this);

        _edPasswort.setShowSoftInputOnFocus(false);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_edPasswort.getText().toString().equals(_pin)) {
                    startActivity(new Intent(MainActivity.this, KontoActivity.class));
                    _edPasswort.setText("");
                } else if (_edPasswort.equals("")) {
                    btnConfirm.setEnabled(false);
                } else {
                    _edPasswort.setText("");
                    Toast.makeText(MainActivity.this, "Passwort ist Flasch", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnZero:
                updateText(getResources().getString(R.string.ZeroText));
                break;
            case R.id.btnOne:
                updateText(getResources().getString(R.string.OneText));
                break;
            case R.id.btnTwo:
                updateText(getResources().getString(R.string.TwoText));
                break;
            case R.id.btnThree:
                updateText(getResources().getString(R.string.ThreeText));
                break;
            case R.id.btnFour:
                updateText(getResources().getString(R.string.FourText));
                break;
            case R.id.btnFive:
                updateText(getResources().getString(R.string.FiveText));
                break;
            case R.id.btnSix:
                updateText(getResources().getString(R.string.SixText));
                break;
            case R.id.btnSeven:
                updateText(getResources().getString(R.string.SevenText));
                break;
            case R.id.btnEight:
                updateText(getResources().getString(R.string.EightText));
                break;
            case R.id.btnNine:
                updateText(getResources().getString(R.string.NineText));
                break;
            case R.id.btnBackspace:
                int cursorPos = _edPasswort.getSelectionStart();
                int textLen = _edPasswort.getText().length();

                if (cursorPos != 0 && textLen != 0) {
                    SpannableStringBuilder selection = (SpannableStringBuilder) _edPasswort.getText();
                    selection.replace(cursorPos - 1, cursorPos, "");
                    _edPasswort.setText(selection);
                    _edPasswort.setSelection(cursorPos - 1);
                }
                break;
            case R.id.btngenerieren:
            case R.id.btnSettings:
                startActivity(new Intent(MainActivity.this, PasswortGenerieren.class));
                break;
        }
    }

    public void updateText(String strToAdd) {
        String oldStr = _edPasswort.getText().toString();
        int cursorPosition = _edPasswort.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPosition),
                rightStr = oldStr.substring(cursorPosition);
        _edPasswort.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        _edPasswort.setSelection(cursorPosition + strToAdd.length());
    }

    public void getAuthentification() {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Please Verify!!")
                .setDescription("User Authentification is required to proceed")
                .setNegativeButtonText("Cancel")
                .build();
        getPrompt().authenticate(promptInfo);
    }

    private BiometricPrompt getPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Authentification succeeded!!");
                Intent intent = new Intent(MainActivity.this, KontoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Authentification Faild!!!");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
        return biometricPrompt;
    }

    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}