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

@WebServlet(name = "AdminServlet", urlPatterns ={"/AdminServlet"})
public class AdminServlet extends HttpServlet {
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
        try(PrintWriter out = response.getWriter()) {
            switch (action) {
                case "INIT":
                   out.print(mioInit(request, response));
                    break;
                case "NEWCORSO":
                    out.print(InserisciCorso(request, response));
                    break;
                case "DELETECORSO":
                    out.print(rimuoviCorso(request, response));
                    break;
                case "NEWDOCENTE":
                    out.print(InserisciDocenti(request, response));
                    break;
                case "DELETEDOCENTE":
                    out.print(rimuoviDocenti(request, response));
                    break;
                case "NEWINSEGNAMENTI":
                    out.print(InserisciInsegnamenti(request, response));
                    break;
                 case "DELETEINSEGNAMENTI":
                    out.print(rimuoviInsegnamenti(request, response));
                    break;
            }
        }catch (IOException e) {
            System.out.println(e);
        }

    }

    /*CORSI*/
    private String mioInit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Corso> cor = Model.getCorsi();
        ArrayList<Insegnamenti> ins = Model.getInsegnamenti();
        ArrayList<Docenti> doc = Model.getDocenti();
        ArrayList<Ripetizioni> rip = Model.getRipetizioni();
        Gson gson = new Gson();
        String add = "[" + gson.toJson(rip) + "," + gson.toJson(cor) + "," + gson.toJson(doc) + "," + gson.toJson(ins) + "]";
        return add;

    }
    /*AGGIUNGI CORSO*/
    private String InserisciCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String titolo = request.getParameter("titolo");
        String desc = request.getParameter("desc");
        if(titolo != null && desc!= null){
            Corso c = new Corso(titolo,desc);
            Boolean add = Model.InserisciCorso(c);
            System.out.println("return:" + add);

            return gson.toJson(add);
        }
        else
            return gson.toJson("nullo");
    }
    /* RIMUOVI CORSO*/
    private String rimuoviCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String titolo = request.getParameter("titolo");
        boolean add = Model.RimuoviCorso(titolo);
        return gson.toJson(add);
    }
    /*AGGIUNGI DOCENTE*/
    private String InserisciDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        if(nome != null && cognome != null){
            nome += " "+ cognome;
            Docenti d = new Docenti(nome);
            boolean add = Model.InserisciDocenti(d);
            System.out.println("return:" + add);
            return gson.toJson(add);
        }
        else
            return gson.toJson("nullo");
    }
    /* RIMUOVI DOCENTE*/
    private String rimuoviDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String Nome = request.getParameter("nome");
        Docenti d = new Docenti(Nome);
        boolean delete = Model.RimuoviDoc(d);
        return gson.toJson(delete);
    }

    /*AGGIUNGI INSEGNAMENTI*/
    private String InserisciInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String titolo = request.getParameter("titolo");
        String id_docente = request.getParameter("id_docente");
        if(titolo!= null && id_docente != null && !titolo.equals("") && !id_docente.equals("")){
            Insegnamenti i = new Insegnamenti(titolo,id_docente);
            Boolean add = Model.InserisciInsegnamenti(i);
            System.out.println("return:" + add);
            return gson.toJson(add);}
        else
            return  gson.toJson("nullo");
    }
    /* RIMUOVI INSEGNAMENTI*/
    private String rimuoviInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String titolo = request.getParameter("titolo");
        String id_docente =request.getParameter("id_docente");
        Insegnamenti i = new Insegnamenti(titolo,id_docente);
        boolean delete =Model.RimuoviInsegnamenti(i);
        System.out.println("AdminServlet/riumuoviInsegnamenti; delete-> " + delete);
        return gson.toJson(delete);
    }


}
