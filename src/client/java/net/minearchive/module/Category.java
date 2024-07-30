package net.minearchive.module;

public enum Category {
    COMBAT("Combat"),
    RENDER("Render"),
    MISC("Misc");

    private final String display;

    Category(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
