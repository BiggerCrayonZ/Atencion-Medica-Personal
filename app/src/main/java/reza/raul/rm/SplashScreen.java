package reza.raul.rm;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import reza.raul.rm.ActividadDePacientes.MenuPacientes;

/**
 * Created by Android on 05/11/2017.
 */

public class SplashScreen extends Activity {
    private static final long SplashScreenDelay = 3000;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this,Login.class);
                startActivity(mainIntent);

                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SplashScreenDelay);
    }
}
