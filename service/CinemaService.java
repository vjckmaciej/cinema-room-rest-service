package cinema.service;

import cinema.exception.SeatNotAvailableException;
import cinema.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private final ModelMapper modelMapper;
    private final CinemaRoom cinemaRoom = new CinemaRoom(ROWS, COLUMNS);

    public CinemaService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CinemaRoomDTO toCinemaRoomDto(CinemaRoom cinemaRoom) {
        return modelMapper.map(cinemaRoom, CinemaRoomDTO.class);
    }

    public PurchaseResponseDTO toPurchaseResponseDto(UUID token, Seat seat) {
        return new PurchaseResponseDTO(token, seat);
    }

    public CinemaRoomDTO getAvailableSeats() {
        List<Seat> allAvailableSeats = cinemaRoom.getAvailableSeats();
        allAvailableSeats = allAvailableSeats.stream().filter(seat -> !seat.isTaken())
                .collect(Collectors.toList());
        cinemaRoom.setAvailableSeats(allAvailableSeats);
        return toCinemaRoomDto(this.cinemaRoom);
    }

    public List<Ticket> getPurchasedTickets() {
        return cinemaRoom.getPurchasedTickets();
    }

    public PurchaseResponseDTO purchaseTicket(Seat purchaseRequestSeat) {
        if (purchaseRequestSeat.getRow() > cinemaRoom.getNumberOfRows() || purchaseRequestSeat.getRow() < 0 ||
                purchaseRequestSeat.getColumn() > cinemaRoom.getNumberOfColumns() || purchaseRequestSeat.getColumn() < 0) {
            throw new SeatNotAvailableException("The number of a row or a column is out of bounds!");
        }
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == purchaseRequestSeat.getRow() && seat.getColumn() == purchaseRequestSeat.getColumn()) {
                if (!seat.isTaken()) {
                    takeSeat(seat, true);
                    Ticket purchasedTicket = new Ticket(seat);
                    cinemaRoom.getPurchasedTickets().add(purchasedTicket);
                    return toPurchaseResponseDto(purchasedTicket.getToken(), seat);
                } else {
                    throw new SeatNotAvailableException("The ticket has been already purchased!");
                }
            }
        }
        throw new RuntimeException();
    }

    public ReturnedTicketResponseDTO returnTicket(ReturnedTicketRequestDTO returnRequestToken) {
        for (Ticket ticket : cinemaRoom.getPurchasedTickets()) {
            if (ticket.getToken().equals(returnRequestToken.getToken())) {
                cinemaRoom.getPurchasedTickets().remove(ticket);
                int rowOfSeatToReturn = ticket.getRow();
                int columnOfSeatToReturn = ticket.getColumn();
                for (Seat seat : cinemaRoom.getAvailableSeats()) {
                    if (seat.getRow() == rowOfSeatToReturn && seat.getColumn() == columnOfSeatToReturn) {
                        takeSeat(seat, false);
                        cinemaRoom.getAvailableSeats().add(seat);
                        return new ReturnedTicketResponseDTO(seat);
                    }
                }
            }
        }
        // If no ticket with the specified token was found in the purchasedTickets list
        throw new SeatNotAvailableException("Wrong token!");
    }

    public void takeSeat(Seat seatToUpdate, boolean takeThatSeat) {
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == seatToUpdate.getRow() && seat.getColumn() == seatToUpdate.getColumn()) {
                seat.setTaken(takeThatSeat);
                break;
            }
        }
    }
}
