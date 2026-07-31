/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  mcjty.theoneprobe.api.IProbeHitData
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.IProbeInfoProvider
 *  mcjty.theoneprobe.api.ProbeMode
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.probeplugin.base;

import ic2.core.IC2;
import ic2.core.block.base.features.IProfileListener;
import ic2.core.block.base.features.ITickListener;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.profiler.IProfilerTile;
import ic2.core.utils.helpers.profiler.ProfilerEntry;
import ic2.core.utils.helpers.profiler.ProfilerHelper;
import ic2.core.utils.math.MathUtils;
import ic2.probeplugin.base.ProbePlugin;
import ic2.probeplugin.info.ITileInfoComponent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ProbeTileListener
implements IProbeInfoProvider {
    static Map<Class<? extends BlockEntity>, List<ITileInfoComponent<?>>> cachedData = CollectionUtils.createMap();

    public ResourceLocation getID() {
        return new ResourceLocation("ic2", "tile_provider");
    }

    public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level Level2, BlockState state, IProbeHitData hitData) {
        List<ITileInfoComponent<?>> list;
        BlockEntity tile = Level2.m_7702_(hitData.getPos());
        if (tile != null && !(list = ProbeTileListener.getData(tile.getClass())).isEmpty()) {
            PlayerHandler handler = PlayerHandler.getHandler(player);
            int m = list.size();
            for (int i = 0; i < m; ++i) {
                ITileInfoComponent<?> comp = list.get(i);
                if (!comp.isValid(mode, handler)) continue;
                comp.addTileInfo(info, player, hitData.getSideHit(), tile);
            }
        }
        if (IC2.KEYBOARD.isHudModeKeyDown(player) && IC2.CONFIG.tileProfiler.get()) {
            if (tile instanceof IProfilerTile) {
                ProfilerHelper.ProfilerInstance instance = ProfilerHelper.INSTANCE.getOrCreate(tile);
                if (instance.getMain() == null) {
                    return;
                }
                IProbeInfo profile = info.vertical();
                ObjectList entry = CollectionUtils.createList();
                ProbeTileListener.find(instance.getMain(), entry);
                int m = entry.size();
                for (int i = 0; i < m; ++i) {
                    ((ProfilerEntry)entry.get(i)).print(profile);
                }
            }
            if (tile instanceof IProfileListener) {
                IProfileListener listener = (IProfileListener)tile;
                long qs = listener.getLag() / 1000L;
                MutableComponent s = listener.showResults().m_130940_(qs <= 0L ? ChatFormatting.AQUA : (qs <= 4L ? ChatFormatting.GREEN : (qs <= 20L ? ChatFormatting.YELLOW : ChatFormatting.RED)));
                IProbeInfo subInfo = info.vertical();
                subInfo.mcText((Component)s).progress(qs, 50L, info.defaultProgressStyle().suffix("\u00b5s").filledColor((qs <= 0L ? Color.BLUE : (qs <= 4L ? Color.GREEN : (qs <= 20L ? Color.YELLOW : Color.RED))).getRGB()));
                ITickListener.GlobalLag lag = ITickListener.GLOBAL_LAG.get(tile.getClass());
                if (lag != null) {
                    qs = lag.getGlobalAverage();
                    long colorQS = qs / 1000L;
                    ChatFormatting format = colorQS <= 0L ? ChatFormatting.AQUA : (colorQS <= 4L ? ChatFormatting.GREEN : (colorQS <= 20L ? ChatFormatting.YELLOW : ChatFormatting.RED));
                    subInfo.mcText((Component)Component.m_237113_((String)MathUtils.toLagStringProbe("PerTile Lag Avg", qs)).m_130940_(format));
                    qs = lag.getGlobalTotal();
                    subInfo.mcText((Component)Component.m_237113_((String)MathUtils.toLagStringProbe("Total Lag Avg", qs)).m_130940_(format));
                }
                lag = ITickListener.SERVER_LAG;
                subInfo.mcText((Component)Component.m_237113_((String)MathUtils.toLagStringProbe("Mod Per Tile Lag", lag.getGlobalAverage())).m_130940_(ChatFormatting.AQUA));
                subInfo.mcText((Component)Component.m_237113_((String)MathUtils.toLagStringProbe("Mod Total Lag", lag.getGlobalTotal())).m_130940_(ChatFormatting.AQUA));
            }
        }
    }

    static void find(ProfilerEntry mainEntry, List<ProfilerEntry> entry) {
        if (mainEntry == null) {
            return;
        }
        entry.add(mainEntry);
        int m = mainEntry.getChildCount();
        for (int i = 0; i < m; ++i) {
            ProbeTileListener.find(mainEntry.getChild(i), entry);
        }
    }

    public static List<ITileInfoComponent<?>> getData(Class<? extends BlockEntity> tile) {
        List<ITileInfoComponent<?>> list = cachedData.get(tile);
        if (list == null) {
            list = CollectionUtils.createList();
            cachedData.put(tile, list);
            ObjectSortedSet classes = CollectionUtils.createLinkedSet();
            ProbeTileListener.loopClass(tile, classes);
            for (Class clz : classes) {
                ITileInfoComponent<?> info = ProbePlugin.getComponents(clz);
                if (info == null) continue;
                list.add(info);
                info.disableClasses((Set<Class<?>>)classes);
            }
        }
        return list;
    }

    public static void loopClass(Class<?> clz, Set<Class<?>> set) {
        ObjectArrayList classes = new ObjectArrayList();
        try {
            while (clz != BlockEntity.class) {
                classes.add(clz);
                clz = clz.getSuperclass();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < classes.size(); ++i) {
            clz = (Class)classes.get(i);
            set.add(clz);
            for (Class<?> sub : clz.getInterfaces()) {
                ProbeTileListener.loopInterface(sub, set);
            }
        }
    }

    public static void loopInterface(Class<?> clz, Set<Class<?>> set) {
        ObjectArrayList stack = new ObjectArrayList();
        try {
            stack.push(clz);
            while (!stack.isEmpty()) {
                Object[] sub;
                Class entry = (Class)stack.pop();
                if (!set.add(entry) || (sub = entry.getInterfaces()) == null) continue;
                stack.addAll((ObjectList)ObjectArrayList.wrap((Object[])sub));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

