package cinema.service;

import cinema.exception.SeatNotAvailableException;
import cinema.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public TicketDTO toTicketDto(Seat seat) {
        return modelMapper.map(seat, TicketDTO.class);
    }
    public CinemaRoomDTO getAvailableSeats() {
        List<Seat> allAvailableSeats = cinemaRoom.getAvailableSeats();
        allAvailableSeats = allAvailableSeats.stream().filter(seat -> !seat.isTaken())
                .collect(Collectors.toList());
        cinemaRoom.setAvailableSeats(allAvailableSeats);
        return toCinemaRoomDto(this.cinemaRoom);
    }

    public TicketDTO purchaseTicket(PurchaseRequestDTO purchaseRequestDTO) {
        if (purchaseRequestDTO.getRow() > cinemaRoom.getNumberOfRows() || purchaseRequestDTO.getRow() < 0 ||
                purchaseRequestDTO.getColumn() > cinemaRoom.getNumberOfColumns() || purchaseRequestDTO.getColumn() < 0) {
                    throw new SeatNotAvailableException("The number of a row or a column is out of bounds!");
        }
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == purchaseRequestDTO.getRow() && seat.getColumn() == purchaseRequestDTO.getColumn()) {
                if (!seat.isTaken()) {
                    updateSeatToTaken(seat);
                    return toTicketDto(seat);
                } else {
                    throw new SeatNotAvailableException("The ticket has been already purchased!");
                }
            }
        }
        throw new RuntimeException();
    }

    public void updateSeatToTaken(Seat seatToUpdate) {
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == seatToUpdate.getRow() && seat.getColumn() == seatToUpdate.getColumn()) {
                seat.setTaken(true);
                break;
            }
        }
    }
}
