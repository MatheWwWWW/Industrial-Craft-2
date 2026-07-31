package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/** Exact G-key anti-gravity engine state and 256/512 EU-per-tick flight drain. */
public final class LegacyGravisuitFlight {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(LegacyGravisuitMod.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals);
    private static final Map<UUID, Boolean> PREVIOUS_MAYFLY = new HashMap<>();
    private static LegacyGravisuitFlight instance;

    private LegacyGravisuitFlight() {
    }

    public static synchronized void install() {
        if (instance != null) {
            return;
        }
        CHANNEL.registerMessage(
                0,
                TogglePacket.class,
                TogglePacket::encode,
                TogglePacket::decode,
                TogglePacket::handle);
        instance = new LegacyGravisuitFlight();
        MinecraftForge.EVENT_BUS.register(instance);
    }

    public static void requestToggle() {
        CHANNEL.sendToServer(new TogglePacket());
    }

    public static boolean toggle(ServerPlayer player) {
        ItemStack chest = player.m_6844_(EquipmentSlot.CHEST);
        if (!(chest.m_41720_() instanceof LegacyGravisuitJetpack jetpack)
                || !jetpack.isGravitation()) {
            return false;
        }
        byte cooldown = chest.m_41784_().m_128445_("JetpackTicker");
        if (cooldown > 0) {
            return false;
        }
        boolean enabled = !jetpack.isGravitationEngineEnabled(chest);
        jetpack.setGravitationEngineEnabled(chest, enabled);
        chest.m_41784_().m_128344_("JetpackTicker", (byte) 10);
        player.m_5661_(Component.m_237115_(
                enabled ? "message.graviEngineOn" : "message.graviEngineOff"), false);
        if (!enabled) {
            revokeFlight(player);
        }
        return true;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START
                || event.player.m_20193_().f_46443_
                || !(event.player instanceof ServerPlayer player)) {
            return;
        }

        ItemStack chest = player.m_6844_(EquipmentSlot.CHEST);
        if (chest.m_41720_() instanceof LegacyGravisuitJetpack jetpack
                && jetpack.isGravitation()) {
            byte cooldown = chest.m_41784_().m_128445_("JetpackTicker");
            if (cooldown > 0) {
                chest.m_41784_().m_128344_("JetpackTicker", (byte) (cooldown - 1));
            }
            if (jetpack.isGravitationEngineEnabled(chest)
                    && ElectricItem.manager.getCharge(chest) >= 512.0) {
                grantFlight(player);
                if (!player.m_7500_() && !player.m_5833_()) {
                    int amount = player.m_150110_().f_35935_ ? 512 : 256;
                    if (!ElectricItem.manager.use(chest, amount, player)) {
                        ElectricItem.manager.discharge(
                                chest, amount, Integer.MAX_VALUE, true, false, false);
                    }
                }
                if (ElectricItem.manager.getCharge(chest) < 512.0) {
                    chest.m_41784_().m_128379_("ResetFlying", true);
                }
                return;
            }
        }
        revokeFlight(player);
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            revokeFlight(player);
        }
    }

    private static void grantFlight(ServerPlayer player) {
        if (player.m_7500_() || player.m_5833_()) {
            return;
        }
        UUID id = player.m_20148_();
        PREVIOUS_MAYFLY.putIfAbsent(id, player.m_150110_().f_35936_);
        if (!player.m_150110_().f_35936_) {
            player.m_150110_().f_35936_ = true;
            player.m_6885_();
        }
    }

    private static void revokeFlight(ServerPlayer player) {
        Boolean previous = PREVIOUS_MAYFLY.remove(player.m_20148_());
        if (previous == null || player.m_7500_() || player.m_5833_()) {
            return;
        }
        boolean changed = player.m_150110_().f_35936_ != previous;
        player.m_150110_().f_35936_ = previous;
        if (!previous && player.m_150110_().f_35935_) {
            player.m_150110_().f_35935_ = false;
            changed = true;
        }
        if (changed) {
            player.m_6885_();
        }
    }

    private static final class TogglePacket {
        private static void encode(TogglePacket packet, FriendlyByteBuf buffer) {
        }

        private static TogglePacket decode(FriendlyByteBuf buffer) {
            return new TogglePacket();
        }

        private static void handle(
                TogglePacket packet,
                Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender != null) {
                    toggle(sender);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
