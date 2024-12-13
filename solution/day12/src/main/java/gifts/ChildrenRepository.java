package gifts;

import java.util.Optional;

public interface ChildrenRepository {
    Optional<Child> addChild(Child child);

    Optional<Child> findByName(String childName);
}
