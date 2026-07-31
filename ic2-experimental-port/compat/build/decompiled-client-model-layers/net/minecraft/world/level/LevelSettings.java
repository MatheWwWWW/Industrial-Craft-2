/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicLike
 */
package net.minecraft.world.level;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicLike;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

public final class LevelSettings {
    private final String f_46902_;
    private final GameType f_46903_;
    private final boolean f_46904_;
    private final Difficulty f_46905_;
    private final boolean f_46906_;
    private final GameRules f_46907_;
    private final DataPackConfig f_46908_;

    public LevelSettings(String p_46910_, GameType p_46911_, boolean p_46912_, Difficulty p_46913_, boolean p_46914_, GameRules p_46915_, DataPackConfig p_46916_) {
        this.f_46902_ = p_46910_;
        this.f_46903_ = p_46911_;
        this.f_46904_ = p_46912_;
        this.f_46905_ = p_46913_;
        this.f_46906_ = p_46914_;
        this.f_46907_ = p_46915_;
        this.f_46908_ = p_46916_;
    }

    public static LevelSettings m_46924_(Dynamic<?> p_46925_, DataPackConfig p_46926_) {
        GameType $$2 = GameType.m_46393_(p_46925_.get("GameType").asInt(0));
        return new LevelSettings(p_46925_.get("LevelName").asString(""), $$2, p_46925_.get("hardcore").asBoolean(false), p_46925_.get("Difficulty").asNumber().map(p_46928_ -> Difficulty.m_19029_(p_46928_.byteValue())).result().orElse(Difficulty.NORMAL), p_46925_.get("allowCommands").asBoolean($$2 == GameType.CREATIVE), new GameRules((DynamicLike<?>)p_46925_.get("GameRules")), p_46926_);
    }

    public String m_46917_() {
        return this.f_46902_;
    }

    public GameType m_46929_() {
        return this.f_46903_;
    }

    public boolean m_46930_() {
        return this.f_46904_;
    }

    public Difficulty m_46931_() {
        return this.f_46905_;
    }

    public boolean m_46932_() {
        return this.f_46906_;
    }

    public GameRules m_46933_() {
        return this.f_46907_;
    }

    public DataPackConfig m_46934_() {
        return this.f_46908_;
    }

    public LevelSettings m_46922_(GameType p_46923_) {
        return new LevelSettings(this.f_46902_, p_46923_, this.f_46904_, this.f_46905_, this.f_46906_, this.f_46907_, this.f_46908_);
    }

    public LevelSettings m_46918_(Difficulty p_46919_) {
        return new LevelSettings(this.f_46902_, this.f_46903_, this.f_46904_, p_46919_, this.f_46906_, this.f_46907_, this.f_46908_);
    }

    public LevelSettings m_46920_(DataPackConfig p_46921_) {
        return new LevelSettings(this.f_46902_, this.f_46903_, this.f_46904_, this.f_46905_, this.f_46906_, this.f_46907_, p_46921_);
    }

    public LevelSettings m_46935_() {
        return new LevelSettings(this.f_46902_, this.f_46903_, this.f_46904_, this.f_46905_, this.f_46906_, this.f_46907_.m_46202_(), this.f_46908_);
    }
}

