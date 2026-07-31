package ru.mot.ic2exfidelity.legacy;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Original coordinate-list Dynamite-O-Mote behavior and NBT layout. */
public final class LegacyRemoteItem extends Item {
    private static final String COORDS = "coords";

    public LegacyRemoteItem(Properties properties) {
        super(properties.m_41487_(1));
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Level level = context.m_43725_();
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.m_8083_();
        BlockState state = level.m_8055_(pos);
        if (!state.m_60713_(RestoredLegacyContent.DYNAMITE_BLOCK.get())) {
            return InteractionResult.SUCCESS;
        }

        ItemStack remote = context.m_43722_();
        if (!state.m_61143_(LegacyDynamiteBlock.LINKED)) {
            addRemote(pos, remote);
            level.m_7731_(pos, state.m_61124_(LegacyDynamiteBlock.LINKED, Boolean.TRUE), 3);
        } else {
            int index = hasRemote(pos, remote);
            if (index >= 0) {
                level.m_7731_(pos, state.m_61124_(LegacyDynamiteBlock.LINKED, Boolean.FALSE), 3);
                removeRemote(index, remote);
            } else {
                Player player = context.m_43723_();
                if (player != null) {
                    player.m_5661_(Component.m_237110_("message.ic2.remote.not_linked"), false);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack remote = player.m_21120_(hand);
        if (!level.f_46443_) {
            level.m_5594_(
                    null,
                    player.m_20183_(),
                    RestoredLegacyContent.REMOTE_USE.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F);
            launchRemotes(level, remote, player);
        }
        return InteractionResultHolder.m_19092_(remote, level.f_46443_);
    }

    public static void addRemote(BlockPos pos, ItemStack remote) {
        ListTag coords = getCoords(remote, true);
        CompoundTag coordinate = new CompoundTag();
        coordinate.m_128405_("x", pos.m_123341_());
        coordinate.m_128405_("y", pos.m_123342_());
        coordinate.m_128405_("z", pos.m_123343_());
        coords.add(coordinate);
        remote.m_41764_(coords.size());
    }

    public static int hasRemote(BlockPos pos, ItemStack remote) {
        ListTag coords = getCoords(remote, false);
        if (coords == null) {
            return -1;
        }
        for (int index = 0; index < coords.size(); index++) {
            CompoundTag coordinate = coords.m_128728_(index);
            if (coordinate.m_128451_("x") == pos.m_123341_()
                    && coordinate.m_128451_("y") == pos.m_123342_()
                    && coordinate.m_128451_("z") == pos.m_123343_()) {
                return index;
            }
        }
        return -1;
    }

    public static void removeRemote(int index, ItemStack remote) {
        ListTag coords = getCoords(remote, false);
        if (coords == null) {
            return;
        }
        ListTag replacement = new ListTag();
        for (int current = 0; current < coords.size(); current++) {
            if (current != index) {
                replacement.add(coords.m_128728_(current).m_6426_());
            }
        }
        remote.m_41784_().m_128365_(COORDS, replacement);
        remote.m_41764_(replacement.size());
    }

    public static void launchRemotes(Level level, ItemStack remote, Player player) {
        ListTag coords = getCoords(remote, false);
        if (coords == null) {
            return;
        }
        int index = 0;
        while (index < coords.size()) {
            CompoundTag coordinate = coords.m_128728_(index);
            BlockPos pos = new BlockPos(
                    coordinate.m_128451_("x"),
                    coordinate.m_128451_("y"),
                    coordinate.m_128451_("z"));
            if (level.m_46805_(pos)) {
                BlockState state = level.m_8055_(pos);
                if (state.m_60713_(RestoredLegacyContent.DYNAMITE_BLOCK.get())
                        && state.m_61143_(LegacyDynamiteBlock.LINKED)) {
                    RestoredLegacyContent.DYNAMITE_BLOCK.get().ignite(level, pos, player, 40);
                }
                coords.remove(index);
            } else {
                index++;
            }
        }
        // The 1.12.2 implementation resets damage even if unloaded entries remain.
        remote.m_41764_(0);
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        int linked = stack.m_41773_();
        if (linked > 0) {
            tooltip.add(Component.m_237110_("tooltip.ic2.remote.linked", linked));
        }
    }

    private static ListTag getCoords(ItemStack remote, boolean create) {
        CompoundTag root = remote.m_41784_();
        if (!root.m_128425_(COORDS, 9)) {
            if (!create) {
                return null;
            }
            root.m_128365_(COORDS, new ListTag());
        }
        return root.m_128437_(COORDS, 10);
    }
}
