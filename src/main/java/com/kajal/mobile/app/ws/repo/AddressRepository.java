package com.kajal.mobile.app.ws.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kajal.mobile.app.ws.io.entity.AddressEntity;
import com.kajal.mobile.app.ws.io.entity.UserEntity;
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

	Iterable<AddressEntity> findAllByUserDetails(UserEntity userEntity);

	AddressEntity findByAddressId(String addressId);

	
	
}
