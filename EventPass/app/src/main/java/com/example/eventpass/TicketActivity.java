package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TicketActivity extends AppCompatActivity {

    private Bundle data;            
    private TextView tvName;        
    private TextView tvCode;       

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }

        tvCode = findViewById(R.id.tvCode);
        tvName = findViewById(R.id.tvName);

        tvCode.setText(getString(R.string.code_fmt, data.getInt(Keys.CODE, 0)));
        tvName.setText(data.getString(Keys.NAME, ""));
        ((TextView) findViewById(R.id.tvEvent)).setText(data.getString(Keys.EVENT, ""));
        ((TextView) findViewById(R.id.tvType)).setText(data.getString(Keys.TYPE, ""));
        ((TextView) findViewById(R.id.tvQuantity)).setText(String.valueOf(data.getInt(Keys.TICKETS, 0)));
        ((TextView) findViewById(R.id.tvTotal))
                .setText(getString(R.string.total_fmt, data.getInt(Keys.TOTAL, 0)));

        // NEW: open EditNameActivity and wait for its answer
        Button btnEditName = findViewById(R.id.btnEditName);
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, EditNameActivity.class);
                intent.putExtra(Keys.NAME, data.getString(Keys.NAME, ""));
                startActivityForResult(intent, Keys.REQ_EDIT_NAME);
            }
        });

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

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (requestCode == Keys.REQ_EDIT_NAME && resultCode == RESULT_OK && result != null) {
            String newName = result.getStringExtra(Keys.NAME);
            if (newName == null || newName.isEmpty()) return;

            int newCode = TicketUtils.makeCode(newName, data.getString(Keys.EVENT, ""));

            data.putString(Keys.NAME, newName);     // keep the Bundle up to date (used by Tips)
            data.putInt(Keys.CODE, newCode);

            tvName.setText(newName);
            tvCode.setText(getString(R.string.code_fmt, newCode));
            Toast.makeText(this, R.string.name_updated, Toast.LENGTH_SHORT).show();
        }
    }
}
