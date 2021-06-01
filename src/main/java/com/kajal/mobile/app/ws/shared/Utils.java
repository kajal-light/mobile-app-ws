package com.kajal.mobile.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random RANDOM = new SecureRandom();

	private final String ALPHABET = "1234dhqdsbnfjs";

	public String genrateUserId(int length) {

		return genrateRandomString(length);
	}
	
	public String genrateAddressId(int length) {

		return genrateRandomString(length);
	}

	private String genrateRandomString(int length) {

		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

		}

		return new String(returnValue);

	}

}
