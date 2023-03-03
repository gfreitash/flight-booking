package com.gfreitash.flight_booking.services;

import com.gfreitash.flight_booking.dto.output.AirportOutputDTO;
import com.gfreitash.flight_booking.repositories.AirportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Optional<AirportOutputDTO> getAirportById(String id) {
        return airportRepository.findById(Integer.valueOf(id)).map(AirportOutputDTO::new);
    }

    public List<AirportOutputDTO> getAllAirports() {
        return airportRepository.findAll().stream().map(AirportOutputDTO::new).toList();
    }

    public Page<AirportOutputDTO> getAllAirports(Pageable pagination) {
        return airportRepository.findAll(pagination).map(AirportOutputDTO::new);
    }

}
