package com.santex.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class LevelDTO
{
    private String name;
    private List<UserDTO> arrUsers;
}
