package preparation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

public class Gift {
    private final String name;
    private final double weight;
    private final String color;
    private final String material;
    private final Map<String, String> attributes = new HashMap<>();
    private String childName;

    public Gift(String name, double weight, String color, String material) {
        this.name = name;
        this.weight = weight;
        this.color = color;
        this.material = material;
    }

    public void assignToChild(String childName) {
        this.childName = childName;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    private Optional<String> getAttribute(String key) {
        return Optional.ofNullable(attributes.get(key));
    }

    public int getRecommendedAge() {
        return getAttribute("recommendedAge")
                .map(Integer::parseInt)
                .orElse(0);
    }

    @Override
    public String toString() {
        return format("A %s-colored %s weighting %s kg made in %s", color, name, weight, material);
    }
}