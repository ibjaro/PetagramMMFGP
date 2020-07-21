package mx.unam.petagrammmfgp.fragment;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.adapter.MascotaAdaptador;
import mx.unam.petagrammmfgp.pojo.Mascota;

public interface IRVMascotasFragmentView {
    public void generarLinerLayoutVertial();

    public MascotaAdaptador generarAdaptador(ArrayList<Mascota> mascotas);

    public void inicializarAdaptadorRV(MascotaAdaptador adaptador);
}
