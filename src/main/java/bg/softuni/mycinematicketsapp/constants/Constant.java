package bg.softuni.mycinematicketsapp.constants;

public class Constant {

    public static final String REGISTER_ATTRIBUTE_NAME = "registerDto";
    public static final String REGISTER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.registerDto";
    public static final String MOVIE_ATTRIBUTE_NAME = "createMovie";
    public static final String MOVIE_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createMovie";
    public static final String ORDER_ATTRIBUTE_NAME = "createOrder";
    public static final String ORDER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createOrder";
    public static final String OFFER_ATTRIBUTE_NAME = "createOffer";
    public static final String OFFER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createOffer";
    public static final String BAD_CREDENTIALS = "badCredentials";


//====================================================================================================================//

    public static final String REDIRECT_REGISTER = "redirect:/users/register";
    public static final String REDIRECT_LOGIN = "redirect:/users/login";
    public static final String REDIRECT_AFTER_REGISTER = "redirect:/users/check-email";
    public static final String REDIRECT_ADD_OFFER = "redirect:/offers/add-offer";
    public static final String REDIRECT_OFFERS = "redirect:/offers";
    public static final String REDIRECT_PROGRAM = "redirect:/program";
    public static final String REDIRECT_PROGRAM_ORDER_TICKETS = "redirect:/program/order-tickets";
    public static final String REDIRECT_UPDATE_BOOKING_TIME = "redirect:/program/update-projection-time/{id}";
    public static final String REDIRECT_BUY_TICKETS = "redirect:/order/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}";
    public static final String REDIRECT_ADD_MOVIE = "redirect:/movies/add-movie";
    public static final String REDIRECT_SELECT_SEATS = "redirect:/order/select-seats/{orderId}/movie/{movieId}";
    public static final String REDIRECT_CONFIRM_ORDER = "redirect:/order/confirm-order/{orderId}";

//====================================================================================================================//
    public static final double FIX_COMMISSION_PER_TICKET = 0.80;

    public static final String OBJECT_ZERO_QUANTITY = "zeroRegularQuantity";
    public static final String FIELD_QUANTITY = "regularQuantity";
    public static final String DEFAULT_MESSAGE_QUANTITY = "You must select a number of tickets";


    public static final String OBJECT_DIFFERENT_TRAILER_URL = "https://www.youtube.com";
    public static final String FIELD_TRAILER_URL = "trailerUrl";
    public static final String DEFAULT_MESSAGE_TRAILER = "You must add trailer from \"https://www.youtube.com\"!";

//====================================================================================================================//
    public static final int COUNT_AVAILABLE_DAYS = 7;
    public static final String UUID_REPLACE = "-";
    public static final String UUID_REPLACEMENT = "";
    public static final String SOURCE_NAME = "UserService";
    public static final String ROLE_ = "ROLE_";

//========================================SQL-Admin user==============================================================//

    public static final String USER_USERNAME = "admin";
    public static final String USER_BIRTHDATE = "1990-06-06";
    public static final String USER_CREATED = "2024-05-19T12:09:30.367508";
    public static final String USER_EMAIL = "admin@gmail.com";
    public static final String USER_NAME = "Admin Adminov";
    public static final String USER_PASSWORD = "$2a$10$9cgWfwwTx.fUSNnFc0tkbOERNJVAVloDgi/DIF6JwPSDD4YfY1PVy";
}
