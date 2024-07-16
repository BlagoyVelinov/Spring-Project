package bg.softuni.mycinematicketsapp.testutils;


import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Component("TestDataUtils")
public class TestDataUtil {

    @Autowired
    private OrderRepository orderRepository;


    public Order createTestOrder(UserEntity testUser) {
        String lUUID = String.format("%020d", new BigInteger(
                UUID.randomUUID().toString().replace("-", ""), 16));
        lUUID = lUUID.substring(20);

        LocalDate projectionDate = LocalDate.of(2024, 7, 20);

        City city = new City(CityName.SOFIA);

        Order order = new Order()
                .setOrderNumber(lUUID)
                .setCity(city)
                .setProjectionDate(projectionDate)
                .setUser(testUser);

        return this.orderRepository.save(order);
    }
    public void cleanUp() {
        orderRepository.deleteAll();
    }
}
