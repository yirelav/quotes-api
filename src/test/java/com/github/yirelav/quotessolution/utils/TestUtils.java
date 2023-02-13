package com.github.yirelav.quotessolution.utils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test Utils.
 */

public class TestUtils {

    public static String loadFile(String name) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URI uri = loader.getResource(name).toURI();
        return Files.readString(Path.of(uri), StandardCharsets.UTF_8);
    }


}
