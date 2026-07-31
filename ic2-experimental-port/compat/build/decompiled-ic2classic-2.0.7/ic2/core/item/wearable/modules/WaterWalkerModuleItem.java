/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.IC2;
import ic2.core.item.wearable.modules.BaseModuleItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WaterWalkerModuleItem
extends BaseModuleItem {
    public WaterWalkerModuleItem(String textureFolder, String textureName) {
        super("water_walker_module", null, textureFolder, textureName, IArmorModule.ModuleType.MOVEMENT);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.FEET;
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (!player.m_6144_() && player.m_20184_().m_7098_() < 0.0 && this.canUseEnergy(armor, 100) && world.m_46859_(player.m_20183_()) && player.f_19853_.m_46855_(new AABB(player.m_20183_().m_7495_())) && Math.abs(player.m_20186_() - (double)player.m_20183_().m_123342_()) < 0.1) {
            if (world.m_46467_() % 10L == 0L) {
                this.useEnergy(armor, 100, (LivingEntity)player);
            }
            Vec3 motion = player.m_20184_();
            player.m_20334_(motion.m_7096_(), 0.0, motion.m_7094_());
            player.m_6853_(true);
            Vec3 pos = player.m_20182_();
            for (int i = 0; i < 2; ++i) {
                world.m_7107_((ParticleOptions)ParticleTypes.f_123774_, pos.m_7096_(), pos.m_7098_() - 0.2, pos.m_7094_(), world.f_46441_.m_188500_() - 0.5, 0.04, world.f_46441_.m_188500_() - 0.5);
            }
            IC2.PLATFORM.resetPlayerInAirTime((Entity)player);
        }
    }
}

