package mx.unam.petagrammmfgp;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.adapter.MascotaAdaptador;
import mx.unam.petagrammmfgp.adapter.PerfMascotaAdaptador;
import mx.unam.petagrammmfgp.pojo.Mascota;

public interface IMascotasFavoritasView {
    public void generarLinerLayoutVertial();

    public MascotaAdaptador generarAdaptador(ArrayList<Mascota> mascotas);

    public void inicializarAdaptadorRV(MascotaAdaptador adaptador);

    public void agregarBarraPersonalizada();
}
