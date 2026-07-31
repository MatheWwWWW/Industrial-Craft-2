/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.MultiPartEntityPart
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EntityDamageSourceIndirect
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.world.BlockEvent$BreakEvent
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.registry.IThrowableEntity
 */
package ic2.core.item.tool;

import ic2.api.event.LaserEvent;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.Ic2Player;
import ic2.core.block.MaterialIC2TNT;
import ic2.core.ref.BlockName;
import ic2.core.util.StackUtil;
import ic2.core.util.Vector3;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntityMiningLaser
extends Entity
implements IThrowableEntity {
    public float range = 0.0f;
    public float power = 0.0f;
    public int blockBreaks = 0;
    public boolean explosive = false;
    public static final double laserSpeed = 1.0;
    public EntityLivingBase owner;
    public boolean headingSet = false;
    public boolean smelt = false;
    private int ticksInAir = 0;

    public EntityMiningLaser(World world) {
        super(world);
        this.func_70105_a(0.8f, 0.8f);
    }

    public EntityMiningLaser(World world, Vector3 start, Vector3 dir, EntityLivingBase owner, float range, float power, int blockBreaks, boolean explosive) {
        super(world);
        this.owner = owner;
        this.func_70105_a(0.8f, 0.8f);
        this.func_70107_b(start.x, start.y, start.z);
        this.setLaserHeading(dir.x, dir.y, dir.z, 1.0);
        this.range = range;
        this.power = power;
        this.blockBreaks = blockBreaks;
        this.explosive = explosive;
    }

    protected void func_70088_a() {
    }

    public void setLaserHeading(double motionX, double motionY, double motionZ, double speed) {
        double currentSpeed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        this.field_70159_w = motionX / currentSpeed * speed;
        this.field_70181_x = motionY / currentSpeed * speed;
        this.field_70179_y = motionZ / currentSpeed * speed;
        this.field_70126_B = this.field_70177_z = (float)Math.toDegrees(Math.atan2(motionX, motionZ));
        this.field_70127_C = this.field_70125_A = (float)Math.toDegrees(Math.atan2(motionY, Math.sqrt(motionX * motionX + motionZ * motionZ)));
        this.headingSet = true;
    }

    public void func_70016_h(double motionX, double motionY, double motionZ) {
        this.setLaserHeading(motionX, motionY, motionZ, 1.0);
    }

    /*
     * Unable to fully structure code
     */
    public void func_70071_h_() {
        block12: {
            block11: {
                super.func_70071_h_();
                if (IC2.platform.isSimulating() && (this.range < 1.0f || this.power <= 0.0f || this.blockBreaks <= 0)) {
                    if (this.explosive) {
                        this.explode();
                    }
                    this.func_70106_y();
                    return;
                }
                ++this.ticksInAir;
                oldPosition = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                newPosition = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
                world = this.func_130014_f_();
                result = world.func_147447_a(oldPosition, newPosition, false, true, false);
                oldPosition = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                newPosition = result != null ? new Vec3d(result.field_72307_f.field_72450_a, result.field_72307_f.field_72448_b, result.field_72307_f.field_72449_c) : new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
                hitEntity = null;
                list = world.func_72839_b((Entity)this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0));
                distance = 0.0;
                for (Entity entity : list) {
                    if (!entity.func_70067_L() || entity == this.owner && this.ticksInAir < 5 || (intercept = (hitBox = entity.func_174813_aQ().func_186662_g(0.3)).func_72327_a(oldPosition, newPosition)) == null || !((newDistance = oldPosition.func_72438_d(intercept.field_72307_f)) < distance) && distance != 0.0) continue;
                    hitEntity = entity;
                    distance = newDistance;
                }
                blockHit = result;
                if (hitEntity != null) {
                    result = new RayTraceResult(hitEntity);
                }
                if (result == null || result.field_72313_a == RayTraceResult.Type.MISS || world.field_72995_K) break block11;
                if (this.explosive) {
                    this.explode();
                    this.func_70106_y();
                    return;
                }
                switch (1.$SwitchMap$net$minecraft$util$math$RayTraceResult$Type[result.field_72313_a.ordinal()]) {
                    case 1: {
                        if (this.hitEntity(result.field_72308_g)) break block12;
                        if (blockHit != null) ** GOTO lbl36
                        this.power -= 0.5f;
                        break block12;
lbl36:
                        // 1 sources

                        result = blockHit;
                    }
                    case 2: {
                        if (!this.hitBlock(result.func_178782_a(), result.field_178784_b)) {
                            this.power -= 0.5f;
                        }
                        break block12;
                    }
                    default: {
                        throw new RuntimeException("invalid hit type: " + result.field_72313_a);
                    }
                }
            }
            this.power -= 0.5f;
        }
        this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        this.range = (float)((double)this.range - Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y));
        if (this.func_70090_H()) {
            this.func_70106_y();
        }
    }

    private void explode() {
        World world = this.func_130014_f_();
        LaserEvent.LaserExplodesEvent event = new LaserEvent.LaserExplodesEvent(world, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, 5.0f, 0.85f, 0.55f);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            this.func_70106_y();
            return;
        }
        this.copyDataFromEvent(event);
        ExplosionIC2 explosion = new ExplosionIC2(world, this, this.field_70165_t, this.field_70163_u, this.field_70161_v, event.explosionPower, event.explosionDropRate);
        explosion.doExplosion();
    }

    private boolean hitEntity(Entity entity) {
        LaserEvent.LaserHitsEntityEvent event = new LaserEvent.LaserHitsEntityEvent(this.func_130014_f_(), this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, entity);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            if (event.passThrough) {
                return false;
            }
            this.func_70106_y();
            return true;
        }
        this.copyDataFromEvent(event);
        entity = event.hitEntity;
        int damage = (int)this.power;
        if (damage > 0) {
            entity.func_70015_d(damage * (this.smelt ? 2 : 1));
            if (entity.func_70097_a(new EntityDamageSourceIndirect("arrow", (Entity)this, (Entity)this.owner).func_76349_b(), (float)damage) && (this.owner instanceof EntityPlayer && entity instanceof EntityDragon && ((EntityDragon)entity).func_110143_aJ() <= 0.0f || entity instanceof MultiPartEntityPart && ((MultiPartEntityPart)entity).field_70259_a instanceof EntityDragon && ((EntityLivingBase)((MultiPartEntityPart)entity).field_70259_a).func_110143_aJ() <= 0.0f)) {
                IC2.achievements.issueAchievement((EntityPlayer)this.owner, "killDragonMiningLaser");
            }
        }
        this.func_70106_y();
        return true;
    }

    private boolean hitBlock(BlockPos pos, EnumFacing side) {
        EntityPlayer playerOwner;
        World world = this.func_130014_f_();
        LaserEvent.LaserHitsBlockEvent event = new LaserEvent.LaserHitsBlockEvent(world, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, pos, side, 0.9f, true, true);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            this.func_70106_y();
            return true;
        }
        this.copyDataFromEvent(event);
        IBlockState state = world.func_180495_p(event.pos);
        Block block = state.func_177230_c();
        EntityPlayer entityPlayer = playerOwner = this.owner instanceof EntityPlayer ? (EntityPlayer)this.owner : Ic2Player.get(world);
        if (MinecraftForge.EVENT_BUS.post((Event)new BlockEvent.BreakEvent(world, pos, state, playerOwner))) {
            this.func_70106_y();
            return true;
        }
        if (block.isAir(state, (IBlockAccess)world, event.pos) || block == Blocks.field_150359_w || block == Blocks.field_150410_aZ || block == BlockName.glass.getInstance()) {
            return false;
        }
        if (world.field_72995_K) {
            return true;
        }
        float hardness = state.func_185887_b(world, event.pos);
        if (hardness < 0.0f) {
            this.func_70106_y();
            return true;
        }
        this.power -= hardness / 1.5f;
        if (this.power < 0.0f) {
            return true;
        }
        ArrayList<ItemStack> replacements = new ArrayList<ItemStack>();
        if (state.func_185904_a() == Material.field_151590_u || state.func_185904_a() == MaterialIC2TNT.instance) {
            block.func_180652_a(world, event.pos, new Explosion(world, (Entity)this, (double)event.pos.func_177958_n() + 0.5, (double)event.pos.func_177956_o() + 0.5, (double)event.pos.func_177952_p() + 0.5, 1.0f, false, true));
        } else if (this.smelt) {
            if (state.func_185904_a() == Material.field_151575_d) {
                event.dropBlock = false;
            } else {
                for (ItemStack isa : StackUtil.getDrops((IBlockAccess)world, event.pos, state, block, 0)) {
                    ItemStack is = FurnaceRecipes.func_77602_a().func_151395_a(isa);
                    if (StackUtil.isEmpty(is)) continue;
                    replacements.add(is);
                }
                event.dropBlock = replacements.isEmpty();
            }
        }
        if (event.removeBlock) {
            if (event.dropBlock) {
                block.func_180653_a(world, event.pos, state, event.dropChance, 0);
            }
            world.func_175698_g(event.pos);
            for (ItemStack replacement : replacements) {
                if (!StackUtil.placeBlock(replacement, world, event.pos)) {
                    StackUtil.dropAsEntity(world, event.pos, replacement);
                }
                this.power = 0.0f;
            }
            if (world.field_73012_v.nextInt(10) == 0 && state.func_185904_a().func_76217_h()) {
                world.func_175656_a(event.pos, Blocks.field_150480_ab.func_176223_P());
            }
        }
        --this.blockBreaks;
        return true;
    }

    public void func_70014_b(NBTTagCompound nbttagcompound) {
    }

    public void func_70037_a(NBTTagCompound nbttagcompound) {
    }

    void copyDataFromEvent(LaserEvent event) {
        this.owner = event.owner;
        this.range = event.range;
        this.power = event.power;
        this.blockBreaks = event.blockBreaks;
        this.explosive = event.explosive;
        this.smelt = event.smelt;
    }

    public Entity getThrower() {
        return this.owner;
    }

    public void setThrower(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.owner = (EntityLivingBase)entity;
        }
    }
}

