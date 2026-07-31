/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.base;

import ic2.core.IC2;
import ic2.core.block.base.IC2Block;
import ic2.core.block.misc.explosive.NukeBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.base.IC2Item;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class IC2ExplosiveBlock
extends IC2Block
implements IBlockModel {
    private String spriteBaseName;
    private String spritePath;
    private boolean isTriggeredByExplosion;

    public IC2ExplosiveBlock(String blockName, String spritePath, String spriteBaseName, boolean isTriggeredByExplosion) {
        super(blockName, BlockBehaviour.Properties.m_60939_((Material)Material.f_76273_).m_60918_(SoundType.f_56740_));
        this.spriteBaseName = spriteBaseName;
        this.spritePath = spritePath;
        this.isTriggeredByExplosion = isTriggeredByExplosion;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(handIn);
        if (stack.m_41720_() == Items.f_42409_) {
            if (IC2.PLATFORM.isRendering()) {
                return InteractionResult.SUCCESS;
            }
            worldIn.m_7967_(this.getExplosiveEntity(worldIn, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, state, false));
            worldIn.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
            worldIn.m_7471_(pos, false);
            stack.m_41622_(1, (LivingEntity)player, IC2Item.get(handIn));
            if (this instanceof NukeBlock) {
                player.m_36220_(IC2Stats.NUKES_IGNITED);
            }
            return InteractionResult.SUCCESS;
        }
        if (stack.m_41720_() == Items.f_42613_) {
            if (IC2.PLATFORM.isRendering()) {
                return InteractionResult.SUCCESS;
            }
            worldIn.m_7967_(this.getExplosiveEntity(worldIn, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, state, false));
            worldIn.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
            worldIn.m_7471_(pos, false);
            stack.m_41774_(1);
            if (this instanceof NukeBlock) {
                player.m_36220_(IC2Stats.NUKES_IGNITED);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void m_6807_(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (worldIn.m_46753_(pos) && IC2.PLATFORM.isSimulating()) {
            worldIn.m_7967_(this.getExplosiveEntity(worldIn, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, state, false));
            worldIn.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
            worldIn.m_7471_(pos, false);
        }
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.m_46753_(pos) && IC2.PLATFORM.isSimulating()) {
            worldIn.m_7967_(this.getExplosiveEntity(worldIn, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, state, false));
            worldIn.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
            worldIn.m_7471_(pos, false);
        }
    }

    public void m_7592_(Level worldIn, BlockPos pos, Explosion explosionIn) {
        if (IC2.PLATFORM.isSimulating() && this.isTriggeredByExplosion) {
            worldIn.m_7967_(this.getExplosiveEntity(worldIn, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, worldIn.m_8055_(pos), true));
            worldIn.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0f, 1.0f);
            worldIn.m_7471_(pos, false);
        }
    }

    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        this.m_7592_(level, pos, explosion);
        level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
    }

    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return !this.isTriggeredByExplosion;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("misc/explosive/" + this.spritePath).get(this.spriteBaseName + (side.m_122434_().m_122478_() ? (side.m_122421_() == Direction.AxisDirection.POSITIVE ? "top" : "bottom") : "sides"));
    }

    public abstract Entity getExplosiveEntity(Level var1, double var2, double var4, double var6, BlockState var8, boolean var9);
}

