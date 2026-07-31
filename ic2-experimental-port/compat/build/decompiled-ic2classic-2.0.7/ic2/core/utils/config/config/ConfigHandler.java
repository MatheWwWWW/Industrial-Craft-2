/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.chars.Char2ObjectMap
 *  it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.api.ILogger;
import ic2.core.utils.config.config.Config;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.config.ConfigSettings;
import ic2.core.utils.config.config.FileSystemWatcher;
import ic2.core.utils.config.utils.AutomationType;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.MultilinePolicy;
import ic2.core.utils.config.utils.ParseExpection;
import ic2.core.utils.config.utils.ParseResult;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

public final class ConfigHandler {
    private Path cfgDir;
    private Path configFile;
    private boolean isLoaded;
    private boolean registered;
    private int wasSaving = 0;
    private final String subFolder;
    private final Config config;
    private final EnumSet<AutomationType> setting;
    private final MultilinePolicy policy;
    public final ConfigType type;
    private final List<ConfigError> errors = new ObjectArrayList();
    private final IConfigProxy proxy;
    private final ILogger logger;
    private FileSystemWatcher owner;
    private List<Runnable> loadedListeners = new ObjectArrayList();
    private Char2ObjectMap<IConfigParser> parsers = new Char2ObjectOpenHashMap();

    ConfigHandler(Config config, ConfigSettings settings) {
        this(settings.getSubFolder(), settings.getProxy(), settings.getLogger(), config, settings.getAutomationType(), settings.getMultilinePolicy(), settings.getType());
    }

    ConfigHandler(String subFolder, IConfigProxy proxy, ILogger logger, Config config, EnumSet<AutomationType> setting, MultilinePolicy policy, ConfigType type) {
        this.config = config;
        String tmp = subFolder.trim().replace("\\\\", "/").replace("\\", "/");
        if (tmp.endsWith("/")) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        this.subFolder = tmp;
        this.logger = logger;
        this.policy = policy;
        this.setting = setting;
        this.proxy = proxy;
        this.type = type;
        this.parsers.put('I', ConfigEntry.IntValue::parse);
        this.parsers.put('D', ConfigEntry.DoubleValue::parse);
        this.parsers.put('B', ConfigEntry.BoolValue::parse);
        this.parsers.put('S', ConfigEntry.StringValue::parse);
        this.parsers.put('A', ConfigEntry.ArrayValue::parse);
        this.parsers.put('E', ConfigEntry.StringValue::parse);
        this.parsers.put('p', ConfigEntry.StringValue::parse);
        this.parsers.put('P', ConfigEntry.StringValue::parse);
    }

    ConfigHandler setOwner(FileSystemWatcher owner) {
        this.owner = owner;
        if (owner != null) {
            owner.onConfigCreated(this);
        }
        return this;
    }

    public void addTempParser(char id) {
        this.addParser(id, ConfigEntry.StringValue::parse);
    }

    public void addParser(char id, IConfigParser parser) {
        if (!(id >= 'A' && id <= 'Z' || id >= 'a' && id <= 'z')) {
            throw new IllegalArgumentException("Character must be [a-zA-Z]");
        }
        this.parsers.putIfAbsent(id, (Object)parser);
    }

    public IConfigProxy getProxy() {
        return this.proxy;
    }

    public ConfigType getConfigType() {
        return this.type;
    }

    public MultilinePolicy getMultilinePolicy() {
        return this.policy;
    }

    public Config getConfig() {
        return this.config;
    }

    public String getSubFolder() {
        return this.subFolder;
    }

    public Path createConfigFile(Path baseFolder) {
        return (!this.subFolder.isEmpty() ? baseFolder.resolve(this.subFolder) : baseFolder).resolve(this.config.getName().concat(".cfg"));
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public Path getCfgDir() {
        return this.cfgDir;
    }

    public Path getConfigFile() {
        return this.configFile;
    }

    public String getConfigIdentifer() {
        return this.subFolder + "/" + this.config.getName();
    }

    public boolean hasErrors() {
        return this.errors.size() > 0;
    }

    public List<ConfigError> getErrors() {
        return this.errors;
    }

    public void register() {
        if (this.owner != null) {
            this.owner.registerConfigHandler(this);
            this.registered = true;
            if (!this.proxy.isDynamicProxy() && this.setting.contains((Object)AutomationType.AUTO_LOAD)) {
                this.load();
            }
        }
    }

    public void load() {
        this.findConfigFile();
        if (this.owner != null) {
            if (this.setting.contains((Object)AutomationType.AUTO_SYNC)) {
                this.owner.registerSyncHandler(this);
            }
            if (this.setting.contains((Object)AutomationType.AUTO_RELOAD)) {
                this.owner.registerReloadHandler(this.configFile, this);
            }
        }
        if (this.loadInternally()) {
            this.save();
        }
        this.isLoaded = true;
    }

    public boolean reload() {
        if (!this.isLoaded) {
            return false;
        }
        if (this.wasSaving > 0) {
            --this.wasSaving;
            return false;
        }
        this.loadInternally();
        return true;
    }

    public void unload() {
        this.isLoaded = false;
        if (this.owner != null && this.setting.contains((Object)AutomationType.AUTO_RELOAD)) {
            this.owner.unregisterReloadHandler(this.configFile);
        }
    }

    private void findConfigFile() {
        int i;
        List<Path> baseFolders = this.proxy.getBasePaths();
        if (baseFolders.isEmpty()) {
            throw new IllegalStateException("Proxy has no Folders");
        }
        if (baseFolders.size() == 1) {
            this.configFile = this.createConfigFile(baseFolders.get(0));
            this.cfgDir = this.configFile.getParent();
            Helpers.ensureFolder(this.cfgDir);
            return;
        }
        int m = i = baseFolders.size() - 1;
        while (i >= 0) {
            Path file = this.createConfigFile(baseFolders.get(i));
            if (Files.notExists(file, new LinkOption[0])) {
                if (i == m) {
                    this.save(file);
                } else {
                    Helpers.copyFile(this.createConfigFile(baseFolders.get(i + 1)), file);
                }
            }
            --i;
        }
        this.configFile = this.createConfigFile(baseFolders.get(0));
        this.cfgDir = this.configFile.getParent();
    }

    public void addLoadedListener(Runnable listener) {
        this.loadedListeners.add(listener);
    }

    public void onSynced() {
        for (Runnable r : this.loadedListeners) {
            r.run();
        }
    }

    private int handleEntry(ConfigSection currentSection, List<String> lines, int index, String line, String[] comment, boolean logErrors) {
        if (currentSection == null) {
            this.logger.error("config entry not in section: {}", line);
            return 0;
        }
        String[] entryData = Helpers.trimArray(line.split("[:=]", 3));
        if (entryData.length != 3) {
            this.logger.error("invalid config entry: {}", line);
            return 0;
        }
        int extra = 0;
        if (entryData[2].length() > 0 && entryData[2].charAt(0) == '<') {
            if (entryData[2].endsWith(">")) {
                entryData[2] = entryData[2].substring(1, entryData[2].length() - 1);
            } else {
                StringBuilder builder = new StringBuilder();
                extra += this.findString(entryData[2], lines, index, builder);
                entryData[2] = builder.toString();
            }
        }
        try {
            ConfigEntry<?> entry = currentSection.getEntry(entryData[1]);
            if (entry == null) {
                IConfigParser parser = (IConfigParser)this.parsers.get(line.charAt(0));
                if (parser == null) {
                    this.logger.warn("config entry is not registered and no parser found: {}", line);
                    return extra;
                }
                ParseResult<ConfigEntry<?>> result = parser.parse(entryData[1], entryData[2], comment);
                entry = result.getValue();
                currentSection.addParsed(entry);
                if (result.hasError() && logErrors) {
                    this.logger.warn("couldn't parse value: {}", result.getValue());
                    this.errors.add(new ConfigError(entry, result.getError()));
                }
                return extra;
            }
            entry.parseComment(comment);
            if (line.charAt(0) == entry.getPrefix()) {
                ParseResult<String> result = entry.deserializeValue(entryData[2]);
                if (result.hasError() && logErrors) {
                    this.logger.warn("couldn't parse value: {}", result.getValue());
                    this.errors.add(new ConfigError(entry, result.getError()));
                }
            } else {
                this.logger.warn("config entry has wrong type: {}", line);
            }
        }
        catch (Throwable e) {
            this.logger.error("Crash during parsing. THIS SHOULD NEVER HAPPEN!", e);
        }
        return extra;
    }

    private int findString(String base, List<String> lines, int index, StringBuilder builder) {
        builder.append(base.substring(1));
        int stepsDone = 0;
        while (index + 1 < lines.size()) {
            String data;
            ++stepsDone;
            if ((data = lines.get(++index).trim()).endsWith(">")) {
                builder.append(data.substring(0, data.length() - 1));
                break;
            }
            if (data.length() > 1 && data.charAt(1) == ':' && this.parsers.containsKey(data.charAt(0))) {
                --stepsDone;
                break;
            }
            if (data.isEmpty()) continue;
            builder.append(data);
        }
        return stepsDone;
    }

    private boolean loadInternally() {
        if (Files.notExists(this.configFile, new LinkOption[0])) {
            return true;
        }
        try {
            this.errors.clear();
            ConfigHandler.load(this, this.config, Files.readAllLines(this.configFile), true);
            for (Runnable r : this.loadedListeners) {
                r.run();
            }
            if (this.errors.size() > 0 && this.owner != null) {
                this.owner.onConfigErrored(this);
            }
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean load(ConfigHandler handler, Config output, List<String> linesToParse, boolean logErrors) {
        ConfigSection currentSection = null;
        ObjectArrayList comments = new ObjectArrayList();
        int m = linesToParse.size();
        block4: for (int i = 0; i < m; ++i) {
            String line = linesToParse.get(i).trim();
            if (line.length() == 0) continue;
            switch (line.charAt(0)) {
                case '[': {
                    currentSection = output.getSectionRecursive(line.substring(1, line.length() - 1).split("\\."));
                    currentSection.parseComment(comments.toArray(new String[comments.size()]));
                    comments.clear();
                    continue block4;
                }
                case '#': {
                    if (line.charAt(1) == '\u200b') continue block4;
                    comments.add(line.substring(1).trim());
                    continue block4;
                }
                default: {
                    i += handler.handleEntry(currentSection, linesToParse, i, line, comments.toArray(new String[comments.size()]), logErrors);
                    comments.clear();
                }
            }
        }
        return true;
    }

    public void save() {
        this.save(this.configFile);
    }

    private void save(Path file) {
        ++this.wasSaving;
        try (BufferedWriter writer = Files.newBufferedWriter(file, new OpenOption[0]);){
            writer.write(this.config.serialize(this.policy));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public static interface IConfigParser {
        public ParseResult<? extends ConfigEntry<?>> parse(String var1, String var2, String[] var3);
    }

    public class ConfigError {
        ConfigEntry<?> entry;
        ParseExpection error;

        public ConfigError(ConfigEntry<?> entry, ParseExpection error) {
            this.entry = entry;
            this.error = error;
        }

        public ConfigEntry<?> getEntry() {
            return this.entry;
        }

        public ParseExpection getError() {
            return this.error;
        }
    }
}

