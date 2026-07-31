/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Node {
    public final int f_77271_;
    public final int f_77272_;
    public final int f_77273_;
    private final int f_77283_;
    public int f_77274_ = -1;
    public float f_77275_;
    public float f_77276_;
    public float f_77277_;
    @Nullable
    public Node f_77278_;
    public boolean f_77279_;
    public float f_77280_;
    public float f_77281_;
    public BlockPathTypes f_77282_ = BlockPathTypes.BLOCKED;

    public Node(int p_77285_, int p_77286_, int p_77287_) {
        this.f_77271_ = p_77285_;
        this.f_77272_ = p_77286_;
        this.f_77273_ = p_77287_;
        this.f_77283_ = Node.m_77295_(p_77285_, p_77286_, p_77287_);
    }

    public Node m_77289_(int p_77290_, int p_77291_, int p_77292_) {
        Node $$3 = new Node(p_77290_, p_77291_, p_77292_);
        $$3.f_77274_ = this.f_77274_;
        $$3.f_77275_ = this.f_77275_;
        $$3.f_77276_ = this.f_77276_;
        $$3.f_77277_ = this.f_77277_;
        $$3.f_77278_ = this.f_77278_;
        $$3.f_77279_ = this.f_77279_;
        $$3.f_77280_ = this.f_77280_;
        $$3.f_77281_ = this.f_77281_;
        $$3.f_77282_ = this.f_77282_;
        return $$3;
    }

    public static int m_77295_(int p_77296_, int p_77297_, int p_77298_) {
        return p_77297_ & 0xFF | (p_77296_ & Short.MAX_VALUE) << 8 | (p_77298_ & Short.MAX_VALUE) << 24 | (p_77296_ < 0 ? Integer.MIN_VALUE : 0) | (p_77298_ < 0 ? 32768 : 0);
    }

    public float m_77293_(Node p_77294_) {
        float $$1 = p_77294_.f_77271_ - this.f_77271_;
        float $$2 = p_77294_.f_77272_ - this.f_77272_;
        float $$3 = p_77294_.f_77273_ - this.f_77273_;
        return Mth.m_14116_($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
    }

    public float m_230613_(Node p_230614_) {
        float $$1 = p_230614_.f_77271_ - this.f_77271_;
        float $$2 = p_230614_.f_77273_ - this.f_77273_;
        return Mth.m_14116_($$1 * $$1 + $$2 * $$2);
    }

    public float m_164697_(BlockPos p_164698_) {
        float $$1 = p_164698_.m_123341_() - this.f_77271_;
        float $$2 = p_164698_.m_123342_() - this.f_77272_;
        float $$3 = p_164698_.m_123343_() - this.f_77273_;
        return Mth.m_14116_($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
    }

    public float m_77299_(Node p_77300_) {
        float $$1 = p_77300_.f_77271_ - this.f_77271_;
        float $$2 = p_77300_.f_77272_ - this.f_77272_;
        float $$3 = p_77300_.f_77273_ - this.f_77273_;
        return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
    }

    public float m_164702_(BlockPos p_164703_) {
        float $$1 = p_164703_.m_123341_() - this.f_77271_;
        float $$2 = p_164703_.m_123342_() - this.f_77272_;
        float $$3 = p_164703_.m_123343_() - this.f_77273_;
        return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
    }

    public float m_77304_(Node p_77305_) {
        float $$1 = Math.abs(p_77305_.f_77271_ - this.f_77271_);
        float $$2 = Math.abs(p_77305_.f_77272_ - this.f_77272_);
        float $$3 = Math.abs(p_77305_.f_77273_ - this.f_77273_);
        return $$1 + $$2 + $$3;
    }

    public float m_77306_(BlockPos p_77307_) {
        float $$1 = Math.abs(p_77307_.m_123341_() - this.f_77271_);
        float $$2 = Math.abs(p_77307_.m_123342_() - this.f_77272_);
        float $$3 = Math.abs(p_77307_.m_123343_() - this.f_77273_);
        return $$1 + $$2 + $$3;
    }

    public BlockPos m_77288_() {
        return new BlockPos(this.f_77271_, this.f_77272_, this.f_77273_);
    }

    public Vec3 m_164701_() {
        return new Vec3(this.f_77271_, this.f_77272_, this.f_77273_);
    }

    public boolean equals(Object p_77309_) {
        if (p_77309_ instanceof Node) {
            Node $$1 = (Node)p_77309_;
            return this.f_77283_ == $$1.f_77283_ && this.f_77271_ == $$1.f_77271_ && this.f_77272_ == $$1.f_77272_ && this.f_77273_ == $$1.f_77273_;
        }
        return false;
    }

    public int hashCode() {
        return this.f_77283_;
    }

    public boolean m_77303_() {
        return this.f_77274_ >= 0;
    }

    public String toString() {
        return "Node{x=" + this.f_77271_ + ", y=" + this.f_77272_ + ", z=" + this.f_77273_ + "}";
    }

    public void m_164699_(FriendlyByteBuf p_164700_) {
        p_164700_.writeInt(this.f_77271_);
        p_164700_.writeInt(this.f_77272_);
        p_164700_.writeInt(this.f_77273_);
        p_164700_.writeFloat(this.f_77280_);
        p_164700_.writeFloat(this.f_77281_);
        p_164700_.writeBoolean(this.f_77279_);
        p_164700_.writeInt(this.f_77282_.ordinal());
        p_164700_.writeFloat(this.f_77277_);
    }

    public static Node m_77301_(FriendlyByteBuf p_77302_) {
        Node $$1 = new Node(p_77302_.readInt(), p_77302_.readInt(), p_77302_.readInt());
        $$1.f_77280_ = p_77302_.readFloat();
        $$1.f_77281_ = p_77302_.readFloat();
        $$1.f_77279_ = p_77302_.readBoolean();
        $$1.f_77282_ = BlockPathTypes.values()[p_77302_.readInt()];
        $$1.f_77277_ = p_77302_.readFloat();
        return $$1;
    }
}

