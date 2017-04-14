package com.example.samsung.p0891_acynctaskcancel;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    private TextView tvInfo;

    private class MyTask extends AsyncTask<Void, Void, Void> {

        private String message = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            message = getString(R.string.begin);
            Log.d(LOG_TAG, message);
            tvInfo.setText(message);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    //Если дана команда на выход
                    if (isCancelled()) return null;
                    message = "isCanceled: " + isCancelled();
                    Log.d(LOG_TAG, message);
                }
            } catch (InterruptedException e) {
                message = "Interrupted";
                Log.d(LOG_TAG, message);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            message = getString(R.string.end);
            Log.d(LOG_TAG, message);
            tvInfo.setText(message);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            message = getString(R.string.cancel);
            Log.d(LOG_TAG, message);
            tvInfo.setText(message);
        }
    }

    private MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
    }

    public void onClickBtn(View view) {

        switch (view.getId()) {

            case R.id.btnStart :
                myTask = new MyTask();
                myTask.execute();
                break;
            case R.id.btnCancel :
                cancelTask();
                break;
            default:
                break;
        }
    }

    private void cancelTask() {

        if (myTask == null) return;

//        String message = "Cancel result: " + myTask.cancel(false);
        String message = "Cancel result: " + myTask.cancel(true);
        Log.d(LOG_TAG, message);
    }
}
