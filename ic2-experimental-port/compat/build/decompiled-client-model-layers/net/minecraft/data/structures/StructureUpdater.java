/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data.structures;

import com.mojang.logging.LogUtils;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.slf4j.Logger;

public class StructureUpdater
implements SnbtToNbt.Filter {
    private static final Logger f_126499_ = LogUtils.getLogger();

    @Override
    public CompoundTag m_6392_(String p_126503_, CompoundTag p_126504_) {
        if (p_126503_.startsWith("data/minecraft/structures/")) {
            return StructureUpdater.m_176822_(p_126503_, p_126504_);
        }
        return p_126504_;
    }

    public static CompoundTag m_176822_(String p_176823_, CompoundTag p_176824_) {
        return StructureUpdater.m_126507_(p_176823_, StructureUpdater.m_126505_(p_176824_));
    }

    private static CompoundTag m_126505_(CompoundTag p_126506_) {
        if (!p_126506_.m_128425_("DataVersion", 99)) {
            p_126506_.m_128405_("DataVersion", 500);
        }
        return p_126506_;
    }

    private static CompoundTag m_126507_(String p_126508_, CompoundTag p_126509_) {
        StructureTemplate $$2 = new StructureTemplate();
        int $$3 = p_126509_.m_128451_("DataVersion");
        int $$4 = 3075;
        if ($$3 < 3075) {
            f_126499_.warn("SNBT Too old, do not forget to update: {} < {}: {}", new Object[]{$$3, 3075, p_126508_});
        }
        CompoundTag $$5 = NbtUtils.m_129213_(DataFixers.m_14512_(), DataFixTypes.STRUCTURE, p_126509_, $$3);
        $$2.m_74638_($$5);
        return $$2.m_74618_(new CompoundTag());
    }
}

