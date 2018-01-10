package com.webgalaxie.blischke.bachelortakesix;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented add_new_expose_layout, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under add_new_expose_layout.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.webgalaxie.blischke.bachelortakesix", appContext.getPackageName());
    }
}
