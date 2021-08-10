package entity;

public class Admin {
    private int id_admin;
    private String phone;
    private String password;

    public Admin() {
    }

    public Admin(int id_admin, String phone, String password) {
        this.id_admin = id_admin;
        this.phone = phone;
        this.password = password;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
