package cinema.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ReturnedTicketRequestDTO {
    private UUID token;
}
