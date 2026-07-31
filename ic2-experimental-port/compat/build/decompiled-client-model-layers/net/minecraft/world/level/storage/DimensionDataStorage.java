/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;

public class DimensionDataStorage {
    private static final Logger f_78143_ = LogUtils.getLogger();
    private final Map<String, SavedData> f_78144_ = Maps.newHashMap();
    private final DataFixer f_78145_;
    private final File f_78146_;

    public DimensionDataStorage(File p_78149_, DataFixer p_78150_) {
        this.f_78145_ = p_78150_;
        this.f_78146_ = p_78149_;
    }

    private File m_78156_(String p_78157_) {
        return new File(this.f_78146_, p_78157_ + ".dat");
    }

    public <T extends SavedData> T m_164861_(Function<CompoundTag, T> p_164862_, Supplier<T> p_164863_, String p_164864_) {
        T $$3 = this.m_164858_(p_164862_, p_164864_);
        if ($$3 != null) {
            return $$3;
        }
        SavedData $$4 = (SavedData)p_164863_.get();
        this.m_164855_(p_164864_, $$4);
        return (T)$$4;
    }

    @Nullable
    public <T extends SavedData> T m_164858_(Function<CompoundTag, T> p_164859_, String p_164860_) {
        SavedData $$2 = this.f_78144_.get(p_164860_);
        if ($$2 == null && !this.f_78144_.containsKey(p_164860_)) {
            $$2 = this.m_164868_(p_164859_, p_164860_);
            this.f_78144_.put(p_164860_, $$2);
        }
        return (T)$$2;
    }

    @Nullable
    private <T extends SavedData> T m_164868_(Function<CompoundTag, T> p_164869_, String p_164870_) {
        try {
            File $$2 = this.m_78156_(p_164870_);
            if ($$2.exists()) {
                CompoundTag $$3 = this.m_78158_(p_164870_, SharedConstants.m_183709_().getWorldVersion());
                return (T)((SavedData)p_164869_.apply($$3.m_128469_("data")));
            }
        }
        catch (Exception $$4) {
            f_78143_.error("Error loading saved data: {}", (Object)p_164870_, (Object)$$4);
        }
        return null;
    }

    public void m_164855_(String p_164856_, SavedData p_164857_) {
        this.f_78144_.put(p_164856_, p_164857_);
    }

    /*
     * WARNING - void declaration
     */
    public CompoundTag m_78158_(String p_78159_, int p_78160_) throws IOException {
        File $$2 = this.m_78156_(p_78159_);
        try (FileInputStream $$3 = new FileInputStream($$2);){
            CompoundTag compoundTag;
            try (PushbackInputStream $$4 = new PushbackInputStream($$3, 2);){
                void $$8;
                if (this.m_78154_($$4)) {
                    CompoundTag $$5 = NbtIo.m_128939_($$4);
                } else {
                    try (DataInputStream $$6 = new DataInputStream($$4);){
                        CompoundTag $$7 = NbtIo.m_128928_($$6);
                    }
                }
                int $$9 = $$8.m_128425_("DataVersion", 99) ? $$8.m_128451_("DataVersion") : 1343;
                compoundTag = NbtUtils.m_129218_(this.f_78145_, DataFixTypes.SAVED_DATA, (CompoundTag)$$8, $$9, p_78160_);
            }
            return compoundTag;
        }
    }

    private boolean m_78154_(PushbackInputStream p_78155_) throws IOException {
        int $$4;
        byte[] $$1 = new byte[2];
        boolean $$2 = false;
        int $$3 = p_78155_.read($$1, 0, 2);
        if ($$3 == 2 && ($$4 = ($$1[1] & 0xFF) << 8 | $$1[0] & 0xFF) == 35615) {
            $$2 = true;
        }
        if ($$3 != 0) {
            p_78155_.unread($$1, 0, $$3);
        }
        return $$2;
    }

    public void m_78151_() {
        this.f_78144_.forEach((p_164866_, p_164867_) -> {
            if (p_164867_ != null) {
                p_164867_.m_77757_(this.m_78156_((String)p_164866_));
            }
        });
    }
}

