/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.api.reactor.IBaseReactorComponent;
import ic2.api.reactor.IReactor;
import ic2.core.Ic2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemNuclearResource
extends Item
implements IBaseReactorComponent {
    private final int radiationDuration;
    private final int radiationAmplifier;

    public ItemNuclearResource(Item.Properties properties, int n, int n2) {
        super(properties);
        this.radiationDuration = n;
        this.radiationAmplifier = n2;
    }

    @Override
    public boolean canBePlacedIn(ItemStack itemStack, IReactor iReactor) {
        return false;
    }

    public void m_6883_(ItemStack itemStack, Level level, Entity entity, int n, boolean bl) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entity;
        if (ItemArmorHazmat.hasCompleteHazmat(livingEntity)) {
            return;
        }
        Ic2Potion.radiation.applyTo(livingEntity, this.radiationDuration * 20, this.radiationAmplifier);
    }
}

