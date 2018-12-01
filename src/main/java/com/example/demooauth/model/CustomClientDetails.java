package com.example.demooauth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class CustomClientDetails {

    @Id
    private String id;

    @NotNull
    private String clientId;
    private String clientSecret;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "custom_client_resource_id",
        joinColumns = @JoinColumn(name = "custom_client_id"),
        inverseJoinColumns = @JoinColumn(name = "resource_id_id")
    )
    private Set<ResourceId> resourceIds;
    private boolean secretRequired;
    private boolean scoped;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "custom_client_scope",
            joinColumns = @JoinColumn(name = "custom_client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<Scope> scopes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "custom_client_authorized_grant_type",
            joinColumns = @JoinColumn(name = "custom_client_id"),
            inverseJoinColumns = @JoinColumn(name = "authorized_grant_type_id")
    )
    private Set<AuthorizedGrantType> authorizedGrantTypes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "custom_client_authority",
            joinColumns = @JoinColumn(name = "custom_client_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;
    private Integer accessTokenValiditySeconds;
    private  Integer refreshTokenValiditySeconds;
    private boolean autoApprove;
}
