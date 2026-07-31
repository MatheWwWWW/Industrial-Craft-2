/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  ic2.api.crops.ICropModifier
 *  ic2.core.audio.AudioManager$SoundType
 *  ic2.core.item.tool.electric.ElectricWrenchTool
 *  ic2.core.platform.player.KeyHelper
 *  ic2.core.platform.registries.IC2Items
 *  ic2.core.platform.rendering.IC2Textures
 *  ic2.core.platform.rendering.features.item.IItemModel
 *  ic2.core.utils.helpers.StackUtil
 *  ic2.core.utils.tooltips.ToolTipHelper
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 *  org.jetbrains.annotations.Nullable
 */
package trinsdar.gravisuit.items.tools;

import com.google.common.collect.Multimap;
import ic2.api.crops.ICropModifier;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.tool.electric.ElectricWrenchTool;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.GravisuitLang;
import trinsdar.gravisuit.util.GravisuitSounds;
import trinsdar.gravisuit.util.Registry;
import trinsdar.gravisuit.util.RotationHelper;

public class ItemToolGravitool
extends ElectricWrenchTool
implements ICropModifier,
IItemModel {
    final ResourceLocation id;

    public ItemToolGravitool() {
        super("gravitool", null);
        this.tier = 2;
        this.losslessUses = -1;
        this.id = new ResourceLocation("gravisuit", "gravitool");
        Registry.REGISTRY.put(this.id, (Item)this);
    }

    public ResourceLocation getRegistryName() {
        return this.id;
    }

    public int getCapacity(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.GRAVITOOL_STORAGE;
    }

    public int getTransferLimit(ItemStack stack) {
        return GravisuitConfig.POWER_VALUES.GRAVITOOL_TRANSFER;
    }

    public boolean canOverrideLoss(ItemStack stack) {
        return true;
    }

    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            IC2.AUDIO.playSound(playerIn, GravisuitSounds.toolGraviToolSound, AudioManager.SoundType.ITEM, IC2.AUDIO.getDefaultVolume(), 1.0f);
            ItemStack stack = playerIn.m_21120_(handIn);
            CompoundTag nbt = stack.m_41784_();
            byte mode = nbt.m_128445_("mode");
            if (mode == 3) {
                nbt.m_128344_("mode", (byte)0);
                playerIn.m_5661_((Component)this.translate(GravisuitLang.messageWrench, new ChatFormatting[]{ChatFormatting.AQUA}), false);
            } else if (mode == 0) {
                nbt.m_128344_("mode", (byte)1);
                playerIn.m_5661_((Component)this.translate(GravisuitLang.messageHoe, new ChatFormatting[]{ChatFormatting.GOLD}), false);
            } else if (mode == 1) {
                nbt.m_128344_("mode", (byte)2);
                playerIn.m_5661_((Component)this.translate(GravisuitLang.messageTreetap, new ChatFormatting[]{ChatFormatting.DARK_GREEN}), false);
            } else {
                nbt.m_128344_("mode", (byte)3);
                playerIn.m_5661_((Component)this.translate(GravisuitLang.messageScrewdriver, new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE}), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        ItemStack stack = context.m_43722_();
        if (!IC2.KEYBOARD.isModeSwitchKeyDown(context.m_43723_())) {
            if (this.getMode(stack) == 2) {
                return IC2Items.ELECTRIC_TREETAP.m_6225_(context);
            }
            if (this.getMode(stack) == 1) {
                return IC2Items.ELECTRIC_HOE.m_6225_(context);
            }
        }
        return super.m_6225_(context);
    }

    public boolean m_6813_(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (this.getMode(stack) == 1) {
            return IC2Items.ELECTRIC_HOE.m_6813_(stack, level, state, pos, miningEntity);
        }
        return super.m_6813_(stack, level, state, pos, miningEntity);
    }

    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (this.getMode(stack) == 1) {
            boolean isHoe = state.m_204336_(BlockTags.f_144281_);
            return super.isCorrectToolForDrops(stack, state) || isHoe;
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    public float m_8102_(ItemStack stack, BlockState state) {
        return this.isCorrectToolForDrops(stack, state) ? Tiers.IRON.m_6624_() : super.m_8102_(stack, state);
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction)) {
            return this.getMode(stack) == 1;
        }
        return super.canPerformAction(stack, toolAction);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (this.getMode(stack) == 1) {
            return IC2Items.ELECTRIC_HOE.getAttributeModifiers(slot, stack);
        }
        return super.getAttributeModifiers(slot, stack);
    }

    public byte getMode(ItemStack stack) {
        CompoundTag nbt = stack.m_41784_();
        return nbt.m_128445_("mode");
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (this.getMode(stack) == 0) {
            return super.onItemUseFirst(stack, context);
        }
        if (this.getMode(stack) == 3) {
            return RotationHelper.rotateBlock(context.m_43725_(), context.m_8083_(), context.m_43723_() != null && context.m_43723_().m_6144_() != context.m_43719_().equals((Object)Direction.DOWN)) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this.getMode(stack) == 0) {
            helper.addSimpleToolTip(GravisuitLang.toolMode, new Object[]{Component.m_237115_((String)GravisuitLang.wrench)});
        } else if (this.getMode(stack) == 1) {
            helper.addSimpleToolTip(GravisuitLang.toolMode, new Object[]{Component.m_237115_((String)GravisuitLang.hoe)});
        } else if (this.getMode(stack) == 2) {
            helper.addSimpleToolTip(GravisuitLang.toolMode, new Object[]{Component.m_237115_((String)GravisuitLang.treetap)});
        } else if (this.getMode(stack) == 3) {
            helper.addSimpleToolTip(GravisuitLang.toolMode, new Object[]{Component.m_237115_((String)GravisuitLang.screwdriver)});
        }
        if (this.isImport(stack)) {
            helper.addSimpleToolTip("tooltip.item.ic2.electric_tree_tap.inv_import", new Object[0]);
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.hoe.seedmode", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, GravisuitLang.multiModes, new Object[0]));
    }

    public void onLossPrevented(Player player, ItemStack stack) {
    }

    public boolean hasBigCost(ItemStack stack) {
        return false;
    }

    public boolean isImport(ItemStack stack) {
        return StackUtil.getNbtData((ItemStack)stack).m_128471_("inv_import");
    }

    public boolean canChangeSeedMode(ItemStack itemStack) {
        return this.getMode(itemStack) == 1;
    }

    public boolean shouldRenderOverlay(ItemStack stack) {
        return this.getMode(stack) == 0;
    }

    public List<ItemStack> getModelTypes() {
        ObjectArrayList stacks = new ObjectArrayList();
        for (int i = 0; i < 4; ++i) {
            ItemStack stack = new ItemStack((ItemLike)this);
            stack.m_41784_().m_128344_("mode", (byte)i);
            stacks.add(stack);
        }
        return stacks;
    }

    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack itemStack) {
        Map textures = IC2Textures.getMappedEntriesItem((String)"gravisuit", (String)"tools/gravitool");
        byte mode = this.getMode(itemStack);
        return (TextureAtlasSprite)textures.get(mode == 0 ? "wrench" : (mode == 1 ? "hoe" : (mode == 2 ? "treetap" : "screwdriver")));
    }

    public int getModelIndexForStack(ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return this.getMode(itemStack) + 1;
    }
}

