package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Activity 3: simulated registration/payment. ProgressBar driven by a Handler, lifecycle-aware. */
public class RegistrationActivity extends AppCompatActivity {

    private static final int INTERVAL_MS = 50;

    private ProgressBar progressBar;
    private TextView tvPercent;
    private Button btnTicket;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int progress = 0;
    private boolean running = false;
    private Bundle data;

    private final Runnable update = new Runnable() {
        @Override
        public void run() {
            if (progress < 100) progress++;
            progressBar.setProgress(progress);
            tvPercent.setText(getString(R.string.percent_fmt, progress));

            if (progress >= 100) {
                running = false;
                btnTicket.setEnabled(true);     // registration finished
            } else if (running) {
                handler.postDelayed(this, INTERVAL_MS);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = findViewById(R.id.progressBar);
        tvPercent = findViewById(R.id.tvPercent);
        btnTicket = findViewById(R.id.btnTicket);
        TextView tvProcessing = findViewById(R.id.tvProcessing);

        data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }
        tvProcessing.setText(getString(R.string.processing_fmt, data.getString(Keys.EVENT, "")));

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Unique-looking ticket code derived from name + event
                String seed = data.getString(Keys.NAME, "") + data.getString(Keys.EVENT, "");
                int code = Math.abs(seed.hashCode()) % 100000;

                Bundle b = new Bundle(data);
                b.putInt(Keys.CODE, code);

                Intent intent = new Intent(RegistrationActivity.this, TicketActivity.class);
                intent.putExtra(Keys.EXTRA_BUNDLE, b);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!running && data != null && progress < 100) {
            running = true;
            handler.postDelayed(update, INTERVAL_MS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        handler.removeCallbacks(update);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
