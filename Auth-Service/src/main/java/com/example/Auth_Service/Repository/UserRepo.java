package com.example.Auth_Service.Repository;

import com.example.Auth_Service.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,UUID> {

    optional<User> findByEmail(String Email);
}
