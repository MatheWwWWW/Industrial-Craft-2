/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.color.block.BlockColors
 *  net.minecraft.client.renderer.block.BlockModelShaper
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.tool.electric;

import ic2.api.events.RetextureEvent;
import ic2.api.items.IFoamOverrider;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.renders.models.ObscuratorModel;
import ic2.core.networking.buffers.data.ObscuratorBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class ObscuratorTool
extends IC2ElectricItem
implements INetworkItemBufferEvent<ObscuratorBuffer>,
ICustomItemModel,
IItemColorListener,
IElectricEnchantable,
IFoamOverrider {
    public static final int COPY_COST = 10000;
    public static final int DRAW_COST = 1500;

    public ObscuratorTool() {
        super("obscurator");
        this.capacity = 100000;
        this.tier = 2;
        this.transferLimit = 250;
    }

    @Override
    protected int getEnergyCost(ItemStack stack) {
        return 100;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        RetextureEvent.TextureContainer container = ObscuratorTool.getContainer(stack);
        if (container != null) {
            int layer = this.getLayer(stack);
            helper.addStatTooltip("tooltip.item.ic2.obscurator.block", container.getState().m_60734_().m_49954_());
            helper.addStatTooltip("tooltip.item.ic2.obscurator.side", DirectionList.getName(container.getSide()));
            helper.addStatTooltip("tooltip.item.ic2.obscurator.layers", container.getRotations().length);
            helper.addStatTooltip("tooltip.item.ic2.obscurator.layer", layer);
            helper.addStatTooltip("tooltip.item.ic2.obscurator.rotation", container.getRotations()[layer].getRotation());
        }
        helper.addSimpleToolTip(this.translate("tooltip.item.ic2.painter.range", new Object[]{StackUtil.getNbtData(stack).m_128445_("range") * 2 + 1}));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.SNEAK_KEY, KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.obscurator.change.copy", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.obscurator.change.paste", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.obscurator.change.layer", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.obscurator.change.rotation", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.obscurator.change.paste_all_sides", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.SNEAK_KEY, KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.obscurator.change.range", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        RetextureEvent.TextureContainer container = ObscuratorTool.getContainer(stack);
        return container == null || container.getColors().length <= 0 ? -1 : container.getColors()[tintIndex % container.getColors().length];
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return 0;
    }

    @Override
    public List<ItemStack> getCustomTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack stack) {
        return new ObscuratorModel(IC2Textures.getMappedEntriesItemIC2("tools").get("obscurator"));
    }

    public int increaseLayer(ItemStack stack, int max) {
        CompoundTag nbt = stack.m_41784_();
        int result = (nbt.m_128451_("layer") + 1) % max;
        nbt.m_128344_("layer", (byte)result);
        return result;
    }

    public int getLayer(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("layer");
    }

    public void setLayer(ItemStack stack, int layer) {
        stack.m_41784_().m_128344_("layer", (byte)layer);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.f_46443_) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.KEYBOARD.isSneakKeyDown(playerIn)) {
                CompoundTag nbt = stack.m_41784_();
                byte range = nbt.m_128445_("range");
                if ((range = (byte)(range + 1)) > 2) {
                    range = 0;
                }
                nbt.m_128344_("range", range);
                playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.obscurator.range", new Object[]{range * 2 + 1}), false);
            } else if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                if (!stack.m_41784_().m_128441_("block")) {
                    return InteractionResultHolder.m_19100_((Object)playerIn.m_21120_(handIn));
                }
                RetextureEvent.TextureContainer container = ObscuratorTool.getContainer(stack);
                if (playerIn.m_6144_()) {
                    playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.obscurator.layer", new Object[]{this.increaseLayer(stack, container.getRotations().length)}), false);
                } else {
                    RetextureEvent.Rotation[] rotations = container.getRotations();
                    int layer = this.getLayer(stack);
                    if (layer >= rotations.length) {
                        layer = rotations.length - 1;
                        this.setLayer(stack, layer);
                    }
                    rotations[layer] = rotations[layer].getNext();
                    stack.m_41784_().m_128365_("block", (Tag)container.save());
                    playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.obscurator.rotation", new Object[]{rotations[layer].getRotation()}), false);
                }
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.m_43723_();
        if (player == null || IC2.KEYBOARD.isModeSwitchKeyDown(player)) {
            return InteractionResult.FAIL;
        }
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        if (player.m_6144_() && IC2.PLATFORM.isRendering()) {
            if (world.m_46859_(pos)) {
                return InteractionResult.FAIL;
            }
            if (!ElectricItem.MANAGER.canUse(context.m_43722_(), 10000)) {
                return InteractionResult.FAIL;
            }
            BlockState state = world.m_8055_(pos);
            if (state.m_60799_() != RenderShape.MODEL) {
                return InteractionResult.FAIL;
            }
            if (!this.isModelValid(state, context.m_43719_(), pos)) {
                return InteractionResult.FAIL;
            }
            int[] colors = this.getColors(world, pos, state, context.m_43719_());
            IC2.NETWORKING.get(false).sendClientItemBuffer(context.m_43722_(), "", new ObscuratorBuffer(state, context.m_43719_(), colors, context.m_43724_() != InteractionHand.OFF_HAND));
            return InteractionResult.SUCCESS;
        }
        if (!player.m_6144_()) {
            RetextureEvent.TextureContainer container = ObscuratorTool.getContainer(context.m_43722_());
            if (container == null) {
                return InteractionResult.PASS;
            }
            if (IC2.PLATFORM.isRendering()) {
                return InteractionResult.SUCCESS;
            }
            Direction facing = context.m_43719_();
            Box box = Box.withRange(pos, 0).expandSide(facing.m_122434_(), StackUtil.getNbtData(stack).m_128445_("range"));
            DirectionList sides = IC2.KEYBOARD.isAltKeyDown(player) ? DirectionList.ALL : DirectionList.ofFacing(facing);
            InteractionResult result = InteractionResult.FAIL;
            for (BlockPos position : box) {
                for (Direction side : sides) {
                    if (!ElectricItem.MANAGER.canUse(stack, 1500)) {
                        return result;
                    }
                    RetextureEvent event = new RetextureEvent((LevelAccessor)world, position, side, player, container);
                    MinecraftForge.EVENT_BUS.post((Event)event);
                    if (!event.isApplied()) continue;
                    player.m_36220_(IC2Stats.TEXTURES_STOLEN);
                    if (!player.m_7500_()) {
                        ElectricItem.MANAGER.use(stack, 1500, (LivingEntity)player);
                    }
                    result = InteractionResult.SUCCESS;
                }
            }
            return result;
        }
        return InteractionResult.PASS;
    }

    @OnlyIn(value=Dist.CLIENT)
    public int[] getColors(Level world, BlockPos position, BlockState state, Direction side) {
        List quads = Minecraft.m_91087_().m_91289_().m_110907_().m_110893_(state).getQuads(state, side, RandomSource.m_216335_((long)state.m_60726_(position)), ModelData.EMPTY, null);
        int[] colors = new int[quads.size()];
        BlockColors color = Minecraft.m_91087_().m_91298_();
        for (int i = 0; i < quads.size(); ++i) {
            BakedQuad quad = (BakedQuad)quads.get(i);
            colors[i] = quad.m_111304_() ? color.m_92577_(state, (BlockAndTintGetter)world, position, quad.m_111305_()) : -1;
        }
        return colors;
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean isModelValid(BlockState state, Direction side, BlockPos position) {
        BlockModelShaper shape = Minecraft.m_91087_().m_91289_().m_110907_();
        BakedModel model = shape.m_110893_(state);
        if (model.m_7521_() || model == shape.m_110881_().m_119409_()) {
            return false;
        }
        List quads = model.getQuads(state, side, RandomSource.m_216335_((long)state.m_60726_(position)), ModelData.EMPTY, null);
        return quads.size() > 0 && quads.size() <= 10;
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, ObscuratorBuffer buffer, Dist targetSide) {
        if (targetSide.isClient()) {
            return;
        }
        ItemStack target = buffer.getItem(player);
        if (stack.m_41619_() || !ItemStack.m_41728_((ItemStack)stack, (ItemStack)target)) {
            return;
        }
        stack = target;
        CompoundTag nbt = stack.m_41784_();
        if (nbt.m_128441_("block")) {
            RetextureEvent.TextureContainer container = buffer.createContainer();
            if (container.equals(new RetextureEvent.TextureContainer(nbt.m_128469_("block")))) {
                return;
            }
            nbt.m_128365_("block", (Tag)container.save());
            nbt.m_128344_("layer", (byte)0);
            return;
        }
        nbt.m_128365_("block", (Tag)buffer.createContainer().save());
        if (!player.m_7500_()) {
            ElectricItem.MANAGER.use(stack, 10000, (LivingEntity)player);
        }
    }

    public static RetextureEvent.TextureContainer getContainer(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        return nbt.m_128441_("block") ? new RetextureEvent.TextureContainer(nbt.m_128469_("block")) : null;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44962_ ? InteractionResult.FAIL : (ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }
}

