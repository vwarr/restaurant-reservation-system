package org.group4;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.group4.commandLine.CommandLineController;
import org.group4.routers.RestaurantRouter;
import org.group4.serverUtil.HotReloadingFileLoader;
import org.group4.serverUtil.PebbleFileRenderer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("cli")) {
            System.out.println(
                    "Welcome to the Restaurant Reservation System!\nEnter commands (type 'exit' to finish):");
            CommandLineController simulator = new CommandLineController();
            simulator.commandLoop();
        } else {

            var app = Javalin.create(config -> {
                config.fileRenderer(new PebbleFileRenderer());
            })
                    .get("/", Main::renderHomepage)
                    .start(7070);

            new RestaurantRouter(app);
        }
    }

    static void renderHomepage(Context ctx) {
        ctx.render("home.peb", Collections.singletonMap("name", "John Doe"));
    }
}