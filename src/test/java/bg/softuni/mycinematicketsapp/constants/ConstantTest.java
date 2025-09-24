package bg.softuni.mycinematicketsapp.constants;

public class ConstantTest {

    //========================================== EmailServiceImplTest
    public static final String TEST_COMPANY_EMAIL = "cinematickets@example.com";
    public static final String TEST_ACTIVATION_BASE_URL = "http://localhost:8080/activate";
    public static final String TEST_EMAIL_TEMPLATE = "email/registration-email";
    public static final String TEST_RESPONSE_HTML = "<html>Test Email</html>";
    public static final String TEST_SUBJECT = "Test Subject";
    public static final String TEST_MESSAGE = "This is a test email message!";

    //========================================== UserServiceImplTest

    public static final String TEST_USERNAME = "testUser";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_NAME = "admin adminov";
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String USER_EMAIL = "test@mail.com";
    public static final String TEST_NAME = "fullName";
    public static final String TEST_PASSWORD = "secret123";
    public static final String TEST_NEW_PASSWORD = "secret1234";
    public static final String TEST_WRONG_PASSWORD = "wrongPassword";
    public static final String TEST_ENCODED_PASSWORD = "encodedNewPassword";
    public static final String TEST_USER_BIRTHDATE = "28-11-1998";
    public static final String TEST_TOKEN = "abc123";

    //========================================== UserDetailServiceImplTest

    public static final String TEST_NOT_REGISTERED_USER = "gosho";
    public static final String MESSAGE_USERNAME = "Username is not populated properly.";
    public static final String MESSAGE_USER = "User is not User.";
    public static final String MESSAGE_ADMINISTRATOR = "User is not Admin.";
    public static final String ROLE_ = "ROLE_";



    //========================================== TicketServiceImplTest

    public static final String SUCCESSFULLY_DELETED_TICKET = "Deleted ticket with ID: %d - successfully";


    //========================================== UserDetailServiceImplTest

    public static final String TEST_OFFER_TITLE = "Test offer";
    public static final String TEST_OFFER_DESCRIPTION = "This is created test offer!";
    public static final String TEST_OFFER_IMAGE_URL = "http://example.com/image.jpg";



    // ========================================== OfferControllerIT

    public static final String TEST_OFFER_TITLE_1 = "Cinema Promo";
    public static final String TEST_OFFER_TITLE_2 = "School Promo";
    public static final String TEST_OFFER_TITLE_3 = "Business Promo";
}
