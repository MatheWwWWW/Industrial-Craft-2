/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class StructureTemplate {
    public static final String f_163789_ = "palette";
    public static final String f_163790_ = "palettes";
    public static final String f_163791_ = "entities";
    public static final String f_163792_ = "blocks";
    public static final String f_163793_ = "pos";
    public static final String f_163794_ = "state";
    public static final String f_163795_ = "nbt";
    public static final String f_163796_ = "pos";
    public static final String f_163797_ = "blockPos";
    public static final String f_163798_ = "nbt";
    public static final String f_163799_ = "size";
    static final int f_163800_ = 16;
    private final List<Palette> f_74482_ = Lists.newArrayList();
    private final List<StructureEntityInfo> f_74483_ = Lists.newArrayList();
    private Vec3i f_74484_ = Vec3i.f_123288_;
    private String f_74485_ = "?";

    public Vec3i m_163801_() {
        return this.f_74484_;
    }

    public void m_74612_(String p_74613_) {
        this.f_74485_ = p_74613_;
    }

    public String m_74627_() {
        return this.f_74485_;
    }

    public void m_163802_(Level p_163803_, BlockPos p_163804_, Vec3i p_163805_, boolean p_163806_, @Nullable Block p_163807_) {
        if (p_163805_.m_123341_() < 1 || p_163805_.m_123342_() < 1 || p_163805_.m_123343_() < 1) {
            return;
        }
        BlockPos $$5 = p_163804_.m_121955_(p_163805_).m_7918_(-1, -1, -1);
        ArrayList $$6 = Lists.newArrayList();
        ArrayList $$7 = Lists.newArrayList();
        ArrayList $$8 = Lists.newArrayList();
        BlockPos $$9 = new BlockPos(Math.min(p_163804_.m_123341_(), $$5.m_123341_()), Math.min(p_163804_.m_123342_(), $$5.m_123342_()), Math.min(p_163804_.m_123343_(), $$5.m_123343_()));
        BlockPos $$10 = new BlockPos(Math.max(p_163804_.m_123341_(), $$5.m_123341_()), Math.max(p_163804_.m_123342_(), $$5.m_123342_()), Math.max(p_163804_.m_123343_(), $$5.m_123343_()));
        this.f_74484_ = p_163805_;
        for (BlockPos $$11 : BlockPos.m_121940_($$9, $$10)) {
            StructureBlockInfo $$16;
            BlockPos $$12 = $$11.m_121996_($$9);
            BlockState $$13 = p_163803_.m_8055_($$11);
            if (p_163807_ != null && $$13.m_60713_(p_163807_)) continue;
            BlockEntity $$14 = p_163803_.m_7702_($$11);
            if ($$14 != null) {
                StructureBlockInfo $$15 = new StructureBlockInfo($$12, $$13, $$14.m_187481_());
            } else {
                $$16 = new StructureBlockInfo($$12, $$13, null);
            }
            StructureTemplate.m_74573_($$16, $$6, $$7, $$8);
        }
        List<StructureBlockInfo> $$17 = StructureTemplate.m_74614_($$6, $$7, $$8);
        this.f_74482_.clear();
        this.f_74482_.add(new Palette($$17));
        if (p_163806_) {
            this.m_74500_(p_163803_, $$9, $$10.m_7918_(1, 1, 1));
        } else {
            this.f_74483_.clear();
        }
    }

    private static void m_74573_(StructureBlockInfo p_74574_, List<StructureBlockInfo> p_74575_, List<StructureBlockInfo> p_74576_, List<StructureBlockInfo> p_74577_) {
        if (p_74574_.f_74677_ != null) {
            p_74576_.add(p_74574_);
        } else if (!p_74574_.f_74676_.m_60734_().m_49967_() && p_74574_.f_74676_.m_60838_(EmptyBlockGetter.INSTANCE, BlockPos.f_121853_)) {
            p_74575_.add(p_74574_);
        } else {
            p_74577_.add(p_74574_);
        }
    }

    private static List<StructureBlockInfo> m_74614_(List<StructureBlockInfo> p_74615_, List<StructureBlockInfo> p_74616_, List<StructureBlockInfo> p_74617_) {
        Comparator<StructureBlockInfo> $$3 = Comparator.comparingInt(p_74641_ -> p_74641_.f_74675_.m_123342_()).thenComparingInt(p_74637_ -> p_74637_.f_74675_.m_123341_()).thenComparingInt(p_74572_ -> p_74572_.f_74675_.m_123343_());
        p_74615_.sort($$3);
        p_74617_.sort($$3);
        p_74616_.sort($$3);
        ArrayList $$4 = Lists.newArrayList();
        $$4.addAll(p_74615_);
        $$4.addAll(p_74617_);
        $$4.addAll(p_74616_);
        return $$4;
    }

    private void m_74500_(Level p_74501_, BlockPos p_74502_, BlockPos p_74503_) {
        List<Entity> $$3 = p_74501_.m_6443_(Entity.class, new AABB(p_74502_, p_74503_), p_74499_ -> !(p_74499_ instanceof Player));
        this.f_74483_.clear();
        for (Entity $$4 : $$3) {
            BlockPos $$8;
            Vec3 $$5 = new Vec3($$4.m_20185_() - (double)p_74502_.m_123341_(), $$4.m_20186_() - (double)p_74502_.m_123342_(), $$4.m_20189_() - (double)p_74502_.m_123343_());
            CompoundTag $$6 = new CompoundTag();
            $$4.m_20223_($$6);
            if ($$4 instanceof Painting) {
                BlockPos $$7 = ((Painting)$$4).m_31748_().m_121996_(p_74502_);
            } else {
                $$8 = new BlockPos($$5);
            }
            this.f_74483_.add(new StructureEntityInfo($$5, $$8, $$6.m_6426_()));
        }
    }

    public List<StructureBlockInfo> m_74603_(BlockPos p_74604_, StructurePlaceSettings p_74605_, Block p_74606_) {
        return this.m_230335_(p_74604_, p_74605_, p_74606_, true);
    }

    public ObjectArrayList<StructureBlockInfo> m_230335_(BlockPos p_230336_, StructurePlaceSettings p_230337_, Block p_230338_, boolean p_230339_) {
        ObjectArrayList $$4 = new ObjectArrayList();
        BoundingBox $$5 = p_230337_.m_74409_();
        if (this.f_74482_.isEmpty()) {
            return $$4;
        }
        for (StructureBlockInfo $$6 : p_230337_.m_74387_(this.f_74482_, p_230336_).m_74653_(p_230338_)) {
            BlockPos $$7;
            BlockPos blockPos = $$7 = p_230339_ ? StructureTemplate.m_74563_(p_230337_, $$6.f_74675_).m_121955_(p_230336_) : $$6.f_74675_;
            if ($$5 != null && !$$5.m_71051_($$7)) continue;
            $$4.add((Object)new StructureBlockInfo($$7, $$6.f_74676_.m_60717_(p_230337_.m_74404_()), $$6.f_74677_));
        }
        return $$4;
    }

    public BlockPos m_74566_(StructurePlaceSettings p_74567_, BlockPos p_74568_, StructurePlaceSettings p_74569_, BlockPos p_74570_) {
        BlockPos $$4 = StructureTemplate.m_74563_(p_74567_, p_74568_);
        BlockPos $$5 = StructureTemplate.m_74563_(p_74569_, p_74570_);
        return $$4.m_121996_($$5);
    }

    public static BlockPos m_74563_(StructurePlaceSettings p_74564_, BlockPos p_74565_) {
        return StructureTemplate.m_74593_(p_74565_, p_74564_.m_74401_(), p_74564_.m_74404_(), p_74564_.m_74407_());
    }

    public boolean m_230328_(ServerLevelAccessor p_230329_, BlockPos p_230330_, BlockPos p_230331_, StructurePlaceSettings p_230332_, RandomSource p_230333_, int p_230334_) {
        if (this.f_74482_.isEmpty()) {
            return false;
        }
        List<StructureBlockInfo> $$6 = p_230332_.m_74387_(this.f_74482_, p_230330_).m_74652_();
        if ($$6.isEmpty() && (p_230332_.m_74408_() || this.f_74483_.isEmpty()) || this.f_74484_.m_123341_() < 1 || this.f_74484_.m_123342_() < 1 || this.f_74484_.m_123343_() < 1) {
            return false;
        }
        BoundingBox $$7 = p_230332_.m_74409_();
        ArrayList $$8 = Lists.newArrayListWithCapacity((int)(p_230332_.m_74413_() ? $$6.size() : 0));
        ArrayList $$9 = Lists.newArrayListWithCapacity((int)(p_230332_.m_74413_() ? $$6.size() : 0));
        ArrayList $$10 = Lists.newArrayListWithCapacity((int)$$6.size());
        int $$11 = Integer.MAX_VALUE;
        int $$12 = Integer.MAX_VALUE;
        int $$13 = Integer.MAX_VALUE;
        int $$14 = Integer.MIN_VALUE;
        int $$15 = Integer.MIN_VALUE;
        int $$16 = Integer.MIN_VALUE;
        List<StructureBlockInfo> $$17 = StructureTemplate.m_74517_(p_230329_, p_230330_, p_230331_, p_230332_, $$6);
        for (StructureBlockInfo $$18 : $$17) {
            BlockEntity $$23;
            BlockPos $$19 = $$18.f_74675_;
            if ($$7 != null && !$$7.m_71051_($$19)) continue;
            FluidState $$20 = p_230332_.m_74413_() ? p_230329_.m_6425_($$19) : null;
            BlockState $$21 = $$18.f_74676_.m_60715_(p_230332_.m_74401_()).m_60717_(p_230332_.m_74404_());
            if ($$18.f_74677_ != null) {
                BlockEntity $$22 = p_230329_.m_7702_($$19);
                Clearable.m_18908_($$22);
                p_230329_.m_7731_($$19, Blocks.f_50375_.m_49966_(), 20);
            }
            if (!p_230329_.m_7731_($$19, $$21, p_230334_)) continue;
            $$11 = Math.min($$11, $$19.m_123341_());
            $$12 = Math.min($$12, $$19.m_123342_());
            $$13 = Math.min($$13, $$19.m_123343_());
            $$14 = Math.max($$14, $$19.m_123341_());
            $$15 = Math.max($$15, $$19.m_123342_());
            $$16 = Math.max($$16, $$19.m_123343_());
            $$10.add(Pair.of((Object)$$19, (Object)$$18.f_74677_));
            if ($$18.f_74677_ != null && ($$23 = p_230329_.m_7702_($$19)) != null) {
                if ($$23 instanceof RandomizableContainerBlockEntity) {
                    $$18.f_74677_.m_128356_("LootTableSeed", p_230333_.m_188505_());
                }
                $$23.m_142466_($$18.f_74677_);
            }
            if ($$20 == null) continue;
            if ($$21.m_60819_().m_76170_()) {
                $$9.add($$19);
                continue;
            }
            if (!($$21.m_60734_() instanceof LiquidBlockContainer)) continue;
            ((LiquidBlockContainer)((Object)$$21.m_60734_())).m_7361_(p_230329_, $$19, $$21, $$20);
            if ($$20.m_76170_()) continue;
            $$8.add($$19);
        }
        boolean $$24 = true;
        Direction[] $$25 = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        while ($$24 && !$$8.isEmpty()) {
            $$24 = false;
            Iterator $$26 = $$8.iterator();
            while ($$26.hasNext()) {
                BlockState $$32;
                Object $$33;
                BlockPos $$27 = (BlockPos)$$26.next();
                FluidState $$28 = p_230329_.m_6425_($$27);
                for (int $$29 = 0; $$29 < $$25.length && !$$28.m_76170_(); ++$$29) {
                    BlockPos $$30 = $$27.m_121945_($$25[$$29]);
                    FluidState $$31 = p_230329_.m_6425_($$30);
                    if (!$$31.m_76170_() || $$9.contains($$30)) continue;
                    $$28 = $$31;
                }
                if (!$$28.m_76170_() || !(($$33 = ($$32 = p_230329_.m_8055_($$27)).m_60734_()) instanceof LiquidBlockContainer)) continue;
                ((LiquidBlockContainer)$$33).m_7361_(p_230329_, $$27, $$32, $$28);
                $$24 = true;
                $$26.remove();
            }
        }
        if ($$11 <= $$14) {
            if (!p_230332_.m_74410_()) {
                BitSetDiscreteVoxelShape $$34 = new BitSetDiscreteVoxelShape($$14 - $$11 + 1, $$15 - $$12 + 1, $$16 - $$13 + 1);
                int $$35 = $$11;
                int $$36 = $$12;
                int $$37 = $$13;
                for (Pair $$38 : $$10) {
                    BlockPos $$39 = (BlockPos)$$38.getFirst();
                    ((DiscreteVoxelShape)$$34).m_142703_($$39.m_123341_() - $$35, $$39.m_123342_() - $$36, $$39.m_123343_() - $$37);
                }
                StructureTemplate.m_74510_(p_230329_, p_230334_, $$34, $$35, $$36, $$37);
            }
            for (Pair $$40 : $$10) {
                BlockEntity $$44;
                BlockPos $$41 = (BlockPos)$$40.getFirst();
                if (!p_230332_.m_74410_()) {
                    BlockState $$43;
                    BlockState $$42 = p_230329_.m_8055_($$41);
                    if ($$42 != ($$43 = Block.m_49931_($$42, p_230329_, $$41))) {
                        p_230329_.m_7731_($$41, $$43, p_230334_ & 0xFFFFFFFE | 0x10);
                    }
                    p_230329_.m_6289_($$41, $$43.m_60734_());
                }
                if ($$40.getSecond() == null || ($$44 = p_230329_.m_7702_($$41)) == null) continue;
                $$44.m_6596_();
            }
        }
        if (!p_230332_.m_74408_()) {
            this.m_74523_(p_230329_, p_230330_, p_230332_.m_74401_(), p_230332_.m_74404_(), p_230332_.m_74407_(), $$7, p_230332_.m_74414_());
        }
        return true;
    }

    public static void m_74510_(LevelAccessor p_74511_, int p_74512_, DiscreteVoxelShape p_74513_, int p_74514_, int p_74515_, int p_74516_) {
        p_74513_.m_82810_((p_74494_, p_74495_, p_74496_, p_74497_) -> {
            BlockState $$14;
            BlockState $$12;
            BlockState $$13;
            BlockPos $$9 = new BlockPos(p_74514_ + p_74495_, p_74515_ + p_74496_, p_74516_ + p_74497_);
            BlockPos $$10 = $$9.m_121945_(p_74494_);
            BlockState $$11 = p_74511_.m_8055_($$9);
            if ($$11 != ($$13 = $$11.m_60728_(p_74494_, $$12 = p_74511_.m_8055_($$10), p_74511_, $$9, $$10))) {
                p_74511_.m_7731_($$9, $$13, p_74512_ & 0xFFFFFFFE);
            }
            if ($$12 != ($$14 = $$12.m_60728_(p_74494_.m_122424_(), $$13, p_74511_, $$10, $$9))) {
                p_74511_.m_7731_($$10, $$14, p_74512_ & 0xFFFFFFFE);
            }
        });
    }

    public static List<StructureBlockInfo> m_74517_(LevelAccessor p_74518_, BlockPos p_74519_, BlockPos p_74520_, StructurePlaceSettings p_74521_, List<StructureBlockInfo> p_74522_) {
        ArrayList $$5 = Lists.newArrayList();
        for (StructureBlockInfo $$6 : p_74522_) {
            BlockPos $$7 = StructureTemplate.m_74563_(p_74521_, $$6.f_74675_).m_121955_(p_74519_);
            StructureBlockInfo $$8 = new StructureBlockInfo($$7, $$6.f_74676_, $$6.f_74677_ != null ? $$6.f_74677_.m_6426_() : null);
            Iterator<StructureProcessor> $$9 = p_74521_.m_74411_().iterator();
            while ($$8 != null && $$9.hasNext()) {
                $$8 = $$9.next().m_7382_(p_74518_, p_74519_, p_74520_, $$6, $$8, p_74521_);
            }
            if ($$8 == null) continue;
            $$5.add($$8);
        }
        return $$5;
    }

    private void m_74523_(ServerLevelAccessor p_74524_, BlockPos p_74525_, Mirror p_74526_, Rotation p_74527_, BlockPos p_74528_, @Nullable BoundingBox p_74529_, boolean p_74530_) {
        for (StructureEntityInfo $$7 : this.f_74483_) {
            BlockPos $$8 = StructureTemplate.m_74593_($$7.f_74684_, p_74526_, p_74527_, p_74528_).m_121955_(p_74525_);
            if (p_74529_ != null && !p_74529_.m_71051_($$8)) continue;
            CompoundTag $$9 = $$7.f_74685_.m_6426_();
            Vec3 $$10 = StructureTemplate.m_74578_($$7.f_74683_, p_74526_, p_74527_, p_74528_);
            Vec3 $$11 = $$10.m_82520_(p_74525_.m_123341_(), p_74525_.m_123342_(), p_74525_.m_123343_());
            ListTag $$12 = new ListTag();
            $$12.add(DoubleTag.m_128500_($$11.f_82479_));
            $$12.add(DoubleTag.m_128500_($$11.f_82480_));
            $$12.add(DoubleTag.m_128500_($$11.f_82481_));
            $$9.m_128365_("Pos", $$12);
            $$9.m_128473_("UUID");
            StructureTemplate.m_74543_(p_74524_, $$9).ifPresent(p_205061_ -> {
                float $$7 = p_205061_.m_7890_(p_74527_);
                p_205061_.m_7678_(p_205057_.f_82479_, p_205057_.f_82480_, p_205057_.f_82481_, $$7 += p_205061_.m_6961_(p_74526_) - p_205061_.m_146908_(), p_205061_.m_146909_());
                if (p_74530_ && p_205061_ instanceof Mob) {
                    ((Mob)p_205061_).m_6518_(p_74524_, p_74524_.m_6436_(new BlockPos($$11)), MobSpawnType.STRUCTURE, null, $$9);
                }
                p_74524_.m_47205_((Entity)p_205061_);
            });
        }
    }

    private static Optional<Entity> m_74543_(ServerLevelAccessor p_74544_, CompoundTag p_74545_) {
        try {
            return EntityType.m_20642_(p_74545_, p_74544_.m_6018_());
        }
        catch (Exception $$2) {
            return Optional.empty();
        }
    }

    public Vec3i m_163808_(Rotation p_163809_) {
        switch (p_163809_) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                return new Vec3i(this.f_74484_.m_123343_(), this.f_74484_.m_123342_(), this.f_74484_.m_123341_());
            }
        }
        return this.f_74484_;
    }

    public static BlockPos m_74593_(BlockPos p_74594_, Mirror p_74595_, Rotation p_74596_, BlockPos p_74597_) {
        int $$4 = p_74594_.m_123341_();
        int $$5 = p_74594_.m_123342_();
        int $$6 = p_74594_.m_123343_();
        boolean $$7 = true;
        switch (p_74595_) {
            case LEFT_RIGHT: {
                $$6 = -$$6;
                break;
            }
            case FRONT_BACK: {
                $$4 = -$$4;
                break;
            }
            default: {
                $$7 = false;
            }
        }
        int $$8 = p_74597_.m_123341_();
        int $$9 = p_74597_.m_123343_();
        switch (p_74596_) {
            case CLOCKWISE_180: {
                return new BlockPos($$8 + $$8 - $$4, $$5, $$9 + $$9 - $$6);
            }
            case COUNTERCLOCKWISE_90: {
                return new BlockPos($$8 - $$9 + $$6, $$5, $$8 + $$9 - $$4);
            }
            case CLOCKWISE_90: {
                return new BlockPos($$8 + $$9 - $$6, $$5, $$9 - $$8 + $$4);
            }
        }
        return $$7 ? new BlockPos($$4, $$5, $$6) : p_74594_;
    }

    public static Vec3 m_74578_(Vec3 p_74579_, Mirror p_74580_, Rotation p_74581_, BlockPos p_74582_) {
        double $$4 = p_74579_.f_82479_;
        double $$5 = p_74579_.f_82480_;
        double $$6 = p_74579_.f_82481_;
        boolean $$7 = true;
        switch (p_74580_) {
            case LEFT_RIGHT: {
                $$6 = 1.0 - $$6;
                break;
            }
            case FRONT_BACK: {
                $$4 = 1.0 - $$4;
                break;
            }
            default: {
                $$7 = false;
            }
        }
        int $$8 = p_74582_.m_123341_();
        int $$9 = p_74582_.m_123343_();
        switch (p_74581_) {
            case CLOCKWISE_180: {
                return new Vec3((double)($$8 + $$8 + 1) - $$4, $$5, (double)($$9 + $$9 + 1) - $$6);
            }
            case COUNTERCLOCKWISE_90: {
                return new Vec3((double)($$8 - $$9) + $$6, $$5, (double)($$8 + $$9 + 1) - $$4);
            }
            case CLOCKWISE_90: {
                return new Vec3((double)($$8 + $$9 + 1) - $$6, $$5, (double)($$9 - $$8) + $$4);
            }
        }
        return $$7 ? new Vec3($$4, $$5, $$6) : p_74579_;
    }

    public BlockPos m_74583_(BlockPos p_74584_, Mirror p_74585_, Rotation p_74586_) {
        return StructureTemplate.m_74587_(p_74584_, p_74585_, p_74586_, this.m_163801_().m_123341_(), this.m_163801_().m_123343_());
    }

    public static BlockPos m_74587_(BlockPos p_74588_, Mirror p_74589_, Rotation p_74590_, int p_74591_, int p_74592_) {
        int $$5 = p_74589_ == Mirror.FRONT_BACK ? --p_74591_ : 0;
        int $$6 = p_74589_ == Mirror.LEFT_RIGHT ? --p_74592_ : 0;
        BlockPos $$7 = p_74588_;
        switch (p_74590_) {
            case NONE: {
                $$7 = p_74588_.m_7918_($$5, 0, $$6);
                break;
            }
            case CLOCKWISE_90: {
                $$7 = p_74588_.m_7918_(p_74592_ - $$6, 0, $$5);
                break;
            }
            case CLOCKWISE_180: {
                $$7 = p_74588_.m_7918_(p_74591_ - $$5, 0, p_74592_ - $$6);
                break;
            }
            case COUNTERCLOCKWISE_90: {
                $$7 = p_74588_.m_7918_($$6, 0, p_74591_ - $$5);
            }
        }
        return $$7;
    }

    public BoundingBox m_74633_(StructurePlaceSettings p_74634_, BlockPos p_74635_) {
        return this.m_74598_(p_74635_, p_74634_.m_74404_(), p_74634_.m_74407_(), p_74634_.m_74401_());
    }

    public BoundingBox m_74598_(BlockPos p_74599_, Rotation p_74600_, BlockPos p_74601_, Mirror p_74602_) {
        return StructureTemplate.m_163810_(p_74599_, p_74600_, p_74601_, p_74602_, this.f_74484_);
    }

    @VisibleForTesting
    protected static BoundingBox m_163810_(BlockPos p_163811_, Rotation p_163812_, BlockPos p_163813_, Mirror p_163814_, Vec3i p_163815_) {
        Vec3i $$5 = p_163815_.m_7918_(-1, -1, -1);
        BlockPos $$6 = StructureTemplate.m_74593_(BlockPos.f_121853_, p_163814_, p_163812_, p_163813_);
        BlockPos $$7 = StructureTemplate.m_74593_(BlockPos.f_121853_.m_121955_($$5), p_163814_, p_163812_, p_163813_);
        return BoundingBox.m_162375_($$6, $$7).m_162373_(p_163811_);
    }

    public CompoundTag m_74618_(CompoundTag p_74619_) {
        if (this.f_74482_.isEmpty()) {
            p_74619_.m_128365_(f_163792_, new ListTag());
            p_74619_.m_128365_(f_163789_, new ListTag());
        } else {
            ArrayList $$1 = Lists.newArrayList();
            SimplePalette $$2 = new SimplePalette();
            $$1.add($$2);
            for (int $$3 = 1; $$3 < this.f_74482_.size(); ++$$3) {
                $$1.add(new SimplePalette());
            }
            ListTag $$4 = new ListTag();
            List<StructureBlockInfo> $$5 = this.f_74482_.get(0).m_74652_();
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                StructureBlockInfo $$7 = $$5.get($$6);
                CompoundTag $$8 = new CompoundTag();
                $$8.m_128365_("pos", this.m_74625_($$7.f_74675_.m_123341_(), $$7.f_74675_.m_123342_(), $$7.f_74675_.m_123343_()));
                int $$9 = $$2.m_74669_($$7.f_74676_);
                $$8.m_128405_(f_163794_, $$9);
                if ($$7.f_74677_ != null) {
                    $$8.m_128365_("nbt", $$7.f_74677_);
                }
                $$4.add($$8);
                for (int $$10 = 1; $$10 < this.f_74482_.size(); ++$$10) {
                    SimplePalette $$11 = (SimplePalette)$$1.get($$10);
                    $$11.m_74671_(this.f_74482_.get((int)$$10).m_74652_().get((int)$$6).f_74676_, $$9);
                }
            }
            p_74619_.m_128365_(f_163792_, $$4);
            if ($$1.size() == 1) {
                ListTag $$12 = new ListTag();
                for (BlockState $$13 : $$2) {
                    $$12.add(NbtUtils.m_129202_($$13));
                }
                p_74619_.m_128365_(f_163789_, $$12);
            } else {
                ListTag $$14 = new ListTag();
                for (SimplePalette $$15 : $$1) {
                    ListTag $$16 = new ListTag();
                    for (BlockState $$17 : $$15) {
                        $$16.add(NbtUtils.m_129202_($$17));
                    }
                    $$14.add($$16);
                }
                p_74619_.m_128365_(f_163790_, $$14);
            }
        }
        ListTag $$18 = new ListTag();
        for (StructureEntityInfo $$19 : this.f_74483_) {
            CompoundTag $$20 = new CompoundTag();
            $$20.m_128365_("pos", this.m_74623_($$19.f_74683_.f_82479_, $$19.f_74683_.f_82480_, $$19.f_74683_.f_82481_));
            $$20.m_128365_(f_163797_, this.m_74625_($$19.f_74684_.m_123341_(), $$19.f_74684_.m_123342_(), $$19.f_74684_.m_123343_()));
            if ($$19.f_74685_ != null) {
                $$20.m_128365_("nbt", $$19.f_74685_);
            }
            $$18.add($$20);
        }
        p_74619_.m_128365_(f_163791_, $$18);
        p_74619_.m_128365_(f_163799_, this.m_74625_(this.f_74484_.m_123341_(), this.f_74484_.m_123342_(), this.f_74484_.m_123343_()));
        p_74619_.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        return p_74619_;
    }

    public void m_74638_(CompoundTag p_74639_) {
        this.f_74482_.clear();
        this.f_74483_.clear();
        ListTag $$1 = p_74639_.m_128437_(f_163799_, 3);
        this.f_74484_ = new Vec3i($$1.m_128763_(0), $$1.m_128763_(1), $$1.m_128763_(2));
        ListTag $$2 = p_74639_.m_128437_(f_163792_, 10);
        if (p_74639_.m_128425_(f_163790_, 9)) {
            ListTag $$3 = p_74639_.m_128437_(f_163790_, 9);
            for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                this.m_74620_($$3.m_128744_($$4), $$2);
            }
        } else {
            this.m_74620_(p_74639_.m_128437_(f_163789_, 10), $$2);
        }
        ListTag $$5 = p_74639_.m_128437_(f_163791_, 10);
        for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
            CompoundTag $$7 = $$5.m_128728_($$6);
            ListTag $$8 = $$7.m_128437_("pos", 6);
            Vec3 $$9 = new Vec3($$8.m_128772_(0), $$8.m_128772_(1), $$8.m_128772_(2));
            ListTag $$10 = $$7.m_128437_(f_163797_, 3);
            BlockPos $$11 = new BlockPos($$10.m_128763_(0), $$10.m_128763_(1), $$10.m_128763_(2));
            if (!$$7.m_128441_("nbt")) continue;
            CompoundTag $$12 = $$7.m_128469_("nbt");
            this.f_74483_.add(new StructureEntityInfo($$9, $$11, $$12));
        }
    }

    private void m_74620_(ListTag p_74621_, ListTag p_74622_) {
        SimplePalette $$2 = new SimplePalette();
        for (int $$3 = 0; $$3 < p_74621_.size(); ++$$3) {
            $$2.m_74671_(NbtUtils.m_129241_(p_74621_.m_128728_($$3)), $$3);
        }
        ArrayList $$4 = Lists.newArrayList();
        ArrayList $$5 = Lists.newArrayList();
        ArrayList $$6 = Lists.newArrayList();
        for (int $$7 = 0; $$7 < p_74622_.size(); ++$$7) {
            CompoundTag $$13;
            CompoundTag $$8 = p_74622_.m_128728_($$7);
            ListTag $$9 = $$8.m_128437_("pos", 3);
            BlockPos $$10 = new BlockPos($$9.m_128763_(0), $$9.m_128763_(1), $$9.m_128763_(2));
            BlockState $$11 = $$2.m_74667_($$8.m_128451_(f_163794_));
            if ($$8.m_128441_("nbt")) {
                CompoundTag $$12 = $$8.m_128469_("nbt");
            } else {
                $$13 = null;
            }
            StructureBlockInfo $$14 = new StructureBlockInfo($$10, $$11, $$13);
            StructureTemplate.m_74573_($$14, $$4, $$5, $$6);
        }
        List<StructureBlockInfo> $$15 = StructureTemplate.m_74614_($$4, $$5, $$6);
        this.f_74482_.add(new Palette($$15));
    }

    private ListTag m_74625_(int ... p_74626_) {
        ListTag $$1 = new ListTag();
        for (int $$2 : p_74626_) {
            $$1.add(IntTag.m_128679_($$2));
        }
        return $$1;
    }

    private ListTag m_74623_(double ... p_74624_) {
        ListTag $$1 = new ListTag();
        for (double $$2 : p_74624_) {
            $$1.add(DoubleTag.m_128500_($$2));
        }
        return $$1;
    }

    public static class StructureBlockInfo {
        public final BlockPos f_74675_;
        public final BlockState f_74676_;
        public final CompoundTag f_74677_;

        public StructureBlockInfo(BlockPos p_74679_, BlockState p_74680_, @Nullable CompoundTag p_74681_) {
            this.f_74675_ = p_74679_;
            this.f_74676_ = p_74680_;
            this.f_74677_ = p_74681_;
        }

        public String toString() {
            return String.format(Locale.ROOT, "<StructureBlockInfo | %s | %s | %s>", this.f_74675_, this.f_74676_, this.f_74677_);
        }
    }

    public static final class Palette {
        private final List<StructureBlockInfo> f_74645_;
        private final Map<Block, List<StructureBlockInfo>> f_74646_ = Maps.newHashMap();

        Palette(List<StructureBlockInfo> p_74648_) {
            this.f_74645_ = p_74648_;
        }

        public List<StructureBlockInfo> m_74652_() {
            return this.f_74645_;
        }

        public List<StructureBlockInfo> m_74653_(Block p_74654_) {
            return this.f_74646_.computeIfAbsent(p_74654_, p_74659_ -> this.f_74645_.stream().filter(p_163818_ -> p_163818_.f_74676_.m_60713_((Block)p_74659_)).collect(Collectors.toList()));
        }
    }

    public static class StructureEntityInfo {
        public final Vec3 f_74683_;
        public final BlockPos f_74684_;
        public final CompoundTag f_74685_;

        public StructureEntityInfo(Vec3 p_74687_, BlockPos p_74688_, CompoundTag p_74689_) {
            this.f_74683_ = p_74687_;
            this.f_74684_ = p_74688_;
            this.f_74685_ = p_74689_;
        }
    }

    static class SimplePalette
    implements Iterable<BlockState> {
        public static final BlockState f_74660_ = Blocks.f_50016_.m_49966_();
        private final IdMapper<BlockState> f_74661_ = new IdMapper(16);
        private int f_74662_;

        SimplePalette() {
        }

        public int m_74669_(BlockState p_74670_) {
            int $$1 = this.f_74661_.m_7447_(p_74670_);
            if ($$1 == -1) {
                $$1 = this.f_74662_++;
                this.f_74661_.m_122664_(p_74670_, $$1);
            }
            return $$1;
        }

        @Nullable
        public BlockState m_74667_(int p_74668_) {
            BlockState $$1 = this.f_74661_.m_7942_(p_74668_);
            return $$1 == null ? f_74660_ : $$1;
        }

        @Override
        public Iterator<BlockState> iterator() {
            return this.f_74661_.iterator();
        }

        public void m_74671_(BlockState p_74672_, int p_74673_) {
            this.f_74661_.m_122664_(p_74672_, p_74673_);
        }
    }
}

