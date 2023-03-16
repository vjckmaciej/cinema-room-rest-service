package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class StatsDTO {
    @JsonProperty("current_income")
    private int sumOfPrices;

    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;

    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedTickets;

}
