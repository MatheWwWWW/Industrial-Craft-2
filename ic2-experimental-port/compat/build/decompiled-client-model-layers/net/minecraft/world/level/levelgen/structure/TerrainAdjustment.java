/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public final class TerrainAdjustment
extends Enum<TerrainAdjustment>
implements StringRepresentable {
    public static final /* enum */ TerrainAdjustment NONE = new TerrainAdjustment("none");
    public static final /* enum */ TerrainAdjustment BURY = new TerrainAdjustment("bury");
    public static final /* enum */ TerrainAdjustment BEARD_THIN = new TerrainAdjustment("beard_thin");
    public static final /* enum */ TerrainAdjustment BEARD_BOX = new TerrainAdjustment("beard_box");
    public static final Codec<TerrainAdjustment> f_226918_;
    private final String f_226919_;
    private static final /* synthetic */ TerrainAdjustment[] $VALUES;

    public static TerrainAdjustment[] values() {
        return (TerrainAdjustment[])$VALUES.clone();
    }

    public static TerrainAdjustment valueOf(String p_226929_) {
        return Enum.valueOf(TerrainAdjustment.class, p_226929_);
    }

    private TerrainAdjustment(String p_226925_) {
        this.f_226919_ = p_226925_;
    }

    @Override
    public String m_7912_() {
        return this.f_226919_;
    }

    private static /* synthetic */ TerrainAdjustment[] m_226926_() {
        return new TerrainAdjustment[]{NONE, BURY, BEARD_THIN, BEARD_BOX};
    }

    static {
        $VALUES = TerrainAdjustment.m_226926_();
        f_226918_ = StringRepresentable.m_216439_(TerrainAdjustment::values);
    }
}

