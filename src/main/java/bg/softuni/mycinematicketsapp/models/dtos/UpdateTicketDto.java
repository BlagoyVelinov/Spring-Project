package bg.softuni.mycinematicketsapp.models.dtos;

public class UpdateTicketDto {

    private boolean[][] seats;

    public UpdateTicketDto() {}

    public UpdateTicketDto(int rows, int cols) {
        this.seats = new boolean[rows][cols];
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public UpdateTicketDto setSeats(boolean[][] seats) {
        this.seats = seats;
        return this;
    }

    public int getCountOfTickets(boolean[][] matrix) {
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                boolean isSelectedSeat = matrix[i][j];

                if (isSelectedSeat) {
                    count++;
                }
            }
        }
        return count;
    }
}
