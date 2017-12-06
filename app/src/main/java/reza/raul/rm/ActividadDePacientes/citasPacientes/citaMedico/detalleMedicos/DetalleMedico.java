package reza.raul.rm.ActividadDePacientes.citasPacientes.citaMedico.detalleMedicos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import reza.raul.rm.R;

/**
 * Created by Android on 12/11/2017.
 */

public class DetalleMedico extends AppCompatActivity {

    private String NOMBRE_PACIENTE;
    private String FECHA_PACIENTE;
    private String HORA_PACIENTE;
    private String DIRECCION_PACIENTE;

    private TextView txtNombrePsicnete;
    private TextView txtFechaPaciente;
    private TextView txtHoraPaciente;
    private TextView txtDireccionPaciente;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_cita_detalle_medico);

        //Bundle
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            NOMBRE_PACIENTE = bundle.getString("key_detalle_paciente");
            FECHA_PACIENTE = bundle.getString("key_detalle_fecha");
            HORA_PACIENTE = bundle.getString("key_detalle_hora");
            DIRECCION_PACIENTE = bundle.getString("key_detalle_direccion");
        }

        //Instanciamos
        txtNombrePsicnete = (TextView) findViewById(R.id.detalleNombreMedico);
        txtFechaPaciente = (TextView) findViewById(R.id.detalleFechaMedico);
        txtHoraPaciente = (TextView) findViewById(R.id.detalleHoraMedico);
        txtDireccionPaciente = (TextView) findViewById(R.id.detalleDireccionMedico);

        txtNombrePsicnete.setText(NOMBRE_PACIENTE);
        txtFechaPaciente.setText(FECHA_PACIENTE);
        txtHoraPaciente.setText(HORA_PACIENTE);
        txtDireccionPaciente.setText(DIRECCION_PACIENTE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalleMedico);

        //toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
