/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.vehicle;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MinecartCommandBlock
extends AbstractMinecart {
    static final EntityDataAccessor<String> f_38503_ = SynchedEntityData.m_135353_(MinecartCommandBlock.class, EntityDataSerializers.f_135030_);
    static final EntityDataAccessor<Component> f_38504_ = SynchedEntityData.m_135353_(MinecartCommandBlock.class, EntityDataSerializers.f_135031_);
    private final BaseCommandBlock f_38505_ = new MinecartCommandBase();
    private static final int f_150284_ = 4;
    private int f_38506_;

    public MinecartCommandBlock(EntityType<? extends MinecartCommandBlock> p_38509_, Level p_38510_) {
        super(p_38509_, p_38510_);
    }

    public MinecartCommandBlock(Level p_38512_, double p_38513_, double p_38514_, double p_38515_) {
        super(EntityType.f_20471_, p_38512_, p_38513_, p_38514_, p_38515_);
    }

    @Override
    protected Item m_213728_() {
        return Items.f_42449_;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.m_20088_().m_135372_(f_38503_, "");
        this.m_20088_().m_135372_(f_38504_, CommonComponents.f_237098_);
    }

    @Override
    protected void m_7378_(CompoundTag p_38525_) {
        super.m_7378_(p_38525_);
        this.f_38505_.m_45431_(p_38525_);
        this.m_20088_().m_135381_(f_38503_, this.m_38534_().m_45438_());
        this.m_20088_().m_135381_(f_38504_, this.m_38534_().m_45437_());
    }

    @Override
    protected void m_7380_(CompoundTag p_38529_) {
        super.m_7380_(p_38529_);
        this.f_38505_.m_45421_(p_38529_);
    }

    @Override
    public AbstractMinecart.Type m_6064_() {
        return AbstractMinecart.Type.COMMAND_BLOCK;
    }

    @Override
    public BlockState m_6390_() {
        return Blocks.f_50272_.m_49966_();
    }

    public BaseCommandBlock m_38534_() {
        return this.f_38505_;
    }

    @Override
    public void m_6025_(int p_38517_, int p_38518_, int p_38519_, boolean p_38520_) {
        if (p_38520_ && this.f_19797_ - this.f_38506_ >= 4) {
            this.m_38534_().m_45414_(this.f_19853_);
            this.f_38506_ = this.f_19797_;
        }
    }

    @Override
    public InteractionResult m_6096_(Player p_38522_, InteractionHand p_38523_) {
        return this.f_38505_.m_45412_(p_38522_);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_38527_) {
        super.m_7350_(p_38527_);
        if (f_38504_.equals(p_38527_)) {
            try {
                this.f_38505_.m_45433_(this.m_20088_().m_135370_(f_38504_));
            }
            catch (Throwable throwable) {}
        } else if (f_38503_.equals(p_38527_)) {
            this.f_38505_.m_6590_(this.m_20088_().m_135370_(f_38503_));
        }
    }

    @Override
    public boolean m_6127_() {
        return true;
    }

    public class MinecartCommandBase
    extends BaseCommandBlock {
        @Override
        public ServerLevel m_5991_() {
            return (ServerLevel)MinecartCommandBlock.this.f_19853_;
        }

        @Override
        public void m_7368_() {
            MinecartCommandBlock.this.m_20088_().m_135381_(f_38503_, this.m_45438_());
            MinecartCommandBlock.this.m_20088_().m_135381_(f_38504_, this.m_45437_());
        }

        @Override
        public Vec3 m_6607_() {
            return MinecartCommandBlock.this.m_20182_();
        }

        public MinecartCommandBlock m_38543_() {
            return MinecartCommandBlock.this;
        }

        @Override
        public CommandSourceStack m_6712_() {
            return new CommandSourceStack(this, MinecartCommandBlock.this.m_20182_(), MinecartCommandBlock.this.m_20155_(), this.m_5991_(), 2, this.m_45439_().getString(), MinecartCommandBlock.this.m_5446_(), this.m_5991_().m_7654_(), MinecartCommandBlock.this);
        }
    }
}

