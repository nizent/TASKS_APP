package com.example.zage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zage.controller.Controller;
import com.example.zage.database.DatabaseAdapter;
import com.example.zage.view.DatePickerFragment;
import com.example.zage.view.TimePickerFragment;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTaskActivity extends AppCompatActivity {
    EditText title;
    EditText description;
    EditText date;
    EditText time;
    EditText importance;
    Button submit;
    DatabaseAdapter dbAdapter;

    public void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(
                new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            // +1 because January is zero
                            final String selectedDate = day + "/" + (month+1) + "/" + year;
                            date.setText(selectedDate);
                        }}
        );
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(){
        TimePickerFragment newFragment = TimePickerFragment.newInstance(
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        final String selectedTime = hour + ":" + (minute);
                        time.setText(selectedTime);
                    }}
        );
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.add_task_layout);
        title = (EditText) findViewById(R.id.task_title);
        description = (EditText) findViewById(R.id.task_description);
        date = (EditText) findViewById(R.id.task_date);

        // Set the actual date by default
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(LocalDateTime.now().format(dateFormat));

        date.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        time = (EditText) findViewById(R.id.task_time);
        time.setOnClickListener(view -> {
            showTimePickerDialog();
        });

        importance = (EditText) findViewById(R.id.task_importance);
        dbAdapter = new DatabaseAdapter(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.menu_layout);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ImageButton submit = (ImageButton) findViewById(R.id.submit_task_button);
        submit.setVisibility(ImageButton.VISIBLE);

        submit.setOnClickListener(view -> {
            AtomicInteger importance_number = new AtomicInteger();
            try{
                importance_number.set(Integer.parseInt(importance.getText().toString()));
            } catch (NumberFormatException e){
                importance_number.set(0);
            }

            Controller.setAlarm(this, date.getText().toString(), time.getText().toString());

            dbAdapter.insertData(title.getText().toString(),
                    description.getText().toString(),
                    date.getText().toString(),
                    0,
                    importance_number.get()
            );
            backToMain();
        });
    }

    public void backToMain(){
        Intent backToMain = new Intent(this, MainActivity.class);
        startActivity(backToMain);
    }
}