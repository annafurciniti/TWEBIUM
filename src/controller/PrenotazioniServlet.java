package controller;

import com.google.gson.Gson;
import dao.Model;
import dao.Ripetizioni;
import dao.Utenti;

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

@WebServlet(name = "PrenotazioniServlet", urlPatterns ={"/PrenotazioniServlet"})
public class PrenotazioniServlet extends HttpServlet {

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter(" user");
        //String pwd= ctx.getInitParameter(" pwd");
        //m = new Model(url, user, "root"); //problema probabilmente con conf
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

        try(PrintWriter out = response.getWriter()) {
            switch (action){
                case "INIT":
                    out.print(mioinit(request, response));
                    break;
                /*case "DISPONIBILE":
                    out.print(statoDisponibile(request, response));
                    break;*/
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String mioinit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Utenti u;
        Gson gson = new Gson();
        String json ="[";


        if (!s.isNew()) {
                ArrayList<Ripetizioni> mieRip = Model.MieRip((String) s.getAttribute("username"));
                u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));
                json += gson.toJson(true) + "," + gson.toJson(u) + "," + gson.toJson(mieRip) + "]";}
        else{
            json += gson.toJson(false) + "]";
        }

        return json;
        }


    /*private String statoDisponibile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Utenti u;
        Gson gson = new Gson();
        String json ="[";

        ArrayList<Ripetizioni> ripetizioni = Model.DisponibilitaRip();
        if (!s.isNew()) {
            //sessione utente attiva
            if (s.getAttribute("username") != null) {
                u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));
                json += gson.toJson(true) + "," + gson.toJson(u) + "," + gson.toJson(ripetizioni) + "]";
            }
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }*/
}
