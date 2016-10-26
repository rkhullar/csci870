package me.nydev.wifituner.model;

public class User
{
    protected String fname, lname, email, token;

    public User()
    {
        fname = null; lname = null;
        email = null; token = null;
    }

    public User(String fname, String lname, String email, String token)
    {
        this.fname = fname; this.lname = lname;
        this.email = email; this.token = token;
    }

    public String fname(){return this.fname;}
    public String lname(){return this.lname;}
    public String email(){return this.email;}
    public String token(){return this.token;}
}
