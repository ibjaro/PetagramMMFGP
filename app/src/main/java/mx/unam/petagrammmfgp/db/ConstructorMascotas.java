package mx.unam.petagrammmfgp.db;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.R;
import mx.unam.petagrammmfgp.pojo.Mascota;

public class ConstructorMascotas {
    private static final Integer LIKE = 1;
    private Context context;
    private ArrayList<Mascota> mascotas;

    public ConstructorMascotas(Context context) {
        this.context = context;
    }
    //funcion para obtener un arreglo de las mascotas de la base de datos
    public ArrayList<Mascota> obtenerDatos()
    {   boolean check;

        //creamos un objero de base de datos y le pasamos el contexto en que estamos
        BaseDatos db = new BaseDatos(context);
        //se llama a la funcion checarExistenciaDatosEnTabla() para verificar si hay valores en la tabla mascotas
        check = db.checarExistenciaDatosEnTabla(1);
        //si check es verdadero(que si esta vacia la tabla) se llamara a la funcion insertarTresMascotas
        //de lo contrario seguira al return, donde devolvera los datos de las mascotas.
        if(check)
        {
            //hacemos un llmado a la funcion insertarTresMascotas y le agregamos el objeto db
            insertarTresMascotas(db);
        }
        //retornamos los datos de las mascotas a partir del metodo obtenerTodasLasMascotas de la clase BaseDatos
        return db.obtenerTodasLasMascotas();
    }
    //funcion para ingresar tres mascotas al iniciar la aplicacion
    public void insertarTresMascotas(BaseDatos db)
    {   //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Elefantin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.elefante);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
        //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Conejin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.conejo);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
        //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Caballin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.caballo);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
        //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Tortuguin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.tortuga);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
        //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Ranin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.rana);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
        //creamos un objero ContectValues, para ingresarle el nombre de la mascota y su foto
        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL,"Ratoncin");
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL, R.drawable.raton);
        //hacemos un llamado a la funcion insertarMascota de la clase BaseDatos
        db.insertarMascota(contentValues);
    }
    //funcion para dar like a una mascota
    public void darLikeMascota(Mascota mascota)
    {
        //creamos un objero de base de datos y le pasamos el contexto en que estamos
        BaseDatos db = new BaseDatos(context);
        //creamos un objero ContentValues, le ingresamos el id de la mascota y el numero de likes
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_PET_ID_MASCOTA,mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_PET_NUMERO_LIKES,LIKE);
        //hacemos un llamado a la funcion insertarLikeMascota de la clase BaseDatos
        db.insertarLikeMascota(contentValues);
        //llamado a la funcion agregarDatosUltimasCincoMascotas, para llenar la tabla mascota que tendra solo las ultimas 5
        //mascotas a las que se les ha dado like
        agregarDatosUltimasCincoMascotas(mascota);
    }
    //funcion para obtener las cantidad de likes de una mascota
    public int obtenerLikesMascota(Mascota mascota)
    {   //creamos un objero de base de datos y le pasamos el contexto en que estamos
        BaseDatos db = new BaseDatos(context);
        //retornamos los likes a partir del metodo obtenerLikesMascota de la clase BaseDatos
        return db.obtenerLikesMascota(mascota);
    }
    //funcion para agregar todos los datos de la mascota cuando se da like a una mascota
    public void agregarDatosUltimasCincoMascotas(Mascota mascota)
    {
        //creamos un objero de base de datos y le pasamos el contexto en que estamos
        BaseDatos db = new BaseDatos(context);
        //creamos un objero ContentValues, le ingresamos todos los datos de la mascota
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_PET_ID,mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_PET_NOMBRE,mascota.getNombre_mascota());
        contentValues.put(ConstantesBaseDatos.TABLE_PET_FOTO,mascota.getFoto());
        contentValues.put(ConstantesBaseDatos.TABLE_PET_LIKES,db.obtenerLikesMascota(mascota));//omascota.getCantidad_like()+1 tambien
        //hacemos un llamado a la funcion insertarLikeMascota de la clase BaseDatos
        db.insertarDatosUltimasCincoMascotas(contentValues);
    }
    //funcion para obtener los datos de las Ultimas Cinco mascotas  las que se les ha dado like
    public ArrayList<Mascota> obtenerDatosUltimasCincoMascotas()
    {
        boolean check;
        ArrayList<Mascota> mascotasvacias = new ArrayList<>();
        //creamos un objero de base de datos y le pasamos el contexto en que estamos
        BaseDatos db = new BaseDatos(context);
        check = db.checarExistenciaDatosEnTabla(2);
        if (check)
        {   //si no se le ha dado like a alguna foto, mostramos un mensaje que indique esto
            Toast.makeText(context, "No se le ha dado Like  a ningura Foto", Toast.LENGTH_SHORT).show();
        }else{
               //retornamos los datos a partir del metodo obtenerDatosUltimasCincoMascotas de la clase BaseDatos
               return db.obtenerDatosUltimasCincoMascotas();
             }

        return mascotasvacias;
    }
}
