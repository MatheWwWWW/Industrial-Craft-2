/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.MoverType
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.item.EntityBoat$Status
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketSteerBoat
 *  net.minecraft.util.EntitySelectors
 *  net.minecraft.util.SoundEvent
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$PooledMutableBlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.util.ReflectionUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class EntityIC2Boat
extends EntityBoat {
    private static Method method_tickLerp;
    private static Field field_paddlePositions;
    private static Field field_previousStatus;
    private static Field field_status;
    private static Field field_outOfControlTicks;
    private static Field field_momentum;
    private static Field field_lastYd;
    private static Field field_waterLevel;
    private static Field field_boatGlide;
    private static Field field_deltaRotation;
    private static Field field_rightInputDown;
    private static Field field_leftInputDown;
    private static Field field_forwardInputDown;
    private static Field field_backInputDown;

    public static void init() {
        method_tickLerp = EntityIC2Boat.getMethod("tickLerp", "func_184447_s", new Class[0]);
        field_paddlePositions = EntityIC2Boat.getField("paddlePositions", "field_184470_f");
        field_previousStatus = EntityIC2Boat.getField("previousStatus", "field_184471_aG");
        field_status = EntityIC2Boat.getField("status", "field_184469_aF");
        field_outOfControlTicks = EntityIC2Boat.getField("outOfControlTicks", "field_184474_h");
        field_momentum = EntityIC2Boat.getField("momentum", "field_184472_g");
        field_lastYd = EntityIC2Boat.getField("lastYd", "field_184473_aH");
        field_waterLevel = EntityIC2Boat.getField("waterLevel", "field_184465_aD");
        field_boatGlide = EntityIC2Boat.getField("boatGlide", "field_184467_aE");
        field_deltaRotation = EntityIC2Boat.getField("deltaRotation", "field_184475_as");
        field_rightInputDown = EntityIC2Boat.getField("rightInputDown", "field_184459_aA");
        field_leftInputDown = EntityIC2Boat.getField("leftInputDown", "field_184480_az");
        field_forwardInputDown = EntityIC2Boat.getField("forwardInputDown", "field_184461_aB");
        field_backInputDown = EntityIC2Boat.getField("backInputDown", "field_184463_aC");
    }

    private static Field getField(String deobfName, String srgName) {
        return ReflectionUtil.getField(EntityBoat.class, srgName, deobfName);
    }

    private static Method getMethod(String deobfName, String srgName, Class<?> ... parameterTypes) {
        return ReflectionUtil.getMethod(EntityBoat.class, new String[]{srgName, deobfName}, parameterTypes);
    }

    public EntityIC2Boat(World world) {
        super(world);
    }

    public void func_70071_h_() {
        World world = this.func_130014_f_();
        try {
            field_previousStatus.set((Object)this, field_status.get((Object)this));
            EntityBoat.Status status = this.getBoatStatus();
            field_status.set((Object)this, status);
            if (status != EntityBoat.Status.UNDER_WATER && status != EntityBoat.Status.UNDER_FLOWING_WATER) {
                field_outOfControlTicks.setFloat((Object)this, 0.0f);
            } else {
                field_outOfControlTicks.setFloat((Object)this, field_outOfControlTicks.getFloat((Object)this) + 1.0f);
            }
            if (!world.field_72995_K && field_outOfControlTicks.getFloat((Object)this) >= 60.0f) {
                this.func_184226_ay();
            }
            if (this.func_70268_h() > 0) {
                this.func_70265_b(this.func_70268_h() - 1);
            }
            if (this.func_70271_g() > 0.0f) {
                this.func_70266_a(this.func_70271_g() - 1.0f);
            }
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            this.doEntityUpdate(world);
            method_tickLerp.invoke((Object)this, new Object[0]);
            if (this.func_184186_bw()) {
                if (this.func_184188_bt().isEmpty() || !(this.func_184188_bt().get(0) instanceof EntityPlayer)) {
                    this.func_184445_a(false, false);
                }
                this.updateMotion();
                if (world.field_72995_K) {
                    this.controlBoat();
                    world.func_184135_a((Packet)new CPacketSteerBoat(this.func_184457_a(0), this.func_184457_a(1)));
                }
                this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            } else {
                this.field_70159_w = 0.0;
                this.field_70181_x = 0.0;
                this.field_70179_y = 0.0;
            }
            for (int i = 0; i <= 1; ++i) {
                if (this.func_184457_a(i)) {
                    SoundEvent soundevent;
                    double paddlePosition = Array.getFloat(field_paddlePositions.get((Object)this), i);
                    if (!this.func_174814_R() && paddlePosition % (Math.PI * 2) <= 0.7853981633974483 && (paddlePosition + 0.4) % (Math.PI * 2) >= 0.7853981633974483 && (soundevent = this.func_193047_k()) != null) {
                        Vec3d look = this.func_70676_i(1.0f);
                        world.func_184148_a(null, this.field_70165_t + (i == 1 ? -look.field_72449_c : look.field_72449_c), this.field_70163_u, this.field_70161_v + (i == 1 ? look.field_72450_a : -look.field_72450_a), soundevent, this.func_184176_by(), 1.0f, 0.8f + 0.4f * this.field_70146_Z.nextFloat());
                    }
                    Array.setFloat(field_paddlePositions.get((Object)this), i, (float)(paddlePosition + 0.04));
                    continue;
                }
                Array.setFloat(field_paddlePositions.get((Object)this), i, 0.0f);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error reflecting boat in update", e);
        }
        this.func_145775_I();
        List list = world.func_175674_a((Entity)this, this.func_174813_aQ().func_72314_b(0.2, -0.01, 0.2), EntitySelectors.func_188442_a((Entity)this));
        if (!list.isEmpty()) {
            boolean flag = !world.field_72995_K && !(this.func_184179_bs() instanceof EntityPlayer);
            for (Entity entity : list) {
                if (entity.func_184196_w((Entity)this)) continue;
                if (flag && this.func_184188_bt().size() < 2 && !entity.func_184218_aH() && entity.field_70130_N < this.field_70130_N && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer)) {
                    entity.func_184220_m((Entity)this);
                    continue;
                }
                this.func_70108_f(entity);
            }
        }
    }

    private void doEntityUpdate(World world) {
        if (!world.field_72995_K) {
            this.func_70052_a(6, this.func_184202_aL());
        }
        this.func_70030_z();
    }

    private void updateMotion() {
        double generalHeightChangingValue = this.func_189652_ae() ? 0.0 : -0.04;
        double heightChange = 0.0;
        float momentum = 0.05f;
        try {
            EntityBoat.Status status = (EntityBoat.Status)field_status.get((Object)this);
            if (field_previousStatus.get((Object)this) == EntityBoat.Status.IN_AIR && status != EntityBoat.Status.IN_AIR && status != EntityBoat.Status.ON_LAND) {
                field_waterLevel.setDouble((Object)this, this.func_174813_aQ().field_72338_b + (double)this.field_70131_O);
                this.func_70107_b(this.field_70165_t, (double)(this.func_184451_k() - this.field_70131_O) + 0.101, this.field_70161_v);
                this.field_70181_x = 0.0;
                field_lastYd.setDouble((Object)this, 0.0);
                field_status.set((Object)this, EntityBoat.Status.IN_WATER);
            } else {
                switch (status) {
                    case IN_AIR: {
                        momentum = 0.9f;
                        break;
                    }
                    case IN_WATER: {
                        heightChange = (field_waterLevel.getDouble((Object)this) - this.func_174813_aQ().field_72338_b) / (double)this.field_70131_O;
                        momentum = 0.9f;
                        break;
                    }
                    case ON_LAND: {
                        momentum = field_boatGlide.getFloat((Object)this);
                        if (!(this.func_184179_bs() instanceof EntityPlayer)) break;
                        field_boatGlide.setFloat((Object)this, momentum / 2.0f);
                        break;
                    }
                    case UNDER_FLOWING_WATER: {
                        generalHeightChangingValue = -7.0E-4;
                        momentum = 0.9f;
                        break;
                    }
                    case UNDER_WATER: {
                        heightChange = 0.01;
                        momentum = 0.45f;
                    }
                }
                this.field_70159_w *= (double)momentum;
                this.field_70179_y *= (double)momentum;
                field_deltaRotation.setFloat((Object)this, field_deltaRotation.getFloat((Object)this) * momentum);
                this.field_70181_x += generalHeightChangingValue;
                if (heightChange > 0.0) {
                    this.field_70181_x += heightChange * 0.061538461538461535;
                    this.field_70181_x *= 0.75;
                }
            }
            field_momentum.setFloat((Object)this, momentum);
        }
        catch (Exception e) {
            throw new RuntimeException("Error reflecting boat in updateMotion", e);
        }
    }

    public float func_184451_k() {
        AxisAlignedBB boundingBox = this.func_174813_aQ();
        int minX = (int)Math.floor(boundingBox.field_72340_a);
        int maxX = (int)Math.ceil(boundingBox.field_72336_d);
        int minZ = (int)Math.floor(boundingBox.field_72339_c);
        int maxZ = (int)Math.ceil(boundingBox.field_72334_f);
        BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
        try {
            World world = this.func_130014_f_();
            int maxY = (int)Math.ceil(boundingBox.field_72337_e - field_lastYd.getDouble((Object)this));
            block6: for (int y = (int)Math.floor(boundingBox.field_72337_e); y < maxY; ++y) {
                float waterHeight = 0.0f;
                int x = minX;
                while (true) {
                    if (x >= maxX) {
                        if (!(waterHeight < 1.0f)) continue block6;
                        float f = (float)blockPosPool.func_177956_o() + waterHeight;
                        return f;
                    }
                    for (int z = minZ; z < maxZ; ++z) {
                        blockPosPool.func_181079_c(x, y, z);
                        IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
                        if (this.isWater(block)) {
                            waterHeight = Math.max(waterHeight, EntityIC2Boat.getBlockLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool));
                        }
                        if (waterHeight >= 1.0f) continue block6;
                    }
                    ++x;
                }
            }
            float f = maxY + 1;
            return f;
        }
        catch (Exception e) {
            throw new RuntimeException("Error reflecting boat in getWaterLevelAbove", e);
        }
        finally {
            blockPosPool.func_185344_t();
        }
    }

    private EntityBoat.Status getBoatStatus() {
        EntityBoat.Status isUnderWater = this.getUnderwaterStatus();
        try {
            if (isUnderWater != null) {
                field_waterLevel.setDouble((Object)this, this.func_174813_aQ().field_72337_e);
                return isUnderWater;
            }
            if (this.checkInWater()) {
                return EntityBoat.Status.IN_WATER;
            }
            float glideSpeed = this.func_184441_l();
            if (glideSpeed > 0.0f) {
                field_boatGlide.setFloat((Object)this, glideSpeed);
                return EntityBoat.Status.ON_LAND;
            }
            return EntityBoat.Status.IN_AIR;
        }
        catch (Exception e) {
            throw new RuntimeException("Error reflecting boat in getBoatStatus", e);
        }
    }

    private boolean checkInWater() {
        World world = this.func_130014_f_();
        AxisAlignedBB boundingBox = this.func_174813_aQ();
        boolean isInWater = false;
        BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
        try {
            double waterLevel = Double.MIN_VALUE;
            int x = (int)Math.floor(boundingBox.field_72340_a);
            while ((double)x < Math.ceil(boundingBox.field_72336_d)) {
                int y = (int)Math.floor(boundingBox.field_72338_b);
                while ((double)y < Math.ceil(boundingBox.field_72338_b + 0.001)) {
                    int z = (int)Math.floor(boundingBox.field_72339_c);
                    while ((double)z < Math.ceil(boundingBox.field_72334_f)) {
                        blockPosPool.func_181079_c(x, y, z);
                        IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
                        if (this.isWater(block)) {
                            float waterHeight = EntityIC2Boat.getLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool);
                            waterLevel = Math.max((double)waterHeight, waterLevel);
                            isInWater |= boundingBox.field_72338_b < (double)waterHeight;
                        }
                        ++z;
                    }
                    ++y;
                }
                ++x;
            }
            field_waterLevel.setDouble((Object)this, waterLevel);
        }
        catch (Exception e) {
            throw new RuntimeException("Error reflecting boat in checkInWater", e);
        }
        finally {
            blockPosPool.func_185344_t();
        }
        return isInWater;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private EntityBoat.Status getUnderwaterStatus() {
        World world = this.func_130014_f_();
        AxisAlignedBB boundingBox = this.func_174813_aQ();
        double boatTop = boundingBox.field_72337_e + 0.001;
        BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
        try {
            int x = (int)Math.floor(boundingBox.field_72340_a);
            while ((double)x < Math.ceil(boundingBox.field_72336_d)) {
                int y = (int)Math.floor(boundingBox.field_72337_e);
                while ((double)y < Math.ceil(boatTop)) {
                    int z = (int)Math.floor(boundingBox.field_72339_c);
                    while ((double)z < Math.ceil(boundingBox.field_72334_f)) {
                        blockPosPool.func_181079_c(x, y, z);
                        IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
                        if (this.isWater(block) && boatTop < (double)EntityIC2Boat.getLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool)) {
                            EntityBoat.Status status = (Integer)block.func_177229_b((IProperty)BlockLiquid.field_176367_b) != 0 ? EntityBoat.Status.UNDER_FLOWING_WATER : EntityBoat.Status.UNDER_WATER;
                            return status;
                        }
                        ++z;
                    }
                    ++y;
                }
                ++x;
            }
        }
        finally {
            blockPosPool.func_185344_t();
        }
        return null;
    }

    public static float getLiquidHeight(IBlockState block, IBlockAccess world, BlockPos pos) {
        return (float)pos.func_177956_o() + EntityIC2Boat.getBlockLiquidHeight(block, world, pos);
    }

    public static float getBlockLiquidHeight(IBlockState block, IBlockAccess world, BlockPos pos) {
        int liquidHeight = (Integer)block.func_177229_b((IProperty)BlockLiquid.field_176367_b);
        return (liquidHeight & 7) == 0 && world.func_180495_p(pos.func_177984_a()).func_185904_a() == block.func_185904_a() ? 1.0f : 1.0f - BlockLiquid.func_149801_b((int)liquidHeight);
    }

    private void controlBoat() {
        if (this.func_184207_aI()) {
            float speed = 0.0f;
            try {
                boolean left = field_leftInputDown.getBoolean((Object)this);
                boolean right = field_rightInputDown.getBoolean((Object)this);
                boolean forward = field_forwardInputDown.getBoolean((Object)this);
                boolean backward = field_backInputDown.getBoolean((Object)this);
                if (left) {
                    field_deltaRotation.setFloat((Object)this, field_deltaRotation.getFloat((Object)this) - 1.0f);
                }
                if (right) {
                    field_deltaRotation.setFloat((Object)this, field_deltaRotation.getFloat((Object)this) + 1.0f);
                }
                if (right != left && !forward && !backward) {
                    speed += 0.005f;
                }
                this.field_70177_z += field_deltaRotation.getFloat((Object)this);
                if (forward) {
                    speed += 0.04f;
                }
                if (backward) {
                    speed -= 0.005f;
                }
                this.field_70159_w += (double)(MathHelper.func_76126_a((float)(-this.field_70177_z * (float)Math.PI / 180.0f)) * speed) * this.getAccelerationFactor();
                this.field_70179_y += (double)(MathHelper.func_76134_b((float)(this.field_70177_z * (float)Math.PI / 180.0f)) * speed) * this.getAccelerationFactor();
                this.func_184445_a(right && !left || forward, left && !right || forward);
            }
            catch (Exception e) {
                throw new RuntimeException("Error reflecting boat in controlBoat", e);
            }
        }
    }

    protected void func_184231_a(double y, boolean onGround, IBlockState state, BlockPos pos) {
        boolean expectDeath = this.field_70143_R > 3.0f && !this.field_70128_L;
        super.func_184231_a(y, onGround, state, pos);
        if (expectDeath && this.field_70128_L && this.func_130014_f_().func_82736_K().func_82766_b("doEntityDrops")) {
            super.func_70099_a(this.getBrokenItem(), 0.0f);
        }
    }

    public EntityItem func_70099_a(ItemStack stack, float offsetY) {
        if (stack.func_77973_b() == Items.field_151124_az) {
            return super.func_70099_a(this.getItem(), offsetY);
        }
        return null;
    }

    public ItemStack getPickedResult(RayTraceResult target) {
        return this.getItem();
    }

    protected abstract ItemStack getItem();

    protected ItemStack getBrokenItem() {
        return this.getItem();
    }

    public abstract String getTexture();

    protected double getAccelerationFactor() {
        return 1.0;
    }

    protected double getTopSpeed() {
        return 0.35;
    }

    protected boolean isWater(IBlockState block) {
        return block.func_185904_a() == Material.field_151586_h;
    }
}

