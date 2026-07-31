/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.api.tile;

import ic2.api.info.Info;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class RotorRegistry {
    private static IRotorRegistry INSTANCE;

    public static <T extends TileEntity> void registerRotorProvider(Class<T> clazz) {
        if (INSTANCE != null) {
            INSTANCE.registerRotorProvider(clazz);
        }
    }

    public static void setInstance(IRotorRegistry i) {
        ModContainer mc = Loader.instance().activeModContainer();
        if (mc == null || !Info.MOD_ID.equals(mc.getModId())) {
            throw new IllegalAccessError("Only IC2 can set the instance");
        }
        INSTANCE = i;
    }

    public static interface IRotorRegistry {
        public <T extends TileEntity> void registerRotorProvider(Class<T> var1);
    }
}

