package gifts;

import java.util.NoSuchElementException;

public class Santa {
    private final ChildrenRepository childrenRepository;

    public Santa(ChildrenRepository childrenRepository) {
        this.childrenRepository = childrenRepository;
    }

    public Toy chooseToyForChild(String childName) {
        return childrenRepository.findByName(childName)
                .map(Child::chooseToy)
                .orElseThrow(NoSuchElementException::new);
    }
}