/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import ic2.core.util.Vector3;

public final class Quaternion {
    public Vector3 v;
    public double w;

    public Quaternion() {
    }

    public Quaternion(Vector3 vector3, double d) {
        this(vector3, d, true);
    }

    private Quaternion(Vector3 vector3, double d, boolean bl) {
        this.v = bl ? vector3.copy() : vector3;
        this.w = d;
    }

    public Quaternion(double d, double d2, double d3, double d4) {
        this(new Vector3(d, d2, d3), d4, false);
    }

    public Quaternion(Quaternion quaternion) {
        this(quaternion.v, quaternion.w, true);
    }

    public Quaternion set(Vector3 vector3, double d, boolean bl) {
        this.v = bl ? vector3.copy() : vector3;
        this.w = d;
        return this;
    }

    public Quaternion set(double d, double d2, double d3, double d4) {
        this.v.x = d;
        this.v.y = d2;
        this.v.z = d3;
        this.w = d4;
        return this;
    }

    public Quaternion setFromAxisAngle(Vector3 vector3, double d) {
        return this.set(vector3.copy().scale(Math.sin(d / 2.0)), Math.cos(d / 2.0), false);
    }

    public Quaternion mul(Quaternion quaternion) {
        return this.set(this.v.copy().scale(quaternion.w).add(quaternion.v.copy().scale(this.w)).add(this.v.copy().cross(quaternion.v)), this.w * quaternion.w - this.v.dot(quaternion.v), false);
    }

    public Quaternion inverse() {
        return this.set(this.v.negate(), this.w, false);
    }

    public Vector3 rotate(Vector3 vector3) {
        Vector3 vector32 = this.v.copy().cross(vector3);
        vector3.set(vector3.add(this.v.copy().cross(vector32).scale(2.0)).add(vector32.scale(2.0 * this.w)));
        return vector3;
    }
}

