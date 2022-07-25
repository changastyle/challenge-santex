package com.santex.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveDTO extends BaseDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private int organizationLevel;
}
