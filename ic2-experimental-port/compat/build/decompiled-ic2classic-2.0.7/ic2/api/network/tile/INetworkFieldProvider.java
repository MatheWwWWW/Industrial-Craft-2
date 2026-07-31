/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.network.tile;

import java.util.List;

public interface INetworkFieldProvider {
    public List<String> getNetworkFields();

    public List<String> getGuiFields();

    default public boolean isDefaultData(String fieldName) {
        return false;
    }
}

