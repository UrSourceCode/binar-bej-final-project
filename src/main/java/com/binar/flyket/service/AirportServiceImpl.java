package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.AirportMapper;
import com.binar.flyket.dto.model.AirportDTO;
import com.binar.flyket.dto.model.AirportDetailDTO;
import com.binar.flyket.dto.request.InputAirportRequest;
import com.binar.flyket.dto.request.UpdateAirportRequest;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Airport;
import com.binar.flyket.model.Country;
import com.binar.flyket.repository.AirportRepository;
import com.binar.flyket.repository.CountryRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    private final CountryRepository countryRepository;

    public AirportServiceImpl(AirportRepository airportRepository, CountryRepository countryRepository) {
        this.airportRepository = airportRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public InputAirportRequest addAirport(InputAirportRequest inputAirportRequest) {
        Optional<Airport> airport = airportRepository.findById(inputAirportRequest.getIATACode());
        if(airport.isEmpty()) {
            Optional<Country> country = countryRepository.findById(inputAirportRequest.getCountryCode());
            if(country.isPresent()) {
                Airport airportModel = new Airport();
                airportModel.setIATACode(inputAirportRequest.getIATACode());
                airportModel.setCity(inputAirportRequest.getCity());
                airportModel.setName(inputAirportRequest.getName());
                airportModel.setCountry(country.get());
                airportRepository.save(airportModel);
                return inputAirportRequest;
            }
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
    }

    @Override
    public AirportDTO deleteAirportById(String IATACode) {
        Optional<Airport> airport = airportRepository.findById(IATACode);
        if(airport.isPresent()) {
            Airport airportModel = airport.get();
            airportRepository.delete(airportModel);
            return AirportMapper.toDto(airportModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public AirportDetailDTO getAirportById(String IATACode) {
        Optional<AirportDetailDTO> airportDetailDTO = airportRepository.findByIATACode(IATACode);
        if(airportDetailDTO.isPresent()) {
            return airportDetailDTO.get();
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }

    @Override
    public List<AirportDetailDTO> getAirports() {
        List<AirportDetailDTO> airportList = airportRepository.findAllAirport();
        if(!airportList.isEmpty()) {
            return airportList;
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean updateAirport(String IATACode, UpdateAirportRequest updateAirportRequest) {
        Optional<Airport> airport = airportRepository.findById(IATACode.toUpperCase().trim());
        if(airport.isPresent()) {
            Airport airportModel = new Airport();
            airportModel.setName(updateAirportRequest.getName());
            airportModel.setCity(updateAirportRequest.getCity());
            airportModel.setIATACode(updateAirportRequest.getIATACode().toUpperCase().trim());
            airportRepository.save(airportModel);
            return true;
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
    }
}
