/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.api;

import ic2.core.utils.config.config.ConfigHandler;

public interface IConfigChangeListener {
    public void onConfigCreated(ConfigHandler var1);

    public void onConfigAdded(ConfigHandler var1);

    public void onConfigChanged(ConfigHandler var1);

    public void onConfigErrored(ConfigHandler var1);
}

