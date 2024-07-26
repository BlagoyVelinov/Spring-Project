package bg.softuni.mycinematicketsapp.constants;

public class Constant {

    public static final String REGISTER_ATTRIBUTE_NAME = "registerDto";
    public static final String REGISTER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.registerDto";
    public static final String MOVIE_ATTRIBUTE_NAME = "createMovie";
    public static final String MOVIE_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createMovie";
    public static final String ORDER_ATTRIBUTE_NAME = "createOrder";
    public static final String ORDER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createOrder";
    public static final String TICKET_ATTRIBUTE_NAME = "updateTicket";
    public static final String TICKET_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.updateTicket";
    public static final String BAD_CREDENTIALS = "badCredentials";


//====================================================================================================================//

    public static final String REDIRECT_REGISTER = "redirect:/users/register";
    public static final String REDIRECT_LOGIN = "redirect:/users/login";
    public static final String REDIRECT_AFTER_REGISTER = "redirect:/users/check-email";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_INDEX = "redirect:/";
    public static final String REDIRECT_PROGRAM = "redirect:/program";
    public static final String REDIRECT_PROGRAM_ORDER_TICKETS = "redirect:/program/order-tickets";
    public static final String REDIRECT_UPDATE_BOOKING_TIME = "redirect:/program/update-projection-time/{id}";
    public static final String REDIRECT_BUY_TICKETS = "redirect:/order/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}";
    public static final String REDIRECT_ADD_MOVIE = "redirect:/movies/add-movie";
    public static final String REDIRECT_SELECT_SEATS = "redirect:/order/select-seats/{orderId}/movie/{movieId}";
    public static final String REDIRECT_CONFIRM_ORDER = "redirect:/order/confirm-order/{orderId}";

//====================================================================================================================//
    public static final String OBJECT_ZERO_QUANTITY = "zeroRegularQuantity";
    public static final String FIELD_QUANTITY = "regularQuantity";
    public static final String DEFAULT_MESSAGE = "You must select a number of tickets";
    public static final double FIX_COMMISSION_PER_TICKET = 0.80;


}
