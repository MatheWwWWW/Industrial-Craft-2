/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.core.block.base.features;

import net.minecraft.network.chat.MutableComponent;

public interface IProfileListener {
    public void onProfile(long var1);

    public long getLag();

    public MutableComponent showResults();
}

