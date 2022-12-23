package com.binar.flyket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAirportRequest {
    @NotNull
    private String IATACode;

    @NotNull
    private String name;

    @NotNull
    private String city;
}
