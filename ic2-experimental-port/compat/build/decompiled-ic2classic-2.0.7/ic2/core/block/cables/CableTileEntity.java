/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.cables;

import ic2.api.blocks.PainterHelper;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyConductorAnchored;
import ic2.api.energy.tile.IEnergyConductorColored;
import ic2.api.energy.tile.IEnergyConductorModifiable;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.cables.CableBlock;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public abstract class CableTileEntity
extends BaseTileEntity
implements IEnergyConductorColored,
IEnergyConductorAnchored,
IEnergyConductorModifiable,
IDropProvider,
IClickable,
ICamouflagable,
ISpecialWrenchable {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int connectivity = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int anchors = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int cableColor = 0;
    @NetworkInfo
    public byte foamed = 0;
    @NetworkInfo
    public byte insulation = 0;
    @NetworkInfo
    public CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);
    protected boolean addedToEnergyNet;
    final int maxInsulation;

    public CableTileEntity(BlockPos pos, BlockState state, int max) {
        super(pos, state);
        this.maxInsulation = max;
        this.addNetworkFields("connectivity", "anchors", "cableColor", "insulation", "foamed", "storage");
    }

    public boolean hasUninsulatedColor() {
        return false;
    }

    public boolean canDye(int insulation) {
        return insulation > 0;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.connectivity = compound.m_128451_("connections");
        this.anchors = compound.m_128451_("anchors");
        this.cableColor = compound.m_128451_("color");
        this.foamed = compound.m_128445_("foamed");
        this.insulation = NBTUtils.getByte(compound, "insulation", (byte)this.maxInsulation);
        this.storage.load(compound.m_128469_("camouflage"));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128473_("facing");
        NBTUtils.putByte(compound, "connections", this.connectivity, 0);
        NBTUtils.putByte(compound, "anchors", this.anchors, 0);
        NBTUtils.putByte(compound, "color", this.cableColor, 0);
        NBTUtils.putByte(compound, "foamed", (int)this.foamed, 0);
        NBTUtils.putByte(compound, "insulation", (int)this.insulation, this.maxInsulation);
        NBTUtils.putIf(compound, "camouflage", this.storage.save(new CompoundTag()), this.foamed > 0);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("connectivity") || fields.contains("anchors") || fields.contains("foamed") || fields.contains("storage")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        BlockState dustState;
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41619_()) {
            return false;
        }
        if (stack.m_204117_(ItemTags.f_13137_) && this.foamed == 1) {
            this.changeFoam((byte)2, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        if (IC2Blocks.CFOAM_WET.m_5456_() == stack.m_41720_() && this.foamed == 0) {
            this.changeFoam((byte)1, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        if (stack.m_41720_() == IC2Blocks.MINING_PIPE_SHAFT.m_5456_()) {
            Direction click = ITubeBlock.isClickingAt(hit.m_82450_().m_82492_((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_()), side, this.getCableWidth() / 16.0f);
            if (click != null) {
                this.addAnchor(click);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        } else if (this.foamed == 1 && PainterHelper.CONCRETE_DUST.contains((dustState = Block.m_49814_((Item)stack.m_41720_()).m_49966_()).m_60734_())) {
            this.storage.setColor(DirectionList.ALL, PainterHelper.INSTANCE.getColor(dustState));
            this.changeFoam((byte)2, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        float radius = this.getCableWidth() / 16.0f;
        Direction dir = ITubeBlock.isClickingAt(hit, side, radius);
        return dir == null || !this.hasAnchor(dir) ? null : ITubeBlock.getHitBox(hit, side, radius);
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        Direction dir = ITubeBlock.isClickingAt(hit, side, this.getCableWidth() / 16.0f);
        if (dir != null && this.hasAnchor(dir)) {
            this.removeAnchor(dir);
            StackUtil.addOrPop(player, this.m_58899_(), new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT));
            return true;
        }
        return false;
    }

    public float getCableWidth() {
        BlockState state = this.m_58900_();
        if (state.m_60734_() instanceof ITubeBlock) {
            return ((ITubeBlock)state.m_60734_()).getRadius(state);
        }
        return -1.0f;
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.foamed == 2;
    }

    @Override
    public boolean removeCamouflage() {
        return this.changeFoam((byte)0, false);
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.foamed == 2 && this.storage.canApply(dir, container)) {
            this.storage.setTexture(DirectionList.ofFacing(dir), container);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (!this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        if (this.isSimulating()) {
            this.tickFoamed();
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public Component m_7755_() {
        return this.m_7770_();
    }

    public int getConnectivity() {
        return this.connectivity | this.anchors;
    }

    public int getAnchors() {
        return this.anchors;
    }

    public int getConnections() {
        return this.connectivity;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        if (this.foamed == 2) {
            return ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, R -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)R)).build();
        }
        return ModelData.builder().with((ModelProperty)CableProperty.INSTANCE, (Object)(this.connectivity << 6 | this.anchors)).build();
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return (this.connectivity & 1 << side.m_122411_()) != 0;
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return (this.connectivity & 1 << side.m_122411_()) != 0;
    }

    @Override
    public void removeInsulation() {
        this.setInsulation(0);
    }

    @Override
    public void removeConductor() {
        this.f_58857_.m_7471_(this.m_58899_(), false);
    }

    @Override
    public DyeColor getColor() {
        return this.cableColor == 0 ? null : DyeColor.m_41053_((int)(this.cableColor - 1));
    }

    @Override
    public boolean hasAnchor(Direction side) {
        return (this.anchors & 1 << side.m_122411_()) != 0;
    }

    @Override
    public boolean addAnchor(Direction side) {
        if (this.hasAnchor(side)) {
            return false;
        }
        this.anchors |= 1 << side.m_122411_();
        this.updateTileField("anchors");
        EnergyNet.INSTANCE.updateTile(this);
        return true;
    }

    @Override
    public boolean removeAnchor(Direction side) {
        if (this.hasAnchor(side)) {
            this.anchors &= ~(1 << side.m_122411_());
            this.updateTileField("anchors");
            EnergyNet.INSTANCE.updateTile(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean tryAddInsulation() {
        if (this.insulation < this.maxInsulation) {
            this.insulation = (byte)(this.insulation + 1);
            this.updateTileField("insulation");
            this.onStateChanged();
            return true;
        }
        return false;
    }

    @Override
    public boolean tryRemoveInsulation() {
        if (this.insulation > 0) {
            this.insulation = (byte)(this.insulation - 1);
            if (this.insulation == 0) {
                this.cableColor = 0;
                this.updateTileField("cableColor");
                EnergyNet.INSTANCE.updateTile(this);
            }
            this.updateTileField("insulation");
            this.onStateChanged();
            return true;
        }
        return false;
    }

    public boolean setColor(Direction dir, DyeColor color) {
        if (!(this.foamed != 0 || this.cableColor != (color == null ? 0 : color.m_41060_() + 1) && this.canDye(this.insulation))) {
            return false;
        }
        if (this.foamed == 1) {
            return false;
        }
        if (this.foamed == 2 && !this.storage.canApply(dir == null ? DirectionList.ALL : DirectionList.ofFacing(dir), color)) {
            return false;
        }
        if (this.isSimulating()) {
            if (this.foamed == 0) {
                this.cableColor = color == null ? 0 : color.m_41060_() + 1;
                EnergyNet.INSTANCE.updateTile(this);
                this.updateTileField("cableColor");
                this.onStateChanged();
            } else if (this.foamed == 2) {
                this.storage.setColor(dir == null ? DirectionList.ALL : DirectionList.ofFacings(dir), color);
                this.updateTileField("storage");
            }
        }
        return true;
    }

    public boolean changeFoam(byte level, boolean duringLoad) {
        if (this.foamed == level) {
            return false;
        }
        if (this.isSimulating()) {
            this.foamed = level;
            if (this.foamed == 1) {
                this.storage.setColor(DirectionList.ALL, DyeColor.LIGHT_GRAY);
                if (!duringLoad) {
                    this.updateTileField("storage");
                }
                this.tickFoamed();
            }
            if (!duringLoad) {
                this.updateTileField("foamed");
                this.onStateChanged();
            }
        }
        return true;
    }

    private void tickFoamed() {
        if (this.foamed != 1) {
            return;
        }
        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, new ToIntFunction<Level>(){

            @Override
            public int applyAsInt(Level world) {
                if (CableTileEntity.this.m_58901_() || CableTileEntity.this.foamed != 1) {
                    return 0;
                }
                if (world.m_46472_().equals((Object)Level.f_46429_)) {
                    if (world.f_46441_.m_188503_(5) == 0) {
                        CableTileEntity.this.changeFoam((byte)2, false);
                        return 0;
                    }
                    return 20;
                }
                if (world.m_46803_(CableTileEntity.this.f_58858_) * 6 > world.f_46441_.m_188503_(1000)) {
                    CableTileEntity.this.changeFoam((byte)2, false);
                    return 0;
                }
                return 500;
            }
        });
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.m_58900_().m_61138_((Property)CableBlock.WATER) && ((Boolean)this.m_58900_().m_61143_((Property)CableBlock.WATER)).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        } else if (this.m_58900_().m_61138_((Property)IC2Properties.LAVA_LOGGED) && ((Boolean)this.m_58900_().m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)this.f_58857_));
        }
        if (this.isRendering()) {
            return;
        }
        DirectionList newConnection = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            IEnergyTile tile = EnergyNet.INSTANCE.getSubTile(this.m_58904_(), this.m_58899_().m_121945_(dir));
            if (!(tile instanceof IEnergyAcceptor) && !(tile instanceof IEnergyEmitter)) {
                tile = EnergyNet.INSTANCE.getTile(this.m_58904_(), this.m_58899_().m_121945_(dir));
            }
            if (tile == null || !this.canInteractWith(tile, dir)) continue;
            newConnection = newConnection.add(dir);
        }
        if (newConnection.getCode() != this.connectivity) {
            this.connectivity = newConnection.getCode();
            this.updateTileField("connectivity");
        }
    }

    public void setInsulation(int insulation) {
        if (this.insulation != (insulation = Mth.m_14045_((int)insulation, (int)0, (int)this.maxInsulation))) {
            this.insulation = (byte)insulation;
            if (insulation == 0 && !this.hasUninsulatedColor()) {
                this.cableColor = 0;
            }
            EnergyNet.INSTANCE.updateTile(this);
            this.updateTileField("cableColor");
            this.updateTileField("insulation");
            this.onStateChanged();
        }
    }

    public boolean canInteractWith(IEnergyTile tile, Direction side) {
        if (this.hasAnchor(side)) {
            return false;
        }
        if (tile instanceof IEnergyConductorAnchored && ((IEnergyConductorAnchored)tile).hasAnchor(side.m_122424_())) {
            return false;
        }
        if (tile instanceof IEnergyConductorColored) {
            return this.canInteractWithAPICable((IEnergyConductorColored)tile);
        }
        if (tile instanceof IEnergyConductor) {
            return true;
        }
        if (tile instanceof IEnergyAcceptor && !(tile instanceof IEnergyEmitter)) {
            return ((IEnergyAcceptor)tile).canAcceptEnergy(this, side.m_122424_());
        }
        if (tile instanceof IEnergyEmitter && !(tile instanceof IEnergyAcceptor)) {
            return ((IEnergyEmitter)tile).canEmitEnergy(this, side.m_122424_());
        }
        if (tile instanceof IEnergyAcceptor) {
            return ((IEnergyEmitter)tile).canEmitEnergy(this, side.m_122424_()) || ((IEnergyAcceptor)tile).canAcceptEnergy(this, side.m_122424_());
        }
        return this.canSpecialConnect(tile, side);
    }

    public boolean canInteractWithAPICable(IEnergyConductorColored cable) {
        return this.cableColor == 0 || cable.getColor() == null || DyeColor.m_41053_((int)(this.cableColor - 1)) == cable.getColor();
    }

    public boolean canSpecialConnect(IEnergyTile other, Direction side) {
        return false;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        int count = DirectionList.ofNumber(this.anchors).size();
        if (count > 0) {
            drops.add(new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT, count));
        }
    }

    @Override
    public boolean isLavaLogged() {
        BlockState state = this.m_58900_();
        return state.m_61138_((Property)IC2Properties.LAVA_LOGGED) && (Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED) != false;
    }
}

