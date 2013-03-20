package com.believersresource.web;

import com.believersresource.data.User;
import com.believersresource.data.Utils;
import com.google.gson.Gson;

public class FacebookHelper {
	private static String clientId = "**REMOVED**";
    private static String clientSecret = "**REMOVED**";

    public static String getFacebookLoginUrl(String redirectPath)
    {    	
        String loginUrl = getLoginUrl(redirectPath);
        String result = "https://www.facebook.com/dialog/oauth?client_id=" + clientId + "&redirect_uri=" + java.net.URLEncoder.encode(loginUrl);
        return result;
    }

    public static String getLoginUrl(String returnUrl)
    {
        //Facebook used upper case for url encoding (%2F).  ASP.NET returns lower case (%2f).  This covers to upper case so the url matches FB.
        String encodedUrl = java.net.URLEncoder.encode(returnUrl);
        //encodedUrl = Regex.replace(encodedUrl, "(%[0-9a-f][0-9a-f])", c => c.Value.ToUpper());
        return "http://www.believersresource.com/login.xhtml?action=fbAuth&returnUrl=" + encodedUrl;
    }

    public static String getAccessToken(String code, String returnUrl)
    {
        String url = "https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + java.net.URLEncoder.encode(returnUrl) + "&code=" + java.net.URLEncoder.encode(code);
        String response=Utils.getUrlContents(url);
        String result = response.split("&")[0];
        result = result.replace("access_token=", "");
        return result;
    }

    public static User getUserData(String accessToken)
    {
        String url = "https://graph.facebook.com/me?access_token=" + accessToken;
        String contents = Utils.getUrlContents(url);
        com.google.gson.Gson gson = new Gson();
        
        FacebookResponse obj = gson.fromJson(contents, FacebookResponse.class);
        //Hashtable hash = (Hashtable)com.believersresource.data.JSON.JsonDecode(contents);

        User user = new User();
        user.setFacebookId(obj.getId());
        user.setDisplayName(obj.getName());
        return user;
    }
}
