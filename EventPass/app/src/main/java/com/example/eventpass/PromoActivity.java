package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class PromoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        final EditText editPromo = findViewById(R.id.editPromo);
        Button btnApply = findViewById(R.id.btnApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int discount = TicketUtils.discountFor(editPromo.getText().toString());
                if (discount == 0) {
                    Toast.makeText(PromoActivity.this, R.string.err_promo,
                            Toast.LENGTH_SHORT).show();
                    return;                       
                }

                Intent result = new Intent();      
                result.putExtra(Keys.DISCOUNT, discount);
                setResult(RESULT_OK, result);
                finish();                    
            }
        });
    }
}
