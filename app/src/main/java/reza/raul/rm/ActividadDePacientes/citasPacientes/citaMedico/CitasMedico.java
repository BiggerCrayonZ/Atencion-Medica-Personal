package reza.raul.rm.ActividadDePacientes.citasPacientes.citaMedico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import reza.raul.rm.ActividadDePacientes.citasPacientes.citaAtributos;
import reza.raul.rm.ActividadDePacientes.citasPacientes.citaPacientes.CitasPacientesAdapter;
import reza.raul.rm.Preferences;
import reza.raul.rm.R;
import reza.raul.rm.VolleyRP;

/**
 * Created by Android on 11/11/2017.
 */

public class CitasMedico extends Fragment {
    private String MEDICO = "";

    private List<citaAtributos> citasList;
    private CitasMedicoAdapter adapter;

    private RecyclerView rv;
    private VolleyRP volley;
    private RequestQueue mRequest;

    private static final String URL_GET_CITAS_BY_PACIENTES = "https://ampandroid.000webhostapp.com/archivosPHP/Citas_GETIDMEDICO.php?idMedico=";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_citas, container, false);

        volley = VolleyRP.getInstance(getContext());
        mRequest = volley.getRequestQueue();

        citasList = new ArrayList<>();

        MEDICO = Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);

        rv = (RecyclerView) v.findViewById(R.id.citasRecyclerView);

        LinearLayoutManager lym = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lym);

        adapter = new CitasMedicoAdapter(citasList, getContext());
        rv.setAdapter(adapter);

        generadorURL(MEDICO);

        return v;
    }

    public void generadorURL(String medico) {
        SolicitudCitas(URL_GET_CITAS_BY_PACIENTES + medico);
    }

    private void SolicitudCitas(String Url) {
        JsonObjectRequest solicitud = new JsonObjectRequest(Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String estado = datos.getString("resultado");
                    if (estado.equals("CC")) {
                        String todasLasCitas = datos.getString("datos");
                        JSONArray jsonArray = new JSONArray(todasLasCitas);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            agendarCita(R.drawable.calorange64, "Paciente: " +  jsonObject.getString("idPaciente"), "Fecha: " + jsonObject.getString("fecha"), "Hora: " + jsonObject.getString("hora"), "Dirección: " + jsonObject.getString("direccion"));
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

    public void agendarCita(int fotoCita, String nombrePaciente, String fechaCita, String horaCita, String direccionCita) {
        citaAtributos atributo = new citaAtributos();
        atributo.setFotoCita(fotoCita);
        atributo.setIdMedico(nombrePaciente);
        atributo.setFecha(fechaCita);
        atributo.setHora(horaCita);
        atributo.setDireccion(direccionCita);
        citasList.add(atributo);
        adapter.notifyDataSetChanged();
    }
}
