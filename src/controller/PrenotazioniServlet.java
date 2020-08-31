package controller;

import com.google.gson.Gson;
import dao.Model;
import dao.Ripetizioni;
import dao.Utenti;

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

        ArrayList<Ripetizioni> ripetizioni = Model.getRipetizioni();
        ArrayList<Ripetizioni> ripDisponibili = Model.DisponibilitaRip();


        for (Ripetizioni rip: ripetizioni){
            switch(rip.getStato()) {
                case "disponibile":
                    ripDisponibili.add(rip);
            }
        }

        if (!s.isNew()) {
            //sessione utente attiva
            if (s.getAttribute("username") != null) {
                u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));
                json += gson.toJson(true) + "," + gson.toJson(u) + "," + gson.toJson(ripetizioni) + "," + gson.toJson(ripDisponibili) + "]";
            }
        }
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
