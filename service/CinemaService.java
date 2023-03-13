package cinema.service;

import cinema.model.CinemaRoom;
import cinema.model.CinemaRoomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private final ModelMapper modelMapper;

    public CinemaService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    public CinemaRoomDTO toCinemaRoomDto(CinemaRoom cinemaRoom) {
//        return new CinemaRoomDTO(cinemaRoom.getNumberOfRows(), cinemaRoom.getNumberOfColumns(), cinemaRoom.getAvailableSeats());
//    }
    public CinemaRoomDTO toCinemaRoomDto(CinemaRoom cinemaRoom) {
        return modelMapper.map(cinemaRoom, CinemaRoomDTO.class);
    }
    public CinemaRoomDTO getAvailableSeats() {
        CinemaRoom cinemaRoom = new CinemaRoom(ROWS, COLUMNS);
        return toCinemaRoomDto(cinemaRoom);
    }
}
