/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.ServerLevelData;

public interface WorldData {
    public static final int f_164978_ = 19133;
    public static final int f_164979_ = 19132;

    public DataPackConfig m_7513_();

    public void m_6645_(DataPackConfig var1);

    public boolean m_6565_();

    public Set<String> m_6161_();

    public void m_7955_(String var1, boolean var2);

    default public void m_5461_(CrashReportCategory p_78640_) {
        p_78640_.m_128165_("Known server brands", () -> String.join((CharSequence)", ", this.m_6161_()));
        p_78640_.m_128165_("Level was modded", () -> Boolean.toString(this.m_6565_()));
        p_78640_.m_128165_("Level storage version", () -> {
            int $$0 = this.m_6517_();
            return String.format(Locale.ROOT, "0x%05X - %s", $$0, this.m_78646_($$0));
        });
    }

    default public String m_78646_(int p_78647_) {
        switch (p_78647_) {
            case 19133: {
                return "Anvil";
            }
            case 19132: {
                return "McRegion";
            }
        }
        return "Unknown?";
    }

    @Nullable
    public CompoundTag m_6587_();

    public void m_5917_(@Nullable CompoundTag var1);

    public ServerLevelData m_5996_();

    public LevelSettings m_5926_();

    public CompoundTag m_6626_(RegistryAccess var1, @Nullable CompoundTag var2);

    public boolean m_5466_();

    public int m_6517_();

    public String m_5462_();

    public GameType m_5464_();

    public void m_5458_(GameType var1);

    public boolean m_5468_();

    public Difficulty m_5472_();

    public void m_6166_(Difficulty var1);

    public boolean m_5474_();

    public void m_5560_(boolean var1);

    public GameRules m_5470_();

    @Nullable
    public CompoundTag m_6614_();

    public CompoundTag m_6564_();

    public void m_5915_(CompoundTag var1);

    public WorldGenSettings m_5961_();

    public Lifecycle m_5754_();
}

