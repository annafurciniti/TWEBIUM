package controller;

import com.google.gson.Gson;
import dao.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "MyServlet", urlPatterns ={"/MyServlet"})
public class MyServlet extends HttpServlet {
    Model m = null;

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter(" user");
        //String pwd= ctx.getInitParameter(" pwd");
        //m = new Model(url, user, "root"); //problema probabilmente con conf
        m = new Model("jdbc:mysql://localhost:3306/tweb", "root", "root");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String add;
        switch (action) {
            case "LOGIN":
                this.login(request, response);
                break;
            case "SIGNIN":
                this.signin(request, response);
                break;
            case "LOGOUT":
                this.logout(request, response);
                break;
            case "NEWCORSO":
                this.InserisciCorso(request,response);
                break;
            case "RIMUOVICORSO":
                this.rimuoviCorso(request,response);
                break;
            case "SHOWDOCENTE":
                this.getCorsi(request,response);
                break;
            case "NEWDOCENTE":
                this.InserisciDocenti(request,response);
                break;
            case "RIMUOVIDOC":
                this.rimuoviDocenti(request,response);
                break;
            case "SHOWINSEGNAMENTI":
                this.getInsegnamenti(request,response);
                break;
            case "NEWINSEGNAMENTI":
                this.InserisciInsegnamenti(request,response);
                break;
            case "RIMUOVIINSEGNAMENTI":
                this.rimuoviInsegnamenti(request,response);
                break;
        }
    }

    /*LOGIN*/
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException { //commit
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String sessionID = request.getParameter("sessione");

            HttpSession s = request.getSession();
            String jsessionID = s.getId();

            Gson gson = new Gson();
            String jsonUtente;
            if (sessionID != null && jsessionID.equals(sessionID)) {
                System.out.println("sessione riconosciuta!");
            }

            Utenti u = Model.autenticazione(username, password);

            if (u != null) { //utente riconosciuto
                jsonUtente = gson.toJson(u);
                s.setAttribute("username", username);
                ArrayList<Ripetizioni> ripetizioni = Model.getRipetizioni();
                String bothJson = "[" + jsonUtente + "," + gson.toJson(jsessionID) + "," + gson.toJson(ripetizioni) + "]";
                out.print(bothJson);
            } else { //utente non riconosciuto
                jsonUtente = gson.toJson(new Utenti("", "", false));
                out.print("[" + jsonUtente + "," + gson.toJson(jsessionID) + "]");
            }
        }
    }
    /*SIGNIN*/
    private void signin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean amministratore = Boolean.parseBoolean(request.getParameter("amministratore"));

        String sessionID = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String jsessionID = s.getId();

        Gson gson = new Gson();
        String jsonUtente;
        if (sessionID != null && jsessionID.equals(sessionID)) {
            System.out.println("sessione riconosciuta!");
        }

        Utenti u = new Utenti(username, password, amministratore);
        boolean ok = Model.InserisciUt(u);
        ArrayList<Ripetizioni> ripetizioni;

        if (ok && jsessionID.equals(sessionID)) { //utente creato con sessione
            s.setAttribute("username", username);
            ripetizioni = Model.getRipetizioni();
            String bothJson = "[" + gson.toJson(u) + "," + gson.toJson(jsessionID) + "," + gson.toJson(ripetizioni) + "]";
            out.print(bothJson);
        } else if (ok) {//utente creato senza sessione
            ripetizioni = Model.getRipetizioni();
            String bothJson = "[" + gson.toJson(u) + "," + gson.toJson(jsessionID) + "," + gson.toJson(ripetizioni) + "]";
            out.print(bothJson);
        } else {//utente non creato
            out.print("[" + gson.toJson(new Utenti("", "", false)) + "," + gson.toJson(jsessionID) + "]");
        }
    }
    /*LOGOUT*/
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println("Invalidata la sessione");
    }
    /*CORSI*/
    private void getCorsi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        String add="[";
        ArrayList<Corso> corsi = Model.getCorsi();

        add += "," + gson.toJson(corsi);

    }
    /*AGGIUNGI CORSO*/
    private String InserisciCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String titolo = request.getParameter("titolo");
        Corso c = new Corso(titolo);
        Boolean add = Model.InserisciCorso(c);
        System.out.println("return:" + add);

        return gson.toJson(add);
    }
    /* RIMUOVI CORSO*/
    private String rimuoviCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String Titolo = request.getParameter("titolo");
        Corso c = new Corso(Titolo);
        boolean add =Model.RimuoviCorso(c);
        return gson.toJson(add);
    }
    /*DOCENTI*/
    private void getDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        String add="[";
        ArrayList<Docenti> docenti = Model.getDocenti();
        add += "," + gson.toJson(docenti);

    }
    /*AGGIUNGI DOCENTE*/
    private String InserisciDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String Nome = request.getParameter("nome");
        String Cognome =request.getParameter("cognome");
        Docenti d = new Docenti(Nome,Cognome);
        Boolean add = Model.InserisciDocenti(d);
        System.out.println("return:" + add);

        return gson.toJson(add);
    }
    /* RIMUOVI DOCENTE*/
    private String rimuoviDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String Nome = request.getParameter("nome");
        String Cognome =request.getParameter("cognome");
        Docenti d = new Docenti(Nome,Cognome);
        boolean add =Model.RimuoviDoc(d);
        return gson.toJson(add);
    }
    /*INSEGNAMENTI*/
    private void getInsegnamenti(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        String add="[";
        ArrayList<Insegnamenti> insegnamenti = Model.getInsegnamenti();
        add += "," + gson.toJson(insegnamenti);
    }

    /*AGGIUNGI INSEGNAMENTI*/
    private String InserisciInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente =Integer.parseInt(request.getParameter("id_docente"));
        Insegnamenti i = new Insegnamenti(id_corso,id_docente);
        Boolean add = Model.InserisciInsegnamenti(i);
        System.out.println("return:" + add);
        return gson.toJson(add);
    }
    /* RIMUOVI INSEGNAMENTI*/
    private String rimuoviInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente =Integer.parseInt(request.getParameter("id_docente"));
        Insegnamenti i = new Insegnamenti(id_corso,id_docente);
        boolean delete =Model.RimuoviInsegnamenti(i);
        return gson.toJson(delete);
    }


}
