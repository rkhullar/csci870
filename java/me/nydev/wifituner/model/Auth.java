package me.nydev.wifituner.model;

public class Auth
{
    private String username, secret;

    public Auth(String username, String secret)
    {
        this.username = username;
        this.secret = secret;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getSecret()
    {
        return this.secret;
    }
}
