package cinema.service;

import cinema.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

//    public CinemaRoomDTO toCinemaRoomDto(CinemaRoom cinemaRoom) {
//
//        return new CinemaRoomDTO(cinemaRoom.getNumberOfRows(), cinemaRoom.getNumberOfColumns(), cinemaRoom.getAvailableSeats());
//    }

    public CinemaRoomDTO toCinemaRoomDto(CinemaRoom cinemaRoom) {
        return modelMapper.map(cinemaRoom, CinemaRoomDTO.class);
    }

    public SeatDTO toSeatDto(Seat seat) {
        return modelMapper.map(seat, SeatDTO.class);
    }
    public CinemaRoomDTO getAvailableSeats() {
      //  CinemaRoom cinemaRoom = new CinemaRoom(ROWS, COLUMNS);
        //        List<Seat> allAvailableSeats = cinemaRoomDTO.getAvailableSeats();
//        allAvailableSeats = allAvailableSeats.stream().filter(seat -> !seat.isTaken())
//                .collect(Collectors.toList());
//        cinemaRoomDTO.setAvailableSeats(allAvailableSeats);
        return toCinemaRoomDto(this.cinemaRoom);
    }

    public SeatDTO purchaseTicket(PurchaseRequestDTO purchaseRequestDTO) {
        if (purchaseRequestDTO.getRow() > cinemaRoom.getNumberOfRows() || purchaseRequestDTO.getRow() < 0 ||
                purchaseRequestDTO.getColumn() > cinemaRoom.getNumberOfColumns() || purchaseRequestDTO.getColumn() < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
        }
        for (Seat seat : cinemaRoom.getAvailableSeats()) {
            if (seat.getRow() == purchaseRequestDTO.getRow() && seat.getColumn() == purchaseRequestDTO.getColumn()) {
                if (!seat.isTaken()) {
                    updateSeatToTaken(seat);
                    return toSeatDto(seat);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "The ticket has been already purchased!");
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
