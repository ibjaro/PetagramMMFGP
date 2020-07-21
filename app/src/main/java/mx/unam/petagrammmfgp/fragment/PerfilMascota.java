package mx.unam.petagrammmfgp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.pojo.Mascota;
import mx.unam.petagrammmfgp.R;
import mx.unam.petagrammmfgp.adapter.PerfMascotaAdaptador;

public class PerfilMascota extends Fragment {
    private ArrayList<Mascota> perfilmascota;
    private RecyclerView listaperfilmascota;
    CircularImageView cIVFotoPerfilMascota;
    private TextView txtvNombrePerfilM;
    //para saber que mascota se ha seleccionado
    private String nombreMascota="sinmascota";
    private int fotoMascota=0;
    PerfMascotaAdaptador adaptador;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_mascota, container, false);
        //agregamos la Foto de perfil de la mascota, que se vera de forma circular, y agregamos el TextView para su nombre
        cIVFotoPerfilMascota = (CircularImageView) v.findViewById(R.id.civFotoPerfilMascota);
        txtvNombrePerfilM = (TextView) v.findViewById(R.id.txtvNombrePerfilM);
        //hacemos visible la listamascotas, que es un recyclerView
        listaperfilmascota = (RecyclerView) v.findViewById(R.id.rvPerfilMascotas);

        //mostraremos la lista de mascotas en forma de Gridlayout, de tres columnas
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        listaperfilmascota.setLayoutManager(glm);

        //hacemos el llamada a la funcion que cargara los datos inicialmente
        inicializarListaPerfilMascota();
        //hacemos el llamado al adaptador que cargara los datos inicialmente
        inicializarAdaptadorPM();

        return v;
    }
    //funcion para inicializar adartador
    public void inicializarAdaptadorPM()
    {
        //creamos el adaptador y le pasamos el arreglo de mascotas favoritas, en que activity estamos
        //y que numero es para saber en la clase MascotaAdaptador como mostrara los datos y si seran clicables o no
        adaptador = new PerfMascotaAdaptador(perfilmascota, getActivity());
        listaperfilmascota.setAdapter(adaptador);
    }
    //funcion para cargar inicialmente los datos del perfil de mascota
    public void inicializarListaPerfilMascota()
    {
        int cantlike=0;
        double cl;
        //creamos un nuevo arreglo de mascotas
        perfilmascota = new ArrayList<>();
        //agregamos el nombre de la mascota cargada inicialmente
        txtvNombrePerfilM.setText("conejo");
        for (int i=0;i<9;i++)
        {   //agregamos un valor aleatorio para los like de cada foto
            cl = Math.random()*15+1;
            cantlike = (int) cl;
            //agregamos los datos iniciales
            perfilmascota.add(new Mascota(R.drawable.conejo,"conejin",cantlike));
        }
    }
    //funcion para inicializar los datos de la mascota seleccionada( con datos dummy)
    public void inicializarMascotaSeleccionada()
    {
        int cantlike=0;
        double cl;
        //creamos un nuevo arreglo de mascotas
        perfilmascota = new ArrayList<>();
        //agregamos la foto y el nombre de la mascota seleccionada
        cIVFotoPerfilMascota.setImageResource(fotoMascota);
        txtvNombrePerfilM.setText(nombreMascota);
        for (int i=0;i<9;i++)
        {   //agregamos un valor aleatorio para los like de cada foto
            cl = Math.random()*15+1;
            cantlike = (int) cl;
            perfilmascota.add(new Mascota(fotoMascota,nombreMascota,cantlike));
        }
    }
    //funcion para atrapar los datos Bundle enviados desde el MainActivity, agregarlos al adaptador
    //y colocarlos en la lista de recyclerView
    public void cargaDeDatos()
    {
        //validamos que si hay datos bundle enviados
        if(getArguments()!=null)
        {
            //asignamos los datos bundle enviados a su variable respectiva
            fotoMascota = getArguments().getInt("foto");
            nombreMascota = getArguments().getString("nombre");
            //hacemos el llamado a la funcion que inicializara los datos
            inicializarMascotaSeleccionada();
            //enviamos los datos inicializados para ser visualizados
            inicializarAdaptadorPM();
        }
    }
}
