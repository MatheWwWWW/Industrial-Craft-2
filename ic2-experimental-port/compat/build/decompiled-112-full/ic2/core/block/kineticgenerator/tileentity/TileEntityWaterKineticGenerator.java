/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraftforge.common.BiomeDictionary
 *  net.minecraftforge.common.BiomeDictionary$Type
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.api.item.IKineticRotor;
import ic2.api.tile.IRotorProvider;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotConsumableKineticRotor;
import ic2.core.block.kineticgenerator.container.ContainerWaterKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiWaterKineticGenerator;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.BiomeUtil;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityWaterKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IRotorProvider,
IHasGui {
    public InvSlotConsumableClass rotorSlot;
    public BiomeState type = BiomeState.UNKNOWN;
    protected int updateTicker = IC2.random.nextInt(this.getTickRate());
    private boolean rightFacing;
    private int distanceToNormalBiome;
    private int crossSection;
    private int obstructedCrossSection;
    private int waterFlow;
    private long lastcheck;
    private float angle = 0.0f;
    private float rotationSpeed;
    private static final float rotationModifier = 0.1f;
    private static final double efficiencyRollOffExponent = 2.0;
    private static final float outputModifier = 0.2f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/water");
    private static final ResourceLocation woodenRotorTexture = new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png");

    public TileEntityWaterKineticGenerator() {
        this.rotorSlot = new InvSlotConsumableKineticRotor(this, "rotorslot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, IKineticRotor.GearboxType.WATER, "rotorSlot");
    }

    protected int getTickRate() {
        return 20;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateSeaInfo();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.getTickRate() != 0) {
            return;
        }
        World world = this.func_145831_w();
        if (this.type == BiomeState.UNKNOWN) {
            Biome biome = BiomeUtil.getBiome(world, this.field_174879_c);
            if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.OCEAN)) {
                this.type = BiomeState.OCEAN;
            } else if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.RIVER)) {
                this.type = BiomeState.RIVER;
            } else {
                this.type = BiomeState.INVALID;
                return;
            }
        }
        boolean nextActive = this.getActive();
        boolean needsInvUpdate = false;
        if (!this.rotorSlot.isEmpty() && this.checkSpace(1, true) == 0) {
            if (!nextActive) {
                needsInvUpdate = true;
                nextActive = true;
            }
        } else if (nextActive) {
            nextActive = false;
            needsInvUpdate = true;
        }
        if (nextActive) {
            this.crossSection = Util.square(this.getRotorDiameter() / 2 * 2 * 2 + 1);
            this.obstructedCrossSection = this.checkSpace(this.getRotorDiameter() * 3, false);
            if (this.obstructedCrossSection > 0 && this.obstructedCrossSection <= (this.getRotorDiameter() + 1) / 2) {
                this.obstructedCrossSection = 0;
            }
            int rotorDamage = 0;
            if (this.obstructedCrossSection < 0) {
                this.stopSpinning();
            } else if (this.type == BiomeState.OCEAN) {
                float diff = (float)Math.sin((double)world.func_72820_D() * Math.PI / 6000.0);
                diff *= Math.abs(diff);
                this.rotationSpeed = (float)((double)(diff * (float)this.distanceToNormalBiome / 100.0f) * (1.0 - Math.pow((double)this.obstructedCrossSection / (double)this.crossSection, 2.0)));
                this.waterFlow = (int)(this.rotationSpeed * 3000.0f);
                if (this.rightFacing) {
                    this.rotationSpeed *= -1.0f;
                }
                IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
                this.waterFlow = (int)((float)this.waterFlow * this.getEfficiency());
                rotorDamage = 2;
            } else if (this.type == BiomeState.RIVER) {
                this.rotationSpeed = (float)Util.limit(this.distanceToNormalBiome, 20, 50) / 50.0f;
                this.waterFlow = (int)(this.rotationSpeed * 1000.0f);
                if (this.getFacing() == EnumFacing.EAST || this.getFacing() == EnumFacing.NORTH) {
                    this.rotationSpeed *= -1.0f;
                }
                IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
                this.waterFlow = (int)((float)this.waterFlow * (this.getEfficiency() * (1.0f - 0.3f * world.field_73012_v.nextFloat() - 0.1f * ((float)this.obstructedCrossSection / (float)this.crossSection))));
                rotorDamage = 1;
            }
            this.rotorSlot.damage(rotorDamage, false);
        } else {
            this.stopSpinning();
        }
        this.setActive(nextActive);
        if (needsInvUpdate) {
            this.func_70296_d();
        }
    }

    protected void stopSpinning() {
        boolean update = this.rotationSpeed != 0.0f;
        this.rotationSpeed = 0.0f;
        this.waterFlow = 0;
        if (update) {
            IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
        }
    }

    @Override
    public void setFacing(EnumFacing side) {
        super.setFacing(side);
        this.updateSeaInfo();
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("rotationSpeed");
        ret.add("rotorSlot");
        return ret;
    }

    @Override
    public int getRotorDiameter() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            if (this.type == BiomeState.OCEAN) {
                return ((IKineticRotor)stack.func_77973_b()).getDiameter(stack);
            }
            return (((IKineticRotor)stack.func_77973_b()).getDiameter(stack) + 1) * 2 / 3;
        }
        return 0;
    }

    public int checkSpace(int length, boolean onlyrotor) {
        int box = this.getRotorDiameter() / 2;
        int lentemp = 0;
        if (onlyrotor) {
            length = 1;
            lentemp = length + 1;
        } else {
            box *= 2;
        }
        EnumFacing fwdDir = this.getFacing();
        EnumFacing rightDir = fwdDir.func_176732_a(EnumFacing.DOWN.func_176740_k());
        int ret = 0;
        int xCoord = this.field_174879_c.func_177958_n();
        int yCoord = this.field_174879_c.func_177956_o();
        int zCoord = this.field_174879_c.func_177952_p();
        World world = this.func_145831_w();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int up = -box; up <= box; ++up) {
            int y = yCoord + up;
            for (int right = -box; right <= box; ++right) {
                boolean occupied = false;
                for (int fwd = lentemp - length; fwd <= length; ++fwd) {
                    int x = xCoord + fwd * fwdDir.func_82601_c() + right * rightDir.func_82601_c();
                    int z = zCoord + fwd * fwdDir.func_82599_e() + right * rightDir.func_82599_e();
                    pos.func_181079_c(x, y, z);
                    if (world.func_180495_p((BlockPos)pos).func_177230_c() == Blocks.field_150355_j) continue;
                    occupied = true;
                    if (up == 0 && right == 0 && fwd == 0 || !(world.func_175625_s((BlockPos)pos) instanceof TileEntityWaterKineticGenerator) || onlyrotor) continue;
                    return -1;
                }
                if (!occupied) continue;
                ++ret;
            }
        }
        return ret;
    }

    public void updateSeaInfo() {
        World world = this.func_145831_w();
        EnumFacing facing = this.getFacing();
        for (int distance = 1; distance < 200; ++distance) {
            Biome biomeTemp = BiomeUtil.getBiome(world, this.field_174879_c.func_177967_a(facing, distance));
            if (!this.isValidBiome(biomeTemp)) {
                this.distanceToNormalBiome = distance;
                this.rightFacing = true;
                return;
            }
            biomeTemp = BiomeUtil.getBiome(world, this.field_174879_c.func_177967_a(facing, -distance));
            if (this.isValidBiome(biomeTemp)) continue;
            this.distanceToNormalBiome = distance;
            this.rightFacing = false;
            return;
        }
        this.distanceToNormalBiome = 200;
        this.rightFacing = true;
    }

    public boolean isValidBiome(Biome biome) {
        return BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.OCEAN) || BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.RIVER);
    }

    @Override
    public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
        return this.getConnectionBandwidth(directionFrom);
    }

    @Override
    public int getConnectionBandwidth(EnumFacing side) {
        return side.func_176734_d() == this.getFacing() ? this.getKuOutput() : 0;
    }

    @Override
    public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
        return this.drawKineticEnergy(directionFrom, requestkineticenergy, false);
    }

    @Override
    public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
        if (side.func_176734_d() == this.getFacing()) {
            return Math.min(request, this.getKuOutput());
        }
        return 0;
    }

    public int getKuOutput() {
        if (this.getActive()) {
            return (int)Math.abs((float)this.waterFlow * outputModifier);
        }
        return 0;
    }

    public float getEfficiency() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getEfficiency(stack);
        }
        return 0.0f;
    }

    public ContainerWaterKineticGenerator getGuiContainer(EntityPlayer player) {
        return new ContainerWaterKineticGenerator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiWaterKineticGenerator(this.getGuiContainer(player));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public String getRotorHealth() {
        if (!this.rotorSlot.isEmpty()) {
            return Localization.translate("ic2.WaterKineticGenerator.gui.rotorhealth", (int)(100.0f - (float)this.rotorSlot.get().func_77952_i() / (float)this.rotorSlot.get().func_77958_k() * 100.0f));
        }
        return "";
    }

    @Override
    public ResourceLocation getRotorRenderTexture() {
        ItemStack stack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IKineticRotor) {
            return ((IKineticRotor)stack.func_77973_b()).getRotorRenderTexture(stack);
        }
        return woodenRotorTexture;
    }

    @Override
    public float getAngle() {
        if (this.rotationSpeed != 0.0f) {
            this.angle += (float)(System.currentTimeMillis() - this.lastcheck) * this.rotationSpeed * 0.1f;
            this.angle %= 360.0f;
        }
        this.lastcheck = System.currentTimeMillis();
        return this.angle;
    }

    public static enum BiomeState {
        UNKNOWN,
        OCEAN,
        RIVER,
        INVALID;

    }
}

