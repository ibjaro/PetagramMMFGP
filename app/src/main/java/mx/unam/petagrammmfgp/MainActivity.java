package mx.unam.petagrammmfgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.adapter.PageAdapter;
import mx.unam.petagrammmfgp.fragment.PerfilMascota;
import mx.unam.petagrammmfgp.fragment.RVMascotasFragment;

public class MainActivity extends AppCompatActivity {


    private Toolbar actiontoolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RVMascotasFragment rvmf;
    private PerfilMascota rvpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Agregamos la barra personalizada a esta activity, el tablayout y el viewpager
        actiontoolbar = findViewById(R.id.miActionToolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.vPager);

        //hacemos el llamado al metodo que agregara el ViewPager al Tablayout
        setUpViewPager();

        //preguntamos si la toolbar no tiene valor nulo
        if(actiontoolbar!=null)
        {
            setSupportActionBar(actiontoolbar);
        }

    }
    //funcion sobre escrita para agregar el menu a la actiontoolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //funcion sobre escrita para captar la seleccion de las opciones del menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuCincoEstrellas)
        {
            envioDatosBundleMascota();

        }else if(item.getItemId()==R.id.menuContacto)
        {
            //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(Contacto)
            Intent intent = new Intent(this, Contacto.class);
            startActivity(intent);
            finish();//finalizamos( cerramos ) la activity( MainActivity ), para liberar memoria
        }else if(item.getItemId()==R.id.menuAcercaDe)
        {
            //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(AcercaDe)
            Intent intent = new Intent(this, AcercaDe.class);
            startActivity(intent);
            finish();//finalizamos( cerramos ) la activity( MainActivity ), para liberar memoria
        }

        return super.onOptionsItemSelected(item);
    }
    //metodo para hacer un ArrayList de Fragments, donde agregamos cada fragment
    public ArrayList<Fragment> agregarFragments()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();
        rvmf = new RVMascotasFragment();
        fragments.add(rvmf);
        rvpm = new PerfilMascota();
        fragments.add(rvpm);

        return fragments;
    }
    //funsion para agregar el ArrayList de Fragment a el ViewPager y este a su ves a el TabLayout
    private void setUpViewPager()
    {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        //agregamos los iconos para cada tab
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_casitamascota);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_caramascota);
        //tabLayout.getTabAt(0).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
    }
    //funcion para enviar datos Bundle a la Actividad MascotasFavoritas
    public void envioDatosBundleMascota()
    {
        //hacemos un intent para indicar que enviaremos datos desde esta activity hacia la siguiente
        Intent intent = new Intent(this,MascotasFavoritas.class);
        //iniciamos el intent
        startActivity(intent);
        finish();//finalizamos( cerramos ) la activity( MainActivity ), para liberar memoria
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la barra inferior
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();//finalizamos( cerramos ) la activity( AcercaDe ), para liberar memoria
        }
        return super.onKeyDown(keyCode, event);
    }
    //metodo sobre escrito que recibe los datos bundle de MascotaAdaptador
    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
        //enviamos como argumentos los datos bundle recibidos, a el fragment PerfilMascota
        rvpm.setArguments(data);
        //hacemos un llamado a el metodo que recibira los datos Bundle
        rvpm.cargaDeDatos();
    }
}