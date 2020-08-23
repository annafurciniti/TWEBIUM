package dao;

public class Corso {
    private String titolo;


    public Corso(String titolo) { this.titolo=titolo;
    }

    public String getTitolo() { return titolo; }


    public void setTitolo(String titolo) { this.titolo = titolo; }


    @Override
    public String toString() { return titolo ;}
}

