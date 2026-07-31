/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.cauldron.CauldronInteraction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.Sheep
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.LayeredCauldronBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.blocks.PainterHelper;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PainterTool
extends IC2Item
implements ISimpleItemModel,
IToolModel {
    private DyeColor color;

    public PainterTool(DyeColor color) {
        super("painter_" + color.m_41065_(), new PropertiesBuilder().setNoRepair().maxStackSize(1).maxDamage(32).group(IC2.IC2_MAIN_GROUP));
        this.color = color;
        ColorMaps.PAINTER.putIfAbsent(color, this);
        CauldronInteraction.f_175607_.put(this, new Decauldren());
    }

    public PainterTool() {
        super("painter", new PropertiesBuilder().setNoRepair().maxStackSize(1).maxDamage(32).group(IC2.IC2_MAIN_GROUP));
        this.color = null;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public InteractionResult m_6880_(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (stack == null || this.color == null || stack.m_41720_() != this) {
            return InteractionResult.PASS;
        }
        if (target instanceof Sheep) {
            Sheep sheep = (Sheep)target;
            if (sheep.m_29874_() == this.color) {
                return InteractionResult.PASS;
            }
            sheep.m_29855_(this.color);
            PainterTool.damagePainter(stack, player, hand);
            return InteractionResult.SUCCESS;
        }
        return super.m_6880_(stack, player, target, hand);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.PLATFORM.isSimulating()) {
            PlayerHandler handler = PlayerHandler.getHandler(player);
            CompoundTag nbt = stack.m_41784_();
            if (player.m_6144_()) {
                byte range = (byte)(nbt.m_128445_("range") + 1);
                if (range > 2) {
                    range = 0;
                }
                nbt.m_128344_("range", range);
                player.m_5661_((Component)this.translate("info.item.ic2.painter.range", new Object[]{range * 2 + 1}), false);
                return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
            }
            if (handler.modeSwitchKeyDown) {
                boolean newValue = !nbt.m_128471_("autoRefill");
                nbt.m_128379_("autoRefill", newValue);
                player.m_5661_((Component)(newValue ? this.translate("info.item.ic2.painter.auto_refill_on") : this.translate("info.item.ic2.painter.auto_refill_off")), false);
                return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)stack);
            }
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)stack);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.m_43723_();
        InteractionHand hand = context.m_43724_();
        BlockPos pos = context.m_8083_();
        Level world = context.m_43725_();
        Direction facing = context.m_43719_();
        Vec3 position = this.getLocation(context);
        Box box = Box.withRange(pos, 0).expandSide(facing.m_122434_(), StackUtil.getNbtData(stack).m_128445_("range"));
        boolean didSomething = false;
        for (BlockPos block : box) {
            if (world.m_46859_(block) || !(PlayerHandler.getHandler((Player)player).altKeyDown ? this.colorBlock(world.m_8055_(block), world, block, position, null, this.color) || this.colorBlock(world.m_8055_(block), world, block, position, facing, this.color) : this.colorBlock(world.m_8055_(block), world, block, position, facing, this.color))) continue;
            didSomething = true;
            if (!IC2.PLATFORM.isSimulating() || PainterTool.damagePainter(stack, player, hand)) continue;
            break;
        }
        if (didSomething) {
            if (IC2.PLATFORM.isSimulating()) {
                IC2.AUDIO.playSound(player, new ResourceLocation(IC2.MODID, "sounds/tools/painter.ogg"), AudioManager.SoundType.ITEM, 4.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private Vec3 getLocation(UseOnContext context) {
        return context.m_43720_().m_82546_(Vec3.m_82528_((Vec3i)context.m_8083_()));
    }

    private boolean colorBlock(BlockState state, Level world, BlockPos pos, Vec3 exactPos, Direction side, DyeColor color) {
        if (color == null) {
            return false;
        }
        PainterHelper.IPaintable paintable = PainterHelper.INSTANCE.getPaintable(state);
        return paintable != null && paintable.recolor(state, world, pos, exactPos, side, color);
    }

    private static boolean damagePainter(ItemStack stack, Player player, InteractionHand hand) {
        if (stack.m_41773_() + 1 >= stack.m_41776_()) {
            IItemTransporter transporter = TransporterManager.getTransporter(player);
            if (transporter != null && !transporter.removeItem(SpecialFilters.createDyeFilter(((PainterTool)stack.m_41720_()).color), null, 1, false).m_41619_()) {
                stack.m_41721_(0);
                return true;
            }
            player.m_21008_(hand, new ItemStack((ItemLike)IC2Items.PAINTER));
            return false;
        }
        stack.m_41622_(1, (LivingEntity)player, PainterTool.get(hand));
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        if (this.color == null) {
            return IC2Textures.getMappedEntriesItemIC2("tools/painter").get("empty");
        }
        return IC2Textures.getMappedEntriesItemIC2("tools/painter").get(this.color.m_41065_());
    }

    public boolean isDamaged(ItemStack stack) {
        return this.getDamage(stack) > 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        helper.addSimpleToolTip(this.translate("tooltip.item.ic2.painter.range", new Object[]{nbt.m_128445_("range") * 2 + 1}));
        helper.addSimpleToolTip(this.translate("tooltip.item.ic2.painter.refill", new Object[]{nbt.m_128471_("autoRefill") ? "On" : "Off"}));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.painter.change_refill", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.SNEAK_KEY, KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.painter.change_range", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.painter.paint_full_block", new Object[0]));
    }

    public boolean m_8120_(ItemStack stack) {
        return false;
    }

    private static class Decauldren
    implements CauldronInteraction {
        private Decauldren() {
        }

        public InteractionResult m_175710_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
            PainterTool painter;
            Item item = stack.m_41720_();
            if (item instanceof PainterTool && (painter = (PainterTool)item).getColor() != null) {
                if (!level.m_5776_()) {
                    player.m_21008_(hand, new ItemStack((ItemLike)IC2Items.PAINTER));
                    LayeredCauldronBlock.m_153559_((BlockState)state, (Level)level, (BlockPos)pos);
                }
                return InteractionResult.m_19078_((boolean)level.m_5776_());
            }
            return InteractionResult.PASS;
        }
    }
}

