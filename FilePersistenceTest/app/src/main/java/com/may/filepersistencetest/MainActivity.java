package com.may.filepersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.edit);

        String content=load();
        if (!TextUtils.isEmpty(content)){
            edit.setText(content);
            edit.setSelection(content.length());
            Toast.makeText(this,content,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String content = edit.getText().toString();
        save(content);
    }

    private String load() {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content =new StringBuilder();
        try{
            in =openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line ="";
            while((line =reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e ){
            e.printStackTrace();
        }finally {
            try{
                reader.close();
            }catch (IOException e){
                e.printStackTrace();

            }
        }
        return content.toString();
    }

    private void save(String content) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}