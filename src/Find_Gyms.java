import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores the application's in-memory data and provides operations for accounts,
 * services, favourites, carts, orders, digital cards, gyms, and announcements.
 *
 * <p>All data is temporary and is lost when the application terminates.</p>
 */
public class Find_Gyms {
    String name = "Find Gym";
    User user;
    Company_User c_user;
    Services serv;

    // Central in-memory collections shared by the application's menu workflows.
    ArrayList<Services> servicesList = new ArrayList<>();
    ArrayList<Company_User> companyUserList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();
    ArrayList<String> companyNameList = new ArrayList<>();
    ArrayList<String> serviceCategoryList = new ArrayList<>();
    ArrayList<String> serviceNameList = new ArrayList<>();
    ArrayList<Favourites> favouritesList = new ArrayList<>();
    ArrayList<Cart> cartList = new ArrayList<>();
    ArrayList<My_Codes> digitalCardList = new ArrayList<>();
    ArrayList<My_Gyms> MyGymsList = new ArrayList<>();
    ArrayList<Order> ordersList = new ArrayList<>();
    ArrayList<Announcements> announcementsList = new ArrayList<>();

    public int addcompany(Company_User company) {
        for (Company_User existingCompany : companyUserList) {
            if (existingCompany.getcompany_name().equalsIgnoreCase(company.getcompany_name())) {
                System.out.println("Company name already exists.");
                return 0;
            }
            if (existingCompany.getemail().equalsIgnoreCase(company.getemail())) {
                System.out.println("Company email already exists.");
                return 0;
            }
        }

        // Personal usernames and company names share the same sign-in namespace.
        for (User existingUser : userList) {
            if (existingUser.getusername().equalsIgnoreCase(company.getcompany_name())) {
                System.out.println("That account name is already used by a personal account.");
                return 0;
            }
        }

        companyUserList.add(company);
        System.out.println("Company added successfully.");
        return 1;
    }

    public void addservice(Services service) {
        // A gym cannot publish two services with the same name.
        for (Services existingService : servicesList) {
            if (existingService.getcompany_id() == service.getcompany_id()
                    && existingService.getservice_name().equalsIgnoreCase(service.getservice_name())) {
                System.out.println("Service already exists for this gym.");
                return;
            }
        }

        servicesList.add(service);
        System.out.println("Service added successfully.");
    }

    public int adduser(User user) {
        for (User existingUser : userList) {
            if (existingUser.getusername().equalsIgnoreCase(user.getusername())) {
                System.out.println("Username already exists.");
                return 0;
            }
            if (existingUser.getemail().equalsIgnoreCase(user.getemail())) {
                System.out.println("Email already exists.");
                return 0;
            }
        }

        // Personal usernames and company names share the same sign-in namespace.
        for (Company_User existingCompany : companyUserList) {
            if (existingCompany.getcompany_name().equalsIgnoreCase(user.getusername())) {
                System.out.println("That account name is already used by a company account.");
                return 0;
            }
        }

        userList.add(user);
        System.out.println("User added successfully.");
        return 1;
    }

    public boolean isUsernameAvailable(String username, int currentUserId) {
        for (User existingUser : userList) {
            if (existingUser.getuser_id() != currentUserId
                    && existingUser.getusername().equalsIgnoreCase(username)) {
                return false;
            }
        }

        // A personal username must also be different from every company name.
        for (Company_User existingCompany : companyUserList) {
            if (existingCompany.getcompany_name().equalsIgnoreCase(username)) {
                return false;
            }
        }

        return true;
    }

    public boolean isUserEmailAvailable(String email, int currentUserId) {
        for (User existingUser : userList) {
            if (existingUser.getuser_id() != currentUserId
                    && existingUser.getemail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCompanyNameAvailable(String companyName, int currentCompanyId) {
        for (Company_User existingCompany : companyUserList) {
            if (existingCompany.getcompany_id() != currentCompanyId
                    && existingCompany.getcompany_name().equalsIgnoreCase(companyName)) {
                return false;
            }
        }

        // A company name must also be different from every personal username.
        for (User existingUser : userList) {
            if (existingUser.getusername().equalsIgnoreCase(companyName)) {
                return false;
            }
        }

        return true;
    }

    public boolean isCompanyEmailAvailable(String email, int currentCompanyId) {
        for (Company_User existingCompany : companyUserList) {
            if (existingCompany.getcompany_id() != currentCompanyId
                    && existingCompany.getemail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    public void showCategories() {
        // LinkedHashSet removes duplicates while preserving insertion order.
        Set<String> categories = new LinkedHashSet<>();
        for (Services service : servicesList) {
            categories.add(service.getservice_category());
        }

        if (categories.isEmpty()) {
            System.out.println("No service categories are available.");
            return;
        }

        System.out.println("Available service categories:");
        for (String category : categories) {
            System.out.println("- " + category);
        }
    }

    public boolean showCategoryServices(String category) {
        boolean found = false;
        System.out.println("Available services:");
        for (Services service : servicesList) {
            if (service.getservice_category().equalsIgnoreCase(category)) {
                System.out.println("- " + service.getservice_name() + " - " + service.getcompany_name());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No services were found in this category.");
        }
        return found;
    }

    /**
     * Searches independently by service name, category, and company name.
     * Multiple matches may be printed when the query exists in more than one group.
     */
    public boolean search(String query) {
        boolean found = false;

        for (Services service : servicesList) {
            if (service.getservice_name().equalsIgnoreCase(query)) {
                printService(service);
                found = true;
            }
        }

        for (Services service : servicesList) {
            if (service.getservice_category().equalsIgnoreCase(query)) {
                System.out.println("- " + service.getservice_name() + " - " + service.getcompany_name());
                found = true;
            }
        }

        for (Company_User company : companyUserList) {
            if (company.getcompany_name().equalsIgnoreCase(query)) {
                showcuserInfo(company.getcompany_name());
                System.out.println();
                showcompanyServices(company.getcompany_name());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching gym, category, or service was found.");
        }
        return found;
    }

    public boolean showServiceInfo(String serviceName) {
        boolean found = false;
        for (Services service : servicesList) {
            if (service.getservice_name().equalsIgnoreCase(serviceName)) {
                printService(service);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Service not found.");
        }
        return found;
    }

    private void printService(Services service) {
        System.out.println("Service Name: " + service.getservice_name());
        System.out.println("Service Price: " + service.getservice_price());
        System.out.println("Service Category: " + service.getservice_category());
        System.out.println("Company Name: " + service.getcompany_name());
    }

    public void showcuserInfo(String companyName) {
        for (Company_User company : companyUserList) {
            if (company.getcompany_name().equalsIgnoreCase(companyName)) {
                System.out.println("Company Name: " + company.getcompany_name());
                System.out.println("Company ID: " + company.getcompany_id());
                System.out.println("Company Address: " + company.getaddress());
                System.out.println("Company Phone: " + company.getphone_no());
                System.out.println("Company Email: " + company.getemail());
                return;
            }
        }
        System.out.println("Company not found.");
    }

    public void showuserProfile(int userId) {
        for (User existingUser : userList) {
            if (existingUser.getuser_id() == userId) {
                System.out.println("User Name: " + existingUser.getfirstname() + " " + existingUser.getlastname());
                System.out.println("Username: " + existingUser.getusername());
                System.out.println("User ID: " + existingUser.getuser_id());
                System.out.println("Email: " + existingUser.getemail());
                System.out.println("Phone: " + valueOrNotProvided(existingUser.getphone_number()));
                System.out.println("Address: " + valueOrNotProvided(existingUser.getaddress()));
                return;
            }
        }
        System.out.println("User not found.");
    }

    private String valueOrNotProvided(String value) {
        return value == null || value.trim().isEmpty() ? "Not provided" : value;
    }

    public void showcompanyServices(String companyName) {
        Company_User company = findCompanyByName(companyName);
        if (company == null) {
            System.out.println("Company not found.");
            return;
        }

        boolean found = false;
        System.out.println("Services offered by " + company.getcompany_name() + ":");
        for (Services service : servicesList) {
            if (service.getcompany_id() == company.getcompany_id()) {
                System.out.println("- " + service.getservice_name());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services are currently available.");
        }
    }

    public Company_User findCompanyByName(String companyName) {
        for (Company_User company : companyUserList) {
            if (company.getcompany_name().equalsIgnoreCase(companyName)) {
                return company;
            }
        }
        return null;
    }

    public Services findService(String serviceName, int companyId) {
        for (Services service : servicesList) {
            if (service.getcompany_id() == companyId
                    && service.getservice_name().equalsIgnoreCase(serviceName)) {
                return service;
            }
        }
        return null;
    }

    public boolean addToFavourites(String serviceName, int userId, int companyId) {
        Services service = findService(serviceName, companyId);
        if (service == null) {
            System.out.println("Service not found.");
            return false;
        }

        for (Favourites favourite : favouritesList) {
            if (favourite.getuser_id() == userId
                    && favourite.getcompany_id() == companyId
                    && favourite.getservice_id() == service.getservice_id()) {
                System.out.println("Service already in favourites.");
                return false;
            }
        }

        // Store a snapshot of the service for independent favourites display.
        Favourites favourite = new Favourites(
                service.getservice_name(),
                service.getservice_price(),
                service.getservice_category(),
                service.getservice_id(),
                service.getcompany_id(),
                userId
        );
        favourite.setcompany_name(service.getcompany_name());
        favouritesList.add(favourite);
        System.out.println("Service added to favourites: " + service.getservice_name());
        return true;
    }

    public boolean removeFavourite(int userId, String companyName, String serviceName) {
        Company_User company = findCompanyByName(companyName);
        if (company == null) {
            System.out.println("Gym not found.");
            return false;
        }

        Services service = findService(serviceName, company.getcompany_id());
        if (service == null) {
            System.out.println("Service not found.");
            return false;
        }

        boolean removed = favouritesList.removeIf(favourite ->
                favourite.getuser_id() == userId
                        && favourite.getcompany_id() == company.getcompany_id()
                        && favourite.getservice_id() == service.getservice_id());

        System.out.println(removed ? "Service removed from favourites." : "Service was not in favourites.");
        return removed;
    }

    public void removeAllFavourites(int userId) {
        boolean removed = favouritesList.removeIf(favourite -> favourite.getuser_id() == userId);
        System.out.println(removed ? "All favourites were removed." : "The favourites list is already empty.");
    }

    public void showUserFavourites(int userId) {
        boolean found = false;
        System.out.println("Favourites for user ID " + userId + ":");
        for (Favourites favourite : favouritesList) {
            if (favourite.getuser_id() == userId) {
                System.out.println("- " + favourite.getservice_name() + " - " + favourite.getcompany_name());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No favourite services.");
        }
    }

    public boolean addToCart(String serviceName, int userId, int companyId) {
        Services service = findService(serviceName, companyId);
        if (service == null) {
            System.out.println("Service not found.");
            return false;
        }

        for (Cart existingItem : cartList) {
            if (existingItem.getuser_id() == userId
                    && existingItem.getcompany_id() == companyId
                    && existingItem.getservice_id() == service.getservice_id()) {
                System.out.println("Service is already in the cart.");
                return false;
            }
        }

        // Store the current service details as a purchase snapshot.
        Cart cart = new Cart();
        cart.setservice_name(service.getservice_name());
        cart.setservice_id(service.getservice_id());
        cart.setcompany_id(service.getcompany_id());
        cart.setuser_id(userId);
        cart.setprice(service.getservice_price());
        cart.setcompany_name(service.getcompany_name());
        cartList.add(cart);
        System.out.println("Service added to cart: " + service.getservice_name());
        return true;
    }

    public boolean removeFromCart(int userId, String companyName, String serviceName) {
        Company_User company = findCompanyByName(companyName);
        if (company == null) {
            System.out.println("Gym not found.");
            return false;
        }

        Services service = findService(serviceName, company.getcompany_id());
        if (service == null) {
            System.out.println("Service not found.");
            return false;
        }

        boolean removed = cartList.removeIf(cart ->
                cart.getuser_id() == userId
                        && cart.getcompany_id() == company.getcompany_id()
                        && cart.getservice_id() == service.getservice_id());

        System.out.println(removed ? "Service removed from cart." : "Service was not in the cart.");
        return removed;
    }

    public void clearCart(int userId) {
        boolean removed = cartList.removeIf(cart -> cart.getuser_id() == userId);
        System.out.println(removed ? "Cart cleared." : "The cart is already empty.");
    }

    public boolean showCart(int userId) {
        boolean found = false;
        float total = 0.0f;
        System.out.println("Cart for user ID " + userId + ":");
        for (Cart cart : cartList) {
            if (cart.getuser_id() == userId) {
                System.out.println("- Service Name: " + cart.getservice_name());
                System.out.println("  Service ID: " + cart.getservice_id());
                System.out.println("  Company: " + cart.getcompany_name());
                System.out.println("  Price: " + cart.getprice());
                System.out.println("-------------------------------------");
                total += cart.getprice();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Total: " + total);
        }
        return found;
    }

    /**
     * Converts a user's cart items into orders, creates any missing membership
     * records, and clears the cart after a successful checkout.
     */
    public boolean completeOrder(int userId) {
        // Copy matching items before modifying the shared cart collection.
        List<Cart> userCart = new ArrayList<>();
        for (Cart cart : cartList) {
            if (cart.getuser_id() == userId) {
                userCart.add(cart);
            }
        }

        if (userCart.isEmpty()) {
            System.out.println("The cart is empty. Nothing to checkout.");
            return false;
        }

        for (Cart cart : userCart) {
            Order order = new Order(cart.getcompany_id(), cart.getuser_id());
            order.setservice_id(cart.getservice_id());
            order.setservice_name(cart.getservice_name());
            order.setservice_price(cart.getprice());
            // Retrieve the category from the service record when it still exists.
            Services service = findService(cart.getservice_name(), cart.getcompany_id());
            if (service != null) {
                order.setservice_category(service.getservice_category());
            }
            order.setorder_id(ordersList.size() + 1);
            ordersList.add(order);

            // The helper methods prevent duplicate cards and My Gyms entries.
            cardCreation(userId, cart.getcompany_id());
            AddGymToMyGyms(userId, cart.getcompany_id(), cart.getcompany_name(), cart.getservice_id());
        }

        cartList.removeIf(cart -> cart.getuser_id() == userId);
        System.out.println("Order completed successfully.");
        return true;
    }

    public boolean cardCreation(int userId, int companyId) {
        // Each user receives at most one digital card per gym.
        for (My_Codes existingCard : digitalCardList) {
            if (existingCard.getuser_id() == userId && existingCard.getcompany_id() == companyId) {
                return false;
            }
        }

        My_Codes card = new My_Codes();
        card.setuser_id(userId);
        card.setcompany_id(companyId);
        card.setcard_number("CARD-" + userId + "-" + companyId);
        digitalCardList.add(card);
        return true;
    }

    public boolean AddGymToMyGyms(int userId, int companyId, String companyName, int serviceId) {
        // A gym appears only once in a user's My Gyms list.
        for (My_Gyms existingGym : MyGymsList) {
            if (existingGym.getuser_id() == userId && existingGym.getcompany_id() == companyId) {
                return false;
            }
        }

        My_Gyms myGym = new My_Gyms(companyId, companyName, userId, serviceId);
        MyGymsList.add(myGym);
        return true;
    }

    public void showSubscriptionHistory(int userId) {
        boolean found = false;
        System.out.println("Subscription history for user ID " + userId + ":");
        for (Order order : ordersList) {
            if (order.getuser_id() == userId) {
                System.out.println("- Order ID: " + order.getorder_id());
                System.out.println("  Service Name: " + order.getservice_name());
                System.out.println("  Service Price: " + order.getservice_price());
                System.out.println("  Company ID: " + order.getcompany_id());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No completed subscriptions.");
        }
    }

    public void showGymHistory(int companyId) {
        boolean found = false;
        System.out.println("Subscription history for company ID " + companyId + ":");
        for (Order order : ordersList) {
            if (order.getcompany_id() == companyId) {
                System.out.println("- Order ID: " + order.getorder_id());
                System.out.println("  User ID: " + order.getuser_id());
                System.out.println("  Service ID: " + order.getservice_id());
                System.out.println("  Service Name: " + order.getservice_name());
                System.out.println("  Service Price: " + order.getservice_price());
                System.out.println("-------------------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No completed subscriptions.");
        }
    }

    public boolean addAnnouncement(Announcements announcement) {
        if (announcement == null) {
            return false;
        }

        announcementsList.add(announcement);
        System.out.println("Announcement added successfully.");
        return true;
    }

    public Announcements findAnnouncement(int announcementId, int companyId) {
        for (Announcements announcement : announcementsList) {
            if (announcement.getann_id() == announcementId
                    && announcement.getcompany_id() == companyId) {
                return announcement;
            }
        }
        return null;
    }

    public boolean deleteAnnouncement(int announcementId, int companyId) {
        Announcements announcement = findAnnouncement(announcementId, companyId);
        if (announcement == null) {
            System.out.println("Announcement not found or does not belong to your gym.");
            return false;
        }

        announcementsList.remove(announcement);
        System.out.println("Announcement deleted successfully.");
        return true;
    }

    /**
     * Displays announcements from gyms connected to the user through either a
     * completed purchase or a saved favourite.
     */
    public void showUserAnnouncements(int userId) {
        // Remove duplicate company IDs while preserving their discovery order.
        Set<Integer> relevantCompanyIds = new LinkedHashSet<>();

        for (My_Gyms gym : MyGymsList) {
            if (gym.getuser_id() == userId) {
                relevantCompanyIds.add(gym.getcompany_id());
            }
        }

        for (Favourites favourite : favouritesList) {
            if (favourite.getuser_id() == userId) {
                relevantCompanyIds.add(favourite.getcompany_id());
            }
        }

        boolean found = false;
        for (Announcements announcement : announcementsList) {
            if (relevantCompanyIds.contains(announcement.getcompany_id())) {
                printAnnouncement(announcement);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No announcements are available for your gyms or favourites.");
        }
    }

    private void printAnnouncement(Announcements announcement) {
        System.out.println("Announcement ID: " + announcement.getann_id());
        System.out.println("Gym: " + announcement.getcompany_name());
        System.out.println("Title: " + announcement.getann_title());
        System.out.println("Description: " + announcement.getann_description());
        System.out.println("Published: " + announcement.getann_date());
        System.out.println();
    }

    public void showCompanyAnnouncements(int companyId) {
        boolean found = false;
        for (Announcements announcement : announcementsList) {
            if (announcement.getcompany_id() == companyId) {
                printAnnouncement(announcement);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No announcements are available.");
        }
    }
}
