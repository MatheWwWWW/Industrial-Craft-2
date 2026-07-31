package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Complete three-mode GraviSuite relocator behavior. */
public final class LegacyRelocatorItem extends LegacyGravisuitElectricItem
        implements IHandHeldInventory {
    public static final int PORTAL_COST = 10_000_000;
    public static final int TRANSLOCATOR_ACTIVATION_REQUIREMENT = 500_000;

    public LegacyRelocatorItem(Item.Properties properties) {
        super(properties, 50_000_000.0, 25_000.0, 5);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        CompoundTag data = stack.m_41784_();
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (!level.f_46443_) {
                byte mode = data.m_128445_("mode");
                byte next = mode == 2 ? (byte) 0 : (byte) (mode + 1);
                data.m_128344_("mode", next);
                String key = next == 0 ? "message.relocatorPersonal"
                        : next == 1 ? "message.relocatorTranslocator"
                                : "message.relocatorPortal";
                ChatFormatting color = next == 0 ? ChatFormatting.GREEN
                        : next == 1 ? ChatFormatting.GOLD : ChatFormatting.AQUA;
                player.m_5661_(Component.m_237115_(key).m_130940_(color), false);
            }
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        if (!level.f_46443_) {
            byte mode = data.m_128445_("mode");
            if (player.m_6047_() || mode == 0) {
                getInventory(player, hand, stack).openManagedItem(player, hand, null);
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }
            CompoundTag locations = data.m_128469_("Locations");
            String defaultName = data.m_128461_("DefaultLocation");
            if (!defaultName.isEmpty() && locations.m_128403_(defaultName)) {
                boolean portal = mode == 2;
                int activationCost = portal
                        ? PORTAL_COST : TRANSLOCATOR_ACTIVATION_REQUIREMENT;
                if (ElectricItem.manager.canUse(stack, activationCost)) {
                    LegacyRelocatorData target = LegacyRelocatorData.read(
                            locations.m_128469_(defaultName), defaultName);
                    LegacyPlasmaBallEntity plasma = new LegacyPlasmaBallEntity(
                            level, player, target, hand);
                    level.m_7967_(plasma);
                    if (portal) {
                        ElectricItem.manager.use(stack, activationCost, player);
                    }
                } else {
                    player.m_213846_(Component.m_237115_(
                            "message.relocatorNotEnoughPower")
                            .m_130940_(ChatFormatting.RED));
                }
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
            }
        }
        return super.m_7203_(level, player, hand);
    }

    @Override
    public IHasGui getInventory(
            Player player, InteractionHand hand, ItemStack stack) {
        return new LegacyRelocatorInventory(player, hand, stack);
    }

    public boolean teleportEntity(
            LivingEntity entity, LegacyRelocatorData target,
            Direction direction, ItemStack stack) {
        return teleportEntity(entity, target, direction, stack, true);
    }

    boolean teleportEntity(
            LivingEntity entity, LegacyRelocatorData target,
            Direction direction, ItemStack stack, boolean notifyFailure) {
        ServerLevel targetLevel = target.resolve(entity.m_20194_());
        if (targetLevel == null) {
            return false;
        }
        int weight = LegacyTeleportUtil.getWeightOfEntity(entity, true);
        if (weight == 0) {
            return false;
        }
        int cost = (int) (weight * LegacyTeleportUtil.getDistanceCost(
                entity.m_9236_(), entity.m_20183_(),
                targetLevel, target.blockPosition()) * 5.0);
        if (!ElectricItem.manager.use(stack, cost, entity)) {
            if (notifyFailure && entity instanceof Player player) {
                player.m_213846_(Component.m_237115_(
                        "message.relocatorNotEnoughPower")
                        .m_130940_(ChatFormatting.RED));
            }
            return false;
        }
        LegacyTeleportUtil.teleportEntity(
                entity, targetLevel, target.blockPosition(), direction);
        if (notifyFailure) {
            targetLevel.m_5594_(null, target.blockPosition(),
                    RestoredLegacyContent.RELOCATOR_TELEPORT.get(),
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return true;
    }
}
