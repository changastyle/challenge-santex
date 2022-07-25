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
public class HierachyDTO
{
//    private List<LevelDTO> arrLevels;
    private AreaDTO ceo;
    private List<AreaDTO> arrAreas;

    public HierachyDTO(List<AreaDTO> arrAreas)
    {
        autoPopulate(arrAreas);
    }

    public void autoPopulate(List<AreaDTO> arrAreas)
    {
        this.arrAreas = new ArrayList<>();

        for (AreaDTO areaLoop : arrAreas)
        {
            if(areaLoop.getName().equalsIgnoreCase("CEO"))
            {
                this.ceo = areaLoop;
            }
            else {
                this.arrAreas.add(areaLoop);
            }
        }
    }


    public void setArrAreas(List<AreaDTO> arrAreas)
    {
        autoPopulate(arrAreas);
    }
}
