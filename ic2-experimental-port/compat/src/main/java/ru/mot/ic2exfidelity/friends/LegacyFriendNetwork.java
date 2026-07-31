package ru.mot.ic2exfidelity.friends;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;

/** Server-authoritative friend edits and client snapshots. */
public final class LegacyFriendNetwork {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Ic2ExperimentalFidelity.MOD_ID, "friends"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
    private static boolean installed;

    private LegacyFriendNetwork() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        CHANNEL.registerMessage(0, OpenRequest.class,
                OpenRequest::encode, OpenRequest::decode, OpenRequest::handle);
        CHANNEL.registerMessage(1, Snapshot.class,
                Snapshot::encode, Snapshot::decode, Snapshot::handle);
        CHANNEL.registerMessage(2, Update.class,
                Update::encode, Update::decode, Update::handle);
        installed = true;
    }

    public static void open() {
        CHANNEL.sendToServer(new OpenRequest());
    }

    public static void update(
            UUID friend, String name, boolean enabled, boolean breakIridium) {
        CHANNEL.sendToServer(new Update(friend, name, enabled, breakIridium));
    }

    private static void sendSnapshot(ServerPlayer player) {
        List<LegacyFriendManager.FriendEntry> entries =
                LegacyFriendManager.get(player.m_20194_())
                        .getFriends(player.m_20148_());
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new Snapshot(entries));
    }

    private record OpenRequest() {
        private static void encode(OpenRequest packet, FriendlyByteBuf buffer) {
        }

        private static OpenRequest decode(FriendlyByteBuf buffer) {
            return new OpenRequest();
        }

        private static void handle(
                OpenRequest packet,
                Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player == null) {
                    return;
                }
                NetworkHooks.openScreen(player, new SimpleMenuProvider(
                        (syncId, inventory, ignored) ->
                                new LegacyFriendsMenu(syncId, inventory),
                        Component.m_237115_("gui.ic2.friends")));
                sendSnapshot(player);
            });
            context.setPacketHandled(true);
        }
    }

    private record Snapshot(List<LegacyFriendManager.FriendEntry> entries) {
        private static void encode(Snapshot packet, FriendlyByteBuf buffer) {
            buffer.m_130130_(packet.entries.size());
            for (LegacyFriendManager.FriendEntry entry : packet.entries) {
                buffer.m_130077_(entry.id());
                buffer.m_130070_(entry.name());
                buffer.writeBoolean(entry.canBreakIridium());
            }
        }

        private static Snapshot decode(FriendlyByteBuf buffer) {
            int count = Math.min(buffer.m_130242_(), 1_024);
            List<LegacyFriendManager.FriendEntry> entries = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                UUID id = buffer.m_130259_();
                String name = buffer.m_130136_(64);
                boolean breakIridium = buffer.readBoolean();
                LinkedHashSet<String> actions = new LinkedHashSet<>();
                if (breakIridium) {
                    actions.add(LegacyFriendManager.BREAK_IRIDIUM);
                }
                entries.add(new LegacyFriendManager.FriendEntry(id, name, actions));
            }
            return new Snapshot(entries);
        }

        private static void handle(
                Snapshot packet,
                Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> LegacyFriendClientState.replace(packet.entries));
            context.setPacketHandled(true);
        }
    }

    private record Update(
            UUID friend, String name,
            boolean enabled, boolean breakIridium) {
        private static void encode(Update packet, FriendlyByteBuf buffer) {
            buffer.m_130077_(packet.friend);
            buffer.m_130070_(packet.name);
            buffer.writeBoolean(packet.enabled);
            buffer.writeBoolean(packet.breakIridium);
        }

        private static Update decode(FriendlyByteBuf buffer) {
            return new Update(buffer.m_130259_(), buffer.m_130136_(64),
                    buffer.readBoolean(), buffer.readBoolean());
        }

        private static void handle(
                Update packet,
                Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player == null || packet.name.length() > 64) {
                    return;
                }
                LegacyFriendManager.get(player.m_20194_()).update(
                        player.m_20148_(), packet.friend, packet.name,
                        packet.enabled, packet.breakIridium);
                sendSnapshot(player);
            });
            context.setPacketHandled(true);
        }
    }
}
