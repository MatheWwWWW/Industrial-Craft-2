/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 */
package ic2.api.info;

import ic2.api.info.IInfoProvider;
import ic2.api.util.CoreAccessRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;

public final class Info {
    public static final String MOD_ID = "ic2";
    private static IInfoProvider itemInfo;
    private static DamageSource DMG_ELECTRIC;
    private static DamageSource DMG_NUKE_EXPLOSION;
    private static DamageSource DMG_RADIATION;
    private static MobEffect POTION_RADIATION;

    public static boolean isIc2Available() {
        return CoreAccessRef.exists();
    }

    public static IInfoProvider getItemInfo() {
        IInfoProvider iInfoProvider = itemInfo;
        if (iInfoProvider == null) {
            itemInfo = iInfoProvider = CoreAccessRef.get().getItemInfo();
        }
        return iInfoProvider;
    }

    public static DamageSource getElectricDamageSource() {
        DamageSource damageSource = DMG_ELECTRIC;
        if (damageSource == null) {
            DMG_ELECTRIC = damageSource = CoreAccessRef.get().getElectricDamageSource();
        }
        return damageSource;
    }

    public static DamageSource getNukeExplosionDamageSource() {
        DamageSource damageSource = DMG_NUKE_EXPLOSION;
        if (damageSource == null) {
            DMG_NUKE_EXPLOSION = damageSource = CoreAccessRef.get().getNukeExplosionDamageSource();
        }
        return damageSource;
    }

    public static DamageSource getRadiationDamageSource() {
        DamageSource damageSource = DMG_RADIATION;
        if (damageSource == null) {
            DMG_RADIATION = damageSource = CoreAccessRef.get().getRadiationDamageSource();
        }
        return damageSource;
    }

    public static MobEffect getRadiationStatusEffect() {
        MobEffect mobEffect = POTION_RADIATION;
        if (mobEffect == null) {
            POTION_RADIATION = mobEffect = CoreAccessRef.get().getRadiationStatusEffect();
        }
        return mobEffect;
    }
}

