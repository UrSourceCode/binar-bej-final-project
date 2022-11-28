package com.binar.flyket.dto.request;


import com.binar.flyket.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisRequest {
    @NotNull
    @Size(min = 5, max = 20)
    private String title;

    @NotNull
    @Size(min = 5, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 20)
    private String lastName;

    @NotNull
    @Pattern(regexp = Constants.EMAIL_PATTERN)
    private String email;

    private String phoneNumber;

    @NotNull
    @Size(min = 7, max = 20)
    private String password;

    @NotNull
    private String roleName;
}
