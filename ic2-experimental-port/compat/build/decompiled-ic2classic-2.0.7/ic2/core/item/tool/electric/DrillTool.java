/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IMiningDrill;
import ic2.api.items.electric.IScanner;
import ic2.core.IC2;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.item.base.ElectricToolItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.geometry.Box;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DrillTool
extends ElectricToolItem
implements ISimpleItemModel,
IMiningDrill {
    int cost;

    public DrillTool() {
        this("drill", 2, 50, 8.0f);
    }

    public DrillTool(String itemName, int level, int cost, float speed) {
        super(itemName, speed, 0.0f, -3.0f, new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP));
        this.registerTools(Tool.PICKAXE.withLevel(level), Tool.SHOVEL.withLevel(level));
        this.tier = 1;
        this.transferLimit = 100;
        this.capacity = 10000;
        this.cost = cost;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.diamond_drill.probe", new Object[0]));
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return this.cost;
    }

    @Override
    public boolean m_6813_(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (entityLiving instanceof Player) {
            ((Player)entityLiving).m_36220_(IC2Stats.BLOCKS_DRILLED);
        }
        return super.m_6813_(stack, worldIn, state, pos, entityLiving);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        IScanner scanner;
        ItemStack stack;
        if (handIn == InteractionHand.MAIN_HAND && playerIn.m_6144_() && (stack = playerIn.m_21206_()).m_41720_() instanceof IScanner && (scanner = (IScanner)stack.m_41720_()).hasScanEffect(stack)) {
            return InteractionResultHolder.m_19090_((Object)playerIn.m_21205_());
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        IScanner scanner;
        ItemStack offhand;
        Player player = context.m_43723_();
        if (player != null && player.m_6144_() && context.m_43724_() == InteractionHand.MAIN_HAND && (offhand = player.m_21206_()).m_41720_() instanceof IScanner && (scanner = (IScanner)offhand.m_41720_()).hasScanEffect(offhand)) {
            int radius = scanner.getScanRadius(offhand, true);
            if (radius > 0) {
                Level world = context.m_43725_();
                for (BlockPos pos : Box.fromPos(context.m_8083_()).expandSide(context.m_43719_().m_122434_(), 1).expand(context.m_43719_(), radius)) {
                    BlockState state = world.m_8055_(pos);
                    if (!scanner.isOreValuable(offhand, state, (LevelReader)world, pos)) continue;
                    BlockHighlighter.INSTANCE.addServerHighlight(player, pos.m_7949_(), 200, state.m_60767_().m_76339_().f_76396_ | 0x50000000);
                }
            }
            ElectricItem.MANAGER.chargeFromArmor(offhand, (LivingEntity)context.m_43723_());
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/drill").get("base");
    }

    @Override
    public boolean canMineBlock(ItemStack drill, BlockState state, LevelReader access, BlockPos pos) {
        return this.isCorrectToolForDrops(drill, state);
    }

    @Override
    public boolean canDrillBeUsed(ItemStack stack) {
        return ElectricItem.MANAGER.canUse(stack, this.cost);
    }

    @Override
    public void onDrillUsed(ItemStack stack) {
        ElectricItem.MANAGER.use(stack, this.cost, null);
    }

    @Override
    public int getMiningBoost(ItemStack stack, BlockState state) {
        int boost = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
        return boost == 0 ? 0 : boost * boost + 1;
    }

    @Override
    public int getExtraEnergyCost(ItemStack stack) {
        int eff = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
        int unb = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)stack);
        return (eff == 0 ? 0 : eff * eff + 1) - unb * (unb + unb);
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44987_ || ench == Enchantments.f_44984_ ? InteractionResult.SUCCESS : super.getEnchantmentCompatibility(stack, ench);
    }

    public static class RockCutterDrillTool
    extends DiamondDrillTool {
        public RockCutterDrillTool() {
            super("rock_cutter_drill", 3, 20, 60.0f);
        }

        @Override
        public float m_8102_(ItemStack stack, BlockState state) {
            return state.m_204336_(IC2Tags.ROCK_CUTTER_BLOCKS) ? super.m_8102_(stack, state) : 1.0f;
        }

        @Override
        public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
            helper.addSimpleToolTip(this.translate("tooltip.item.ic2.rock_cutter"));
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture() {
            return IC2Textures.getMappedEntriesItemIC2("tools/drill").get("jackhammer");
        }

        @Override
        public int getMiningBoost(ItemStack stack, BlockState state) {
            int boost = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
            return (state.m_204336_(IC2Tags.ROCK_CUTTER_BLOCKS) ? 100 : 3) + (boost == 0 ? 0 : boost * boost + 1);
        }

        @Override
        public int getExtraEnergyCost(ItemStack stack) {
            int eff = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
            int unb = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)stack);
            return 20 + (eff == 0 ? 0 : eff * eff + 1) - unb * (unb + unb);
        }
    }

    public static class DiamondDrillTool
    extends DrillTool {
        public static final String DIRT_MODE_TAG = "dirt_mode";

        public DiamondDrillTool() {
            super("diamond_drill", 3, 80, 16.0f);
        }

        public DiamondDrillTool(String itemName, int level, int cost, float speed) {
            super(itemName, level, cost, speed);
            this.cost = cost;
        }

        @Override
        public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
            CompoundTag nbt = StackUtil.getNbtData(stack);
            helper.addSimpleToolTip(this.translate(nbt.m_128471_(DIRT_MODE_TAG) ? "tooltip.item.ic2.diamond_drill.dirtModeOn" : "tooltip.item.ic2.diamond_drill.dirtModeOff"));
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, new Object[]{this.translate("tooltip.item.ic2.diamond_drill.toggleDirtMode")}));
            super.addToolTip(stack, player, type, helper);
        }

        @Override
        public float m_8102_(ItemStack stack, BlockState state) {
            CompoundTag nbt = StackUtil.getNbtData(stack);
            return super.m_8102_(stack, state) / (state.m_204336_(BlockTags.f_144274_) && nbt.m_128471_(DIRT_MODE_TAG) ? 8.0f : 1.0f);
        }

        @Override
        public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
            if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
                ItemStack stack = playerIn.m_21120_(handIn);
                CompoundTag nbt = stack.m_41784_();
                boolean result = !nbt.m_128471_(DIRT_MODE_TAG);
                nbt.m_128379_(DIRT_MODE_TAG, result);
                if (IC2.PLATFORM.isSimulating()) {
                    playerIn.m_5661_((Component)this.translate(result ? "tooltip.item.ic2.diamond_drill.dirtModeOn" : "tooltip.item.ic2.diamond_drill.dirtModeOff"), false);
                }
                return InteractionResultHolder.m_19090_((Object)stack);
            }
            return super.m_7203_(worldIn, playerIn, handIn);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture() {
            return IC2Textures.getMappedEntriesItemIC2("tools/drill").get("diamond");
        }

        @Override
        public int getMiningBoost(ItemStack stack, BlockState state) {
            int boost = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
            return 3 + (boost == 0 ? 0 : boost * boost + 1);
        }

        @Override
        public int getExtraEnergyCost(ItemStack stack) {
            int eff = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)stack);
            int unb = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)stack);
            return 14 + (eff == 0 ? 0 : eff * eff + 1) - unb * (unb + unb);
        }
    }
}

