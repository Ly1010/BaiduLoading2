package baiduloading2.ly.com.baiduloading2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ParabolicView view_main_circle;
    private Button btn_main_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_main_circle = (ParabolicView)findViewById(R.id.view_main_circle);
        btn_main_stop = (Button)findViewById(R.id.btn_main_stop);

        view_main_circle.startAnim();

        btn_main_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_main_circle.stopAnim();
            }
        });
    }
}
