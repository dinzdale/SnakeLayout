package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SnakeLayout snakeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        snakeLayout = (SnakeLayout) findViewById(R.id.snake_layout);
        load();
    }


    protected void load() {
        super.onResume();
        for (int i = 0; i < 100; i++) {
            TextView textView = new TextView(this);
            textView.setText("TextView " + i);
            snakeLayout.addView(textView);
        }
    }
}
