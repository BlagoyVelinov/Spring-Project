package bg.softuni.mycinematicketsapp.util;

import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("origin")
public class FirstInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final CityService cityService;
    private final UserService userService;

    @Autowired
    public FirstInit(UserRoleService userRoleService, CityService cityService, UserService userService) {
        this.userRoleService = userRoleService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoleInDb();
        this.cityService.initCitiesNamesInDb();
        this.userService.initAdminUserInDb();
    }

}
