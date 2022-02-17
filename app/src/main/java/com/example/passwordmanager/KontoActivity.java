package com.example.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class KontoActivity extends AppCompatActivity {

    Button btnAdd;
    ListView listView;
    EditText edEingabe;
    String newItem, content;
    public int itemPos = 0;
    public String[] items;
    public TextView textView;
    public ArrayList<String> arrayList;
    public ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);

        edEingabe = (EditText) findViewById(R.id.edEingabe);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        listView = (ListView) findViewById(R.id.listView);
        items = new String[] {"Facebook", "Instagram", "Google", "PC", "Amazon", "Outlook", "Yahoo", "GitHub"};

        arrayList = new ArrayList<>(Arrays.asList(items));
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtInput, arrayList);
        listView.setAdapter(arrayAdapter);

        writeXML();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItem = edEingabe.getText().toString();
                if (!newItem.equals("")) {
                    arrayList.add(newItem);
                } else {
                    Toast.makeText(KontoActivity.this, "Text field can not be empty", Toast.LENGTH_SHORT).show();
                }
                arrayAdapter.notifyDataSetChanged();
                edEingabe.setText("");
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            itemPos = position;
            textView = findViewById(R.id.txtInput);
            content = textView.getText().toString();
            //selected = ((TextView) view.findViewById(R.id.txtInput)).getText().toString();
            Intent intent = new Intent(KontoActivity.this, ShowPassword.class);
            intent.putExtra("positionValue", position);
            startActivity(intent);
        });
    }

    public void writeXML() {
        ShowPassword showPassword = new ShowPassword();
        try {
            FileOutputStream fileos = getApplicationContext().openFileOutput(showPassword.filename, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "Passwords");
            //for (int i = 0; i <= arrayList.size() -1; i++) {
            //TODO: die schleife mit die arraylist ersetzen statt die String Array[];
                for (int y = 0; y <= ((items.length) - 1); y++){
                    xmlSerializer.startTag(null, "userData");
                    xmlSerializer.startTag(null, items[y]);
                    xmlSerializer.text("test");
                    xmlSerializer.endTag(null, items[y]);
                    xmlSerializer.endTag(null, "userData");
                }
           // }
            xmlSerializer.endTag(null, "Passwords");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}