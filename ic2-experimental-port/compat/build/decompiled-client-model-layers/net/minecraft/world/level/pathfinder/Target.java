/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.pathfinder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;

public class Target
extends Node {
    private float f_77494_ = Float.MAX_VALUE;
    private Node f_77495_;
    private boolean f_77496_;

    public Target(Node p_77502_) {
        super(p_77502_.f_77271_, p_77502_.f_77272_, p_77502_.f_77273_);
    }

    public Target(int p_77498_, int p_77499_, int p_77500_) {
        super(p_77498_, p_77499_, p_77500_);
    }

    public void m_77503_(float p_77504_, Node p_77505_) {
        if (p_77504_ < this.f_77494_) {
            this.f_77494_ = p_77504_;
            this.f_77495_ = p_77505_;
        }
    }

    public Node m_77508_() {
        return this.f_77495_;
    }

    public void m_77509_() {
        this.f_77496_ = true;
    }

    public boolean m_164723_() {
        return this.f_77496_;
    }

    public static Target m_77506_(FriendlyByteBuf p_77507_) {
        Target $$1 = new Target(p_77507_.readInt(), p_77507_.readInt(), p_77507_.readInt());
        $$1.f_77280_ = p_77507_.readFloat();
        $$1.f_77281_ = p_77507_.readFloat();
        $$1.f_77279_ = p_77507_.readBoolean();
        $$1.f_77282_ = BlockPathTypes.values()[p_77507_.readInt()];
        $$1.f_77277_ = p_77507_.readFloat();
        return $$1;
    }
}

