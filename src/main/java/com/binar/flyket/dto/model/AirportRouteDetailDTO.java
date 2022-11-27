package com.binar.flyket.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class AirportRouteDetailDTO {
    private String id;
    private AirportDetailDTO from;
    private AirportDetailDTO to;
    private Integer hours;
    private Integer minutes;
}
