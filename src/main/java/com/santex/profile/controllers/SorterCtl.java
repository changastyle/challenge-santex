package com.santex.profile.controllers;

import com.santex.profile.dto.AreaDTO;
import com.santex.profile.dto.HierachyDTO;
import com.santex.profile.dto.LevelDTO;
import com.santex.profile.dto.UserDTO;
import com.santex.profile.model.UserProfile;
import com.santex.profile.services.UserService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/sort")
public class SorterCtl
{
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    @CrossOrigin
    public Map sort()
    {
        List<UserProfile> arrUsers = (List<UserProfile>) userService.findAll();
//        List<UserDTO> arrSorted = arrUsers.stream().map(user ->  (UserDTO) new UserDTO(user).toDTO(user)).collect(Collectors.toList());


        List<String> arrAreas = StreamEx.of(arrUsers).distinct(u -> u.getFunction()).map(u ->  u.getFunction()).toList();

//        arrAreas = (List<String>) arrAreas.stream().sorted();
        Map map=new HashMap();
        for(String areaLoop : arrAreas)
        {
            List<UserDTO> arrUsersByArea = arrUsers.stream().filter( u -> u.getFunction().equalsIgnoreCase(areaLoop)).sorted(Comparator.comparingInt(u -> u.getOrganizationLevel())).map(user -> { return (UserDTO) new UserDTO(user).toDTO(user);}).collect(Collectors.toList());
            if(arrUsersByArea != null && arrUsersByArea.size() > 0 )
            {
                map.put(areaLoop,arrUsersByArea);
            }
        }

//        List<UserDTO> arrSortedDTO = arrSorted.stream().map(user -> (UserDTO) new UserDTO(user).toDTO(user)).collect(Collectors.toList());
        return map;
    }
    @GetMapping(value = "/2")
    @CrossOrigin
    public HierachyDTO sort2()
    {
        HierachyDTO hierachy;
        List<LevelDTO> arrRta = new ArrayList<>();
        List<UserProfile> arrUsers = (List<UserProfile>) userService.findAll();

        List<Integer> arrLevels = StreamEx.of(arrUsers).distinct(u -> u.getOrganizationLevel()).map(u ->  u.getOrganizationLevel()).toList();
        List<String> arrAreas = StreamEx.of(arrUsers).distinct(u -> u.getFunction()).map(u ->  u.getFunction()).toList();

        Map mapGlobal = new HashMap();

        Map mapLevel = new HashMap();

        List<AreaDTO> arrAreasDTO = new ArrayList<>();

        for (String areaLoop : arrAreas)
        {
            AreaDTO areaDTOLoop = new AreaDTO(areaLoop);

            for(int levelLoop : arrLevels)
            {
                List<UserDTO> arrUsersByLevel = arrUsers.stream().filter(u->u.getFunction().equalsIgnoreCase(areaLoop)).filter( u -> u.getOrganizationLevel()==levelLoop).map(user -> { return (UserDTO) new UserDTO(user).toDTO(user);}).collect(Collectors.toList());

                LevelDTO levelDTO = new LevelDTO("LEVEL " + levelLoop,arrUsersByLevel);
                areaDTOLoop.addLevel(levelDTO);
            }

            arrAreasDTO.add(areaDTOLoop);
        }
        // 1 - ITERATE OVER LEVELS:
//        for(int levelLoop : arrLevels)
//        {
//            mapLevel = new HashMap();
//
////            List<UserProfile> arrUsersByLevel = arrUsers.stream().filter( u -> u.getOrganizationLevel()==levelLoop).collect(Collectors.toList());
//
//                List<UserDTO> arrUsersByFunction = arrUsersByLevel.stream().filter( u -> u.getFunction().equalsIgnoreCase(areaLoop)).sorted(Comparator.comparingInt(u -> u.getOrganizationLevel())).map(user -> { return (UserDTO) new UserDTO(user).toDTO(user);}).collect(Collectors.toList());
////
//                mapLevel.put(areaLoop, arrUsersByFunction);
//            }
//            for(String areaLoop : arrAreas)
//            {
//
//
//                List<UserProfile> arrUsersByLevel = arrUsers.stream().filter( u -> u.getOrganizationLevel()==levelLoop).collect(Collectors.toList());
//                if(arrUsersByLevel != null && arrUsersByLevel.size() > 0 )
//                {
//                    List<UserDTO> arrSortedByFunction = arrUsersByLevel.stream().filter( u -> u.getFunction().equalsIgnoreCase(areaLoop)).sorted(Comparator.comparingInt(u -> u.getOrganizationLevel())).map(user -> { return (UserDTO) new UserDTO(user).toDTO(user);}).collect(Collectors.toList());
//                    arrX.addAll(arrSortedByFunction);
//
//
//                }
//            }


//            mapLevel.put(("LEVEL " + levelLoop),arrX);

//            arrRta.add(new LevelDTO("LEVEL "+ levelLoop,mapLevel));
//            mapGlobal.put(, mapLevel );
//        }

        hierachy  = new HierachyDTO(arrAreasDTO);
//        List<UserDTO> arrSortedDTO = arrSorted.stream().map(user -> (UserDTO) new UserDTO(user).toDTO(user)).collect(Collectors.toList());
        return hierachy;
    }

}
