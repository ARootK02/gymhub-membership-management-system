/**
 * Represents a digital membership card created for a user after purchasing
 * a service from a specific gym.
 */
public class My_Codes extends User {
    private String card_number;
    private int company_id;
    private int user_id;

    public My_Codes() {
    }

    public My_Codes(String card_number, int company_id, int user_id) {
        this.card_number = card_number;
        this.company_id = company_id;
        this.user_id = user_id;
    }

    public String getcard_number() {
        return card_number;
    }

    public void setcard_number(String card_number) {
        this.card_number = card_number;
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
}