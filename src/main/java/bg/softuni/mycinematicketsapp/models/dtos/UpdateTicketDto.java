package bg.softuni.mycinematicketsapp.models.dtos;

public class UpdateTicketDto {

    private boolean[][] seats;

    public UpdateTicketDto() {
        this.seats = new boolean[10][20];
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public UpdateTicketDto setSeats(boolean[][] seats) {
        this.seats = seats;
        return this;
    }
}
