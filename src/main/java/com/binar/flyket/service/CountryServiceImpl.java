package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.CountryMapper;
import com.binar.flyket.dto.model.CountryDTO;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Country;
import com.binar.flyket.repository.CountryRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean addCountry(CountryDTO countryDTO) {
        System.out.println(countryDTO.getCode());
        Optional<Country> country = countryRepository.findById(countryDTO.getCode());
        if(country.isEmpty()) {
            Country countryModel = new Country();
            countryModel.setCode(countryDTO.getCode());
            countryModel.setName(countryDTO.getName());
            countryRepository.save(countryModel);
            return true;
        }
        throw FlyketException.throwException(ExceptionType.DUPLICATE_ENTITY, HttpStatus.CONFLICT, Constants.ALREADY_EXIST_MSG);
    }

    @Override
    public List<CountryDTO> addCountries(List<CountryDTO> countryDTO) {
        List<Country> countries = countryDTO.stream()
                .map(country -> {
                    Country countryModel = new Country();
                    countryModel.setName(country.getName());
                    countryModel.setCode(country.getCode());
                    return countryModel;
                }).toList();
        countryRepository.saveAll(countries);
        return countryDTO;
    }

    @Override
    public CountryDTO deleteCountry(String countryCode) {
        Optional<Country> country = countryRepository.findById(countryCode);
        if(country.isPresent()) {
            countryRepository.delete(country.get());
            return CountryMapper.toDto(country.get());
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public CountryDTO updateCountry(String countryCode, CountryDTO countryDTO) {
        Optional<Country> country = countryRepository.findById(countryCode);
        if(country.isPresent()) {
            Country countryModel = new Country();
            countryModel.setCode(countryCode);
            countryModel.setName(countryDTO.getName());
            return  CountryMapper.toDto(countryModel);
        }
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public CountryDTO getCountryById(String countryCode) {
        Optional<Country> country = countryRepository.findById(countryCode);
        if(country.isPresent())
            return CountryMapper.toDto(country.get());
        throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.COUNTRY_NOT_FOUND);
    }

    @Override
    public List<CountryDTO> getCountries() {
        return countryRepository.findAll().stream()
                .map(CountryMapper::toDto).toList();
    }
}
