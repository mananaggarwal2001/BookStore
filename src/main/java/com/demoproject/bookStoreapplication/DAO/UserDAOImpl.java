package com.demoproject.bookStoreapplication.DAO;

import com.demoproject.bookStoreapplication.DTO.RegisterUser;
import com.demoproject.bookStoreapplication.databaseClasses.Register;
import com.demoproject.bookStoreapplication.databaseClasses.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addUser(Register user) {
        entityManager.persist(user);
    }

    @Override
    public Register findUser(String username) {
        TypedQuery<Register> registerTypedQuery = entityManager.createQuery("from Register " + "where username=:name", Register.class);
        registerTypedQuery.setParameter("name", username);
        Register resultuser;
        try {
            resultuser = registerTypedQuery.getSingleResult();
        } catch (Exception e) {
            resultuser = null;
        }
        return resultuser;
    }

    @Override
    public Roles findRoleByName(String roleName) {
        TypedQuery<Roles> createQuery = entityManager.createQuery("from Roles " + "where role=:rname", Roles.class);
        createQuery.setParameter("rname", roleName);
        return createQuery.getSingleResult();
    }
}
