/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.core.block.base.IC2Block;
import ic2.core.block.misc.tiles.IridiumStoneTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IridiumStone
extends IC2Block
implements IBlockModel,
EntityBlock {
    public IridiumStone() {
        super("iridium_stone", BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60913_(-1.0f, 3600000.0f));
        this.setHarvestTool(Tool.PICKAXE.withLevel(2));
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("personal").get("iridium_stone");
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.IRIDIUM_STONE.m_155264_(pos, state);
    }

    public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!(placer instanceof Player)) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof IridiumStoneTileEntity) {
            ((IridiumStoneTileEntity)tile).setOwner(placer.m_20148_());
        }
    }

    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return false;
    }

    public float m_5880_(BlockState state, Player player, BlockGetter world, BlockPos pos) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IridiumStoneTileEntity) {
            IridiumStoneTileEntity iridium = (IridiumStoneTileEntity)tile;
            if (iridium.canDoAction(player.m_20148_(), Action.BREAK_IRIDIUM, false)) {
                return player.getDigSpeed(state, pos) / 240.0f;
            }
            return 0.0f;
        }
        return super.m_5880_(state, player, world, pos);
    }
}

