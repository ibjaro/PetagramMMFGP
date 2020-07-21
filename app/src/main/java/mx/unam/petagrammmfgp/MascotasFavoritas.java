package mx.unam.petagrammmfgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.adapter.MascotaAdaptador;
import mx.unam.petagrammmfgp.adapter.PerfMascotaAdaptador;
import mx.unam.petagrammmfgp.pojo.Mascota;
import mx.unam.petagrammmfgp.presentador.MascotasFavoritasPresenter;

public class MascotasFavoritas extends AppCompatActivity implements IMascotasFavoritasView{

    private Toolbar actiontoolbarf;
    private RecyclerView listamascotasFav;
    private MascotasFavoritasPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas_favoritas);

        //creamos un objeto presenter de la clase MascotasFavoritasPresenter, el cual recibe como parametro un
        // IMascotasFavoritasView y el contexto donde estamos(en este caso: MascotasFavoritas)
        presenter = new MascotasFavoritasPresenter(this,MascotasFavoritas.this);

    }
    //funcion sobre escrita donde se captura el evento del boton atras de la ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        //Si el boton de atras se selecciona se llamara a la funcion datosBundleIrAtras()
        datosBundleIrAtras();
        return super.onSupportNavigateUp();
    }
    //funcion para enviar los datos en el botos atras
    public void datosBundleIrAtras()
    {
        //hacemos un intent para indicar que enviaremos datos desde esta activity hacia la siguiente
        Intent intentatras = new Intent(this, MainActivity.class);
        //iniciamos el intent
        startActivity(intentatras);
        finish();//finalizamos( cerramos ) la activity( MascotasFavoritas ), para liberar memoria
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la barra inferior
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            datosBundleIrAtras();
        }
        return super.onKeyDown(keyCode, event);
    }
    //funcion sobre escrita para generar el LinearLayoutVertical del RecyclerView (rvMascotasF)
    @Override
    public void generarLinerLayoutVertial() {
        //hacemos visible la mascota favorita, que es un recyclerView
        listamascotasFav = findViewById(R.id.rvMascotasF);
        //mostraremos la lista de mascotas en forma de Linearlayout
        LinearLayoutManager llmf = new LinearLayoutManager(this);
        llmf.setOrientation(LinearLayoutManager.VERTICAL);

        listamascotasFav.setLayoutManager(llmf);
    }
    //funcion sobre escrita para generar un PerfMascotaAdaptador
    @Override
    public MascotaAdaptador generarAdaptador(ArrayList<Mascota> mascotas) {
        //creamos el adaptador y le pasamos el arreglo de mascotas favoritas, en que activity estamos
        //y que numero es para saber en la clase MascotaAdaptador como mostrara los datos y si seran clicables o no
        MascotaAdaptador adaptador = new MascotaAdaptador(mascotas, this,2);
        return adaptador;
    }
    //funcion sobre escrita para inicializar el adaptador generado de PerfMascotaAdaptador
    @Override
    public void inicializarAdaptadorRV(MascotaAdaptador adaptador) {

        listamascotasFav.setAdapter(adaptador);
    }
    //funcion para agregar la barra(actiontoolbar) personalizada
    @Override
    public void agregarBarraPersonalizada() {
        //Agregamos la barra personalizada a esta activity
        actiontoolbarf = findViewById(R.id.miActionToolBarF);
        setSupportActionBar(actiontoolbarf);
        //le cambiamos el titulo a la barra personalizada para MascotasFavoritas
        actiontoolbarf.setTitle(R.string.titulo_actiontoolbarf);
        actiontoolbarf.setTitleTextColor(Color.WHITE);
        //validamos que la barra no este NUll
        if(getSupportActionBar()!=null)
        {
            //habilitamos el boton de subir
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}