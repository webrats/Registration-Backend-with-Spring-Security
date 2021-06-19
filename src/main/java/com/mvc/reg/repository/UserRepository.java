package com.mvc.reg.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mvc.reg.model.UserDao;

public interface UserRepository extends JpaRepository<UserDao, Long> {

	UserDao findByUsername(String username) ;
}
