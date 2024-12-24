package domain.core;

public record Error(String message) {
    public static Error anError(String message) {
        return new Error(message);
    }
}