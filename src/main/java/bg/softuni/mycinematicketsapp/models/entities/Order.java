package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer numberOfRow;
    @Column(nullable = false)
    private Integer numberOfSeat;
    @Column(name = "voucher_number")
    private String voucherNumber;

    public TicketType getTicketType() {
        return ticketType;
    }

    public Order setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Order setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getNumberOfRow() {
        return numberOfRow;
    }

    public Order setNumberOfRow(Integer numberOfRow) {
        this.numberOfRow = numberOfRow;
        return this;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public Order setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        return this;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public Order setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
        return this;
    }
}
