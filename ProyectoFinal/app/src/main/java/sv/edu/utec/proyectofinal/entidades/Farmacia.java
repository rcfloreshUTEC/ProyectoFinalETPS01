package sv.edu.utec.proyectofinal.entidades;

public class Farmacia {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public boolean isEsActiva() {
        return esActiva;
    }

    public void setEsActiva(boolean esActiva) {
        this.esActiva = esActiva;
    }

    private int id;
    private String nombre;
    private int idMunicipio;
    private boolean esActiva;

    public Farmacia(int id, String nombre, int idMunicipio, boolean esActiva) {
        this.id = id;
        this.nombre = nombre;
        this.idMunicipio = idMunicipio;
        this.esActiva = esActiva;
    }

}
