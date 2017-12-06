package reza.raul.rm.mensajeria;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import reza.raul.rm.R;

/**
 * Created by Android on 02/11/2017.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MensajesViewHolder> {

    private List<MsgTexto> mensajeDeTextos;
    private Context context;

    public MsgAdapter(List<MsgTexto> mensajesTexto, Context context) {
        this.mensajeDeTextos = mensajesTexto;
        this.context = context;
    }

    @Override
    public MensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_msg,parent,false);
        return new MsgAdapter.MensajesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.MensajesViewHolder holder, int position) {
        RelativeLayout.LayoutParams r1 = (RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();
        FrameLayout.LayoutParams f1 = (FrameLayout.LayoutParams) holder.mensajeBG.getLayoutParams();

        LinearLayout.LayoutParams l1 = (LinearLayout.LayoutParams) holder.TvMensaje.getLayoutParams();
        LinearLayout.LayoutParams l2 = (LinearLayout.LayoutParams) holder.TvHora.getLayoutParams();
        if ((mensajeDeTextos.get(position)).getTipoMensaje() == 1) {
            holder.mensajeBG.setBackgroundResource(R.drawable.globo11);
            r1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            r1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            l2.gravity = Gravity.RIGHT;
            l1.gravity = Gravity.RIGHT;
            f1.gravity = Gravity.RIGHT;
            holder.TvMensaje.setGravity(Gravity.RIGHT);
        } else if ((mensajeDeTextos.get(position)).getTipoMensaje() == 0) {
            holder.mensajeBG.setBackgroundResource(R.drawable.globo21);
            r1.addRule(0, RelativeLayout.ALIGN_PARENT_RIGHT);
            r1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            l2.gravity = Gravity.LEFT;
            l1.gravity = Gravity.LEFT;
            f1.gravity = Gravity.LEFT;
            holder.TvMensaje.setGravity(Gravity.LEFT);
        }
        holder.cardView.setLayoutParams(r1);
        holder.mensajeBG.setLayoutParams(f1);
        holder.TvMensaje.setLayoutParams(l2);
        holder.TvHora.setLayoutParams(l1);

        holder.TvMensaje.setText((mensajeDeTextos.get(position)).getMensaje());
        holder.TvHora.setText((mensajeDeTextos.get(position)).getHoraMensaje());
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            holder.cardView.getBackground().setAlpha(0);
        else
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
    }

    @Override
    public int getItemCount() {
        return mensajeDeTextos.size();
    }


    static class MensajesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout mensajeBG;
        TextView TvMensaje;
        TextView TvHora;

        MensajesViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewMensaje);
            mensajeBG = (LinearLayout) itemView.findViewById(R.id.msgBG);
            TvMensaje = (TextView) itemView.findViewById(R.id.txtMsg);
            TvHora = (TextView) itemView.findViewById(R.id.txtHora);
        }
    }
}
