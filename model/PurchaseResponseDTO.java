package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@JsonPropertyOrder({
        "token",
        "row",
        "column",
        "price"
})

@Data
@Getter
@Setter
@AllArgsConstructor
public class PurchaseResponseDTO {
    private UUID token;
    @JsonProperty("ticket")
    private Seat seat;
}
