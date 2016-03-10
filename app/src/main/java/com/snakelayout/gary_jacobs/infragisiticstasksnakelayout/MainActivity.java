package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.databinding.ActivityMainBinding;
import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.model.ButtonModel;

public class MainActivity extends AppCompatActivity {

    SnakeLayout snakeLayout;
    ButtonModel buttonModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        buttonModel = new ButtonModel();
        buttonModel.setButtonLabel("Button Label Set!");
        binding.setButtonVM(buttonModel);
        snakeLayout = (SnakeLayout) findViewById(R.id.snake_layout);

        //load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonModel.setButtonLabel("On Resume!!!");
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
