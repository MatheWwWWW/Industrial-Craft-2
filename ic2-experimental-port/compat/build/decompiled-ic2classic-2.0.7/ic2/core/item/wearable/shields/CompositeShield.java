/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.wearable.shields;

import ic2.core.IC2;
import ic2.core.item.base.IC2Item;
import ic2.core.item.wearable.base.IC2ShieldBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CompositeShield
extends IC2ShieldBase {
    private ResourceLocation texture = new ResourceLocation(IC2.MODID, "textures/models/armor/shieldalloy.png");

    public CompositeShield() {
        super("composite_shield", 3000);
    }

    @Override
    public void damageShield(ItemStack stack, int amount, LivingEntity entity) {
        stack.m_41622_(amount, entity, IC2Item.get(entity.m_7655_()));
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack) {
        return this.texture;
    }

    public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
        return StackUtil.isStackEqual(new ItemStack((ItemLike)IC2Items.INGOT_ADVANCED_ALLOY), repair);
    }
}

