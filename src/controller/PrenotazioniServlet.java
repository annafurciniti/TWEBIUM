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
import java.util.Iterator;

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
                case "STATO":
                    out.print(modificaStato(request, response));
                    break;
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
        String c = request.getParameter("caseMobile");


        if (!s.isNew()) {
            u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));

            Ripetizioni[][] goPren = new Ripetizioni[5][4];
            ArrayList<Ripetizioni> goPrenApp =new ArrayList<>();

            Ripetizioni[][] goDisd = new Ripetizioni[5][4];
            ArrayList<Ripetizioni> goDisdApp =new ArrayList<>();

            Ripetizioni[][] goSvol = new Ripetizioni[5][4];
            ArrayList<Ripetizioni> goSvolApp =new ArrayList<>();

            String username = (String) s.getAttribute("username");
            ArrayList<Ripetizioni> rip = Model.getMieRip(username);
            String pAttive, pSvolte, pDisdette;

            if(c==null){
                for(Iterator<Ripetizioni> ripIterator = rip.iterator(); ripIterator.hasNext();){
                    Ripetizioni r = ripIterator.next();
                    if(r.getStato().equals("prenotato") ){
                        goPren[r.getGiorno()-1][r.getOra_i()-15] = r;
                    }
                    else if(r.getStato().equals("svolto"))
                        goSvol[r.getGiorno()-1][r.getOra_i()-15] = r;
                    else
                        goDisd[r.getGiorno()-1][r.getOra_i()-15] = r;
                }

                pAttive = gson.toJson(goPren);
                pSvolte = gson.toJson(goSvol);
                pDisdette = gson.toJson(goDisd);

            }
            else{ //android
                for(Iterator<Ripetizioni> ripIterator = rip.iterator(); ripIterator.hasNext();){
                    Ripetizioni r = ripIterator.next();
                    if(r.getStato().equals("prenotato"))
                        goPrenApp.add(r);
                    else if(r.getStato().equals("svolto"))
                        goSvolApp.add(r);
                    else
                        goDisdApp.add(r);
                }

                pAttive = gson.toJson(goPrenApp);
                pSvolte = gson.toJson(goSvolApp);
                pDisdette = gson.toJson(goDisdApp);
            }


            json += gson.toJson(true) + "," + gson.toJson(u) + "," + pAttive + "," + pSvolte +  "," + pDisdette + "]";
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }

    private String modificaStato(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession s = request.getSession();
        Utenti u;
        Gson gson = new Gson();
        String json ="[";


        if (!s.isNew()) {
            u = new Utenti((String) s.getAttribute("username"), (String) s.getAttribute("password"), (int) s.getAttribute("role"));
            String docente = request.getParameter("docente");
            int ora = Integer.parseInt(request.getParameter("ora"));
            int giorno = Integer.parseInt(request.getParameter("giorno"));
            String stato = request.getParameter("stato");
            System.out.println(docente + " " + giorno + " " + ora + " " + stato);

            boolean x = Model.modificaStato(new Ripetizioni("",giorno,ora,"",docente,(String) s.getAttribute("username")),stato);
            System.out.println("X è: " + x);
            json += gson.toJson(x) + "]";
        }
        else{
            json += gson.toJson(false) + "]";
        }
        return json;
    }
}
