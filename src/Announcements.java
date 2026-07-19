import java.util.Date;

/**
 * Represents an announcement published by a gym or company account.
 * The announcement stores its content, publication date, and related account identifiers.
 */
public class Announcements extends Services {
    private int ann_id;
    private String ann_title;
    private String ann_description;
    private int company_id;
    private Date ann_date;
    private String company_name;
    private int user_id;

    public Announcements() {
    }

    public Announcements(
            int ann_id,
            String ann_title,
            String ann_description,
            Date ann_date,
            int company_id,
            String company_name,
            int user_id) {

        this.ann_id = ann_id;
        this.ann_title = ann_title;
        this.ann_description = ann_description;
        this.ann_date = ann_date;
        this.company_id = company_id;
        this.company_name = company_name;
        this.user_id = user_id;
    }

    public int getann_id() {
        return ann_id;
    }

    public void setann_id(int ann_id) {
        this.ann_id = ann_id;
    }

    public String getann_title() {
        return ann_title;
    }

    public void setann_title(String ann_title) {
        this.ann_title = ann_title;
    }

    public String getann_description() {
        return ann_description;
    }

    public void setann_description(String ann_description) {
        this.ann_description = ann_description;
    }

    // Retained for compatibility with code using the original misspelled method name.
    public void setann_desription(String ann_description) {
        setann_description(ann_description);
    }

    public Date getann_date() {
        return ann_date;
    }

    public void setann_date(Date ann_date) {
        this.ann_date = ann_date;
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
}