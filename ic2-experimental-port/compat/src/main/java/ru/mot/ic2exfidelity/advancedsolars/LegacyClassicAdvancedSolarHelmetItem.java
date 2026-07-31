package ru.mot.ic2exfidelity.advancedsolars;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

/** IC2 Classic's non-electric five-EU/t advanced solar helmet. */
public final class LegacyClassicAdvancedSolarHelmetItem extends ItemArmorUtility {
    private static final UUID ARMOR_MODIFIER = UUID.fromString(
            "2AD3F246-FEE1-4E67-B886-69FD380BB150");
    public static final int PRODUCTION = 5;

    public LegacyClassicAdvancedSolarHelmetItem(Item.Properties properties) {
        super(Ic2ArmorMaterials.CF_PACK, properties.m_41487_(1), EquipmentSlot.HEAD);
    }

    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.f_46443_
                && LegacyAdvancedSolarPanelBlockEntity.isSunVisible(
                        level, player.m_20183_())) {
            chargeArmor(player, PRODUCTION);
        }
    }

    public int chargeArmor(Player player, int provided) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (provided <= 0) {
                return 0;
            }
            if (slot.m_20743_() == EquipmentSlot.Type.HAND) {
                continue;
            }
            provided -= (int) ElectricItem.manager.charge(
                    player.m_6844_(slot), provided, Integer.MAX_VALUE, false, false);
        }
        return provided > 0 ? chargeCurios(player, provided) : 0;
    }

    private int chargeCurios(Player player, int provided) {
        if (!ModList.get().isLoaded("curios")) {
            return provided;
        }
        try {
            Class<?> apiClass = Class.forName("top.theillusivec4.curios.api.CuriosApi");
            Object helper = apiClass.getMethod("getCuriosHelper").invoke(null);
            Method getEquipped = helper.getClass().getMethod(
                    "getEquippedCurios", net.minecraft.world.entity.LivingEntity.class);
            Object lazy = getEquipped.invoke(helper, player);
            Optional<?> resolved = (Optional<?>) lazy.getClass().getMethod("resolve").invoke(lazy);
            if (resolved.isEmpty()) {
                return provided;
            }
            Object handler = resolved.get();
            Method getSlots = handler.getClass().getMethod("getSlots");
            Method getStack = handler.getClass().getMethod("getStackInSlot", int.class);
            int slots = (int) getSlots.invoke(handler);
            for (int slot = 0; slot < slots && provided > 0; slot++) {
                provided -= (int) ElectricItem.manager.charge(
                        (ItemStack) getStack.invoke(handler, slot),
                        provided,
                        Integer.MAX_VALUE,
                        false,
                        false);
            }
        } catch (ReflectiveOperationException ignored) {
            // Curios is optional in the original IC2 Classic implementation.
        }
        return provided;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        if (slot == EquipmentSlot.HEAD) {
            modifiers.put(Attributes.f_22284_, new AttributeModifier(
                    ARMOR_MODIFIER,
                    "Armor modifier",
                    1.0,
                    AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    public String getArmorTexture(
            ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "ic2:textures/models/armor/solar_1.png";
    }
}
