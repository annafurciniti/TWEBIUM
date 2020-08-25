package controller;

import com.google.gson.Gson;
import dao.Model;
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

@WebServlet(name = "LoginServlet", urlPatterns ={"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        System.out.println(action);

        try(PrintWriter out = response.getWriter()) {
            switch (action){
                case "login":
                    out.print(login(request, response));
                    break;
                case "sing":
                    out.print(signIn(request, response));
                    break;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        String uJson;
        Gson gson = new Gson();

        Utenti u = Model.autenticazione(user,password);
        if(u!=null){ //login con successo
            uJson = "[" + gson.toJson(user) + "," + gson.toJson(u.getIsAdmin()) + "]";

            HttpSession s = request.getSession();
            s.setAttribute("username", u.getUsername());
            s.setAttribute("password", u.getPassword());
            s.setAttribute("role", u.getIsAdmin());//0 = user, 1 = admin

        }
        else{
            uJson = "[" + gson.toJson("errore") + "]";
        }
        return uJson;
    }

    private String signIn(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("funzione signin");
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        String uJson;
        Gson gson = new Gson();

        System.out.println("signin");
        Utenti u = new Utenti(user,password,false);
        boolean flag = Model.InserisciUt(u);
        if(flag){
            System.out.println("flag true");
            uJson = "[" + gson.toJson(user) + "]";
            HttpSession s = request.getSession();
            s.setAttribute("username", u.getUsername());
            s.setAttribute("password", u.getPassword());
            s.setAttribute("role", u.getIsAdmin());//0 user, 1 admin
        }
        else{
            System.out.println("flag false");
            uJson = "[" + gson.toJson("esiste") + "]";
        }
        return uJson;
    }

}
