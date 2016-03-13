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

public class MainActivity extends AppCompatActivity {


    private SnakeLayout snakeLayout; // Snake Layout used to display a list of views
    private ButtonModelView buttonModelView; // ModelView bound to display UI elements
    private Button orientation_btn;

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
        loadData.start();
    }

    /**
     * Simple background thread that updates button data through the ButtonModelView. This will update
     * the displayed buttons size (increasing margin widths, heights and label). No UI element is updated
     * directly!
     */
    Thread loadData = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 17; x++) {
                    buttonModelView.setButtonValues(x, "Label Update " + (x + i), x % 2);
                }
                try {
                    Thread.sleep(1000 * 3);
                } catch (Exception ex) {
                    Log.d("10", "Error in Thread", ex);
                }
            }
        }
    });
}
