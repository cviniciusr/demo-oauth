package com.example.demooauth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String scope;
    @ManyToMany(mappedBy = "scopes", fetch = FetchType.LAZY)
    private List<CustomClientDetails> customClientDetails;
}
