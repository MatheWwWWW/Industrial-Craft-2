/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package trinsdar.gravisuit.network;

import ic2.core.utils.helpers.StackUtil;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import trinsdar.gravisuit.items.tools.ItemRelocator;

public class PacketRelocator {
    private boolean dataB;
    private TeleportFunction function;
    private ItemRelocator.TeleportData location;
    private InteractionHand hand;

    public PacketRelocator(ItemRelocator.TeleportData location, TeleportFunction function, InteractionHand hand) {
        this.location = location;
        this.function = function;
        this.hand = hand;
    }

    public static void encode(PacketRelocator relocator, FriendlyByteBuf bytes) {
        bytes.m_130068_((Enum)relocator.function);
        if (relocator.function == TeleportFunction.ADDDESTINATION) {
            bytes.writeLong(relocator.location.getPos());
            bytes.m_130070_(relocator.location.getDimId());
            bytes.m_130070_(relocator.location.getName());
        } else {
            bytes.m_130070_(relocator.location.getName());
        }
        bytes.m_130068_((Enum)relocator.hand);
    }

    public static PacketRelocator decode(FriendlyByteBuf bytes) {
        TeleportFunction function = (TeleportFunction)bytes.m_130066_(TeleportFunction.class);
        ItemRelocator.TeleportData location = function == TeleportFunction.ADDDESTINATION ? new ItemRelocator.TeleportData(bytes.readLong(), bytes.m_130277_(), bytes.m_130277_()) : new ItemRelocator.TeleportData(bytes.m_130277_());
        InteractionHand hand = (InteractionHand)bytes.m_130066_(InteractionHand.class);
        return new PacketRelocator(location, function, hand);
    }

    public static InteractionHand boolToHand(boolean hand) {
        return hand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static boolean handToBool(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND;
    }

    public static void handle(PacketRelocator msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkEvent.Context context = (NetworkEvent.Context)ctx.get();
            ServerPlayer sender = context.getSender();
            if (sender != null) {
                CompoundTag entry;
                ItemStack teleporter = sender.m_21120_(msg.hand);
                if (teleporter.m_41619_()) {
                    return;
                }
                CompoundTag nbt = StackUtil.getNbtData((ItemStack)teleporter);
                CompoundTag map = nbt.m_128469_("Locations");
                if (msg.function == TeleportFunction.REMOVEDESTINATION && map.m_128441_(msg.location.getName())) {
                    map.m_128473_(msg.location.getName());
                }
                if (msg.function == TeleportFunction.ADDDEFAULT && map.m_128441_(msg.location.getName())) {
                    nbt.m_128359_("DefaultLocation", msg.location.getName());
                }
                if (msg.function == TeleportFunction.ADDDESTINATION) {
                    entry = msg.location.writeToNBT();
                    map.m_128365_(msg.location.getName(), (Tag)entry);
                }
                if (msg.function == TeleportFunction.TELEPORT && map.m_128441_(msg.location.getName())) {
                    entry = map.m_128469_(msg.location.getName());
                    ItemRelocator.teleportEntity((Player)sender, entry, teleporter);
                }
                nbt.m_128365_("Locations", (Tag)map);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static enum TeleportFunction {
        ADDDESTINATION,
        REMOVEDESTINATION,
        ADDDEFAULT,
        TELEPORT;

    }
}

