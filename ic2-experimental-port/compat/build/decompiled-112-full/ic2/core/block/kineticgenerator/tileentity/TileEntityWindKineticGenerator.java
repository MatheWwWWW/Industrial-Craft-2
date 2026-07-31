/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.ChunkCache
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.api.item.IKineticRotor;
import ic2.api.tile.IRotorProvider;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.WorldData;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotConsumableKineticRotor;
import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityWindKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IRotorProvider,
IHasGui {
    public final InvSlotConsumableClass rotorSlot;
    private double windStrength;
    private int obstructedCrossSection;
    private int crossSection;
    private int updateTicker = IC2.random.nextInt(this.getTickRate());
    private float rotationSpeed;
    private float angle = 0.0f;
    private long lastcheck;
    private static final double efficiencyRollOffExponent = 2.0;
    public static final float outputModifier = 10.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/wind");
    private static final ResourceLocation woodenRotorTexture = new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png");

    public TileEntityWindKineticGenerator() {
        this.rotorSlot = new InvSlotConsumableKineticRotor(this, "rotorslot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, IKineticRotor.GearboxType.WIND, "rotorSlot");
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.getTickRate() != 0) {
            return;
        }
        boolean needsInvUpdate = false;
        boolean isActive = this.getActive();
        if ((this.hasRotor() && this.rotorHasSpace()) != isActive) {
            isActive = !isActive;
            this.setActive(isActive);
            needsInvUpdate = true;
        }
        if (isActive) {
            this.crossSection = Util.square(this.getRotorDiameter() / 2 * 2 * 2 + 1);
            this.obstructedCrossSection = this.checkSpace(this.getRotorDiameter() * 3, false);
            if (this.obstructedCrossSection > 0 && this.obstructedCrossSection <= (this.getRotorDiameter() + 1) / 2) {
                this.obstructedCrossSection = 0;
            }
            if (this.obstructedCrossSection < 0) {
                this.windStrength = 0.0;
                this.setRotationSpeed(0.0f);
            } else {
                this.windStrength = this.calcWindStrength();
                float speed = (float)Util.limit((this.windStrength - (double)this.getMinWindStrength()) / (double)this.getMaxWindStrength(), 0.0, 2.0);
                this.setRotationSpeed(speed);
                if (this.windStrength >= (double)this.getMinWindStrength()) {
                    if (this.windStrength <= (double)this.getMaxWindStrength()) {
                        this.rotorSlot.damage(1, false);
                    } else {
                        this.rotorSlot.damage(4, false);
                    }
                    needsInvUpdate = true;
                }
            }
        } else {
            this.setRotationSpeed(0.0f);
        }
        if (needsInvUpdate) {
            this.func_70296_d();
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("rotationSpeed");
        ret.add("rotorSlot");
        return ret;
    }

    public ContainerBase<TileEntityWindKineticGenerator> getGuiContainer(EntityPlayer player) {
        return new ContainerWindKineticGenerator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiWindKineticGenerator(new ContainerWindKineticGenerator(player, this));
    }

    public boolean facingMatchesDirection(EnumFacing direction) {
        return direction == this.getFacing();
    }

    public String getRotorHealth() {
        if (!this.rotorSlot.isEmpty()) {
            return Localization.translate("ic2.WindKineticGenerator.gui.rotorhealth", (int)(100.0f - (float)this.rotorSlot.get().func_77952_i() / (float)this.rotorSlot.get().func_77958_k() * 100.0f));
        }
        return "";
    }

    @Override
    public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
        return this.getConnectionBandwidth(directionFrom);
    }

    @Override
    public int getConnectionBandwidth(EnumFacing side) {
        return this.facingMatchesDirection(side.func_176734_d()) ? this.getKuOutput() : 0;
    }

    @Override
    public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
        return this.drawKineticEnergy(directionFrom, requestkineticenergy, false);
    }

    @Override
    public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
        if (this.facingMatchesDirection(side.func_176734_d())) {
            return Math.min(request, this.getKuOutput());
        }
        return 0;
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public int checkSpace(int length, boolean onlyrotor) {
        int box = this.getRotorDiameter() / 2;
        int lentemp = 0;
        if (onlyrotor) {
            length = 1;
            lentemp = length + 1;
        }
        if (!onlyrotor) {
            box *= 2;
        }
        EnumFacing fwdDir = this.getFacing();
        EnumFacing rightDir = fwdDir.func_176732_a(EnumFacing.DOWN.func_176740_k());
        int xMaxDist = Math.abs(length * fwdDir.func_82601_c() + box * rightDir.func_82601_c());
        int zMaxDist = Math.abs(length * fwdDir.func_82599_e() + box * rightDir.func_82599_e());
        ChunkCache chunkCache = new ChunkCache(this.func_145831_w(), this.field_174879_c.func_177982_a(-xMaxDist, -box, -zMaxDist), this.field_174879_c.func_177982_a(xMaxDist, box, zMaxDist), 0);
        int ret = 0;
        int xCoord = this.field_174879_c.func_177958_n();
        int yCoord = this.field_174879_c.func_177956_o();
        int zCoord = this.field_174879_c.func_177952_p();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int up = -box; up <= box; ++up) {
            int y = yCoord + up;
            for (int right = -box; right <= box; ++right) {
                boolean occupied = false;
                for (int fwd = lentemp - length; fwd <= length; ++fwd) {
                    int x = xCoord + fwd * fwdDir.func_82601_c() + right * rightDir.func_82601_c();
                    int z = zCoord + fwd * fwdDir.func_82599_e() + right * rightDir.func_82599_e();
                    pos.func_181079_c(x, y, z);
                    assert (Math.abs(x - xCoord) <= xMaxDist);
                    assert (Math.abs(z - zCoord) <= zMaxDist);
                    IBlockState state = chunkCache.func_180495_p((BlockPos)pos);
                    Block block = state.func_177230_c();
                    if (block.isAir(state, (IBlockAccess)chunkCache, (BlockPos)pos)) continue;
                    occupied = true;
                    if (up == 0 && right == 0 && fwd == 0 || !(chunkCache.func_175625_s((BlockPos)pos) instanceof TileEntityWindKineticGenerator) || onlyrotor) continue;
                    return -1;
                }
                if (!occupied) continue;
                ++ret;
            }
        }
        return ret;
    }

    public boolean hasRotor() {
        return !this.rotorSlot.isEmpty();
    }

    public boolean rotorHasSpace() {
        return this.checkSpace(1, true) == 0;
    }

    private void setRotationSpeed(float speed) {
        if (this.rotationSpeed != speed) {
            this.rotationSpeed = speed;
            IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
        }
    }

    public int getTickRate() {
        return 32;
    }

    public double calcWindStrength() {
        double windStr = WorldData.get((World)this.func_145831_w()).windSim.getWindAt(this.field_174879_c.func_177956_o());
        return Math.max(0.0, windStr *= 1.0 - Math.pow((double)this.obstructedCrossSection / (double)this.crossSection, 2.0));
    }

    @Override
    public float getAngle() {
        if (this.rotationSpeed != 0.0f) {
            this.angle += (float)(System.currentTimeMillis() - this.lastcheck) * this.rotationSpeed;
            this.angle %= 360.0f;
        }
        this.lastcheck = System.currentTimeMillis();
        return this.angle;
    }

    public float getEfficiency() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getEfficiency(stack);
        }
        return 0.0f;
    }

    public int getMinWindStrength() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getMinWindStrength(stack);
        }
        return 0;
    }

    public int getMaxWindStrength() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getMaxWindStrength(stack);
        }
        return 0;
    }

    @Override
    public int getRotorDiameter() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getDiameter(stack);
        }
        return 0;
    }

    @Override
    public ResourceLocation getRotorRenderTexture() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getRotorRenderTexture(stack);
        }
        return woodenRotorTexture;
    }

    public boolean isRotorOverloaded() {
        return this.hasRotor() && this.rotorHasSpace() && this.isWindStrongEnough() && this.windStrength > (double)this.getMaxWindStrength();
    }

    public boolean isWindStrongEnough() {
        return this.windStrength >= (double)this.getMinWindStrength();
    }

    public int getKuOutput() {
        if (this.windStrength >= (double)this.getMinWindStrength() && this.getActive()) {
            return (int)(this.windStrength * (double)outputModifier * (double)this.getEfficiency());
        }
        return 0;
    }

    public int getWindStrength() {
        return (int)this.windStrength;
    }

    public int getObstructions() {
        return this.obstructedCrossSection;
    }

    @Override
    public void setActive(boolean active) {
        if (active != this.getActive()) {
            IC2.network.get(true).updateTileEntityField(this, "rotorSlot");
        }
        super.setActive(active);
    }
}

