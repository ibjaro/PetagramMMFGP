package mx.unam.petagrammmfgp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import mx.unam.petagrammmfgp.pojo.Mascota;

public class BaseDatos extends SQLiteOpenHelper {
    private Context context;
    //constructor de la clase BaseDatos, que recibe un contexto y a su constructor heredado se le pasa
    //ese contexto, el nombre de la base de datos, un  factory y la version de la base de datos
    public BaseDatos(@Nullable Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }
    //en esta funcion sobrescrita de la clase SQLiteOpenHelper, se crea la estructura de la Base de Datos
    // por medio del query create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //hacemos el query para la tabla mascota_principal
        String queryCrearTablaMascotaPrincipal = "CREATE TABLE "+ConstantesBaseDatos.TABLE_PET_PRINCIPAL+" ("+
                ConstantesBaseDatos.TABLE_PET_ID_PRINCIPAL     + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ConstantesBaseDatos.TABLE_PET_NOMBRE_PRINCIPAL + " TEXT,"+
                ConstantesBaseDatos.TABLE_PET_FOTO_PRINCIPAL   + " INTEGER"+
                ")";
        //ejecutamos el query para la tabla mascota_principal
        sqLiteDatabase.execSQL(queryCrearTablaMascotaPrincipal);

        //hacemos el query para la tabla mascota
        String queryCrearTablaMascota = "CREATE TABLE "+ConstantesBaseDatos.TABLE_PET+" ("+
                ConstantesBaseDatos.TABLE_PET_ID      +" INTEGER,"+
                ConstantesBaseDatos.TABLE_PET_NOMBRE  +" TEXT,"+
                ConstantesBaseDatos.TABLE_PET_FOTO    +" INTEGER,"+
                ConstantesBaseDatos.TABLE_PET_LIKES   +" INTEGER"+
                ")";
        //ejecutamos el query para la tabla mascota
        sqLiteDatabase.execSQL(queryCrearTablaMascota);

        //hacemos el query para la tabla mascota_likes
        String queryCrearTablaLIkeMascota = "CREATE TABLE "+ConstantesBaseDatos.TABLE_LIKES_PET+" ("+
                ConstantesBaseDatos.TABLE_LIKES_PET_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                ConstantesBaseDatos.TABLE_LIKES_PET_ID_MASCOTA   + " INTEGER,"+
                ConstantesBaseDatos.TABLE_LIKES_PET_NUMERO_LIKES + " INTEGER,"+
                "FOREIGN KEY ("+ConstantesBaseDatos.TABLE_LIKES_PET_ID_MASCOTA+") "+
                "REFERENCES "+ConstantesBaseDatos.TABLE_PET_PRINCIPAL+"("+ConstantesBaseDatos.TABLE_PET_ID_PRINCIPAL+")"+
                ")";
        //ejecutamos el query para la tabla mascota_likes
        sqLiteDatabase.execSQL(queryCrearTablaLIkeMascota);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_PET_PRINCIPAL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_LIKES_PET);
        onCreate(sqLiteDatabase);
    }
    //funcion que estara abriendo la base de datos en modo escritura para obtener los datos de la base de datos
    // por medio de un query select
    public ArrayList<Mascota> obtenerTodasLasMascotas()
    {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        //query para selecionar todos los datos de la tabla mascota_principal
        String query = "SELECT * FROM "+ConstantesBaseDatos.TABLE_PET_PRINCIPAL;
        //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query,null);
        //verificamos si nos podemos mover a un registro siguiente
        while (registros.moveToNext())
        {   //creamos un objeto de tipo Mascota al cual le ingresaremos los datos de cada registro encontrado
            Mascota mascotaActual = new Mascota();
            mascotaActual.setId(registros.getInt(0));
            mascotaActual.setNombre_mascota(registros.getString(1));
            mascotaActual.setFoto(registros.getInt(2));
            //query para saber los likes de la table mascota_likes
            String queryLike = "SELECT COUNT("+ConstantesBaseDatos.TABLE_LIKES_PET_NUMERO_LIKES+") AS like"+
                    " FROM "+ConstantesBaseDatos.TABLE_LIKES_PET+
                    " WHERE "+ConstantesBaseDatos.TABLE_LIKES_PET_ID_MASCOTA+"="+mascotaActual.getId();
            //ejecutamos un rawQuery cuando nos va a traer un valos de retorno, si no traera valor seria un execSQL
            Cursor registrosLikes = db.rawQuery(queryLike,null);
            if (registrosLikes.moveToNext())
            {  //si mascota actual tiene registros toma el valor del registro
                mascotaActual.setCantidad_like(registrosLikes.getInt(0));
            }else{
                   //si mascota actual No tiene registros se le coloca el valor 0
                   mascotaActual.setCantidad_like(0);
                 }
            mascotas.add(mascotaActual);
        }
        //cerramos la base de datos
        db.close();

        return mascotas;
    }
    //en esta funcion se estara abriendo la base de datos en modo escritura e insertando los datos de la mascota
    public void insertarMascota(ContentValues contentValues)
    {   //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();//cuando se ejecuta esta linea entra a la funcion onCreate
        db.insert(ConstantesBaseDatos.TABLE_PET_PRINCIPAL,null,contentValues);
        //cerramos la base de datos
        db.close();
    }
    //en esta funcion se estara abriendo la base de datos en modo escritura e insertando los datos de los like
    public void insertarLikeMascota(ContentValues contentValues)
    {
        //con esto se abre la base de datos, si no esta creada se crea en la funsion onCreate
        // y si ya lo esta se recarga en la funcion onUpdate
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_LIKES_PET,null,contentValues);
        //cerramos la base de datos
        db.close();
    }
    //funcion para obtener los Likes de la mascota
    public int obtenerLikesMascota(Mascota mascota)
    {
        int like =0;
        //query para saber cuantos likes se le ha dado a una foto de mascota, en la tabla mascota_likes
        String query = "SELECT COUNT("+ConstantesBaseDatos.TABLE_LIKES_PET_NUMERO_LIKES+")"+
                " FROM "+ConstantesBaseDatos.TABLE_LIKES_PET+
                " WHERE "+ConstantesBaseDatos.TABLE_LIKES_PET_ID_MASCOTA+ "="+mascota.getId();
        //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query,null);
        if(registros.moveToNext())
        {
            like = registros.getInt(0);
        }
        //cerramos la base de datos
        db.close();

        return like;
    }
    //funcion para verificar si existen valores en la tabla mascotas_principal(numTabla = 1) o mascotas(numTabla = 2)
    public boolean checarExistenciaDatosEnTabla(int numTabla)
    {   long cant = 0;
        //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        if(numTabla==1)
        {
            cant = DatabaseUtils.queryNumEntries(db,ConstantesBaseDatos.TABLE_PET_PRINCIPAL);
        }else if(numTabla==2)
               {
                   cant = DatabaseUtils.queryNumEntries(db,ConstantesBaseDatos.TABLE_PET);
               }
        //cerramos la base de datos
        db.close();
        return  cant==0;
    }
    //en esta funcion se estara abriendo la base de datos en modo escritura e insertando los datos de las
    // Ultimas Cinco Mascotas a las que se les dio Like
    public void insertarDatosUltimasCincoMascotas(ContentValues contentValues)
    {
        int numFilas=0;
        ContentValues contentValuesMascotasEnTabla;
        ArrayList<Mascota> mascotasEnTabla = new ArrayList<>();
        //query para selecionar todos los datos de la tabla mascota
        String queryTodoslosDatos = "SELECT * FROM "+ConstantesBaseDatos.TABLE_PET;
        //con esto se abre la base de datos, si no esta creada se crea en la funsion onCreate
        // y si ya lo esta se recarga en la funcion onUpdate
        SQLiteDatabase db = this.getWritableDatabase();
        //creamos un objeto tipo Cursor, que recibira los datos obtenidos del queryTodoslosDatos
        Cursor registrosTabla = db.rawQuery(queryTodoslosDatos,null);
        //consultamos la tabla mascota tiene alguna fila(datos cargados)
        numFilas = registrosTabla.getCount();

        if(numFilas==0)
        {   //insertamos los datos contentValues recibidos en el llamado a la funcion(que tiene los datos a ingresar)
            db.insert(ConstantesBaseDatos.TABLE_PET,null,contentValues);
        }else if(numFilas<=5)
                {
                    //ejecutamos un while para recorrer los registros obtenidos de la tabla e ingresarlos en
                    // un objeto mascota, y posteriormente agregarlos a un Array de mascotas que sera el que insertaremos en la tabla mascotas
                    while (registrosTabla.moveToNext())
                    {
                      Mascota mascota = new Mascota();
                      mascota.setId(registrosTabla.getInt(0));
                      mascota.setNombre_mascota(registrosTabla.getString(1));
                      mascota.setFoto(registrosTabla.getInt(2));
                      mascota.setCantidad_like(registrosTabla.getInt(3));

                      mascotasEnTabla.add(mascota);
                    }
                    //eliminamos todos los datos de la tabla mascotas haciendo uso de una funcion de la clase BaseDatos
                   db.delete(ConstantesBaseDatos.TABLE_PET,null,null);
                   //luego de borrar todos los datos de la tabla mascota lo primero que haremos sera insertar los datos de la
                    // mascota a la cual se le ha dada click
                   db.insert(ConstantesBaseDatos.TABLE_PET, null,contentValues);
                   //luego ingresamos los datos de las mascotas que ya estaban en la tabla, aqui nos aseguramos que
                    // solo se ingresen hasta Cinco datos en la tabla
                   for(int i=0;i<mascotasEnTabla.size()&&i<4;i++)
                   {   //creamos un objero ContentValues, le ingresamos todos los datos de la mascota
                       contentValuesMascotasEnTabla = new ContentValues();
                       contentValuesMascotasEnTabla.put(ConstantesBaseDatos.TABLE_PET_ID,mascotasEnTabla.get(i).getId());
                       contentValuesMascotasEnTabla.put(ConstantesBaseDatos.TABLE_PET_NOMBRE,mascotasEnTabla.get(i).getNombre_mascota());
                       contentValuesMascotasEnTabla.put(ConstantesBaseDatos.TABLE_PET_FOTO,mascotasEnTabla.get(i).getFoto());
                       contentValuesMascotasEnTabla.put(ConstantesBaseDatos.TABLE_PET_LIKES,mascotasEnTabla.get(i).getCantidad_like());
                       //hacemos un llamado a la funcion insert de la clase BaseDatos
                       db.insert(ConstantesBaseDatos.TABLE_PET,null,contentValuesMascotasEnTabla);
                   }

                }
        //cerramos la base de datos
        db.close();
    }
    //en esta funcion se estara abriendo la base de datos en modo escritura y obteniendo todos los
    //registros de las Ultimas Cinco Mascotas a las que se les dio Like
    public ArrayList<Mascota> obtenerDatosUltimasCincoMascotas()
    {
        ArrayList<Mascota> ultimasCincoMascotas = new ArrayList<>();
        //query para selecionar todos los datos de la tabla mascota_principal
        String query = "SELECT * FROM "+ConstantesBaseDatos.TABLE_PET;
        //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        //creamos un objeto tipo Cursor, que recibira los datos obtenidos del query
        Cursor registros = db.rawQuery(query,null);
        //ejecutamos un while para recorrer los registros obtenidos de la tabla e ingresarlos en
        // un objeto mascota, y posteriormente agregarlos a un Array de mascotas que sera el que retornaremos
        while (registros.moveToNext())
        {
            Mascota mascotaEnTabla = new Mascota();
            mascotaEnTabla.setId(registros.getInt(0));
            mascotaEnTabla.setNombre_mascota(registros.getString(1));
            mascotaEnTabla.setFoto(registros.getInt(2));
            mascotaEnTabla.setCantidad_like(registros.getInt(3));

            ultimasCincoMascotas.add(mascotaEnTabla);
        }
        //cerramos la base de datos
        db.close();

        return ultimasCincoMascotas;
    }
}
