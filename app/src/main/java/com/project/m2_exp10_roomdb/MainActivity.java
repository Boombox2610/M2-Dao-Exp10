package com.project.m2_exp10_roomdb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // UserDatabase -> ExecutorService -> 1 editText -> 3 Button (date, Submit, delete) -> 1 string (date)
    UserDatabase database;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    //EditText inputText;
    Button submit;
    Button delete;
    Button select_date;
    String date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = UserDatabase.getInstance(this);
        EditText inputText = findViewById(R.id.inputText);
        Button submit = findViewById(R.id.submit_butt);
        Button delete = findViewById(R.id.delete_butt);
        Button select_date = findViewById(R.id.select_date);

        select_date.setOnClickListener(view ->{
            DatePickerDialog dialog = new DatePickerDialog(this);
            dialog.setOnDateSetListener((datepicker, year, month, day) -> date = day +"/" + (month+1) + "/" + year);
            dialog.show();
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userObject = new UserData();
                userObject.name = inputText.getText().toString();
                userObject.dateOfBirth = date;
                executorService.execute(() -> {
                    database.userDao().insert(userObject);
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(() -> {
                    database.userDao().delete(Integer.parseInt(inputText.getText().toString()));
                });
            }
        });

        database.userDao().getAllUsers().observe(this, new Observer<List<UserData>>() {
            @Override
            public void onChanged(List<UserData> userData) {
                StringBuilder result = new StringBuilder();
                for(UserData userData1 : userData){
                    result.append(userData1.srNo + "-"+userData1.name+"-"+userData1.dateOfBirth+"\n" );
                }
                TextView output = findViewById(R.id.result);
                output.setText(result.toString());
            }
        });

    }
}