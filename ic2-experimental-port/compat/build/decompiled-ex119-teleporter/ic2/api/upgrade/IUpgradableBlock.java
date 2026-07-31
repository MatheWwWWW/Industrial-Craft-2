/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.upgrade;

import ic2.api.upgrade.UpgradableProperty;
import java.util.Set;

public interface IUpgradableBlock {
    public double getEnergy();

    public boolean useEnergy(double var1);

    public Set<UpgradableProperty> getUpgradableProperties();
}

