package mx.unam.petagrammmfgp.adapter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.db.ConstructorMascotas;
import mx.unam.petagrammmfgp.pojo.Mascota;
import mx.unam.petagrammmfgp.R;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder>{

    ArrayList<Mascota> mascotas;
    Activity activity;
    int cantidad=0, usoactividad;
    String Like;

    public MascotaAdaptador(ArrayList<Mascota> mascotas, Activity activity, int usoactividad) {
        this.mascotas = mascotas;
        this.activity = activity;
        this.usoactividad = usoactividad;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos un View inflado para que maneje nuestro RecyclerView que esta en activity_main.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota,parent,false);
        //le pasamos el view( v, inflado ) a la clase estatica MascotaViewHolder()
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MascotaViewHolder mascotaViewHolderholder, int position) {
        //creamos un elemento contacto para agregarle los datos de el contacto especificado con position
        final Mascota mascota = mascotas.get(position);
        mascotaViewHolderholder.imgvFotoCV.setImageResource(mascota.getFoto());
        mascotaViewHolderholder.txvNombreCV.setText(mascota.getNombre_mascota());
        Like = Integer.toString(mascota.getCantidad_like());
        mascotaViewHolderholder.txvCantidadLikeCV.setText(Like);
        mascotaViewHolderholder.imgvCantidadLikeCV.setImageResource(R.mipmap.huesoamarillo);
        if(usoactividad==1)
        {
            mascotaViewHolderholder.imgbtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                    constructorMascotas.darLikeMascota(mascota);
                    Like = String.valueOf(constructorMascotas.obtenerLikesMascota(mascota));
                    mascotaViewHolderholder.txvCantidadLikeCV.setText(Like);
                }
            });
        }
        mascotaViewHolderholder.imgvFotoCV.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                //se crea un bundle para enviar al ActivityMain los datos de la mascota seleccionada,
                //para que esta a su ves le envie los datos a el fragment PerfilMascota
                Bundle bun = new Bundle();
                bun.putInt("foto",mascota.getFoto());
                bun.putString("nombre",mascota.getNombre_mascota());
                //usamos un metodo sobre escrito de activity
                activity.onProvideAssistData(bun);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgvFotoCV;
        private ImageButton imgbtnLike;
        private TextView txvNombreCV;
        private TextView txvCantidadLikeCV;
        private ImageView imgvCantidadLikeCV;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvFotoCV = (ImageView) itemView.findViewById(R.id.imgvFotoCV);
            imgbtnLike = (ImageButton) itemView.findViewById(R.id.imgbtnLike);
            txvNombreCV = (TextView) itemView.findViewById(R.id.txvNombreCV);
            txvCantidadLikeCV = (TextView) itemView.findViewById(R.id.txvCantidadLikeCV);
            imgvCantidadLikeCV = (ImageView) itemView.findViewById(R.id.imgvCantidadLikeCV);
        }
    }
}
