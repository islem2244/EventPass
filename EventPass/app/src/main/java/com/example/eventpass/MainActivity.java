package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Activity 1: collects name, event and ticket count, sends them in a Bundle to ProfileActivity. */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editName = findViewById(R.id.editName);
        final EditText editEvent = findViewById(R.id.editEvent);
        final EditText editTickets = findViewById(R.id.editTickets);
        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String event = editEvent.getText().toString().trim();
                String ticketsTxt = editTickets.getText().toString().trim();

                if (name.isEmpty() || event.isEmpty() || ticketsTxt.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.err_fields,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                int tickets = Integer.parseInt(ticketsTxt);
                if (tickets < 1 || tickets > 10) {
                    Toast.makeText(MainActivity.this, R.string.err_tickets,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle b = new Bundle();
                b.putString(Keys.NAME, name);
                b.putString(Keys.EVENT, event);
                b.putInt(Keys.TICKETS, tickets);

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(Keys.EXTRA_BUNDLE, b);
                startActivity(intent);
            }
        });
    }
}
