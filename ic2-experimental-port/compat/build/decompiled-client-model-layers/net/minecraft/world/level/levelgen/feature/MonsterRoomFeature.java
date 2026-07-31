/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.slf4j.Logger;

public class MonsterRoomFeature
extends Feature<NoneFeatureConfiguration> {
    private static final Logger f_66340_ = LogUtils.getLogger();
    private static final EntityType<?>[] f_66341_ = new EntityType[]{EntityType.f_20524_, EntityType.f_20501_, EntityType.f_20501_, EntityType.f_20479_};
    private static final BlockState f_66342_ = Blocks.f_50627_.m_49966_();

    public MonsterRoomFeature(Codec<NoneFeatureConfiguration> p_66345_) {
        super(p_66345_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160066_) {
        Predicate<BlockState> $$1 = Feature.m_204735_(BlockTags.f_144287_);
        BlockPos $$2 = p_160066_.m_159777_();
        RandomSource $$3 = p_160066_.m_225041_();
        WorldGenLevel $$4 = p_160066_.m_159774_();
        int $$5 = 3;
        int $$6 = $$3.m_188503_(2) + 2;
        int $$7 = -$$6 - 1;
        int $$8 = $$6 + 1;
        int $$9 = -1;
        int $$10 = 4;
        int $$11 = $$3.m_188503_(2) + 2;
        int $$12 = -$$11 - 1;
        int $$13 = $$11 + 1;
        int $$14 = 0;
        for (int $$15 = $$7; $$15 <= $$8; ++$$15) {
            for (int $$16 = -1; $$16 <= 4; ++$$16) {
                for (int $$17 = $$12; $$17 <= $$13; ++$$17) {
                    BlockPos $$18 = $$2.m_7918_($$15, $$16, $$17);
                    Material $$19 = $$4.m_8055_($$18).m_60767_();
                    boolean $$20 = $$19.m_76333_();
                    if ($$16 == -1 && !$$20) {
                        return false;
                    }
                    if ($$16 == 4 && !$$20) {
                        return false;
                    }
                    if ($$15 != $$7 && $$15 != $$8 && $$17 != $$12 && $$17 != $$13 || $$16 != 0 || !$$4.m_46859_($$18) || !$$4.m_46859_($$18.m_7494_())) continue;
                    ++$$14;
                }
            }
        }
        if ($$14 < 1 || $$14 > 5) {
            return false;
        }
        for (int $$21 = $$7; $$21 <= $$8; ++$$21) {
            for (int $$22 = 3; $$22 >= -1; --$$22) {
                for (int $$23 = $$12; $$23 <= $$13; ++$$23) {
                    BlockPos $$24 = $$2.m_7918_($$21, $$22, $$23);
                    BlockState $$25 = $$4.m_8055_($$24);
                    if ($$21 == $$7 || $$22 == -1 || $$23 == $$12 || $$21 == $$8 || $$22 == 4 || $$23 == $$13) {
                        if ($$24.m_123342_() >= $$4.m_141937_() && !$$4.m_8055_($$24.m_7495_()).m_60767_().m_76333_()) {
                            $$4.m_7731_($$24, f_66342_, 2);
                            continue;
                        }
                        if (!$$25.m_60767_().m_76333_() || $$25.m_60713_(Blocks.f_50087_)) continue;
                        if ($$22 == -1 && $$3.m_188503_(4) != 0) {
                            this.m_159742_($$4, $$24, Blocks.f_50079_.m_49966_(), $$1);
                            continue;
                        }
                        this.m_159742_($$4, $$24, Blocks.f_50652_.m_49966_(), $$1);
                        continue;
                    }
                    if ($$25.m_60713_(Blocks.f_50087_) || $$25.m_60713_(Blocks.f_50085_)) continue;
                    this.m_159742_($$4, $$24, f_66342_, $$1);
                }
            }
        }
        block6: for (int $$26 = 0; $$26 < 2; ++$$26) {
            for (int $$27 = 0; $$27 < 3; ++$$27) {
                int $$30;
                int $$29;
                int $$28 = $$2.m_123341_() + $$3.m_188503_($$6 * 2 + 1) - $$6;
                BlockPos $$31 = new BlockPos($$28, $$29 = $$2.m_123342_(), $$30 = $$2.m_123343_() + $$3.m_188503_($$11 * 2 + 1) - $$11);
                if (!$$4.m_46859_($$31)) continue;
                int $$32 = 0;
                for (Direction $$33 : Direction.Plane.HORIZONTAL) {
                    if (!$$4.m_8055_($$31.m_121945_($$33)).m_60767_().m_76333_()) continue;
                    ++$$32;
                }
                if ($$32 != 1) continue;
                this.m_159742_($$4, $$31, StructurePiece.m_73407_($$4, $$31, Blocks.f_50087_.m_49966_()), $$1);
                RandomizableContainerBlockEntity.m_222766_($$4, $$3, $$31, BuiltInLootTables.f_78742_);
                continue block6;
            }
        }
        this.m_159742_($$4, $$2, Blocks.f_50085_.m_49966_(), $$1);
        BlockEntity $$34 = $$4.m_7702_($$2);
        if ($$34 instanceof SpawnerBlockEntity) {
            ((SpawnerBlockEntity)$$34).m_59801_().m_45462_(this.m_225153_($$3));
        } else {
            f_66340_.error("Failed to fetch mob spawner entity at ({}, {}, {})", new Object[]{$$2.m_123341_(), $$2.m_123342_(), $$2.m_123343_()});
        }
        return true;
    }

    private EntityType<?> m_225153_(RandomSource p_225154_) {
        return Util.m_214670_(f_66341_, p_225154_);
    }
}

