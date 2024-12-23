package app;

import core.control.ControlSystem;
import core.control.SleighNotStartedException;
import core.control.ReindeersNeedRestException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.startSystem();

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Enter a command (ascend (a), descend (d), park (p), or quit (q)): ");
            String command = scanner.nextLine();

            switch (command) {
                case "ascend": case "a":
                    try {
                        controlSystem.ascend();
                    } catch (ReindeersNeedRestException | SleighNotStartedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "descend":  case "d":
                    try {
                        controlSystem.descend();
                    } catch (SleighNotStartedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "park": case "p":
                    try {
                        controlSystem.park();
                    } catch (SleighNotStartedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "quit": case "q":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
        controlSystem.stopSystem();
    }
}
