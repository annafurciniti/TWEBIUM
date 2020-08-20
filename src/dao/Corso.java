package dao;

public class Corso {
    private String titolo;
    private int id_corso;


    public Corso(String titolo, int id_corso) {
        this.titolo = titolo;
        this.id_corso = id_corso;
    }

    public Corso(String titolo) { titolo=titolo;
    }

    public String getTitolo() { return titolo; }
    public int getId_corso() { return id_corso; }


    public void setTitolo(String titolo) { this.titolo = titolo; }
    public void setId_corso(Integer id_corso) { this.id_corso = id_corso; }


    @Override
    public String toString() { return titolo + " " + id_corso;}
}

