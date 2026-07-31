/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.boss.EntityWither
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityMinecart
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTeleporter
extends TileEntityBlock
implements INetworkTileEntityEventListener {
    private BlockPos target;
    private AudioSource audioSource = null;
    private int targetCheckTicker = IC2.random.nextInt(1024);
    private int cooldown = 0;
    protected final ComparatorEmitter comparator = this.addComponent(new ComparatorEmitter(this));
    private static final int EventTeleport = 0;

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        if (nbt.func_74764_b("targetX")) {
            this.target = new BlockPos(nbt.func_74762_e("targetX"), nbt.func_74762_e("targetY"), nbt.func_74762_e("targetZ"));
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        if (this.target != null) {
            nbt.func_74768_a("targetX", this.target.func_177958_n());
            nbt.func_74768_a("targetY", this.target.func_177956_o());
            nbt.func_74768_a("targetZ", this.target.func_177952_p());
        }
        return nbt;
    }

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateComparatorLevel();
    }

    @Override
    protected void updateEntityServer() {
        World world;
        boolean coolingDown;
        super.updateEntityServer();
        boolean bl = coolingDown = this.cooldown > 0;
        if (coolingDown) {
            --this.cooldown;
            IC2.network.get(true).updateTileEntityField(this, "cooldown");
        }
        if ((world = this.func_145831_w()).func_175640_z(this.field_174879_c) && this.target != null) {
            this.setActive(true);
            List entitiesNearby = coolingDown ? Collections.emptyList() : world.func_72872_a(Entity.class, new AxisAlignedBB((double)(this.field_174879_c.func_177958_n() - 1), (double)this.field_174879_c.func_177956_o(), (double)(this.field_174879_c.func_177952_p() - 1), (double)(this.field_174879_c.func_177958_n() + 2), (double)(this.field_174879_c.func_177956_o() + 3), (double)(this.field_174879_c.func_177952_p() + 2)));
            if (!entitiesNearby.isEmpty() && this.verifyTarget()) {
                double minDistanceSquared = Double.MAX_VALUE;
                Entity closestEntity = null;
                for (Entity entity : entitiesNearby) {
                    double distSquared;
                    if (entity.func_184187_bx() != null || !((distSquared = this.field_174879_c.func_177957_d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v)) < minDistanceSquared)) continue;
                    minDistanceSquared = distSquared;
                    closestEntity = entity;
                }
                assert (closestEntity != null);
                this.teleport(closestEntity, Math.sqrt(this.field_174879_c.func_177951_i((Vec3i)this.target)));
            } else if (++this.targetCheckTicker % 1024 == 0) {
                this.verifyTarget();
            }
        } else {
            this.setActive(false);
        }
    }

    private boolean verifyTarget() {
        if (this.func_145831_w().func_175625_s(this.target) instanceof TileEntityTeleporter) {
            return true;
        }
        this.target = null;
        this.updateComparatorLevel();
        this.setActive(false);
        return false;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            if (this.cooldown > 0) {
                this.spawnGreenParticles(2, this.field_174879_c);
            } else {
                this.spawnBlueParticles(2, this.field_174879_c);
            }
        }
    }

    private void updateComparatorLevel() {
        int targetLevel = this.target != null ? 15 : 0;
        this.comparator.setLevel(targetLevel);
    }

    public void teleport(Entity user, double distance) {
        int weight = this.getWeightOf(user);
        if (weight == 0) {
            return;
        }
        int energyCost = (int)((double)weight * Math.pow(distance + 10.0, 0.7) * 5.0);
        if (energyCost > this.getAvailableEnergy()) {
            return;
        }
        this.consumeEnergy(energyCost);
        if (user instanceof EntityPlayerMP) {
            ((EntityPlayerMP)user).func_70634_a((double)this.target.func_177958_n() + 0.5, (double)this.target.func_177956_o() + 1.5 + user.func_70033_W(), (double)this.target.func_177952_p() + 0.5);
        } else {
            user.func_70080_a((double)this.target.func_177958_n() + 0.5, (double)this.target.func_177956_o() + 1.5 + user.func_70033_W(), (double)this.target.func_177952_p() + 0.5, user.field_70177_z, user.field_70125_A);
        }
        TileEntity te = this.func_145831_w().func_175625_s(this.target);
        assert (te instanceof TileEntityTeleporter);
        ((TileEntityTeleporter)te).onTeleportTo(this, user);
        IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
        if (user instanceof EntityPlayer && distance >= 1000.0) {
            IC2.achievements.issueAchievement((EntityPlayer)user, "teleportFarAway");
        }
    }

    public void spawnBlueParticles(int n, BlockPos pos) {
        this.spawnParticles(n, pos, -1, 0, 1);
    }

    public void spawnGreenParticles(int n, BlockPos pos) {
        this.spawnParticles(n, pos, -1, 1, 0);
    }

    private void spawnParticles(int n, BlockPos pos, int red, int green, int blue) {
        World world = this.func_145831_w();
        Random rnd = world.field_73012_v;
        for (int i = 0; i < n; ++i) {
            world.func_175688_a(EnumParticleTypes.REDSTONE, (double)((float)pos.func_177958_n() + rnd.nextFloat()), (double)((float)(pos.func_177956_o() + 1) + rnd.nextFloat()), (double)((float)pos.func_177952_p() + rnd.nextFloat()), (double)red, (double)green, (double)blue, new int[0]);
            world.func_175688_a(EnumParticleTypes.REDSTONE, (double)((float)pos.func_177958_n() + rnd.nextFloat()), (double)((float)(pos.func_177956_o() + 2) + rnd.nextFloat()), (double)((float)pos.func_177952_p() + rnd.nextFloat()), (double)red, (double)green, (double)blue, new int[0]);
        }
    }

    public void consumeEnergy(int energy) {
        World world = this.func_145831_w();
        LinkedList<IEnergyStorage> energySources = new LinkedList<IEnergyStorage>();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            IEnergyStorage energySource;
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof IEnergyStorage) || !(energySource = (IEnergyStorage)target).isTeleporterCompatible(dir.func_176734_d()) || energySource.getStored() <= 0) continue;
            energySources.add(energySource);
        }
        while (energy > 0) {
            int drain = (energy + energySources.size() - 1) / energySources.size();
            Iterator it = energySources.iterator();
            while (it.hasNext()) {
                IEnergyStorage energySource = (IEnergyStorage)it.next();
                if (drain > energy) {
                    drain = energy;
                }
                if (energySource.getStored() <= drain) {
                    energy -= energySource.getStored();
                    energySource.setStored(0);
                    it.remove();
                    continue;
                }
                energy -= drain;
                energySource.addEnergy(-drain);
            }
        }
    }

    public int getAvailableEnergy() {
        World world = this.func_145831_w();
        int energy = 0;
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            IEnergyStorage storage;
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof IEnergyStorage) || !(storage = (IEnergyStorage)target).isTeleporterCompatible(dir.func_176734_d())) continue;
            energy += storage.getStored();
        }
        return energy;
    }

    public int getWeightOf(Entity user) {
        boolean teleporterUseInventoryWeight = ConfigUtil.getBool(MainConfig.get(), "balance/teleporterUseInventoryWeight");
        int weight = 0;
        if (user instanceof EntityItem) {
            ItemStack is = ((EntityItem)user).func_92059_d();
            weight += 100 * StackUtil.getSize(is) / is.func_77976_d();
        } else if (user instanceof EntityAnimal || user instanceof EntityMinecart || user instanceof EntityBoat) {
            weight += 100;
        } else if (user instanceof EntityPlayer) {
            weight += 1000;
            if (teleporterUseInventoryWeight) {
                for (ItemStack stack : ((EntityPlayer)user).field_71071_by.field_70462_a) {
                    weight += TileEntityTeleporter.getStackCost(stack);
                }
            }
        } else if (user instanceof EntityGhast) {
            weight += 2500;
        } else if (user instanceof EntityWither) {
            weight += 5000;
        } else if (user instanceof EntityDragon) {
            weight += 10000;
        } else if (user instanceof EntityCreature) {
            weight += 500;
        }
        if (teleporterUseInventoryWeight && user instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase)user;
            for (ItemStack stack : living.func_184209_aF()) {
                weight += TileEntityTeleporter.getStackCost(stack);
            }
            if (user instanceof EntityPlayer) {
                ItemStack stack;
                stack = living.func_184614_ca();
                weight -= TileEntityTeleporter.getStackCost(stack);
            }
        }
        for (Entity passenger : user.func_184188_bt()) {
            weight += this.getWeightOf(passenger);
        }
        return weight;
    }

    private static int getStackCost(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        return 100 * StackUtil.getSize(stack) / stack.func_77976_d();
    }

    private void onTeleportTo(TileEntityTeleporter from, Entity entity) {
        this.cooldown = 20;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return !(entity instanceof EntityDragon) && !(entity instanceof EntityWither);
    }

    public boolean hasTarget() {
        return this.target != null;
    }

    public BlockPos getTarget() {
        return this.target;
    }

    public void setTarget(BlockPos pos) {
        this.target = pos;
        this.updateComparatorLevel();
        IC2.network.get(true).updateTileEntityField(this, "target");
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("target");
        return ret;
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("active")) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/Teleporter/TeleChargedLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }
            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }
        super.onNetworkUpdate(field);
    }

    @Override
    public void onNetworkEvent(int event) {
        switch (event) {
            case 0: {
                IC2.audioManager.playOnce(this, "Machines/Teleporter/TeleUse.ogg");
                IC2.audioManager.playOnce(new AudioPosition(this.func_145831_w(), this.field_174879_c), "Machines/Teleporter/TeleUse.ogg");
                this.spawnBlueParticles(20, this.field_174879_c);
                this.spawnBlueParticles(20, this.target);
                break;
            }
            default: {
                IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_174879_c + ")", new Object[0]);
            }
        }
    }
}

