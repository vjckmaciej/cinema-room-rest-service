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
@NoArgsConstructor //needed because we map CinemaRoom object to CinemaRoomDTO object with Mapper
@Getter
@Setter
public class PurchaseResponseDTO {
    @JsonProperty
    private int row;
    @JsonProperty
    private int column;
    @JsonProperty
    private int price;

}
