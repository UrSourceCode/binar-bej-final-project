package com.binar.flyket.dto.model;

import com.binar.flyket.model.user.ERoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private ERoles eRoles;
}
