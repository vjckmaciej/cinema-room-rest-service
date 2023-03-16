package cinema.controller;


import cinema.model.*;
import cinema.service.CinemaService;
import org.apache.tomcat.util.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @GetMapping("/purchasedTickets")
    public List<Ticket> getPurchasedTickets() {
        return cinemaService.getPurchasedTickets();
    }

    @PostMapping("/purchase")
    public PurchaseResponseDTO purchaseTicket(@RequestBody Seat purchaseRequestSeat) {
        return cinemaService.purchaseTicket(purchaseRequestSeat);
    }

    @PostMapping("/return")
    public ReturnedTicketResponseDTO returnTicket(@RequestBody ReturnedTicketRequestDTO returnRequestToken) {
        return cinemaService.returnTicket(returnRequestToken);
    }

}
