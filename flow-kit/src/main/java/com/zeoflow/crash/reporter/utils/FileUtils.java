package com.zeoflow.crash.reporter.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class FileUtils
{

    public static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils()
    {
        //this class is not publicly instantiable
    }

    public static boolean delete(String absPath)
    {
        if (TextUtils.isEmpty(absPath))
        {
            return false;
        }

        File file = new File(absPath);
        return delete(file);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean delete(File file)
    {
        if (!exists(file))
        {
            return true;
        }

        if (file.isFile())
        {
            return file.delete();
        }

        boolean result = true;
        File[] files = file.listFiles();
        if (files == null) return false;
        for (File value : files)
        {
            result |= delete(value);
        }
        result |= file.delete();

        return result;
    }

    public static boolean exists(File file)
    {
        return file != null && file.exists();
    }

    public static String cleanPath(String absPath)
    {
        if (TextUtils.isEmpty(absPath))
        {
            return absPath;
        }
        try
        {
            File file = new File(absPath);
            absPath = file.getCanonicalPath();
        } catch (Exception ignored)
        {

        }
        return absPath;
    }

    public static String getParent(File file)
    {
        return file == null ? null : file.getParent();
    }

    public static String getParent(String absPath)
    {
        if (TextUtils.isEmpty(absPath))
        {
            return null;
        }
        absPath = cleanPath(absPath);
        File file = new File(absPath);
        return getParent(file);
    }

    public static boolean deleteFiles(String directoryPath)
    {
        String directoryToDelete;
        if (!TextUtils.isEmpty(directoryPath))
        {
            directoryToDelete = directoryPath;
        } else
        {
            directoryToDelete = CrashUtil.getDefaultPath();
        }

        return delete(directoryToDelete);
    }

    public static String readFirstLineFromFile(File file)
    {
        String line = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return line;
    }

    public static String readFromFile(File file)
    {
        StringBuilder crash = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
            {
                crash.append(line);
                crash.append('\n');
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return crash.toString();
    }
}
