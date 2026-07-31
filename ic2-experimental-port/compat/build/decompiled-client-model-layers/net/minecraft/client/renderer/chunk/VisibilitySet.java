/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.core.Direction;

public class VisibilitySet {
    private static final int f_112979_ = Direction.values().length;
    private final BitSet f_112980_ = new BitSet(f_112979_ * f_112979_);

    public void m_112990_(Set<Direction> p_112991_) {
        for (Direction $$1 : p_112991_) {
            for (Direction $$2 : p_112991_) {
                this.m_112986_($$1, $$2, true);
            }
        }
    }

    public void m_112986_(Direction p_112987_, Direction p_112988_, boolean p_112989_) {
        this.f_112980_.set(p_112987_.ordinal() + p_112988_.ordinal() * f_112979_, p_112989_);
        this.f_112980_.set(p_112988_.ordinal() + p_112987_.ordinal() * f_112979_, p_112989_);
    }

    public void m_112992_(boolean p_112993_) {
        this.f_112980_.set(0, this.f_112980_.size(), p_112993_);
    }

    public boolean m_112983_(Direction p_112984_, Direction p_112985_) {
        return this.f_112980_.get(p_112984_.ordinal() + p_112985_.ordinal() * f_112979_);
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append(' ');
        for (Direction $$1 : Direction.values()) {
            $$0.append(' ').append($$1.toString().toUpperCase().charAt(0));
        }
        $$0.append('\n');
        for (Direction $$2 : Direction.values()) {
            $$0.append($$2.toString().toUpperCase().charAt(0));
            for (Direction $$3 : Direction.values()) {
                if ($$2 == $$3) {
                    $$0.append("  ");
                    continue;
                }
                boolean $$4 = this.m_112983_($$2, $$3);
                $$0.append(' ').append($$4 ? (char)'Y' : 'n');
            }
            $$0.append('\n');
        }
        return $$0.toString();
    }
}

