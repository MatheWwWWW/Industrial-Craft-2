/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public final class NoteBlockInstrument
extends Enum<NoteBlockInstrument>
implements StringRepresentable {
    public static final /* enum */ NoteBlockInstrument HARP = new NoteBlockInstrument("harp", SoundEvents.f_12214_);
    public static final /* enum */ NoteBlockInstrument BASEDRUM = new NoteBlockInstrument("basedrum", SoundEvents.f_12208_);
    public static final /* enum */ NoteBlockInstrument SNARE = new NoteBlockInstrument("snare", SoundEvents.f_12217_);
    public static final /* enum */ NoteBlockInstrument HAT = new NoteBlockInstrument("hat", SoundEvents.f_12215_);
    public static final /* enum */ NoteBlockInstrument BASS = new NoteBlockInstrument("bass", SoundEvents.f_12209_);
    public static final /* enum */ NoteBlockInstrument FLUTE = new NoteBlockInstrument("flute", SoundEvents.f_12212_);
    public static final /* enum */ NoteBlockInstrument BELL = new NoteBlockInstrument("bell", SoundEvents.f_12210_);
    public static final /* enum */ NoteBlockInstrument GUITAR = new NoteBlockInstrument("guitar", SoundEvents.f_12213_);
    public static final /* enum */ NoteBlockInstrument CHIME = new NoteBlockInstrument("chime", SoundEvents.f_12211_);
    public static final /* enum */ NoteBlockInstrument XYLOPHONE = new NoteBlockInstrument("xylophone", SoundEvents.f_12218_);
    public static final /* enum */ NoteBlockInstrument IRON_XYLOPHONE = new NoteBlockInstrument("iron_xylophone", SoundEvents.f_12167_);
    public static final /* enum */ NoteBlockInstrument COW_BELL = new NoteBlockInstrument("cow_bell", SoundEvents.f_12168_);
    public static final /* enum */ NoteBlockInstrument DIDGERIDOO = new NoteBlockInstrument("didgeridoo", SoundEvents.f_12169_);
    public static final /* enum */ NoteBlockInstrument BIT = new NoteBlockInstrument("bit", SoundEvents.f_12170_);
    public static final /* enum */ NoteBlockInstrument BANJO = new NoteBlockInstrument("banjo", SoundEvents.f_12171_);
    public static final /* enum */ NoteBlockInstrument PLING = new NoteBlockInstrument("pling", SoundEvents.f_12216_);
    private final String f_61656_;
    private final SoundEvent f_61657_;
    private static final /* synthetic */ NoteBlockInstrument[] $VALUES;

    public static NoteBlockInstrument[] values() {
        return (NoteBlockInstrument[])$VALUES.clone();
    }

    public static NoteBlockInstrument valueOf(String p_61670_) {
        return Enum.valueOf(NoteBlockInstrument.class, p_61670_);
    }

    private NoteBlockInstrument(String p_61663_, SoundEvent p_61664_) {
        this.f_61656_ = p_61663_;
        this.f_61657_ = p_61664_;
    }

    @Override
    public String m_7912_() {
        return this.f_61656_;
    }

    public SoundEvent m_61668_() {
        return this.f_61657_;
    }

    public static NoteBlockInstrument m_61666_(BlockState p_61667_) {
        if (p_61667_.m_60713_(Blocks.f_50129_)) {
            return FLUTE;
        }
        if (p_61667_.m_60713_(Blocks.f_50074_)) {
            return BELL;
        }
        if (p_61667_.m_204336_(BlockTags.f_13089_)) {
            return GUITAR;
        }
        if (p_61667_.m_60713_(Blocks.f_50354_)) {
            return CHIME;
        }
        if (p_61667_.m_60713_(Blocks.f_50453_)) {
            return XYLOPHONE;
        }
        if (p_61667_.m_60713_(Blocks.f_50075_)) {
            return IRON_XYLOPHONE;
        }
        if (p_61667_.m_60713_(Blocks.f_50135_)) {
            return COW_BELL;
        }
        if (p_61667_.m_60713_(Blocks.f_50133_)) {
            return DIDGERIDOO;
        }
        if (p_61667_.m_60713_(Blocks.f_50268_)) {
            return BIT;
        }
        if (p_61667_.m_60713_(Blocks.f_50335_)) {
            return BANJO;
        }
        if (p_61667_.m_60713_(Blocks.f_50141_)) {
            return PLING;
        }
        Material $$1 = p_61667_.m_60767_();
        if ($$1 == Material.f_76278_) {
            return BASEDRUM;
        }
        if ($$1 == Material.f_76317_) {
            return SNARE;
        }
        if ($$1 == Material.f_76275_) {
            return HAT;
        }
        if ($$1 == Material.f_76320_ || $$1 == Material.f_76321_) {
            return BASS;
        }
        return HARP;
    }

    private static /* synthetic */ NoteBlockInstrument[] m_156026_() {
        return new NoteBlockInstrument[]{HARP, BASEDRUM, SNARE, HAT, BASS, FLUTE, BELL, GUITAR, CHIME, XYLOPHONE, IRON_XYLOPHONE, COW_BELL, DIDGERIDOO, BIT, BANJO, PLING};
    }

    static {
        $VALUES = NoteBlockInstrument.m_156026_();
    }
}

