package org.samuelcordova.texttospeech;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    String [] languages = new String[]{"English, English UK, French, Chinese"};

    TextToSpeech ttsobject;

    Button bspeak,
           bstop;

    EditText et;

    String text;

    int result;

    Spinner options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B3E71")));

        bspeak = (Button) findViewById(R.id.bspeak);
        bstop = (Button) findViewById(R.id.bstop);
        et = (EditText) findViewById(R.id.editText);

        options = (Spinner) findViewById(R.id.spinner);

       // ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, languages);
        //stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //options.setAdapter(stringAdapter);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);



        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS){


                    options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            //result = ttsobject.setLanguage(Locale.UK);

                            if(position == 0)
                                result = ttsobject.setLanguage(Locale.US);

                            else if(position == 1)
                                result = ttsobject.setLanguage(Locale.UK);

                            else if(position == 2)
                                result = ttsobject.setLanguage(Locale.FRENCH);
                            else if(position == 3)
                                result = ttsobject.setLanguage(Locale.GERMAN);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                            Toast.makeText(getBaseContext(), "You didnt select anything lol!", Toast.LENGTH_LONG).show();

                        }
                    });

                }
                else{

                    Toast.makeText(getApplicationContext(), "Feature not available in your Device", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void doSomething(View v){

       switch (v.getId()){

           case R.id.bspeak:

               if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){

                   Toast.makeText(getApplicationContext(), "Feature not available in your Device", Toast.LENGTH_SHORT).show();


               }
               else{

                   text = et.getText().toString();

                   ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);

               }


               break;

           case R.id.bstop:

               if(ttsobject != null) ttsobject.stop();

               break;


       }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(ttsobject != null) {

            ttsobject.stop();
            ttsobject.shutdown();
        }


    }
}
