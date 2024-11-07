package com.example.dz_10;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {
    private TextView statusTextView;
    private ProgressBar progressBar;
    private TaskCallback callback;

    public MyAsyncTask(TextView statusTextView, ProgressBar progressBar, TaskCallback callback) {
        this.statusTextView = statusTextView;
        this.progressBar = progressBar;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        statusTextView.setText("Status: Running");
        progressBar.setProgress(0);
    }

    @Override
    protected Void doInBackground(Integer... params) {
        int iterations = params[0];
        int delay = params[1];

        for (int i = 1; i <= iterations; i++) {
            if (isCancelled()) break;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i * 100 / iterations);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        progressBar.setProgress(progress);
        statusTextView.setText("Progress: " + progress + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        statusTextView.setText("Status: Finished");
        if (callback != null) {
            callback.onTaskCompleted();
        }
    }

    @Override
    protected void onCancelled() {
        statusTextView.setText("Status: Cancelled");
        if (callback != null) {
            callback.onTaskCancelled();
        }
    }

    public interface TaskCallback {
        void onTaskCompleted();
        void onTaskCancelled();
    }
}
