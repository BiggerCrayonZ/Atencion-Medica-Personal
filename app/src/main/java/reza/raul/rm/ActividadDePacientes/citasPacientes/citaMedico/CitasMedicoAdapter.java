package reza.raul.rm.ActividadDePacientes.citasPacientes.citaMedico;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import reza.raul.rm.ActividadDePacientes.citasPacientes.citaAtributos;
import reza.raul.rm.ActividadDePacientes.citasPacientes.citaMedico.detalleMedicos.DetalleMedico;
import reza.raul.rm.ActividadDePacientes.citasPacientes.citaPacientes.detallePacientes.DetallePaciente;
import reza.raul.rm.R;

/**
 * Created by Android on 11/11/2017.
 */

public class CitasMedicoAdapter extends RecyclerView.Adapter<CitasMedicoAdapter.HolderCitas> {

    private List<citaAtributos> citasList;
    private Context context;

    public CitasMedicoAdapter(List<citaAtributos> citasList, Context context) {
        this.citasList = citasList;
        this.context = context;
    }

    @Override
    public HolderCitas onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_citas, parent, false);
        return new CitasMedicoAdapter.HolderCitas(v);
    }

    @Override
    public void onBindViewHolder(CitasMedicoAdapter.HolderCitas holder, final int position) {
        holder.imageView.setImageResource(citasList.get(position).getFotoCita());
        holder.nombrePaciente.setText(citasList.get(position).getIdMedico());
        holder.txtFecha.setText(citasList.get(position).getFecha());
        holder.txtHora.setText(citasList.get(position).getHora());
        holder.txtDireccion.setText(citasList.get(position).getDireccion());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetalleMedico.class);
                i.putExtra("key_detalle_paciente", citasList.get(position).getIdMedico());
                i.putExtra("key_detalle_fecha", citasList.get(position).getFecha());
                i.putExtra("key_detalle_hora", citasList.get(position).getHora());
                i.putExtra("key_detalle_direccion", citasList.get(position).getDireccion());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citasList.size();
    }

    static class HolderCitas extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView nombrePaciente;
        TextView txtFecha;
        TextView txtHora;
        TextView txtDireccion;


        public HolderCitas(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewCita);
            imageView = (ImageView) itemView.findViewById(R.id.imgCita);
            nombrePaciente = (TextView) itemView.findViewById(R.id.txtNombreMedico);
            txtFecha = (TextView) itemView.findViewById(R.id.txtCitaFecha);
            txtHora = (TextView) itemView.findViewById(R.id.txtCitaHora);
            txtDireccion = (TextView) itemView.findViewById(R.id.txtDireccion);
        }
    }
}
