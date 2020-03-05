package com.example.almabaseassignment.Models;

public class Permissions
{
    private String pull;

    private String admin;

    private String push;

    public String getPull ()
    {
        return pull;
    }

    public void setPull (String pull)
    {
        this.pull = pull;
    }

    public String getAdmin ()
    {
        return admin;
    }

    public void setAdmin (String admin)
    {
        this.admin = admin;
    }

    public String getPush ()
    {
        return push;
    }

    public void setPush (String push)
    {
        this.push = push;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pull = "+pull+", admin = "+admin+", push = "+push+"]";
    }
}

