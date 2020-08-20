package dao;

public class Utenti {
    private String username;
    private String password;
    private boolean isAdmin;

    public Utenti( String username, String password, boolean ruolo) {

        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getIsAdmin() {
        if(isAdmin)
            return 1;
        else
            return 0;
    }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public String toString() {
        return username + " " + password + " " + isAdmin;
    }
}
