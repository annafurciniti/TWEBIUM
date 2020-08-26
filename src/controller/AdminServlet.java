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
          /*      case "NEWCORSO":
                    out.print(InserisciCorso(request, response));
                    break;
                case "RIMUOVICORSO":
                    out.print(rimuoviCorso(request, response));
                    break;
                case "SHOWDOCENTE":
                    out.print(getDocenti(request, response));
                    break;
                case "NEWDOCENTE":
                    out.print(InserisciDocenti(request, response));
                    break;
                case "RIMUOVIDOC":
                    out.print(rimuoviDocenti(request, response));
                    break;
                case "SHOWINSEGNAMENTI":
                    out.print(getInsegnamenti(request, response));
                    break;
                case "NEWINSEGNAMENTI":
                    out.print(InserisciInsegnamenti(request, response));
                    break;
                case "RIMUOVIINSEGNAMENTI":
                    out.print(rimuoviInsegnamenti(request, response));
                    break;*/
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
    /*AGGIUNGI CORSO
    private String InserisciCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String titolo = request.getParameter("titolo");
        Corso c = new Corso(titolo);
        Boolean add = Model.InserisciCorso(c);
        System.out.println("return:" + add);

        return gson.toJson(add);
    }
    /* RIMUOVI CORSO
    private String rimuoviCorso(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String Titolo = request.getParameter("titolo");
        Corso c = new Corso(Titolo);
        boolean add =Model.RimuoviCorso(c);
        return gson.toJson(add);
    }*/
    /*DOCENTI
    private String getDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        String add="[";
        ArrayList<Docenti> docenti = Model.getDocenti();
        add += "," + gson.toJson(docenti);
        return add;

    }*/
    /*AGGIUNGI DOCENTE
    private String InserisciDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String Nome = request.getParameter("nome");
        String Cognome =request.getParameter("cognome");
        Docenti d = new Docenti(Nome,Cognome);
        Boolean add = Model.InserisciDocenti(d);
        System.out.println("return:" + add);

        return gson.toJson(add);
    }*/
    /* RIMUOVI DOCENTE
    private String rimuoviDocenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        String Nome = request.getParameter("nome");
        String Cognome =request.getParameter("cognome");
        Docenti d = new Docenti(Nome,Cognome);
        boolean add =Model.RimuoviDoc(d);
        return gson.toJson(add);
    }*/
    /*INSEGNAMENTI
    private String getInsegnamenti(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        String add="[";
        ArrayList<Insegnamenti> insegnamenti = Model.getInsegnamenti();
        add += "," + gson.toJson(insegnamenti);
        return add;
    }*/

    /*AGGIUNGI INSEGNAMENTI
    private String InserisciInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente =Integer.parseInt(request.getParameter("id_docente"));
        Insegnamenti i = new Insegnamenti(id_corso,id_docente);
        Boolean add = Model.InserisciInsegnamenti(i);
        System.out.println("return:" + add);
        return gson.toJson(add);
    }*/
    /* RIMUOVI INSEGNAMENTI
    private String rimuoviInsegnamenti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson =new Gson();
        int id_corso = Integer.parseInt(request.getParameter("id_corso"));
        int id_docente =Integer.parseInt(request.getParameter("id_docente"));
        Insegnamenti i = new Insegnamenti(id_corso,id_docente);
        boolean delete =Model.RimuoviInsegnamenti(i);
        return gson.toJson(delete);
    }*/


}
