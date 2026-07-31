/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.ModList
 */
package ic2.curioplugin;

import ic2.api.addons.IC2Plugin;
import ic2.api.addons.IModule;
import ic2.core.IC2;
import ic2.core.networking.PacketManager;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import ic2.curioplugin.core.CurioPlugin;
import ic2.curioplugin.core.packet.CurioScrollPacket;
import ic2.curioplugin.core.packet.ToggleCurioPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

@IC2Plugin(id="curio", name="Curio-Plugin", version="1.0")
public class CurioModule
implements IModule {
    @Override
    public boolean canLoad(Dist side) {
        return ModList.get().isLoaded("curios");
    }

    @Override
    public void preInit(IEventBus modBus) {
        CurioPlugin plugin = new CurioPlugin();
        IC2.CURIO_PLUGIN = plugin;
        modBus.addListener(plugin::loadIMC);
        MinecraftForge.EVENT_BUS.register((Object)plugin);
        PacketManager.INSTANCE.registerPacket(44, CurioScrollPacket.class, CurioScrollPacket::new);
        PacketManager.INSTANCE.registerPacket(45, ToggleCurioPacket.class, ToggleCurioPacket::new);
    }

    @Override
    public void postInit() {
        IC2Tags.registerSimpleTag("curios", "back", IC2Items.JETPACK_ELECTRIC, IC2Items.JETPACK_ELECTRIC_COMPACT, IC2Items.JETPACK_FUEL, IC2Items.JETPACK_NUCLEAR, IC2Items.JETPACK_NUCLEAR_COMPACT, IC2Items.BAT_PACK, IC2Items.LAP_PACK, IC2Items.QUANTUM_PACK, IC2Items.CF_PACK, IC2Items.JEPTACK_FUEL_COMPACT);
        IC2Tags.registerSimpleTag("curios", "belt", IC2Items.RE_BATTERY, IC2Items.ENERGY_CRYSTAL, IC2Items.LAPATRON_CRYSTAL, IC2Items.GLOWTRONIC_CRYSTAL, IC2Items.UESC, IC2Items.BATTERY_BELT, IC2Items.QUANTUM_ACCUMULATOR, IC2Items.QUANTUM_ACCUMULATOR_BIG, IC2Items.PESD);
    }
}

