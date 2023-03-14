package cinema.controller;

import cinema.model.CinemaRoomDTO;
import cinema.model.PurchaseRequestDTO;
import cinema.model.SeatDTO;
import cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {
    private CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public CinemaRoomDTO getAvailableSeats() {
        return cinemaService.getAvailableSeats();
    }

    @PostMapping("/purchase")
    public SeatDTO purchaseTicket(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return cinemaService.purchaseTicket(purchaseRequestDTO);
    }
}
