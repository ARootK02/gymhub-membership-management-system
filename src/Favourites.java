/**
 * Represents a service saved in a user's favourites list.
 * It stores a copy of the service details together with the related user and company.
 */
public class Favourites extends Services {
    private String service_name;
    private float service_price;
    private String service_category;
    private int service_id;
    private int company_id;
    private int user_id;
    private String company_name;

    public Favourites() {
    }

    public Favourites(
            String service_name,
            float service_price,
            String service_category,
            int service_id,
            int company_id,
            int user_id) {

        this.service_name = service_name;
        this.service_price = service_price;
        this.service_category = service_category;
        this.service_id = service_id;
        this.company_id = company_id;
        this.user_id = user_id;
    }

    public String getservice_name() {
        return service_name;
    }

    public void setservice_name(String service_name) {
        this.service_name = service_name;
    }

    public float getservice_price() {
        return service_price;
    }

    public void setservice_price(float service_price) {
        this.service_price = service_price;
    }

    public String getservice_category() {
        return service_category;
    }

    public void setservice_category(String service_category) {
        this.service_category = service_category;
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