package com.binar.flyket.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class InputAirportRequest {
    @NotNull
    private String IATACode;
    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String countryCode;
}
