package bg.softuni.mycinematicketsapp.util;

import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirstInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final CityService cityService;

    @Autowired
    public FirstInit(UserRoleService userRoleService, CityService cityService) {
        this.userRoleService = userRoleService;
        this.cityService = cityService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.initRoleInDb();
        this.cityService.initCitiesNamesInDb();

    }

}
