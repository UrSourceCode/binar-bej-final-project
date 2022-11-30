package com.binar.flyket.service;

import com.binar.flyket.dto.model.CountryDTO;

import java.util.List;

public interface CountryService {

    boolean addCountry(CountryDTO countryDTO);

    List<CountryDTO> addCountries(List<CountryDTO> countryDTO);

    CountryDTO deleteCountry(String countryCode);

    CountryDTO updateCountry(String countryCode, CountryDTO countryDTO);

    CountryDTO getCountryById(String countryCode);

    List<CountryDTO> getCountries();
}
