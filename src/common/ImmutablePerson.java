package common;

public final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter only, no setter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
