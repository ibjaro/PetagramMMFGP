package mx.unam.petagrammmfgp.db;

public class ConstantesBaseDatos {
    public static final String DATABASE_NAME = "mascotas";
    public static final int DATABASE_VERSION = 1;
    //para la tabla mascota_principal, que guardara todas las mascotas agregadas
    public static final String TABLE_PET_PRINCIPAL = "mascota_principal";
    public static final String TABLE_PET_ID_PRINCIPAL = "id";
    public static final String TABLE_PET_NOMBRE_PRINCIPAL = "nombre";
    public static final String TABLE_PET_FOTO_PRINCIPAL = "foto";
    //para la tabla mascota(identica al POJO mascota), que guardara todos los datos de las
    // ultimas 5 mascotas a las que se les dio like(rating)
    public static final String TABLE_PET = "mascota";
    public static final String TABLE_PET_ID = "id";
    public static final String TABLE_PET_NOMBRE = "nombre";
    public static final String TABLE_PET_FOTO = "foto";
    public static final String TABLE_PET_LIKES = "likes";
    //para la tabla mascota_likes
    public static final String TABLE_LIKES_PET = "mascota_likes";
    public static final String TABLE_LIKES_PET_ID = "id";
    public static final String TABLE_LIKES_PET_ID_MASCOTA = "id_mascota";
    public static final String TABLE_LIKES_PET_NUMERO_LIKES = "numero_likes";
}
