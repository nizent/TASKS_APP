package com.example.zage.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.zage.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Controller {

    public static void setAlarm(Context context, String date, String time){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        //String dateTimeString = date + ' ' + time;
        //DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        //LocalDateTime alarmDateTime = LocalDateTime.parse(dateTimeString, datePattern);

        cal.set(Calendar.YEAR, Integer.parseInt(date.split("/")[2]));
        cal.set(Calendar.MONTH, Integer.parseInt(date.split("/")[1])-1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("/")[0]));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));  // set hour
        cal.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));          // set minute
        cal.set(Calendar.SECOND, 0);               // set seconds
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }
}
