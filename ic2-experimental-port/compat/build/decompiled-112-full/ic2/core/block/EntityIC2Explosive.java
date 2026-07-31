/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.MoverType
 *  net.minecraft.init.Blocks
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityIC2Explosive
extends Entity {
    public DamageSource damageSource;
    public EntityLivingBase igniter;
    public int fuse = 80;
    public float explosivePower = 4.0f;
    public int radiationRange = 0;
    public float dropRate = 0.3f;
    public float damageVsEntitys = 1.0f;
    public IBlockState renderBlockState = Blocks.field_150346_d.func_176223_P();

    public EntityIC2Explosive(World world) {
        super(world);
        this.field_70156_m = true;
        this.func_70105_a(0.98f, 0.98f);
    }

    public EntityIC2Explosive(World world, double x, double y, double z, int fuse, float power, float dropRate, float damage, IBlockState renderBlockState, int radiationRange) {
        this(world);
        this.func_70107_b(x, y, z);
        float f = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.field_70159_w = -MathHelper.func_76126_a((float)(f * 3.141593f / 180.0f)) * 0.02f;
        this.field_70181_x = 0.2f;
        this.field_70179_y = -MathHelper.func_76134_b((float)(f * 3.141593f / 180.0f)) * 0.02f;
        this.field_70169_q = x;
        this.field_70167_r = y;
        this.field_70166_s = z;
        this.fuse = fuse;
        this.explosivePower = power;
        this.radiationRange = radiationRange;
        this.dropRate = dropRate;
        this.damageVsEntitys = damage;
        this.renderBlockState = renderBlockState;
    }

    protected void func_70088_a() {
    }

    protected boolean func_70041_e_() {
        return false;
    }

    public boolean func_70067_L() {
        return !this.field_70128_L;
    }

    public void func_70071_h_() {
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.field_70181_x -= 0.04;
        this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.98;
        this.field_70181_x *= 0.98;
        this.field_70179_y *= 0.98;
        if (this.field_70122_E) {
            this.field_70159_w *= 0.7;
            this.field_70179_y *= 0.7;
            this.field_70181_x *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.func_70106_y();
            if (IC2.platform.isSimulating()) {
                this.explode();
            }
        } else {
            this.func_130014_f_().func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.5, this.field_70161_v, 0.0, 0.0, 0.0, new int[0]);
        }
    }

    private void explode() {
        ExplosionIC2 explosion = new ExplosionIC2(this.func_130014_f_(), this, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosivePower, this.dropRate, this.radiationRange > 0 ? ExplosionIC2.Type.Nuclear : ExplosionIC2.Type.Normal, this.igniter, this.radiationRange);
        explosion.doExplosion();
    }

    protected void func_70014_b(NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74774_a("Fuse", (byte)this.fuse);
    }

    protected void func_70037_a(NBTTagCompound nbttagcompound) {
        this.fuse = nbttagcompound.func_74771_c("Fuse");
    }

    public EntityIC2Explosive setIgniter(EntityLivingBase igniter1) {
        this.igniter = igniter1;
        return this;
    }
}

