/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.forgespi.language.ModFileScanData
 *  net.minecraftforge.forgespi.language.ModFileScanData$AnnotationData
 *  org.objectweb.asm.Type
 */
package ic2.core.utils.plugins;

import ic2.api.addons.IC2Plugin;
import ic2.api.addons.IModule;
import ic2.core.IC2;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.impl.ReloadMode;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

public class PluginLoader {
    Map<String, IModule> modules = CollectionUtils.createMap();

    public void init(int apiVersion, ConfigSection section) {
        for (Module entry : this.getClasses()) {
            ConfigEntry.BoolValue value;
            IModule module = entry.getModule();
            int api = entry.getPlugin().requiredAPIVersion();
            if (api != 0 && api > apiVersion || !module.canLoad(FMLEnvironment.dist) || !(value = (ConfigEntry.BoolValue)section.addBool(entry.getPlugin().id(), true).setRequiredReload(ReloadMode.GAME)).get()) continue;
            this.modules.put(entry.getPlugin().id(), entry.getModule());
            entry.getModule().loadConfigs();
        }
    }

    public void fireEvent(Consumer<IModule> module) {
        this.modules.values().forEach(module);
    }

    public IModule getModule(String id) {
        return this.modules.get(id);
    }

    private List<Module> getClasses() {
        Type type = Type.getType(IC2Plugin.class);
        ObjectList lists = CollectionUtils.createList();
        for (ModFileScanData mod : ModList.get().getAllScanData()) {
            for (ModFileScanData.AnnotationData data : mod.getAnnotations()) {
                if (!data.annotationType().equals((Object)type)) continue;
                try {
                    Class<?> clz = Class.forName(data.memberName());
                    if (clz == null) continue;
                    IC2Plugin plug = clz.getAnnotation(IC2Plugin.class);
                    IC2.LOGGER.info("Loading Plugin: [name=" + plug.name() + ", version=" + plug.version() + "]");
                    IModule module = (IModule)clz.newInstance();
                    if (module == null) continue;
                    lists.add((Module)new Module(plug, module));
                }
                catch (Exception exception) {}
            }
        }
        return lists;
    }

    static class Module {
        IC2Plugin plugin;
        IModule module;

        public Module(IC2Plugin plugin, IModule module) {
            this.plugin = plugin;
            this.module = module;
        }

        public IModule getModule() {
            return this.module;
        }

        public IC2Plugin getPlugin() {
            return this.plugin;
        }
    }
}

