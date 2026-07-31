/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.math.geometry;

public class Vec2i {
    int x;
    int y;

    public Vec2i() {
    }

    public Vec2i(int i) {
        this(i, i);
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(Vec2i pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Vec2i) {
            return ((Vec2i)obj).getX() == this.getX() && ((Vec2i)obj).getY() == this.getY();
        }
        return false;
    }

    public int hashCode() {
        return this.getX() + this.getY() * 31;
    }

    public String toString() {
        return "Vec2i[x=" + this.x + ", y=" + this.y + "]";
    }
}

