package com.example.eventpass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** NEW page 2: edits the attendee name and returns the new name to TicketActivity. */
public class EditNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        final EditText editNewName = findViewById(R.id.editNewName);
        Button btnSave = findViewById(R.id.btnSave);

        // pre-fill with the current name sent by TicketActivity
        editNewName.setText(getIntent().getStringExtra(Keys.NAME));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editNewName.getText().toString().trim();
                if (newName.isEmpty()) {
                    Toast.makeText(EditNameActivity.this, R.string.err_name,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent result = new Intent();
                result.putExtra(Keys.NAME, newName);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
