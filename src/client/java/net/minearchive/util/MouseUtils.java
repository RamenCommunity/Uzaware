package net.minearchive.util;

public class MouseUtils {
    public static boolean hover(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }
}
