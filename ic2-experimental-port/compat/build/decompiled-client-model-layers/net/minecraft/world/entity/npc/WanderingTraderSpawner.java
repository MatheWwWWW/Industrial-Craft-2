/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.npc;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;

public class WanderingTraderSpawner
implements CustomSpawner {
    private static final int f_150051_ = 1200;
    public static final int f_150050_ = 24000;
    private static final int f_150052_ = 25;
    private static final int f_150053_ = 75;
    private static final int f_150054_ = 25;
    private static final int f_150055_ = 10;
    private static final int f_150056_ = 10;
    private final RandomSource f_35908_ = RandomSource.m_216327_();
    private final ServerLevelData f_35909_;
    private int f_35910_;
    private int f_35911_;
    private int f_35912_;

    public WanderingTraderSpawner(ServerLevelData p_35914_) {
        this.f_35909_ = p_35914_;
        this.f_35910_ = 1200;
        this.f_35911_ = p_35914_.m_6530_();
        this.f_35912_ = p_35914_.m_6528_();
        if (this.f_35911_ == 0 && this.f_35912_ == 0) {
            this.f_35911_ = 24000;
            p_35914_.m_6391_(this.f_35911_);
            this.f_35912_ = 25;
            p_35914_.m_6387_(this.f_35912_);
        }
    }

    @Override
    public int m_7995_(ServerLevel p_35922_, boolean p_35923_, boolean p_35924_) {
        if (!p_35922_.m_46469_().m_46207_(GameRules.f_46125_)) {
            return 0;
        }
        if (--this.f_35910_ > 0) {
            return 0;
        }
        this.f_35910_ = 1200;
        this.f_35911_ -= 1200;
        this.f_35909_.m_6391_(this.f_35911_);
        if (this.f_35911_ > 0) {
            return 0;
        }
        this.f_35911_ = 24000;
        if (!p_35922_.m_46469_().m_46207_(GameRules.f_46134_)) {
            return 0;
        }
        int $$3 = this.f_35912_;
        this.f_35912_ = Mth.m_14045_(this.f_35912_ + 25, 25, 75);
        this.f_35909_.m_6387_(this.f_35912_);
        if (this.f_35908_.m_188503_(100) > $$3) {
            return 0;
        }
        if (this.m_35915_(p_35922_)) {
            this.f_35912_ = 25;
            return 1;
        }
        return 0;
    }

    private boolean m_35915_(ServerLevel p_35916_) {
        ServerPlayer $$1 = p_35916_.m_8890_();
        if ($$1 == null) {
            return true;
        }
        if (this.f_35908_.m_188503_(10) != 0) {
            return false;
        }
        BlockPos $$2 = $$1.m_20183_();
        int $$3 = 48;
        PoiManager $$4 = p_35916_.m_8904_();
        Optional<BlockPos> $$5 = $$4.m_27186_(p_219713_ -> p_219713_.m_203565_(PoiTypes.f_218061_), p_219711_ -> true, $$2, 48, PoiManager.Occupancy.ANY);
        BlockPos $$6 = $$5.orElse($$2);
        BlockPos $$7 = this.m_35928_(p_35916_, $$6, 48);
        if ($$7 != null && this.m_35925_(p_35916_, $$7)) {
            if (p_35916_.m_204166_($$7).m_203656_(BiomeTags.f_215807_)) {
                return false;
            }
            WanderingTrader $$8 = EntityType.f_20494_.m_20600_(p_35916_, null, null, null, $$7, MobSpawnType.EVENT, false, false);
            if ($$8 != null) {
                for (int $$9 = 0; $$9 < 2; ++$$9) {
                    this.m_35917_(p_35916_, $$8, 4);
                }
                this.f_35909_.m_8115_($$8.m_20148_());
                $$8.m_35891_(48000);
                $$8.m_35883_($$6);
                $$8.m_21446_($$6, 16);
                return true;
            }
        }
        return false;
    }

    private void m_35917_(ServerLevel p_35918_, WanderingTrader p_35919_, int p_35920_) {
        BlockPos $$3 = this.m_35928_(p_35918_, p_35919_.m_20183_(), p_35920_);
        if ($$3 == null) {
            return;
        }
        TraderLlama $$4 = EntityType.f_20488_.m_20600_(p_35918_, null, null, null, $$3, MobSpawnType.EVENT, false, false);
        if ($$4 == null) {
            return;
        }
        $$4.m_21463_(p_35919_, true);
    }

    @Nullable
    private BlockPos m_35928_(LevelReader p_35929_, BlockPos p_35930_, int p_35931_) {
        BlockPos $$3 = null;
        for (int $$4 = 0; $$4 < 10; ++$$4) {
            int $$6;
            int $$7;
            int $$5 = p_35930_.m_123341_() + this.f_35908_.m_188503_(p_35931_ * 2) - p_35931_;
            BlockPos $$8 = new BlockPos($$5, $$7 = p_35929_.m_6924_(Heightmap.Types.WORLD_SURFACE, $$5, $$6 = p_35930_.m_123343_() + this.f_35908_.m_188503_(p_35931_ * 2) - p_35931_), $$6);
            if (!NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, p_35929_, $$8, EntityType.f_20494_)) continue;
            $$3 = $$8;
            break;
        }
        return $$3;
    }

    private boolean m_35925_(BlockGetter p_35926_, BlockPos p_35927_) {
        for (BlockPos $$2 : BlockPos.m_121940_(p_35927_, p_35927_.m_7918_(1, 2, 1))) {
            if (p_35926_.m_8055_($$2).m_60812_(p_35926_, $$2).m_83281_()) continue;
            return false;
        }
        return true;
    }
}

