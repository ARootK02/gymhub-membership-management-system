/**
 * Represents a completed service purchase made by a user.
 * It stores a snapshot of the purchased service details for subscription history.
 */
public class Order {
    private int company_id;
    private int service_id;
    private String service_name;
    private float service_price;
    private String service_category;
    private int order_id = 0;
    private int user_id;

    public Order(int company_id, int user_id) {
        this.user_id = user_id;
        this.company_id = company_id;
    }

    public Order(
            int company_id,
            int service_id,
            String service_name,
            float service_price,
            String service_category,
            int order_id,
            int user_id) {

        this.company_id = company_id;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.service_category = service_category;
        this.order_id = order_id;
        this.user_id = user_id;
    }

    public int getcompany_id() {
        return company_id;
    }

    public void setcompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getservice_id() {
        return service_id;
    }

    public void setservice_id(int service_id) {
        this.service_id = service_id;
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

    public int getorder_id() {
        return order_id;
    }

    public void setorder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getuser_id() {
        return user_id;
    }

    public void setuser_id(int user_id) {
        this.user_id = user_id;
    }
}