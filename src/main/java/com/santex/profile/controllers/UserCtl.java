package com.santex.profile.controllers;

import com.santex.profile.dto.UserDTO;
import com.santex.profile.dto.UserSaveDTO;
import com.santex.profile.model.UserProfile;
import com.santex.profile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
