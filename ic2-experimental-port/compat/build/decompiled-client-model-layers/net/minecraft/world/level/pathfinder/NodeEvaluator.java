/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;

public abstract class NodeEvaluator {
    protected PathNavigationRegion f_77312_;
    protected Mob f_77313_;
    protected final Int2ObjectMap<Node> f_77314_ = new Int2ObjectOpenHashMap();
    protected int f_77315_;
    protected int f_77316_;
    protected int f_77317_;
    protected boolean f_77318_;
    protected boolean f_77319_;
    protected boolean f_77320_;

    public void m_6028_(PathNavigationRegion p_77347_, Mob p_77348_) {
        this.f_77312_ = p_77347_;
        this.f_77313_ = p_77348_;
        this.f_77314_.clear();
        this.f_77315_ = Mth.m_14143_(p_77348_.m_20205_() + 1.0f);
        this.f_77316_ = Mth.m_14143_(p_77348_.m_20206_() + 1.0f);
        this.f_77317_ = Mth.m_14143_(p_77348_.m_20205_() + 1.0f);
    }

    public void m_6802_() {
        this.f_77312_ = null;
        this.f_77313_ = null;
    }

    @Nullable
    protected Node m_77349_(BlockPos p_77350_) {
        return this.m_5676_(p_77350_.m_123341_(), p_77350_.m_123342_(), p_77350_.m_123343_());
    }

    @Nullable
    protected Node m_5676_(int p_77325_, int p_77326_, int p_77327_) {
        return (Node)this.f_77314_.computeIfAbsent(Node.m_77295_(p_77325_, p_77326_, p_77327_), p_77332_ -> new Node(p_77325_, p_77326_, p_77327_));
    }

    @Nullable
    public abstract Node m_7171_();

    @Nullable
    public abstract Target m_7568_(double var1, double var3, double var5);

    @Nullable
    protected Target m_230615_(@Nullable Node p_230616_) {
        if (p_230616_ != null) {
            return new Target(p_230616_);
        }
        return null;
    }

    public abstract int m_6065_(Node[] var1, Node var2);

    public abstract BlockPathTypes m_7209_(BlockGetter var1, int var2, int var3, int var4, Mob var5, int var6, int var7, int var8, boolean var9, boolean var10);

    public abstract BlockPathTypes m_8086_(BlockGetter var1, int var2, int var3, int var4);

    public void m_77351_(boolean p_77352_) {
        this.f_77318_ = p_77352_;
    }

    public void m_77355_(boolean p_77356_) {
        this.f_77319_ = p_77356_;
    }

    public void m_77358_(boolean p_77359_) {
        this.f_77320_ = p_77359_;
    }

    public boolean m_77357_() {
        return this.f_77318_;
    }

    public boolean m_77360_() {
        return this.f_77319_;
    }

    public boolean m_77361_() {
        return this.f_77320_;
    }
}

