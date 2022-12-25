package com.binar.flyket.service;

import com.binar.flyket.dto.model.CountryDTO;
import com.binar.flyket.dummy.CountryDummies;
import com.binar.flyket.model.Country;
import com.binar.flyket.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CountryServiceImplTest {


    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddCountry() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Indonesia");
        countryDTO.setCode("ID");

        Mockito.when(countryRepository.findById(countryDTO.getCode())).thenReturn(Optional.empty());
        Mockito.when(countryRepository.save(CountryDummies.countryList().get(0))).thenReturn(CountryDummies.countryList().get(0));

        var actualValue = countryService.addCountry(countryDTO);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void testAddCountries() {
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setName("Indonesia");
        countryDTO1.setCode("ID");

        CountryDTO countryDTO2 = new CountryDTO();
        countryDTO2.setName("Japan");
        countryDTO2.setCode("JP");

        List<CountryDTO> listDto = new ArrayList<>();
        listDto.add(countryDTO1);
        listDto.add(countryDTO2);

        List<Country> listModel = listDto.stream().map(country -> {
            Country countryModel = new Country();
            countryModel.setName(country.getName());
            countryModel.setCode(country.getCode());
            return countryModel;
        }).toList();

        Mockito.when(countryRepository.saveAll(listModel)).thenReturn(listModel);

        var actualValue = countryService.addCountries(listDto);
        var expectedSize = 2;

        Assertions.assertEquals(expectedSize, actualValue.size());
    }

    @Test
    void testDeleteCountry() {
        String id = "ID";

        Mockito.when(countryRepository.findById(id))
                .thenReturn(Optional.of(CountryDummies.countryList().get(0)));
        Mockito.doNothing()
                .when(countryRepository).delete(CountryDummies.countryList().get(0));

        var actualValue = countryService.deleteCountry(id);
        var expectedCode = "ID";
        var expectedName = "Indonesia";

        Assertions.assertEquals(expectedCode, actualValue.getCode());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testUpdateCountry() {
        String countryCode = "ID";
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setName("indonesia");
        countryDTO1.setCode("ID");

        Mockito.when(countryRepository.findById(countryCode))
                .thenReturn(Optional.of(CountryDummies.countryList().get(0)));

        var actualValue = countryService.updateCountry(countryCode, countryDTO1);
        var expectedName = "indonesia";
        var expectedCode = "ID";

        Assertions.assertEquals(expectedCode, actualValue.getCode());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testGetCountryById() {
        String id = "ID";

        Mockito.when(countryRepository.findById(id))
                .thenReturn(Optional.of(CountryDummies.countryList().get(0)));

        var actualValue = countryService.getCountryById(id);
        var expectedCode = "ID";
        var expectedName = "Indonesia";

        Assertions.assertEquals(expectedCode, actualValue.getCode());
        Assertions.assertEquals(expectedName, actualValue.getName());
    }

    @Test
    void testGetCountries() {

        Mockito.when(countryRepository.findAll()).thenReturn(CountryDummies.countryList());

        var actualValue = countryService.getCountries();
        var expectedSize = 2;

        Assertions.assertEquals(expectedSize, actualValue.size());
    }

}