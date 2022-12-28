package com.binar.flyket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String userId;
    private String email;
    private String token;
}
