package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
        "row",
        "column",
        "price"
})

@Data
@NoArgsConstructor //needed because we map Seat object to TicketDTO object with Mapper
@Getter
@Setter
public class TicketDTO {
    private int row;
    private int column;
    private int price;

}
