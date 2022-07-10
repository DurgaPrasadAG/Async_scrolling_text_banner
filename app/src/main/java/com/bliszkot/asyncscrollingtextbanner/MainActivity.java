package com.bliszkot.asyncscrollingtextbanner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    AsyncTask<TextView, Void, Void> thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.textView);

        ImageView startTask = findViewById(R.id.startTask);
        ImageView endTask = findViewById(R.id.endTask);

        thread = new BackgroundThread();

        startTask.setOnClickListener(v -> {
            try {
                thread.execute(text);
                Toast.makeText(this, "Task started.", Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException e) {
                this.recreate(); // Restart the activity
            }
        });

        endTask.setOnClickListener(v -> {
            thread.cancel(true);
            Toast.makeText(this, "Stopping the task.", Toast.LENGTH_LONG).show();
        });
    }

    class BackgroundThread extends AsyncTask<TextView, Void, Void> {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 1500.0f, 0.0f, 0.0f);

        @Override
        protected Void doInBackground(TextView... textViews) {
            while (true) {
                if (isCancelled()) {
                    break;
                } else {
                    animation.setDuration(3500);
                    animation.setFillAfter(false);
                    animation.setRepeatCount(1);
                    textViews[0].startAnimation(animation);

                    try {
                        Thread.sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
