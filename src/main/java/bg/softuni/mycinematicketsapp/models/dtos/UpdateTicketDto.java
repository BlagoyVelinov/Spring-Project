package bg.softuni.mycinematicketsapp.models.dtos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateTicketDto {

    private Map<Integer, List<Integer>> seats;


    public UpdateTicketDto() {
        this.seats = new LinkedHashMap<>();
        this.initSeatsKeys();
    }

    public Map<Integer, List<Integer>> getSeats() {
        return seats;
    }

    public UpdateTicketDto setSeats(Map<Integer, List<Integer>> seats) {
        this.seats = seats;
        return this;
    }

    public int getCountOfTickets() {
        AtomicInteger count = new AtomicInteger();

        seats.forEach((row, col) -> {
            col.forEach(seat-> {
                if (seat > 0) {
                    count.getAndIncrement();
                }
            });
        });

        return count.get();
    }

    public void initSeatsKeys() {
        this.seats.put(1, new ArrayList<>());
//        this.seats.put(2, new ArrayList<>());
//        this.seats.put(3, new ArrayList<>());
//        this.seats.put(4, new ArrayList<>());
//        this.seats.put(5, new ArrayList<>());
//        this.seats.put(6, new ArrayList<>());
//        this.seats.put(7, new ArrayList<>());
//        this.seats.put(8, new ArrayList<>());
//        this.seats.put(9, new ArrayList<>());
//        this.seats.put(10,new ArrayList<>());
    }
}
