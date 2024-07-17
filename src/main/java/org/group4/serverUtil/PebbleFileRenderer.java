package org.group4.serverUtil;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PebbleFileRenderer implements FileRenderer {
    private final PebbleEngine engine;

    public PebbleFileRenderer() {
        engine = new PebbleEngine.Builder()
                .loader(new HotReloadingFileLoader())
                .cacheActive(false)
                .strictVariables(true)
                .build();
    }

    @NotNull
    @Override
    public String render(@NotNull String s, @NotNull Map<String, ?> map, @NotNull Context context) {
        PebbleTemplate compiledTemplate = engine.getTemplate(s);

        Writer writer = new StringWriter();
        try {
            compiledTemplate.evaluate(writer, (Map<String, Object>) map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return writer.toString();
    }
}
