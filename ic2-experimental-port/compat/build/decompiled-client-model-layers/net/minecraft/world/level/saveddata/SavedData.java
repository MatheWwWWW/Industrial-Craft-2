/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.saveddata;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.slf4j.Logger;

public abstract class SavedData {
    private static final Logger f_77751_ = LogUtils.getLogger();
    private boolean f_77753_;

    public abstract CompoundTag m_7176_(CompoundTag var1);

    public void m_77762_() {
        this.m_77760_(true);
    }

    public void m_77760_(boolean p_77761_) {
        this.f_77753_ = p_77761_;
    }

    public boolean m_77764_() {
        return this.f_77753_;
    }

    public void m_77757_(File p_77758_) {
        if (!this.m_77764_()) {
            return;
        }
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128365_("data", this.m_7176_(new CompoundTag()));
        $$1.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        try {
            NbtIo.m_128944_($$1, p_77758_);
        }
        catch (IOException $$2) {
            f_77751_.error("Could not save data {}", (Object)this, (Object)$$2);
        }
        this.m_77760_(false);
    }
}

