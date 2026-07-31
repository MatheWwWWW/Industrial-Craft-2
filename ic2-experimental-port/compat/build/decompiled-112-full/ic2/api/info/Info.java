/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.DamageSource
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.LoaderState
 */
package ic2.api.info;

import ic2.api.info.IInfoProvider;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

public class Info {
    public static IInfoProvider itemInfo;
    public static Object ic2ModInstance;
    public static DamageSource DMG_ELECTRIC;
    public static DamageSource DMG_NUKE_EXPLOSION;
    public static DamageSource DMG_RADIATION;
    public static Potion POTION_RADIATION;
    public static String MOD_ID;
    private static Boolean ic2Available;

    public static boolean isIc2Available() {
        if (ic2Available != null) {
            return ic2Available;
        }
        boolean loaded = Loader.isModLoaded((String)MOD_ID);
        if (Loader.instance().hasReachedState(LoaderState.CONSTRUCTING)) {
            ic2Available = loaded;
        }
        return loaded;
    }

    static {
        MOD_ID = "ic2";
        ic2Available = null;
    }
}

