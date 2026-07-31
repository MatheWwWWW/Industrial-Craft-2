package ru.mot.ic2exfidelity.advancedsolars;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

/** Original Advanced Solars helmet generation and inventory charging order. */
public final class LegacyAdvancedSolarHelmetItem extends ItemArmorUtility {
    private static final UUID ARMOR_MODIFIER = UUID.fromString(
            "2AD3F246-FEE1-4E67-B886-69FD380BB150");

    private final int production;
    private final int lowerProduction;
    private final int tier;
    private final String armorTexture;

    public LegacyAdvancedSolarHelmetItem(
            Item.Properties properties,
            int production,
            int lowerProduction,
            int tier,
            String armorTexture) {
        super(Ic2ArmorMaterials.CF_PACK, properties.m_41487_(1), EquipmentSlot.HEAD);
        this.production = production;
        this.lowerProduction = lowerProduction;
        this.tier = tier;
        this.armorTexture = armorTexture;
    }

    // Forge adds this hook to Item while transforming the game classes.
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (level.f_46443_
                || !level.m_6042_().f_223549_()
                || !level.m_46861_(player.m_20183_())) {
            return;
        }
        int available = LegacyAdvancedSolarPanelBlockEntity.isSunVisible(
                level, player.m_20183_()) ? production : lowerProduction;
        chargeInventory(player, available, stack);
    }

    public int chargeInventory(Player player, int provided, ItemStack helmet) {
        double helmetCharge = ElectricItem.manager.getCharge(helmet);
        double helmetCapacity = ElectricItem.manager.getMaxCharge(helmet);
        if (helmetCharge != helmetCapacity) {
            provided -= (int) ElectricItem.manager.charge(
                    helmet, provided, tier, false, false);
        } else {
            provided = chargeInventoryLists(Arrays.asList(
                    player.m_150109_().f_35975_,
                    player.m_150109_().f_35976_,
                    player.m_150109_().f_35974_), provided);
            if (provided > 0) {
                provided = chargeCurios(player, provided);
            }
        }
        return provided;
    }

    public int chargeInventoryLists(
            List<? extends List<ItemStack>> inventories, int provided) {
        for (List<ItemStack> inventory : inventories) {
            for (ItemStack stack : inventory) {
                if (provided <= 0) {
                    return 0;
                }
                if (stack.m_41619_()) {
                    continue;
                }
                provided -= (int) ElectricItem.manager.charge(
                        stack, provided, tier, false, false);
            }
        }
        return provided;
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
                ItemStack stack = (ItemStack) getStack.invoke(handler, slot);
                provided -= (int) ElectricItem.manager.charge(
                        stack, provided, tier, false, false);
            }
        } catch (ReflectiveOperationException ignored) {
            // Curios is an optional integration in the original addon too.
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

    @Override
    public void m_7373_(
            ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, level, tooltip, flag);
        tooltip.add(Component.m_237110_(
                "item_info.advanced_solars.helmet_production", production)
                .m_130940_(ChatFormatting.GRAY));
        tooltip.add(Component.m_237110_(
                "item_info.advanced_solars.helmet_lower_production", lowerProduction)
                .m_130940_(ChatFormatting.GRAY));
    }

    public String getArmorTexture(
            ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "advanced_solars:textures/models/" + armorTexture;
    }

    public int getProduction() {
        return production;
    }

    public int getLowerProduction() {
        return lowerProduction;
    }

    public int getChargingTier() {
        return tier;
    }
}
