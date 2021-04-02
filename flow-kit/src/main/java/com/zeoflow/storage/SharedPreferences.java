// Copyright 2021 ZeoFlow SRL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zeoflow.storage;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.zeoflow.initializer.ZeoFlowApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Set;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class SharedPreferences
{

    private static SharedPreferences instance;
    private final android.content.SharedPreferences regularSharedPreferences;
    private final Set<String> DEFAULT_SET = null;
    private android.content.SharedPreferences encryptSharedPreferences;

    /**
     * private constructor for singelton implementation.
     * Generates 2 storage locations: encrypted with AES256 and non-encrypted
     */
    private SharedPreferences()
    {
        Context mContext = ZeoFlowApp.getContext();
        regularSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName() + "regular", Context.MODE_PRIVATE);
        String masterKeyAlias = null;
        try
        {
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    mContext.getPackageName() + ".encrypted",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build();

            MasterKey masterKey = new MasterKey.Builder(mContext)
                    .setKeyGenParameterSpec(spec)
                    .build();

            encryptSharedPreferences = EncryptedSharedPreferences.create(
                    mContext.getPackageName() + ".encrypted",
                    masterKey, // masterKey created above
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e)
        {
            e.printStackTrace();
        }

    }

    public static synchronized SharedPreferences getInstance()
    {
        if (instance == null)
        {
            instance = new SharedPreferences();
        }

        return instance;
    }

    /**
     * use this to store Int values
     *
     * @param key   of the int value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putInt(String key, int value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putInt(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putInt(key, value).apply();
        }
    }

    /**
     * use this to retrieve int from storage without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return int value
     */
    public int getInt(String key, Type type)
    {
        return getInt(key, -1, type);
    }

    /**
     * use this to retrieve int from storage with default value
     *
     * @param key          of the int value that is going to be stored
     * @param defaultValue in case that the key is not stored
     * @param type         encrypted OR not encrypted
     *
     * @return int value
     */
    public int getInt(String key, int defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getInt(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getInt(key, defaultValue);
        }
    }

    /**
     * use this to retrieve boolean from storage with default value
     *
     * @param key          you want to retrieve
     * @param defaultValue will return if the key is not exist
     * @param type         encrypted OR not encrypted
     *
     * @return boolean value
     */
    public boolean getBoolean(String key, boolean defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getBoolean(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getBoolean(key, defaultValue);
        }
    }

    /**
     * use this to retrieve boolean from storage without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return boolean value
     */
    public boolean getBoolean(String key, Type type)
    {
        return getBoolean(key, false, type);
    }

    /**
     * use this to store boolean values
     *
     * @param key   of the boolean value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putBoolean(String key, boolean value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putBoolean(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * use this to retrieve float value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return float value
     */
    public float getFloat(String key, Type type)
    {
        return getFloat(key, -1, type);
    }

    /**
     * use this to retrieve float value with default value
     *
     * @param key          you want to retrieve
     * @param defaultValue will return if the key is not exist
     * @param type         encrypted OR not encrypted
     *
     * @return float value
     */
    public float getFloat(String key, float defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getFloat(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getFloat(key, defaultValue);
        }
    }

    /**
     * use this to store float values
     *
     * @param key   of the float value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putFloat(String key, float value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putFloat(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putFloat(key, value).apply();
        }
    }

    /**
     * use this to retrieve long value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return long value
     */
    public float getLong(String key, Type type)
    {
        return getLong(key, (long) -1, type);
    }

    /**
     * use this to retrieve long value with default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return long value
     */
    public float getLong(String key, Long defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getLong(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getLong(key, defaultValue);
        }
    }

    /**
     * use this to store long values
     *
     * @param key   of the long value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putLong(String key, Long value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putLong(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putLong(key, value).apply();
        }
    }

    /**
     * use this to retrieve double value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return double value
     */
    public float getDouble(String key, Type type)
    {
        return getDouble(key, (double) -1, type);
    }

    /**
     * use this to retrieve double value with default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return double value
     */
    public float getDouble(String key, double defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getLong(key, Double.valueOf(defaultValue).longValue());
        } else
        {
            return encryptSharedPreferences.getLong(key, Double.valueOf(defaultValue).longValue());
        }
    }

    /**
     * use this to store double values
     *
     * @param key   of the double value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putDouble(String key, double value, Type type)
    {
        putLong(key, Double.doubleToRawLongBits(value), type);
    }

    /**
     * use this to retrieve string value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return string value
     */
    public String getString(String key, Type type)
    {
        return getString(key, "null", type);
    }

    /**
     * use this to retrieve string value with default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return string value
     */
    public String getString(String key, String defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getString(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getString(key, defaultValue);
        }
    }

    /**
     * use this to store string values
     *
     * @param key   of the string value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putString(String key, String value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putString(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putString(key, value).apply();
        }
    }

    /**
     * use this to retrieve stringSet value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return stringSet
     */
    public Set<String> getStringSet(String key, Type type)
    {
        return getStringSet(key, DEFAULT_SET, type);
    }

    /**
     * use this to retrieve stringSet value with default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return stringSet
     */
    public Set<String> getStringSet(String key, Set<String> defaultValue, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            return regularSharedPreferences.getStringSet(key, defaultValue);
        } else
        {
            return encryptSharedPreferences.getStringSet(key, defaultValue);
        }
    }

    /**
     * use this to store stringSet values
     *
     * @param key   of the stringSet value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putStringSet(String key, Set<String> value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putStringSet(key, value).apply();
        } else
        {
            encryptSharedPreferences.edit().putStringSet(key, value).apply();
        }
    }

    /**
     * use this to retrieve JSONObject value without default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return JSONObject
     */
    public JSONObject getJsonObject(String key, Type type)
    {
        return getJsonObject(key, null, type);
    }

    /**
     * use this to retrieve JSONObject value with default value
     *
     * @param key  you want to retrieve
     * @param type encrypted OR not encrypted
     *
     * @return JSONObject
     */
    public JSONObject getJsonObject(String key, JSONObject defaultValue, Type type)
    {
        String jsonString;
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            jsonString = regularSharedPreferences.getString(key, defaultValue.toString());
        } else
        {
            jsonString = encryptSharedPreferences.getString(key, defaultValue.toString());
        }
        if (jsonString != null)
        {
            try
            {
                return new JSONObject(jsonString);

            } catch (JSONException e)
            {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * use this to store JSONObject values
     *
     * @param key   of the JSONObject value that is going to be stored
     * @param value you want to store
     * @param type  encrypted OR not encrypted
     */
    public void putJsonObject(String key, JSONObject value, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().putString(key, value.toString()).apply();
        } else
        {
            encryptSharedPreferences.edit().putString(key, value.toString()).apply();
        }
    }

    /**
     * use this to remove a key:value pair drom storage
     *
     * @param key  you want to remove
     * @param type from which storage you want to delete?
     */
    public void remove(String key, Type type)
    {
        if (type.equals(Type.NOT_ENCRYPTED))
        {
            regularSharedPreferences.edit().remove(key).apply();
        } else
        {
            encryptSharedPreferences.edit().remove(key).apply();
        }
    }

    /**
     * check if the storage contains a certain key
     *
     * @param key you want to find
     *
     * @return true/false
     */
    public boolean contains(String key)
    {
        if (regularSharedPreferences.contains(key))
        {
            return true;
        } else return encryptSharedPreferences.contains(key);
    }

    /**
     * clear all the storage!
     */
    public void removeAll()
    {
        regularSharedPreferences.edit().clear().apply();
        encryptSharedPreferences.edit().clear().apply();
    }

}
