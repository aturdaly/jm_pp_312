package jm.security.example.service;

import jm.security.example.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();
    Role getRoleById(Long id);
    Role getRoleByName(String role);
    void addRole(Role role);
    void deleteRole(Role role);
    void updateRole(Role role);
}
