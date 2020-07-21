package mx.unam.petagrammmfgp.presentador;

import android.content.Context;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.IMascotasFavoritasView;
import mx.unam.petagrammmfgp.db.ConstructorMascotas;
import mx.unam.petagrammmfgp.pojo.Mascota;

public class MascotasFavoritasPresenter implements IMascotasFavoritasPresenter {
    private IMascotasFavoritasView iMascotasFavoritasView;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;

    public MascotasFavoritasPresenter(IMascotasFavoritasView iMascotasFavoritasView, Context context) {
        this.iMascotasFavoritasView = iMascotasFavoritasView;
        this.context = context;
        //hacemos el llamado a la funcion agregarBarra() para agregar la barra(actiontoolbar) personalizada
        agregarBarra();
        //hacemos el llamado a la funcion obtenerMascotasBaseDatos, que se ejecutara al crear
        //un objeto de MascotasFavoritasPresenter
        obtenerMascotasBaseDatos();
    }
    //funcion para agregar la barra(actiontoolbar) personalizada
    @Override
    public void agregarBarra() {
        iMascotasFavoritasView.agregarBarraPersonalizada();
    }

    @Override
    public void obtenerMascotasBaseDatos() {
        //hacemos un ubjeto Constructor mascotas y le pasamos el contexto
        constructorMascotas= new ConstructorMascotas(context);
        //le agregamos a la variable mascota lo que devuelva el metodo obtenerDatosUltimasCincoMascotas de la clase ConstructorMascotas
        mascotas = constructorMascotas.obtenerDatosUltimasCincoMascotas();
        //hacemos el llamada al metodo sobre escrito mostrarMascotas
        mostrarMascotas();
    }

    @Override
    public void mostrarMascotas() {
        //haciendo uso del objeto iMascotasFavoritasView de la clase IMascotasFavoritasView implementada en la
        // clase MascotasFavoritas, hacemos el llamado a la funcion generarLinerLayoutVertial()
        iMascotasFavoritasView.generarLinerLayoutVertial();
        //hacemos el llamado a la funcion inicializarAdaptadorRV que recibe como parametro la funcion generarAdaptador
        //que a su ves recibe cmo parametro un arrelo de mascotas( obtenido en el metodo obtenerMascotasBaseDatos)
        iMascotasFavoritasView.inicializarAdaptadorRV(iMascotasFavoritasView.generarAdaptador(mascotas));

    }
}
