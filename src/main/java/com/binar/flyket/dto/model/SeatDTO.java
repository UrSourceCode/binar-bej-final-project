package com.binar.flyket.dto.model;

import com.binar.flyket.model.SeatNo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SeatDTO {
    private SeatNo id;
        private Boolean isAvailable;
}
