/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraftforge.fml.client.registry.ClientRegistry
 */
package ic2.core.apihelper;

import ic2.api.info.Info;
import ic2.api.item.IC2Items;
import ic2.api.network.NetworkHelper;
import ic2.api.tile.RotorRegistry;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.apihelper.ItemAPI;
import ic2.core.block.KineticGeneratorRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ApiHelper {
    public static void preload() {
        Info.DMG_ELECTRIC = IC2DamageSource.electricity;
        Info.DMG_NUKE_EXPLOSION = IC2DamageSource.nuke;
        Info.DMG_RADIATION = IC2DamageSource.radiation;
        IC2Items.setInstance(new ItemAPI());
        NetworkHelper.setInstance(IC2.network.get(true), IC2.network.get(false));
        if (IC2.platform.isRendering()) {
            RotorRegistry.setInstance(new RotorRegistry.IRotorRegistry(){

                @Override
                public <T extends TileEntity> void registerRotorProvider(Class<T> clazz) {
                    ClientRegistry.bindTileEntitySpecialRenderer(clazz, new KineticGeneratorRenderer());
                }
            });
        }
    }
}

