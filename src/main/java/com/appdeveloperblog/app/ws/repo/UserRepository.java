package com.appdeveloperblog.app.ws.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appdeveloperblog.app.ws.io.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

UserEntity findByEmail(String email);

UserEntity findByUserid(String userId);



}
