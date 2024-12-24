package core.control.dashboard;

import core.control.Dashboard;

public class ConsoleDashboard implements Dashboard {
    public void display(String message) {
        System.out.println(message);
    }
}
