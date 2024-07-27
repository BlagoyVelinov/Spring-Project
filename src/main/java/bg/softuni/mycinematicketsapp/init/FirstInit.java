package bg.softuni.mycinematicketsapp.init;

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

        //Try with Array

//        int[][] integer = new int[3][6];
//        integer[0][1] = 1;
//        for (int i = 0; i < integer.length; i++)  {
//            for (int j = 0; j < integer[0].length; j++) {
//                integer[i][j] += 1;
//                int selected = integer[i][j];
//                if (selected != 0) {
//                    System.out.println("===================");
//                    System.out.println("work correct!");
//                    System.out.println("===================");
//
//                }
//            }
//        }

        //Try with List

//        List<List<Integer>> mainList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ArrayList<Integer> intList = new ArrayList<>();
//            for (int k = 0; k < 20; k++) {
//                intList.add(0);
//            }
//            mainList.add(intList);
//        }
    }

}
