/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.crops;

import ic2.api.crops.IFarmland;
import ic2.api.network.buffer.NetworkInfo;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.rendering.props.BlockProperty;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.registries.ForgeRegistries;

public class PlanterTileEntity
extends BaseTileEntity
implements IClickable {
    @NetworkInfo
    public Block block = Blocks.f_50016_;

    public PlanterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.clearNetworkFields();
        this.addNetworkFields("block");
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.block = NBTUtils.getForgeEntry(compound, "block", ForgeRegistries.BLOCKS, Blocks.f_50016_);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putForgeEntry(compound, "block", this.block, Blocks.f_50016_, ForgeRegistries.BLOCKS);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        Block block;
        if (side == Direction.UP && (block = Block.m_49814_((Item)player.m_21120_(hand).m_41720_())) != Blocks.f_50016_ && this.getLand(block) != null && block != IC2Blocks.PLANTER_POT) {
            if (this.setBlock(block)) {
                player.m_21120_(hand).m_41774_(1);
            }
            return true;
        }
        return false;
    }

    public IFarmland getLand(Block block) {
        IFarmland farmland = CropRegistry.INSTANCE.getFarmland(block);
        return farmland != null && !farmland.isSpecial() ? farmland : null;
    }

    public boolean setBlock(Block block) {
        if (this.block != block && block != null && block != Blocks.f_50016_) {
            if (this.block != Blocks.f_50016_) {
                Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_().m_7494_(), (ItemStack)new ItemStack((ItemLike)this.block));
            }
            this.block = block;
            this.updateTileField("block");
            return true;
        }
        return false;
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        if (fields.contains("block")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
        super.onNetworkFieldChanged(fields, player);
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        return ModelData.builder().with((ModelProperty)BlockProperty.INSTANCE, (Object)this.block).build();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PLANTER_POT;
    }
}

