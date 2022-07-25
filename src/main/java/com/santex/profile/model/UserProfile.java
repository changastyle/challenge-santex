package com.santex.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity @Table(name = "users")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class UserProfile
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String function;
    private int organizationLevel;

    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "fkManager")
    @Nullable
    @JsonIgnore
    private UserProfile manager;

    @Override
    public String toString()
    {
        return "";
    }
}
