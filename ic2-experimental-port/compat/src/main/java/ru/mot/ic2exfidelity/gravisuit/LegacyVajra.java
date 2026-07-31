package ru.mot.ic2exfidelity.gravisuit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.ElectricItemTooltipHandler;
import ic2.core.item.tool.ItemElectricTool;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

/** Gravisuit 2.2 Vajra: 3333 EU operation, creative-speed mining and silk mode. */
public final class LegacyVajra extends ItemElectricTool {
    private static final int ENERGY_COST = 3_333;
    private static final float MINING_SPEED = 16_384.0F;

    public LegacyVajra(Item.Properties properties) {
        super(properties, ENERGY_COST, Tiers.NETHERITE, List.of(
                BlockTags.f_144280_,
                BlockTags.f_144281_,
                BlockTags.f_144282_,
                BlockTags.f_144283_));
        maxCharge = 3_000_000;
        transferLimit = 1_000;
        tier = 3;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (!level.f_46443_) {
                boolean silkTouch = !stack.m_41784_().m_128471_("silkTouch");
                stack.m_41784_().m_128379_("silkTouch", silkTouch);
                player.m_5661_(Component.m_237115_(
                        silkTouch ? "message.silkTouchOn" : "message.silkTouchOff"), false);
            }
            return InteractionResultHolder.m_19090_(stack);
        }
        return super.m_7203_(level, player, hand);
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        return ElectricItem.manager.canUse(stack, ENERGY_COST) ? MINING_SPEED : 1.0F;
    }

    @Override
    public boolean m_8096_(BlockState state) {
        return state.m_60767_() != Material.f_76296_
                && !state.m_60767_().m_76332_();
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        ItemStack stack = context.m_43722_();
        Level level = context.m_43725_();
        BlockPos position = context.m_8083_();
        Player player = context.m_43723_();
        BlockState state = level.m_8055_(position);
        boolean breakable = state.m_60767_() != Material.f_76296_
                && !state.m_60767_().m_76332_()
                && state.m_60800_((BlockGetter) level, position) >= 0.0F
                && TierSortingRegistry.isCorrectTierForDrops(Tiers.NETHERITE, state);
        if (!level.f_46443_
                && breakable
                && ElectricItem.manager.use(stack, ENERGY_COST, player)
                && destroyBlock(level, position, true, player, stack)) {
            return InteractionResult.SUCCESS;
        }
        return breakable ? InteractionResult.m_19078_(level.f_46443_) : InteractionResult.PASS;
    }

    public static boolean destroyBlock(
            Level level,
            BlockPos position,
            boolean dropBlock,
            Entity entity,
            ItemStack tool) {
        BlockState state = level.m_8055_(position);
        if (state.m_60795_()) {
            return false;
        }
        FluidState fluid = level.m_6425_(position);
        if (!(state.m_60734_() instanceof BaseFireBlock)) {
            level.m_46796_(2001, position, Block.m_49956_(state));
        }
        BlockEntity blockEntity = state.m_155947_() ? level.m_7702_(position) : null;
        Player player = entity instanceof Player ? (Player) entity : null;
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(
                level, position, state, player);
        MinecraftForge.EVENT_BUS.post((Event) event);
        if (event.isCanceled()) {
            return false;
        }

        CompoundTag tag = tool.m_41783_();
        if (dropBlock) {
            Map enchantments = EnchantmentHelper.m_44831_(tool);
            boolean silkTouch = tag != null && tag.m_128471_("silkTouch");
            if (silkTouch) {
                enchantments.put(Enchantments.f_44985_, 1);
                EnchantmentHelper.m_44865_(enchantments, tool);
            }
            Block.m_49881_(state, level, position, blockEntity, entity, tool);
            if (silkTouch) {
                enchantments.remove(Enchantments.f_44985_);
                EnchantmentHelper.m_44865_(enchantments, tool);
            }
        }

        boolean removed = level.m_6933_(position, fluid.m_76188_(), 3, 512);
        if (removed) {
            level.m_220407_(GameEvent.f_157794_, position,
                    GameEvent.Context.m_223719_(entity, state));
        }
        return removed;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
            EquipmentSlot slot, ItemStack stack) {
        HashMultimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            double damage = ElectricItem.manager.getCharge(stack) >= ENERGY_COST * 2.0
                    ? 25.0 : 3.0;
            modifiers.put(
                    Attributes.f_22281_,
                    new AttributeModifier(
                            f_41374_,
                            damage > 3.0 ? "Vajra Powered Damage" : "Vajra Unpowered Damage",
                            damage,
                            AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ElectricItem.manager.use(stack, ENERGY_COST * 2.0, attacker);
        return true;
    }

    @Override
    public void m_7373_(
            ItemStack stack,
            Level level,
            List<Component> tooltip,
            TooltipFlag flag) {
        ElectricItemTooltipHandler.addTooltip(stack, tooltip);
        tooltip.add(Component.m_237110_(
                "item_info.silkMode",
                Component.m_237115_(stack.m_41784_().m_128471_("silkTouch")
                        ? "item_info.vajraSilktouchOn"
                        : "item_info.vajraSilktouchOff")));
    }
}
