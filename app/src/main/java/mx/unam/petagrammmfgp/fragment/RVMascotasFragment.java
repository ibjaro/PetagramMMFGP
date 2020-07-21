package mx.unam.petagrammmfgp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.pojo.Mascota;
import mx.unam.petagrammmfgp.R;
import mx.unam.petagrammmfgp.adapter.MascotaAdaptador;
import mx.unam.petagrammmfgp.presentador.RVMascotasFragmentPresenter;

public class RVMascotasFragment extends Fragment implements IRVMascotasFragmentView{
    private ArrayList<Mascota> mascotas;
    private RecyclerView listamascotas;
    private RVMascotasFragmentPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_rvmascotas,container,false);
        //hacemos visible la listamascotas, que es un recyclerView
        listamascotas =  v.findViewById(R.id.rvMascotas);
        //creamos un objeto presenter de la clase RVMascotasFragmentPresenter, el cual recibe como parametro un
        // IRVMascotasFragmentView y el contexto donde estamos(en este caso: RVMascotasFragment, anexa a MainActivity)
        presenter = new RVMascotasFragmentPresenter(this, getContext());

        return v;
    }
    //funcion get que devuelve el ArrayList de mascotas
    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }
    //funcion sobre escrita para generar el LinearLayoutVertical del RecyclerView (rvMascotas)
    @Override
    public void generarLinerLayoutVertial() {
        //mostraremos la lista de mascotas en forma de Linearlayout
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listamascotas.setLayoutManager(llm);

    }
    //funcion sobre escrita para generar un MascotaAdaptador
    @Override
    public MascotaAdaptador generarAdaptador(ArrayList<Mascota> mascotas) {
        //creamos un objero Mascota adaptador y lo retornamos en llamado a la funcion
        MascotaAdaptador adaptador = new MascotaAdaptador(mascotas, getActivity(), 1);
        return adaptador;
    }
    //funcion sobre escrita para inicializar el adaptador generado de MascotaAdaptador
    @Override
    public void inicializarAdaptadorRV(MascotaAdaptador adaptador) {

        listamascotas.setAdapter(adaptador);
    }
}
