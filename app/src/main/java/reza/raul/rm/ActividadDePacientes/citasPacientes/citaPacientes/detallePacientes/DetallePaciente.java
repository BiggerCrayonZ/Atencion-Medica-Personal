package reza.raul.rm.ActividadDePacientes.citasPacientes.citaPacientes.detallePacientes;

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

public class DetallePaciente extends AppCompatActivity {

    private String NOMBRE_MEDICO;
    private String FECHA;
    private String HORA;
    private String DIRECCION;

    private TextView txtNombreMedico;
    private TextView txtFecha;
    private TextView txtHora;
    private TextView txtDireccion;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_cita_detalle_paciente);

        //Bundle
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            NOMBRE_MEDICO = bundle.getString("key_detalle_mendico");
            FECHA = bundle.getString("key_detalle_fecha");
            HORA = bundle.getString("key_detalle_hora");
            DIRECCION = bundle.getString("key_detalle_direccion");
        }

        txtNombreMedico = (TextView) findViewById(R.id.detalleNombre);
        txtFecha = (TextView) findViewById(R.id.detalleFecha);
        txtHora = (TextView) findViewById(R.id.detalleHora);
        txtDireccion = (TextView) findViewById(R.id.detalleDireccion);

        //Seteamos el texto
        txtNombreMedico.setText(NOMBRE_MEDICO);
        txtFecha.setText(FECHA);
        txtHora.setText(HORA);
        txtDireccion.setText(DIRECCION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetallePaciente);

        //toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
