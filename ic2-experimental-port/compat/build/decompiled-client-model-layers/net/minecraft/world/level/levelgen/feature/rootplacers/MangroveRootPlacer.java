/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MangroveRootPlacer
extends RootPlacer {
    public static final int f_225811_ = 8;
    public static final int f_225812_ = 15;
    public static final Codec<MangroveRootPlacer> f_225813_ = RecordCodecBuilder.create(p_225856_ -> MangroveRootPlacer.m_225885_(p_225856_).and((App)MangroveRootPlacement.f_225772_.fieldOf("mangrove_root_placement").forGetter(p_225849_ -> p_225849_.f_225814_)).apply((Applicative)p_225856_, MangroveRootPlacer::new));
    private final MangroveRootPlacement f_225814_;

    public MangroveRootPlacer(IntProvider p_225817_, BlockStateProvider p_225818_, Optional<AboveRootPlacement> p_225819_, MangroveRootPlacement p_225820_) {
        super(p_225817_, p_225818_, p_225819_);
        this.f_225814_ = p_225820_;
    }

    @Override
    public boolean m_213684_(LevelSimulatedReader p_225840_, BiConsumer<BlockPos, BlockState> p_225841_, RandomSource p_225842_, BlockPos p_225843_, BlockPos p_225844_, TreeConfiguration p_225845_) {
        ArrayList $$6 = Lists.newArrayList();
        BlockPos.MutableBlockPos $$7 = p_225843_.m_122032_();
        while ($$7.m_123342_() < p_225844_.m_123342_()) {
            if (!this.m_213551_(p_225840_, $$7)) {
                return false;
            }
            $$7.m_122173_(Direction.UP);
        }
        $$6.add(p_225844_.m_7495_());
        for (Direction $$8 : Direction.Plane.HORIZONTAL) {
            ArrayList $$10;
            BlockPos $$9 = p_225844_.m_121945_($$8);
            if (!this.m_225822_(p_225840_, p_225842_, $$9, $$8, p_225844_, $$10 = Lists.newArrayList(), 0)) {
                return false;
            }
            $$6.addAll($$10);
            $$6.add(p_225844_.m_121945_($$8));
        }
        for (BlockPos $$11 : $$6) {
            this.m_213654_(p_225840_, p_225841_, p_225842_, $$11, p_225845_);
        }
        return true;
    }

    private boolean m_225822_(LevelSimulatedReader p_225823_, RandomSource p_225824_, BlockPos p_225825_, Direction p_225826_, BlockPos p_225827_, List<BlockPos> p_225828_, int p_225829_) {
        int $$7 = this.f_225814_.f_225777_();
        if (p_225829_ == $$7 || p_225828_.size() > $$7) {
            return false;
        }
        List<BlockPos> $$8 = this.m_225850_(p_225825_, p_225826_, p_225824_, p_225827_);
        for (BlockPos $$9 : $$8) {
            if (!this.m_213551_(p_225823_, $$9)) continue;
            p_225828_.add($$9);
            if (this.m_225822_(p_225823_, p_225824_, $$9, p_225826_, p_225827_, p_225828_, p_225829_ + 1)) continue;
            return false;
        }
        return true;
    }

    protected List<BlockPos> m_225850_(BlockPos p_225851_, Direction p_225852_, RandomSource p_225853_, BlockPos p_225854_) {
        BlockPos $$4 = p_225851_.m_7495_();
        BlockPos $$5 = p_225851_.m_121945_(p_225852_);
        int $$6 = p_225851_.m_123333_(p_225854_);
        int $$7 = this.f_225814_.f_225776_();
        float $$8 = this.f_225814_.f_225778_();
        if ($$6 > $$7 - 3 && $$6 <= $$7) {
            return p_225853_.m_188501_() < $$8 ? List.of($$4, $$5.m_7495_()) : List.of($$4);
        }
        if ($$6 > $$7) {
            return List.of($$4);
        }
        if (p_225853_.m_188501_() < $$8) {
            return List.of($$4);
        }
        return p_225853_.m_188499_() ? List.of($$5) : List.of($$4);
    }

    @Override
    protected boolean m_213551_(LevelSimulatedReader p_225831_, BlockPos p_225832_) {
        return super.m_213551_(p_225831_, p_225832_) || p_225831_.m_7433_(p_225832_, p_225858_ -> p_225858_.m_204341_(this.f_225814_.f_225773_()));
    }

    @Override
    protected void m_213654_(LevelSimulatedReader p_225834_, BiConsumer<BlockPos, BlockState> p_225835_, RandomSource p_225836_, BlockPos p_225837_, TreeConfiguration p_225838_) {
        if (p_225834_.m_7433_(p_225837_, p_225847_ -> p_225847_.m_204341_(this.f_225814_.f_225774_()))) {
            BlockState $$5 = this.f_225814_.f_225775_().m_213972_(p_225836_, p_225837_);
            p_225835_.accept(p_225837_, this.m_225870_(p_225834_, p_225837_, $$5));
        } else {
            super.m_213654_(p_225834_, p_225835_, p_225836_, p_225837_, p_225838_);
        }
    }

    @Override
    protected RootPlacerType<?> m_213745_() {
        return RootPlacerType.f_225898_;
    }
}

