package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
        "row",
        "column"
})

@Data
@NoArgsConstructor
@Getter
@Setter
public class PurchaseRequestDTO {
    private int row;
    private int column;
}
