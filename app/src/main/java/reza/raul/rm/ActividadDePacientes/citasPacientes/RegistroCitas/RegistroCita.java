package reza.raul.rm.ActividadDePacientes.citasPacientes.RegistroCitas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import reza.raul.rm.Preferences;
import reza.raul.rm.R;
import reza.raul.rm.Registro;
import reza.raul.rm.VolleyRP;

public class RegistroCita extends AppCompatActivity {

    private static final String IP_REGISTRAR = "https://ampandroid.000webhostapp.com/archivosPHP/Citas_INSERT.php";

    Button btnFecha, btnHora;
    Button registrar;

    EditText txtFecha, txtHora;
    EditText nombrePacinete;
    EditText direccion;

    private VolleyRP volley;
    private RequestQueue mRequest;

    private String RECEPTOR;

    private int dia,mes,ano,hora,minutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        //getSupportActionBar().hide();
        setTitle("Registro de Citas");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_registro_cita);



        //Instanciamos los request
        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        //Botones de Fecha y Hora
        btnFecha = (Button) findViewById(R.id.selectFecha);
        btnHora = (Button) findViewById(R.id.selectHora);
        txtFecha = (EditText) findViewById(R.id.efecha);
        txtHora = (EditText) findViewById(R.id.ehora);



        //Metodo instanciado en su propia clase
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                ano = calendar.get(Calendar.YEAR);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroCita.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            txtFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    },ano, mes, dia);
                    datePickerDialog.show();

            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minutos = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(RegistroCita.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtHora.setText(hourOfDay+":"+minute);
                    }
                },hora,minutos, false);
                timePickerDialog.show();
            }
        });

        nombrePacinete = (EditText) findViewById(R.id.registroCitaNombrePaciente);
        direccion = (EditText) findViewById(R.id.registroCitaDireccion);

        //Bundle
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            RECEPTOR = bundle.getString("key_usuario");//
            nombrePacinete.setText(RECEPTOR);
        }

        //Boton Registrar
        registrar = (Button) findViewById(R.id.selectRegistrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MEDICO = Preferences.obtenerPreferenceString(RegistroCita.this, Preferences.PREFERENCE_USUARIO_LOGIN);
                registrarCitaWebService(MEDICO,
                        getStringET(nombrePacinete).trim(),
                        getStringET(txtFecha).trim(),
                        getStringET(txtHora).trim(),
                        getStringET(direccion).trim());
            }
        });



    }

    private void registrarCitaWebService(String Medico, String Paciente, String fecha, String hora, String direccion){
        if (!Medico.isEmpty() && !Paciente.isEmpty() && !fecha.isEmpty() && !hora.isEmpty() && !direccion.isEmpty()){
            HashMap<String, String> hashMapToken = new HashMap<>();
            hashMapToken.put("idMedico", Medico);
            hashMapToken.put("idPaciente", Paciente);
            hashMapToken.put("fecha", fecha);
            hashMapToken.put("hora", hora);
            hashMapToken.put("direccion", direccion);

            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_REGISTRAR, new JSONObject(hashMapToken), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject datos) {
                    try {
                        String estado = datos.getString("resultado");
                        if (estado.equalsIgnoreCase("Cita Registrada Con Exito")) {
                            Toast.makeText(RegistroCita.this, estado, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistroCita.this, estado, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {}

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroCita.this, "No se pudo registrar ", Toast.LENGTH_SHORT).show();
                }
            });
            VolleyRP.addToQueue(solicitud, mRequest, this, volley);
        }else{
            Toast.makeText(this, "Es importante rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getStringET(EditText e){
        return e.getText().toString();
    }


}
