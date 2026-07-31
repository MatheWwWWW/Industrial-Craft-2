/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.energy;

import ic2.core.energy.Node;
import net.minecraft.core.Direction;

class Change {
    Node node;
    final Direction dir;
    private double amount;
    private double voltage;

    Change(Node node, Direction direction, double d, double d2) {
        this.node = node;
        this.dir = direction;
        this.setAmount(d);
        this.setVoltage(d2);
    }

    public String toString() {
        return this.node + "@" + this.dir + " " + this.amount + " EU / " + this.voltage + " V";
    }

    double getAmount() {
        return this.amount;
    }

    void setAmount(double d) {
        double d2 = Math.rint(d);
        if (Math.abs(d - d2) < 0.001) {
            d = d2;
        }
        assert (!Double.isInfinite(d) && !Double.isNaN(d));
        this.amount = d;
    }

    double getVoltage() {
        return this.voltage;
    }

    private void setVoltage(double d) {
        double d2 = Math.rint(this.amount);
        if (Math.abs(d - d2) < 0.001) {
            d = d2;
        }
        assert (!Double.isInfinite(d) && !Double.isNaN(d));
        this.voltage = d;
    }
}

