package com.example.demooauth.service;

import com.example.demooauth.model.SecurityGroup;
import com.example.demooauth.repository.SecurityGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityGroupService {

    @Autowired
    private SecurityGroupRepository securityGroupRepository;

    public List<SecurityGroup> listUserGroups(String companyId, String id) {
        return securityGroupRepository.getByCompanyId(companyId);
    }
}
