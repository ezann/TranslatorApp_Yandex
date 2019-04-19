/*
 * This Project (MyTranslatorApp) Created by Ezra Septian
 * Copyright(c) 2019. All rights reserved.
 * Last modified 19/04/19 17:19
 */

package com.ezrasept.mytranslatorapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ezrasept.mytranslatorapp", appContext.getPackageName());
    }
}
