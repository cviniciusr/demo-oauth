package com.example.demooauth.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_table")
public class User extends BasicEntity implements Serializable {

    @Id
    @NonNull
    private String id;

    @Column
    @NonNull
    private String username;

    @Column
    @NonNull
    private String companyId;

    @Column
    @NonNull
    private String password;

    @Column
    private Boolean isEnabled;

    @Column
    private Boolean isVisible;
}
