package com.kondrat.wcic.repository;

import com.kondrat.wcic.controllers.CreateUserRequest;
import com.kondrat.wcic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    default boolean createIfNotExists(User newUser) {
        if (!userPresent(newUser.getLogin())) {
            save(newUser);
            return true;
        } else {
            return false;
        }
    }

    default boolean dataVerification(CreateUserRequest createUserRequest) {
        if (userPresent(createUserRequest.getLogin())) {
            System.out.println("ok in first if");
            if (createUserRequest.getPassword().equals(findByLogin(createUserRequest.getLogin()).getPassword())) {
                System.out.println("ok in second if\n-----------------");
                return true;
            } else {
                System.out.println(" not ok in else\n-----------------");
                return false;
            }
        }System.out.println("nothing");
        return false;
    }

    default boolean userPresent(String login) {
        if (login == null) {
            return false;
        } else {
            return existsByLogin(login);
        }
    }

    boolean existsByLogin(String login);
    User findByLogin(String login);
}
