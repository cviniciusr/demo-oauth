package com.example.demooauth.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "security_group")
public class SecurityGroup extends BasicEntity implements Serializable {

    @Id
    private String id;

    @NonNull
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String companyId;

    @Transient
//    private List<String> users = new ArrayList<>();
    private List<String> users = Arrays.asList("User 1", "User 2", "User 3");

    @Column
    private boolean removed = false;
}
