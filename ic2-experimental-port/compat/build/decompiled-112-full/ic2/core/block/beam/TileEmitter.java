/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package ic2.core.block.beam;

import ic2.core.block.beam.EntityParticle;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraft.entity.Entity;

public class TileEmitter
extends TileEntityElectricMachine {
    private int progress;

    public TileEmitter() {
        super(5000, 1);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.progress < 100) {
            ++this.progress;
        }
        if (this.progress == 100 && this.func_145831_w().func_175640_z(this.field_174879_c)) {
            this.progress = 0;
            this.func_145831_w().func_72838_d((Entity)new EntityParticle(this));
        }
    }
}

