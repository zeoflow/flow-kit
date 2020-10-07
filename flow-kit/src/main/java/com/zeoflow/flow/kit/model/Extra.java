package com.zeoflow.flow.kit.model;

import com.zeoflow.zson.JsonElement;

public class Extra
{
    private String key;
    private JsonElement value;

    public <T> Extra(String key, JsonElement value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public JsonElement getValue()
    {
        return value;
    }

}
