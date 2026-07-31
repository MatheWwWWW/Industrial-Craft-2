/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

public enum MouseButton {
    left(0),
    right(1);

    public final int id;
    private static final MouseButton[] map;

    private MouseButton(int n2) {
        this.id = n2;
    }

    public static MouseButton get(int n) {
        if (n < 0 || n >= map.length) {
            return null;
        }
        return map[n];
    }

    private static MouseButton[] createMap() {
        MouseButton[] mouseButtonArray = MouseButton.values();
        int n = -1;
        for (MouseButton mouseButton : mouseButtonArray) {
            if (mouseButton.id <= n) continue;
            n = mouseButton.id;
        }
        if (n < 0) {
            return new MouseButton[0];
        }
        MouseButton[] mouseButtonArray2 = new MouseButton[n + 1];
        MouseButton[] mouseButtonArray3 = mouseButtonArray;
        int n2 = mouseButtonArray3.length;
        for (int i = 0; i < n2; ++i) {
            MouseButton mouseButton;
            mouseButtonArray2[mouseButton.id] = mouseButton = mouseButtonArray3[i];
        }
        return mouseButtonArray2;
    }

    static {
        map = MouseButton.createMap();
    }
}

