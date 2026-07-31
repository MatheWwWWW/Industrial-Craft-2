/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 */
package ic2.core.block.personal;

import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.base.tiles.impls.BasePersonalTileEntity;
import ic2.core.block.base.tiles.impls.BaseTradeOMatTileEntity;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class PersonalBlock
extends BaseFacingBlock<BaseTileEntity> {
    public PersonalBlock(String blockName, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type) {
        this(blockName, provider, type, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60913_(-1.0f, 6000000.0f));
    }

    protected PersonalBlock(String blockName, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type, BlockBehaviour.Properties properties) {
        super(blockName, properties, provider, type);
    }

    @Override
    protected void setPlaceData(BaseTileEntity tile, BlockState state, LivingEntity placer, ItemStack stack) {
        if (tile instanceof BasePersonalTileEntity) {
            CompoundTag data;
            if (placer instanceof Player) {
                ((BasePersonalTileEntity)tile).setOwner(placer.m_20148_());
            }
            if (!(data = StackUtil.getNbtData(stack).m_128469_("tile_data")).m_128456_()) {
                ((BasePersonalTileEntity)tile).readItemData(data, true);
            }
        } else if (tile instanceof BaseTradeOMatTileEntity && placer instanceof Player) {
            ((BaseTradeOMatTileEntity)tile).setOwner(placer.m_20148_());
        }
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack stack, RandomSource rand, BlockEntity tile, boolean wrench) {
        ItemStack drop = new ItemStack((ItemLike)this);
        if (tile instanceof BasePersonalTileEntity) {
            drop.m_41700_("tile_data", (Tag)((BasePersonalTileEntity)tile).writeItemData(new CompoundTag()));
        }
        return drop;
    }
}

