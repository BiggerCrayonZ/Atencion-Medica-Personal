package reza.raul.rm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import reza.raul.rm.ActividadDePacientes.MenuMedico;
import reza.raul.rm.ActividadDePacientes.MenuPacientes;

public class Login extends AppCompatActivity {

    private static final String IP = "https://ampandroid.000webhostapp.com/archivosPHP/login_GETID.php?id=";
    private static final String IP_TOKEN = "https://ampandroid.000webhostapp.com/archivosPHP/Token_INSERTandUPDATE.php";
    private CheckBox CBR;
    private String PASS = "";
    private String USER = "";
    private String TYPE = "";
    private Button btnLogin;
    private Button btnRegistro;
    private RequestQueue mRequest;
    private EditText txtLogin;
    private EditText txtPass;
    private VolleyRP volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        if (Preferences.obtenerPreferenceBoolean(this, Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
            if (Preferences.obtenerPreferenceTipo(this, Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("1")){
                iniciarActividad();
            }else if (Preferences.obtenerPreferenceTipo(this, Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("0") || Preferences.obtenerPreferenceTipo(this, Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("a")){
                iniciarActividadMedico();
            }
        }

        volley = VolleyRP.getInstance(this);
        mRequest = this.volley.getRequestQueue();
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnRegistro = (Button) findViewById(R.id.btnReg);
        CBR = (CheckBox) findViewById(R.id.cCRecordar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Procesando...", Toast.LENGTH_SHORT).show();
                verificarLogin(txtLogin.getText().toString(), txtPass.getText().toString());
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });


    }

    public void verificarLogin(String user, String password) {
        USER = user;
        PASS = password;
        SolicitudJSON(IP + user);
    }

    public void SolicitudJSON(String URL) {
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                VerificarPass(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Ocurrio un error, por favor contactese con el administrador", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, this, volley);
    }

    public void VerificarPass(JSONObject datos) {
        try {
            String estado = datos.getString("resultado");
            if (estado.equals("CC")) {
                JSONObject Jsondatos = new JSONObject(datos.getString("datos"));
                String usuario = Jsondatos.getString("id");
                String contraseña = Jsondatos.getString("password");
                String tipo = Jsondatos.getString("tipo");
                TYPE = tipo.toString().trim();
                if (usuario.equals(USER) && contraseña.equals(PASS) && !tipo.isEmpty()) {
                    if (tipo.equals("1")) { //en caso de ser paciente
                        Preferences.savePreferenceString(Login.this, Jsondatos.getString("id"), Preferences.PREFERENCE_USUARIO_LOGIN);
                        Preferences.savePreferencesTipo(Login.this, Jsondatos.getString("tipo"), Preferences.PREFERENCE_USUARIO_TIPO);
                        //iniciarActividad();
                        String Token = FirebaseInstanceId.getInstance().getToken();
                        if (Token != null) {
                            if (("" + Token.charAt(0)).equalsIgnoreCase("{")) {
                                JSONObject js = new JSONObject(Token);//SOLO SI LES APARECE {"token":"...."} o "asdasdas"
                                String tokenRecortado = js.getString("token");
                                subirToken(tokenRecortado);
                            } else {
                                subirToken(Token);
                            }
                        } else Toast.makeText(this, "El token es nulo", Toast.LENGTH_SHORT).show();
                    } else if (tipo.equals("0") || tipo.equals("a")) { //EN CASO DE QUE SEA MEDICO O ADMINISTRADOR
                        Preferences.savePreferenceString(Login.this, Jsondatos.getString("id"), Preferences.PREFERENCE_USUARIO_LOGIN);
                        Preferences.savePreferencesTipo(Login.this, Jsondatos.getString("tipo"), Preferences.PREFERENCE_USUARIO_TIPO);
                        //iniciarActividadMedico();
                        String Token = FirebaseInstanceId.getInstance().getToken();
                        if (Token != null) {
                            if (("" + Token.charAt(0)).equalsIgnoreCase("{")) {
                                JSONObject js = new JSONObject(Token);//SOLO SI LES APARECE {"token":"...."} o "asdasdas"
                                String tokenRecortado = js.getString("token");
                                subirToken(tokenRecortado);
                            } else {
                                subirToken(Token);
                            }
                        } else Toast.makeText(this, "El token es nulo", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "No se  asigno el tipo de usuario", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "El token no se pudo recortar", Toast.LENGTH_SHORT).show();
        }
    }

    private void subirToken(String token) {
        HashMap<String, String> hashMapToken = new HashMap();
        hashMapToken.put("id", USER);
        hashMapToken.put("token", token);
        VolleyRP.addToQueue(new JsonObjectRequest(1, IP_TOKEN, new JSONObject(hashMapToken), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject datos) {
                if (TYPE.equalsIgnoreCase("1")) {
                    Preferences.savePreferenceBoolean(Login.this, CBR.isChecked(), Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                    Preferences.savePreferenceString(Login.this, USER, Preferences.PREFERENCE_USUARIO_LOGIN);
                    Preferences.savePreferencesTipo(Login.this, TYPE, Preferences.PREFERENCE_USUARIO_TIPO);
                    Toast.makeText(Login.this, "Bienvenido " + USER, 1).show();
                    iniciarActividad();
                } else if (TYPE.equalsIgnoreCase("0") || TYPE.equalsIgnoreCase("a")) {
                    Preferences.savePreferenceBoolean(Login.this, CBR.isChecked(), Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                    Preferences.savePreferenceString(Login.this, USER, Preferences.PREFERENCE_USUARIO_LOGIN);
                    Preferences.savePreferencesTipo(Login.this, TYPE, Preferences.PREFERENCE_USUARIO_TIPO);
                    Toast.makeText(Login.this, "Bienvenido " + USER, 1).show();
                    iniciarActividadMedico();
                } else Toast.makeText(Login.this, "El tipo de usuario no esta asignado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Ocurrio error con el Token.", 1).show();
            }
        }), mRequest, this, volley);
    }


    public void iniciarActividad() {
        Intent i = new Intent(Login.this, MenuPacientes.class);
        startActivity(i);
        finish();
    }

    public void iniciarActividadMedico() {
        Intent i = new Intent(Login.this, MenuMedico.class);
        startActivity(i);
        finish();
    }
}
