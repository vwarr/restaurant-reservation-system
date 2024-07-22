package org.group4;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.group4.commandLine.CommandLineController;
import org.group4.routers.OwnerRouter;
import org.group4.routers.ReservationRouter;
import org.group4.routers.RestaurantRouter;
import org.group4.routers.*;
import org.group4.serverUtil.HotReloadingFileLoader;
import org.group4.serverUtil.PebbleFileRenderer;
import org.group4.serverUtil.TestData;

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
            // For populating test data
            TestData.generateOwners();
            TestData.generateCustomers();
            TestData.generateRestaurants();

            var app = Javalin.create(config -> {
                config.fileRenderer(new PebbleFileRenderer());
            })
                    .get("/", Main::renderHomepage)
                    .start(7070);

            new RestaurantRouter(app);
            new ReservationRouter(app);
            new OwnerRouter(app);
            new OrderRouter(app);
            new CustomerRouter(app);
            new MenuRouter(app);
            new ReviewRouter(app);
        }
    }

    static void renderHomepage(Context ctx) {
        ctx.render("home.peb", Collections.singletonMap("name", "John Doe"));
    }
}