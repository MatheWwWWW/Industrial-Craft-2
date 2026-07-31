/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package ic2.api.util;

import ic2.api.info.IInfoProvider;
import ic2.api.network.INetworkManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface CoreAccess {
    public boolean isCallingFromIc2();

    public IInfoProvider getItemInfo();

    public INetworkManager getClientNetworkManager();

    public INetworkManager getServerNetworkManager();

    public DamageSource getElectricDamageSource();

    public DamageSource getNukeExplosionDamageSource();

    public DamageSource getRadiationDamageSource();

    public MobEffect getRadiationStatusEffect();

    public <T extends BlockEntity> void registerRotorProvider(BlockEntityType<T> var1);
}

