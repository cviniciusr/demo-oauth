package com.example.demooauth.repository;

import com.example.demooauth.model.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityGroupRepository extends JpaRepository<SecurityGroup, String> {

    List<SecurityGroup> getByCompanyId(String companyId);
}
