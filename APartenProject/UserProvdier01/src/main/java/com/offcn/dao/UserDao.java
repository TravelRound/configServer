package com.offcn.dao;

import com.offcn.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends JpaRepository<User, Long> {

	
}
