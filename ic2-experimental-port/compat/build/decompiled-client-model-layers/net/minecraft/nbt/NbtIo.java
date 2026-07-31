/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.nbt;

import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;
import net.minecraft.util.FastBufferedInputStream;

public class NbtIo {
    public static CompoundTag m_128937_(File p_128938_) throws IOException {
        try (FileInputStream $$1 = new FileInputStream(p_128938_);){
            CompoundTag compoundTag = NbtIo.m_128939_($$1);
            return compoundTag;
        }
    }

    private static DataInputStream m_202493_(InputStream p_202494_) throws IOException {
        return new DataInputStream(new FastBufferedInputStream(new GZIPInputStream(p_202494_)));
    }

    public static CompoundTag m_128939_(InputStream p_128940_) throws IOException {
        try (DataInputStream $$1 = NbtIo.m_202493_(p_128940_);){
            CompoundTag compoundTag = NbtIo.m_128934_($$1, NbtAccounter.f_128917_);
            return compoundTag;
        }
    }

    public static void m_202487_(File p_202488_, StreamTagVisitor p_202489_) throws IOException {
        try (FileInputStream $$2 = new FileInputStream(p_202488_);){
            NbtIo.m_202490_($$2, p_202489_);
        }
    }

    public static void m_202490_(InputStream p_202491_, StreamTagVisitor p_202492_) throws IOException {
        try (DataInputStream $$2 = NbtIo.m_202493_(p_202491_);){
            NbtIo.m_197509_($$2, p_202492_);
        }
    }

    public static void m_128944_(CompoundTag p_128945_, File p_128946_) throws IOException {
        try (FileOutputStream $$2 = new FileOutputStream(p_128946_);){
            NbtIo.m_128947_(p_128945_, $$2);
        }
    }

    public static void m_128947_(CompoundTag p_128948_, OutputStream p_128949_) throws IOException {
        try (DataOutputStream $$2 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(p_128949_)));){
            NbtIo.m_128941_(p_128948_, $$2);
        }
    }

    public static void m_128955_(CompoundTag p_128956_, File p_128957_) throws IOException {
        try (FileOutputStream $$2 = new FileOutputStream(p_128957_);
             DataOutputStream $$3 = new DataOutputStream($$2);){
            NbtIo.m_128941_(p_128956_, $$3);
        }
    }

    @Nullable
    public static CompoundTag m_128953_(File p_128954_) throws IOException {
        if (!p_128954_.exists()) {
            return null;
        }
        try (FileInputStream $$1 = new FileInputStream(p_128954_);){
            CompoundTag compoundTag;
            try (DataInputStream $$2 = new DataInputStream($$1);){
                compoundTag = NbtIo.m_128934_($$2, NbtAccounter.f_128917_);
            }
            return compoundTag;
        }
    }

    public static CompoundTag m_128928_(DataInput p_128929_) throws IOException {
        return NbtIo.m_128934_(p_128929_, NbtAccounter.f_128917_);
    }

    public static CompoundTag m_128934_(DataInput p_128935_, NbtAccounter p_128936_) throws IOException {
        Tag $$2 = NbtIo.m_128930_(p_128935_, 0, p_128936_);
        if ($$2 instanceof CompoundTag) {
            return (CompoundTag)$$2;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void m_128941_(CompoundTag p_128942_, DataOutput p_128943_) throws IOException {
        NbtIo.m_128950_(p_128942_, p_128943_);
    }

    public static void m_197509_(DataInput p_197510_, StreamTagVisitor p_197511_) throws IOException {
        TagType<?> $$2 = TagTypes.m_129397_(p_197510_.readByte());
        if ($$2 == EndTag.f_128533_) {
            if (p_197511_.m_196213_(EndTag.f_128533_) == StreamTagVisitor.ValueResult.CONTINUE) {
                p_197511_.m_196525_();
            }
            return;
        }
        switch (p_197511_.m_196213_($$2)) {
            case HALT: {
                break;
            }
            case BREAK: {
                StringTag.m_197563_(p_197510_);
                $$2.m_196159_(p_197510_);
                break;
            }
            case CONTINUE: {
                StringTag.m_197563_(p_197510_);
                $$2.m_196511_(p_197510_, p_197511_);
            }
        }
    }

    public static void m_128950_(Tag p_128951_, DataOutput p_128952_) throws IOException {
        p_128952_.writeByte(p_128951_.m_7060_());
        if (p_128951_.m_7060_() == 0) {
            return;
        }
        p_128952_.writeUTF("");
        p_128951_.m_6434_(p_128952_);
    }

    private static Tag m_128930_(DataInput p_128931_, int p_128932_, NbtAccounter p_128933_) throws IOException {
        byte $$3 = p_128931_.readByte();
        if ($$3 == 0) {
            return EndTag.f_128534_;
        }
        StringTag.m_197563_(p_128931_);
        try {
            return TagTypes.m_129397_($$3).m_7300_(p_128931_, p_128932_, p_128933_);
        }
        catch (IOException $$4) {
            CrashReport $$5 = CrashReport.m_127521_($$4, "Loading NBT data");
            CrashReportCategory $$6 = $$5.m_127514_("NBT Tag");
            $$6.m_128159_("Tag type", $$3);
            throw new ReportedException($$5);
        }
    }
}

