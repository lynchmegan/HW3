package com.example.hw3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private final String file = "list.txt";
    private OutputStreamWriter out;

    private EditText entryField;
    private ListView listView;
    private ArrayList<String> items;
    private ArrayAdapter<String> arrayAdapter;
    private listviewjava adapter;
    private int index;
    private String line;
    private TextToSpeech speaker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            out = new OutputStreamWriter(openFileOutput(file, MODE_PRIVATE));}
        catch (IOException e) {}



        entryField = (EditText) findViewById(R.id.entryField);
        listView = (ListView) findViewById(R.id.listView);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4A4159"));
        actionBar.setBackgroundDrawable(colorDrawable);
        speaker = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = speaker.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    } else {
                        speak("Text to speech is enabled.");
                    }
                } else {
                }
            }
        });
        items = new ArrayList<>();
//        items.add("study");
//        items.add("sleep");
//        items.add("shop");

        adapter = new listviewjava(this,items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                index = position;
                entryField.setText(items.get(position));


            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void speak(String output){
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                String fieldValue = entryField.getText().toString();

                items.add(fieldValue);
                adapter.notifyDataSetChanged();
                entryField.getText().clear();

                speak(fieldValue + "has been added.");
                return true;

            case R.id.delete:
                fieldValue = items.get(index);

                items.remove(index);
                adapter.notifyDataSetChanged();
                entryField.getText().clear();

                speak(fieldValue + "has been deleted.");

                return true;

            case R.id.update:

                    items.set(index, entryField.getText().toString());
                    adapter.notifyDataSetChanged();
                    entryField.getText().clear();
                return true;
            case R.id.save:
                try {
                   out.write(String.valueOf(items));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.close:
                try {
                    out.write(String.valueOf(items));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

