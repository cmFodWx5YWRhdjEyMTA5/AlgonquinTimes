package com.algonquintimes.algonquintimes.Email;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.algonquintimes.algonquintimes.R;

public class StoryIdeaActivity extends AppCompatActivity {

    private EditText editTextSubject;
    private EditText editTextMessage;
    private EditText editTextAdditional;
    private String finalMessage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_idea);

        editTextSubject = findViewById(R.id.edit_tip_title);
        editTextMessage = findViewById(R.id.editDescTip);
        editTextAdditional = findViewById(R.id.editInfoTip);
        buttonSend = findViewById(R.id.submitTip);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Send a Story Idea");

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editTextSubject.getText().toString().isEmpty() && !editTextMessage.getText().toString().isEmpty()) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                finalMessage = "<div style=\"font-weight:bold;\">Message</div><br>" + editTextMessage.getText().toString() + "<br><br><br> <div style=\"font-weight:bold;\">Additional info</div><br>" + editTextAdditional.getText().toString();
                                SendMail sender = new SendMail();
                                sender.sendMail(editTextSubject.getText().toString(), finalMessage);
                            } catch (Exception e) {
                                Log.i("Error: ", e.toString());
                            }
                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "Thank you for your story idea!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
                if (editTextSubject.getText().toString().isEmpty()) {
                    editTextSubject.setError("Please enter a Title.");
                    editTextSubject.requestFocus();
                }
                if (editTextMessage.getText().toString().isEmpty()) {
                    editTextMessage.setError("What is this about?");
                    editTextMessage.requestFocus();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}


