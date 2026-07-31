/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.core.item.tool.AbstractItemNanoSaber;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemNanoSaber
extends AbstractItemNanoSaber {
    public ItemNanoSaber(Item.Properties properties) {
        super(properties);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) {
            return this.m_7167_(equipmentSlot);
        }
        int n = 4;
        if (ElectricItem.manager.canUse(itemStack, 400.0) && ItemNanoSaber.isActive(itemStack)) {
            n = 20;
        }
        HashMultimap hashMultimap = HashMultimap.create();
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        builder.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", (double)n, AttributeModifier.Operation.ADDITION));
        builder.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Tool modifier", -3.0, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}

