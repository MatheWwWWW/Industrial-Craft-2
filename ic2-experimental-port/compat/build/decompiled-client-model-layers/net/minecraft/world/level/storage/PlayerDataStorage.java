/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;

public class PlayerDataStorage {
    private static final Logger f_78426_ = LogUtils.getLogger();
    private final File f_78427_;
    protected final DataFixer f_78425_;

    public PlayerDataStorage(LevelStorageSource.LevelStorageAccess p_78430_, DataFixer p_78431_) {
        this.f_78425_ = p_78431_;
        this.f_78427_ = p_78430_.m_78283_(LevelResource.f_78176_).toFile();
        this.f_78427_.mkdirs();
    }

    public void m_78433_(Player p_78434_) {
        try {
            CompoundTag $$1 = p_78434_.m_20240_(new CompoundTag());
            File $$2 = File.createTempFile(p_78434_.m_20149_() + "-", ".dat", this.f_78427_);
            NbtIo.m_128944_($$1, $$2);
            File $$3 = new File(this.f_78427_, p_78434_.m_20149_() + ".dat");
            File $$4 = new File(this.f_78427_, p_78434_.m_20149_() + ".dat_old");
            Util.m_137462_($$3, $$2, $$4);
        }
        catch (Exception $$5) {
            f_78426_.warn("Failed to save player data for {}", (Object)p_78434_.m_7755_().getString());
        }
    }

    @Nullable
    public CompoundTag m_78435_(Player p_78436_) {
        CompoundTag $$1 = null;
        try {
            File $$2 = new File(this.f_78427_, p_78436_.m_20149_() + ".dat");
            if ($$2.exists() && $$2.isFile()) {
                $$1 = NbtIo.m_128937_($$2);
            }
        }
        catch (Exception $$3) {
            f_78426_.warn("Failed to load player data for {}", (Object)p_78436_.m_7755_().getString());
        }
        if ($$1 != null) {
            int $$4 = $$1.m_128425_("DataVersion", 3) ? $$1.m_128451_("DataVersion") : -1;
            p_78436_.m_20258_(NbtUtils.m_129213_(this.f_78425_, DataFixTypes.PLAYER, $$1, $$4));
        }
        return $$1;
    }

    public String[] m_78432_() {
        String[] $$0 = this.f_78427_.list();
        if ($$0 == null) {
            $$0 = new String[]{};
        }
        for (int $$1 = 0; $$1 < $$0.length; ++$$1) {
            if (!$$0[$$1].endsWith(".dat")) continue;
            $$0[$$1] = $$0[$$1].substring(0, $$0[$$1].length() - 4);
        }
        return $$0;
    }
}

