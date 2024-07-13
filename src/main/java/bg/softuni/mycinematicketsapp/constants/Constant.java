package bg.softuni.mycinematicketsapp.constants;

public class Constant {

    public static final String REGISTER_ATTRIBUTE_NAME = "registerDto";
    public static final String REGISTER_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.registerDto";
    public static final String MOVIE_ATTRIBUTE_NAME = "createMovie";
    public static final String MOVIE_BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.createMovie";
    public static final String BAD_CREDENTIALS = "badCredentials";


    //==================================================================================================================

    public static final String REDIRECT_REGISTER = "redirect:/users/register";
    public static final String REDIRECT_LOGIN = "redirect:/users/login";
    public static final String REDIRECT_AFTER_REGISTER = "redirect:/users/check-email";
    public static final String REDIRECT_HOME = "redirect:/home";
    public static final String REDIRECT_INDEX = "redirect:/";
    public static final String REDIRECT_PROGRAM = "redirect:/program";
    public static final String REDIRECT_PROGRAM_CITY_NAME = "redirect:/program/{cityName}";
    public static final String REDIRECT_UPDATE_BOOKING_TIME = "redirect:/program/update-projection-time/{id}";
    public static final String REDIRECT_ADD_MOVIE = "redirect:/movies/add-movie";
}
