/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL$TypeReference
 */
package net.minecraft.util.datafix;

import com.mojang.datafixers.DSL;
import net.minecraft.util.datafix.fixes.References;

public final class DataFixTypes
extends Enum<DataFixTypes> {
    public static final /* enum */ DataFixTypes LEVEL = new DataFixTypes(References.f_16771_);
    public static final /* enum */ DataFixTypes PLAYER = new DataFixTypes(References.f_16772_);
    public static final /* enum */ DataFixTypes CHUNK = new DataFixTypes(References.f_16773_);
    public static final /* enum */ DataFixTypes HOTBAR = new DataFixTypes(References.f_16774_);
    public static final /* enum */ DataFixTypes OPTIONS = new DataFixTypes(References.f_16775_);
    public static final /* enum */ DataFixTypes STRUCTURE = new DataFixTypes(References.f_16776_);
    public static final /* enum */ DataFixTypes STATS = new DataFixTypes(References.f_16777_);
    public static final /* enum */ DataFixTypes SAVED_DATA = new DataFixTypes(References.f_16778_);
    public static final /* enum */ DataFixTypes ADVANCEMENTS = new DataFixTypes(References.f_16779_);
    public static final /* enum */ DataFixTypes POI_CHUNK = new DataFixTypes(References.f_16780_);
    public static final /* enum */ DataFixTypes WORLD_GEN_SETTINGS = new DataFixTypes(References.f_16795_);
    public static final /* enum */ DataFixTypes ENTITY_CHUNK = new DataFixTypes(References.f_145628_);
    private final DSL.TypeReference f_14497_;
    private static final /* synthetic */ DataFixTypes[] $VALUES;

    public static DataFixTypes[] values() {
        return (DataFixTypes[])$VALUES.clone();
    }

    public static DataFixTypes valueOf(String p_14506_) {
        return Enum.valueOf(DataFixTypes.class, p_14506_);
    }

    private DataFixTypes(DSL.TypeReference p_14503_) {
        this.f_14497_ = p_14503_;
    }

    public DSL.TypeReference m_14504_() {
        return this.f_14497_;
    }

    private static /* synthetic */ DataFixTypes[] m_145042_() {
        return new DataFixTypes[]{LEVEL, PLAYER, CHUNK, HOTBAR, OPTIONS, STRUCTURE, STATS, SAVED_DATA, ADVANCEMENTS, POI_CHUNK, WORLD_GEN_SETTINGS, ENTITY_CHUNK};
    }

    static {
        $VALUES = DataFixTypes.m_145042_();
    }
}

