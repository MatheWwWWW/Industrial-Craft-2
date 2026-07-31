/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.tiles.teleporter.ITeleporterTarget;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.helpers.TeleportUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PortableTeleporter
extends IC2ElectricItem
implements ISimpleItemModel,
IElectricEnchantable {
    public PortableTeleporter() {
        super("portable_teleporter");
        this.capacity = 50000000;
        this.tier = 4;
        this.transferLimit = 25000;
    }

    @Override
    protected int getEnergyCost(ItemStack stack) {
        return 1000;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.portable_teleporter.target_set", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, "tooltip.item.ic2.portable_teleporter.target", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("portable_teleporter");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ITeleporterTarget tileTarget;
        BlockEntity tile;
        ItemStack stack;
        TeleporterTarget target;
        if (IC2.PLATFORM.isSimulating() && !playerIn.m_6144_() && (target = PortableTeleporter.getTarget(stack = playerIn.m_21120_(handIn))) != null && (tile = target.getTile()) instanceof ITeleporterTarget && (tileTarget = (ITeleporterTarget)tile).canReceive(ITeleporterTarget.TeleportType.ENTITY)) {
            this.teleportEntity(playerIn, target, tileTarget.getFacing(), stack);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockEntity tile;
        if (IC2.PLATFORM.isSimulating() && context.m_43723_() != null && context.m_43723_().m_6144_() && (tile = context.m_43725_().m_7702_(context.m_8083_())) instanceof ITeleporterTarget && ((ITeleporterTarget)tile).canReceive(ITeleporterTarget.TeleportType.ENTITY)) {
            PortableTeleporter.setTarget(stack, new TeleporterTarget(tile), this.getName(tile));
            context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.portable_teleporter.set_target"), true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    protected String getName(BlockEntity tile) {
        return tile instanceof Nameable && ((Nameable)tile).m_8077_() ? ((Nameable)tile).m_7770_().getString() : null;
    }

    public void teleportEntity(Player player, TeleporterTarget target, Direction dir, ItemStack stack) {
        int weight = TeleportUtil.getWeightOfEntity((Entity)player, true);
        if (weight == 0) {
            return;
        }
        ServerLevel server = target.getWorld();
        BlockPos pos = target.getTargetPosition();
        if (!ElectricItem.MANAGER.use(stack, (int)((double)weight * TeleportUtil.getDistanceCost(player.m_9236_(), player.m_20183_(), (Level)server, pos) * 5.0), (LivingEntity)player)) {
            return;
        }
        TeleportUtil.teleportEntity((Entity)player, server, pos, dir);
        IC2.AUDIO.playSound(player, new ResourceLocation("ic2", "sounds/machines/teleport.ogg"), AudioManager.SoundType.ITEM);
    }

    public static TeleporterTarget getTarget(ItemStack stack) {
        return TeleporterTarget.read(StackUtil.getNbtData(stack).m_128469_("target"));
    }

    public static void setTarget(ItemStack stack, TeleporterTarget target, String name) {
        if (target == null) {
            CompoundTag nbt = StackUtil.getNbtData(stack);
            nbt.m_128473_("target");
            nbt.m_128473_("targetName");
            return;
        }
        CompoundTag nbt = stack.m_41784_();
        nbt.m_128365_("target", (Tag)target.write(new CompoundTag()));
        if (name != null) {
            nbt.m_128359_("targetName", name);
            return;
        }
        nbt.m_128473_("targetName");
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : (ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }
}

