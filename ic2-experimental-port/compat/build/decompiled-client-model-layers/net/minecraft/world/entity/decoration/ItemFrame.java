/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.decoration;

import com.mojang.logging.LogUtils;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class ItemFrame
extends HangingEntity {
    private static final Logger f_31756_ = LogUtils.getLogger();
    private static final EntityDataAccessor<ItemStack> f_31757_ = SynchedEntityData.m_135353_(ItemFrame.class, EntityDataSerializers.f_135033_);
    private static final EntityDataAccessor<Integer> f_31758_ = SynchedEntityData.m_135353_(ItemFrame.class, EntityDataSerializers.f_135028_);
    public static final int f_149619_ = 8;
    private float f_31754_ = 1.0f;
    private boolean f_31755_;

    public ItemFrame(EntityType<? extends ItemFrame> p_31761_, Level p_31762_) {
        super((EntityType<? extends HangingEntity>)p_31761_, p_31762_);
    }

    public ItemFrame(Level p_31764_, BlockPos p_31765_, Direction p_31766_) {
        this(EntityType.f_20462_, p_31764_, p_31765_, p_31766_);
    }

    public ItemFrame(EntityType<? extends ItemFrame> p_149621_, Level p_149622_, BlockPos p_149623_, Direction p_149624_) {
        super(p_149621_, p_149622_, p_149623_);
        this.m_6022_(p_149624_);
    }

    @Override
    protected float m_6380_(Pose p_31784_, EntityDimensions p_31785_) {
        return 0.0f;
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_31757_, ItemStack.f_41583_);
        this.m_20088_().m_135372_(f_31758_, 0);
    }

    @Override
    protected void m_6022_(Direction p_31793_) {
        Validate.notNull((Object)p_31793_);
        this.f_31699_ = p_31793_;
        if (p_31793_.m_122434_().m_122479_()) {
            this.m_146926_(0.0f);
            this.m_146922_(this.f_31699_.m_122416_() * 90);
        } else {
            this.m_146926_(-90 * p_31793_.m_122421_().m_122540_());
            this.m_146922_(0.0f);
        }
        this.f_19860_ = this.m_146909_();
        this.f_19859_ = this.m_146908_();
        this.m_7087_();
    }

    @Override
    protected void m_7087_() {
        if (this.f_31699_ == null) {
            return;
        }
        double $$0 = 0.46875;
        double $$1 = (double)this.f_31698_.m_123341_() + 0.5 - (double)this.f_31699_.m_122429_() * 0.46875;
        double $$2 = (double)this.f_31698_.m_123342_() + 0.5 - (double)this.f_31699_.m_122430_() * 0.46875;
        double $$3 = (double)this.f_31698_.m_123343_() + 0.5 - (double)this.f_31699_.m_122431_() * 0.46875;
        this.m_20343_($$1, $$2, $$3);
        double $$4 = this.m_7076_();
        double $$5 = this.m_7068_();
        double $$6 = this.m_7076_();
        Direction.Axis $$7 = this.f_31699_.m_122434_();
        switch ($$7) {
            case X: {
                $$4 = 1.0;
                break;
            }
            case Y: {
                $$5 = 1.0;
                break;
            }
            case Z: {
                $$6 = 1.0;
            }
        }
        this.m_20011_(new AABB($$1 - ($$4 /= 32.0), $$2 - ($$5 /= 32.0), $$3 - ($$6 /= 32.0), $$1 + $$4, $$2 + $$5, $$3 + $$6));
    }

    @Override
    public boolean m_7088_() {
        if (this.f_31755_) {
            return true;
        }
        if (!this.f_19853_.m_45786_(this)) {
            return false;
        }
        BlockState $$0 = this.f_19853_.m_8055_(this.f_31698_.m_121945_(this.f_31699_.m_122424_()));
        if (!($$0.m_60767_().m_76333_() || this.f_31699_.m_122434_().m_122479_() && DiodeBlock.m_52586_($$0))) {
            return false;
        }
        return this.f_19853_.m_6249_(this, this.m_20191_(), f_31697_).isEmpty();
    }

    @Override
    public void m_6478_(MoverType p_31781_, Vec3 p_31782_) {
        if (!this.f_31755_) {
            super.m_6478_(p_31781_, p_31782_);
        }
    }

    @Override
    public void m_5997_(double p_31817_, double p_31818_, double p_31819_) {
        if (!this.f_31755_) {
            super.m_5997_(p_31817_, p_31818_, p_31819_);
        }
    }

    @Override
    public float m_6143_() {
        return 0.0f;
    }

    @Override
    public void m_6074_() {
        this.m_31810_(this.m_31822_());
        super.m_6074_();
    }

    @Override
    public boolean m_6469_(DamageSource p_31776_, float p_31777_) {
        if (this.f_31755_) {
            if (p_31776_ == DamageSource.f_19317_ || p_31776_.m_19390_()) {
                return super.m_6469_(p_31776_, p_31777_);
            }
            return false;
        }
        if (this.m_6673_(p_31776_)) {
            return false;
        }
        if (!p_31776_.m_19372_() && !this.m_31822_().m_41619_()) {
            if (!this.f_19853_.f_46443_) {
                this.m_31802_(p_31776_.m_7639_(), false);
                this.m_5496_(this.m_142544_(), 1.0f, 1.0f);
            }
            return true;
        }
        return super.m_6469_(p_31776_, p_31777_);
    }

    public SoundEvent m_142544_() {
        return SoundEvents.f_12016_;
    }

    @Override
    public int m_7076_() {
        return 12;
    }

    @Override
    public int m_7068_() {
        return 12;
    }

    @Override
    public boolean m_6783_(double p_31769_) {
        double $$1 = 16.0;
        return p_31769_ < ($$1 *= 64.0 * ItemFrame.m_20150_()) * $$1;
    }

    @Override
    public void m_5553_(@Nullable Entity p_31779_) {
        this.m_5496_(this.m_142543_(), 1.0f, 1.0f);
        this.m_31802_(p_31779_, true);
    }

    public SoundEvent m_142543_() {
        return SoundEvents.f_12014_;
    }

    @Override
    public void m_7084_() {
        this.m_5496_(this.m_142541_(), 1.0f, 1.0f);
    }

    public SoundEvent m_142541_() {
        return SoundEvents.f_12015_;
    }

    private void m_31802_(@Nullable Entity p_31803_, boolean p_31804_) {
        if (this.f_31755_) {
            return;
        }
        ItemStack $$2 = this.m_31822_();
        this.m_31805_(ItemStack.f_41583_);
        if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
            if (p_31803_ == null) {
                this.m_31810_($$2);
            }
            return;
        }
        if (p_31803_ instanceof Player) {
            Player $$3 = (Player)p_31803_;
            if ($$3.m_150110_().f_35937_) {
                this.m_31810_($$2);
                return;
            }
        }
        if (p_31804_) {
            this.m_19983_(this.m_142590_());
        }
        if (!$$2.m_41619_()) {
            $$2 = $$2.m_41777_();
            this.m_31810_($$2);
            if (this.f_19796_.m_188501_() < this.f_31754_) {
                this.m_19983_($$2);
            }
        }
    }

    private void m_31810_(ItemStack p_31811_) {
        this.m_218868_().ifPresent(p_218864_ -> {
            MapItemSavedData $$1 = MapItem.m_151128_(p_218864_, this.f_19853_);
            if ($$1 != null) {
                $$1.m_77947_(this.f_31698_, this.m_19879_());
                $$1.m_77760_(true);
            }
        });
        p_31811_.m_41636_(null);
    }

    public ItemStack m_31822_() {
        return this.m_20088_().m_135370_(f_31757_);
    }

    public OptionalInt m_218868_() {
        Integer $$1;
        ItemStack $$0 = this.m_31822_();
        if ($$0.m_150930_(Items.f_42573_) && ($$1 = MapItem.m_151131_($$0)) != null) {
            return OptionalInt.of($$1);
        }
        return OptionalInt.empty();
    }

    public boolean m_218869_() {
        return this.m_218868_().isPresent();
    }

    public void m_31805_(ItemStack p_31806_) {
        this.m_31789_(p_31806_, true);
    }

    public void m_31789_(ItemStack p_31790_, boolean p_31791_) {
        if (!p_31790_.m_41619_()) {
            p_31790_ = p_31790_.m_41777_();
            p_31790_.m_41764_(1);
        }
        this.m_218865_(p_31790_);
        this.m_20088_().m_135381_(f_31757_, p_31790_);
        if (!p_31790_.m_41619_()) {
            this.m_5496_(this.m_142546_(), 1.0f, 1.0f);
        }
        if (p_31791_ && this.f_31698_ != null) {
            this.f_19853_.m_46717_(this.f_31698_, Blocks.f_50016_);
        }
    }

    public SoundEvent m_142546_() {
        return SoundEvents.f_12013_;
    }

    @Override
    public SlotAccess m_141942_(int p_149629_) {
        if (p_149629_ == 0) {
            return new SlotAccess(){

                @Override
                public ItemStack m_142196_() {
                    return ItemFrame.this.m_31822_();
                }

                @Override
                public boolean m_142104_(ItemStack p_149635_) {
                    ItemFrame.this.m_31805_(p_149635_);
                    return true;
                }
            };
        }
        return super.m_141942_(p_149629_);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_31797_) {
        if (p_31797_.equals(f_31757_)) {
            this.m_218865_(this.m_31822_());
        }
    }

    private void m_218865_(ItemStack p_218866_) {
        if (!p_218866_.m_41619_() && p_218866_.m_41795_() != this) {
            p_218866_.m_41636_(this);
        }
        this.m_7087_();
    }

    public int m_31823_() {
        return this.m_20088_().m_135370_(f_31758_);
    }

    public void m_31770_(int p_31771_) {
        this.m_31772_(p_31771_, true);
    }

    private void m_31772_(int p_31773_, boolean p_31774_) {
        this.m_20088_().m_135381_(f_31758_, p_31773_ % 8);
        if (p_31774_ && this.f_31698_ != null) {
            this.f_19853_.m_46717_(this.f_31698_, Blocks.f_50016_);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_31808_) {
        super.m_7380_(p_31808_);
        if (!this.m_31822_().m_41619_()) {
            p_31808_.m_128365_("Item", this.m_31822_().m_41739_(new CompoundTag()));
            p_31808_.m_128344_("ItemRotation", (byte)this.m_31823_());
            p_31808_.m_128350_("ItemDropChance", this.f_31754_);
        }
        p_31808_.m_128344_("Facing", (byte)this.f_31699_.m_122411_());
        p_31808_.m_128379_("Invisible", this.m_20145_());
        p_31808_.m_128379_("Fixed", this.f_31755_);
    }

    @Override
    public void m_7378_(CompoundTag p_31795_) {
        super.m_7378_(p_31795_);
        CompoundTag $$1 = p_31795_.m_128469_("Item");
        if ($$1 != null && !$$1.m_128456_()) {
            ItemStack $$3;
            ItemStack $$2 = ItemStack.m_41712_($$1);
            if ($$2.m_41619_()) {
                f_31756_.warn("Unable to load item from: {}", (Object)$$1);
            }
            if (!($$3 = this.m_31822_()).m_41619_() && !ItemStack.m_41728_($$2, $$3)) {
                this.m_31810_($$3);
            }
            this.m_31789_($$2, false);
            this.m_31772_(p_31795_.m_128445_("ItemRotation"), false);
            if (p_31795_.m_128425_("ItemDropChance", 99)) {
                this.f_31754_ = p_31795_.m_128457_("ItemDropChance");
            }
        }
        this.m_6022_(Direction.m_122376_(p_31795_.m_128445_("Facing")));
        this.m_6842_(p_31795_.m_128471_("Invisible"));
        this.f_31755_ = p_31795_.m_128471_("Fixed");
    }

    @Override
    public InteractionResult m_6096_(Player p_31787_, InteractionHand p_31788_) {
        boolean $$4;
        ItemStack $$2 = p_31787_.m_21120_(p_31788_);
        boolean $$3 = !this.m_31822_().m_41619_();
        boolean bl = $$4 = !$$2.m_41619_();
        if (this.f_31755_) {
            return InteractionResult.PASS;
        }
        if (this.f_19853_.f_46443_) {
            return $$3 || $$4 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        if (!$$3) {
            if ($$4 && !this.m_213877_()) {
                MapItemSavedData $$5;
                if ($$2.m_150930_(Items.f_42573_) && ($$5 = MapItem.m_42853_($$2, this.f_19853_)) != null && $$5.m_181312_(256)) {
                    return InteractionResult.FAIL;
                }
                this.m_31805_($$2);
                if (!p_31787_.m_150110_().f_35937_) {
                    $$2.m_41774_(1);
                }
            }
        } else {
            this.m_5496_(this.m_142545_(), 1.0f, 1.0f);
            this.m_31770_(this.m_31823_() + 1);
        }
        return InteractionResult.CONSUME;
    }

    public SoundEvent m_142545_() {
        return SoundEvents.f_12017_;
    }

    public int m_31824_() {
        if (this.m_31822_().m_41619_()) {
            return 0;
        }
        return this.m_31823_() % 8 + 1;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this, this.f_31699_.m_122411_(), this.m_31748_());
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_149626_) {
        super.m_141965_(p_149626_);
        this.m_6022_(Direction.m_122376_(p_149626_.m_131509_()));
    }

    @Override
    public ItemStack m_142340_() {
        ItemStack $$0 = this.m_31822_();
        if ($$0.m_41619_()) {
            return this.m_142590_();
        }
        return $$0.m_41777_();
    }

    protected ItemStack m_142590_() {
        return new ItemStack(Items.f_42617_);
    }

    @Override
    public float m_213816_() {
        Direction $$0 = this.m_6350_();
        int $$1 = $$0.m_122434_().m_122478_() ? 90 * $$0.m_122421_().m_122540_() : 0;
        return Mth.m_14098_(180 + $$0.m_122416_() * 90 + this.m_31823_() * 45 + $$1);
    }
}

