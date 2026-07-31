/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
    }

    public Vector2(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public Vector2(Vector2 vector2) {
        this(vector2.x, vector2.y);
    }

    public Vector2 copy() {
        return new Vector2(this);
    }

    public Vector2 copy(Vector2 vector2) {
        return vector2.set(this);
    }

    public Vector2 set(double d, double d2) {
        this.x = d;
        this.y = d2;
        return this;
    }

    public Vector2 set(Vector2 vector2) {
        return this.set(vector2.x, vector2.y);
    }

    public Vector2 add(double d, double d2) {
        this.x += d;
        this.y += d2;
        return this;
    }

    public Vector2 add(Vector2 vector2) {
        return this.add(vector2.x, vector2.y);
    }

    public Vector2 sub(double d, double d2) {
        this.x -= d;
        this.y -= d2;
        return this;
    }

    public Vector2 sub(Vector2 vector2) {
        return this.sub(vector2.x, vector2.y);
    }

    public double dot(double d, double d2) {
        return this.x * d + this.y * d2;
    }

    public double dot(Vector2 vector2) {
        return this.dot(vector2.x, vector2.y);
    }

    public Vector2 normalize() {
        double d = this.length();
        this.x /= d;
        this.y /= d;
        return this;
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector2 negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public double distanceSquared(double d, double d2) {
        double d3 = d - this.x;
        double d4 = d2 - this.y;
        return d3 * d3 + d4 * d4;
    }

    public double distanceSquared(Vector2 vector2) {
        return this.distanceSquared(vector2.x, vector2.y);
    }

    public double distance(double d, double d2) {
        return Math.sqrt(this.distanceSquared(d, d2));
    }

    public double distance(Vector2 vector2) {
        return this.distance(vector2.x, vector2.y);
    }

    public Vector2 scale(double d) {
        this.x *= d;
        this.y *= d;
        return this;
    }

    public Vector2 scaleTo(double d) {
        double d2 = d / this.length();
        return this.scale(d2);
    }

    public Vector2 rotate(double d) {
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        return this.set(d2 * this.x - d3 * this.y, d3 * this.x + d2 * this.y);
    }
}

