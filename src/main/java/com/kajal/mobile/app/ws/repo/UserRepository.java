package com.kajal.mobile.app.ws.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kajal.mobile.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	UserEntity findByUserid(String userId);

	UserEntity findUserByEmailVerificationToken(String token);

}
