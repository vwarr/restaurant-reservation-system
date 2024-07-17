package org.group4.serverUtil;

import io.pebbletemplates.pebble.loader.FileLoader;

public class HotReloadingFileLoader extends FileLoader {
    public HotReloadingFileLoader() {
        super();
        setPrefix("src/main/resources/");  // Adjust this path as needed
    }

    @Override
    public boolean resourceExists(String templateName) {
        return true;  // Always check the file system
    }
}