/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.properties.WoodType
 */
package ic2.core.platform.registries;

import ic2.core.item.wearable.material.IC2ArmorMaterial;
import ic2.core.item.wearable.material.IC2SpecialArmorMaterial;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class IC2Materials {
    public static ArmorMaterial BRONZE_ARMOR = new IC2ArmorMaterial("IC2_BRONZE", 15, new int[]{3, 6, 8, 3}, 9, SoundEvents.f_11677_, () -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.INGOT_BRONZE}), 0.0f);
    public static ArmorMaterial COMPOSITE_ARMOR = new IC2ArmorMaterial("IC2_ALLOY", 50, new int[]{4, 7, 9, 4}, 12, SoundEvents.f_11677_, () -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.INGOT_ADVANCED_ALLOY}), 4.0f);
    public static ArmorMaterial HAZMAT_ARMOR = new IC2SpecialArmorMaterial((ArmorMaterial)ArmorMaterials.DIAMOND).setName("IC2_HAZMAT").setDurability(64).setRepairMaterial(() -> Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.RUBBER}));
    public static ArmorMaterial ELECTRIC_ARMOR = new IC2SpecialArmorMaterial((ArmorMaterial)ArmorMaterials.DIAMOND).setName("IC2_ELECTRIC").setDurability(0).setRepairMaterial(() -> Ingredient.f_43901_);
    public static ArmorMaterial ADVANCED_ARMOR = new IC2SpecialArmorMaterial((ArmorMaterial)ArmorMaterials.DIAMOND).setName("IC2_ADVANCED").setDurability(0);
    public static final WoodType SIGN_RUBBER_WOOD = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:rubberwood_sign"));
    public static final WoodType SIGN_COPPER = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:copper_sign"));
    public static final WoodType SIGN_ALUMINIUM = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:aluminium_sign"));
    public static final WoodType SIGN_TIN = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:tin_sign"));
    public static final WoodType SIGN_SILVER = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:silver_sign"));
    public static final WoodType SIGN_BRONZE = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:bronze_sign"));
    public static final WoodType SIGN_REFINED_IRON = WoodType.m_61844_((WoodType)WoodType.create((String)"ic2:refined_iron_sign"));
}

