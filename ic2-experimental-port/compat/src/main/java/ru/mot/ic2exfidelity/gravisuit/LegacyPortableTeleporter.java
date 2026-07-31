package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.core.block.machine.tileentity.TileEntityTeleporter;
import ic2.core.item.BaseElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Portable entity teleporter adapted to Experimental's teleporter tile. */
public final class LegacyPortableTeleporter extends BaseElectricItem {
    public LegacyPortableTeleporter(Item.Properties properties) {
        super(properties, 50_000_000.0, 25_000.0, 4);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.m_43723_();
        BlockEntity tile = context.m_43725_().m_7702_(context.m_8083_());
        if (!context.m_43725_().f_46443_
                && player != null && player.m_6144_()
                && tile instanceof TileEntityTeleporter) {
            CompoundTag target = new CompoundTag();
            target.m_128359_("id", context.m_43725_().m_46472_().m_135782_().toString());
            target.m_128356_("pos", context.m_8083_().m_121878_());
            stack.m_41784_().m_128365_("target", target);
            player.m_5661_(Component.m_237115_(
                    "tooltip.item.ic2.portable_teleporter.set_target"), true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (level.f_46443_ || player.m_6144_()) {
            return super.m_7203_(level, player, hand);
        }
        CompoundTag data = stack.m_41784_();
        if (!data.m_128441_("target")) {
            return super.m_7203_(level, player, hand);
        }
        LegacyRelocatorData target = LegacyRelocatorData.read(
                data.m_128469_("target"), "portable");
        ServerLevel destination = target.resolve(player.m_20194_());
        BlockPos position = target.blockPosition();
        if (destination == null
                || !(destination.m_7702_(position) instanceof TileEntityTeleporter)) {
            return super.m_7203_(level, player, hand);
        }
        int weight = LegacyTeleportUtil.getWeightOfEntity(player, true);
        int cost = (int) (weight * LegacyTeleportUtil.getDistanceCost(
                level, player.m_20183_(), destination, position) * 5.0);
        if (weight > 0 && ElectricItem.manager.use(stack, cost, player)) {
            LegacyTeleportUtil.teleportEntity(player, destination, position, Direction.UP);
            destination.m_5594_(null, position,
                    RestoredLegacyContent.RELOCATOR_TELEPORT.get(),
                    SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.m_19090_(stack);
        }
        return super.m_7203_(level, player, hand);
    }
}
