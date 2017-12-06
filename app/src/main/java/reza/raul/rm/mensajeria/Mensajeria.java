package reza.raul.rm.mensajeria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import reza.raul.rm.ActividadDePacientes.MenuMedico;
import reza.raul.rm.ActividadDePacientes.citasPacientes.RegistroCitas.RegistroCita;
import reza.raul.rm.Preferences;
import reza.raul.rm.R;
import reza.raul.rm.VolleyRP;

/**
 * Created by Android on 02/11/2017.
 */

public class Mensajeria extends AppCompatActivity {
    private static final String IP_MENSAJE = "https://ampandroid.000webhostapp.com/archivosPHP/Enviar_Mensajes.php";
    public static final String MENSAJE = "MENSAJE";
    private String EMISOR = "";
    private String TIPO_EMISOR = "";
    private String MENSAJE_ENVIAR = "";
    private String RECEPTOR;
    private String NOMBRE_ENVIADO;
    private MsgAdapter adaptador;
    private BroadcastReceiver bR;
    private ImageButton btnEnviarMsg;
    private ImageButton btnRegistrarCita;
    private EditText inputTextMsg;
    private RequestQueue mRequest;
    private List<MsgTexto> mensajesTexto;
    private RecyclerView rv;
    private VolleyRP volley;
    private static final long SplashScreenDelay = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_mensaje);

        mensajesTexto = new ArrayList<>();

        EMISOR = Preferences.obtenerPreferenceString(this, Preferences.PREFERENCE_USUARIO_LOGIN);
        TIPO_EMISOR = Preferences.obtenerPreferenceTipo(this, Preferences.PREFERENCE_USUARIO_TIPO);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            RECEPTOR = bundle.getString("key_extra");//
            NOMBRE_ENVIADO = bundle.getString("key_nombre_enviado");
        }

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnEnviarMsg = (ImageButton) findViewById(R.id.btnEnviar);
        inputTextMsg = (EditText) findViewById(R.id.txtInputMsg);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        //Stack
        LinearLayoutManager lym = new LinearLayoutManager(this);
        lym.setStackFromEnd(true); //Mensajería especial
        rv.setLayoutManager(lym);
        //Adaptador
        adaptador = new MsgAdapter(mensajesTexto, this);
        rv.setAdapter(adaptador);

        btnEnviarMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = inputTextMsg.getText().toString().trim();
                if (!mensaje.isEmpty() && !RECEPTOR.isEmpty()) {
                    MENSAJE_ENVIAR = mensaje;
                    MandarMsg();
                    crearMsg(mensaje, "00:00", 1);
                    inputTextMsg.setText("");
                }

            }
        });

        //toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegistrarCita = (ImageButton) findViewById(R.id.btnRegistraPacienteCita);
        btnRegistrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TIPO_EMISOR.equals("1")) {
                    Snackbar.make(v, "No se pueden registrar siendo paciente ", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else if (TIPO_EMISOR.equals("0") || TIPO_EMISOR.equals("a")) {
                    Snackbar.make(v, "Registrar una cita para " + RECEPTOR, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Mensajeria.this, RegistroCita.class);
                            i.putExtra("key_usuario", RECEPTOR);
                            startActivity(i);


                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, SplashScreenDelay);
                }
            }
        });


        //setScrollbar
        setScrollBar();
        //Broadcast reciver
        bR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getStringExtra("key_mensaje");
                String hora = intent.getStringExtra("key_hora");
                String emisor = intent.getStringExtra("key_emisor_PHP");
                if (emisor.equals(RECEPTOR)) {
                    crearMsg(mensaje, hora, 0);
                }
            }
        };

        toolbar.setTitle(NOMBRE_ENVIADO);


    }

    private void MandarMsg() {
        HashMap<String, String> hashMapToken = new HashMap();
        hashMapToken.put("emisor", EMISOR);
        hashMapToken.put("receptor", RECEPTOR);
        hashMapToken.put("mensaje", MENSAJE_ENVIAR);
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_MENSAJE, new JSONObject(hashMapToken), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(Mensajeria.this, datos.getString("resultado"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void crearMsg(String mensaje, String hora, int tipoDato) {
        MsgTexto mensajeTextoAuxiliar = new MsgTexto();
        mensajeTextoAuxiliar.setId("0");
        mensajeTextoAuxiliar.setMensaje(mensaje);
        mensajeTextoAuxiliar.setTipoMensaje(tipoDato);
        mensajeTextoAuxiliar.setHoraMensaje(hora);
        mensajesTexto.add(mensajeTextoAuxiliar);
        adaptador.notifyDataSetChanged();
        setScrollBar();
    }

    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.bR);
    }

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.bR, new IntentFilter(MENSAJE));
    }


    public void setScrollBar() {
        rv.scrollToPosition(this.adaptador.getItemCount() - 1);
    }
}
