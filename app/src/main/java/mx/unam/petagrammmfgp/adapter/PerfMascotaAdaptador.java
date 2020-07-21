package mx.unam.petagrammmfgp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.pojo.Mascota;
import mx.unam.petagrammmfgp.R;

public class PerfMascotaAdaptador extends RecyclerView.Adapter<PerfMascotaAdaptador.PerfMascotaViewHolder> {

    ArrayList<Mascota> perfilmascotas;
    Activity activity;
    String Like;

    public PerfMascotaAdaptador(ArrayList<Mascota> perfilmascotas, Activity activity) {
        this.perfilmascotas = perfilmascotas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PerfMascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos un View inflado para que maneje nuestro RecyclerView que esta en fragment_perfil_mascota.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_perfilmascota,parent,false);
        //le pasamos el view( v, inflado ) a la clase estatica PerfilMascotaViewHolder()
        return new PerfMascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfMascotaViewHolder PerfMascotaViewHolderholder, int position) {
        final Mascota mascota = perfilmascotas.get(position);
        PerfMascotaViewHolderholder.imgvFotoPerfilCV.setImageResource(mascota.getFoto());
        Like = Integer.toString(mascota.getCantidad_like());
        PerfMascotaViewHolderholder.txvCantidadLikePerfilCV.setText(Like);
        PerfMascotaViewHolderholder.imgvCantidadLikePerfilCV.setImageResource(R.mipmap.huesoamarillo);
    }

    @Override
    public int getItemCount() {
        return perfilmascotas.size();
    }

    public static class PerfMascotaViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgvFotoPerfilCV;
        private TextView txvCantidadLikePerfilCV;
        private ImageView imgvCantidadLikePerfilCV;

        public PerfMascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvFotoPerfilCV = (ImageView) itemView.findViewById(R.id.imgvFotoPerfilCV);
            txvCantidadLikePerfilCV = (TextView) itemView.findViewById(R.id.txvCantidadLikePerfilCV);
            imgvCantidadLikePerfilCV = (ImageView) itemView.findViewById(R.id.imgvCantidadLikePerfilCV);
        }
    }
}
