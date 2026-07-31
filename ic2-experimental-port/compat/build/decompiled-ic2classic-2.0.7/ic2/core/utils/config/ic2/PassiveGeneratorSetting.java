/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.ic2;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;

public class PassiveGeneratorSetting {
    public ConfigEntry.IntValue base;
    public ConfigEntry.IntValue passive;

    public PassiveGeneratorSetting(ConfigSection parent, String prefix, int baseValue, int passiveValue) {
        ConfigSection section = parent.addSubSection(prefix);
        this.base = (ConfigEntry.IntValue)section.addInt("base", baseValue, "Base Production of the Generator").setServerSynced();
        this.passive = (ConfigEntry.IntValue)section.addInt("passive", passiveValue, "How much passive energy should be required per fuel. Lower => more Production").setServerSynced();
    }

    public int getProduction() {
        return this.base.get();
    }

    public int getPassiveProduction() {
        return this.passive.get();
    }
}

