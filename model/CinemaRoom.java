package cinema.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@NoArgsConstructor
public class CinemaRoom {
    private int numberOfRows;
    private int numberOfColumns;
    private List<Seat> availableSeats;
    private List<Ticket> purchasedTickets;

    public CinemaRoom(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.availableSeats = IntStream.rangeClosed(1, numberOfRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, numberOfColumns)
                        .mapToObj(num -> {
                            int price = row <= 4 ? 10 : 8;
                            return new Seat(row, num, price, false);
                        }))
                .collect(Collectors.toList());
        this.purchasedTickets = new ArrayList<>();
    }
}
