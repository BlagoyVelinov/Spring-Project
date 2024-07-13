package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/program")
public class ProgramRestController {

    //TODO: This controller should be REST!!!

    private final OrderService programService;
    @Autowired
    public ProgramRestController(OrderService programService) {
        this.programService = programService;
    }


}
