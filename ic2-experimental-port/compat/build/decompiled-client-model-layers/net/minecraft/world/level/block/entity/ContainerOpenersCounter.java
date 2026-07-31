/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public abstract class ContainerOpenersCounter {
    private static final int f_155447_ = 5;
    private int f_155448_;

    protected abstract void m_142292_(Level var1, BlockPos var2, BlockState var3);

    protected abstract void m_142289_(Level var1, BlockPos var2, BlockState var3);

    protected abstract void m_142148_(Level var1, BlockPos var2, BlockState var3, int var4, int var5);

    protected abstract boolean m_142718_(Player var1);

    public void m_155452_(Player p_155453_, Level p_155454_, BlockPos p_155455_, BlockState p_155456_) {
        int $$4;
        if (($$4 = this.f_155448_++) == 0) {
            this.m_142292_(p_155454_, p_155455_, p_155456_);
            p_155454_.m_142346_(p_155453_, GameEvent.f_157803_, p_155455_);
            ContainerOpenersCounter.m_155480_(p_155454_, p_155455_, p_155456_);
        }
        this.m_142148_(p_155454_, p_155455_, p_155456_, $$4, this.f_155448_);
    }

    public void m_155468_(Player p_155469_, Level p_155470_, BlockPos p_155471_, BlockState p_155472_) {
        int $$4 = this.f_155448_--;
        if (this.f_155448_ == 0) {
            this.m_142289_(p_155470_, p_155471_, p_155472_);
            p_155470_.m_142346_(p_155469_, GameEvent.f_157802_, p_155471_);
        }
        this.m_142148_(p_155470_, p_155471_, p_155472_, $$4, this.f_155448_);
    }

    private int m_155457_(Level p_155458_, BlockPos p_155459_) {
        int $$2 = p_155459_.m_123341_();
        int $$3 = p_155459_.m_123342_();
        int $$4 = p_155459_.m_123343_();
        float $$5 = 5.0f;
        AABB $$6 = new AABB((float)$$2 - 5.0f, (float)$$3 - 5.0f, (float)$$4 - 5.0f, (float)($$2 + 1) + 5.0f, (float)($$3 + 1) + 5.0f, (float)($$4 + 1) + 5.0f);
        return p_155458_.m_142425_(EntityTypeTest.m_156916_(Player.class), $$6, this::m_142718_).size();
    }

    public void m_155476_(Level p_155477_, BlockPos p_155478_, BlockState p_155479_) {
        int $$4 = this.f_155448_;
        int $$3 = this.m_155457_(p_155477_, p_155478_);
        if ($$4 != $$3) {
            boolean $$6;
            boolean $$5 = $$3 != 0;
            boolean bl = $$6 = $$4 != 0;
            if ($$5 && !$$6) {
                this.m_142292_(p_155477_, p_155478_, p_155479_);
                p_155477_.m_142346_(null, GameEvent.f_157803_, p_155478_);
            } else if (!$$5) {
                this.m_142289_(p_155477_, p_155478_, p_155479_);
                p_155477_.m_142346_(null, GameEvent.f_157802_, p_155478_);
            }
            this.f_155448_ = $$3;
        }
        this.m_142148_(p_155477_, p_155478_, p_155479_, $$4, $$3);
        if ($$3 > 0) {
            ContainerOpenersCounter.m_155480_(p_155477_, p_155478_, p_155479_);
        }
    }

    public int m_155450_() {
        return this.f_155448_;
    }

    private static void m_155480_(Level p_155481_, BlockPos p_155482_, BlockState p_155483_) {
        p_155481_.m_186460_(p_155482_, p_155483_.m_60734_(), 5);
    }
}

