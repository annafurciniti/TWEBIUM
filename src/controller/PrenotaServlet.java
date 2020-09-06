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
import java.util.Iterator;

@WebServlet(name = "PrenotaServlet", urlPatterns ={"/PrenotaServlet"})
public class PrenotaServlet extends HttpServlet {

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter(" user");
        new Model("jdbc:mysql://localhost:3306/tweb", "root", "root");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);

        try (PrintWriter out = response.getWriter()) {
            switch (action) {
                case "INIT":
                    out.println(this.mioinit(request, response));
                    break;
                case "DOC":
                    out.println(this.getDoc(request, response));
                    break;
                case "RIP":
                    out.println(this.getRip(request, response));
                    break;
                case "PRENOTA":
                    out.println(this.prenota(request, response));
                    break;
            }
        }
    }

    private String mioinit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Gson gson = new Gson();
        String json ="[";


        ArrayList<Corso> cor = Model.getCorsi();
        if (!s.isNew()) {
            //sessione utente attiva
            if (s.getAttribute("username") != null) {
                Utenti u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));
                json += gson.toJson(true) + "," + gson.toJson(u) + "," + gson.toJson(cor) + "]";
            }
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }

    private String getDoc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Gson gson = new Gson();
        String json ="[";


        if (!s.isNew()) {
            //sessione utente attiva
            String corso = request.getParameter("corso");
            System.out.println(corso);
            ArrayList<Insegnamenti> ins = Model.getInsegnamenti();
            ArrayList<Docenti> doc = Model.getDocenti();

            for (Iterator<Insegnamenti> insIterator = ins.iterator(); insIterator.hasNext();) {
                Insegnamenti i = insIterator.next();
                if (!(i.getTitolo().equals(corso))) {
                    insIterator.remove();
                }
            }

            ArrayList<Docenti> ret = new ArrayList<Docenti>();
            for(Insegnamenti i: ins){
                for(Iterator<Docenti> docIterator = doc.iterator(); docIterator.hasNext();){
                    Docenti d = docIterator.next();
                    if(i.getId_docente() == d.getId_docente())
                        ret.add(d);
                }
            }
            json += gson.toJson(true) + "," + gson.toJson(ret) + "]";
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }

    private String getRip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Gson gson = new Gson();
        String json ="[";


        if (!s.isNew()) {
            //sessione utente attiva
            int docente = Integer.parseInt(request.getParameter("doc"));
            String corso = request.getParameter("corso");
            ArrayList<Ripetizioni> rip = Model.getRipDisponibili();

            boolean[][] godisp = new boolean[5][4];
            for(int i=0; i<5; i++){
                for(int j=0; j<4; j++){
                    godisp[i][j]= false;
                }
            }

            boolean vuoto= true;
            for(Iterator<Ripetizioni> ripIterator = rip.iterator(); ripIterator.hasNext();){
                Ripetizioni r = ripIterator.next();
                if(r.getId_docente() == docente && (r.getId_corso().equals(corso))){
                    godisp[r.getGiorno()-1][r.getOra_i()-15] = true;
                    vuoto=false;
                }
            }
            if(vuoto)
                json +=  gson.toJson("vuoto")+ "]";
            else
                json += gson.toJson(true) + "," + gson.toJson(godisp) + "]";
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }

    private String prenota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Gson gson = new Gson();
        String json;


        if (!s.isNew()) {
            //sessione utente attiva
            int docente = Integer.parseInt(request.getParameter("doc"));
            int ora = Integer.parseInt(request.getParameter("ora")) + 15;
            int giorno = Integer.parseInt(request.getParameter("giorno")) + 1;
            String corso = request.getParameter("corso");

            System.out.println("id_docente: "+docente+" ,corso: "+corso+" , giorno: "+ giorno+" , ora: "+ora);
            if(Model.utenteLibero(giorno, ora,(String) s.getAttribute("username"))){
                boolean res = Model.prenota(new Ripetizioni("",giorno,ora,corso,docente,(String) s.getAttribute("username")));
                if(res)//prenotazione andata a buon fine
                    json = "[" + gson.toJson(true) + "]";
                else
                    json = "["+gson.toJson("errore")+"]";
            }
            else{//l'utente ha già una prenotazione a quell'ora di quel giorno
                json = "["+gson.toJson("occupato")+"]";
            }

        }
        else{
            json = "[" + gson.toJson(false) + "]";
        }
        return json;
    }
}
