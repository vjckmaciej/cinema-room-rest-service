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
    private List<Seat> availableSeats = new ArrayList<>();

    public CinemaRoom(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        availableSeats = IntStream.rangeClosed(1, numberOfRows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, numberOfColumns)
                        .mapToObj(num -> new Seat(row, num)))
                .collect(Collectors.toList());
    }
}
