package com.ssg.kms.role;

import java.util.HashSet;
import java.util.Set;

import com.ssg.kms.user.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole role;
    
//    @OneToMany(mappedBy = "role")    
//    private Set<UserRole> userRoles = new HashSet<>();

}

