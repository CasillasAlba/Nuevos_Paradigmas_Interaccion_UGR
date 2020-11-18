package npi.betweenstudents;

public class Campus {

    private int id;
    private String nombre;
    private int image;

    public Campus(int i, String nom, int ima) {
        this.id = i;
        this.nombre = nom;
        this.image = ima;
    }

    public  int getId(){return id;}
    public void setId(int i){this.id = i;}

    public int getImage() {
        return image;
    }

    public void setImage(int ima) {this.image = ima;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }
}
