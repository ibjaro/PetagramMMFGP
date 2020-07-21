package mx.unam.petagrammmfgp.presentador;

import android.content.Context;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.db.ConstructorMascotas;
import mx.unam.petagrammmfgp.fragment.IRVMascotasFragmentView;
import mx.unam.petagrammmfgp.pojo.Mascota;

public class RVMascotasFragmentPresenter implements IRVMascotasFrgmentPresenter{

    private IRVMascotasFragmentView irvMascotasFragmentView;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;

    public RVMascotasFragmentPresenter(IRVMascotasFragmentView irvMascotasFragmentView, Context context) {
        this.irvMascotasFragmentView = irvMascotasFragmentView;
        this.context = context;
        //hacemos el llamado a la funcion obtenerMascotasBaseDatos, que se ejecutara al crear
        //un objeto de RVMascotasFragmentPresenter
        obtenerMascotasBaseDatos();
    }
    //metodo sobre escrito para obtener las mascotas de la base de datos
    @Override
    public void obtenerMascotasBaseDatos() {
        //hacemos un ubjeto Constructor mascotas y le pasamos el contexto
        constructorMascotas = new ConstructorMascotas(context);
        //le agregamos a la variable mascota lo que devuelva el metodo obtenerDatos de la clase ConstructorMascotas
        mascotas = constructorMascotas.obtenerDatos();
        //hacemos el llamada al metodo sobre escrito mostrarMascotas
        mostrarMascotas();

    }
    //metodo sobre escrito mostrarMascotas
    @Override
    public void mostrarMascotas() {
        //haciendo uso del objeto irvMascotasFragmentView de la clase IRVMascotasFragmentView implementada en la
        // clase RVMascotasFragment, hacemos el llamado a la funcion generarLinerLayoutVertial()
        irvMascotasFragmentView.generarLinerLayoutVertial();
        //hacemos el llamado a la funcion inicializarAdaptadorRV que recibe como parametro la funcion generarAdaptador
        //que a su ves recibe cmo parametro un arrelo de mascotas( obtenido en el metodo obtenerMascotasBaseDatos)
        irvMascotasFragmentView.inicializarAdaptadorRV(irvMascotasFragmentView.generarAdaptador(mascotas));
    }
}
