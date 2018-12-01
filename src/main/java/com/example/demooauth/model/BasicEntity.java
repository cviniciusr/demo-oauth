package com.example.demooauth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

import static lombok.AccessLevel.PROTECTED;

public class BasicEntity {

    @Getter(PROTECTED)
    @Setter(PROTECTED)
    @Transient
    protected String _class;
}
