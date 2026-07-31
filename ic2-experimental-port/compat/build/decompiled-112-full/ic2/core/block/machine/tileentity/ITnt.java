/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package ic2.core.block.machine.tileentity;

import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.EntityItnt;
import ic2.core.block.machine.tileentity.Explosive;
import net.minecraft.entity.EntityLivingBase;

public class ITnt
extends Explosive {
    @Override
    protected boolean explodeOnRemoval() {
        return true;
    }

    @Override
    protected EntityIC2Explosive getEntity(EntityLivingBase igniter) {
        return new EntityItnt(this.func_145831_w(), (double)this.field_174879_c.func_177958_n() + 0.5, (double)this.field_174879_c.func_177956_o() + 0.5, (double)this.field_174879_c.func_177952_p() + 0.5);
    }
}

