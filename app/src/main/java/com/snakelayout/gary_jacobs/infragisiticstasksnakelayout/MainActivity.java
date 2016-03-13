/**
 * Main Activity that displays a screen of buttons in a SnakeLayout. Using Android's new data binding
 * library, a MVVM model is implemented by using the ButtonModelView to bind label and button size data to UI Buttons.
 * This Activity lauches a background thread which updates label and sizing data and indirectly updates the displayed buttons.
 */
package com.snakelayout.gary_jacobs.infragisiticstasksnakelayout;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.databinding.ActivityMainBinding;
import com.snakelayout.gary_jacobs.infragisiticstasksnakelayout.model.ButtonModelView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private SnakeLayout snakeLayout; // Snake Layout used to display a list of views
    private ButtonModelView buttonModelView; // ModelView bound to display UI elements
    private Button orientation_btn;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        buttonModelView = new ButtonModelView();
        binding.setButtonVM(buttonModelView);
        snakeLayout = (SnakeLayout) findViewById(R.id.snake_layout);
        orientation_btn = (Button) findViewById(R.id.orientation_btn);
        orientation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakeLayout.toggleOrientaton();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//          Simple background thread that updates button data through the ButtonModelView. This will update
//          the displayed buttons size (increasing margin widths, heights and label). No UI element is updated
//          directly!

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < 3; i++) {
                        for (int x = 0; x < 17; x++) {
                            buttonModelView.setButtonValues(x, "UPDATE: " + (x + i), x % 2);
                        }
                        try {
                            Thread.sleep(1000 * 3);
                        } catch (InterruptedException ex) {
                            Log.d(null, ex.getMessage(), ex);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        executorService.shutdown();
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            Log.d(null, ie.getMessage(), ie);
        }
    }


}
