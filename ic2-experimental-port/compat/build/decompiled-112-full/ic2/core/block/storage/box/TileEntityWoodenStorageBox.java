/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.SoundType
 *  net.minecraft.entity.Entity
 */
package ic2.core.block.storage.box;

import ic2.core.block.storage.box.TileEntityStorageBox;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;

public class TileEntityWoodenStorageBox
extends TileEntityStorageBox {
    public TileEntityWoodenStorageBox() {
        super(27);
    }

    @Override
    protected SoundType getBlockSound(Entity entity) {
        return SoundType.field_185848_a;
    }
}

