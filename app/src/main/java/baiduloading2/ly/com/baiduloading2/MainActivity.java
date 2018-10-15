package baiduloading2.ly.com.baiduloading2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ParabolicView view_main_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_main_circle = (ParabolicView)findViewById(R.id.view_main_circle);

        view_main_circle.startAnim();
    }
}
