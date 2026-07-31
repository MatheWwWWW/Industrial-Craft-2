/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.BlockFaceShape
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureAttribute
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.block.wiring;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.IWorldTickCallback;
import ic2.core.Ic2Player;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.util.StackUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileEntityLuminator
extends TileEntityBlock {
    private static final int manualChargeCapacity = 10000;
    private static final Map<EnumFacing, List<AxisAlignedBB>> aabbMap = TileEntityLuminator.getAabbMap();
    private final Energy energy = this.addComponent(Energy.asBasicSink(this, 5.0));
    private final Redstone redstone = this.addComponent(new Redstone(this));
    private final ComparatorEmitter comparator = this.addComponent(new ComparatorEmitter(this));
    private boolean invertRedstone;
    public static boolean ignoreBlockStay = false;

    public TileEntityLuminator() {
        this.comparator.setUpdate(this.energy::getComparatorValue);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.invertRedstone = nbt.func_74767_n("invert");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74757_a("invert", this.invertRedstone);
        return nbt;
    }

    @Override
    public void onLoaded() {
        this.energy.setDirections(Collections.singleton(this.getFacing().func_176734_d()), Collections.emptySet());
        super.onLoaded();
        IC2.tickHandler.requestSingleWorldTick(this.func_145831_w(), new IWorldTickCallback(){

            @Override
            public void onTick(World world) {
                TileEntityLuminator.this.checkPlacement();
            }
        });
    }

    @Override
    protected EnumFacing getPlacementFacing(EntityLivingBase placer, EnumFacing facing) {
        return facing;
    }

    @Override
    protected void updateEntityServer() {
        boolean lit;
        super.updateEntityServer();
        boolean bl = lit = this.isLit() && this.energy.useEnergy(0.25);
        if (this.getActive() != lit) {
            this.setActive(lit);
            this.updateLight();
        }
    }

    private boolean isLit() {
        return this.redstone.hasRedstoneInput() != this.invertRedstone;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.func_145831_w().field_72995_K) return true;
        ItemStack stack = StackUtil.get(player, hand);
        double amount = 10000.0 - this.energy.getEnergy();
        if (stack != null && amount > 0.0) {
            double d;
            amount = ElectricItem.manager.discharge(stack, amount, this.energy.getSinkTier(), true, true, false);
            if (d > 0.0) {
                this.energy.forceAddEnergy(amount);
                return true;
            }
        }
        this.invertRedstone = !this.invertRedstone;
        IC2.network.get(true).updateTileEntityField(this, "invertRedstone");
        return true;
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        this.checkPlacement();
    }

    private void checkPlacement() {
        World world = this.func_145831_w();
        if (!TileEntityLuminator.isValidPosition(world, this.field_174879_c.func_177972_a(this.getFacing().func_176734_d()), this.getFacing())) {
            this.getBlockType().func_180657_a(world, Ic2Player.get(world), this.field_174879_c, world.func_180495_p(this.field_174879_c), this, StackUtil.emptyStack);
            world.func_175698_g(this.field_174879_c);
        }
    }

    public static boolean isValidPosition(World world, BlockPos pos, EnumFacing side) {
        if (world.field_72995_K || ignoreBlockStay) {
            return true;
        }
        if (world.func_180495_p(pos).func_193401_d((IBlockAccess)world, pos, side) == BlockFaceShape.SOLID) {
            return true;
        }
        IEnergyTile tile = EnergyNet.instance.getSubTile(world, pos);
        return tile instanceof IEnergyEmitter;
    }

    @Override
    protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
        return aabbMap.get(this.getFacing());
    }

    @Override
    public int getLightValue() {
        return this.getActive() ? 15 : 0;
    }

    @Override
    protected void onEntityCollision(Entity entity) {
        super.onEntityCollision(entity);
        if (this.getActive() && entity instanceof EntityMob) {
            boolean isUndead = entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70668_bt() == EnumCreatureAttribute.UNDEAD;
            entity.func_70015_d(isUndead ? 20 : 10);
        }
    }

    @Override
    protected boolean canSetFacingWrench(EnumFacing facing, EntityPlayer player) {
        return true;
    }

    @Override
    protected boolean setFacingWrench(EnumFacing facing, EntityPlayer player) {
        this.invertRedstone = !this.invertRedstone;
        return true;
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer player) {
        return false;
    }

    @Override
    public void onNetworkUpdate(String field) {
        super.onNetworkUpdate(field);
        if (field.equals("active")) {
            this.updateLight();
        }
    }

    private void updateLight() {
        this.func_145831_w().func_180500_c(EnumSkyBlock.BLOCK, this.field_174879_c);
    }

    private static Map<EnumFacing, List<AxisAlignedBB>> getAabbMap() {
        EnumMap<EnumFacing, List<AxisAlignedBB>> ret = new EnumMap<EnumFacing, List<AxisAlignedBB>>(EnumFacing.class);
        double height = 0.0625;
        double remHeight = 0.9375;
        for (EnumFacing side : EnumFacing.field_82609_l) {
            int dx = side.func_82601_c();
            int dy = side.func_96559_d();
            int dz = side.func_82599_e();
            double xS = (double)((dx + 1) / 2) * 0.9375;
            double yS = (double)((dy + 1) / 2) * 0.9375;
            double zS = (double)((dz + 1) / 2) * 0.9375;
            double xE = 0.0625 + (double)((dx + 2) / 2) * 0.9375;
            double yE = 0.0625 + (double)((dy + 2) / 2) * 0.9375;
            double zE = 0.0625 + (double)((dz + 2) / 2) * 0.9375;
            ret.put(side.func_176734_d(), Arrays.asList(new AxisAlignedBB(xS, yS, zS, xE, yE, zE)));
        }
        return ret;
    }
}

