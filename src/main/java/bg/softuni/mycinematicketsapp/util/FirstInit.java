package bg.softuni.mycinematicketsapp.util;

import bg.softuni.mycinematicketsapp.services.CategoryService;
import bg.softuni.mycinematicketsapp.services.MovieClassService;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final CategoryService categoryService;
    private final MovieClassService movieClassService;
    @Autowired
    public FirstInit(UserRoleService userRoleService,
                     CategoryService categoryService, MovieClassService movieClassService) {
        this.userRoleService = userRoleService;
        this.categoryService = categoryService;
        this.movieClassService = movieClassService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoleInDb();
        this.categoryService.initCategoryInDb();
        this.movieClassService.initMovieClassesInDb();
    }

}
