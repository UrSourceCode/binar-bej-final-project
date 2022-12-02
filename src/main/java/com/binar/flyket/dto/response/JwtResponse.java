package com.binar.flyket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String email;
    private Object role;
    private String token;
}
