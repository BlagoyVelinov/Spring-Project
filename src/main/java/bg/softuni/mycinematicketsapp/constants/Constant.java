package bg.softuni.mycinematicketsapp.constants;

public class Constant {

//=============================================== Tickets ============================================================//

    public static final String SUCCESSFULLY_DELETED_TICKET = "Deleted ticket with ID: %d - successfully";



//=============================================== Offers ============================================================//

    public static final String SUCCESS_CREATED_OFFER = "Offer created successfully";
    public static final String SUCCESS_DELETED_OFFER = "Offer deleted successfully";



//=============================================== Orders ============================================================//

    public static final String SUCCESS_CREATED_ORDER = "Order created successfully";


//====================================================================================================================//

    public static final String REDIRECT_REGISTER = "redirect:/users/register";
    public static final String REDIRECT_LOGIN = "redirect:/users/login";
    public static final String REDIRECT_AFTER_REGISTER = "redirect:/users/check-email";
    public static final String REDIRECT_ADD_OFFER = "redirect:/offers/add-offer";
    public static final String REDIRECT_OFFERS = "redirect:/offers";
    public static final String REDIRECT_PROGRAM = "redirect:/program";

//========================================== Messages ================================================================//
    public static final String MESSAGE = "message";
    public static final String ERROR = "error";
    public static final String SUCCESS_SEND_MESSAGE = "Message sent successfully";
    public static final String BAD_CREDENTIALS = "badCredentials";
    public static final String USERNAME = "username";
    public static final String BEARER = "Bearer ";
    public static final String ACTIVATION_LINK = "activationLink";
    public static final String UUID_REPLACE = "-";
    public static final String UUID_REPLACEMENT = "";
    public static final String SOURCE_NAME = "UserService";
    public static final String ROLE_ = "ROLE_";
    public static final String ROLE_ADMIN = "ROLE_ADMINISTRATOR";

//========================================SQL-Admin user==============================================================//

    public static final String USER_USERNAME = "admin";
    public static final String USER_BIRTHDATE = "1990-06-06";
    public static final String USER_CREATED = "2024-05-19T12:09:30.367508";
    public static final String USER_EMAIL = "admin@gmail.com";
    public static final String USER_NAME = "Admin Adminov";
    public static final String USER_PASSWORD = "$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy";

//======================================== User Messages ==============================================================//

    public static final String UPDATE_PROFILE_PHOTO = "Successfully updated profile photo!";
    public static final String SUCCESS_PASSWORD_UPDATE = "Password updated successfully!";
    public static final String SUCCESS_DELETE_USER = "User deleted successfully!";
    public static final String SUCCESS_SIGNUP_USER = "User registered successfully";
    public static final String SUCCESS_LOGOUT_USER = "Logout successful";
    public static final String IS_AUTHENTICATED = "isAuthenticated";


//============================================ Home Messages ==========================================================//

    public static final String ABOUT_US = "This is the About Us information for the Cinema Tickets application.";
    public static final String CONTACT_US = "This is the Contact Us information for the Cinema Tickets application.";
    public static final String FOUR_DX = "Information about 4DX cinemas.";
    public static final String IMAX = "Information about IMAX cinemas.";



//============================================ Email Messages ==========================================================//

    public static final String EMAIL_WELCOME = "Welcome to Cinema Tickets!";
    public static final String EMAIL_TEMPLATE = "email/registration-email";

}
