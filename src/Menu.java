import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Controls the console menus and coordinates the main GymHub workflows.
 * It keeps track of the signed-in account and delegates data operations to
 * the shared {@link Find_Gyms} in-memory repository.
 */
public class Menu {
    // Shared application data and the accounts currently used by the menu flow.
    Find_Gyms find_gym;
    Company_User c_user;
    Services services;
    User user;

    private final Scanner scanner;

    // Next available identifiers for objects created during this execution.
    private int company_id;
    private int user_id;
    private int service_id;
    private int announcement_id;

    public Menu() {
        this.find_gym = new Find_Gyms();
        this.c_user = new Company_User();
        this.services = new Services();
        this.user = new User();
        this.scanner = new Scanner(System.in);
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

    public int getservice_id() {
        return service_id;
    }

    public void setservice_id(int service_id) {
        this.service_id = service_id;
    }

    public int getannouncement_id() {
        return announcement_id;
    }

    public void setannouncement_id(int announcement_id) {
        this.announcement_id = announcement_id;
    }

    /**
     * Connects the menu to an existing data repository and recalculates the next
     * available identifiers from the objects already stored in memory.
     */
    public void menu(Find_Gyms find_gym) {
        this.find_gym = find_gym;
        this.company_id = nextCompanyId();
        this.user_id = nextUserId();
        this.service_id = nextServiceId();
        this.announcement_id = nextAnnouncementId();
    }

    // IDs are derived from the highest existing value rather than list size,
    // so deleted or non-sequential records do not cause identifier reuse.
    private int nextCompanyId() {
        int nextId = 0;
        for (Company_User company : find_gym.companyUserList) {
            nextId = Math.max(nextId, company.getcompany_id() + 1);
        }
        return nextId;
    }

    private int nextUserId() {
        int nextId = 0;
        for (User existingUser : find_gym.userList) {
            nextId = Math.max(nextId, existingUser.getuser_id() + 1);
        }
        return nextId;
    }

    private int nextServiceId() {
        int nextId = 0;
        for (Services service : find_gym.servicesList) {
            nextId = Math.max(nextId, service.getservice_id() + 1);
        }
        return nextId;
    }

    private int nextAnnouncementId() {
        int nextId = 0;
        for (Announcements announcement : find_gym.announcementsList) {
            nextId = Math.max(nextId, announcement.getann_id() + 1);
        }
        return nextId;
    }

    /**
     * Authenticates the supplied credentials and stores the matching account as
     * the active session. Returns 1 for a company, 2 for a personal user, and 0
     * when no account matches.
     */
    public int getuser_info(String username, String password) {
        for (Company_User company : find_gym.companyUserList) {
            if (company.getcompany_name().equalsIgnoreCase(username)
                    && company.getpassword().equals(password)) {
                c_user = company;
                return 1;
            }
        }

        for (User existingUser : find_gym.userList) {
            if (existingUser.getusername().equalsIgnoreCase(username)
                    && existingUser.getpassword().equals(password)) {
                user = existingUser;
                return 2;
            }
        }

        System.out.println("Username or password is incorrect. Please try again.");
        return 0;
    }

    public boolean newCompany_User_info() {
        System.out.println("Creating your company account...");
        String name = readRequiredLine("Give your gym's name:");
        String address = readRequiredLine("Give your address:");
        String phoneNumber = readRequiredLine("Give your phone number:");
        int postalCode = readInt("Give your postal code:");
        String email = readRequiredLine("Give your email:");
        long taxId = readLong("Give your Tax ID (TIN):");
        String password = readConfirmedPassword();

        Company_User company = new Company_User(
                company_id,
                name,
                postalCode,
                email,
                address,
                phoneNumber,
                taxId,
                password
        );

        if (find_gym.addcompany(company) == 0) {
            return false;
        }

        c_user = company;
        company_id++;
        return true;
    }

    public boolean newuser_info() {
        System.out.println("Creating your account...");
        String firstName = readRequiredLine("Give your first name:");
        String lastName = readRequiredLine("Give your last name:");
        String username = readRequiredLine("Give your username:");
        String email = readRequiredLine("Give your email:");
        String password = readConfirmedPassword();

        User newUser = new User(
                user_id,
                firstName,
                lastName,
                username,
                email,
                password,
                null,
                null
        );

        if (find_gym.adduser(newUser) == 0) {
            return false;
        }

        user = newUser;
        user_id++;
        return true;
    }

    private String readConfirmedPassword() {
        while (true) {
            String password = readRequiredLine("Password:");
            String repeatedPassword = readRequiredLine("Repeat Password:");
            if (password.equals(repeatedPassword)) {
                return password;
            }
            System.out.println("Passwords do not match. Please try again.");
        }
    }

    /**
     * Adds one or more services for the signed-in company while enforcing
     * non-negative prices and unique service names within that gym.
     */
    public void newservices() {
        int serviceCount;
        do {
            serviceCount = readInt("Enter the number of services you want to add: ");
            if (serviceCount <= 0) {
                System.out.println("Enter a number greater than zero.");
            }
        } while (serviceCount <= 0);

        int addedCount = 0;
        for (int i = 0; i < serviceCount; i++) {
            System.out.println("Enter details for the " + ordinal(i + 1) + " service:");
            String serviceName = readRequiredLine("Service name:");
            String serviceCategory = readRequiredLine("Service category:");
            float servicePrice = readNonNegativeFloat("Service price:");

            if (find_gym.findService(serviceName, c_user.getcompany_id()) != null) {
                System.out.println("A service with this name already exists for your gym.");
                continue;
            }

            Services newService = new Services(
                    c_user.getcompany_id(),
                    service_id,
                    serviceName,
                    servicePrice,
                    serviceCategory,
                    c_user.getcompany_name()
            );
            find_gym.servicesList.add(newService);
            service_id++;
            addedCount++;
        }

        System.out.println(addedCount + " service(s) added to your gym.");
    }

    private String ordinal(int number) {
        int mod100 = number % 100;
        if (mod100 >= 11 && mod100 <= 13) {
            return number + "th";
        }
        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }

    public void showmenu() {
        while (true) {
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    signin();
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    System.out.println("Thank you for using GymHub.");
                    return;
                default:
                    System.out.println("Choose a valid option.");
            }
        }
    }

    public void signin() {
        String username = readRequiredLine("Username:");
        String password = readRequiredLine("Password:");

        int accountType = getuser_info(username, password);
        if (accountType == 1) {
            companyhomescreen();
        } else if (accountType == 2) {
            personalhomescreen();
        }
    }

    public void signup() {
        while (true) {
            System.out.println("1. Create a personal account");
            System.out.println("2. Create a company account");
            System.out.println("3. Go back");

            int accountType = readInt("Choose an option: ");
            boolean accountCreated;

            if (accountType == 1) {
                accountCreated = newuser_info();
            } else if (accountType == 2) {
                accountCreated = newCompany_User_info();
            } else if (accountType == 3) {
                return;
            } else {
                System.out.println("Choose a valid option.");
                continue;
            }

            if (accountCreated) {
                System.out.println("You can now sign in to your account!");
                signin();
                return;
            }
        }
    }

    public void personalhomescreen() {
        while (true) {
            System.out.println();
            System.out.println("Welcome " + user.getfirstname() + " " + user.getlastname() + " to GymHub!");
            System.out.println("------------------------");
            System.out.println("1. Find Gyms");
            System.out.println("2. My Gyms");
            System.out.println("3. Favourites");
            System.out.println("4. My Codes");
            System.out.println("5. Profile");
            System.out.println("6. Cart");
            System.out.println("7. Announcements");
            System.out.println("8. Sign Out");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    personalSearchMenu();
                    break;
                case 2:
                    personalGymsMenu();
                    break;
                case 3:
                    favouritesMenu();
                    break;
                case 4:
                    codesMenu();
                    break;
                case 5:
                    personalProfileMenu();
                    break;
                case 6:
                    cartMenu();
                    break;
                case 7:
                    showAnnouncements(user.getuser_id());
                    break;
                case 8:
                    if (confirmSignOut()) {
                        System.out.println("You have signed out successfully!");
                        return;
                    }
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }
        }
    }

    private void personalSearchMenu() {
        while (true) {
            String query = readRequiredLine("Search for a category, service, or gym:");
            find_gym.search(query);

            System.out.println("1. Add a service to favourites");
            System.out.println("2. Add a service to cart");
            System.out.println("3. Search again");
            System.out.println("4. Go back");
            int action = readInt("Choose an option: ");

            if (action == 1 || action == 2) {
                String serviceName = readRequiredLine("Service name:");
                String companyName = readRequiredLine("Gym name:");
                Company_User company = find_gym.findCompanyByName(companyName);
                if (company == null) {
                    System.out.println("Gym not found.");
                    continue;
                }

                if (action == 1) {
                    find_gym.addToFavourites(serviceName, user.getuser_id(), company.getcompany_id());
                } else {
                    find_gym.addToCart(serviceName, user.getuser_id(), company.getcompany_id());
                }
                return;
            } else if (action == 3) {
                continue;
            } else if (action == 4) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void personalGymsMenu() {
        System.out.println("My Gyms");
        MyGyms(user.getuser_id());
    }

    private void favouritesMenu() {
        while (true) {
            find_gym.showUserFavourites(user.getuser_id());
            System.out.println("1. Remove a service");
            System.out.println("2. Remove all services");
            System.out.println("3. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                String companyName = readRequiredLine("Gym name:");
                String serviceName = readRequiredLine("Service name:");
                find_gym.removeFavourite(user.getuser_id(), companyName, serviceName);
            } else if (choice == 2) {
                find_gym.removeAllFavourites(user.getuser_id());
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void codesMenu() {
        if (find_gym.digitalCardList.stream().noneMatch(code -> code.getuser_id() == user.getuser_id())) {
            System.out.println("You do not have any digital membership cards.");
            return;
        }

        String gymName = readRequiredLine("Select a gym to show its code:");
        Company_User company = find_gym.findCompanyByName(gymName);
        if (company == null) {
            System.out.println("Gym not found.");
            return;
        }
        MyCodes(user.getuser_id(), company.getcompany_id());
    }

    private void personalProfileMenu() {
        while (true) {
            find_gym.showuserProfile(user.getuser_id());
            System.out.println("1. Edit Profile");
            System.out.println("2. View Subscription History");
            System.out.println("3. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                editPersonalProfile();
            } else if (choice == 2) {
                find_gym.showSubscriptionHistory(user.getuser_id());
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void editPersonalProfile() {
        System.out.println("1. Name");
        System.out.println("2. Address");
        System.out.println("3. Phone Number");
        System.out.println("4. Email");
        System.out.println("5. Username");
        System.out.println("6. Cancel");
        int choice = readInt("Choose the field to edit: ");

        switch (choice) {
            case 1:
                user.setfirstname(readRequiredLine("Give new first name:"));
                user.setlastname(readRequiredLine("Give new last name:"));
                break;
            case 2:
                user.setaddress(readRequiredLine("Give new address:"));
                break;
            case 3:
                user.setphone_number(readRequiredLine("Give new phone number:"));
                break;
            case 4:
                String email = readRequiredLine("Give new email:");
                if (find_gym.isUserEmailAvailable(email, user.getuser_id())) {
                    user.setemail(email);
                } else {
                    System.out.println("Email already exists.");
                    return;
                }
                break;
            case 5:
                String username = readRequiredLine("Give new username:");
                if (find_gym.isUsernameAvailable(username, user.getuser_id())) {
                    user.setusername(username);
                } else {
                    System.out.println("Username already exists.");
                    return;
                }
                break;
            case 6:
                return;
            default:
                System.out.println("Choose a valid option.");
                return;
        }
        System.out.println("Profile updated successfully.");
    }

    private void cartMenu() {
        while (true) {
            find_gym.showCart(user.getuser_id());
            System.out.println("1. Checkout");
            System.out.println("2. Remove a service from cart");
            System.out.println("3. Remove all services from cart");
            System.out.println("4. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                find_gym.completeOrder(user.getuser_id());
            } else if (choice == 2) {
                String gymName = readRequiredLine("Gym name:");
                String serviceName = readRequiredLine("Service name:");
                find_gym.removeFromCart(user.getuser_id(), gymName, serviceName);
            } else if (choice == 3) {
                find_gym.clearCart(user.getuser_id());
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    public void companyhomescreen() {
        while (true) {
            System.out.println();
            System.out.println("Welcome " + c_user.getcompany_name() + " to GymHub!");
            System.out.println("-------------------------------");
            System.out.println("1. Find Gyms");
            System.out.println("2. Services");
            System.out.println("3. Profile");
            System.out.println("4. Subscription History");
            System.out.println("5. Announcements");
            System.out.println("6. Sign Out");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    companySearchMenu();
                    break;
                case 2:
                    serviceManagementMenu();
                    break;
                case 3:
                    companyProfileMenu();
                    break;
                case 4:
                    find_gym.showGymHistory(c_user.getcompany_id());
                    break;
                case 5:
                    announcementManagementMenu();
                    break;
                case 6:
                    if (confirmSignOut()) {
                        System.out.println("You have signed out successfully!");
                        return;
                    }
                    break;
                default:
                    System.out.println("Choose a valid option.");
            }
        }
    }

    private void companySearchMenu() {
        while (true) {
            System.out.println("1. Search");
            System.out.println("2. Show categories");
            System.out.println("3. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                String query = readRequiredLine("Search for a category, service, or gym:");
                find_gym.search(query);
            } else if (choice == 2) {
                find_gym.showCategories();
                String category = readRequiredLine("Category name (or leave empty to go back):", true);
                if (!category.isEmpty()) {
                    find_gym.showCategoryServices(category);
                }
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void serviceManagementMenu() {
        while (true) {
            System.out.println("1. Add Services");
            System.out.println("2. Edit Service");
            System.out.println("3. Delete Service");
            System.out.println("4. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                newservices();
            } else if (choice == 2) {
                editService();
            } else if (choice == 3) {
                deleteService();
            } else if (choice == 4) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void editService() {
        String serviceName = readRequiredLine("Give the name of the service you want to edit:");
        Services service = find_gym.findService(serviceName, c_user.getcompany_id());
        if (service == null) {
            System.out.println("Service not found or does not belong to your gym.");
            return;
        }

        System.out.println("Current Service Name: " + service.getservice_name());
        System.out.println("Current Price: " + service.getservice_price());
        System.out.println("Current Category: " + service.getservice_category());

        String nameInput = readRequiredLine("Enter new name (or press Enter to keep current):", true);
        String updatedName = nameInput.isEmpty() ? service.getservice_name() : nameInput;

        Services duplicate = find_gym.findService(updatedName, c_user.getcompany_id());
        if (duplicate != null && duplicate != service) {
            System.out.println("Another service with that name already exists.");
            return;
        }

        String priceInput = readRequiredLine("Enter new price (or press Enter to keep current):", true);
        float updatedPrice = service.getservice_price();
        if (!priceInput.isEmpty()) {
            Float parsedPrice = parseNonNegativeFloat(priceInput);
            if (parsedPrice == null) {
                System.out.println("Price must be a non-negative number. No changes were applied.");
                return;
            }
            updatedPrice = parsedPrice;
        }

        String categoryInput = readRequiredLine("Enter new category (or press Enter to keep current):", true);
        String updatedCategory = categoryInput.isEmpty()
                ? service.getservice_category()
                : categoryInput;

        // Apply the edit only after every new value has been validated.
        service.setservice_name(updatedName);
        service.setservice_price(updatedPrice);
        service.setservice_category(updatedCategory);

        /*
         * Cart and favourites entries store copies of the service data.
         * Keep those copies consistent after a successful edit.
         */
        for (Cart cart : find_gym.cartList) {
            if (cart.getcompany_id() == service.getcompany_id()
                    && cart.getservice_id() == service.getservice_id()) {
                cart.setservice_name(service.getservice_name());
                cart.setprice(service.getservice_price());
                cart.setcompany_name(service.getcompany_name());
            }
        }

        for (Favourites favourite : find_gym.favouritesList) {
            if (favourite.getcompany_id() == service.getcompany_id()
                    && favourite.getservice_id() == service.getservice_id()) {
                favourite.setservice_name(service.getservice_name());
                favourite.setservice_price(service.getservice_price());
                favourite.setservice_category(service.getservice_category());
                favourite.setcompany_name(service.getcompany_name());
            }
        }

        System.out.println("Service updated successfully.");
    }

    private void deleteService() {
        String serviceName = readRequiredLine("Give the name of the service you want to delete:");
        Services service = find_gym.findService(serviceName, c_user.getcompany_id());
        if (service == null) {
            System.out.println("Service not found or does not belong to your gym.");
            return;
        }

        // Remove dependent cart and favourite entries so no stale references remain.
        find_gym.servicesList.remove(service);
        find_gym.favouritesList.removeIf(favourite ->
                favourite.getcompany_id() == c_user.getcompany_id()
                        && favourite.getservice_id() == service.getservice_id());
        find_gym.cartList.removeIf(cart ->
                cart.getcompany_id() == c_user.getcompany_id()
                        && cart.getservice_id() == service.getservice_id());
        System.out.println("Service deleted successfully.");
    }

    private void companyProfileMenu() {
        while (true) {
            find_gym.showcuserInfo(c_user.getcompany_name());
            System.out.println("1. Edit Profile");
            System.out.println("2. Go back");
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                editCompanyProfile();
            } else if (choice == 2) {
                return;
            } else {
                System.out.println("Choose a valid option.");
            }
        }
    }

    private void editCompanyProfile() {
        System.out.println("1. Name");
        System.out.println("2. Address");
        System.out.println("3. Phone Number");
        System.out.println("4. Email");
        System.out.println("5. Cancel");
        int choice = readInt("Choose the field to edit: ");

        switch (choice) {
            case 1:
                String oldName = c_user.getcompany_name();
                String newName = readRequiredLine("Give new name:");
                if (!find_gym.isCompanyNameAvailable(newName, c_user.getcompany_id())) {
                    System.out.println("Company name already exists.");
                    return;
                }
                c_user.setcompany_name(newName);
                updateCompanyNameReferences(oldName, newName);
                break;
            case 2:
                c_user.setaddress(readRequiredLine("Give new address:"));
                break;
            case 3:
                c_user.setphone_no(readRequiredLine("Give new phone number:"));
                break;
            case 4:
                String email = readRequiredLine("Give new email:");
                if (!find_gym.isCompanyEmailAvailable(email, c_user.getcompany_id())) {
                    System.out.println("Company email already exists.");
                    return;
                }
                c_user.setemail(email);
                break;
            case 5:
                return;
            default:
                System.out.println("Choose a valid option.");
                return;
        }
        System.out.println("Profile updated successfully.");
    }

    /**
     * Propagates a company rename to every stored record that keeps a copy of
     * the company name. Relationships are matched by the stable company ID.
     */
    private void updateCompanyNameReferences(String oldName, String newName) {
        for (Services service : find_gym.servicesList) {
            if (service.getcompany_id() == c_user.getcompany_id()) {
                service.setcompany_name(newName);
            }
        }
        for (Cart cart : find_gym.cartList) {
            if (cart.getcompany_id() == c_user.getcompany_id()) {
                cart.setcompany_name(newName);
            }
        }
        for (Favourites favourite : find_gym.favouritesList) {
            if (favourite.getcompany_id() == c_user.getcompany_id()) {
                favourite.setcompany_name(newName);
            }
        }
        for (My_Gyms gym : find_gym.MyGymsList) {
            if (gym.getcompany_id() == c_user.getcompany_id()
                    && gym.getcompany_name().equalsIgnoreCase(oldName)) {
                gym.setcompany_name(newName);
            }
        }
        for (Announcements announcement : find_gym.announcementsList) {
            if (announcement.getcompany_id() == c_user.getcompany_id()) {
                announcement.setcompany_name(newName);
            }
        }
    }

    private boolean confirmSignOut() {
        String answer = readRequiredLine("Are you sure you want to sign out? (Y/N)");
        if (answer.equalsIgnoreCase("Y")) {
            return true;
        }
        if (!answer.equalsIgnoreCase("N")) {
            System.out.println("Invalid input. You are still signed in.");
        } else {
            System.out.println("You are still signed in.");
        }
        return false;
    }

    private void announcementManagementMenu() {
        while (true) {
            System.out.println("1. View announcements");
            System.out.println("2. Add announcement");
            System.out.println("3. Edit announcement");
            System.out.println("4. Delete announcement");
            System.out.println("5. Go back");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    find_gym.showCompanyAnnouncements(c_user.getcompany_id());
                    break;
                case 2:
                    addAnnouncement();
                    break;
                case 3:
                    editAnnouncement();
                    break;
                case 4:
                    deleteAnnouncement();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Choose a valid option.");
            }
        }
    }

    private void addAnnouncement() {
        String title = readRequiredLine("Announcement title:");
        String description = readRequiredLine("Announcement description:");

        Announcements announcement = new Announcements(
                announcement_id,
                title,
                description,
                new Date(),
                c_user.getcompany_id(),
                c_user.getcompany_name(),
                -1
        );

        if (find_gym.addAnnouncement(announcement)) {
            announcement_id++;
        }
    }

    private void editAnnouncement() {
        find_gym.showCompanyAnnouncements(c_user.getcompany_id());
        int announcementId = readInt("Give the ID of the announcement you want to edit:");
        Announcements announcement = find_gym.findAnnouncement(
                announcementId,
                c_user.getcompany_id()
        );

        if (announcement == null) {
            System.out.println("Announcement not found or does not belong to your gym.");
            return;
        }

        String title = readRequiredLine(
                "Enter a new title (or press Enter to keep the current title):",
                true
        );
        String description = readRequiredLine(
                "Enter a new description (or press Enter to keep the current description):",
                true
        );

        if (!title.isEmpty()) {
            announcement.setann_title(title);
        }
        if (!description.isEmpty()) {
            announcement.setann_description(description);
        }
        // Editing an announcement refreshes its displayed publication timestamp.
        announcement.setann_date(new Date());
        System.out.println("Announcement updated successfully.");
    }

    private void deleteAnnouncement() {
        find_gym.showCompanyAnnouncements(c_user.getcompany_id());
        int announcementId = readInt("Give the ID of the announcement you want to delete:");
        find_gym.deleteAnnouncement(announcementId, c_user.getcompany_id());
    }

    public void showAnnouncements(int userId) {
        find_gym.showUserAnnouncements(userId);
    }

    public void MyCodes(int userId, int companyId) {
        boolean found = false;
        for (My_Codes code : find_gym.digitalCardList) {
            if (userId == code.getuser_id() && companyId == code.getcompany_id()) {
                System.out.println("Card Number: " + code.getcard_number());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No digital card was found for this gym.");
        }
    }

    public void MyGyms(int userId) {
        boolean found = false;
        for (My_Gyms gym : find_gym.MyGymsList) {
            if (userId == gym.getuser_id()) {
                System.out.println("Gym ID: " + gym.getcompany_id());
                System.out.println("Gym Name: " + gym.getcompany_name());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("You do not have any active gym subscriptions.");
        }
    }

    // Centralized input helpers keep all menus on one Scanner and prevent
    // malformed numeric input from terminating the application.
    private int readInt(String prompt) {
        while (true) {
            String input = readRequiredLine(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            String input = readRequiredLine(prompt);
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private float readNonNegativeFloat(String prompt) {
        while (true) {
            String input = readRequiredLine(prompt);
            Float value = parseNonNegativeFloat(input);
            if (value != null) {
                return value;
            }
            System.out.println("Enter a non-negative number.");
        }
    }

    /**
     * Parses a price without printing an error so callers can decide whether to
     * retry input or cancel an operation without applying partial changes.
     */
    private Float parseNonNegativeFloat(String input) {
        try {
            float value = Float.parseFloat(input);
            return value >= 0.0f ? value : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private String readRequiredLine(String prompt) {
        return readRequiredLine(prompt, false);
    }

    private String readRequiredLine(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine().trim();
            if (allowEmpty || !value.isEmpty()) {
                return value;
            }
            System.out.println("This value cannot be empty.");
        }
    }
}
