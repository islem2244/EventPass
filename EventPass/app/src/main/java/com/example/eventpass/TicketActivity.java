package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Activity 4 (new): confirmation ticket. LinearLayout root with nested RelativeLayouts. */
public class TicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        final Bundle data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }

        ((TextView) findViewById(R.id.tvCode))
                .setText(getString(R.string.code_fmt, data.getInt(Keys.CODE, 0)));
        ((TextView) findViewById(R.id.tvName)).setText(data.getString(Keys.NAME, ""));
        ((TextView) findViewById(R.id.tvEvent)).setText(data.getString(Keys.EVENT, ""));
        ((TextView) findViewById(R.id.tvType)).setText(data.getString(Keys.TYPE, ""));
        ((TextView) findViewById(R.id.tvQuantity)).setText(String.valueOf(data.getInt(Keys.TICKETS, 0)));
        ((TextView) findViewById(R.id.tvTotal))
                .setText(getString(R.string.total_fmt, data.getInt(Keys.TOTAL, 0)));

        Button btnTips = findViewById(R.id.btnTips);
        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(Keys.NAME, data.getString(Keys.NAME, ""));
                b.putString(Keys.TYPE, data.getString(Keys.TYPE, ""));
                b.putInt(Keys.CODE, data.getInt(Keys.CODE, 0));

                Intent intent = new Intent(TicketActivity.this, TipsActivity.class);
                intent.putExtra(Keys.EXTRA_BUNDLE, b);
                startActivity(intent);
            }
        });
    }
}
