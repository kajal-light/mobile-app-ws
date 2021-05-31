package com.kajal.mobile.app.ws.ui.constants;

import com.kajal.mobile.app.ws.ui.appproperti.AppProperties;
import com.kajal.mobile.app.ws.ui.shared.dto.Spring.SpringApplicationsContext;

public class SecurityConstants {

public static final long EXPIRATION_TIME=864000000;//10DAYS	
public static final String TOKEN_PREFIX="Bearer ";

public static final String HEADER_STRING="Authorization";

public static final String SIGN_UP_URL="/users";


public static String getTokenSecret() {
	
	AppProperties appProperties=(AppProperties) SpringApplicationsContext.getBean("AppProperties");
	
	return appProperties.getTokenSecret();
	
	
}
}
