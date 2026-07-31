/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.level.Level
 *  org.apache.logging.log4j.LogManager
 */
package ic2.core;

import ic2.core.Ic2Achievements;
import ic2.core.ItemGroupIconSupplier;
import ic2.core.audio.AudioManager;
import ic2.core.network.NetworkManager;
import ic2.core.proxy.EnvProxy;
import ic2.core.proxy.SideProxy;
import ic2.core.sound.SoundManager;
import ic2.core.util.Keyboard;
import ic2.core.util.Log;
import ic2.core.util.PriorityExecutor;
import ic2.core.util.SideGateway;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;

public class IC2 {
    public static final String VERSION = "2.9.162+ex119";
    public static final String MODID = "ic2";
    public static final String RESOURCE_DOMAIN = "ic2";
    public static final String ICON_STACK_NAME = "ic2:tab_icon";
    public static final EnvProxy envProxy;
    public static final SideProxy sideProxy;
    public static final Log log;
    public static final SideGateway<NetworkManager> network;
    public static final Keyboard keyboard;
    public static final AudioManager audioManager;
    public static final SoundManager soundManager;
    public static Ic2Achievements achievements;
    public static final CreativeModeTab tabIC2;
    public static final int setBlockNotify = 1;
    public static final int setBlockUpdate = 2;
    public static final int setBlockNoUpdateFromClient = 4;
    public static final PriorityExecutor threadPool;
    public static final RandomSource random;
    public static boolean initialized;
    public static boolean suddenlyHoes;
    public static boolean seasonal;

    public static int getSeaLevel(Level level) {
        return level.m_5736_();
    }

    public static int getWorldMaxHeight(Level level) {
        return level.m_141928_();
    }

    public static int getWorldMinHeight(Level level) {
        return level.m_141937_();
    }

    public static ResourceLocation getIdentifier(String string) {
        return new ResourceLocation("ic2", string);
    }

    private static EnvProxy createEnvProxy() {
        String string;
        try {
            Class.forName("net.fabricmc.api.ModInitializer");
            string = "ic2.fabric.EnvProxyFabric";
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                Class.forName("net.minecraftforge.common.MinecraftForge");
                string = "ic2.forge.EnvProxyForge";
            }
            catch (ClassNotFoundException classNotFoundException2) {
                throw new RuntimeException("unknown environment");
            }
        }
        try {
            return (EnvProxy)Class.forName(string).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static SideProxy createSideProxy() {
        String string = envProxy.isClientEnv() ? "ic2.core.proxy.SideProxyClient" : "ic2.core.proxy.SideProxyServer";
        try {
            return (SideProxy)Class.forName(string).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    static {
        try {
            new BlockPos(1, 2, 3).m_7918_(2, 3, 4);
        }
        catch (Throwable throwable) {
            throw new Error("IC2 is incompatible with this environment, use the normal IC2 version, not the dev one.", throwable);
        }
        envProxy = IC2.createEnvProxy();
        sideProxy = IC2.createSideProxy();
        log = new Log(LogManager.getLogger((String)"ic2"));
        network = new SideGateway("ic2.core.network.NetworkManager", "ic2.core.network.NetworkManagerClient");
        keyboard = sideProxy.getKeyboard();
        audioManager = sideProxy.getAudioManager();
        soundManager = sideProxy.getSoundManager();
        tabIC2 = envProxy.createItemGroup(IC2.getIdentifier("general"), new ItemGroupIconSupplier());
        threadPool = new PriorityExecutor(Math.max(Runtime.getRuntime().availableProcessors(), 2));
        random = RandomSource.m_216327_();
        initialized = false;
        suddenlyHoes = false;
        seasonal = false;
    }
}

