package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {

    private Bundle data;
    private int discount = 0;           // NEW: percentage, 0 = no promo
    private TextView tvPromo;           // NEW: "Promo applied: -10 %"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvEvent = findViewById(R.id.tvEvent);
        TextView tvTickets = findViewById(R.id.tvTickets);
        tvPromo = findViewById(R.id.tvPromo);
        final RadioGroup rgType = findViewById(R.id.rgType);
        Button btnPromo = findViewById(R.id.btnPromo);
        Button btnRegister = findViewById(R.id.btnRegister);

        data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }
        tvName.setText(data.getString(Keys.NAME, ""));
        tvEvent.setText(data.getString(Keys.EVENT, ""));
        tvTickets.setText(getString(R.string.tickets_fmt, data.getInt(Keys.TICKETS, 0)));

        // Open PromoActivity and wait for its answer
        btnPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PromoActivity.class);
                startActivityForResult(intent, Keys.REQ_PROMO);
            }
        });

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

                // apply the discount to the subtotal
                int subtotal = unitPrice * data.getInt(Keys.TICKETS, 1);
                int total = subtotal * (100 - discount) / 100;

                Bundle b = new Bundle(data);    // copy, then enrich
                b.putString(Keys.TYPE, type);
                b.putInt(Keys.TOTAL, total);
                b.putInt(Keys.DISCOUNT, discount);

                Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
                intent.putExtra(Keys.EXTRA_BUNDLE, b);
                startActivity(intent);
            }
        });
    }

    // NEW: called automatically when PromoActivity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (requestCode == Keys.REQ_PROMO && resultCode == RESULT_OK && result != null) {
            discount = result.getIntExtra(Keys.DISCOUNT, 0);
            tvPromo.setText(getString(R.string.promo_applied_fmt, discount));
            tvPromo.setVisibility(View.VISIBLE);
        }
        // RESULT_CANCELED (Back button): nothing changes
    }
}
