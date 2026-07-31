/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.village;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class VillageSiege
implements CustomSpawner {
    private static final Logger f_26997_ = LogUtils.getLogger();
    private boolean f_26998_;
    private State f_26999_ = State.SIEGE_DONE;
    private int f_27000_;
    private int f_27001_;
    private int f_27002_;
    private int f_27003_;
    private int f_27004_;

    @Override
    public int m_7995_(ServerLevel p_27013_, boolean p_27014_, boolean p_27015_) {
        if (p_27013_.m_46461_() || !p_27014_) {
            this.f_26999_ = State.SIEGE_DONE;
            this.f_26998_ = false;
            return 0;
        }
        float $$3 = p_27013_.m_46942_(0.0f);
        if ((double)$$3 == 0.5) {
            State state = this.f_26999_ = p_27013_.f_46441_.m_188503_(10) == 0 ? State.SIEGE_TONIGHT : State.SIEGE_DONE;
        }
        if (this.f_26999_ == State.SIEGE_DONE) {
            return 0;
        }
        if (!this.f_26998_) {
            if (this.m_27007_(p_27013_)) {
                this.f_26998_ = true;
            } else {
                return 0;
            }
        }
        if (this.f_27001_ > 0) {
            --this.f_27001_;
            return 0;
        }
        this.f_27001_ = 2;
        if (this.f_27000_ > 0) {
            this.m_27016_(p_27013_);
            --this.f_27000_;
        } else {
            this.f_26999_ = State.SIEGE_DONE;
        }
        return 1;
    }

    private boolean m_27007_(ServerLevel p_27008_) {
        for (Player player : p_27008_.m_6907_()) {
            BlockPos $$2;
            if (player.m_5833_() || !p_27008_.m_8802_($$2 = player.m_20183_()) || p_27008_.m_204166_($$2).m_203656_(BiomeTags.f_215805_)) continue;
            for (int $$3 = 0; $$3 < 10; ++$$3) {
                float $$4 = p_27008_.f_46441_.m_188501_() * ((float)Math.PI * 2);
                this.f_27002_ = $$2.m_123341_() + Mth.m_14143_(Mth.m_14089_($$4) * 32.0f);
                this.f_27003_ = $$2.m_123342_();
                this.f_27004_ = $$2.m_123343_() + Mth.m_14143_(Mth.m_14031_($$4) * 32.0f);
                if (this.m_27009_(p_27008_, new BlockPos(this.f_27002_, this.f_27003_, this.f_27004_)) == null) continue;
                this.f_27001_ = 0;
                this.f_27000_ = 20;
                break;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private void m_27016_(ServerLevel p_27017_) {
        void $$4;
        Vec3 $$1 = this.m_27009_(p_27017_, new BlockPos(this.f_27002_, this.f_27003_, this.f_27004_));
        if ($$1 == null) {
            return;
        }
        try {
            Zombie $$2 = new Zombie(p_27017_);
            $$2.m_6518_(p_27017_, p_27017_.m_6436_($$2.m_20183_()), MobSpawnType.EVENT, null, null);
        }
        catch (Exception $$3) {
            f_26997_.warn("Failed to create zombie for village siege at {}", (Object)$$1, (Object)$$3);
            return;
        }
        $$4.m_7678_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_, p_27017_.f_46441_.m_188501_() * 360.0f, 0.0f);
        p_27017_.m_47205_((Entity)$$4);
    }

    @Nullable
    private Vec3 m_27009_(ServerLevel p_27010_, BlockPos p_27011_) {
        for (int $$2 = 0; $$2 < 10; ++$$2) {
            int $$4;
            int $$5;
            int $$3 = p_27011_.m_123341_() + p_27010_.f_46441_.m_188503_(16) - 8;
            BlockPos $$6 = new BlockPos($$3, $$5 = p_27010_.m_6924_(Heightmap.Types.WORLD_SURFACE, $$3, $$4 = p_27011_.m_123343_() + p_27010_.f_46441_.m_188503_(16) - 8), $$4);
            if (!p_27010_.m_8802_($$6) || !Monster.m_219013_(EntityType.f_20501_, p_27010_, MobSpawnType.EVENT, $$6, p_27010_.f_46441_)) continue;
            return Vec3.m_82539_($$6);
        }
        return null;
    }

    static final class State
    extends Enum<State> {
        public static final /* enum */ State SIEGE_CAN_ACTIVATE = new State();
        public static final /* enum */ State SIEGE_TONIGHT = new State();
        public static final /* enum */ State SIEGE_DONE = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_27027_) {
            return Enum.valueOf(State.class, p_27027_);
        }

        private static /* synthetic */ State[] m_148564_() {
            return new State[]{SIEGE_CAN_ACTIVATE, SIEGE_TONIGHT, SIEGE_DONE};
        }

        static {
            $VALUES = State.m_148564_();
        }
    }
}

