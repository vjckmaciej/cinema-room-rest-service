package cinema.service;

import cinema.exception.SeatNotAvailableException;
import cinema.exception.WrongPasswordException;
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
    private final String secretPassword = "super_secret";
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

    public CinemaRoomDTO getSizeOfCinemaAndAvailableSeats() {
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
        for (Ticket ticket : cinemaRoom.getPurchasedTickets()) {
            if (ticket.getRow() == purchaseRequestSeat.getRow() && ticket.getColumn() == purchaseRequestSeat.getColumn()) {
                throw new SeatNotAvailableException("The ticket has been already purchased!");
            }
        }
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == purchaseRequestSeat.getRow() && seat.getColumn() == purchaseRequestSeat.getColumn()) {
                if (!seat.isTaken()) {
                    seat.setTaken(true);
                    Ticket purchasedTicket = new Ticket(seat);
                    cinemaRoom.getPurchasedTickets().add(purchasedTicket);
                    cinemaRoom.getAvailableSeats().remove(seat);
                    return toPurchaseResponseDto(purchasedTicket.getToken(), seat);
                }
            }
        }
        throw new RuntimeException();
    }

    public ReturnedTicketResponseDTO returnTicket(ReturnedTicketRequestDTO returnRequestToken) {
        int rowOfSeat;
        int columnOfSeat;
        for (Ticket ticket : cinemaRoom.getPurchasedTickets()) {
            if (ticket.getToken().equals(returnRequestToken.getToken())) {
                cinemaRoom.getPurchasedTickets().remove(ticket);
                rowOfSeat = ticket.getRow();
                columnOfSeat = ticket.getColumn();
                Seat freeSeat = new Seat(rowOfSeat, columnOfSeat,  rowOfSeat <= 4 ? 10 : 8, false);
                cinemaRoom.getAvailableSeats().add(freeSeat);
                return new ReturnedTicketResponseDTO(freeSeat);
            }
        }
        // If no ticket with the specified token was found in the purchasedTickets list
        throw new SeatNotAvailableException("Wrong token!");
    }

    public StatsDTO getStatistics(String password) {
        if (password == null || !password.equals(secretPassword)) {
            throw new WrongPasswordException("The password is wrong!");
        } else {
            int numberOfSeatsAvailable = cinemaRoom.getAvailableSeats().size();
            int numberOfTicketsPurchased = cinemaRoom.getPurchasedTickets().size();
            int incomeFromTickets = 0;
            for (Ticket ticket : cinemaRoom.getPurchasedTickets()) {
                incomeFromTickets += ticket.getPrice();
            }
            return new StatsDTO(incomeFromTickets, numberOfSeatsAvailable, numberOfTicketsPurchased);
        }
    }
}
