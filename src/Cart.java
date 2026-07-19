/**
 * Represents a service that a user has added to their shopping cart.
 * It links the selected service with the user and the company providing it.
 */
public class Cart {
    private int service_id;
    private int company_id;
    private float service_price;
    private int user_id;
    private String service_name;
    private String company_name;

    public Cart() {
    }

    public Cart(int service_id, int company_id, int user_id, float service_price) {
        this.service_id = service_id;
        this.company_id = company_id;
        this.user_id = user_id;
        this.service_price = service_price;
    }

    public float getprice() {
        return service_price;
    }

    public void setprice(float service_price) {
        this.service_price = service_price;
    }

    public String getservice_name() {
        return service_name;
    }

    public void setservice_name(String service_name) {
        this.service_name = service_name;
    }

    public int getservice_id() {
        return service_id;
    }

    public void setservice_id(int service_id) {
        this.service_id = service_id;
    }

    public int getcompany_id() {
        return company_id;
    }

    public void setcompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getuser_id() {
        return user_id;
    }

    public void setuser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name = company_name;
    }
}