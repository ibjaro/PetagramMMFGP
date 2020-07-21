package mx.unam.petagrammmfgp.pojo;

import java.io.Serializable;

public class Mascota implements Serializable {
    private int id;
    private int foto;
    private String nombre_mascota;
    private int cantidad_like;

    public Mascota(int foto, String nombre_mascota, int cantidad_like) {
        this.foto = foto;
        this.nombre_mascota = nombre_mascota;
        this.cantidad_like = cantidad_like;
    }
    //constructor creado para ir agregando mascotas en el while del metodo obtenerTodasLasMascotas()
    //de la clase BaseDatos
    public Mascota() {

    }

    public int getFoto() { return foto; }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre_mascota() {
        return nombre_mascota;
    }

    public void setNombre_mascota(String nombre_mascota) {
        this.nombre_mascota = nombre_mascota;
    }

    public int getCantidad_like() {
        return cantidad_like;
    }

    public void setCantidad_like(int cantidad_like) {
        this.cantidad_like = cantidad_like;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
