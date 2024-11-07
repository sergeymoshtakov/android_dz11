package com.example.dz_10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MyAsyncTask.TaskCallback {
    private TextView statusTextView;
    private ProgressBar progressBar;
    private Button startButton, cancelButton;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        progressBar = findViewById(R.id.progressBar);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAsyncTask == null || myAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                    myAsyncTask = new MyAsyncTask(statusTextView, progressBar, MainActivity.this);
                    myAsyncTask.execute(100, 300);  // 100 ітерацій, 300 мс затримка
                    startButton.setText("Stop");
                    cancelButton.setVisibility(View.VISIBLE);
                    statusTextView.setText("Status: Running");
                } else {
                    myAsyncTask.cancel(true);
                    statusTextView.setText("Status: Stopped");
                    resetButtons();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAsyncTask != null) {
                    myAsyncTask.cancel(true);
                    statusTextView.setText("Status: Cancelled");
                    resetButtons();
                }
            }
        });
    }

    private void resetButtons() {
        startButton.setText("Start");
        cancelButton.setVisibility(View.GONE);
    }

    @Override
    public void onTaskCompleted() {
        resetButtons();
    }

    @Override
    public void onTaskCancelled() {
        resetButtons();
    }
}
