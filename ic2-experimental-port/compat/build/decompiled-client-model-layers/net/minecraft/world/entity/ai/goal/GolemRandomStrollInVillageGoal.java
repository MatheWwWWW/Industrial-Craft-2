/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GolemRandomStrollInVillageGoal
extends RandomStrollGoal {
    private static final int f_148106_ = 2;
    private static final int f_148107_ = 32;
    private static final int f_148108_ = 10;
    private static final int f_148109_ = 7;

    public GolemRandomStrollInVillageGoal(PathfinderMob p_25398_, double p_25399_) {
        super(p_25398_, p_25399_, 240, false);
    }

    @Override
    @Nullable
    protected Vec3 m_7037_() {
        Vec3 $$2;
        float $$0 = this.f_25725_.f_19853_.f_46441_.m_188501_();
        if (this.f_25725_.f_19853_.f_46441_.m_188501_() < 0.3f) {
            return this.m_25410_();
        }
        if ($$0 < 0.7f) {
            Vec3 $$1 = this.m_25411_();
            if ($$1 == null) {
                $$1 = this.m_25412_();
            }
        } else {
            $$2 = this.m_25412_();
            if ($$2 == null) {
                $$2 = this.m_25411_();
            }
        }
        return $$2 == null ? this.m_25410_() : $$2;
    }

    @Nullable
    private Vec3 m_25410_() {
        return LandRandomPos.m_148488_(this.f_25725_, 10, 7);
    }

    @Nullable
    private Vec3 m_25411_() {
        ServerLevel $$0 = (ServerLevel)this.f_25725_.f_19853_;
        List<Villager> $$1 = $$0.m_142425_(EntityType.f_20492_, this.f_25725_.m_20191_().m_82400_(32.0), this::m_25405_);
        if ($$1.isEmpty()) {
            return null;
        }
        Villager $$2 = $$1.get(this.f_25725_.f_19853_.f_46441_.m_188503_($$1.size()));
        Vec3 $$3 = $$2.m_20182_();
        return LandRandomPos.m_148492_(this.f_25725_, 10, 7, $$3);
    }

    @Nullable
    private Vec3 m_25412_() {
        SectionPos $$0 = this.m_25413_();
        if ($$0 == null) {
            return null;
        }
        BlockPos $$1 = this.m_25407_($$0);
        if ($$1 == null) {
            return null;
        }
        return LandRandomPos.m_148492_(this.f_25725_, 10, 7, Vec3.m_82539_($$1));
    }

    @Nullable
    private SectionPos m_25413_() {
        ServerLevel $$0 = (ServerLevel)this.f_25725_.f_19853_;
        List $$1 = SectionPos.m_123201_(SectionPos.m_235861_(this.f_25725_), 2).filter(p_25402_ -> $$0.m_8828_((SectionPos)p_25402_) == 0).collect(Collectors.toList());
        if ($$1.isEmpty()) {
            return null;
        }
        return (SectionPos)$$1.get($$0.f_46441_.m_188503_($$1.size()));
    }

    @Nullable
    private BlockPos m_25407_(SectionPos p_25408_) {
        ServerLevel $$1 = (ServerLevel)this.f_25725_.f_19853_;
        PoiManager $$2 = $$1.m_8904_();
        List $$3 = $$2.m_27181_(p_217747_ -> true, p_25408_.m_123250_(), 8, PoiManager.Occupancy.IS_OCCUPIED).map(PoiRecord::m_27257_).collect(Collectors.toList());
        if ($$3.isEmpty()) {
            return null;
        }
        return (BlockPos)$$3.get($$1.f_46441_.m_188503_($$3.size()));
    }

    private boolean m_25405_(Villager p_25406_) {
        return p_25406_.m_35392_(this.f_25725_.f_19853_.m_46467_());
    }
}

