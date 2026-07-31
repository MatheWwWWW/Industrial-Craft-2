/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShulkerBoxBlockEntity
extends RandomizableContainerBlockEntity
implements WorldlyContainer {
    public static final int f_155657_ = 9;
    public static final int f_155658_ = 3;
    public static final int f_155659_ = 27;
    public static final int f_155660_ = 1;
    public static final int f_155661_ = 10;
    public static final float f_155662_ = 0.5f;
    public static final float f_155663_ = 270.0f;
    public static final String f_155664_ = "Items";
    private static final int[] f_59644_ = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> f_59645_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
    private int f_59646_;
    private AnimationStatus f_59647_ = AnimationStatus.CLOSED;
    private float f_59648_;
    private float f_59649_;
    @Nullable
    private final DyeColor f_59650_;

    public ShulkerBoxBlockEntity(@Nullable DyeColor p_155666_, BlockPos p_155667_, BlockState p_155668_) {
        super(BlockEntityType.f_58939_, p_155667_, p_155668_);
        this.f_59650_ = p_155666_;
    }

    public ShulkerBoxBlockEntity(BlockPos p_155670_, BlockState p_155671_) {
        super(BlockEntityType.f_58939_, p_155670_, p_155671_);
        this.f_59650_ = ShulkerBoxBlock.m_56262_(p_155671_.m_60734_());
    }

    public static void m_155672_(Level p_155673_, BlockPos p_155674_, BlockState p_155675_, ShulkerBoxBlockEntity p_155676_) {
        p_155676_.m_155679_(p_155673_, p_155674_, p_155675_);
    }

    private void m_155679_(Level p_155680_, BlockPos p_155681_, BlockState p_155682_) {
        this.f_59649_ = this.f_59648_;
        switch (this.f_59647_) {
            case CLOSED: {
                this.f_59648_ = 0.0f;
                break;
            }
            case OPENING: {
                this.f_59648_ += 0.1f;
                if (this.f_59648_ >= 1.0f) {
                    this.f_59647_ = AnimationStatus.OPENED;
                    this.f_59648_ = 1.0f;
                    ShulkerBoxBlockEntity.m_155687_(p_155680_, p_155681_, p_155682_);
                }
                this.m_155683_(p_155680_, p_155681_, p_155682_);
                break;
            }
            case CLOSING: {
                this.f_59648_ -= 0.1f;
                if (!(this.f_59648_ <= 0.0f)) break;
                this.f_59647_ = AnimationStatus.CLOSED;
                this.f_59648_ = 0.0f;
                ShulkerBoxBlockEntity.m_155687_(p_155680_, p_155681_, p_155682_);
                break;
            }
            case OPENED: {
                this.f_59648_ = 1.0f;
            }
        }
    }

    public AnimationStatus m_59700_() {
        return this.f_59647_;
    }

    public AABB m_59666_(BlockState p_59667_) {
        return Shulker.m_149790_(p_59667_.m_61143_(ShulkerBoxBlock.f_56183_), 0.5f * this.m_59657_(1.0f));
    }

    private void m_155683_(Level p_155684_, BlockPos p_155685_, BlockState p_155686_) {
        if (!(p_155686_.m_60734_() instanceof ShulkerBoxBlock)) {
            return;
        }
        Direction $$3 = p_155686_.m_61143_(ShulkerBoxBlock.f_56183_);
        AABB $$4 = Shulker.m_149793_($$3, this.f_59649_, this.f_59648_).m_82338_(p_155685_);
        List<Entity> $$5 = p_155684_.m_45933_(null, $$4);
        if ($$5.isEmpty()) {
            return;
        }
        for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
            Entity $$7 = $$5.get($$6);
            if ($$7.m_7752_() == PushReaction.IGNORE) continue;
            $$7.m_6478_(MoverType.SHULKER_BOX, new Vec3(($$4.m_82362_() + 0.01) * (double)$$3.m_122429_(), ($$4.m_82376_() + 0.01) * (double)$$3.m_122430_(), ($$4.m_82385_() + 0.01) * (double)$$3.m_122431_()));
        }
    }

    @Override
    public int m_6643_() {
        return this.f_59645_.size();
    }

    @Override
    public boolean m_7531_(int p_59678_, int p_59679_) {
        if (p_59678_ == 1) {
            this.f_59646_ = p_59679_;
            if (p_59679_ == 0) {
                this.f_59647_ = AnimationStatus.CLOSING;
                ShulkerBoxBlockEntity.m_155687_(this.m_58904_(), this.f_58858_, this.m_58900_());
            }
            if (p_59679_ == 1) {
                this.f_59647_ = AnimationStatus.OPENING;
                ShulkerBoxBlockEntity.m_155687_(this.m_58904_(), this.f_58858_, this.m_58900_());
            }
            return true;
        }
        return super.m_7531_(p_59678_, p_59679_);
    }

    private static void m_155687_(Level p_155688_, BlockPos p_155689_, BlockState p_155690_) {
        p_155690_.m_60701_(p_155688_, p_155689_, 3);
    }

    @Override
    public void m_5856_(Player p_59692_) {
        if (!p_59692_.m_5833_()) {
            if (this.f_59646_ < 0) {
                this.f_59646_ = 0;
            }
            ++this.f_59646_;
            this.f_58857_.m_7696_(this.f_58858_, this.m_58900_().m_60734_(), 1, this.f_59646_);
            if (this.f_59646_ == 1) {
                this.f_58857_.m_142346_(p_59692_, GameEvent.f_157803_, this.f_58858_);
                this.f_58857_.m_5594_(null, this.f_58858_, SoundEvents.f_12409_, SoundSource.BLOCKS, 0.5f, this.f_58857_.f_46441_.m_188501_() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    public void m_5785_(Player p_59688_) {
        if (!p_59688_.m_5833_()) {
            --this.f_59646_;
            this.f_58857_.m_7696_(this.f_58858_, this.m_58900_().m_60734_(), 1, this.f_59646_);
            if (this.f_59646_ <= 0) {
                this.f_58857_.m_142346_(p_59688_, GameEvent.f_157802_, this.f_58858_);
                this.f_58857_.m_5594_(null, this.f_58858_, SoundEvents.f_12408_, SoundSource.BLOCKS, 0.5f, this.f_58857_.f_46441_.m_188501_() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.shulkerBox");
    }

    @Override
    public void m_142466_(CompoundTag p_155678_) {
        super.m_142466_(p_155678_);
        this.m_59693_(p_155678_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187513_) {
        super.m_183515_(p_187513_);
        if (!this.m_59634_(p_187513_)) {
            ContainerHelper.m_18976_(p_187513_, this.f_59645_, false);
        }
    }

    public void m_59693_(CompoundTag p_59694_) {
        this.f_59645_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
        if (!this.m_59631_(p_59694_) && p_59694_.m_128425_(f_155664_, 9)) {
            ContainerHelper.m_18980_(p_59694_, this.f_59645_);
        }
    }

    @Override
    protected NonNullList<ItemStack> m_7086_() {
        return this.f_59645_;
    }

    @Override
    protected void m_6520_(NonNullList<ItemStack> p_59674_) {
        this.f_59645_ = p_59674_;
    }

    @Override
    public int[] m_7071_(Direction p_59672_) {
        return f_59644_;
    }

    @Override
    public boolean m_7155_(int p_59663_, ItemStack p_59664_, @Nullable Direction p_59665_) {
        return !(Block.m_49814_(p_59664_.m_41720_()) instanceof ShulkerBoxBlock);
    }

    @Override
    public boolean m_7157_(int p_59682_, ItemStack p_59683_, Direction p_59684_) {
        return true;
    }

    public float m_59657_(float p_59658_) {
        return Mth.m_14179_(p_59658_, this.f_59649_, this.f_59648_);
    }

    @Nullable
    public DyeColor m_59701_() {
        return this.f_59650_;
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_59660_, Inventory p_59661_) {
        return new ShulkerBoxMenu(p_59660_, p_59661_, this);
    }

    public boolean m_59702_() {
        return this.f_59647_ == AnimationStatus.CLOSED;
    }

    public static final class AnimationStatus
    extends Enum<AnimationStatus> {
        public static final /* enum */ AnimationStatus CLOSED = new AnimationStatus();
        public static final /* enum */ AnimationStatus OPENING = new AnimationStatus();
        public static final /* enum */ AnimationStatus OPENED = new AnimationStatus();
        public static final /* enum */ AnimationStatus CLOSING = new AnimationStatus();
        private static final /* synthetic */ AnimationStatus[] $VALUES;

        public static AnimationStatus[] values() {
            return (AnimationStatus[])$VALUES.clone();
        }

        public static AnimationStatus valueOf(String p_59718_) {
            return Enum.valueOf(AnimationStatus.class, p_59718_);
        }

        private static /* synthetic */ AnimationStatus[] m_155691_() {
            return new AnimationStatus[]{CLOSED, OPENING, OPENED, CLOSING};
        }

        static {
            $VALUES = AnimationStatus.m_155691_();
        }
    }
}

