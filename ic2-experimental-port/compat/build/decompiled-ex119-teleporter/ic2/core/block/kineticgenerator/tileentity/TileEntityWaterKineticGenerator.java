/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Blocks
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
import ic2.core.block.kineticgenerator.container.ContainerWaterKineticGenerator;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.BiomeUtil;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityWaterKineticGenerator
extends TileEntityInventory
implements IKineticSource,
IRotorProvider,
IHasGui {
    public InvSlotConsumableClass rotorSlot;
    public BiomeState type = BiomeState.UNKNOWN;
    protected int updateTicker = IC2.random.m_188503_(this.getTickRate());
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

    public TileEntityWaterKineticGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.WATER_KINETIC_GENERATOR, blockPos, blockState);
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
        Level level = this.m_58904_();
        if (this.type == BiomeState.UNKNOWN) {
            Holder<Biome> holder = BiomeUtil.getBiome((LevelReader)level, this.f_58858_);
            if (holder.m_203656_(BiomeTags.f_207603_)) {
                this.type = BiomeState.OCEAN;
            } else if (holder.m_203656_(BiomeTags.f_207602_)) {
                this.type = BiomeState.DEAP_OCEAN;
            } else if (holder.m_203656_(BiomeTags.f_207605_)) {
                this.type = BiomeState.RIVER;
            } else {
                this.type = BiomeState.INVALID;
                return;
            }
        }
        boolean bl = this.getActive();
        boolean bl2 = false;
        if (!this.rotorSlot.isEmpty() && this.checkSpace(1, true) == 0) {
            if (!bl) {
                bl2 = true;
                bl = true;
            }
        } else if (bl) {
            bl = false;
            bl2 = true;
        }
        if (bl) {
            this.crossSection = Util.square(this.getRotorDiameter() / 2 * 2 * 2 + 1);
            this.obstructedCrossSection = this.checkSpace(this.getRotorDiameter() * 3, false);
            if (this.obstructedCrossSection > 0 && this.obstructedCrossSection <= (this.getRotorDiameter() + 1) / 2) {
                this.obstructedCrossSection = 0;
            }
            int n = 0;
            if (this.obstructedCrossSection < 0) {
                this.stopSpinning();
            } else if (this.type == BiomeState.OCEAN) {
                float f = (float)Math.sin((double)level.m_46468_() * Math.PI / 6000.0);
                f *= Math.abs(f);
                this.rotationSpeed = (float)((double)(f * (float)this.distanceToNormalBiome / 100.0f) * (1.0 - Math.pow((double)this.obstructedCrossSection / (double)this.crossSection, 2.0)));
                this.waterFlow = (int)(this.rotationSpeed * 3000.0f);
                if (this.rightFacing) {
                    this.rotationSpeed *= -1.0f;
                }
                IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
                this.waterFlow = (int)((float)this.waterFlow * this.getEfficiency());
                n = 2;
            } else if (this.type == BiomeState.DEAP_OCEAN) {
                float f = (float)Math.sin((double)level.m_46468_() * Math.PI / 6000.0);
                f *= Math.abs(f);
                this.rotationSpeed = (float)((double)(f * (float)this.distanceToNormalBiome / 100.0f) * (1.0 - Math.pow((double)this.obstructedCrossSection / (double)this.crossSection, 2.0)));
                this.waterFlow = (int)(this.rotationSpeed * 4000.0f);
                if (this.rightFacing) {
                    this.rotationSpeed *= -1.0f;
                }
                IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
                this.waterFlow = (int)((float)this.waterFlow * this.getEfficiency());
                n = 3;
            } else if (this.type == BiomeState.RIVER) {
                this.rotationSpeed = (float)Util.limit(this.distanceToNormalBiome, 20, 50) / 50.0f;
                this.waterFlow = (int)(this.rotationSpeed * 1000.0f);
                if (this.getFacing() == Direction.EAST || this.getFacing() == Direction.NORTH) {
                    this.rotationSpeed *= -1.0f;
                }
                IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
                this.waterFlow = (int)((float)this.waterFlow * (this.getEfficiency() * (1.0f - 0.3f * level.f_46441_.m_188501_() - 0.1f * ((float)this.obstructedCrossSection / (float)this.crossSection))));
                n = 1;
            }
            this.rotorSlot.damage(n, false);
        } else {
            this.stopSpinning();
        }
        this.setActive(bl);
        if (bl2) {
            this.m_6596_();
        }
    }

    protected void stopSpinning() {
        boolean bl = this.rotationSpeed != 0.0f;
        this.rotationSpeed = 0.0f;
        this.waterFlow = 0;
        if (bl) {
            IC2.network.get(true).updateTileEntityField(this, "rotationSpeed");
        }
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateSeaInfo();
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("rotationSpeed");
        list.add("rotorSlot");
        return list;
    }

    @Override
    public int getRotorDiameter() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            if (this.type == BiomeState.OCEAN) {
                return ((IKineticRotor)itemStack.m_41720_()).getDiameter(itemStack);
            }
            return (((IKineticRotor)itemStack.m_41720_()).getDiameter(itemStack) + 1) * 2 / 3;
        }
        return 0;
    }

    public int checkSpace(int n, boolean bl) {
        int n2 = this.getRotorDiameter() / 2;
        int n3 = 0;
        if (bl) {
            n = 1;
            n3 = n + 1;
        } else {
            n2 *= 2;
        }
        Direction direction = this.getFacing();
        Direction direction2 = direction.m_175362_(Direction.Axis.Y);
        int n4 = 0;
        int n5 = this.f_58858_.m_123341_();
        int n6 = this.f_58858_.m_123342_();
        int n7 = this.f_58858_.m_123343_();
        Level level = this.m_58904_();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = -n2; i <= n2; ++i) {
            int n8 = n6 + i;
            for (int j = -n2; j <= n2; ++j) {
                boolean bl2 = false;
                for (int k = n3 - n; k <= n; ++k) {
                    int n9 = n5 + k * direction.m_122429_() + j * direction2.m_122429_();
                    int n10 = n7 + k * direction.m_122431_() + j * direction2.m_122431_();
                    mutableBlockPos.m_122178_(n9, n8, n10);
                    if (level.m_8055_((BlockPos)mutableBlockPos).m_60734_() == Blocks.f_49990_) continue;
                    bl2 = true;
                    if (i == 0 && j == 0 && k == 0 || !(level.m_7702_((BlockPos)mutableBlockPos) instanceof TileEntityWaterKineticGenerator) || bl) continue;
                    return -1;
                }
                if (!bl2) continue;
                ++n4;
            }
        }
        return n4;
    }

    public void updateSeaInfo() {
        Level level = this.m_58904_();
        Direction direction = this.getFacing();
        for (int i = 1; i < 200; ++i) {
            Holder<Biome> holder = BiomeUtil.getBiome((LevelReader)level, this.f_58858_.m_5484_(direction, i));
            if (!this.isValidBiome(holder)) {
                this.distanceToNormalBiome = i;
                this.rightFacing = true;
                return;
            }
            holder = BiomeUtil.getBiome((LevelReader)level, this.f_58858_.m_5484_(direction, -i));
            if (this.isValidBiome(holder)) continue;
            this.distanceToNormalBiome = i;
            this.rightFacing = false;
            return;
        }
        this.distanceToNormalBiome = 200;
        this.rightFacing = true;
    }

    public boolean isValidBiome(Holder<Biome> holder) {
        return holder.m_203656_(BiomeTags.f_207605_) || holder.m_203656_(BiomeTags.f_207603_) || holder.m_203656_(BiomeTags.f_207602_);
    }

    @Override
    public int maxrequestkineticenergyTick(Direction direction) {
        return this.getConnectionBandwidth(direction);
    }

    @Override
    public int getConnectionBandwidth(Direction direction) {
        return direction.m_122424_() == this.getFacing() ? this.getKuOutput() : 0;
    }

    @Override
    public int requestkineticenergy(Direction direction, int n) {
        return this.drawKineticEnergy(direction, n, false);
    }

    @Override
    public int drawKineticEnergy(Direction direction, int n, boolean bl) {
        if (direction.m_122424_() == this.getFacing()) {
            return Math.min(n, this.getKuOutput());
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
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getEfficiency(itemStack);
        }
        return 0.0f;
    }

    public ContainerBase<TileEntityWaterKineticGenerator> createServerScreenHandler(int n, Player player) {
        return new ContainerWaterKineticGenerator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerWaterKineticGenerator(n, inventory, this);
    }

    public String getRotorHealth() {
        if (!this.rotorSlot.isEmpty()) {
            return Localization.translate("ic2.WaterKineticGenerator.gui.rotorhealth", (int)(100.0f - (float)this.rotorSlot.get().m_41773_() / (float)this.rotorSlot.get().m_41776_() * 100.0f));
        }
        return "";
    }

    @Override
    public ResourceLocation getRotorRenderTexture() {
        ItemStack itemStack = this.rotorSlot.get();
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IKineticRotor) {
            return ((IKineticRotor)itemStack.m_41720_()).getRotorRenderTexture(itemStack);
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
        DEAP_OCEAN,
        RIVER,
        INVALID;

    }
}

