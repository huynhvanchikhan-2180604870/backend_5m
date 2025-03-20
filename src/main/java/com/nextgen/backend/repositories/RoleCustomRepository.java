package com.nextgen.backend.repositories;

import com.nextgen.backend.entites.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepository {
    @PersistenceContext
    private EntityManager em;

    public List<String> getAllRoles(User user){
        StringBuilder query = new StringBuilder()
                .append("select r.name from users u ")
                .append("join user_roles ur on u.id = ur.user_id ")
                .append("join roles r on r.id = ur.role_id ")
                .append("where 1 = 1");

        if(user.getEmail() != null){
            query.append(" and email = :email");
        }

        NativeQuery<String> sql = ((Session) em.getDelegate()).createNativeQuery(query.toString());
        if(user.getEmail() != null){
            sql.setParameter("email", user.getEmail());
        }
        return sql.getResultList();
    }
}
