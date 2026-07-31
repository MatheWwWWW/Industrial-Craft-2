/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.ItemStackTagFix;

public class FilteredBooksFix
extends ItemStackTagFix {
    public FilteredBooksFix(Schema p_216660_) {
        super(p_216660_, "Remove filtered text from books", p_216664_ -> p_216664_.equals("minecraft:writable_book") || p_216664_.equals("minecraft:written_book"));
    }

    @Override
    protected <T> Dynamic<T> m_213922_(Dynamic<T> p_216662_) {
        return p_216662_.remove("filtered_title").remove("filtered_pages");
    }
}

