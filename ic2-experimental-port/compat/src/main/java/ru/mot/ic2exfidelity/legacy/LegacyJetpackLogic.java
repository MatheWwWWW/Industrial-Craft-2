package ru.mot.ic2exfidelity.legacy;

import ic2.core.IC2;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.util.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

/** IC2 2.8.222 jetpack movement, hover toggle and energy-consumption logic. */
public final class LegacyJetpackLogic {
    private LegacyJetpackLogic() {
    }

    public static void onArmorTick(Player player, ItemStack stack, IJetpack jetpack) {
        if (stack.m_41619_() || !jetpack.isJetpackActive(stack)) {
            return;
        }

        CompoundTag data = StackUtil.getOrCreateNbtData(stack);
        boolean hoverMode = data.m_128471_("hoverMode");
        byte toggleTimer = data.m_128445_("toggleTimer");
        boolean serverSide = !player.m_20193_().f_46443_;

        if (IC2.keyboard.isJumpKeyDown(player)
                && IC2.keyboard.isModeSwitchKeyDown(player)
                && toggleTimer == 0) {
            toggleTimer = 10;
            hoverMode = !hoverMode;
            if (serverSide) {
                data.m_128379_("hoverMode", hoverMode);
                IC2.sideProxy.messagePlayer(
                        player,
                        hoverMode ? "Hover Mode enabled." : "Hover Mode disabled.",
                        new Object[0]);
            }
        }

        boolean jetpackUsed = false;
        if (IC2.keyboard.isJumpKeyDown(player) || hoverMode) {
            jetpackUsed = useJetpack(player, hoverMode, jetpack, stack);
            if (player.m_20096_() && hoverMode && serverSide) {
                hoverMode = false;
                data.m_128379_("hoverMode", false);
                IC2.sideProxy.messagePlayer(player, "Hover Mode disabled.", new Object[0]);
            }
        }

        if (serverSide && toggleTimer > 0) {
            data.m_128344_("toggleTimer", (byte) (toggleTimer - 1));
        }
        if (serverSide && jetpackUsed) {
            player.f_36096_.m_38946_();
        }
    }

    public static boolean useJetpack(
            Player player, boolean hoverMode, IJetpack jetpack, ItemStack stack) {
        double chargeLevel = jetpack.getChargeLevel(stack);
        if (chargeLevel <= 0.0) {
            return false;
        }

        float power = jetpack.getPower(stack);
        float dropPercentage = jetpack.getDropPercentage(stack);
        if (chargeLevel <= dropPercentage) {
            power *= (float) (chargeLevel / dropPercentage);
        }

        if (IC2.keyboard.isForwardKeyDown(player)) {
            float retruster = hoverMode ? 1.0F : 0.15F;
            float forwardPower = power * retruster * 2.0F;
            if (forwardPower > 0.0F) {
                player.m_19920_(
                        0.02F,
                        new Vec3(0.0, 0.0, 0.4F * forwardPower));
            }
        }

        int worldHeight = player.m_20193_().m_151558_();
        int maxFlightHeight = (int) (worldHeight / jetpack.getWorldHeightDivisor(stack));
        double y = player.m_20186_();
        if (y > maxFlightHeight - 25) {
            y = Math.min(y, maxFlightHeight);
            power *= (float) ((maxFlightHeight - y) / 25.0);
        }

        Vec3 motion = player.m_20184_();
        double previousVertical = motion.f_82480_;
        double vertical = Math.min(previousVertical + power * 0.2F, 0.6);
        if (hoverMode) {
            float maxHoverY = 0.0F;
            if (IC2.keyboard.isJumpKeyDown(player)) {
                maxHoverY += jetpack.getHoverMultiplier(stack, true);
            }
            if (IC2.keyboard.isSneakKeyDown(player)) {
                maxHoverY -= jetpack.getHoverMultiplier(stack, false);
            }
            if (vertical > maxHoverY) {
                vertical = maxHoverY;
                if (previousVertical > vertical) {
                    vertical = previousVertical;
                }
            }
        }
        player.m_20256_(new Vec3(motion.f_82479_, vertical, motion.f_82481_));

        if (!player.m_20096_() && !player.m_20193_().f_46443_) {
            jetpack.drainEnergy(stack, hoverMode ? 1 : 2);
        }
        player.f_19789_ = 0.0F;
        return true;
    }
}
