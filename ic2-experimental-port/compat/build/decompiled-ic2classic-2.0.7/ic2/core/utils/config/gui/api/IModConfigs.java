/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.IExtensionPoint
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.api.ConfigType;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IModConfig;
import java.util.List;
import net.minecraftforge.fml.IExtensionPoint;

public interface IModConfigs {
    public String getModName();

    public List<IModConfig> getConfigInstances(ConfigType var1);

    public BackgroundTexture getBackground();

    public record Background(BackgroundTexture texture) implements IExtensionPoint<Background>
    {
    }
}

