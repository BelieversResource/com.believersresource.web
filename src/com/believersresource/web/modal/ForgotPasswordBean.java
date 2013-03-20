package com.believersresource.web.modal;

import java.util.ArrayList;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.User;
import com.believersresource.data.Utils;
import com.believersresource.web.AppUser;

@ManagedBean(name="modal_forgotPasswordBean")
@RequestScoped
public class ForgotPasswordBean {

	private String email;
	private String output;

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getOutput() { return output; }
	
	public void submit()
	{
		User user = User.load(email);
		if (user == null )
		{
			output = "<div class=\"error\">Could not find an account for this email address.</div>";
		} else {
			user.setAuthCode(UUID.randomUUID().toString());
            user.save();

            String body = "<p>You requested your BelieversResource.com password be reset.  You may change you password by clicking this <a href=\"http://www.believersresource.com/cp/?authCode=" + user.getAuthCode() + "\">link</a> which will allow you to log in one time and edit your account details.</p>";
            Utils.sendEmail(user.getEmail(), "Password Reset Instructions", body);

            output = "<div class=\"success\">Instructions on how to reset your password have been mailed to this address.</div>";
		}
		
	}
	
	

	public ForgotPasswordBean()
	{
	}
	
}
