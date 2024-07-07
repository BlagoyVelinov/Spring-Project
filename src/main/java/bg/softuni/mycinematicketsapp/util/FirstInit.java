package bg.softuni.mycinematicketsapp.util;

import bg.softuni.mycinematicketsapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final CategoryService categoryService;
    private final MovieClassService movieClassService;
    private final BookingTimeService bookingTimeService;
    @Autowired
    public FirstInit(UserRoleService userRoleService, CategoryService categoryService,
                     MovieClassService movieClassService, BookingTimeService bookingTimeService) {
        this.userRoleService = userRoleService;
        this.categoryService = categoryService;
        this.movieClassService = movieClassService;
        this.bookingTimeService = bookingTimeService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoleInDb();
        this.categoryService.initCategoryInDb();
        this.movieClassService.initMovieClassesInDb();
        this.bookingTimeService.initStartProjectionTimesInDb();
    }

}
