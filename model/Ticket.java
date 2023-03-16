package cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Ticket {
    private UUID token;
    private int row;
    private int column;
    private int price;

    public Ticket(Seat seat) {
        this.token = UUID.randomUUID();
        this.row = seat.getRow();
        this.column = seat.getColumn();
        this.price = seat.getPrice();
    }
}
