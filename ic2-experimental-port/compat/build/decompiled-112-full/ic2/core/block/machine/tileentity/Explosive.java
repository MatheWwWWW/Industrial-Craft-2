/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.SoundType
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.init.Items
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package ic2.core.block.machine.tileentity;

import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Redstone;
import ic2.core.util.StackUtil;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public abstract class Explosive
extends TileEntityInventory
implements Redstone.IRedstoneChangeHandler {
    protected final Redstone redstone = this.addComponent(new Redstone(this));
    private boolean exploded;

    protected Explosive() {
        this.redstone.subscribe(this);
    }

    @Override
    protected SoundType getBlockSound(Entity entity) {
        return SoundType.field_185850_c;
    }

    @Override
    public void onRedstoneChange(int newLevel) {
        if (newLevel > 0) {
            this.explode(null, false);
        }
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (StackUtil.consume(player, hand, StackUtil.sameItem(Items.field_151059_bz), 1) || StackUtil.damage(player, hand, StackUtil.sameItem(Items.field_151033_d), 1)) {
            this.explode((EntityLivingBase)player, false);
            return true;
        }
        return super.onActivated(player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    protected void onExploded(Explosion explosion) {
        super.onExploded(explosion);
        this.explode(explosion.func_94613_c(), true);
    }

    @Override
    protected boolean onRemovedByPlayer(EntityPlayer player, boolean willHarvest) {
        if (this.explodeOnRemoval()) {
            this.explode((EntityLivingBase)player, false);
            return true;
        }
        return super.onRemovedByPlayer(player, willHarvest);
    }

    @Override
    protected void onEntityCollision(Entity entity) {
        if (!this.func_145831_w().field_72995_K && entity instanceof EntityArrow && entity.func_70027_ad()) {
            EntityArrow arrow = (EntityArrow)entity;
            this.explode(arrow.field_70250_c instanceof EntityLivingBase ? (EntityLivingBase)arrow.field_70250_c : null, false);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
        if (this.exploded) {
            return null;
        }
        return super.adjustDrop(drop, wrench);
    }

    protected boolean explode(EntityLivingBase igniter, boolean shortFuse) {
        EntityIC2Explosive entity = this.getEntity(igniter);
        if (entity == null) {
            return false;
        }
        World world = this.func_145831_w();
        if (world.field_72995_K) {
            return true;
        }
        entity.setIgniter(igniter);
        this.onIgnite(igniter);
        world.func_175698_g(this.field_174879_c);
        if (shortFuse) {
            entity.fuse = world.field_73012_v.nextInt(Math.max(1, entity.fuse / 4)) + entity.fuse / 8;
        }
        world.func_72838_d((Entity)entity);
        world.func_184148_a((EntityPlayer)null, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0f, 1.0f);
        this.exploded = true;
        return true;
    }

    protected boolean explodeOnRemoval() {
        return false;
    }

    protected abstract EntityIC2Explosive getEntity(EntityLivingBase var1);

    protected void onIgnite(EntityLivingBase igniter) {
    }
}

