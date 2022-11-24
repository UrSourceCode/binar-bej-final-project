package com.binar.flyket.dto.request;

import com.binar.flyket.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    private String emailOrPhoneNumber;
    @NotNull
    private String password;
}
