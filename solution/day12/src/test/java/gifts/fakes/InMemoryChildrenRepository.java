package gifts.fakes;

import gifts.Child;
import gifts.ChildrenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryChildrenRepository implements ChildrenRepository {
    private final List<Child> children;

    public InMemoryChildrenRepository() {
        this.children = new ArrayList<>();
    }

    @Override
    public Optional<Child> addChild(Child child) {
        children.add(child);
        return Optional.of(child);
    }

    @Override
    public Optional<Child> findByName(String childName) {
        return children.stream()
                .filter(child -> child.getName().equals(childName))
                .findFirst();
    }
}