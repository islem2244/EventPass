package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Activity 2: shows the attendee card, lets the user pick a ticket type, adds type + total to the Bundle. */
public class ProfileActivity extends AppCompatActivity {

    private Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvEvent = findViewById(R.id.tvEvent);
        TextView tvTickets = findViewById(R.id.tvTickets);
        final RadioGroup rgType = findViewById(R.id.rgType);
        Button btnRegister = findViewById(R.id.btnRegister);

        data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }
        tvName.setText(data.getString(Keys.NAME, ""));
        tvEvent.setText(data.getString(Keys.EVENT, ""));
        tvTickets.setText(getString(R.string.tickets_fmt, data.getInt(Keys.TICKETS, 0)));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type;
                int unitPrice;
                int id = rgType.getCheckedRadioButtonId();
                if (id == R.id.rbVip) {
                    type = getString(R.string.name_vip);
                    unitPrice = 50;
                } else if (id == R.id.rbStudent) {
                    type = getString(R.string.name_student);
                    unitPrice = 10;
                } else {
                    type = getString(R.string.name_standard);
                    unitPrice = 20;
                }

                Bundle b = new Bundle(data);    // copy, then enrich
                b.putString(Keys.TYPE, type);
                b.putInt(Keys.TOTAL, unitPrice * data.getInt(Keys.TICKETS, 1));

                Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
                intent.putExtra(Keys.EXTRA_BUNDLE, b);
                startActivity(intent);
            }
        });
    }
}
