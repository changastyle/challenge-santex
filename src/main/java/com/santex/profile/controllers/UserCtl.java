package com.santex.profile.controllers;

import com.santex.profile.dto.*;
import com.santex.profile.model.UserProfile;
import com.santex.profile.services.UserService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserCtl
{
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    @CrossOrigin
    public List<UserDTO> findAll()
    {
        List<UserDTO>  arrUsersDTO =  userService.findAll().stream().map(user ->  (UserDTO) new UserDTO(user).toDTO(user)).collect(Collectors.toList());
        return arrUsersDTO;

    }

    @PostMapping()
    @CrossOrigin
    public UserProfile create(@RequestBody UserSaveDTO userDTO)
    {
        UserProfile userProfile = (UserProfile) userDTO.toEntity(UserProfile.class);
        return userService.save(userProfile);
    }

    @GetMapping(value= "/promote/{fkUser}")
    @CrossOrigin
    public UserProfile promote(@PathVariable(name = "fkUser") int fkUser)
    {
        UserProfile userProfile = userService.promote(fkUser);
        return userService.save(userProfile);
    }

    @GetMapping(value = "/sort")
    @CrossOrigin
    public HierachyDTO sort()
    {
        HierachyDTO hierachy;
        List<UserProfile> arrUsers = (List<UserProfile>) userService.findAll();

        List<Integer> arrLevels = StreamEx.of(arrUsers).distinct(u -> u.getOrganizationLevel()).map(u ->  u.getOrganizationLevel()).toList();
        List<String> arrAreas = StreamEx.of(arrUsers).distinct(u -> u.getFunction()).map(u ->  u.getFunction()).toList();

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

        hierachy  = new HierachyDTO(arrAreasDTO);

        return hierachy;
    }
}
