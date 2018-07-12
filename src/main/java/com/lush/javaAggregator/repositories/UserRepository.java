package com.lush.javaAggregator.repositories;

import com.lush.javaAggregator.modles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(
      value = "SELECT id, user_name, password, user_type, reg_date FROM user WHERE user_name = :user_name",
      nativeQuery = true)
  User findByUsername(@Param(value = "user_name") String user_name);
}
