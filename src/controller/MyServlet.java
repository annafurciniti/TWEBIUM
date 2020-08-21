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
        m = new Model("jdbc:mysql://localhost:3306/tweb", "root", "");
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
        }
    }


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

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println("Invalidata la sessione");
    }

    private void getCorsi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String titolo = request.getParameter("titolo");
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        Gson gson = new Gson();
        String jsonCorso;
        ArrayList<Corso> add = Model.getCorsi();
        try (PrintWriter out = response.getWriter()) {
            out.print(add);
        }
    }

    private void inserisciCorso(HttpServletRequest request, HttpServletResponse response) throws IOException { //lo vedi il mio commit
        HttpSession session = request.getSession();
        String titolo = request.getParameter("titolo");
        Gson gson = new Gson();
        String jsonCorso;
        Boolean add = Model.InserisciCorso(new Corso(titolo));
        try (PrintWriter out = response.getWriter()) {
            out.print(add);
        }
    }

    private void getInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente = Integer.parseInt(request.getParameter("id_docente"));
        Gson gson = new Gson();
        String jsonInsegnamenti;
        ArrayList<Insegnamenti> add = Model.getInsegnamenti();
        try (PrintWriter out = response.getWriter()) {
            out.print(add);
        }
    }
    private void inserisciInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente = Integer.parseInt(request.getParameter("id_docente"));
        Gson gson = new Gson();
        String jsonInsegnamenti;
        Boolean add = Model.InserisciInsegnamenti(new Insegnamenti(id_corso,id_docente));
        try (PrintWriter out = response.getWriter()) {
            out.print(add);
        }
    }

    private void rimuoviInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente = Integer.parseInt(request.getParameter("id_docente"));
        Gson gson = new Gson();
        String jsonInsegnamenti;
        Boolean rem = Model.RimuoviInsegnamenti(new Insegnamenti(id_corso,id_docente));
        try (PrintWriter out = response.getWriter()) {
            out.print(rem);
        }
    }
}
