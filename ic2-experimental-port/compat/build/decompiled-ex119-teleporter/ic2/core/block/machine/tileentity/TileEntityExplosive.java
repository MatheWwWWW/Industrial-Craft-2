/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.machine.tileentity;

import ic2.api.entity.block.ExplosiveEntity;
import ic2.core.block.comp.Redstone;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class TileEntityExplosive
extends TileEntityInventory
implements Redstone.IRedstoneChangeHandler {
    protected final Redstone redstone = this.addComponent(new Redstone(this));
    private boolean exploded;

    protected TileEntityExplosive(BlockEntityType<? extends TileEntityExplosive> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.redstone.subscribe(this);
    }

    @Override
    public void onRedstoneChange(int n) {
        if (n > 0) {
            this.explode(null, false);
        }
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (StackUtil.consume(player, interactionHand, StackUtil.sameItem(Items.f_42613_), 1) || StackUtil.damage(player, interactionHand, StackUtil.sameItem(Items.f_42409_), 1)) {
            this.explode((LivingEntity)player, false);
            return InteractionResult.CONSUME;
        }
        return super.onActivated(player, interactionHand, direction, vec3);
    }

    @Override
    protected void onExploded(Explosion explosion) {
        super.onExploded(explosion);
        this.explode(explosion.m_46079_(), true);
    }

    @Override
    protected boolean onRemovedByPlayer(Player player, boolean bl) {
        if (this.explodeOnRemoval()) {
            this.explode((LivingEntity)player, false);
            return true;
        }
        return super.onRemovedByPlayer(player, bl);
    }

    @Override
    protected void onEntityCollision(Entity entity) {
        if (!this.m_58904_().f_46443_ && entity instanceof Projectile && entity.m_6060_()) {
            Projectile projectile = (Projectile)entity;
            Entity entity2 = projectile.m_37282_();
            this.explode(entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null, false);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        if (this.exploded) {
            return null;
        }
        return super.adjustDrop(itemStack, bl);
    }

    protected boolean explode(LivingEntity livingEntity, boolean bl) {
        ExplosiveEntity explosiveEntity = this.getEntity(livingEntity);
        if (explosiveEntity == null) {
            return false;
        }
        Level level = this.m_58904_();
        if (level.f_46443_) {
            return true;
        }
        explosiveEntity.setCausingEntity(livingEntity);
        this.onIgnite(livingEntity);
        level.m_7471_(this.f_58858_, false);
        if (bl) {
            explosiveEntity.setFuse(level.f_46441_.m_188503_(Math.max(1, explosiveEntity.getFuse() / 4)) + explosiveEntity.getFuse() / 8);
        }
        level.m_7967_((Entity)explosiveEntity);
        level.m_6263_((Player)null, explosiveEntity.m_20185_(), explosiveEntity.m_20186_(), explosiveEntity.m_20189_(), SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
        this.exploded = true;
        return true;
    }

    protected boolean explodeOnRemoval() {
        return false;
    }

    protected abstract ExplosiveEntity getEntity(LivingEntity var1);

    protected void onIgnite(LivingEntity livingEntity) {
    }
}

