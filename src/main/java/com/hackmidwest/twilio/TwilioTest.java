package com.hackmidwest.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioTest { 
	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "AC5ccb69d3b5857fd45aa7b92edebb08d5";
	public static final String AUTH_TOKEN = "fcc1fa3fac9883dc4b638f34ef653329";

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+14697284557"), new PhoneNumber("+19724405224"),
				"This is the ship that made the Kessel Run in fourteen parsecs?").create();
		
		System.out.println(message.getSid());
	}
}
