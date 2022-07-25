package com.santex.profile.controllers;

import com.santex.profile.dto.UserDTO;
import com.santex.profile.model.UserProfile;
import com.santex.profile.repo.UserRepo;
import com.santex.profile.util.ClaveValor;
import com.santex.profile.util.Xlsx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/db")
public class DBCtl
{

    @Autowired
    UserRepo userRepo;


    @PostMapping(value = "/populate")
    @CrossOrigin
    public List<UserProfile> populate(
            @RequestParam(value = "fileInput" , required = false, defaultValue = "C:\\Users\\Nico\\Desktop\\profile\\PEOPLE.xlsx") MultipartFile file
    )
    {
        List<UserProfile> arrUsers = new ArrayList<>();

        File convFile = new File(file.getOriginalFilename());

        try
        {
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("FILE RECIBIDO:" + convFile.getAbsolutePath());

//         1 - POPULATE OF USERS FROM EXCEL:
        List<List<ClaveValor>> arrPeople = Xlsx.read(convFile.getAbsolutePath(), 0, 1, true);
//        List<List<ClaveValor>> arrPeople = Xlsx.read("./PEOPLE.xlsx", 0, 1, true);

        log.info("EXCEL OF USERS : " + arrPeople.size());
        for (List<ClaveValor> row : arrPeople)
        {
            UserProfile userLoop = new UserProfile();
            for (ClaveValor cv : row)
            {
//                log.info(String.valueOf(cv));
                int id = -1;
                if (cv.getClave().equalsIgnoreCase("id"))
                {
                    id = Integer.parseInt(String.valueOf(cv.getValor()));
                }
                if (cv.getClave().equalsIgnoreCase("firstname"))
                {
                    userLoop.setFirstName((String) cv.getValor());
                }
                if (cv.getClave().equalsIgnoreCase("lastname"))
                {
                    userLoop.setLastName((String) cv.getValor());
                }
                if (cv.getClave().equalsIgnoreCase("email"))
                {
                    userLoop.setEmail((String) cv.getValor());
                }
                if (cv.getClave().equalsIgnoreCase("level"))
                {
                    userLoop.setOrganizationLevel(Integer.parseInt(String.valueOf(cv.getValor())));
                }
                if (cv.getClave().equalsIgnoreCase("function"))
                {
                    userLoop.setFunction(String.valueOf(cv.getValor()));
                }
                if (cv.getClave().equalsIgnoreCase("manager"))
                {
                    int fkManager = Integer.parseInt(String.valueOf(cv.getValor()));
//                    if(fkManager != -1 && fkManager != id)
//                    {
                        UserProfile managerDB = userRepo.getById(fkManager);

                        if(managerDB != null)
                        {
                            userLoop.setManager(managerDB);
                        }
//                    }

                }
            }
            arrUsers.add(userRepo.save(userLoop));
        }

        List<UserDTO> arrUsersDTO = new ArrayList<>();
//        if(arrUsers != null)
//        {
//            arrUsersDTO = arrUsers.stream().map(user -> {return (UserDTO) new UserDTO(user).toDTO(user);}).collect(Collectors.toList());
//        }

        return arrUsers;
    }
}
