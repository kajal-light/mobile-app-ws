package com.kajal.mobile.app.ws.service;

import java.util.List;

import com.kajal.mobile.app.ws.ui.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);
	AddressDto getAddress(String addressId);
	
}
