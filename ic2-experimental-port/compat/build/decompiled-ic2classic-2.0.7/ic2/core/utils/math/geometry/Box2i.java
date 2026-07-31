/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.math.geometry;

public class Box2i {
    public static final Box2i EMPTY_BOX = new Box2i(0, 0, 0, 0);
    int xCoord;
    int yCoord;
    int height;
    int width;

    public Box2i(int x, int y, int l, int h) {
        this.xCoord = x;
        this.yCoord = y;
        this.height = h;
        this.width = l;
    }

    public boolean isInBox(int x, int y) {
        return this.xCoord <= x && this.xCoord + this.width >= x && this.yCoord <= y && this.yCoord + this.height >= y;
    }

    public boolean isXMatching(int x) {
        return this.xCoord <= x && this.xCoord + this.width >= x;
    }

    public boolean isYMatching(int y) {
        return this.yCoord <= y && this.yCoord + this.height >= y;
    }

    public int getX() {
        return this.xCoord;
    }

    public int getY() {
        return this.yCoord;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setX(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setY(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int length) {
        this.width = length;
    }

    public Box2i moveX(int amount) {
        this.xCoord += amount;
        return this;
    }

    public Box2i moveY(int amount) {
        this.yCoord += amount;
        return this;
    }

    public Box2i moveHeight(int amount) {
        this.height += amount;
        return this;
    }

    public Box2i moveWidth(int amount) {
        this.width += amount;
        return this;
    }

    public String toString() {
        return "Box2i [x=" + this.xCoord + ", y=" + this.yCoord + " width=" + this.width + ", height=" + this.height + "]";
    }
}

