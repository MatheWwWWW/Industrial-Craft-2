/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

public enum MouseButton {
    left(0),
    right(1);

    public final int id;
    private static final MouseButton[] map;

    private MouseButton(int id) {
        this.id = id;
    }

    public static MouseButton get(int id) {
        if (id < 0 || id >= map.length) {
            return null;
        }
        return map[id];
    }

    private static MouseButton[] createMap() {
        MouseButton[] values = MouseButton.values();
        int max = -1;
        for (MouseButton button : values) {
            if (button.id <= max) continue;
            max = button.id;
        }
        if (max < 0) {
            return new MouseButton[0];
        }
        MouseButton[] ret = new MouseButton[max + 1];
        MouseButton[] mouseButtonArray = values;
        int n = mouseButtonArray.length;
        for (int i = 0; i < n; ++i) {
            MouseButton button;
            ret[button.id] = button = mouseButtonArray[i];
        }
        return ret;
    }

    static {
        map = MouseButton.createMap();
    }
}

