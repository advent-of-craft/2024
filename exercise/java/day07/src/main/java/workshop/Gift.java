package workshop;

public record Gift(String name, Status status) {
    public Gift(String name) {
        this(name, Status.PRODUCING);
    }

    public Gift withStatus(Status status) {
        return new Gift(this.name, status);
    }
}
