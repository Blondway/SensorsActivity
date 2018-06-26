package com.md.sensorsactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class OptionsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Delete all rows from table on click
        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getDB().deleteAllRows();
                Log.d("", "All data deleted.");
            }
        });

    }

}
