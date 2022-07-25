package com.santex.profile.services;

import com.santex.profile.model.UserProfile;
import com.santex.profile.repo.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
public class UserService
{

    @Autowired
    UserRepo userRepo;

    public Collection<UserProfile> findAll()
    {
        return userRepo.findAll();
    }
    @Transactional
    public UserProfile getById(int id)
    {
        return userRepo.getById(id);
    }
    @Transactional
    public UserProfile promote(int id)
    {
        UserProfile userProfileDB = getById(id);

        if(userProfileDB != null)
        {
            int level = userProfileDB.getOrganizationLevel();

            if(level > 1 && !userProfileDB.getFunction().equalsIgnoreCase("CEO"))

            if(level > 1)
            {
                level --;
            }

            UserProfile newBoss = null;
            UserProfile myManager = userProfileDB.getManager();
            String newFunction = userProfileDB.getFunction();
            if(myManager != null)
            {
                UserProfile myManagerManager = myManager.getManager();
                if(myManagerManager != null)
                {
                    newBoss = myManagerManager;
                    newFunction = myManager.getFunction();
                }
            }

            userProfileDB.setManager(newBoss);
            userProfileDB.setOrganizationLevel(level);
            userProfileDB.setFunction(newFunction);

            userProfileDB = save(userProfileDB);
        }
        return userProfileDB;
    }
    public UserProfile save(UserProfile userProfile)
    {
        return userRepo.save(userProfile);
    }
}
