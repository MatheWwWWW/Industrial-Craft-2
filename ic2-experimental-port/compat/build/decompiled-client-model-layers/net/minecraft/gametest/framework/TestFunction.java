/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.gametest.framework;

import java.util.function.Consumer;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Rotation;

public class TestFunction {
    private final String f_128067_;
    private final String f_128068_;
    private final String f_128069_;
    private final boolean f_128070_;
    private final int f_177798_;
    private final int f_177799_;
    private final Consumer<GameTestHelper> f_128071_;
    private final int f_128072_;
    private final long f_128073_;
    private final Rotation f_128074_;

    public TestFunction(String p_177801_, String p_177802_, String p_177803_, int p_177804_, long p_177805_, boolean p_177806_, Consumer<GameTestHelper> p_177807_) {
        this(p_177801_, p_177802_, p_177803_, Rotation.NONE, p_177804_, p_177805_, p_177806_, 1, 1, p_177807_);
    }

    public TestFunction(String p_177820_, String p_177821_, String p_177822_, Rotation p_177823_, int p_177824_, long p_177825_, boolean p_177826_, Consumer<GameTestHelper> p_177827_) {
        this(p_177820_, p_177821_, p_177822_, p_177823_, p_177824_, p_177825_, p_177826_, 1, 1, p_177827_);
    }

    public TestFunction(String p_177809_, String p_177810_, String p_177811_, Rotation p_177812_, int p_177813_, long p_177814_, boolean p_177815_, int p_177816_, int p_177817_, Consumer<GameTestHelper> p_177818_) {
        this.f_128067_ = p_177809_;
        this.f_128068_ = p_177810_;
        this.f_128069_ = p_177811_;
        this.f_128074_ = p_177812_;
        this.f_128072_ = p_177813_;
        this.f_128070_ = p_177815_;
        this.f_177799_ = p_177816_;
        this.f_177798_ = p_177817_;
        this.f_128071_ = p_177818_;
        this.f_128073_ = p_177814_;
    }

    public void m_128076_(GameTestHelper p_128077_) {
        this.f_128071_.accept(p_128077_);
    }

    public String m_128075_() {
        return this.f_128068_;
    }

    public String m_128078_() {
        return this.f_128069_;
    }

    public String toString() {
        return this.f_128068_;
    }

    public int m_128079_() {
        return this.f_128072_;
    }

    public boolean m_128080_() {
        return this.f_128070_;
    }

    public String m_128081_() {
        return this.f_128067_;
    }

    public long m_128082_() {
        return this.f_128073_;
    }

    public Rotation m_128083_() {
        return this.f_128074_;
    }

    public boolean m_177828_() {
        return this.f_177798_ > 1;
    }

    public int m_177829_() {
        return this.f_177798_;
    }

    public int m_177830_() {
        return this.f_177799_;
    }
}

