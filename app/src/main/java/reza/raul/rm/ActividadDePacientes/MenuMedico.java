package reza.raul.rm.ActividadDePacientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.Timer;
import java.util.TimerTask;

import reza.raul.rm.ActividadDePacientes.citasPacientes.RegistroCitas.RegistroCita;
import reza.raul.rm.Login;
import reza.raul.rm.Preferences;
import reza.raul.rm.R;

/**
 * Created by Android on 11/11/2017.
 */

public class MenuMedico extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    boolean click = false;

    private static final long SplashScreenDelay = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_medico);
        setTitle("Menu Principal");
        //Instanciamos
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutMedico);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMedico);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new AdapterMedico(getSupportFragmentManager()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonMedico);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = !click;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                            android.R.interpolator.fast_out_slow_in);

                    v.animate()
                            .rotation(click ? 45f : 0)
                            .setInterpolator(interpolador)
                            .start();
                }
                Snackbar.make(v, "Registrar una cita", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MenuMedico.this, RegistroCita.class);
                        startActivity(i);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, SplashScreenDelay);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_amigos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.CerrarSesionMenu) {
            Preferences.savePreferenceBoolean(MenuMedico.this, false, Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
            Intent i = new Intent(MenuMedico.this, Login.class);
            startActivity(i);
            finish();
        } else if (id == R.id.Refrescar) {
            viewPager.getAdapter().notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }


}
