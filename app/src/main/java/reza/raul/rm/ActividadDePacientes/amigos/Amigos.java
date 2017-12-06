package reza.raul.rm.ActividadDePacientes.amigos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import reza.raul.rm.Login;
import reza.raul.rm.Preferences;
import reza.raul.rm.R;
import reza.raul.rm.VolleyRP;

/**
 * Created by Android on 03/11/2017.
 */

public class Amigos extends Fragment {

    private List<AmigosAtributos> atributosList;
    private AmigoAdapter amigoAdapter;

    private RecyclerView rv;
    private VolleyRP volley;
    private RequestQueue mRequest;

    private static final String URL_GET_ALL_USUARIOS = "https://ampandroid.000webhostapp.com/archivosPHP/Amigos_GETALL.php";


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_amigos, container, false);

        volley = VolleyRP.getInstance(getContext());
        mRequest = volley.getRequestQueue();

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.amigosRecyclerView);

        LinearLayoutManager lym = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lym);

        amigoAdapter = new AmigoAdapter(atributosList, getContext());
        rv.setAdapter(amigoAdapter);

        SolicitudJSON();

        return v;
    }

    public void agregarAmigo(int fotoDePerfil, String nombre, String ultimomensaje, String hora, String id) {
        AmigosAtributos atributos = new AmigosAtributos();
        atributos.setFotoPerfil(fotoDePerfil);
        atributos.setNombre(nombre);
        atributos.setUltimoMensaje(ultimomensaje);
        atributos.setHora(hora);
        atributos.setId(id);
        atributosList.add(atributos);
        amigoAdapter.notifyDataSetChanged();
    }

    public void SolicitudJSON() {
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String TodosLosDatos = datos.getString("resultado");
                    String TodosLosUusuarioConToken = datos.getString("usuariosconTokens");
                    JSONArray jsonArray = new JSONArray(TodosLosDatos);
                    JSONArray jsUserTokens = new JSONArray(TodosLosUusuarioConToken);
                    String NuestroUsuario = Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject js = jsonArray.getJSONObject(i);
                        if (!NuestroUsuario.equals(js.getString("id"))) {
                            for (int k = 0; k < jsUserTokens.length(); k++) {
                                JSONObject UsuarioconToken = jsUserTokens.getJSONObject(k);
                                if (js.getString("id").equals(UsuarioconToken.getString("id"))) {
                                    if (Preferences.obtenerPreferenceTipo(getContext(), Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("1")){
                                        agregarAmigo(R.drawable.doctororange64, js.getString("nombre"), "Mensaje #" + i, "00:00", js.getString("id"));
                                    }else if (Preferences.obtenerPreferenceTipo(getContext(), Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("0") || Preferences.obtenerPreferenceTipo(getContext(), Preferences.PREFERENCE_USUARIO_TIPO).equalsIgnoreCase("a")){
                                        agregarAmigo(R.drawable.doctorblue64, js.getString("nombre"), "Mensaje #" + i, "00:00", js.getString("id"));
                                    }

                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ocurrio un error, por favor contactese con el administrador", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, mRequest, getContext(), volley);
    }
}
