package reza.raul.rm.ActividadDePacientes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import reza.raul.rm.ActividadDePacientes.amigos.Amigos;
import reza.raul.rm.ActividadDePacientes.citasPacientes.citaPacientes.CitasPaciente;

/**
 * Created by Android on 05/11/2017.
 */

public class AdapterPacientes extends FragmentPagerAdapter {

    public AdapterPacientes(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new Amigos();
        } else if(position == 1){
            return new CitasPaciente();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Médicos";
        } else if(position == 1){
            return "Citas";
        }
        return null;
    }


}
