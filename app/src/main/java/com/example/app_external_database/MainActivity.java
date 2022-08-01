package com.example.app_external_database;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.AbstractList;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_save , btn_restore ;
    EditText et_color , et_model ;
    My_database db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_color= findViewById(R.id.et_color);
        et_model= findViewById(R.id.et_model);
        btn_restore= findViewById(R.id.btn_restore);
        btn_save= findViewById(R.id.btn_save);

        db = new My_database(this);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String model = et_model.getText().toString();
                String color = et_color.getText().toString();

                Car c = new Car(model,color);
                boolean res =   db.insertCar(c);
                if(res)
                    Toast.makeText(MainActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "no add", Toast.LENGTH_SHORT).show();

            }
        });

        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ArrayList<Car> cars=db.searchCars("2022");
                for (Car c : cars )
                      {
                          Log.d("car # "+c.getId(), c.getModel());
                      }
            }
        });
    }

}