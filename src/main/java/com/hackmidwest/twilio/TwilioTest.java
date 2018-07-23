package com.hackmidwest.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioTest { 
	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "NeedToUpdate";
	public static final String AUTH_TOKEN = "NeedToUpdadte";

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+1234"), new PhoneNumber("+5678"),
				"This is the ship that made the Kessel Run in fourteen parsecs?").create();
		
		System.out.println(message.getSid());
	}
}
