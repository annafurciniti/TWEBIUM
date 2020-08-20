package dao;

import java.util.ArrayList;

public class Inizio {
    public static void main(String[] args) {
        Model m = new Model("jdbc:mysql://localhost:3306/tweb", "root", "root");
        ArrayList<Utenti> utenti = m.getUtenti();
        for (Utenti u: utenti)
            System.out.println(u);
        System.out.println("FINE");

        ArrayList<Docenti> docenti = m.getDocenti();
        for (Docenti d: docenti)
            System.out.println(d);
        System.out.println("FINE");

        ArrayList<Corso> corso = m.getCorsi();
        for (Corso c: corso)
            System.out.println(c);
        System.out.println("FINE");

        ArrayList<Ripetizioni> ripetizioni = m.getRipetizioni();
        for (Ripetizioni r: ripetizioni)
            System.out.println(r);
        System.out.println("FINE");

        ArrayList<Insegnamenti> insegnamenti = m.getInsegnamenti();
        for (Insegnamenti i: insegnamenti)
            System.out.println(i);
        System.out.println("FINE");
    }
}