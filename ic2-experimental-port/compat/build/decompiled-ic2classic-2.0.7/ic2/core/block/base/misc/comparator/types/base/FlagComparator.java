/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IWorkProvider;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import java.util.function.BooleanSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class FlagComparator
extends BaseComparator {
    final BooleanSupplier provider;
    final int off;
    final int on;

    public FlagComparator(String id, Component name, BooleanSupplier provider, int off, int on) {
        super(id, name);
        this.provider = provider;
        this.off = Mth.m_14045_((int)off, (int)0, (int)15);
        this.on = Mth.m_14045_((int)on, (int)0, (int)15);
    }

    public static FlagComparator createWorker(String id, Component name, IWorkProvider provider) {
        return new FlagComparator(id, name, provider::isWorking, 0, 15);
    }

    public static FlagComparator createTile(String id, Component name, BaseTileEntity tile) {
        return new FlagComparator(id, name, tile::isActive, 0, 15);
    }

    @Override
    protected int createValue() {
        return this.provider.getAsBoolean() ? this.on : this.off;
    }
}

