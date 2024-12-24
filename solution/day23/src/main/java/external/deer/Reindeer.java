package external.deer;

public class Reindeer {

    private int spirit = 0;
    private int age = 0;
    private String name;
    public boolean sick = false;
    public int timesHarnessing = 0;
    private int powerPullLimit = 0;

    public Reindeer(String name, int age, int spirit) {
        this(name, age, spirit, false);
    }

    public Reindeer(String name, int age, int spirit, boolean sick) {
        this.name = name;
        this.spirit = spirit;
        this.age = age;
        this.sick = sick;

        powerPullLimit = age <= 5 ? 5 : 5 - (age - 5);
    }

    public float getMagicPower() {
        if (!sick || needsRest()) {
            if(age == 1)
                return spirit * 0.5f;
            else
                if(age <= 5) {
                    return spirit;
                }
                else {
                    return spirit *  0.25f;
                }
        } else {
            return 0;
        }
    }

    public boolean needsRest() {
        if (!sick) {
            return timesHarnessing >= powerPullLimit;
        } else {
            return true;
        }
    }
}
