/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStandItem
extends Item {
    public ArmorStandItem(Item.Properties p_40503_) {
        super(p_40503_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40510_) {
        Direction $$1 = p_40510_.m_43719_();
        if ($$1 == Direction.DOWN) {
            return InteractionResult.FAIL;
        }
        Level $$2 = p_40510_.m_43725_();
        BlockPlaceContext $$3 = new BlockPlaceContext(p_40510_);
        BlockPos $$4 = $$3.m_8083_();
        ItemStack $$5 = p_40510_.m_43722_();
        Vec3 $$6 = Vec3.m_82539_($$4);
        AABB $$7 = EntityType.f_20529_.m_20680_().m_20384_($$6.m_7096_(), $$6.m_7098_(), $$6.m_7094_());
        if (!$$2.m_45756_(null, $$7) || !$$2.m_45933_(null, $$7).isEmpty()) {
            return InteractionResult.FAIL;
        }
        if ($$2 instanceof ServerLevel) {
            ServerLevel $$8 = (ServerLevel)$$2;
            ArmorStand $$9 = EntityType.f_20529_.m_20655_($$8, $$5.m_41783_(), null, p_40510_.m_43723_(), $$4, MobSpawnType.SPAWN_EGG, true, true);
            if ($$9 == null) {
                return InteractionResult.FAIL;
            }
            float $$10 = (float)Mth.m_14143_((Mth.m_14177_(p_40510_.m_7074_() - 180.0f) + 22.5f) / 45.0f) * 45.0f;
            $$9.m_7678_($$9.m_20185_(), $$9.m_20186_(), $$9.m_20189_(), $$10, 0.0f);
            this.m_219998_($$9, $$2.f_46441_);
            $$8.m_47205_($$9);
            $$2.m_6263_(null, $$9.m_20185_(), $$9.m_20186_(), $$9.m_20189_(), SoundEvents.f_11684_, SoundSource.BLOCKS, 0.75f, 0.8f);
            $$9.m_146852_(GameEvent.f_157810_, p_40510_.m_43723_());
        }
        $$5.m_41774_(1);
        return InteractionResult.m_19078_($$2.f_46443_);
    }

    private void m_219998_(ArmorStand p_219999_, RandomSource p_220000_) {
        Rotations $$2 = p_219999_.m_31680_();
        float $$3 = p_220000_.m_188501_() * 5.0f;
        float $$4 = p_220000_.m_188501_() * 20.0f - 10.0f;
        Rotations $$5 = new Rotations($$2.m_123156_() + $$3, $$2.m_123157_() + $$4, $$2.m_123158_());
        p_219999_.m_31597_($$5);
        $$2 = p_219999_.m_31685_();
        $$3 = p_220000_.m_188501_() * 10.0f - 5.0f;
        $$5 = new Rotations($$2.m_123156_(), $$2.m_123157_() + $$3, $$2.m_123158_());
        p_219999_.m_31616_($$5);
    }
}

