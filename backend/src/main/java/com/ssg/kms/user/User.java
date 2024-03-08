package com.ssg.kms.user;

import java.util.HashSet;
import java.util.Set;

import com.ssg.kms.role.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank	// jakarta.validation.constraints.NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)    
//    private Set<UserRole> userRoles = new HashSet<>();
}