package com.binar.flyket.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
