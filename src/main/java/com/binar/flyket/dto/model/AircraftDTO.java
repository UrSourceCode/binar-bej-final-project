package com.binar.flyket.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class AircraftDTO {
    private Integer id;
    private String type;
}
