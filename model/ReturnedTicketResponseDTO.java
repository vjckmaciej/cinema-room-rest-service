package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReturnedTicketResponseDTO {
    @JsonProperty("returned_ticket")
    private Seat seat;
}
