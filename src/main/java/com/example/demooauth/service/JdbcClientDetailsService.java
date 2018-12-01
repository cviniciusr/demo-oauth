package com.example.demooauth.service;

import com.example.demooauth.model.CustomClientDetails;
import com.example.demooauth.repository.CustomClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JdbcClientDetailsService implements ClientDetailsService {

    @Autowired
    private CustomClientDetailsRepository customClientDetailsRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {
            CustomClientDetails client = customClientDetailsRepository.findByClientId(clientId);

            List<String> resources = new ArrayList<>();
            client.getResourceIds().forEach(resourceId -> resources.add(resourceId.getNome()));
            String resourceIds = resources.stream().collect(Collectors.joining(","));

            List<String> scopes = new ArrayList<>();
            client.getScopes().forEach(scope -> scopes.add(scope.getScope()));
            String scopesStr = scopes.stream().collect(Collectors.joining(","));

            List<String> grantTypeList = new ArrayList<>();
            client.getAuthorizedGrantTypes().forEach(grantType -> grantTypeList.add(grantType.getAuthorizedGrantType()));
            String grantTypes = grantTypeList.stream().collect(Collectors.joining(","));

            List<String> authorityList = new ArrayList<>();
            client.getAuthorities().forEach(authority -> authorityList.add(authority.getAuthority()));
            String autorities = authorityList.stream().collect(Collectors.joining(","));

            BaseClientDetails base = new BaseClientDetails(
                    client.getClientId(), resourceIds, scopesStr, grantTypes, autorities);
            base.setClientSecret(client.getClientSecret());
            base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
            base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
            base.setAutoApproveScopes(scopes);

            return base;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
