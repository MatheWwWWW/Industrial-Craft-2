/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.api.ILogger;
import ic2.core.utils.config.api.SimpleConfigProxy;
import ic2.core.utils.config.utils.AutomationType;
import ic2.core.utils.config.utils.MultilinePolicy;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.EnumSet;

public class ConfigSettings {
    AutomationType[] auto;
    MultilinePolicy policy;
    ConfigType type;
    ILogger logger;
    IConfigProxy proxy;
    String subFolder;

    private ConfigSettings() {
    }

    public static ConfigSettings of() {
        return new ConfigSettings();
    }

    public static ConfigSettings withPath(Path baseFolder) {
        return new ConfigSettings().withBaseFolder(baseFolder);
    }

    public static ConfigSettings withFolderProxy(IConfigProxy proxy) {
        return new ConfigSettings().withProxy(proxy);
    }

    public static ConfigSettings withConfigType(ConfigType type) {
        return new ConfigSettings().withType(type);
    }

    public static ConfigSettings withoutSettings() {
        return new ConfigSettings().noAutomations();
    }

    public static ConfigSettings withSettings(AutomationType type) {
        return new ConfigSettings().withAutomations(type);
    }

    public static ConfigSettings withLog(ILogger logger) {
        return new ConfigSettings().withLogger(logger);
    }

    public static ConfigSettings withFolder(String subFolder) {
        return new ConfigSettings().withSubFolder(subFolder);
    }

    public static ConfigSettings withLinePolicy(MultilinePolicy policy) {
        return new ConfigSettings().withMultiline(policy);
    }

    public ConfigSettings withBaseFolder(Path baseFolder) {
        return this.withProxy(new SimpleConfigProxy(baseFolder));
    }

    public ConfigSettings withProxy(IConfigProxy proxy) {
        if (this.proxy == null) {
            this.proxy = proxy;
        }
        return this;
    }

    public ConfigSettings noAutomations() {
        if (this.auto == null) {
            this.auto = new AutomationType[0];
        }
        return this;
    }

    public ConfigSettings withAutomations(AutomationType ... auto) {
        if (this.auto == null) {
            this.auto = auto;
        }
        return this;
    }

    public ConfigSettings withMultiline(MultilinePolicy policy) {
        if (this.policy == null) {
            this.policy = policy;
        }
        return this;
    }

    public ConfigSettings withType(ConfigType type) {
        if (this.type == null) {
            this.type = type;
        }
        return this;
    }

    public ConfigSettings withLogger(ILogger logger) {
        if (this.logger == null) {
            this.logger = logger;
        }
        return this;
    }

    public ConfigSettings withSubFolder(String subFolder) {
        if (this.subFolder == null) {
            this.subFolder = subFolder;
        }
        return this;
    }

    public IConfigProxy getProxy() {
        return this.proxy;
    }

    public ILogger getLogger() {
        return this.logger;
    }

    public String getSubFolder() {
        return this.subFolder;
    }

    public ConfigType getType() {
        return this.type;
    }

    public EnumSet<AutomationType> getAutomationType() {
        return EnumSet.copyOf(Arrays.asList(this.auto));
    }

    public MultilinePolicy getMultilinePolicy() {
        return this.policy;
    }
}

