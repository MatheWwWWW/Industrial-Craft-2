/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.server.level.ServerLevel;

public class GameTestBatch {
    public static final String f_177056_ = "defaultBatch";
    private final String f_127539_;
    private final Collection<TestFunction> f_127540_;
    @Nullable
    private final Consumer<ServerLevel> f_127541_;
    @Nullable
    private final Consumer<ServerLevel> f_177057_;

    public GameTestBatch(String p_177059_, Collection<TestFunction> p_177060_, @Nullable Consumer<ServerLevel> p_177061_, @Nullable Consumer<ServerLevel> p_177062_) {
        if (p_177060_.isEmpty()) {
            throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
        }
        this.f_127539_ = p_177059_;
        this.f_127540_ = p_177060_;
        this.f_127541_ = p_177061_;
        this.f_177057_ = p_177062_;
    }

    public String m_127546_() {
        return this.f_127539_;
    }

    public Collection<TestFunction> m_127549_() {
        return this.f_127540_;
    }

    public void m_127547_(ServerLevel p_127548_) {
        if (this.f_127541_ != null) {
            this.f_127541_.accept(p_127548_);
        }
    }

    public void m_177063_(ServerLevel p_177064_) {
        if (this.f_177057_ != null) {
            this.f_177057_.accept(p_177064_);
        }
    }
}

