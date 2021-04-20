package com.zeoflow.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zeoflow.app.Activity;
import com.zeoflow.utils.string.StringCreator;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringCreator string = StringCreator.creator()
                .add("double strings $S$S", String.class, MainActivity.class)
                .add("upper string multiple 's' $S:Ssss", String.class)
                .add("upper string $S:S's", String.class)
                .add("lower string $S:s", String.class)
                .add("name $N", "String.class")
                .add("upper name $N:S's", "String.class")
                .add("lower name $N:s's", "String.class")
                .add("class full package $C", String.class)
                .add("class name only $c", String.class)
                .create();
        log(string.asString());
    }

}
