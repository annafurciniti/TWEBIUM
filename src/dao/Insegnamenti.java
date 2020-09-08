package dao;

public class Insegnamenti{


    private String titolo;;
    private String id_docente;

    public Insegnamenti(String titolo, String id_docente ) {
        this.titolo = titolo;
        this.id_docente= id_docente;
    }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getId_docente() { return id_docente; }
    public void setId_docente(String id_docente) { this.id_docente = id_docente; }

    public String toString() {
        return "corso: " + titolo + " docente: " + id_docente;
    }

}
