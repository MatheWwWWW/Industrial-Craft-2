/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.File;
import net.minecraft.SharedConstants;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import org.slf4j.Logger;

public class HotbarManager {
    private static final Logger f_90796_ = LogUtils.getLogger();
    public static final int f_167804_ = 9;
    private final File f_90797_;
    private final DataFixer f_90798_;
    private final Hotbar[] f_90799_ = new Hotbar[9];
    private boolean f_90800_;

    public HotbarManager(File p_90803_, DataFixer p_90804_) {
        this.f_90797_ = new File(p_90803_, "hotbar.nbt");
        this.f_90798_ = p_90804_;
        for (int $$2 = 0; $$2 < 9; ++$$2) {
            this.f_90799_[$$2] = new Hotbar();
        }
    }

    private void m_90808_() {
        try {
            CompoundTag $$0 = NbtIo.m_128953_(this.f_90797_);
            if ($$0 == null) {
                return;
            }
            if (!$$0.m_128425_("DataVersion", 99)) {
                $$0.m_128405_("DataVersion", 1343);
            }
            $$0 = NbtUtils.m_129213_(this.f_90798_, DataFixTypes.HOTBAR, $$0, $$0.m_128451_("DataVersion"));
            for (int $$1 = 0; $$1 < 9; ++$$1) {
                this.f_90799_[$$1].m_108783_($$0.m_128437_(String.valueOf($$1), 10));
            }
        }
        catch (Exception $$2) {
            f_90796_.error("Failed to load creative mode options", (Throwable)$$2);
        }
    }

    public void m_90805_() {
        try {
            CompoundTag $$0 = new CompoundTag();
            $$0.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
            for (int $$1 = 0; $$1 < 9; ++$$1) {
                $$0.m_128365_(String.valueOf($$1), this.m_90806_($$1).m_108782_());
            }
            NbtIo.m_128955_($$0, this.f_90797_);
        }
        catch (Exception $$2) {
            f_90796_.error("Failed to save creative mode options", (Throwable)$$2);
        }
    }

    public Hotbar m_90806_(int p_90807_) {
        if (!this.f_90800_) {
            this.m_90808_();
            this.f_90800_ = true;
        }
        return this.f_90799_[p_90807_];
    }
}

