package tour;

import java.time.LocalTime;

public record Step(LocalTime time, String label, int deliveryTime) {}
