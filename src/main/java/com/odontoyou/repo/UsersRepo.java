package com.odontoyou.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odontoyou.model.Users;

public interface UsersRepo extends JpaRepository<Users, String> {

}
