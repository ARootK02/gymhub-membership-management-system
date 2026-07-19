/**
 * Represents a gym associated with a user after a completed service purchase.
 * It stores the user, gym, and related service identifiers.
 */
public class My_Gyms {
    private int company_id;
    private int user_id;
    private int service_id;
    private String company_name;

    public My_Gyms() {
    }

    public My_Gyms(
            int company_id,
            String company_name,
            int user_id,
            int service_id) {

        this.company_id = company_id;
        this.company_name = company_name;
        this.user_id = user_id;
        this.service_id = service_id;
    }

    public int getcompany_id() {
        return company_id;
    }

    public void setcompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getuser_id() {
        return user_id;
    }

    public void setuser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getservice_id() {
        return service_id;
    }

    public void setservice_id(int service_id) {
        this.service_id = service_id;
    }
}