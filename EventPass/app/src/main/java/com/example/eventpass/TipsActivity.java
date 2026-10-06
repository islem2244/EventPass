package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/** Activity 5 (new): tips depending on ticket type. RelativeLayout root with a nested LinearLayout header. */
public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        Bundle data = getIntent().getBundleExtra(Keys.EXTRA_BUNDLE);
        if (data == null) {
            finish();
            return;
        }
        String name = data.getString(Keys.NAME, "");
        String type = data.getString(Keys.TYPE, "");
        String code = getString(R.string.code_fmt, data.getInt(Keys.CODE, 0));

        int resource;
        if (type.equals(getString(R.string.name_vip))) resource = R.string.tips_vip;
        else if (type.equals(getString(R.string.name_student))) resource = R.string.tips_student;
        else resource = R.string.tips_standard;

        ((TextView) findViewById(R.id.tvTips)).setText(getString(resource, name, code));

        Button btnNew = findViewById(R.id.btnNewBooking);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
