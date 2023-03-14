package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@JsonPropertyOrder({
        "numberOfRows",
        "numberOfColumns",
        "availableSeats"
})

@NoArgsConstructor //needed because we map CinemaRoom object to CinemaRoomDTO object with Mapper
@Getter
@Setter
public class CinemaRoomDTO {
    @JsonProperty("total_rows")
    private int numberOfRows;
    @JsonProperty("total_columns")
    private int numberOfColumns;
    @JsonProperty("available_seats")
    private List<Seat> availableSeats;
}
