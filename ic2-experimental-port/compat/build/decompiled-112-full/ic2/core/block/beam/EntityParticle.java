/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.MoverType
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 */
package ic2.core.block.beam;

import ic2.core.block.beam.TileEmitter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityParticle
extends Entity {
    private static final double initialVelocity = 0.5;
    private static final double slowdown = 0.99;

    public EntityParticle(World world) {
        super(world);
        this.field_70145_X = true;
    }

    public EntityParticle(TileEmitter emitter) {
        this(emitter.func_145831_w());
        EnumFacing dir = emitter.getFacing();
        double x = (double)emitter.func_174877_v().func_177958_n() + 0.5 + (double)dir.func_82601_c() * 0.5;
        double y = (double)emitter.func_174877_v().func_177956_o() + 0.5 + (double)dir.func_96559_d() * 0.5;
        double z = (double)emitter.func_174877_v().func_177952_p() + 0.5 + (double)dir.func_82599_e() * 0.5;
        this.func_70107_b(x, y, z);
        this.field_70159_w = (double)dir.func_82601_c() * 0.5;
        this.field_70181_x = (double)dir.func_96559_d() * 0.5;
        this.field_70179_y = (double)dir.func_82599_e() * 0.5;
        this.func_70105_a(0.2f, 0.2f);
    }

    protected void func_70088_a() {
    }

    protected void func_70037_a(NBTTagCompound nbttagcompound) {
    }

    protected void func_70014_b(NBTTagCompound nbttagcompound) {
    }

    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.99;
        this.field_70181_x *= 0.99;
        this.field_70179_y *= 0.99;
        if (this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y < 1.0E-4) {
            this.func_70106_y();
        }
    }
}

