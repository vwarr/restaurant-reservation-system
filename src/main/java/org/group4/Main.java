package org.group4;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import org.group4.commandLine.CommandLineController;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("cli")) {
            System.out.println(
                    "Welcome to the Restaurant Reservation System!\nEnter commands (type 'exit' to finish):");
            CommandLineController simulator = new CommandLineController();
            simulator.commandLoop();
        } else {
            var app = Javalin.create(config -> config.fileRenderer(new JavalinJte()))
                    .get("/", Main::renderRoot)
                    .start(7070);
        }
    }

    public static void renderRoot(Context ctx) {
        HelloPage page = new HelloPage();
        page.userName = "John Doe";
        page.userKarma = 1337;
        ctx.render("hello.jte", Collections.singletonMap("page", page));
    }
}