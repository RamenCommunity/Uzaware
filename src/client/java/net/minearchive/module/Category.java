package net.minearchive.module;

public enum Category {
    COMBAT("Combat"),
    MISC("Misc"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    CLIENT("Client"),
    HUD("Hud");

    private final String display;

    Category(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
