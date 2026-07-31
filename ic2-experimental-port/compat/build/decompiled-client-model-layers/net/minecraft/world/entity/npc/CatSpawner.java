/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.npc;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.phys.AABB;

public class CatSpawner
implements CustomSpawner {
    private static final int f_149996_ = 1200;
    private int f_35324_;

    @Override
    public int m_7995_(ServerLevel p_35330_, boolean p_35331_, boolean p_35332_) {
        if (!p_35332_ || !p_35330_.m_46469_().m_46207_(GameRules.f_46134_)) {
            return 0;
        }
        --this.f_35324_;
        if (this.f_35324_ > 0) {
            return 0;
        }
        this.f_35324_ = 1200;
        ServerPlayer $$3 = p_35330_.m_8890_();
        if ($$3 == null) {
            return 0;
        }
        RandomSource $$4 = p_35330_.f_46441_;
        int $$5 = (8 + $$4.m_188503_(24)) * ($$4.m_188499_() ? -1 : 1);
        int $$6 = (8 + $$4.m_188503_(24)) * ($$4.m_188499_() ? -1 : 1);
        BlockPos $$7 = $$3.m_20183_().m_7918_($$5, 0, $$6);
        int $$8 = 10;
        if (!p_35330_.m_151572_($$7.m_123341_() - 10, $$7.m_123343_() - 10, $$7.m_123341_() + 10, $$7.m_123343_() + 10)) {
            return 0;
        }
        if (NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, p_35330_, $$7, EntityType.f_20553_)) {
            if (p_35330_.m_8736_($$7, 2)) {
                return this.m_35326_(p_35330_, $$7);
            }
            if (p_35330_.m_215010_().m_220491_($$7, StructureTags.f_215887_).m_73603_()) {
                return this.m_35336_(p_35330_, $$7);
            }
        }
        return 0;
    }

    private int m_35326_(ServerLevel p_35327_, BlockPos p_35328_) {
        List<Cat> $$3;
        int $$2 = 48;
        if (p_35327_.m_8904_().m_27121_(p_219610_ -> p_219610_.m_203565_(PoiTypes.f_218060_), p_35328_, 48, PoiManager.Occupancy.IS_OCCUPIED) > 4L && ($$3 = p_35327_.m_45976_(Cat.class, new AABB(p_35328_).m_82377_(48.0, 8.0, 48.0))).size() < 5) {
            return this.m_35333_(p_35328_, p_35327_);
        }
        return 0;
    }

    private int m_35336_(ServerLevel p_35337_, BlockPos p_35338_) {
        int $$2 = 16;
        List<Cat> $$3 = p_35337_.m_45976_(Cat.class, new AABB(p_35338_).m_82377_(16.0, 8.0, 16.0));
        if ($$3.size() < 1) {
            return this.m_35333_(p_35338_, p_35337_);
        }
        return 0;
    }

    private int m_35333_(BlockPos p_35334_, ServerLevel p_35335_) {
        Cat $$2 = EntityType.f_20553_.m_20615_(p_35335_);
        if ($$2 == null) {
            return 0;
        }
        $$2.m_6518_(p_35335_, p_35335_.m_6436_(p_35334_), MobSpawnType.NATURAL, null, null);
        $$2.m_20035_(p_35334_, 0.0f, 0.0f);
        p_35335_.m_47205_($$2);
        return 1;
    }
}

