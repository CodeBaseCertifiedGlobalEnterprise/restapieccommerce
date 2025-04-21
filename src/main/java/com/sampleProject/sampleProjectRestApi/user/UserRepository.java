package com.sampleProject.sampleProjectRestApi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer > {
    Optional<User> findByUserName(String userName); // returns a unique username and if not found don't throw an error but return an empty container

    Optional<User> findByEmail(String email);

}
