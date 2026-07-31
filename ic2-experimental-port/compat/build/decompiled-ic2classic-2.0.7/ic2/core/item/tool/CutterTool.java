/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.energy.tile.IEnergyConductorModifiable;
import ic2.api.items.ICutterItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CutterTool
extends IC2Item
implements ICutterItem,
ISimpleItemModel {
    public static ResourceLocation AUDIO_FILE = new ResourceLocation("ic2", "sounds/tools/cutter.ogg");

    public CutterTool() {
        super("cutter", new PropertiesBuilder().maxDamage(128).group(IC2.IC2_MAIN_GROUP).setNoRepair());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_LEFT_CLICK, "tooltip.item.ic2.cutter.cut", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.cutter.add", new Object[0]));
    }

    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack copy = itemStack.m_41777_();
        return copy.m_220157_(1, IC2Item.RANDOM, null) ? ItemStack.f_41583_ : copy;
    }

    @Override
    public void cutInsulation(Player player, ItemStack stack, Level world, BlockPos pos) {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof IEnergyConductorModifiable && ((IEnergyConductorModifiable)tile).tryRemoveInsulation()) {
            stack.m_41622_(1, (LivingEntity)player, CutterTool.get(InteractionHand.MAIN_HAND));
            IC2.AUDIO.playSound(player, AUDIO_FILE, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            Block.m_49840_((Level)world, (BlockPos)pos, (ItemStack)new ItemStack((ItemLike)IC2Items.RUBBER));
        }
    }

    public InteractionResult m_6225_(UseOnContext context) {
        IEnergyConductorModifiable cable;
        BlockEntity tile = context.m_43725_().m_7702_(context.m_8083_());
        if (tile instanceof IEnergyConductorModifiable && (cable = (IEnergyConductorModifiable)tile).tryAddInsulation()) {
            IItemTransporter trans = TransporterManager.getTransporter(context.m_43723_());
            if (trans != null && !trans.removeItem(new SimpleFilter((ItemLike)IC2Items.RUBBER), Direction.DOWN, 1, false).m_41619_()) {
                if (IC2.PLATFORM.isSimulating()) {
                    context.m_43722_().m_41622_(1, (LivingEntity)context.m_43723_(), CutterTool.get(context.m_43724_()));
                }
                return InteractionResult.SUCCESS;
            }
            cable.tryRemoveInsulation();
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("insulation_cutter");
    }
}

