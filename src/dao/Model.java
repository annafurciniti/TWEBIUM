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
                        rs.getBoolean("Amministratore"));
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
                        rs.getBoolean("amministratore"));
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
            String Password = "'" + utenti.getPassword() + "'";
            System.out.println("L'utente da rimuovere é: " + Username + " " + Password);

            Statement st = conn1.createStatement();
            st.executeQuery("DELETE FROM utenti WHERE utenti.username=" + Username + "AND utenti.password= " + Password + "");
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
                Docenti d = new Docenti(rs1.getString("Nome"), rs1.getString("Cognome"), rs1.getInt("id_docente"));
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
            String Cognome = "\"" + docente.getCognome() + "\"";
            Statement st = conn1.createStatement();
            st.executeQuery("INSERT INTO Docenti (Nome,Cognome) VALUE (" + Nome + "," + Cognome + ")");
            System.out.println("Docente: " + Nome + " " + Cognome + " aggiunto nel DB");
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
    public static boolean RimuovereDoc(Docenti docente) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            String Nome = "'" + docente.getNome() + "'";
            String Cognome = "'" + docente.getCognome() + "'";
            System.out.println("Il docente da rimuovere é: " + Nome + " " + Cognome);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM docenti WHERE docenti.Nome=" + Nome + "AND docenti.Cognome= " + Cognome + "");
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

    /*public static ArrayList<Docenti> DisponibilitàDoc(String id_rip) throws SQLException {
        Connection conn1 = null;
        ArrayList<Docenti> docenti = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT docenti.id_docente FROM docenti join  ripetizioni.id_docente WHERE docenti.id_docente = ripetizioni.id_docente and ripetizioni.stato = 'disponibile' " + id_rip);
            while (rs.next()) {
                int id_docente = rs.getInt("id_docente");
                String Nome = rs.getString("Nome");
                String Cognome = rs.getString("Cognome");
                Docenti d = new Docenti(Nome, Cognome, id_docente);
                docenti.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return docenti;
    }*/

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
                Corso c = new Corso(rs2.getString("titolo"));
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
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT corsi.Titolo " +
                    "FROM `corsi` " +
                    "WHERE corsi.Titolo = " + titolo + "");
            if (rs.isBeforeFirst()) {
                System.out.println("Già presente nel DB");
                return false;
            }
            st.executeUpdate("INSERT INTO Corsi (titolo) VALUE ('" + titolo + "')");
            System.out.println("Corso: " + titolo + "é stato aggiunto nel DB.");
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

    //Rimuovi corso
    public static boolean RimuoviCorso(String titolo) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();

            //String Titolo = "\"" + corso.getTitolo() + "\"";
            //System.out.println("Il corso da rimuovere é: " + Titolo);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM corsi WHERE corsi.Titolo=" + titolo + "");
            System.out.println("Corso: " + titolo + "rimosso.");
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return false;
    }

    //update corso
    public static void ModificaCorso(Corso corso){
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
    }


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
                Ripetizioni r = new Ripetizioni(rs3.getInt("id_rip"),rs3.getString("stato") , rs3.getString("Giorno"), rs3.getInt("Ora_i"), +
                        + rs3.getInt("Ora_f"), rs3.getInt("ID_Corso"), rs3.getInt("Id_docente"),rs3.getString("Username"));
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

    //inserisco ripetizione
    public static boolean InserisciRipetizione(Ripetizioni ripetizioni) {
        Connection conn1 = null;
        try {
            conn1 = openConnection();
            String id_rip = "'" + ripetizioni.getId_rip() + "'";
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO Ripetizioni (id_rip) VALUE (" + id_rip + ")");
            System.out.println("Ripetizione: " + ripetizioni.getId_rip() + "é stato aggiunto nel DB.");
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

            String id_corso = "'" + ripetizioni.getId_corso() + "'";
            String id_docente = "'" + ripetizioni.getId_docente() + "'";
            String id_rip = "'" + ripetizioni.getId_rip() + "'";

            System.out.println("La ripetizione da rimuovere é: " + id_rip);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM ripetizioni WHERE ripetizioni.id_rip=" + id_rip + "");
            System.out.println("Ripetizione: " + id_rip + "rimossa.");
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
    }


    // Visualizzo la disponibilità delle ripetizioni
    public static ArrayList<Ripetizioni> DisponibilitaRip() {
        Connection conn1 = null;
        ArrayList<Ripetizioni> ripetizioni = new ArrayList<>();
        try {
            conn1 = openConnection();

            Statement st = conn1.createStatement();
            ResultSet rs3 = st.executeQuery("SELECT * FROM ripetizioni WHERE stato='disponibile' ");
            while (rs3.next()) {
                if (rs3.wasNull()) {
                    Ripetizioni r = new Ripetizioni(rs3.getInt("id_rip"), rs3.getString("stato") , rs3.getString("Giorno"), rs3.getInt("Ora_i"), rs3.getInt("Ora_f"), rs3.getInt("ID_Corso"), rs3.getInt("Id_docente"),rs3.getString("Username"));
                    ripetizioni.add(r);
                } else {
                    System.out.println("Non abbiamo ripetizioni disponibili");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error communicating with the database: " + e.getMessage());
        } finally {
            if (conn1 != null) {
                closeConnection(conn1);
            }
        }
        return ripetizioni;
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
                Insegnamenti i = new Insegnamenti(rs2.getInt("id_corso"), rs2.getInt("id_docente"));
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
            String id_corso = "\"" + insegnamenti.getId_corso() + "\"";
            String id_docente = "\"" + insegnamenti.getId_docente() + "\"";
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT Insegnamenti(ID_corso, ID_docenti) " + "FROM `insegnamenti` " + "WHERE insegnamenti.ID_corso AND insegnamenti.ID_docente  = " + id_corso + ", "+id_docente+"");
            if (rs.isBeforeFirst()) { System.out.println("Già presente nel DB");
                return false;
            }
            st.executeQuery("INSERT INTO Insegnamenti (id_corso,id_docente) VALUE (" + id_corso + "," + id_docente + ")"); System.out.println("Insegnamento: " + id_corso + " " + id_docente + " aggiunto nel DB");
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
            String id_corso = "'" + insegnamenti.getId_corso() + "'";
            String id_docente = "'" + insegnamenti.getId_docente() + "'";
            System.out.println("L'insegnamento da rimuovere é: " + id_corso +"con docente" +id_docente);
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT Insegnamenti(ID_corso, ID_docenti) " + "FROM `insegnamenti` " + "WHERE insegnamenti.ID_corso AND insegnamenti.ID_docente  = " + id_corso + ", "+id_docente+"");
            if (!rs.isBeforeFirst()) {
                System.out.println("Non é presente nel DB.");
                return false;
            } st.executeUpdate("DELETE FROM insegnamenti WHERE insegnamenti.id_corso AND insegnamenti.id_docente =" + id_corso + ","+id_docente+"");
            System.out.println("L'insegnamento: " + id_corso + "con docente"+id_docente+"rimosso.");
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
            st.executeUpdate("UPDATE insegnamenti Set id_corso = ? WHERE id_docente = ?");
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