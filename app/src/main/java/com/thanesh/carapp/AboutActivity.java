package com.thanesh.carapp; // remove this before copy-paste

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button back_btn = findViewById(R.id.btn_back);

        // code for back button on about activity, selecting it prompts user to the home activity.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // back btn
    @Override
    public void onBackPressed() {
        Intent i = new Intent(AboutActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}


