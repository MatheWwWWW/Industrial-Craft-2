/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.MobEffects
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.ChunkCache
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package ic2.core;

import ic2.api.event.ExplosionEvent;
import ic2.api.tile.ExplosionWhitelist;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.util.ItemComparableItemStack;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ExplosionIC2
extends Explosion {
    private final World worldObj;
    private final Entity exploder;
    private final double explosionX;
    private final double explosionY;
    private final double explosionZ;
    private final int mapHeight;
    private final float power;
    private final float explosionDropRate;
    private final Type type;
    private final int radiationRange;
    private final EntityLivingBase igniter;
    private final Random rng = new Random();
    private final double maxDistance;
    private final int areaSize;
    private final int areaX;
    private final int areaZ;
    private final DamageSource damageSource;
    private final List<EntityDamage> entitiesInRange = new ArrayList<EntityDamage>();
    private final long[][] destroyedBlockPositions;
    private ChunkCache chunkCache;
    private static final double dropPowerLimit = 8.0;
    private static final double damageAtDropPowerLimit = 32.0;
    private static final double accelerationAtDropPowerLimit = 0.7;
    private static final double motionLimit = 60.0;
    private static final int secondaryRayCount = 5;
    private static final int bitSetElementSize = 2;

    public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power, float drop) {
        this(world, entity, x, y, z, power, drop, Type.Normal);
    }

    public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power, float drop, Type type) {
        this(world, entity, x, y, z, power, drop, type, null, 0);
    }

    public ExplosionIC2(World world, Entity entity, BlockPos pos, float power, float drop, Type type) {
        this(world, entity, (double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5, power, drop, type);
    }

    public ExplosionIC2(World world, Entity entity, double x, double y, double z, float power1, float drop, Type type1, EntityLivingBase igniter1, int radiationRange1) {
        super(world, entity, x, y, z, power1, false, false);
        this.worldObj = world;
        this.exploder = entity;
        this.explosionX = x;
        this.explosionY = y;
        this.explosionZ = z;
        this.mapHeight = IC2.getWorldHeight(world);
        this.power = power1;
        this.explosionDropRate = drop;
        this.type = type1;
        this.igniter = igniter1;
        this.radiationRange = radiationRange1;
        this.maxDistance = (double)this.power / 0.4;
        int maxDistanceInt = (int)Math.ceil(this.maxDistance);
        this.areaSize = maxDistanceInt * 2;
        this.areaX = Util.roundToNegInf(x) - maxDistanceInt;
        this.areaZ = Util.roundToNegInf(z) - maxDistanceInt;
        this.damageSource = this.isNuclear() ? IC2DamageSource.getNukeSource(this) : DamageSource.func_94539_a((Explosion)this);
        this.destroyedBlockPositions = new long[this.mapHeight][];
    }

    public ExplosionIC2(World world, Entity entity, BlockPos pos, int i, float f, Type heat) {
        this(world, entity, (double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5, i, f, heat);
    }

    public void doExplosion() {
        boolean entitiesAreInRange;
        if (this.power <= 0.0f) {
            return;
        }
        ExplosionEvent event = new ExplosionEvent(this.worldObj, this.exploder, this.getPosition(), this.power, this.igniter, this.radiationRange, this.maxDistance);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        int range = this.areaSize / 2;
        BlockPos pos = new BlockPos(this.getPosition());
        BlockPos start = pos.func_177982_a(-range, -range, -range);
        BlockPos end = pos.func_177982_a(range, range, range);
        this.chunkCache = new ChunkCache(this.worldObj, start, end, 0);
        List entities = this.worldObj.func_72839_b(null, new AxisAlignedBB(start, end));
        for (Entity entity : entities) {
            if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityItem)) continue;
            int distance = (int)(Util.square(entity.field_70165_t - this.explosionX) + Util.square(entity.field_70163_u - this.explosionY) + Util.square(entity.field_70161_v - this.explosionZ));
            double health = ExplosionIC2.getEntityHealth(entity);
            this.entitiesInRange.add(new EntityDamage(entity, distance, health));
        }
        boolean bl = entitiesAreInRange = !this.entitiesInRange.isEmpty();
        if (entitiesAreInRange) {
            Collections.sort(this.entitiesInRange, new Comparator<EntityDamage>(){

                @Override
                public int compare(EntityDamage a, EntityDamage b) {
                    return a.distance - b.distance;
                }
            });
        }
        int steps = (int)Math.ceil(Math.PI / Math.atan(1.0 / this.maxDistance));
        BlockPos.MutableBlockPos tmpPos = new BlockPos.MutableBlockPos();
        for (int phi_n = 0; phi_n < 2 * steps; ++phi_n) {
            for (int theta_n = 0; theta_n < steps; ++theta_n) {
                double phi = Math.PI * 2 / (double)steps * (double)phi_n;
                double theta = Math.PI / (double)steps * (double)theta_n;
                this.shootRay(this.explosionX, this.explosionY, this.explosionZ, phi, theta, this.power, entitiesAreInRange && phi_n % 8 == 0 && theta_n % 8 == 0, tmpPos);
            }
        }
        for (EntityDamage entry : this.entitiesInRange) {
            double motionSq;
            Entity entity = entry.entity;
            entity.func_70097_a(this.damageSource, (float)entry.damage);
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                if (this.isNuclear() && this.igniter != null && player == this.igniter && player.func_110143_aJ() <= 0.0f) {
                    IC2.achievements.issueAchievement(player, "dieFromOwnNuke");
                }
            }
            double reduction = (motionSq = Util.square(entry.motionX) + Util.square(entity.field_70181_x) + Util.square(entity.field_70179_y)) > 3600.0 ? Math.sqrt(3600.0 / motionSq) : 1.0;
            entity.field_70159_w += entry.motionX * reduction;
            entity.field_70181_x += entry.motionY * reduction;
            entity.field_70179_y += entry.motionZ * reduction;
        }
        if (this.isNuclear() && this.radiationRange >= 1) {
            List entitiesInRange = this.worldObj.func_72872_a(EntityLiving.class, new AxisAlignedBB(this.explosionX - (double)this.radiationRange, this.explosionY - (double)this.radiationRange, this.explosionZ - (double)this.radiationRange, this.explosionX + (double)this.radiationRange, this.explosionY + (double)this.radiationRange, this.explosionZ + (double)this.radiationRange));
            for (EntityLiving entity : entitiesInRange) {
                if (ItemArmorHazmat.hasCompleteHazmat((EntityLivingBase)entity)) continue;
                double distance = entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ);
                int hungerLength = (int)(120.0 * ((double)this.radiationRange - distance));
                int poisonLength = (int)(80.0 * ((double)(this.radiationRange / 3) - distance));
                if (hungerLength >= 0) {
                    entity.func_70690_d(new PotionEffect(MobEffects.field_76438_s, hungerLength, 0));
                }
                if (poisonLength < 0) continue;
                IC2Potion.radiation.applyTo((EntityLivingBase)entity, poisonLength, 0);
            }
        }
        IC2.network.get(true).initiateExplosionEffect(this.worldObj, this.getPosition(), this.type);
        Random rng = this.worldObj.field_73012_v;
        boolean doDrops = this.worldObj.func_82736_K().func_82766_b("doTileDrops");
        HashMap blocksToDrop = new HashMap();
        for (int y = 0; y < this.destroyedBlockPositions.length; ++y) {
            long[] bitSet = this.destroyedBlockPositions[y];
            if (bitSet == null) continue;
            int index = -2;
            while ((index = ExplosionIC2.nextSetIndex(index + 2, bitSet, 2)) != -1) {
                int realIndex = index / 2;
                int z = realIndex / this.areaSize;
                int x = realIndex - z * this.areaSize;
                tmpPos.func_181079_c(x += this.areaX, y, z += this.areaZ);
                IBlockState state = this.chunkCache.func_180495_p((BlockPos)tmpPos);
                Block block = state.func_177230_c();
                if (this.power < 20.0f) {
                    // empty if block
                }
                if (doDrops && block.func_149659_a((Explosion)this) && ExplosionIC2.getAtIndex(index, bitSet, 2) == 1) {
                    for (ItemStack stack : StackUtil.getDrops((IBlockAccess)this.worldObj, (BlockPos)tmpPos, state, block, 0)) {
                        ItemComparableItemStack isw;
                        DropData data;
                        if (rng.nextFloat() > this.explosionDropRate) continue;
                        XZposition xZposition = new XZposition(x / 2, z / 2);
                        HashMap<ItemComparableItemStack, DropData> map = (HashMap<ItemComparableItemStack, DropData>)blocksToDrop.get(xZposition);
                        if (map == null) {
                            map = new HashMap<ItemComparableItemStack, DropData>();
                            blocksToDrop.put(xZposition, map);
                        }
                        if ((data = (DropData)map.get(isw = new ItemComparableItemStack(stack, false))) == null) {
                            data = new DropData(StackUtil.getSize(stack), y);
                            map.put(isw.copy(), data);
                            continue;
                        }
                        data.add(StackUtil.getSize(stack), y);
                    }
                }
                block.onBlockExploded(this.worldObj, (BlockPos)tmpPos, (Explosion)this);
            }
        }
        for (Map.Entry entry : blocksToDrop.entrySet()) {
            XZposition xZposition = (XZposition)entry.getKey();
            for (Map.Entry entry2 : ((Map)entry.getValue()).entrySet()) {
                int stackSize;
                ItemComparableItemStack isw = (ItemComparableItemStack)entry2.getKey();
                for (int count = ((DropData)entry2.getValue()).n; count > 0; count -= stackSize) {
                    stackSize = Math.min(count, 64);
                    EntityItem entityitem = new EntityItem(this.worldObj, (double)(((float)xZposition.x + this.worldObj.field_73012_v.nextFloat()) * 2.0f), (double)((DropData)entry2.getValue()).maxY + 0.5, (double)(((float)xZposition.z + this.worldObj.field_73012_v.nextFloat()) * 2.0f), isw.toStack(stackSize));
                    entityitem.func_174869_p();
                    this.worldObj.func_72838_d((Entity)entityitem);
                }
            }
        }
    }

    public void destroy(int x, int y, int z, boolean noDrop) {
        this.destroyUnchecked(x, y, z, noDrop);
    }

    private void destroyUnchecked(int x, int y, int z, boolean noDrop) {
        int index = (z - this.areaZ) * this.areaSize + (x - this.areaX);
        index *= 2;
        long[] array = this.destroyedBlockPositions[y];
        if (array == null) {
            array = ExplosionIC2.makeArray(Util.square(this.areaSize), 2);
            this.destroyedBlockPositions[y] = array;
        }
        if (noDrop) {
            ExplosionIC2.setAtIndex(index, array, 3);
        } else {
            ExplosionIC2.setAtIndex(index, array, 1);
        }
    }

    private void shootRay(double x, double y, double z, double phi, double theta, double power1, boolean killEntities, BlockPos.MutableBlockPos tmpPos) {
        int blockY;
        double deltaX = Math.sin(theta) * Math.cos(phi);
        double deltaY = Math.cos(theta);
        double deltaZ = Math.sin(theta) * Math.sin(phi);
        int step = 0;
        while ((blockY = Util.roundToNegInf(y)) >= 0 && blockY < this.mapHeight) {
            int blockX = Util.roundToNegInf(x);
            int blockZ = Util.roundToNegInf(z);
            tmpPos.func_181079_c(blockX, blockY, blockZ);
            IBlockState state = this.chunkCache.func_180495_p((BlockPos)tmpPos);
            Block block = state.func_177230_c();
            double absorption = this.getAbsorption(block, (BlockPos)tmpPos);
            if (absorption < 0.0) break;
            if (absorption > 1000.0 && !ExplosionWhitelist.isBlockWhitelisted(block)) {
                absorption = 0.5;
            } else {
                if (absorption > power1) break;
                if (block == Blocks.field_150348_b || block != Blocks.field_150350_a && !block.isAir(state, (IBlockAccess)this.worldObj, (BlockPos)tmpPos)) {
                    this.destroyUnchecked(blockX, blockY, blockZ, power1 > 8.0);
                }
            }
            if (killEntities && (step + 4) % 8 == 0 && !this.entitiesInRange.isEmpty() && power1 >= 0.25) {
                this.damageEntities(x, y, z, step, power1);
            }
            if (absorption > 10.0) {
                for (int i = 0; i < 5; ++i) {
                    this.shootRay(x, y, z, this.rng.nextDouble() * 2.0 * Math.PI, this.rng.nextDouble() * Math.PI, absorption * 0.4, false, tmpPos);
                }
            }
            power1 -= absorption;
            x += deltaX;
            y += deltaY;
            z += deltaZ;
            ++step;
        }
    }

    private double getAbsorption(Block block, BlockPos pos) {
        double ret = 0.5;
        if (block == Blocks.field_150350_a || block.isAir(block.func_176223_P(), (IBlockAccess)this.worldObj, pos)) {
            return ret;
        }
        if ((block == Blocks.field_150355_j || block == Blocks.field_150358_i) && this.type != Type.Normal) {
            ret += 1.0;
        } else {
            float resistance = block.getExplosionResistance(this.worldObj, pos, this.exploder, (Explosion)this);
            if (resistance < 0.0f) {
                return resistance;
            }
            double extra = (double)(resistance + 4.0f) * 0.3;
            ret = this.type != Type.Heat ? (ret += extra) : (ret += extra * 6.0);
        }
        return ret;
    }

    private void damageEntities(double x, double y, double z, int step, double power) {
        int index;
        if (step != 4) {
            int distanceMin = Util.square(step - 5);
            int indexStart = 0;
            int indexEnd = this.entitiesInRange.size() - 1;
            do {
                index = (indexStart + indexEnd) / 2;
                int distance = this.entitiesInRange.get((int)index).distance;
                if (distance < distanceMin) {
                    indexStart = index + 1;
                    continue;
                }
                indexEnd = distance > distanceMin ? index - 1 : index;
            } while (indexStart < indexEnd);
        } else {
            index = 0;
        }
        int distanceMax = Util.square(step + 5);
        for (int i = index; i < this.entitiesInRange.size(); ++i) {
            EntityDamage entry = this.entitiesInRange.get(i);
            if (entry.distance >= distanceMax) break;
            Entity entity = entry.entity;
            if (!(Util.square(entity.field_70165_t - x) + Util.square(entity.field_70163_u - y) + Util.square(entity.field_70161_v - z) <= 25.0)) continue;
            double damage = 4.0 * power;
            entry.damage += damage;
            entry.health -= damage;
            double dx = entity.field_70165_t - this.explosionX;
            double dy = entity.field_70163_u - this.explosionY;
            double dz = entity.field_70161_v - this.explosionZ;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            entry.motionX += dx / distance * 0.0875 * power;
            entry.motionY += dy / distance * 0.0875 * power;
            entry.motionZ += dz / distance * 0.0875 * power;
            if (!(entry.health <= 0.0)) continue;
            entity.func_70097_a(this.damageSource, (float)entry.damage);
            if (entity.func_70089_S()) continue;
            this.entitiesInRange.remove(i);
            --i;
        }
    }

    public EntityLivingBase func_94613_c() {
        return this.igniter;
    }

    private boolean isNuclear() {
        return this.type == Type.Nuclear;
    }

    private static double getEntityHealth(Entity entity) {
        if (entity instanceof EntityItem) {
            return 5.0;
        }
        return Double.POSITIVE_INFINITY;
    }

    private static long[] makeArray(int size, int step) {
        return new long[(size * step + 8 - step) / 8];
    }

    private static int nextSetIndex(int start, long[] array, int step) {
        int offset = start % 8;
        for (int i = start / 8; i < array.length; ++i) {
            long aval = array[i];
            for (int j = offset; j < 8; j += step) {
                int val = (int)(aval >> j & (long)((1 << step) - 1));
                if (val == 0) continue;
                return i * 8 + j;
            }
            offset = 0;
        }
        return -1;
    }

    private static int getAtIndex(int index, long[] array, int step) {
        return (int)(array[index / 8] >>> index % 8 & (long)((1 << step) - 1));
    }

    private static void setAtIndex(int index, long[] array, int value) {
        int n = index / 8;
        array[n] = array[n] | (long)(value << index % 8);
    }

    private static class EntityDamage {
        final Entity entity;
        final int distance;
        double health;
        double damage;
        double motionX;
        double motionY;
        double motionZ;

        EntityDamage(Entity entity, int distance, double health) {
            this.entity = entity;
            this.distance = distance;
            this.health = health;
        }
    }

    public static enum Type {
        Normal,
        Heat,
        Electrical,
        Nuclear;

    }

    private static class DropData {
        int n;
        int maxY;

        DropData(int n1, int y) {
            this.n = n1;
            this.maxY = y;
        }

        public DropData add(int n1, int y) {
            this.n += n1;
            if (y > this.maxY) {
                this.maxY = y;
            }
            return this;
        }
    }

    private static class XZposition {
        int x;
        int z;

        XZposition(int x1, int z1) {
            this.x = x1;
            this.z = z1;
        }

        public boolean equals(Object obj) {
            if (obj instanceof XZposition) {
                XZposition xZposition = (XZposition)obj;
                return xZposition.x == this.x && xZposition.z == this.z;
            }
            return false;
        }

        public int hashCode() {
            return this.x * 31 ^ this.z;
        }
    }
}

