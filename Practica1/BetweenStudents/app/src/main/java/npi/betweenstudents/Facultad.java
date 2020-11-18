package npi.betweenstudents;

import android.widget.SearchView;

import java.io.Serializable;

public class Facultad implements Serializable{
    //Atributos
    private int id;
    private String nombre;
    private String ubicacion;
    private int imagen;
    private String horario;
    private String campus;
    private String pagina_web;


    //Constructor
    public Facultad() {
        id = 0;
        nombre = "";
        ubicacion = "";
        imagen = 0;
        horario = "";
        campus ="";
        pagina_web = "";

    }

    //Constructor por parametros
    public Facultad (int i, String nom, String ubi, int ima, String hor, String camp, String web) {
        id = i;
        nombre = nom;
        ubicacion = ubi;
        imagen = ima;
        horario = hor;
        campus = camp;
        pagina_web = web;
    }

    //Metodos GET
    public int getID(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getUbicacion(){return this.ubicacion;}
    public int getImagen(){return this.imagen;}
    public String getHorario(){return this.horario;}
    public String getPagina_web(){return this.pagina_web;}
    public String getCampus(){return this.campus;}

    //Metodos SET
    public void setNombre(String nom){this.nombre=nom;}
    public void setUbicacion(String ubi){this.ubicacion=ubi;}
    public void setImagen(int ima){this.imagen=ima;}
    public void setHorario(String hor) {this.horario=hor;}
    public void setPagina_web(String web){this.pagina_web=web;}
    public void setCampus(String camp){this.campus=camp;}
}