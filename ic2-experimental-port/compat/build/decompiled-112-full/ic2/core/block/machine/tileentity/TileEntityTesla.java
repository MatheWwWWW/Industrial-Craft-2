/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
package ic2.core.block.machine.tileentity;

import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.item.armor.ItemArmorHazmat;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TileEntityTesla
extends TileEntityBlock {
    protected final Redstone redstone;
    protected final Energy energy;
    private int ticker = IC2.random.nextInt(32);

    public TileEntityTesla() {
        this.redstone = this.addComponent(new Redstone(this));
        this.energy = this.addComponent(Energy.asBasicSink(this, 10000.0, 2));
    }

    @Override
    protected void updateEntityServer() {
        int damage;
        super.updateEntityServer();
        if (!this.redstone.hasRedstoneInput()) {
            return;
        }
        if (this.energy.useEnergy(1.0) && ++this.ticker % 32 == 0 && (damage = (int)this.energy.getEnergy() / 400) > 0 && this.shock(damage)) {
            this.energy.useEnergy(damage * 400);
        }
    }

    protected boolean shock(int damage) {
        int r = 4;
        World world = this.func_145831_w();
        List entities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((double)(this.field_174879_c.func_177958_n() - 4), (double)(this.field_174879_c.func_177956_o() - 4), (double)(this.field_174879_c.func_177952_p() - 4), (double)(this.field_174879_c.func_177958_n() + 4 + 1), (double)(this.field_174879_c.func_177956_o() + 4 + 1), (double)(this.field_174879_c.func_177952_p() + 4 + 1)));
        for (EntityLivingBase entity : entities) {
            if (ItemArmorHazmat.hasCompleteHazmat(entity) || !entity.func_70097_a((DamageSource)IC2DamageSource.electricity, (float)damage)) continue;
            if (world instanceof WorldServer) {
                WorldServer worldServer = (WorldServer)world;
                Random rnd = world.field_73012_v;
                for (int i = 0; i < damage; ++i) {
                    worldServer.func_180505_a(EnumParticleTypes.REDSTONE, true, entity.field_70165_t + (double)rnd.nextFloat() - 0.5, entity.field_70163_u + (double)(rnd.nextFloat() * 2.0f) - 1.0, entity.field_70161_v + (double)rnd.nextFloat() - 0.5, 0, 0.1, 0.1, 1.0, 1.0, new int[0]);
                }
            }
            return true;
        }
        return false;
    }
}

