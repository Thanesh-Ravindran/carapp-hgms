package com.thanesh.carapp; // remove this before copy-paste

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ReadingActivity extends AppCompatActivity {

    TextView value_co, value_lpg, value_meth;
    ProgressBar bar_co, bar_lpg, bar_meth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        createNotificationChannel();

        Button back_btn = findViewById(R.id.btn_back);

        value_co = findViewById(R.id.co_readings);
        bar_co = findViewById(R.id.co_bar);
        value_lpg = findViewById(R.id.lpg_readings);
        value_meth = findViewById(R.id.meth_readings);
        bar_lpg = findViewById(R.id.lpg_bar);
        bar_meth = findViewById(R.id.meth_bar);

        // code for back button on reading activity, selecting it prompts user to the home activity.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Firebase reference
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("carapp");
        mref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // get value from Firebase as a string
                String co = Objects.requireNonNull(snapshot.child("co").getValue()).toString();
                String lpg = Objects.requireNonNull(snapshot.child("lpg").getValue()).toString();
                String meth = Objects.requireNonNull(snapshot.child("meth").getValue()).toString();
                // convert string to float for the bar
                float f_co = Float.parseFloat(co);
                float f_lpg = Float.parseFloat(lpg);
                float f_meth = Float.parseFloat(meth);
                // get the retrieved value to the textview & bar
                try {
                    value_co.setText(co);
                    value_lpg.setText(lpg);
                    value_meth.setText(meth);
                } catch (Exception e) {
                    Toast.makeText(ReadingActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                }
                // bar
                bar_co.setProgress((int) f_co, true);
                bar_lpg.setProgress((int) f_lpg, true);
                bar_meth.setProgress((int) f_meth, true);

                // statement for co noti
                if(f_co >= 50 && f_co < 200) first_noti();
                if (f_co >= 200 && f_co < 400) second_noti();
                if (f_co >= 400 && f_co < 800) third_noti();
                if (f_co >= 800) fourth_noti();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReadingActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ReadingActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    // notifications - only for CO readings
    private void first_noti() {
        // noti tap action
        Intent notiIntent = new Intent(this, ReadingActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // noti content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "carbon") // custom channel ID
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("CO over 50ppm!")
                .setContentText("Max allowable exposure for a given 8 hour period.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Max allowable exposure for a given 8 hour period."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // run noti
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(100, builder.build());
    }

    private void second_noti() {
        // noti tap action
        Intent notiIntent = new Intent(this, ReadingActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // noti content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "carbon")
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("CO over 200ppm!")
                .setContentText("Physical symptoms after 2–3 hours.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Physical symptoms after 2–3 hours."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // run noti
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(101, builder.build());
    }

    private void third_noti() {
        // noti tap action
        Intent notiIntent = new Intent(this, ReadingActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // noti content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "carbon")
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("CO exceeded over 400ppm!")
                .setContentText("Physical symptoms within 45 minutes. Unconsciousness in 2 hours.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Physical symptoms within 45 minutes. Unconsciousness in 2 hours."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // run noti
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(102, builder.build());
    }

    private void fourth_noti() {
        // noti tap action
        Intent notiIntent = new Intent(this, ReadingActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // noti content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "carbon")
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("CO exceeded over 800ppm!")
                .setContentText("Physical symptoms within 20 minutes. Fatal within 1 hour.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Physical symptoms within 20 minutes. Fatal within 1 hour."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // run noti
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(103, builder.build());
    }

    // check SDK version for noti
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "carbonChannel";
            String description = "Channel for carbon notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("carbon", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }
}
