package mx.unam.petagrammmfgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

public class AcercaDe extends AppCompatActivity {

    private Toolbar actiontoolbarad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        //Agregamos la barra personalizada a esta activity
        actiontoolbarad = findViewById(R.id.miActionToolBarAd);
        setSupportActionBar(actiontoolbarad);
        //le cambiamos el titulo a la barra personalizada para Acerca de
        actiontoolbarad.setTitle(R.string.titulo_actiontoolbarAd);
        actiontoolbarad.setTitleTextColor(Color.WHITE);
        //validamos que la barra no este NUll
        if(getSupportActionBar()!=null)
        {
            //habilitamos el boton de subir
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la ActionBar

    @Override
    public boolean onSupportNavigateUp() {
        //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(MainActivity)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();//finalizamos( cerramos ) la activity( AcercaDe ), para liberar memoria
        return super.onSupportNavigateUp();
    }
    //funcion sobre escrita donde se captura el evento del boton atras de la barra inferior

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {  //hacemos un intent para indicar que iremos desde esta activity hacia la siguiente(MainActivity)
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();//finalizamos( cerramos ) la activity( AcercaDe ), para liberar memoria
        }
        return super.onKeyDown(keyCode, event);
    }
}