/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigChangeListener;
import ic2.core.utils.config.api.ILogger;
import ic2.core.utils.config.config.Config;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.config.ConfigSettings;
import ic2.core.utils.config.utils.AutomationType;
import ic2.core.utils.config.utils.MultilinePolicy;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileSystemWatcher {
    private WatchService watchService;
    private Map<String, ConfigHandler> configsByName = new Object2ObjectLinkedOpenHashMap();
    private Set<ConfigHandler> syncedConfigs = new ObjectOpenHashSet();
    private Map<Path, ConfigHandler> configs = new Object2ObjectLinkedOpenHashMap();
    private Map<WatchKey, Path> folders = new Object2ObjectLinkedOpenHashMap();
    private ILogger logger;
    private Path basePath;
    private IConfigChangeListener changeListener;
    private Object sync = new Object();

    public FileSystemWatcher(ILogger logger, Path basePath, IConfigChangeListener changedListener) {
        this.init(logger, basePath, changedListener);
    }

    protected void init(ILogger logger, Path basePath, IConfigChangeListener changedListener) {
        WatchService tmp;
        this.logger = logger;
        this.basePath = basePath;
        this.changeListener = changedListener;
        try {
            tmp = FileSystems.getDefault().newWatchService();
        }
        catch (IOException e) {
            tmp = null;
            logger.error("WatchService could not be created");
            logger.error(e);
        }
        this.watchService = tmp;
    }

    public Path getBasePath() {
        return this.basePath;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    void onConfigCreated(ConfigHandler handler) {
        if (this.changeListener != null) {
            this.changeListener.onConfigCreated(handler);
        }
    }

    void onConfigErrored(ConfigHandler handler) {
        if (this.changeListener != null) {
            this.changeListener.onConfigErrored(handler);
        }
    }

    public ConfigHandler createConfig(Config config) {
        return this.createConfig(config, ConfigSettings.of());
    }

    public ConfigHandler createConfig(Config config, ConfigSettings settings) {
        settings.withAutomations(AutomationType.AUTO_LOAD, AutomationType.AUTO_RELOAD, AutomationType.AUTO_SYNC).withBaseFolder(this.basePath).withType(ConfigType.SHARED).withSubFolder("").withLogger(this.logger).withMultiline(MultilinePolicy.ALWAYS_MULTILINE);
        return new ConfigHandler(config, settings).setOwner(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerSyncHandler(ConfigHandler handler) {
        Object object = this.sync;
        synchronized (object) {
            this.syncedConfigs.add(handler);
            this.configsByName.putIfAbsent(handler.getConfigIdentifer(), handler);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerConfigHandler(ConfigHandler handler) {
        Object object = this.sync;
        synchronized (object) {
            this.configsByName.putIfAbsent(handler.getConfigIdentifer(), handler);
            if (this.changeListener != null) {
                this.changeListener.onConfigAdded(handler);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerReloadHandler(Path configFile, ConfigHandler handler) {
        Object object = this.sync;
        synchronized (object) {
            if (this.configs.putIfAbsent(configFile, handler) != null) {
                this.logger.warn("tried to register a config that already registered at {} path", configFile);
                return;
            }
            this.configsByName.putIfAbsent(handler.getConfigIdentifer(), handler);
            if (!this.folders.containsValue(configFile.getParent())) {
                try {
                    this.folders.put(configFile.getParent().register(this.watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY), configFile.getParent());
                }
                catch (IOException e) {
                    this.logger.error("could not register WatchService for directory {}", configFile.getParent());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unregisterReloadHandler(Path configFile) {
        Object object = this.sync;
        synchronized (object) {
            if (this.configs.remove(configFile) == null) {
                return;
            }
            ObjectOpenHashSet parents = new ObjectOpenHashSet();
            for (Path path : this.configs.keySet()) {
                parents.add(path.getParent());
            }
            Iterator<Map.Entry<WatchKey, Path>> iter = this.folders.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<WatchKey, Path> entry = iter.next();
                if (parents.contains(entry.getValue())) continue;
                entry.getKey().cancel();
                iter.remove();
            }
        }
    }

    public ConfigHandler getConfig(String name) {
        return this.configsByName.get(name);
    }

    public void processFileSystemEvents() {
        if (this.watchService != null) {
            WatchKey key;
            while ((key = this.watchService.poll()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    ConfigHandler handler = this.configs.get(this.folders.get(key).resolve(((Path)event.context()).getFileName()));
                    if (handler == null || !handler.reload() || this.changeListener == null || !this.syncedConfigs.contains(handler)) continue;
                    this.changeListener.onConfigChanged(handler);
                }
                key.reset();
            }
        }
    }

    public List<ConfigHandler> getAllConfigs() {
        return new ObjectArrayList(this.configsByName.values());
    }

    public List<ConfigHandler> getConfigsToSync() {
        return new ObjectArrayList(this.syncedConfigs);
    }
}

