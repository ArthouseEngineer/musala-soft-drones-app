package com.musala.drones.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Data
@Table("users")
public class UserEntity {


    public UserEntity() {
    }

    @Id
    @Column("id")
    private Long id;

    @NonNull
    @Column("username")
    private String username;

    @NonNull
    @Column("password")
    private String password;

    @NonNull
    @Column("role")
    private String role;
}
