/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 */
package ic2.core.utils.config.gui.api;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

public interface IRequestScreen {
    public void receiveConfigData(UUID var1, FriendlyByteBuf var2);
}

