package com.santex.profile.dto;

import com.santex.profile.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO extends BaseDTO
{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String function;
    private int organizationLevel;
    private int fkManager;
    private String firstNameManager;
    private String lastNameManager;

    public UserDTO(UserProfile userProfile)
    {
        if(userProfile != null)
        {
            if(userProfile.getManager() != null)
            {
                UserProfile manager = userProfile.getManager();

                if(manager != null)
                {
                    this.fkManager = manager.getId();
                    this.firstNameManager = manager.getFirstName();
                    this.lastNameManager = manager.getLastName();
                }
            }

        }
    }
}
