/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

public class MobBucketItem
extends BucketItem {
    private final EntityType<?> f_151134_;
    private final SoundEvent f_151135_;

    public MobBucketItem(EntityType<?> p_151137_, Fluid p_151138_, SoundEvent p_151139_, Item.Properties p_151140_) {
        super(p_151138_, p_151140_);
        this.f_151134_ = p_151137_;
        this.f_151135_ = p_151139_;
    }

    @Override
    public void m_142131_(@Nullable Player p_151146_, Level p_151147_, ItemStack p_151148_, BlockPos p_151149_) {
        if (p_151147_ instanceof ServerLevel) {
            this.m_151141_((ServerLevel)p_151147_, p_151148_, p_151149_);
            p_151147_.m_142346_(p_151146_, GameEvent.f_157810_, p_151149_);
        }
    }

    @Override
    protected void m_7718_(@Nullable Player p_151151_, LevelAccessor p_151152_, BlockPos p_151153_) {
        p_151152_.m_5594_(p_151151_, p_151153_, this.f_151135_, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    private void m_151141_(ServerLevel p_151142_, ItemStack p_151143_, BlockPos p_151144_) {
        Entity $$3 = this.f_151134_.m_20592_(p_151142_, p_151143_, null, p_151144_, MobSpawnType.BUCKET, true, false);
        if ($$3 instanceof Bucketable) {
            Bucketable $$4 = (Bucketable)((Object)$$3);
            $$4.m_142278_(p_151143_.m_41784_());
            $$4.m_27497_(true);
        }
    }

    @Override
    public void m_7373_(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) {
        CompoundTag $$4;
        if (this.f_151134_ == EntityType.f_20489_ && ($$4 = p_151155_.m_41783_()) != null && $$4.m_128425_("BucketVariantTag", 3)) {
            int $$5 = $$4.m_128451_("BucketVariantTag");
            ChatFormatting[] $$6 = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            String $$7 = "color.minecraft." + TropicalFish.m_30050_($$5);
            String $$8 = "color.minecraft." + TropicalFish.m_30052_($$5);
            for (int $$9 = 0; $$9 < TropicalFish.f_30007_.length; ++$$9) {
                if ($$5 != TropicalFish.f_30007_[$$9]) continue;
                p_151157_.add(Component.m_237115_(TropicalFish.m_30030_($$9)).m_130944_($$6));
                return;
            }
            p_151157_.add(Component.m_237115_(TropicalFish.m_30054_($$5)).m_130944_($$6));
            MutableComponent $$10 = Component.m_237115_($$7);
            if (!$$7.equals($$8)) {
                $$10.m_130946_(", ").m_7220_(Component.m_237115_($$8));
            }
            $$10.m_130944_($$6);
            p_151157_.add($$10);
        }
    }
}

