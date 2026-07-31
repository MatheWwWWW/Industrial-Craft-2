/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.kineticgenerator.tileentity;

import ic2.api.energy.tile.IKineticSource;
import ic2.api.item.IKineticRotor;
import ic2.api.tile.IRotorProvider;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotConsumableKineticRotor;
import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.event.WorldData;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
    private int updateTicker = IC2.random.m_188503_(this.getTickRate());
    private float rotationSpeed;
    private float angle = 0.0f;
    private long lastcheck;
    private static final double efficiencyRollOffExponent = 2.0;
    public static final float outputModifier = 10.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/wind");
    private static final ResourceLocation woodenRotorTexture = new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png");

    public TileEntityWindKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.WIND_KINETIC_GENERATOR, blockPos, blockState);
        this.rotorSlot = new InvSlotConsumableKineticRotor(this, "rotorslot", InvSlot.Access.IO, 1, InvSlot.InvSide.ANY, IKineticRotor.GearboxType.WIND, "rotorSlot");
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.getTickRate() != 0) {
            return;
        }
        boolean bl = false;
        boolean bl2 = this.getActive();
        if ((this.hasRotor() && this.rotorHasSpace()) != bl2) {
            bl2 = !bl2;
            this.setActive(bl2);
            bl = true;
        }
        if (bl2) {
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
                float f = (float)Util.limit((this.windStrength - (double)this.getMinWindStrength()) / (double)this.getMaxWindStrength(), 0.0, 2.0);
                this.setRotationSpeed(f);
                if (this.windStrength >= (double)this.getMinWindStrength()) {
                    if (this.windStrength <= (double)this.getMaxWindStrength()) {
                        this.rotorSlot.damage(1, false);
                    } else {
                        this.rotorSlot.damage(4, false);
                    }
                    bl = true;
                }
            }
        } else {
            this.setRotationSpeed(0.0f);
        }
        if (bl) {
            this.m_6596_();
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("rotationSpeed");
        list.add("rotorSlot");
        return list;
    }

    public ContainerBase<TileEntityWindKineticGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerWindKineticGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerWindKineticGenerator(n, inventory, this);
    }

    public boolean facingMatchesDirection(Direction direction) {
        return direction == this.getFacing();
    }

    public String getRotorHealth() {
        if (!this.rotorSlot.isEmpty()) {
            return Localization.translate("ic2.WindKineticGenerator.gui.rotorhealth", (int)(100.0f - (float)this.rotorSlot.get().m_41773_() / (float)this.rotorSlot.get().m_41776_() * 100.0f));
        }
        return "";
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return this.getConnectionBandwidth(direction);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        return this.facingMatchesDirection(direction.m_122424_()) ? this.getKuOutput() : 0;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        if (this.facingMatchesDirection(direction.m_122424_())) {
            return Math.min(n, this.getKuOutput());
        }
        return 0;
    }

    public int checkSpace(int n, boolean bl) {
        int n2 = this.getRotorDiameter() / 2;
        int n3 = 0;
        if (bl) {
            n = 1;
            n3 = n + 1;
        }
        if (!bl) {
            n2 *= 2;
        }
        Direction direction = this.getFacing();
        Direction direction2 = direction.m_175362_(Direction.Axis.Y);
        int n4 = Math.abs(n * direction.m_122429_() + n2 * direction2.m_122429_());
        int n5 = Math.abs(n * direction.m_122431_() + n2 * direction2.m_122431_());
        PathNavigationRegion pathNavigationRegion = new PathNavigationRegion(this.m_58904_(), this.f_58858_.m_7918_(-n4, -n2, -n5), this.f_58858_.m_7918_(n4, n2, n5));
        int n6 = 0;
        int n7 = this.f_58858_.m_123341_();
        int n8 = this.f_58858_.m_123342_();
        int n9 = this.f_58858_.m_123343_();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = -n2; i <= n2; ++i) {
            int n10 = n8 + i;
            for (int j = -n2; j <= n2; ++j) {
                boolean bl2 = false;
                for (int k = n3 - n; k <= n; ++k) {
                    int n11 = n7 + k * direction.m_122429_() + j * direction2.m_122429_();
                    int n12 = n9 + k * direction.m_122431_() + j * direction2.m_122431_();
                    mutableBlockPos.m_122178_(n11, n10, n12);
                    assert (Math.abs(n11 - n7) <= n4);
                    assert (Math.abs(n12 - n9) <= n5);
                    BlockState blockState = pathNavigationRegion.m_8055_((BlockPos)mutableBlockPos);
                    Block block = blockState.m_60734_();
                    if (blockState.m_60795_()) continue;
                    bl2 = true;
                    if (i == 0 && j == 0 && k == 0 || !(pathNavigationRegion.m_7702_((BlockPos)mutableBlockPos) instanceof TileEntityWindKineticGenerator) || bl) continue;
                    return -1;
                }
                if (!bl2) continue;
                ++n6;
            }
        }
        return n6;
    }

    public boolean hasRotor() {
        return !this.rotorSlot.isEmpty();
    }

    public boolean rotorHasSpace() {
        return this.checkSpace(1, true) == 0;
    }

    private void setRotationSpeed(float f) {
        if (this.rotationSpeed != f) {
            this.rotationSpeed = f;
            IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
        }
    }

    public int getTickRate() {
        return 32;
    }

    public double calcWindStrength() {
        double d = WorldData.get((Level)this.m_58904_()).windSim.getWindAt(this.f_58858_.m_123342_());
        return Math.max(0.0, d *= 1.0 - Math.pow((double)this.obstructedCrossSection / (double)this.crossSection, 2.0));
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
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getEfficiency(itemStack);
        }
        return 0.0f;
    }

    public int getMinWindStrength() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getMinWindStrength(itemStack);
        }
        return 0;
    }

    public int getMaxWindStrength() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getMaxWindStrength(itemStack);
        }
        return 0;
    }

    @Override
    public int getRotorDiameter() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getDiameter(itemStack);
        }
        return 0;
    }

    @Override
    public ResourceLocation getRotorRenderTexture() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getRotorRenderTexture(itemStack);
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
    public void setActive(boolean bl) {
        if (bl != this.getActive()) {
            IC2.network.get(true).updateTileEntityField(this, "rotorSlot");
        }
        super.setActive(bl);
    }
}

