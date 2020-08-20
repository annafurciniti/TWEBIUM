package dao;

public class Insegnamenti{

    private int id_corso;
    private int id_docente;

    public Insegnamenti(int id_corso, int id_docente ) {
        this.id_corso = id_corso;
        this.id_docente= id_docente;
    }
    public int getId_corso() { return id_corso; }

    public void setId_corso(int id_corso) { this.id_corso = id_corso; }

    public int getId_docente() { return id_docente; }

    public void setId_docente(int id_docente) { this.id_docente = id_docente; }


    public String toString() {
        return "corso: " + id_corso + "docente: " + id_docente;
    }

}
