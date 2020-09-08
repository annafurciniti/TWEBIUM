package dao;

import java.sql.*;
import java.util.ArrayList;


public class Model {

    private static String URL;
    private static String username;
    private static String password;

    public Model(String URL, String Username, String Password) {
        this.URL = URL;
        this.username = Username;
        this.password = Password;
        registerDriver();
    }

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**
     * UTENTI
     **/

    public static Utenti autenticazione(String username, String password) {
        Connection conn1 = null;
        Utenti u = null;
        try {
            conn1 = openConnection();
            username = "\"" + username + "\"";
            password = "\"" + password + "\"";
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `utenti` \n" +
                    "WHERE utenti.username = " + username + " AND utenti.password = " + password + ";");
            if (rs.next()) {
                u = new Utenti(rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getInt("Amministratore"));
            }
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return u;
    }

    // info degli utenti
    public static ArrayList<Utenti> getUtenti() {
        Connection conn1 = null;
        ArrayList<Utenti> out = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM utenti");
            while (rs.next()) {
                Utenti u = new Utenti(rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("amministratore"));
                out.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally{
            closeConnection(conn1);
        }
        return out;
    }

    //Inserisco Utente nel DB
    public static boolean InserisciUt(Utenti utenti) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Username = "\"" + utenti.getUsername() + "\"";
            String Password = "\"" + utenti.getPassword() + "\"";
            String Amministratore = "\"" + utenti.getIsAdmin() + "\"";
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO Utenti(Username, Password, Amministratore) VALUES (" + Username + "," + Password + "," + Amministratore + ")");
            System.out.println("L'utente: " + Username + " é stato inserito ");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //Rimuovo utente dal DB
    public static boolean RimuovereUt(Utenti utenti) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Username = "'" + utenti.getUsername() + "'";
            System.out.println("L'utente da rimuovere é: " + Username);

            Statement st = conn1.createStatement();
            st.executeQuery("DELETE FROM utenti WHERE utenti.username=" + Username + "");
            System.out.println("L'utente: " + Username + " é stato rimosso ");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    public static boolean utenteLibero(int giorno, int ora, String utente) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();
            String g = "'" + giorno + "'";
            String o = "'" + ora + "'";
            String u = "'" + utente + "'";

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ripetizioni WHERE Username = "+ u +" && Ora_i= "+ o +" && Giorno= "+ g +"  && Stato='prenotato'");
            if(rs.next())
                return false;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return true;
    }

    /**
     * DOCENTI
     **/

    public static ArrayList<Docenti> getDocenti() {
        Connection conn1 = null;
        ArrayList<Docenti> doc = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM docenti");
            while (rs1.next()) {
                Docenti d = new Docenti(rs1.getString("Nome"));
                doc.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return doc;
    }

    //Inseriamo nuovi docenti
    public static boolean InserisciDocenti(Docenti docente) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Nome = "\"" + docente.getNome() + "\"";
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * " +
                    "FROM `docenti` " +
                    "WHERE docenti.Nome=" + Nome +"");
            if (rs.isBeforeFirst()) {
                System.out.println("Già presente nel DB");
                return false;
            }
            st.executeUpdate("INSERT INTO Docenti (Nome) VALUE (" + Nome + ")");
            System.out.println("Docente: " + Nome + " aggiunto nel DB");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //Rimuovo Docenti
    public static boolean RimuoviDoc(Docenti docente) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Nome = "'" + docente.getNome() + "'";
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM docenti WHERE docenti.Nome=" +Nome+ "");
            System.out.println("Docente" + Nome + " rimosso.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    /**
     * Corsi
     **/

    public static ArrayList<Corso> getCorsi() {
        Connection conn1 = null;
        ArrayList<Corso> out2 = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st2 = conn1.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM corsi");
            while (rs2.next()) {
                Corso c = new Corso(rs2.getString("titolo"),rs2.getString("descrizione"));
                out2.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return out2;
    }

    //Aggiungo corsi nel DB
    public static boolean InserisciCorso(Corso corso) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();
            String titolo = "\"" + corso.getTitolo()+ "\"";
            String descr = "\"" + corso.getDescrizione()+ "\"";
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT corsi.Titolo " +
                    "FROM `corsi` " +
                    "WHERE corsi.Titolo = " + titolo + "");
            if (rs.isBeforeFirst()) {
                System.out.println("Già presente nel DB");
                return false;
            }
            st.executeUpdate("INSERT INTO corsi (titolo,descrizione) VALUE (" + titolo + "," + descr + ")");
            System.out.println("Corso: " + titolo + "é stato aggiunto nel DB.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database QUA: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //Rimuovi corso
    public static boolean RimuoviCorso(String titolo) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Titolo = "\"" + titolo + "\"";
            System.out.println("Il corso da rimuovere é: " + Titolo);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM corsi WHERE corsi.Titolo=" + Titolo + "");
            System.out.println("Corso: " + Titolo + "rimosso.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    /*update corso
    public static void ModificaCorso(Corso corso){//???
        Connection conn1 = null;
        try {
            conn1 = openConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE corsi Set Titolo = ? WHERE id_corso = ?");

        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
    }*/


    /**
     * Ripetizioni
     **/
    public static ArrayList<Ripetizioni> getRipetizioni() {
        Connection conn1 = null;
        ArrayList<Ripetizioni> out3 = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st3 = conn1.createStatement();
            ResultSet rs3 = st3.executeQuery("SELECT * FROM ripetizioni");
            while (rs3.next()) {
                Ripetizioni r = new Ripetizioni(rs3.getString("stato") , rs3.getInt("Giorno"), rs3.getInt("Ora_i")
                        , rs3.getString("ID_Corso"), rs3.getString("Id_docente"),rs3.getString("Username"));
                out3.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return out3;
    }

    public static boolean prenota(Ripetizioni ripetizioni) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();
            String corso = "'" + ripetizioni.getId_corso() + "'";
            String docente = "'" + ripetizioni.getId_docente() + "'";
            String utente = "'" + ripetizioni.getUsername() + "'";
            String ora = "'" + ripetizioni.getOra_i() + "'";
            String giorno = "'" + ripetizioni.getGiorno() + "'";
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE ripetizioni Set Username = "+utente+", Stato = 'prenotato' WHERE  id_corso= "+corso+" && id_docente= "+docente+" && Ora_i= "+ ora +" && Giorno= "+ giorno +" ");
            System.out.println("Ripetizione prenotata per "+ utente);
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //rimuovo ripetizioni
    public static void rimuoviRipetizioni(Ripetizioni ripetizioni){
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String giorno = "'" + ripetizioni.getGiorno() + "'";
            String id_docente = "'" + ripetizioni.getId_docente() + "'";
            String ora = "'" + ripetizioni.getOra_i() + "'";

            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM ripetizioni WHERE Giorno =" + giorno + " && id_docente =" + id_docente + " && Ora_i =" + ora + "");
            System.out.println("Ripetizione: rimossa.");
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
    }


    public static ArrayList<Ripetizioni> getRipDisponibili() {
        Connection conn1 = null;
        ArrayList<Ripetizioni> ripetizioni = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st = conn1.createStatement();
            ResultSet rs3 = st.executeQuery("SELECT * FROM ripetizioni WHERE username is NULL ");
            while (rs3.next()) {
                    Ripetizioni r = new Ripetizioni( rs3.getString("stato") , rs3.getInt("Giorno"), rs3.getInt("Ora_i"), rs3.getString("ID_Corso"), rs3.getString("Id_docente"),rs3.getString("Username"));
                    ripetizioni.add(r);
                }
            System.out.println("Ripetizioni disponibili");
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return ripetizioni;
    }

    public static ArrayList<Ripetizioni> getMieRip(String username) {
        Connection conn1 = null;
        ArrayList<Ripetizioni> ripetizioni = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st = conn1.createStatement();
            username = "'" + username + "'";
            //username = "'" + username + "'";
            ResultSet rs3 = st.executeQuery("SELECT * FROM ripetizioni WHERE ripetizioni.username=" + username);
            while (rs3.next()) {
                Ripetizioni r = new Ripetizioni( rs3.getString("stato") , rs3.getInt("Giorno"), rs3.getInt("Ora_i"), rs3.getString("ID_Corso"), rs3.getString("Id_docente"),rs3.getString("Username"));
                ripetizioni.add(r);
            }
            System.out.println("Ripetizioni di " + username);
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return ripetizioni;
    }

    public static boolean modificaStato(Ripetizioni rip, String stato) {
        Connection conn1 = null;

        try {
            conn1 = openConnection();
            Statement st = conn1.createStatement();
            stato = "'" + stato + "'";
            String docente = "'" + rip.getId_docente() + "'";
            String giorno = "'" + rip.getGiorno() + "'";
            String ora = "'" + rip.getOra_i() + "'";
            st.executeUpdate("UPDATE ripetizioni Set ripetizioni.stato = '"+stato+"' WHERE ripetizioni.id_docente = "+docente+" && " +
                    "ripetizioni.Ora_i = "+ora+" && ripetizioni.Giorno = "+giorno+"");
            System.out.println("RISULTATO UPDATE: " +stato);
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    /**
     *Insegnamenti*
     **/
    public static ArrayList<Insegnamenti> getInsegnamenti() {
        Connection conn1 = null;
        ArrayList<Insegnamenti> out = new ArrayList<>();
        try {
            conn1 = openConnection();
            Statement st2 = conn1.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM insegnamenti");
            while (rs2.next()) {
                Insegnamenti i = new Insegnamenti(rs2.getString("titolo"), rs2.getString("id_docente"));
                out.add(i);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                closeConnection(conn1); }
        } return out;
    }

    //Aggiungo insegnamenti nel DB public static boolean
    public static boolean InserisciInsegnamenti(Insegnamenti insegnamenti) {
        Connection conn1 = null;
        try { conn1 = openConnection();
            String titolo = "\"" + insegnamenti.getTitolo() + "\"";
            String id_docente = "\"" + insegnamenti.getId_docente() + "\"";
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT insegnamenti.titolo, insegnamenti.id_docente" + " FROM insegnamenti " + "WHERE insegnamenti.titolo =" + titolo + " AND insegnamenti.id_docente="+id_docente+"");
            if (rs.isBeforeFirst()) { System.out.println("Già presente nel DB");
                return false;
            }
            st.executeUpdate("INSERT INTO insegnamenti (titolo,id_docente) VALUE (" + titolo + "," + id_docente + ")");
            System.out.println("Insegnamento: " + titolo + "con docente " + id_docente + " aggiunto nel DB");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage()); }
        finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //rimuovo insegnamenti
    public static boolean RimuoviInsegnamenti(Insegnamenti insegnamenti) {
        Connection conn1 = null;
        try { conn1 = openConnection();
            String titolo = "'" + insegnamenti.getTitolo() + "'";
            String id_docente = "'" + insegnamenti.getId_docente() + "'";
            System.out.println("L'insegnamento da rimuovere é: " + titolo +"con docente" +id_docente);
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT insegnamenti.titolo, insegnamenti.id_docente" + " FROM insegnamenti " + "WHERE insegnamenti.titolo =" + titolo + " AND insegnamenti.id_docente="+id_docente+"");
            if (!rs.isBeforeFirst()) {
                System.out.println("Non é presente nel DB.");
                return false;
            }
            st.executeUpdate("DELETE FROM insegnamenti WHERE insegnamenti.titolo=" + titolo +" AND insegnamenti.id_docente= "+id_docente+"");
            System.out.println("L'insegnamento: " + titolo + "con docente"+id_docente+"rimosso.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //update insegnamenti
    public static void ModificaInsegnamenti(Insegnamenti insegnamenti){
        Connection conn1 = null;
        try { conn1 = openConnection();
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE insegnamenti Set titolo = ? WHERE id_docente = ?");
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
    }

    /**
     * Connection
     **/

    private static Connection openConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, username, password);
            if (conn != null) {
                System.out.println("Connected to the database tweb");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void closeConnection(Connection conn){
        try{
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}