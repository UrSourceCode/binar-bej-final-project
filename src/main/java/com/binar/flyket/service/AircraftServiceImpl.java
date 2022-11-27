package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.AircraftMapper;
import com.binar.flyket.dto.model.AircraftDTO;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Aircraft;
import com.binar.flyket.repository.AircraftRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftServiceImpl implements AircraftService{
    private final AircraftRepository aircraftRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public boolean addAircraft(AircraftDTO aircraftDTO) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftDTO.getId());
        if (aircraft.isEmpty()) {
            Aircraft aircraftModel = new Aircraft();
            aircraftModel.setId(aircraftDTO.getId());
            aircraftModel.setType(aircraftDTO.getType());
            return true;
        }
        throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
    }

    @Override
    public List<AircraftDTO> addAircraft(List<AircraftDTO> aircraftDTO) {
        List<Aircraft> manyAircraft = aircraftDTO.stream()
                .map(aircraft -> {
                    Aircraft aircraftModel = new Aircraft();
                    aircraftModel.setId(aircraft.getId());
                    aircraftModel.setType(aircraft.getType());
                    return aircraftModel;
                }).toList();
        aircraftRepository.saveAll(manyAircraft);
        return aircraftDTO;
    }

    @Override
    public AircraftDTO deleteAircraft(Integer aircraftID) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftID);
        if (aircraft.isPresent()) {
            aircraftRepository.delete(aircraft.get());
            return AircraftMapper.toDto(aircraft.get());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.AIRCRAFT_NOT_FOUND);
    }

    @Override
    public AircraftDTO updateAircraft(Integer aircraftID, AircraftDTO aircraftDTO) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftID);
        if (aircraft.isPresent()) {
            Aircraft aircraftModel = new Aircraft();
            aircraftModel.setId(aircraftID);
            aircraftModel.setType(aircraftDTO.getType());
            return AircraftMapper.toDto(aircraftModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.AIRCRAFT_NOT_FOUND);
    }

    @Override
    public AircraftDTO getAircraftById(Integer aircraftID) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftID);
        if (aircraft.isPresent())
            return AircraftMapper.toDto(aircraft.get());
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.AIRCRAFT_NOT_FOUND);
    }

    @Override
    public List<AircraftDTO> getAircraft() {
        return aircraftRepository.findAll().stream()
                .map(AircraftMapper::toDto).toList();
    }
}
