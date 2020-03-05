package com.example.almabaseassignment.Models;


public class License
{
    private String name;

    private String spdx_id;

    private String key;

    private String url;

    private String node_id;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSpdx_id ()
    {
        return spdx_id;
    }

    public void setSpdx_id (String spdx_id)
    {
        this.spdx_id = spdx_id;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getNode_id ()
    {
        return node_id;
    }

    public void setNode_id (String node_id)
    {
        this.node_id = node_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", spdx_id = "+spdx_id+", key = "+key+", url = "+url+", node_id = "+node_id+"]";
    }
}