package bg.softuni.mycinematicketsapp.models.dtos;

public class UpdateTicketDto {

    private String[][] seats;

    public UpdateTicketDto() {}

    public UpdateTicketDto(int rows, int cols) {
        this.seats = new String[rows][cols];
    }

    public String[][] getSeats() {
        return seats;
    }

    public UpdateTicketDto setSeats(String[][] seats) {
        this.seats = seats;
        return this;
    }

    public int getCountOfTickets(String[][] matrix) {
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String selectedSeat = matrix[i][j];

                if (selectedSeat != null) {
                    count++;
                }
            }
        }
        return count;
    }
}
