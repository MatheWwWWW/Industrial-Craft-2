/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.damagesource.IndirectEntityDamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.SmeltingRecipe
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Explosion$BlockInteraction
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.StainedGlassBlock
 *  net.minecraft.world.level.block.StainedGlassPaneBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 */
package ic2.core.entity;

import ic2.core.IC2;
import ic2.core.Ic2Explosion;
import ic2.core.Ic2Player;
import ic2.core.ref.Ic2Entities;
import ic2.core.util.StackUtil;
import ic2.core.util.Vector3;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LaserBulletEntity
extends ThrowableProjectile {
    public static final double laserSpeed = 1.0;
    public LivingEntity owner;
    public boolean isSmeltMode = false;
    public boolean removeBlock = false;
    public float range = 0.0f;
    public float power = 0.0f;
    public int blockBreaks = 0;
    public boolean isExplosiveMode = false;

    public LaserBulletEntity(Level level) {
        super(Ic2Entities.LASER_BULLET, level);
    }

    public LaserBulletEntity(EntityType<? extends LaserBulletEntity> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBulletEntity(Level level, LivingEntity livingEntity) {
        super(Ic2Entities.LASER_BULLET, livingEntity, level);
    }

    public LaserBulletEntity(Level level, Vector3 vector3, Vector3 vector32, LivingEntity livingEntity, float f, float f2, int n, boolean bl) {
        this(level, livingEntity);
        this.owner = livingEntity;
        this.m_20248_(vector3.x, vector3.y, vector3.z);
        this.range = f;
        this.power = f2;
        this.blockBreaks = n;
        this.isExplosiveMode = bl;
    }

    protected float m_7139_() {
        return 0.0f;
    }

    protected void m_8097_() {
    }

    public void m_8119_() {
        super.m_8119_();
        if (IC2.sideProxy.isSimulating() && (this.range < 1.0f || this.power <= 0.0f || this.blockBreaks <= 0)) {
            if (this.isExplosiveMode) {
                this.explode();
            }
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return;
        }
        this.power -= 0.5f;
    }

    protected void m_8060_(BlockHitResult blockHitResult) {
        super.m_8060_(blockHitResult);
        this.handleHit((HitResult)blockHitResult);
    }

    protected void m_5790_(EntityHitResult entityHitResult) {
        super.m_5790_(entityHitResult);
        this.handleHit((HitResult)entityHitResult);
    }

    protected void handleHit(HitResult hitResult) {
        if (this.isExplosiveMode) {
            this.explode();
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return;
        }
        switch (hitResult.m_6662_()) {
            case ENTITY: {
                if (this.hitEntity(((EntityHitResult)hitResult).m_82443_())) {
                    this.power -= 0.5f;
                    break;
                }
                this.m_142687_(Entity.RemovalReason.DISCARDED);
                break;
            }
            case BLOCK: {
                assert (hitResult instanceof BlockHitResult);
                BlockHitResult blockHitResult = (BlockHitResult)hitResult;
                if (!this.hitBlock(blockHitResult.m_82425_(), blockHitResult.m_82434_())) {
                    this.power -= 0.5f;
                    break;
                }
                this.m_142687_(Entity.RemovalReason.DISCARDED);
                break;
            }
            default: {
                throw new RuntimeException("invalid hit type: " + hitResult.m_6662_());
            }
        }
    }

    private void explode() {
        Level level = this.m_20193_();
        Ic2Explosion ic2Explosion = new Ic2Explosion(level, (Entity)this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 5.0f, 0.85f);
        ic2Explosion.doExplosion();
    }

    private boolean hitEntity(Entity entity) {
        int n = (int)this.power;
        if (n > 0) {
            entity.m_20254_(n * (this.isSmeltMode ? 2 : 1));
            return entity.m_6469_(new IndirectEntityDamageSource("laser", (Entity)this, (Entity)this.owner).m_19366_(), (float)n);
        }
        return true;
    }

    private boolean hitBlock(BlockPos blockPos, Direction direction) {
        Player player;
        Level level = this.m_20193_();
        Player player2 = player = this.owner instanceof Player ? (Player)this.owner : Ic2Player.get(level);
        if (player == null) {
            return false;
        }
        if (player.m_36187_(level, blockPos, Objects.requireNonNull(player.m_20194_()).m_130008_())) {
            return false;
        }
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        boolean bl = true;
        if (level.m_8055_(blockPos).m_60795_() || block == Blocks.f_50058_ || block == Blocks.f_50185_ || block instanceof StainedGlassPaneBlock || block instanceof StainedGlassBlock) {
            return false;
        }
        if (level.f_46443_) {
            return true;
        }
        float f = blockState.m_60800_((BlockGetter)level, blockPos);
        if (f < 0.0f) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            return true;
        }
        this.power -= f / 1.5f;
        if (this.power < 0.0f) {
            return true;
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (blockState.m_60767_() == Material.f_76273_) {
            block.m_7592_(level, blockPos, new Explosion(level, (Entity)this, (double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5, 1.0f, false, Explosion.BlockInteraction.BREAK));
        } else if (this.isSmeltMode) {
            if (blockState.m_60767_() == Material.f_76320_) {
                bl = false;
            } else {
                for (ItemStack itemStack : StackUtil.getDrops((BlockGetter)level, blockPos, blockState, block, 0)) {
                    this.appendSmeltItemStack(block, itemStack, arrayList);
                }
                bl = arrayList.isEmpty();
            }
        }
        if (this.removeBlock) {
            if (bl) {
                Block.m_49950_((BlockState)blockState, (Level)level, (BlockPos)blockPos);
            }
            level.m_7471_(blockPos, false);
            for (ItemStack itemStack : arrayList) {
                if (!StackUtil.placeBlock(itemStack, level, blockPos)) {
                    StackUtil.dropAsEntity(level, blockPos, itemStack);
                }
                this.power = 0.0f;
            }
            if (level.f_46441_.m_188503_(10) == 0 && blockState.m_60767_().m_76335_()) {
                level.m_46597_(blockPos, Blocks.f_50083_.m_49966_());
            }
        }
        --this.blockBreaks;
        return true;
    }

    private void appendSmeltItemStack(Block block, ItemStack itemStack, List<ItemStack> list) {
        SmeltingRecipe smeltingRecipe;
        if (itemStack.m_41720_() instanceof BlockItem && ((BlockItem)itemStack.m_41720_()).m_40614_() != block) {
            itemStack = new ItemStack((ItemLike)block.m_5456_());
        }
        if ((smeltingRecipe = (SmeltingRecipe)IC2.sideProxy.getRecipeManager().m_44015_(RecipeType.f_44108_, (Container)new SimpleContainer(new ItemStack[]{itemStack}), null).orElse(null)) == null) {
            return;
        }
        ItemStack itemStack2 = smeltingRecipe.m_8043_();
        if (!StackUtil.isEmpty(itemStack2)) {
            list.add(itemStack2);
        }
    }

    public void init(LivingEntity livingEntity, float f, float f2, int n, boolean bl, boolean bl2, boolean bl3) {
        this.owner = livingEntity;
        this.range = f;
        this.power = f2;
        this.blockBreaks = n;
        this.removeBlock = bl3;
        this.isExplosiveMode = bl;
        this.isSmeltMode = bl2;
    }
}

