/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class JukeboxBlockEntity
extends BlockEntity
implements Clearable {
    private ItemStack f_59514_ = ItemStack.f_41583_;
    private int f_238796_;
    private long f_238695_;
    private long f_238572_;
    private boolean f_238637_;

    public JukeboxBlockEntity(BlockPos p_155613_, BlockState p_155614_) {
        super(BlockEntityType.f_58921_, p_155613_, p_155614_);
    }

    @Override
    public void m_142466_(CompoundTag p_155616_) {
        super.m_142466_(p_155616_);
        if (p_155616_.m_128425_("RecordItem", 10)) {
            this.m_59517_(ItemStack.m_41712_(p_155616_.m_128469_("RecordItem")));
        }
        this.f_238637_ = p_155616_.m_128471_("IsPlaying");
        this.f_238572_ = p_155616_.m_128454_("RecordStartTick");
        this.f_238695_ = p_155616_.m_128454_("TickCount");
    }

    @Override
    protected void m_183515_(CompoundTag p_187507_) {
        super.m_183515_(p_187507_);
        if (!this.m_59524_().m_41619_()) {
            p_187507_.m_128365_("RecordItem", this.m_59524_().m_41739_(new CompoundTag()));
        }
        p_187507_.m_128379_("IsPlaying", this.f_238637_);
        p_187507_.m_128356_("RecordStartTick", this.f_238572_);
        p_187507_.m_128356_("TickCount", this.f_238695_);
    }

    public ItemStack m_59524_() {
        return this.f_59514_;
    }

    public void m_59517_(ItemStack p_59518_) {
        this.f_59514_ = p_59518_;
        this.m_6596_();
    }

    public void m_239936_() {
        this.f_238572_ = this.f_238695_;
        this.f_238637_ = true;
    }

    @Override
    public void m_6211_() {
        this.m_59517_(ItemStack.f_41583_);
        this.f_238637_ = false;
    }

    public static void m_239937_(Level p_239938_, BlockPos p_239939_, BlockState p_239940_, JukeboxBlockEntity p_239941_) {
        Item item;
        ++p_239941_.f_238796_;
        if (JukeboxBlockEntity.m_240053_(p_239940_, p_239941_) && (item = p_239941_.m_59524_().m_41720_()) instanceof RecordItem) {
            RecordItem $$4 = (RecordItem)item;
            if (JukeboxBlockEntity.m_239766_(p_239941_, $$4)) {
                p_239938_.m_220407_(GameEvent.f_238649_, p_239939_, GameEvent.Context.m_223722_(p_239940_));
                p_239941_.f_238637_ = false;
            } else if (JukeboxBlockEntity.m_239365_(p_239941_)) {
                p_239941_.f_238796_ = 0;
                p_239938_.m_220407_(GameEvent.f_238690_, p_239939_, GameEvent.Context.m_223722_(p_239940_));
            }
        }
        ++p_239941_.f_238695_;
    }

    private static boolean m_240053_(BlockState p_240054_, JukeboxBlockEntity p_240055_) {
        return p_240054_.m_61143_(JukeboxBlock.f_54254_) != false && p_240055_.f_238637_;
    }

    private static boolean m_239766_(JukeboxBlockEntity p_239767_, RecordItem p_239768_) {
        return p_239767_.f_238695_ >= p_239767_.f_238572_ + (long)p_239768_.m_43036_();
    }

    private static boolean m_239365_(JukeboxBlockEntity p_239366_) {
        return p_239366_.f_238796_ >= 20;
    }
}

