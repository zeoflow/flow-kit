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

package com.zeoflow.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtil
{

    public static String readFile(AssetManager assets, String fileName)
    {
        return readFile(assets, fileName, "\n");
    }

    public static String readFile(AssetManager assets, String fileName, String newLine)
    {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(assets.open(fileName), StandardCharsets.UTF_8)))
        {

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null)
            {
                if (!content.toString().isEmpty())
                {
                    content.append(newLine);
                }
                content.append(mLine);
            }
        } catch (IOException e)
        {
            //log the exception
        }
        return content.toString();
    }

}
