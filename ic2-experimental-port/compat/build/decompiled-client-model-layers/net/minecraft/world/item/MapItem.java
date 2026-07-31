/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.LinkedHashMultiset
 *  com.google.common.collect.Multiset
 *  com.google.common.collect.Multisets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapItem
extends ComplexItem {
    public static final int f_151104_ = 128;
    public static final int f_151105_ = 128;
    private static final int f_151106_ = -12173266;
    private static final String f_151107_ = "map";

    public MapItem(Item.Properties p_42847_) {
        super(p_42847_);
    }

    public static ItemStack m_42886_(Level p_42887_, int p_42888_, int p_42889_, byte p_42890_, boolean p_42891_, boolean p_42892_) {
        ItemStack $$6 = new ItemStack(Items.f_42573_);
        MapItem.m_151111_($$6, p_42887_, p_42888_, p_42889_, p_42890_, p_42891_, p_42892_, p_42887_.m_46472_());
        return $$6;
    }

    @Nullable
    public static MapItemSavedData m_151128_(@Nullable Integer p_151129_, Level p_151130_) {
        return p_151129_ == null ? null : p_151130_.m_7489_(MapItem.m_42848_(p_151129_));
    }

    @Nullable
    public static MapItemSavedData m_42853_(ItemStack p_42854_, Level p_42855_) {
        Integer $$2 = MapItem.m_151131_(p_42854_);
        return MapItem.m_151128_($$2, p_42855_);
    }

    @Nullable
    public static Integer m_151131_(ItemStack p_151132_) {
        CompoundTag $$1 = p_151132_.m_41783_();
        return $$1 != null && $$1.m_128425_(f_151107_, 99) ? Integer.valueOf($$1.m_128451_(f_151107_)) : null;
    }

    private static int m_151120_(Level p_151121_, int p_151122_, int p_151123_, int p_151124_, boolean p_151125_, boolean p_151126_, ResourceKey<Level> p_151127_) {
        MapItemSavedData $$7 = MapItemSavedData.m_164780_(p_151122_, p_151123_, (byte)p_151124_, p_151125_, p_151126_, p_151127_);
        int $$8 = p_151121_.m_7354_();
        p_151121_.m_142325_(MapItem.m_42848_($$8), $$7);
        return $$8;
    }

    private static void m_151108_(ItemStack p_151109_, int p_151110_) {
        p_151109_.m_41784_().m_128405_(f_151107_, p_151110_);
    }

    private static void m_151111_(ItemStack p_151112_, Level p_151113_, int p_151114_, int p_151115_, int p_151116_, boolean p_151117_, boolean p_151118_, ResourceKey<Level> p_151119_) {
        int $$8 = MapItem.m_151120_(p_151113_, p_151114_, p_151115_, p_151116_, p_151117_, p_151118_, p_151119_);
        MapItem.m_151108_(p_151112_, $$8);
    }

    public static String m_42848_(int p_42849_) {
        return "map_" + p_42849_;
    }

    public void m_42893_(Level p_42894_, Entity p_42895_, MapItemSavedData p_42896_) {
        if (p_42894_.m_46472_() != p_42896_.f_77887_ || !(p_42895_ instanceof Player)) {
            return;
        }
        int $$3 = 1 << p_42896_.f_77890_;
        int $$4 = p_42896_.f_77885_;
        int $$5 = p_42896_.f_77886_;
        int $$6 = Mth.m_14107_(p_42895_.m_20185_() - (double)$$4) / $$3 + 64;
        int $$7 = Mth.m_14107_(p_42895_.m_20189_() - (double)$$5) / $$3 + 64;
        int $$8 = 128 / $$3;
        if (p_42894_.m_6042_().f_63856_()) {
            $$8 /= 2;
        }
        MapItemSavedData.HoldingPlayer $$9 = p_42896_.m_77916_((Player)p_42895_);
        ++$$9.f_77960_;
        boolean $$10 = false;
        for (int $$11 = $$6 - $$8 + 1; $$11 < $$6 + $$8; ++$$11) {
            if (($$11 & 0xF) != ($$9.f_77960_ & 0xF) && !$$10) continue;
            $$10 = false;
            double $$12 = 0.0;
            for (int $$13 = $$7 - $$8 - 1; $$13 < $$7 + $$8; ++$$13) {
                MaterialColor.Brightness $$44;
                if ($$11 < 0 || $$13 < -1 || $$11 >= 128 || $$13 >= 128) continue;
                int $$14 = $$11 - $$6;
                int $$15 = $$13 - $$7;
                boolean $$16 = $$14 * $$14 + $$15 * $$15 > ($$8 - 2) * ($$8 - 2);
                int $$17 = ($$4 / $$3 + $$11 - 64) * $$3;
                int $$18 = ($$5 / $$3 + $$13 - 64) * $$3;
                LinkedHashMultiset $$19 = LinkedHashMultiset.create();
                LevelChunk $$20 = p_42894_.m_46745_(new BlockPos($$17, 0, $$18));
                if ($$20.m_6430_()) continue;
                ChunkPos $$21 = $$20.m_7697_();
                int $$22 = $$17 & 0xF;
                int $$23 = $$18 & 0xF;
                int $$24 = 0;
                double $$25 = 0.0;
                if (p_42894_.m_6042_().f_63856_()) {
                    int $$26 = $$17 + $$18 * 231871;
                    if ((($$26 = $$26 * $$26 * 31287121 + $$26 * 11) >> 20 & 1) == 0) {
                        $$19.add((Object)Blocks.f_50493_.m_49966_().m_60780_(p_42894_, BlockPos.f_121853_), 10);
                    } else {
                        $$19.add((Object)Blocks.f_50069_.m_49966_().m_60780_(p_42894_, BlockPos.f_121853_), 100);
                    }
                    $$25 = 100.0;
                } else {
                    BlockPos.MutableBlockPos $$27 = new BlockPos.MutableBlockPos();
                    BlockPos.MutableBlockPos $$28 = new BlockPos.MutableBlockPos();
                    for (int $$29 = 0; $$29 < $$3; ++$$29) {
                        for (int $$30 = 0; $$30 < $$3; ++$$30) {
                            BlockState $$35;
                            int $$31 = $$20.m_5885_(Heightmap.Types.WORLD_SURFACE, $$29 + $$22, $$30 + $$23) + 1;
                            if ($$31 > p_42894_.m_141937_() + 1) {
                                BlockState $$32;
                                do {
                                    $$27.m_122178_($$21.m_45604_() + $$29 + $$22, --$$31, $$21.m_45605_() + $$30 + $$23);
                                } while (($$32 = $$20.m_8055_($$27)).m_60780_(p_42894_, $$27) == MaterialColor.f_76398_ && $$31 > p_42894_.m_141937_());
                                if ($$31 > p_42894_.m_141937_() && !$$32.m_60819_().m_76178_()) {
                                    BlockState $$34;
                                    int $$33 = $$31 - 1;
                                    $$28.m_122190_($$27);
                                    do {
                                        $$28.m_142448_($$33--);
                                        $$34 = $$20.m_8055_($$28);
                                        ++$$24;
                                    } while ($$33 > p_42894_.m_141937_() && !$$34.m_60819_().m_76178_());
                                    $$32 = this.m_42900_(p_42894_, $$32, $$27);
                                }
                            } else {
                                $$35 = Blocks.f_50752_.m_49966_();
                            }
                            p_42896_.m_77930_(p_42894_, $$21.m_45604_() + $$29 + $$22, $$21.m_45605_() + $$30 + $$23);
                            $$25 += (double)$$31 / (double)($$3 * $$3);
                            $$19.add((Object)$$35.m_60780_(p_42894_, $$27));
                        }
                    }
                }
                $$24 /= $$3 * $$3;
                MaterialColor $$36 = (MaterialColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)$$19), (Object)MaterialColor.f_76398_);
                if ($$36 == MaterialColor.f_76410_) {
                    double $$37 = (double)$$24 * 0.1 + (double)($$11 + $$13 & 1) * 0.2;
                    if ($$37 < 0.5) {
                        MaterialColor.Brightness $$38 = MaterialColor.Brightness.HIGH;
                    } else if ($$37 > 0.9) {
                        MaterialColor.Brightness $$39 = MaterialColor.Brightness.LOW;
                    } else {
                        MaterialColor.Brightness $$40 = MaterialColor.Brightness.NORMAL;
                    }
                } else {
                    double $$41 = ($$25 - $$12) * 4.0 / (double)($$3 + 4) + ((double)($$11 + $$13 & 1) - 0.5) * 0.4;
                    if ($$41 > 0.6) {
                        MaterialColor.Brightness $$42 = MaterialColor.Brightness.HIGH;
                    } else if ($$41 < -0.6) {
                        MaterialColor.Brightness $$43 = MaterialColor.Brightness.LOW;
                    } else {
                        $$44 = MaterialColor.Brightness.NORMAL;
                    }
                }
                $$12 = $$25;
                if ($$13 < 0 || $$14 * $$14 + $$15 * $$15 >= $$8 * $$8 || $$16 && ($$11 + $$13 & 1) == 0) continue;
                $$10 |= p_42896_.m_164792_($$11, $$13, $$36.m_192925_($$44));
            }
        }
    }

    private BlockState m_42900_(Level p_42901_, BlockState p_42902_, BlockPos p_42903_) {
        FluidState $$3 = p_42902_.m_60819_();
        if (!$$3.m_76178_() && !p_42902_.m_60783_(p_42901_, p_42903_, Direction.UP)) {
            return $$3.m_76188_();
        }
        return p_42902_;
    }

    private static boolean m_212251_(boolean[] p_212252_, int p_212253_, int p_212254_) {
        return p_212252_[p_212254_ * 128 + p_212253_];
    }

    public static void m_42850_(ServerLevel p_42851_, ItemStack p_42852_) {
        MapItemSavedData $$2 = MapItem.m_42853_(p_42852_, p_42851_);
        if ($$2 == null) {
            return;
        }
        if (p_42851_.m_46472_() != $$2.f_77887_) {
            return;
        }
        int $$3 = 1 << $$2.f_77890_;
        int $$4 = $$2.f_77885_;
        int $$5 = $$2.f_77886_;
        boolean[] $$6 = new boolean[16384];
        int $$7 = $$4 / $$3 - 64;
        int $$8 = $$5 / $$3 - 64;
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (int $$10 = 0; $$10 < 128; ++$$10) {
            for (int $$11 = 0; $$11 < 128; ++$$11) {
                Holder<Biome> $$12 = p_42851_.m_204166_($$9.m_122178_(($$7 + $$11) * $$3, 0, ($$8 + $$10) * $$3));
                $$6[$$10 * 128 + $$11] = $$12.m_203656_(BiomeTags.f_215803_);
            }
        }
        for (int $$13 = 1; $$13 < 127; ++$$13) {
            for (int $$14 = 1; $$14 < 127; ++$$14) {
                int $$15 = 0;
                for (int $$16 = -1; $$16 < 2; ++$$16) {
                    for (int $$17 = -1; $$17 < 2; ++$$17) {
                        if ($$16 == 0 && $$17 == 0 || !MapItem.m_212251_($$6, $$13 + $$16, $$14 + $$17)) continue;
                        ++$$15;
                    }
                }
                MaterialColor.Brightness $$18 = MaterialColor.Brightness.LOWEST;
                MaterialColor $$19 = MaterialColor.f_76398_;
                if (MapItem.m_212251_($$6, $$13, $$14)) {
                    $$19 = MaterialColor.f_76413_;
                    if ($$15 > 7 && $$14 % 2 == 0) {
                        switch (($$13 + (int)(Mth.m_14031_((float)$$14 + 0.0f) * 7.0f)) / 8 % 5) {
                            case 0: 
                            case 4: {
                                $$18 = MaterialColor.Brightness.LOW;
                                break;
                            }
                            case 1: 
                            case 3: {
                                $$18 = MaterialColor.Brightness.NORMAL;
                                break;
                            }
                            case 2: {
                                $$18 = MaterialColor.Brightness.HIGH;
                            }
                        }
                    } else if ($$15 > 7) {
                        $$19 = MaterialColor.f_76398_;
                    } else if ($$15 > 5) {
                        $$18 = MaterialColor.Brightness.NORMAL;
                    } else if ($$15 > 3) {
                        $$18 = MaterialColor.Brightness.LOW;
                    } else if ($$15 > 1) {
                        $$18 = MaterialColor.Brightness.LOW;
                    }
                } else if ($$15 > 0) {
                    $$19 = MaterialColor.f_76362_;
                    $$18 = $$15 > 3 ? MaterialColor.Brightness.NORMAL : MaterialColor.Brightness.LOWEST;
                }
                if ($$19 == MaterialColor.f_76398_) continue;
                $$2.m_164803_($$13, $$14, $$19.m_192925_($$18));
            }
        }
    }

    @Override
    public void m_6883_(ItemStack p_42870_, Level p_42871_, Entity p_42872_, int p_42873_, boolean p_42874_) {
        if (p_42871_.f_46443_) {
            return;
        }
        MapItemSavedData $$5 = MapItem.m_42853_(p_42870_, p_42871_);
        if ($$5 == null) {
            return;
        }
        if (p_42872_ instanceof Player) {
            Player $$6 = (Player)p_42872_;
            $$5.m_77918_($$6, p_42870_);
        }
        if (!$$5.f_77892_ && (p_42874_ || p_42872_ instanceof Player && ((Player)p_42872_).m_21206_() == p_42870_)) {
            this.m_42893_(p_42871_, p_42872_, $$5);
        }
    }

    @Override
    @Nullable
    public Packet<?> m_7233_(ItemStack p_42876_, Level p_42877_, Player p_42878_) {
        Integer $$3 = MapItem.m_151131_(p_42876_);
        MapItemSavedData $$4 = MapItem.m_151128_($$3, p_42877_);
        if ($$4 != null) {
            return $$4.m_164796_($$3, p_42878_);
        }
        return null;
    }

    @Override
    public void m_7836_(ItemStack p_42913_, Level p_42914_, Player p_42915_) {
        CompoundTag $$3 = p_42913_.m_41783_();
        if ($$3 != null && $$3.m_128425_("map_scale_direction", 99)) {
            MapItem.m_42856_(p_42913_, p_42914_, $$3.m_128451_("map_scale_direction"));
            $$3.m_128473_("map_scale_direction");
        } else if ($$3 != null && $$3.m_128425_("map_to_lock", 1) && $$3.m_128471_("map_to_lock")) {
            MapItem.m_42897_(p_42914_, p_42913_);
            $$3.m_128473_("map_to_lock");
        }
    }

    private static void m_42856_(ItemStack p_42857_, Level p_42858_, int p_42859_) {
        MapItemSavedData $$3 = MapItem.m_42853_(p_42857_, p_42858_);
        if ($$3 != null) {
            int $$4 = p_42858_.m_7354_();
            p_42858_.m_142325_(MapItem.m_42848_($$4), $$3.m_164787_(p_42859_));
            MapItem.m_151108_(p_42857_, $$4);
        }
    }

    public static void m_42897_(Level p_42898_, ItemStack p_42899_) {
        MapItemSavedData $$2 = MapItem.m_42853_(p_42899_, p_42898_);
        if ($$2 != null) {
            int $$3 = p_42898_.m_7354_();
            String $$4 = MapItem.m_42848_($$3);
            MapItemSavedData $$5 = $$2.m_164775_();
            p_42898_.m_142325_($$4, $$5);
            MapItem.m_151108_(p_42899_, $$3);
        }
    }

    @Override
    public void m_7373_(ItemStack p_42880_, @Nullable Level p_42881_, List<Component> p_42882_, TooltipFlag p_42883_) {
        MapItemSavedData $$5;
        Integer $$4 = MapItem.m_151131_(p_42880_);
        MapItemSavedData mapItemSavedData = $$5 = p_42881_ == null ? null : MapItem.m_151128_($$4, p_42881_);
        if ($$5 != null && $$5.f_77892_) {
            p_42882_.add(Component.m_237110_("filled_map.locked", $$4).m_130940_(ChatFormatting.GRAY));
        }
        if (p_42883_.m_7050_()) {
            if ($$5 != null) {
                p_42882_.add(Component.m_237110_("filled_map.id", $$4).m_130940_(ChatFormatting.GRAY));
                p_42882_.add(Component.m_237110_("filled_map.scale", 1 << $$5.f_77890_).m_130940_(ChatFormatting.GRAY));
                p_42882_.add(Component.m_237110_("filled_map.level", $$5.f_77890_, 4).m_130940_(ChatFormatting.GRAY));
            } else {
                p_42882_.add(Component.m_237115_("filled_map.unknown").m_130940_(ChatFormatting.GRAY));
            }
        }
    }

    public static int m_42918_(ItemStack p_42919_) {
        CompoundTag $$1 = p_42919_.m_41737_("display");
        if ($$1 != null && $$1.m_128425_("MapColor", 99)) {
            int $$2 = $$1.m_128451_("MapColor");
            return 0xFF000000 | $$2 & 0xFFFFFF;
        }
        return -12173266;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_42885_) {
        BlockState $$1 = p_42885_.m_43725_().m_8055_(p_42885_.m_8083_());
        if ($$1.m_204336_(BlockTags.f_13028_)) {
            MapItemSavedData $$2;
            if (!p_42885_.m_43725_().f_46443_ && ($$2 = MapItem.m_42853_(p_42885_.m_43722_(), p_42885_.m_43725_())) != null && !$$2.m_77934_(p_42885_.m_43725_(), p_42885_.m_8083_())) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.m_19078_(p_42885_.m_43725_().f_46443_);
        }
        return super.m_6225_(p_42885_);
    }
}

