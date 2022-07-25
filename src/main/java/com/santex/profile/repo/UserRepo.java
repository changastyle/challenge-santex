package com.santex.profile.repo;

import com.santex.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<UserProfile,  Integer>
{

//    @Query("SELECT u FROM UserProfile u WHERE u.organizationLevel = :level AND u.function = :function")
//    public List<UserProfile> findBossOfLevel(@Param("level") int level,@Param("function") String function);
}
