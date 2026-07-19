/**
 * Represents a personal user account in the GymHub system.
 * It stores the user's identity, sign-in credentials, and optional contact details.
 */
public class User {
    private int user_id = 0;
    private String f_name;
    private String l_name;
    private String username;
    private String email;
    private String password;
    private String address;
    private String phone_number;

    public User() {
    }

    public User(
            int user_id,
            String f_name,
            String l_name,
            String username,
            String email,
            String password,
            String phone_number,
            String address) {

        this.user_id = user_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
    }

    public int getuser_id() {
        return user_id;
    }

    public void setuser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getfirstname() {
        return f_name;
    }

    public void setfirstname(String f_name) {
        this.f_name = f_name;
    }

    public String getlastname() {
        return l_name;
    }

    public void setlastname(String l_name) {
        this.l_name = l_name;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getphone_number() {
        return phone_number;
    }

    public void setphone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }
}