package com.blundell.quicksand.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blundell.quicksand.demo.activitytransition.FromHereActivity;
import com.blundell.quicksand.demo.simpleanimation.SimpleAnimationActivity;
import com.blundell.quicksand.demo.viewanimation.ViewAnimateActivity;

public class MainActivity extends Activity {

    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_main_simple_demo).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SimpleAnimationActivity.class);
                        startActivity(intent);
                    }
                });
        findViewById(R.id.button_main_view_property_demo).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ViewAnimateActivity.class);
                        startActivity(intent);
                    }
                });

        findViewById(R.id.button_main_transition_demo).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                            String message = getString(R.string.message_feature_unavailable_below_X, "Lollipop");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, FromHereActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
