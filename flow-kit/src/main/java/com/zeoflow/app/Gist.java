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

package com.zeoflow.app;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.zeoflow.model.Extra;
import com.zeoflow.zson.ZsonAttributes;
import com.zeoflow.zson.model.Attribute;

import java.util.ArrayList;
import java.util.List;

public class Gist extends Entity
{

    private List<Extra> extras = new ArrayList<>();

    public Gist(Fragment fragment)
    {
        Bundle bundle = fragment.getArguments();
        if (bundle != null)
        {
            bundle.get("initialize");
            Attribute attribute = ZsonAttributes.withObject(ZsonAttributes.withObject(bundle).getAttribute("mMap"))
                .getAllAttributes().get(1);
            List<Attribute> attributes = ZsonAttributes.withObject(attribute.getValue().getAsJsonObject())
                .getAllAttributes();
            for (Attribute attributeToAdd : attributes)
            {
                Extra extraToAdd = new Extra(attributeToAdd.getKey(), attributeToAdd.getValue());
                if (!extras.contains(extraToAdd))
                {
                    extras.add(extraToAdd);
                }
            }
        }
    }

    public Gist(Activity activity)
    {
        if (activity.getIntent() != null)
        {
            if (activity.getIntent().getExtras() != null)
            {
                Bundle bundle = activity.getIntent().getExtras();
                if (bundle != null)
                {
                    bundle.get("initialize");
                    Attribute attribute = ZsonAttributes.withObject(ZsonAttributes.withObject(bundle).getAttribute("mMap"))
                        .getAllAttributes().get(1);
                    List<Attribute> attributes = ZsonAttributes.withObject(attribute.getValue().getAsJsonObject())
                        .getAllAttributes();
                    for (Attribute attributeToAdd : attributes)
                    {
                        Extra extraToAdd = new Extra(attributeToAdd.getKey(), attributeToAdd.getValue());
                        if (!extras.contains(extraToAdd))
                        {
                            extras.add(extraToAdd);
                        }
                    }
                }
            }
        }
    }

    public List<Extra> getExtras()
    {
        return this.extras;
    }

    public Extra getExtra(String key)
    {
        Extra extra = new Extra(key, null);
        for (Extra extraSearch : this.extras)
        {
            if (extraSearch.getKey().equals(key))
            {
                return extraSearch;
            }
        }
        return extra;
    }

}
