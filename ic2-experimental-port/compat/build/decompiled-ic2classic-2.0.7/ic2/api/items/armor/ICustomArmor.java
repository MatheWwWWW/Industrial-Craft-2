/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.armor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ICustomArmor {
    public AbsorptionProperties getProperties(LivingEntity var1, ItemStack var2, DamageSource var3, double var4, EquipmentSlot var6);

    public void damageArmor(LivingEntity var1, ItemStack var2, DamageSource var3, int var4, EquipmentSlot var5, DamageType var6);

    default public boolean canBlockDamageSource(LivingEntity player, ItemStack stack, DamageSource source, EquipmentSlot slot) {
        return !source.m_19376_();
    }

    public static class AbsorptionProperties
    implements Comparable<AbsorptionProperties> {
        public int priority;
        public int absorbMax;
        public double absorbRatio;
        public EquipmentSlot slot = null;

        public AbsorptionProperties(int priority, double ratio, int max) {
            this.priority = priority;
            this.absorbMax = max;
            this.absorbRatio = ratio;
        }

        private AbsorptionProperties(AbsorptionProperties orig) {
            this.priority = orig.priority;
            this.absorbMax = orig.absorbMax;
            this.absorbRatio = orig.absorbRatio;
            this.slot = orig.slot;
        }

        private int calcMaxAbsorption() {
            return (int)(this.absorbRatio == 0.0 ? 0.0 : (double)this.absorbMax * 100.0 / this.absorbRatio);
        }

        @Override
        public int compareTo(AbsorptionProperties o) {
            if (o.priority != this.priority) {
                return o.priority - this.priority;
            }
            return this.calcMaxAbsorption() - o.calcMaxAbsorption();
        }

        public String toString() {
            return String.format("%d, %d, %f, %d", this.priority, this.absorbMax, this.absorbRatio, this.calcMaxAbsorption());
        }

        public AbsorptionProperties copy() {
            return new AbsorptionProperties(this);
        }
    }

    public static enum DamageType {
        MODDED,
        VANILLA,
        THORNS_SELF;

    }
}

