/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 */
package ic2.core.utils.helpers.capabilities;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IToggleableCapabilityProvider
extends ICapabilityProvider {
    public boolean isActive();

    public void setActive(boolean var1);
}

