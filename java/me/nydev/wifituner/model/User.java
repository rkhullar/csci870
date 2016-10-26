package me.nydev.wifituner.model;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static JSONObject jsonify(User x)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("fname", x.fname());
            json.put("lname", x.lname());
            json.put("email", x.email());
            json.put("pswd" , x.token());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
