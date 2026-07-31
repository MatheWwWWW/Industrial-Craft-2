/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  top.theillusivec4.curios.api.type.capability.ICurio
 */
package ic2.curioplugin.modules;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class NuclearCurio
implements ICurio {
    ItemStack stack;

    public NuclearCurio(ItemStack stack) {
        this.stack = stack;
    }

    public void curioTick(String identifier, int index, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            CompoundTag data = this.stack.m_41698_("curio_index");
            data.m_128405_("index", index);
            data.m_128359_("id", identifier);
            this.stack.onArmorTick(livingEntity.f_19853_, player);
            this.stack.m_41749_("curio_index");
        }
    }

    public boolean canSync(String identifier, int index, LivingEntity livingEntity) {
        return true;
    }

    public CompoundTag writeSyncData() {
        return this.stack.getShareTag();
    }

    public void readSyncData(CompoundTag compound) {
        this.stack.readShareTag(compound == null || compound.m_128456_() ? null : compound);
    }

    public ItemStack getStack() {
        return this.stack;
    }
}

