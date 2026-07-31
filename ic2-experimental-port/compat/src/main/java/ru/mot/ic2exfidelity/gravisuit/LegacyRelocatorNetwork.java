package ru.mot.ic2exfidelity.gravisuit;

import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/** Server-authoritative location-list actions for the relocator screens. */
public final class LegacyRelocatorNetwork {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(LegacyGravisuitMod.MOD_ID, "relocator"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
    private static boolean installed;

    private LegacyRelocatorNetwork() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        CHANNEL.registerMessage(0, Packet.class,
                Packet::encode, Packet::decode, Packet::handle);
        installed = true;
    }

    public static void add(InteractionHand hand, LegacyRelocatorData location) {
        CHANNEL.sendToServer(new Packet(Function.ADD_DESTINATION, hand, location));
    }

    public static void remove(InteractionHand hand, String name) {
        CHANNEL.sendToServer(new Packet(Function.REMOVE_DESTINATION, hand,
                new LegacyRelocatorData(0, "minecraft:overworld", name)));
    }

    public static void setDefault(InteractionHand hand, String name) {
        CHANNEL.sendToServer(new Packet(Function.ADD_DEFAULT, hand,
                new LegacyRelocatorData(0, "minecraft:overworld", name)));
    }

    public static void teleport(InteractionHand hand, String name) {
        CHANNEL.sendToServer(new Packet(Function.TELEPORT, hand,
                new LegacyRelocatorData(0, "minecraft:overworld", name)));
    }

    private enum Function {
        ADD_DESTINATION,
        REMOVE_DESTINATION,
        ADD_DEFAULT,
        TELEPORT
    }

    private record Packet(
            Function function, InteractionHand hand, LegacyRelocatorData location) {
        private static void encode(Packet packet, FriendlyByteBuf buffer) {
            buffer.m_130068_(packet.function);
            if (packet.function == Function.ADD_DESTINATION) {
                buffer.writeLong(packet.location.position());
                buffer.m_130070_(packet.location.dimension());
            }
            buffer.m_130070_(packet.location.name());
            buffer.m_130068_(packet.hand);
        }

        private static Packet decode(FriendlyByteBuf buffer) {
            Function function = buffer.m_130066_(Function.class);
            long position = function == Function.ADD_DESTINATION
                    ? buffer.readLong() : 0L;
            String dimension = function == Function.ADD_DESTINATION
                    ? buffer.m_130277_() : "minecraft:overworld";
            String name = buffer.m_130277_();
            InteractionHand hand = buffer.m_130066_(InteractionHand.class);
            return new Packet(function, hand,
                    new LegacyRelocatorData(position, dimension, name));
        }

        private static void handle(
                Packet packet,
                Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> apply(context.getSender(), packet));
            context.setPacketHandled(true);
        }

        private static void apply(ServerPlayer player, Packet packet) {
            if (player == null || packet.location.name().isEmpty()
                    || packet.location.name().length() > 32_500) {
                return;
            }
            ItemStack stack = player.m_21120_(packet.hand);
            if (!(stack.m_41720_() instanceof LegacyRelocatorItem relocator)) {
                return;
            }
            CompoundTag root = stack.m_41784_();
            CompoundTag locations = root.m_128469_("Locations");
            String name = packet.location.name();
            switch (packet.function) {
                case ADD_DESTINATION -> {
                    if (locations.m_128403_(name)) {
                        return;
                    }
                    if (locations.m_128440_() >= 11) {
                        player.m_5661_(Component.m_237115_(
                                "message.relocatorMaxTeleport")
                                .m_130940_(ChatFormatting.RED), false);
                        return;
                    }
                    locations.m_128365_(name, packet.location.write());
                    player.m_5661_(Component.m_237110_(
                            "message.relocatorAddTeleport",
                            Component.m_237113_(name)
                                    .m_130940_(ChatFormatting.YELLOW))
                            .m_130940_(ChatFormatting.GREEN), false);
                }
                case REMOVE_DESTINATION -> {
                    if (!locations.m_128403_(name)) {
                        return;
                    }
                    locations.m_128473_(name);
                    player.m_5661_(Component.m_237110_(
                            "message.relocatorRemoveTeleport",
                            Component.m_237113_(name)
                                    .m_130940_(ChatFormatting.YELLOW))
                            .m_130940_(ChatFormatting.GREEN), false);
                }
                case ADD_DEFAULT -> {
                    if (!locations.m_128403_(name)) {
                        return;
                    }
                    root.m_128359_("DefaultLocation", name);
                    player.m_5661_(Component.m_237110_(
                            "message.relocatorAddDefaultTeleport",
                            Component.m_237113_(name)
                                    .m_130940_(ChatFormatting.YELLOW))
                            .m_130940_(ChatFormatting.GREEN), false);
                }
                case TELEPORT -> {
                    if (!locations.m_128403_(name)) {
                        return;
                    }
                    relocator.teleportEntity(player,
                            LegacyRelocatorData.read(
                                    locations.m_128469_(name), name),
                            player.m_6374_(), stack);
                }
            }
            root.m_128365_("Locations", locations);
        }
    }
}
