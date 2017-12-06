package reza.raul.rm.ActividadDePacientes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import reza.raul.rm.Login;
import reza.raul.rm.Preferences;
import reza.raul.rm.R;

/**
 * Created by Android on 05/11/2017.
 */

public class MenuPacientes extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_usuario_paciente);
        setTitle("Menú Principal");
        //Instanciamos
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutPacientes);
        viewPager = (ViewPager) findViewById(R.id.viewPagerPacientes);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new AdapterPacientes(getSupportFragmentManager()));


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
            Preferences.savePreferenceBoolean(MenuPacientes.this, false, Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
            Intent i = new Intent(MenuPacientes.this, Login.class);
            startActivity(i);
            finish();
        } else if( id == R.id.Refrescar){
            viewPager.getAdapter().notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}

