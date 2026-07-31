/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.ISteamReactorChamber;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.tiles.impls.BaseReactorChamberTileEntity;
import ic2.core.inventory.base.IHasGui;
import ic2.core.item.block.ReactorChamberItemBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorChamberBlock
extends IC2ContainerBlock
implements IBlockModel {
    BlockEntityType<? extends BlockEntity> tile;
    boolean steam;

    public ReactorChamberBlock(String blockName, boolean steam, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60978_(5.0f));
        this.steam = steam;
        this.tile = tile;
    }

    @Override
    public BlockItem createItem() {
        return new ReactorChamberItemBlock((Block)this, this::getReactor);
    }

    private Block getReactor() {
        return this.steam ? IC2Blocks.STEAM_REACTOR : IC2Blocks.NUCLEAR_REACTOR;
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.tile.m_155264_(pos, state);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.steam ? "electric/generator/steam_reactor_chamber" : "electric/generator/reactor_chamber").get(side.m_122433_());
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return this.isReactorNearby(context.m_43725_(), context.m_8083_()) ? super.m_5573_(context) : null;
    }

    @Override
    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        InteractionResult result = super.m_6227_(state, worldIn, pos, player, handIn, hit);
        if (result != InteractionResult.PASS) {
            return result;
        }
        if (player.m_6144_()) {
            return InteractionResult.PASS;
        }
        IReactor reactor = this.getReactor(worldIn, pos);
        if (reactor == null) {
            return InteractionResult.PASS;
        }
        return reactor instanceof IHasGui && (IC2.PLATFORM.isRendering() || IC2.PLATFORM.launchGui(player, handIn, hit.m_82434_(), (IHasGui)((Object)reactor))) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        int n;
        IReactor reactor = this.getReactor(worldIn, pos);
        if (reactor == null) {
            return;
        }
        int puffs = reactor.getHeat() / 1000;
        if (puffs <= 0) {
            return;
        }
        puffs = worldIn.f_46441_.m_188503_(puffs);
        for (n = 0; n < puffs; ++n) {
            worldIn.m_7106_((ParticleOptions)ParticleTypes.f_123762_, (double)((float)pos.m_123341_() + rand.m_188501_()), (double)((float)pos.m_123342_() + 0.95f), (double)((float)pos.m_123343_() + rand.m_188501_()), 0.0, 0.0, 0.0);
        }
        puffs -= rand.m_188503_(4) + 3;
        for (n = 0; n < puffs; ++n) {
            worldIn.m_7106_((ParticleOptions)ParticleTypes.f_123744_, (double)((float)pos.m_123341_() + rand.m_188501_()), (double)((float)pos.m_123342_() + 1.0f), (double)((float)pos.m_123343_() + rand.m_188501_()), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean m_7278_(BlockState state) {
        return true;
    }

    @Override
    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        if (this.isReactorNearby(worldIn, pos)) {
            super.m_6861_(state, worldIn, pos, blockIn, fromPos, isMoving);
            return;
        }
        Block.m_49840_((Level)worldIn, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)this));
        worldIn.m_7471_(pos, isMoving);
    }

    public boolean isReactorNearby(Level world, BlockPos pos) {
        int count = 0;
        for (Direction facing : DirectionList.ALL) {
            if (!this.isReactorAt(world, pos.m_121945_(facing))) continue;
            ++count;
        }
        return count == 1;
    }

    public boolean isReactorAt(Level world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (this.steam) {
            return tile instanceof ISteamReactor;
        }
        return tile instanceof IReactor && !(tile instanceof ISteamReactor);
    }

    public IReactor getReactor(Level world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (!(tile instanceof BaseReactorChamberTileEntity)) {
            return null;
        }
        BaseReactorChamberTileEntity base = (BaseReactorChamberTileEntity)tile;
        if (this.steam) {
            if (base instanceof ISteamReactorChamber) {
                return base.getReactor();
            }
            return null;
        }
        if (base instanceof ISteamReactorChamber) {
            return null;
        }
        return base.getReactor();
    }
}

