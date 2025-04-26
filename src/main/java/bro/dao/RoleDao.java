package bro.dao;

import bro.entity.RoleEntity;

public interface RoleDao {

    public RoleEntity findRoleByName(String theRoleName);
}
