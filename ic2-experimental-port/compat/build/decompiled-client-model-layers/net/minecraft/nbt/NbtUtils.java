/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Splitter
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.SnbtPrinterTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.nbt.TagTypes;
import net.minecraft.nbt.TextComponentTagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.material.FluidState;
import org.slf4j.Logger;

public final class NbtUtils {
    private static final Comparator<ListTag> f_178008_ = Comparator.comparingInt(p_178074_ -> p_178074_.m_128763_(1)).thenComparingInt(p_178070_ -> p_178070_.m_128763_(0)).thenComparingInt(p_178066_ -> p_178066_.m_128763_(2));
    private static final Comparator<ListTag> f_178009_ = Comparator.comparingDouble(p_178060_ -> p_178060_.m_128772_(1)).thenComparingDouble(p_178056_ -> p_178056_.m_128772_(0)).thenComparingDouble(p_178042_ -> p_178042_.m_128772_(2));
    public static final String f_178007_ = "data";
    private static final char f_178010_ = '{';
    private static final char f_178011_ = '}';
    private static final String f_178012_ = ",";
    private static final char f_178013_ = ':';
    private static final Splitter f_178014_ = Splitter.on((String)",");
    private static final Splitter f_178015_ = Splitter.on((char)':').limit(2);
    private static final Logger f_129200_ = LogUtils.getLogger();
    private static final int f_178016_ = 2;
    private static final int f_178017_ = -1;

    private NbtUtils() {
    }

    @Nullable
    public static GameProfile m_129228_(CompoundTag p_129229_) {
        String $$1 = null;
        UUID $$2 = null;
        if (p_129229_.m_128425_("Name", 8)) {
            $$1 = p_129229_.m_128461_("Name");
        }
        if (p_129229_.m_128403_("Id")) {
            $$2 = p_129229_.m_128342_("Id");
        }
        try {
            GameProfile $$3 = new GameProfile($$2, $$1);
            if (p_129229_.m_128425_("Properties", 10)) {
                CompoundTag $$4 = p_129229_.m_128469_("Properties");
                for (String $$5 : $$4.m_128431_()) {
                    ListTag $$6 = $$4.m_128437_($$5, 10);
                    for (int $$7 = 0; $$7 < $$6.size(); ++$$7) {
                        CompoundTag $$8 = $$6.m_128728_($$7);
                        String $$9 = $$8.m_128461_("Value");
                        if ($$8.m_128425_("Signature", 8)) {
                            $$3.getProperties().put((Object)$$5, (Object)new Property($$5, $$9, $$8.m_128461_("Signature")));
                            continue;
                        }
                        $$3.getProperties().put((Object)$$5, (Object)new Property($$5, $$9));
                    }
                }
            }
            return $$3;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static CompoundTag m_129230_(CompoundTag p_129231_, GameProfile p_129232_) {
        if (!StringUtil.m_14408_(p_129232_.getName())) {
            p_129231_.m_128359_("Name", p_129232_.getName());
        }
        if (p_129232_.getId() != null) {
            p_129231_.m_128362_("Id", p_129232_.getId());
        }
        if (!p_129232_.getProperties().isEmpty()) {
            CompoundTag $$2 = new CompoundTag();
            for (String $$3 : p_129232_.getProperties().keySet()) {
                ListTag $$4 = new ListTag();
                for (Property $$5 : p_129232_.getProperties().get((Object)$$3)) {
                    CompoundTag $$6 = new CompoundTag();
                    $$6.m_128359_("Value", $$5.getValue());
                    if ($$5.hasSignature()) {
                        $$6.m_128359_("Signature", $$5.getSignature());
                    }
                    $$4.add($$6);
                }
                $$2.m_128365_($$3, $$4);
            }
            p_129231_.m_128365_("Properties", $$2);
        }
        return p_129231_;
    }

    @VisibleForTesting
    public static boolean m_129235_(@Nullable Tag p_129236_, @Nullable Tag p_129237_, boolean p_129238_) {
        if (p_129236_ == p_129237_) {
            return true;
        }
        if (p_129236_ == null) {
            return true;
        }
        if (p_129237_ == null) {
            return false;
        }
        if (!p_129236_.getClass().equals(p_129237_.getClass())) {
            return false;
        }
        if (p_129236_ instanceof CompoundTag) {
            CompoundTag $$3 = (CompoundTag)p_129236_;
            CompoundTag $$4 = (CompoundTag)p_129237_;
            for (String $$5 : $$3.m_128431_()) {
                Tag $$6 = $$3.m_128423_($$5);
                if (NbtUtils.m_129235_($$6, $$4.m_128423_($$5), p_129238_)) continue;
                return false;
            }
            return true;
        }
        if (p_129236_ instanceof ListTag && p_129238_) {
            ListTag $$7 = (ListTag)p_129236_;
            ListTag $$8 = (ListTag)p_129237_;
            if ($$7.isEmpty()) {
                return $$8.isEmpty();
            }
            for (int $$9 = 0; $$9 < $$7.size(); ++$$9) {
                Tag $$10 = $$7.get($$9);
                boolean $$11 = false;
                for (int $$12 = 0; $$12 < $$8.size(); ++$$12) {
                    if (!NbtUtils.m_129235_($$10, $$8.get($$12), p_129238_)) continue;
                    $$11 = true;
                    break;
                }
                if ($$11) continue;
                return false;
            }
            return true;
        }
        return p_129236_.equals(p_129237_);
    }

    public static IntArrayTag m_129226_(UUID p_129227_) {
        return new IntArrayTag(UUIDUtil.m_235881_(p_129227_));
    }

    public static UUID m_129233_(Tag p_129234_) {
        if (p_129234_.m_6458_() != IntArrayTag.f_128599_) {
            throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayTag.f_128599_.m_5987_() + ", but found " + p_129234_.m_6458_().m_5987_() + ".");
        }
        int[] $$1 = ((IntArrayTag)p_129234_).m_128648_();
        if ($$1.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + $$1.length + ".");
        }
        return UUIDUtil.m_235885_($$1);
    }

    public static BlockPos m_129239_(CompoundTag p_129240_) {
        return new BlockPos(p_129240_.m_128451_("X"), p_129240_.m_128451_("Y"), p_129240_.m_128451_("Z"));
    }

    public static CompoundTag m_129224_(BlockPos p_129225_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128405_("X", p_129225_.m_123341_());
        $$1.m_128405_("Y", p_129225_.m_123342_());
        $$1.m_128405_("Z", p_129225_.m_123343_());
        return $$1;
    }

    public static BlockState m_129241_(CompoundTag p_129242_) {
        if (!p_129242_.m_128425_("Name", 8)) {
            return Blocks.f_50016_.m_49966_();
        }
        Block $$1 = Registry.f_122824_.m_7745_(new ResourceLocation(p_129242_.m_128461_("Name")));
        BlockState $$2 = $$1.m_49966_();
        if (p_129242_.m_128425_("Properties", 10)) {
            CompoundTag $$3 = p_129242_.m_128469_("Properties");
            StateDefinition<Block, BlockState> $$4 = $$1.m_49965_();
            for (String $$5 : $$3.m_128431_()) {
                net.minecraft.world.level.block.state.properties.Property<?> $$6 = $$4.m_61081_($$5);
                if ($$6 == null) continue;
                $$2 = NbtUtils.m_129204_($$2, $$6, $$5, $$3, p_129242_);
            }
        }
        return $$2;
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S m_129204_(S p_129205_, net.minecraft.world.level.block.state.properties.Property<T> p_129206_, String p_129207_, CompoundTag p_129208_, CompoundTag p_129209_) {
        Optional<T> $$5 = p_129206_.m_6215_(p_129208_.m_128461_(p_129207_));
        if ($$5.isPresent()) {
            return (S)((StateHolder)p_129205_.m_61124_(p_129206_, (Comparable)((Comparable)$$5.get())));
        }
        f_129200_.warn("Unable to read property: {} with value: {} for blockstate: {}", new Object[]{p_129207_, p_129208_.m_128461_(p_129207_), p_129209_.toString()});
        return p_129205_;
    }

    public static CompoundTag m_129202_(BlockState p_129203_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128359_("Name", Registry.f_122824_.m_7981_(p_129203_.m_60734_()).toString());
        ImmutableMap<net.minecraft.world.level.block.state.properties.Property<?>, Comparable<?>> $$2 = p_129203_.m_61148_();
        if (!$$2.isEmpty()) {
            CompoundTag $$3 = new CompoundTag();
            for (Map.Entry $$4 : $$2.entrySet()) {
                net.minecraft.world.level.block.state.properties.Property $$5 = (net.minecraft.world.level.block.state.properties.Property)$$4.getKey();
                $$3.m_128359_($$5.m_61708_(), NbtUtils.m_129210_($$5, (Comparable)$$4.getValue()));
            }
            $$1.m_128365_("Properties", $$3);
        }
        return $$1;
    }

    public static CompoundTag m_178022_(FluidState p_178023_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128359_("Name", Registry.f_122822_.m_7981_(p_178023_.m_76152_()).toString());
        ImmutableMap<net.minecraft.world.level.block.state.properties.Property<?>, Comparable<?>> $$2 = p_178023_.m_61148_();
        if (!$$2.isEmpty()) {
            CompoundTag $$3 = new CompoundTag();
            for (Map.Entry $$4 : $$2.entrySet()) {
                net.minecraft.world.level.block.state.properties.Property $$5 = (net.minecraft.world.level.block.state.properties.Property)$$4.getKey();
                $$3.m_128359_($$5.m_61708_(), NbtUtils.m_129210_($$5, (Comparable)$$4.getValue()));
            }
            $$1.m_128365_("Properties", $$3);
        }
        return $$1;
    }

    private static <T extends Comparable<T>> String m_129210_(net.minecraft.world.level.block.state.properties.Property<T> p_129211_, Comparable<?> p_129212_) {
        return p_129211_.m_6940_(p_129212_);
    }

    public static String m_178057_(Tag p_178058_) {
        return NbtUtils.m_178050_(p_178058_, false);
    }

    public static String m_178050_(Tag p_178051_, boolean p_178052_) {
        return NbtUtils.m_178026_(new StringBuilder(), p_178051_, 0, p_178052_).toString();
    }

    public static StringBuilder m_178026_(StringBuilder p_178027_, Tag p_178028_, int p_178029_, boolean p_178030_) {
        switch (p_178028_.m_7060_()) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: {
                p_178027_.append(p_178028_);
                break;
            }
            case 0: {
                break;
            }
            case 7: {
                ByteArrayTag $$4 = (ByteArrayTag)p_178028_;
                byte[] $$5 = $$4.m_128227_();
                int $$6 = $$5.length;
                NbtUtils.m_178019_(p_178029_, p_178027_).append("byte[").append($$6).append("] {\n");
                if (p_178030_) {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                    for (int $$7 = 0; $$7 < $$5.length; ++$$7) {
                        if ($$7 != 0) {
                            p_178027_.append(',');
                        }
                        if ($$7 % 16 == 0 && $$7 / 16 > 0) {
                            p_178027_.append('\n');
                            if ($$7 < $$5.length) {
                                NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                            }
                        } else if ($$7 != 0) {
                            p_178027_.append(' ');
                        }
                        p_178027_.append(String.format(Locale.ROOT, "0x%02X", $$5[$$7] & 0xFF));
                    }
                } else {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
                }
                p_178027_.append('\n');
                NbtUtils.m_178019_(p_178029_, p_178027_).append('}');
                break;
            }
            case 9: {
                ListTag $$8 = (ListTag)p_178028_;
                int $$9 = $$8.size();
                byte $$10 = $$8.m_7264_();
                String $$11 = $$10 == 0 ? "undefined" : TagTypes.m_129397_($$10).m_5986_();
                NbtUtils.m_178019_(p_178029_, p_178027_).append("list<").append($$11).append(">[").append($$9).append("] [");
                if ($$9 != 0) {
                    p_178027_.append('\n');
                }
                for (int $$12 = 0; $$12 < $$9; ++$$12) {
                    if ($$12 != 0) {
                        p_178027_.append(",\n");
                    }
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                    NbtUtils.m_178026_(p_178027_, $$8.get($$12), p_178029_ + 1, p_178030_);
                }
                if ($$9 != 0) {
                    p_178027_.append('\n');
                }
                NbtUtils.m_178019_(p_178029_, p_178027_).append(']');
                break;
            }
            case 11: {
                IntArrayTag $$13 = (IntArrayTag)p_178028_;
                int[] $$14 = $$13.m_128648_();
                int $$15 = 0;
                for (int $$16 : $$14) {
                    $$15 = Math.max($$15, String.format(Locale.ROOT, "%X", $$16).length());
                }
                int $$17 = $$14.length;
                NbtUtils.m_178019_(p_178029_, p_178027_).append("int[").append($$17).append("] {\n");
                if (p_178030_) {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                    for (int $$18 = 0; $$18 < $$14.length; ++$$18) {
                        if ($$18 != 0) {
                            p_178027_.append(',');
                        }
                        if ($$18 % 16 == 0 && $$18 / 16 > 0) {
                            p_178027_.append('\n');
                            if ($$18 < $$14.length) {
                                NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                            }
                        } else if ($$18 != 0) {
                            p_178027_.append(' ');
                        }
                        p_178027_.append(String.format(Locale.ROOT, "0x%0" + $$15 + "X", $$14[$$18]));
                    }
                } else {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
                }
                p_178027_.append('\n');
                NbtUtils.m_178019_(p_178029_, p_178027_).append('}');
                break;
            }
            case 10: {
                CompoundTag $$19 = (CompoundTag)p_178028_;
                ArrayList $$20 = Lists.newArrayList($$19.m_128431_());
                Collections.sort($$20);
                NbtUtils.m_178019_(p_178029_, p_178027_).append('{');
                if (p_178027_.length() - p_178027_.lastIndexOf("\n") > 2 * (p_178029_ + 1)) {
                    p_178027_.append('\n');
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                }
                int $$21 = $$20.stream().mapToInt(String::length).max().orElse(0);
                String $$22 = Strings.repeat((String)" ", (int)$$21);
                for (int $$23 = 0; $$23 < $$20.size(); ++$$23) {
                    if ($$23 != 0) {
                        p_178027_.append(",\n");
                    }
                    String $$24 = (String)$$20.get($$23);
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_).append('\"').append($$24).append('\"').append($$22, 0, $$22.length() - $$24.length()).append(": ");
                    NbtUtils.m_178026_(p_178027_, $$19.m_128423_($$24), p_178029_ + 1, p_178030_);
                }
                if (!$$20.isEmpty()) {
                    p_178027_.append('\n');
                }
                NbtUtils.m_178019_(p_178029_, p_178027_).append('}');
                break;
            }
            case 12: {
                LongArrayTag $$25 = (LongArrayTag)p_178028_;
                long[] $$26 = $$25.m_128851_();
                long $$27 = 0L;
                for (long $$28 : $$26) {
                    $$27 = Math.max($$27, (long)String.format(Locale.ROOT, "%X", $$28).length());
                }
                long $$29 = $$26.length;
                NbtUtils.m_178019_(p_178029_, p_178027_).append("long[").append($$29).append("] {\n");
                if (p_178030_) {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                    for (int $$30 = 0; $$30 < $$26.length; ++$$30) {
                        if ($$30 != 0) {
                            p_178027_.append(',');
                        }
                        if ($$30 % 16 == 0 && $$30 / 16 > 0) {
                            p_178027_.append('\n');
                            if ($$30 < $$26.length) {
                                NbtUtils.m_178019_(p_178029_ + 1, p_178027_);
                            }
                        } else if ($$30 != 0) {
                            p_178027_.append(' ');
                        }
                        p_178027_.append(String.format(Locale.ROOT, "0x%0" + $$27 + "X", $$26[$$30]));
                    }
                } else {
                    NbtUtils.m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
                }
                p_178027_.append('\n');
                NbtUtils.m_178019_(p_178029_, p_178027_).append('}');
                break;
            }
            default: {
                p_178027_.append("<UNKNOWN :(>");
            }
        }
        return p_178027_;
    }

    private static StringBuilder m_178019_(int p_178020_, StringBuilder p_178021_) {
        int $$2 = p_178021_.lastIndexOf("\n") + 1;
        int $$3 = p_178021_.length() - $$2;
        for (int $$4 = 0; $$4 < 2 * p_178020_ - $$3; ++$$4) {
            p_178021_.append(' ');
        }
        return p_178021_;
    }

    public static CompoundTag m_129213_(DataFixer p_129214_, DataFixTypes p_129215_, CompoundTag p_129216_, int p_129217_) {
        return NbtUtils.m_129218_(p_129214_, p_129215_, p_129216_, p_129217_, SharedConstants.m_183709_().getWorldVersion());
    }

    public static CompoundTag m_129218_(DataFixer p_129219_, DataFixTypes p_129220_, CompoundTag p_129221_, int p_129222_, int p_129223_) {
        return (CompoundTag)p_129219_.update(p_129220_.m_14504_(), new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_129221_), p_129222_, p_129223_).getValue();
    }

    public static Component m_178061_(Tag p_178062_) {
        return new TextComponentTagVisitor("", 0).m_178281_(p_178062_);
    }

    public static String m_178063_(CompoundTag p_178064_) {
        return new SnbtPrinterTagVisitor().m_178141_(NbtUtils.m_178067_(p_178064_));
    }

    public static CompoundTag m_178024_(String p_178025_) throws CommandSyntaxException {
        return NbtUtils.m_178071_(TagParser.m_129359_(p_178025_));
    }

    @VisibleForTesting
    static CompoundTag m_178067_(CompoundTag p_178068_) {
        ListTag $$3;
        boolean $$1 = p_178068_.m_128425_("palettes", 9);
        if ($$1) {
            ListTag $$2 = p_178068_.m_128437_("palettes", 9).m_128744_(0);
        } else {
            $$3 = p_178068_.m_128437_("palette", 10);
        }
        ListTag $$4 = $$3.stream().map(CompoundTag.class::cast).map(NbtUtils::m_178075_).map(StringTag::m_129297_).collect(Collectors.toCollection(ListTag::new));
        p_178068_.m_128365_("palette", $$4);
        if ($$1) {
            ListTag $$5 = new ListTag();
            ListTag $$6 = p_178068_.m_128437_("palettes", 9);
            $$6.stream().map(ListTag.class::cast).forEach(p_178049_ -> {
                CompoundTag $$3 = new CompoundTag();
                for (int $$4 = 0; $$4 < p_178049_.size(); ++$$4) {
                    $$3.m_128359_($$4.m_128778_($$4), NbtUtils.m_178075_(p_178049_.m_128728_($$4)));
                }
                $$5.add($$3);
            });
            p_178068_.m_128365_("palettes", $$5);
        }
        if (p_178068_.m_128425_("entities", 10)) {
            ListTag $$7 = p_178068_.m_128437_("entities", 10);
            ListTag $$8 = $$7.stream().map(CompoundTag.class::cast).sorted(Comparator.comparing(p_178080_ -> p_178080_.m_128437_("pos", 6), f_178009_)).collect(Collectors.toCollection(ListTag::new));
            p_178068_.m_128365_("entities", $$8);
        }
        ListTag $$9 = p_178068_.m_128437_("blocks", 10).stream().map(CompoundTag.class::cast).sorted(Comparator.comparing(p_178078_ -> p_178078_.m_128437_("pos", 3), f_178008_)).peek(p_178045_ -> p_178045_.m_128359_("state", $$4.m_128778_(p_178045_.m_128451_("state")))).collect(Collectors.toCollection(ListTag::new));
        p_178068_.m_128365_(f_178007_, $$9);
        p_178068_.m_128473_("blocks");
        return p_178068_;
    }

    @VisibleForTesting
    static CompoundTag m_178071_(CompoundTag p_178072_) {
        ListTag $$1 = p_178072_.m_128437_("palette", 8);
        Map $$2 = (Map)$$1.stream().map(StringTag.class::cast).map(StringTag::m_7916_).collect(ImmutableMap.toImmutableMap(Function.identity(), NbtUtils::m_178053_));
        if (p_178072_.m_128425_("palettes", 9)) {
            p_178072_.m_128365_("palettes", p_178072_.m_128437_("palettes", 10).stream().map(CompoundTag.class::cast).map(p_178033_ -> $$2.keySet().stream().map(p_178033_::m_128461_).map(NbtUtils::m_178053_).collect(Collectors.toCollection(ListTag::new))).collect(Collectors.toCollection(ListTag::new)));
            p_178072_.m_128473_("palette");
        } else {
            p_178072_.m_128365_("palette", $$2.values().stream().collect(Collectors.toCollection(ListTag::new)));
        }
        if (p_178072_.m_128425_(f_178007_, 9)) {
            Object2IntOpenHashMap $$3 = new Object2IntOpenHashMap();
            $$3.defaultReturnValue(-1);
            for (int $$4 = 0; $$4 < $$1.size(); ++$$4) {
                $$3.put((Object)$$1.m_128778_($$4), $$4);
            }
            ListTag $$5 = p_178072_.m_128437_(f_178007_, 10);
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                CompoundTag $$7 = $$5.m_128728_($$6);
                String $$8 = $$7.m_128461_("state");
                int $$9 = $$3.getInt((Object)$$8);
                if ($$9 == -1) {
                    throw new IllegalStateException("Entry " + $$8 + " missing from palette");
                }
                $$7.m_128405_("state", $$9);
            }
            p_178072_.m_128365_("blocks", $$5);
            p_178072_.m_128473_(f_178007_);
        }
        return p_178072_;
    }

    @VisibleForTesting
    static String m_178075_(CompoundTag p_178076_) {
        StringBuilder $$1 = new StringBuilder(p_178076_.m_128461_("Name"));
        if (p_178076_.m_128425_("Properties", 10)) {
            CompoundTag $$2 = p_178076_.m_128469_("Properties");
            String $$3 = $$2.m_128431_().stream().sorted().map(p_178036_ -> p_178036_ + ":" + $$2.m_128423_((String)p_178036_).m_7916_()).collect(Collectors.joining(f_178012_));
            $$1.append('{').append($$3).append('}');
        }
        return $$1.toString();
    }

    @VisibleForTesting
    static CompoundTag m_178053_(String p_178054_) {
        String $$6;
        CompoundTag $$1 = new CompoundTag();
        int $$2 = p_178054_.indexOf(123);
        if ($$2 >= 0) {
            String $$3 = p_178054_.substring(0, $$2);
            CompoundTag $$4 = new CompoundTag();
            if ($$2 + 2 <= p_178054_.length()) {
                String $$5 = p_178054_.substring($$2 + 1, p_178054_.indexOf(125, $$2));
                f_178014_.split((CharSequence)$$5).forEach(p_178040_ -> {
                    List $$3 = f_178015_.splitToList((CharSequence)p_178040_);
                    if ($$3.size() == 2) {
                        $$4.m_128359_((String)$$3.get(0), (String)$$3.get(1));
                    } else {
                        f_129200_.error("Something went wrong parsing: '{}' -- incorrect gamedata!", (Object)p_178054_);
                    }
                });
                $$1.m_128365_("Properties", $$4);
            }
        } else {
            $$6 = p_178054_;
        }
        $$1.m_128359_("Name", $$6);
        return $$1;
    }
}

