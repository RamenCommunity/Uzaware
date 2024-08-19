package net.minearchive.module;

public enum Category {
    COMBAT("Combat"),
    RENDER("Render"),
    CLIENT("Client"),
    MISC("Misc");

    private final String display;

    Category(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
