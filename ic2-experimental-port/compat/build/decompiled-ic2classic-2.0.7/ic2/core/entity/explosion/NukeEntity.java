/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.entity.explosion;

import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.entity.explosion.IC2ExplosiveEntity;
import ic2.core.item.tool.WrenchTool;
import ic2.core.platform.registries.IC2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class NukeEntity
extends IC2ExplosiveEntity {
    public NukeEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public NukeEntity(EntityType<?> entityTypeIn, Level worldIn, double x, double y, double z) {
        super(entityTypeIn, worldIn, x, y, z, 300, (float)IC2.CONFIG.nukeDamage.get(), 0.05f, IC2Blocks.NUKE.m_49966_(), IC2DamageSource.NUKE);
    }

    public InteractionResult m_7111_(Player player, Vec3 vec, InteractionHand hand) {
        WrenchTool wrench;
        if (this.getState().m_60734_() == Blocks.f_50273_) {
            return InteractionResult.PASS;
        }
        ItemStack stack = player.m_21120_(hand);
        if (IC2.PLATFORM.isSimulating() && stack.m_41720_() instanceof WrenchTool && (wrench = (WrenchTool)stack.m_41720_()).canTakeDamage(stack, 1)) {
            wrench.damageWrench(stack, 1, player, hand);
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            Block.m_49840_((Level)this.f_19853_, (BlockPos)this.m_20183_(), (ItemStack)new ItemStack((ItemLike)IC2Blocks.NUKE));
            return InteractionResult.SUCCESS;
        }
        return super.m_7111_(player, vec, hand);
    }
}

