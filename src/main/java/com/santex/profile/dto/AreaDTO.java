package com.santex.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaDTO
{
    private String name;
    private List<LevelDTO> arrLevels;

    public AreaDTO(String name)
    {
        this.name = name;
    }

    public void addLevel(LevelDTO level)
    {
        if(arrLevels == null)
        {
            arrLevels = new ArrayList<>();
        }
        arrLevels.add(level);
    }
}
