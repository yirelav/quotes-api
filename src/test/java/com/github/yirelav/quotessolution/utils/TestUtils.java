package com.github.yirelav.quotessolution.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

/**
 * Test Utils.
 */

public class TestUtils {

    public static String loadFile(String name) throws IOException {
        URL url = Resources.getResource(name);
        return Resources.toString(url, Charsets.UTF_8);
    }


}
