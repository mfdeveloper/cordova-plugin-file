package org.apache.cordova.file;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class FileUtilsTest {

    protected Context context;
    protected ClassLoader loader;

    @Before
    public void setUp() {

        context = ApplicationProvider.getApplicationContext();
        loader = Objects.requireNonNull(getClass().getClassLoader());
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.P)
    public void copyFileWithoutErrors() throws Exception {

        final File srcFile = new File(loader.getResource("data/test.txt").getPath());

        JSONArray params = new JSONArray(){{
            put(srcFile.getAbsolutePath());
            put(srcFile.getParentFile().getParent() + "/other-location");
        }};

        FileUtils filePlugin = new FileUtils(context);
        filePlugin.initialize(null, null);

        JSONObject entry = filePlugin.copy(params);

        assertTrue(entry.length() > 0);
        assertTrue(entry.has("fullPath") && entry.has("filesystemName"));
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.P)
    public void copyDirectoryContentWithoutErrors() throws Exception {

        final File srcFile = new File(loader.getResource("data").getPath());

        JSONArray params = new JSONArray(){{
            put(srcFile.getAbsolutePath());
            put(srcFile.getParent() + "/other-location");
        }};

        FileUtils filePlugin = new FileUtils(context);
        filePlugin.initialize(null, null);

        JSONObject entry = filePlugin.copy(params);

        assertTrue(entry.length() > 0);
        assertTrue(entry.has("fullPath") && entry.has("filesystemName"));
    }
}