/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.util;

import net.minecraft.world.phys.Vec3;

public final class Vector3 {
    public static final Vector3 UP = new Vector3(0.0, 1.0, 0.0);
    public double x;
    public double y;
    public double z;

    public Vector3() {
    }

    public Vector3(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vector3(Vector3 vector3) {
        this(vector3.x, vector3.y, vector3.z);
    }

    public Vector3(Vec3 vec3) {
        this(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
    }

    public Vector3 copy() {
        return new Vector3(this);
    }

    public Vector3 copy(Vector3 vector3) {
        return vector3.set(this);
    }

    public Vector3 set(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        return this;
    }

    public Vector3 set(Vector3 vector3) {
        return this.set(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 set(Vec3 vec3) {
        return this.set(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
    }

    public Vector3 add(double d, double d2, double d3) {
        this.x += d;
        this.y += d2;
        this.z += d3;
        return this;
    }

    public Vector3 add(Vector3 vector3) {
        return this.add(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 addScaled(Vector3 vector3, double d) {
        return this.add(vector3.x * d, vector3.y * d, vector3.z * d);
    }

    public Vector3 sub(double d, double d2, double d3) {
        this.x -= d;
        this.y -= d2;
        this.z -= d3;
        return this;
    }

    public Vector3 sub(Vector3 vector3) {
        return this.sub(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 cross(double d, double d2, double d3) {
        return this.set(this.y * d3 - this.z * d2, this.z * d - this.x * d3, this.x * d2 - this.y * d);
    }

    public Vector3 cross(Vector3 vector3) {
        return this.cross(vector3.x, vector3.y, vector3.z);
    }

    public double dot(double d, double d2, double d3) {
        return this.x * d + this.y * d2 + this.z * d3;
    }

    public double dot(Vector3 vector3) {
        return this.dot(vector3.x, vector3.y, vector3.z);
    }

    public Vector3 normalize() {
        double d = this.length();
        this.x /= d;
        this.y /= d;
        this.z /= d;
        return this;
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector3 negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public double distanceSquared(double d, double d2, double d3) {
        double d4 = d - this.x;
        double d5 = d2 - this.y;
        double d6 = d3 - this.z;
        return d4 * d4 + d5 * d5 + d6 * d6;
    }

    public double distanceSquared(Vector3 vector3) {
        return this.distanceSquared(vector3.x, vector3.y, vector3.z);
    }

    public double distanceSquared(Vec3 vec3) {
        return this.distanceSquared(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
    }

    public double distance(double d, double d2, double d3) {
        return Math.sqrt(this.distanceSquared(d, d2, d3));
    }

    public double distance(Vector3 vector3) {
        return this.distance(vector3.x, vector3.y, vector3.z);
    }

    public double distance(Vec3 vec3) {
        return this.distance(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
    }

    public Vector3 scale(double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
        return this;
    }

    public Vector3 scaleTo(double d) {
        double d2 = d / this.length();
        return this.scale(d2);
    }

    public Vec3 toVec3() {
        return new Vec3(this.x, this.y, this.z);
    }

    public String toString() {
        return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
    }
}

