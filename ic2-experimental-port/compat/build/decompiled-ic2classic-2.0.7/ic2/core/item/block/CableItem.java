/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.block;

import ic2.core.block.cables.CableBlock;
import ic2.core.block.cables.CableTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.tooltips.IAdvancedTooltip;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.Map;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CableItem
extends IC2BlockItem
implements ISimpleItemModel,
IAdvancedTooltip {
    String texture;
    BlockState state;
    int euTransfer = 0;
    double euLoss = 0.0;

    public CableItem(String name, BlockState state, String texture) {
        super(state.m_60734_(), name);
        BlockEntity tile;
        this.texture = texture;
        this.state = state;
        BlockEntity blockEntity = tile = state.m_155947_() ? ((EntityBlock)state.m_60734_()).m_142194_(BlockPos.f_121853_, state) : null;
        if (tile instanceof CableTileEntity) {
            CableTileEntity cable = (CableTileEntity)tile;
            cable.insulation = (byte)((CableBlock)state.m_60734_()).getInsulation(state);
            this.euTransfer = cable.getConductorBreakdownEnergy() - 1;
            this.euLoss = cable.getConductionLoss();
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (PlayerHandler.getHandler(player).hasEUReader()) {
            helper.addSimpleToolTip("tooltip.item.ic2.eu_reader.cable_limit", this.euTransfer);
            helper.addSimpleToolTip("tooltip.item.ic2.eu_reader.cable_loss", Formatters.CABLE_LOSS_FORMAT.format(this.euLoss));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "cable").get(this.texture);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        InteractionResult actionresulttype = this.m_40576_(new BlockPlaceContext(context));
        return actionresulttype != InteractionResult.SUCCESS && this.m_41472_() ? this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_() : actionresulttype;
    }

    public InteractionResult m_40576_(BlockPlaceContext context) {
        if (!context.m_7059_()) {
            return InteractionResult.FAIL;
        }
        if (!context.m_43725_().m_7731_(context.m_8083_(), (BlockState)this.state.m_61124_((Property)CableBlock.WATER, (Comparable)Boolean.valueOf(context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_)), 11)) {
            return InteractionResult.FAIL;
        }
        BlockPos blockpos = context.m_8083_();
        Level world = context.m_43725_();
        Player Player2 = context.m_43723_();
        ItemStack itemstack = context.m_43722_();
        BlockState blockstate1 = world.m_8055_(blockpos);
        Block block = blockstate1.m_60734_();
        if (block == this.state.m_60734_()) {
            BlockEntity tile;
            BlockItem.m_40582_((Level)world, (Player)Player2, (BlockPos)blockpos, (ItemStack)itemstack);
            block.m_6402_(world, blockpos, blockstate1, (LivingEntity)Player2, itemstack);
            if (Player2 instanceof ServerPlayer) {
                CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)Player2, blockpos, itemstack);
            }
            if ((tile = world.m_7702_(blockpos)) instanceof CableTileEntity && this.state.m_60734_() instanceof CableBlock) {
                CableBlock cableBlock = (CableBlock)this.state.m_60734_();
                CableTileEntity cable = (CableTileEntity)tile;
                cable.lock();
                if (cableBlock.hasInsulation()) {
                    cable.setInsulation((Integer)this.state.m_61143_((Property)cableBlock.getInsulation()));
                }
                cable.unlock();
            }
        }
        SoundType soundtype = blockstate1.getSoundType((LevelReader)world, blockpos, (Entity)context.m_43723_());
        world.m_5594_(Player2, blockpos, blockstate1.m_60827_().m_56777_(), SoundSource.BLOCKS, (soundtype.m_56773_() + 1.0f) / 2.0f, soundtype.m_56774_() * 0.8f);
        itemstack.m_41774_(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.m_220152_(group)) {
            items.add((Object)new ItemStack((ItemLike)this));
        }
    }

    public String m_5524_() {
        return this.m_41467_();
    }

    public void m_6192_(Map<Block, Item> blockToItemMap, Item itemIn) {
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
    }
}

