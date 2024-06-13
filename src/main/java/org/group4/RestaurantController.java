package org.group4;

import java.util.Scanner;
public class RestaurantController {
    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println(" echo >> " + wholeInputLine);

                if (tokens[0].indexOf("//") == 0) {
                    // System.out.println(wholeInputLine);

                    // instructions to create simulation resources
                } else if (wholeInputLine.equals("")) {
                    continue;
                } else if (tokens[0].equals("create_restaurant")) {
                    System.out.print("unique_identifier: " + tokens[1] + ", name: " + tokens[2]  + ", city: " + tokens[3]);
                    System.out.print(", state: " + tokens[4] + ", zip_code: " + tokens[5]  + ", rating: " + tokens[6]);
                    System.out.println(", top_10: " + tokens[7] + ", seating_capacity: " + tokens[8]);

                } else if (tokens[0].equals("create_customer")) {
                    System.out.print("unique_identifier: " + tokens[1] + ", first_name: " + tokens[2] + ", last_name: " + tokens[3]);
                    System.out.print(", city: " + tokens[4] + ", state: " + tokens[5]  + ", zip_code: " + tokens[6]);
                    System.out.println(", funds: " + tokens[7]);

                } else if (tokens[0].equals("make_reservation")) {
                    System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2] + ", party_size: " + tokens[3]);
                    System.out.println(", reservation_date: " + tokens[4] + ", reservation_time: " + tokens[5] + ", credits: " + tokens[6]);

                } else if (tokens[0].equals("customer_arrival")) {
                    System.out.print("customer_identifier: " + tokens[1] + ", restaurant_identifier: " + tokens[2]);
                    System.out.println(", reservation_date: " + tokens[3] + ", arrival_time: " + tokens[4] + ", reservation_time: " + tokens[5]);

                } else if (tokens[0].equals("exit")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
                System.out.println("Enter command: ");
            } catch (Exception e) {
                displayMessage("error", "during command loop >> execution");
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }

    void displayMessage(String status, String text_output) {
        System.out.println(status.toUpperCase() + ": " + text_output.toLowerCase());
    }
}
