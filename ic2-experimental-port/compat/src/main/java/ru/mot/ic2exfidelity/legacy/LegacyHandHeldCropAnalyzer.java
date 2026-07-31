package ru.mot.ic2exfidelity.legacy;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropSeed;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.StackUtil;
import java.util.Locale;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Persistent three-slot inventory stored inside the crop analyzer ItemStack. */
public final class LegacyHandHeldCropAnalyzer extends HandHeldInventory {
    public LegacyHandHeldCropAnalyzer(Player player, InteractionHand hand, ItemStack stack) {
        super(player, hand, stack, 3);
    }

    @Override
    public boolean m_7013_(int slot, ItemStack stack) {
        return slot == 0 && !stack.m_41619_() && stack.m_41720_() instanceof ICropSeed;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return new LegacyContainerCropAnalyzer(syncId, this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int syncId, Inventory inventory, GrowingBuffer data) {
        return new LegacyContainerCropAnalyzer(syncId, this);
    }

    public void tickServer() {
        chargeFromSlot();

        ItemStack input = m_8020_(0);
        if (!m_8020_(1).m_41619_() || input.m_41619_()
                || !(input.m_41720_() instanceof ICropSeed seed)) {
            return;
        }

        int scanned = seed.getScannedFromStack(input);
        if (scanned < 4) {
            int cost = energyForLevel(scanned);
            double used = ElectricItem.manager.discharge(
                    containerStack, cost, 2, true, false, false);
            if (Math.abs(used - cost) > 1.0E-6) {
                return;
            }
            seed.incrementScannedFromStack(input);
        }

        m_6836_(1, input);
        m_6836_(0, ItemStack.f_41583_);
    }

    private void chargeFromSlot() {
        ItemStack source = m_8020_(2);
        if (source.m_41619_()) {
            return;
        }

        double room = ElectricItem.manager.charge(
                containerStack, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true);
        if (room <= 0.0) {
            return;
        }

        double available = Info.getItemInfo().getEnergyValue(source);
        if (available > 0.0) {
            source = StackUtil.decSize(source);
            m_6836_(2, source);
        } else {
            available = ElectricItem.manager.discharge(
                    source, room, Integer.MAX_VALUE, false, true, false);
        }
        if (available > 0.0) {
            ElectricItem.manager.charge(containerStack, available, 3, true, false);
            m_6596_();
        }
    }

    public static int energyForLevel(int level) {
        return switch (level) {
            case 1 -> 90;
            case 2 -> 900;
            case 3 -> 9_000;
            default -> 10;
        };
    }

    public int getScannedLevel() {
        ItemStack seed = m_8020_(1);
        return seed.m_41619_() || !(seed.m_41720_() instanceof ICropSeed cropSeed)
                ? -1 : cropSeed.getScannedFromStack(seed);
    }

    public CropCard crop() {
        return Crops.instance.getCropCard(m_8020_(1));
    }

    public String getSeedName() {
        CropCard crop = crop();
        return crop == null ? "" : Component.m_237115_(crop.getUnlocalizedName()).getString();
    }

    public String getSeedTier() {
        CropCard crop = crop();
        if (crop == null) {
            return "0";
        }
        int tier = crop.getProperties().getTier();
        return tier >= 1 && tier <= 16
                ? new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII",
                    "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI"}[tier - 1]
                : "0";
    }

    public String getSeedDiscovered() {
        CropCard crop = crop();
        return crop == null ? "" : crop.getDiscoveredBy();
    }

    public String getSeedDesc(int index) {
        CropCard crop = crop();
        return crop == null ? "" : crop.desc(index);
    }

    public int getSeedGrowth() {
        return ((ICropSeed) m_8020_(1).m_41720_()).getGrowthFromStack(m_8020_(1));
    }

    public int getSeedGain() {
        return ((ICropSeed) m_8020_(1).m_41720_()).getGainFromStack(m_8020_(1));
    }

    public int getSeedResistance() {
        return ((ICropSeed) m_8020_(1).m_41720_()).getResistanceFromStack(m_8020_(1));
    }
}
