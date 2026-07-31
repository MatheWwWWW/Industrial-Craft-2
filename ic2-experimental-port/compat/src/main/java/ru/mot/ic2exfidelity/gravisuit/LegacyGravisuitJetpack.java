package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Gravisuit 2.2 jetpack power, height, storage and consumption parameters. */
public final class LegacyGravisuitJetpack extends ItemArmorElectric
        implements IJetpack, IHandHeldInventory {
    private final float power;
    private final float dropPercentage;
    private final float heightDivisor;
    private final int normalCost;
    private final int hoverCost;
    private final boolean providesEnergy;
    private final boolean nuclear;
    private final boolean gravitation;
    private final String armorTexture;

    private LegacyGravisuitJetpack(
            Item.Properties properties,
            double capacity,
            double transferLimit,
            int tier,
            float power,
            float dropPercentage,
            float heightDivisor,
            int normalCost,
            int hoverCost,
            boolean providesEnergy,
            boolean nuclear,
            boolean gravitation,
            String armorTexture) {
        super(Ic2ArmorMaterials.CF_PACK, EquipmentSlot.CHEST, properties,
                capacity, transferLimit, tier);
        this.power = power;
        this.dropPercentage = dropPercentage;
        this.heightDivisor = heightDivisor;
        this.normalCost = normalCost;
        this.hoverCost = hoverCost;
        this.providesEnergy = providesEnergy;
        this.nuclear = nuclear;
        this.gravitation = gravitation;
        this.armorTexture = armorTexture;
    }

    public static LegacyGravisuitJetpack advancedElectric(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 200_000.0, 500.0, 2,
                1.0F, 0.05F, 1.15F, 14, 8, false, false, false,
                "gravisuit:textures/models/advanced_electric_jetpack_1.png");
    }

    public static LegacyGravisuitJetpack compactedElectric(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 360_000.0, 500.0, 2,
                1.4F, 0.05F, 1.1F, 30, 25, false, false, false,
                "ic2:textures/models/armor/jetpack_compacted_electric_1.png");
    }

    public static LegacyGravisuitJetpack nuclear(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 30_000.0, 0.0, 1,
                0.95F, 0.05F, 1.1F, 12, 9, false, true, false,
                "ic2:textures/models/armor/jetpack_nuclear_1.png");
    }

    public static LegacyGravisuitJetpack compactedNuclear(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 360_000.0, 0.0, 1,
                1.8F, 0.05F, 1.1F, 40, 35, false, true, false,
                "ic2:textures/models/armor/jetpack_compacted_nuclear_1.png");
    }

    public static LegacyGravisuitJetpack advancedNuclear(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 200_000.0, 0.0, 2,
                1.0F, 0.05F, 1.15F, 14, 8, true, true, false,
                "gravisuit:textures/models/advanced_nuclear_jetpack_1.png");
    }

    public static LegacyGravisuitJetpack gravitation(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 500_000.0, 1_000.0, 3,
                1.4F, 0.0F, 1.0F, 25, 30, false, false, true,
                "gravisuit:textures/models/gravitation_jetpack_1.png");
    }

    public static LegacyGravisuitJetpack nuclearGravitation(Item.Properties properties) {
        return new LegacyGravisuitJetpack(
                properties, 500_000.0, 0.0, 3,
                1.4F, 0.0F, 1.0F, 25, 30, true, true, true,
                "gravisuit:textures/models/nuclear_gravitation_jetpack_1.png");
    }

    @Override
    public boolean drainEnergy(ItemStack stack, int amount) {
        double cost = amount == 1 ? hoverCost : normalCost;
        return ElectricItem.manager.discharge(
                stack, cost, Integer.MAX_VALUE, true, false, false) == cost;
    }

    @Override
    public float getPower(ItemStack stack) {
        return power;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return dropPercentage;
    }

    @Override
    public double getChargeLevel(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) / getMaxCharge(stack);
    }

    @Override
    public boolean isJetpackActive(ItemStack stack) {
        // The anti-gravity engine is an extra creative-flight mode. Turning it
        // off leaves the underlying jetpack enabled in Gravisuit 2.2.
        return !gravitation || !isGravitationEngineEnabled(stack);
    }

    @Override
    public float getHoverMultiplier(ItemStack stack, boolean upwards) {
        return upwards ? 0.3F : 0.2F;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack stack) {
        return heightDivisor;
    }

    @Override
    public int getEnergyPerDamage() {
        return 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return providesEnergy;
    }

    // Forge adds this hook to Item while transforming the game classes.
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (nuclear && !level.f_46443_) {
            new LegacyNuclearJetpackReactor(stack, player).onTick();
        }
    }

    public boolean isNuclear() {
        return nuclear;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (nuclear && IC2.keyboard.isSideinventoryKeyDown(player)) {
            if (!level.f_46443_) {
                getInventory(player, hand, stack)
                        .openManagedItem(player, hand, null);
            }
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }
        return super.m_7203_(level, player, hand);
    }

    @Override
    public IHasGui getInventory(
            Player player, InteractionHand hand, ItemStack stack) {
        return new LegacyNuclearJetpackInventory(player, hand, stack);
    }

    public boolean isGravitation() {
        return gravitation;
    }

    public boolean isGravitationEngineEnabled(ItemStack stack) {
        return gravitation
                && stack.m_41782_()
                && stack.m_41783_().m_128471_("engine_on");
    }

    public void setGravitationEngineEnabled(ItemStack stack, boolean enabled) {
        if (!gravitation) {
            return;
        }
        stack.m_41784_().m_128379_("engine_on", enabled);
    }

    public String getArmorTexture(
            ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return armorTexture;
    }
}
