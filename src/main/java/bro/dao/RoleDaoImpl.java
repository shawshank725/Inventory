package bro.dao;

import bro.entity.RoleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao{

    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public RoleEntity findRoleByName(String theRoleName) {
        TypedQuery<RoleEntity> theQuery = entityManager.createQuery(
                "from RoleEntity where role_name = :roleName", RoleEntity.class);

        theQuery.setParameter("roleName", theRoleName);

        RoleEntity theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }
}
