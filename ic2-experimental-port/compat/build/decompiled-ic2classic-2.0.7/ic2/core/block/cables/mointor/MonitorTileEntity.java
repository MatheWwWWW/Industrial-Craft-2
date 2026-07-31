/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.cables.mointor;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.impl.LinkedSink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergyTile;
import ic2.api.items.IDisplayProvider;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.cables.mointor.MonitorBlock;
import ic2.core.block.cables.mointor.MonitorContainer;
import ic2.core.block.cables.mointor.MonitorDataManager;
import ic2.core.block.cables.mointor.MonitorGrid;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.ConnectionState;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

public class MonitorTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IMultiEnergyTile,
INotifyInventory {
    @NetworkInfo
    public int width = 1;
    @NetworkInfo
    public int height = 1;
    @NetworkInfo
    public MonitorDataManager dataManager = new MonitorDataManager();
    @NetworkInfo
    public IDisplayInfo.Alignment align = IDisplayInfo.Alignment.LEFT;
    @NetworkInfo
    public DyeColor color = DyeColor.LIME;
    @NetworkInfo
    public float scale = 4.0f;
    @NetworkInfo
    public boolean requireRedstone = true;
    public int gridId = -1;
    boolean invDirty = true;
    boolean firstTick = true;
    boolean energyNetDirty = false;

    public MonitorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 8, 8192, 1000);
        this.addNetworkFields("width", "height", "dataManager", "align", "scale", "color", "requireRedstone");
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.width = NBTUtils.getInt(compound, "width", 1);
        this.height = NBTUtils.getInt(compound, "height", 1);
        this.align = NBTUtils.getEnum(compound, "align", IDisplayInfo.Alignment.class, IDisplayInfo.Alignment.LEFT);
        this.color = NBTUtils.getEnum(compound, "color", DyeColor.class, DyeColor.LIME);
        this.gridId = NBTUtils.getInt(compound, "grid_id", -1);
        this.scale = NBTUtils.getFloat(compound, "scale", 4.0f);
        this.requireRedstone = NBTUtils.getBoolean(compound, "redstone", true);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.isMaster()) {
            NBTUtils.putInt(compound, "width", this.width, 1);
            NBTUtils.putInt(compound, "height", this.height, 1);
            NBTUtils.putEnum(compound, "align", this.align, IDisplayInfo.Alignment.LEFT);
            NBTUtils.putFloat(compound, "scale", this.scale, 4.0f);
            NBTUtils.putBoolean(compound, "redstone", this.requireRedstone, true);
        }
        NBTUtils.putInt(compound, "grid_id", this.gridId, -1);
        NBTUtils.putEnum(compound, "color", this.color);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MONITOR;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    public boolean isLocked() {
        return (Boolean)this.m_58900_().m_61143_((Property)MonitorBlock.LOCKED);
    }

    public void setGridId(int id) {
        this.gridId = id;
    }

    public void updateGridState() {
        this.withState(MonitorBlock.LOCKED, this.gridId != -1);
    }

    public boolean isMaster() {
        BlockState state = this.m_58900_();
        return state.m_61138_((Property)MonitorBlock.MASTER) && (Boolean)state.m_61143_((Property)MonitorBlock.MASTER) != false;
    }

    public DyeColor getScreenColor() {
        return this.color;
    }

    public void setScreenColor(DyeColor color) {
        this.color = color;
        this.updateTileField("color");
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.isMaster();
    }

    @Override
    public boolean canInteractWith(Player player) {
        return ITileGui.super.canInteractWith(player) && this.isMaster();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MonitorContainer(this, player, windowID);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            MonitorGrid.setGridId(this.gridId == -1 ? new Random().nextInt() : -1, this);
        } else if (key == 1) {
            this.align = IDisplayInfo.Alignment.values()[value];
            this.updateTileField("align");
        } else if (key == 2) {
            float change = Float.intBitsToFloat(value);
            this.scale = Mth.m_14036_((float)(this.scale + change), (float)1.0f, (float)10.0f);
            this.updateTileField("scale");
        } else if (key == 3) {
            MonitorGrid.setGridColor(DyeColor.m_41053_((int)value), this);
        } else if (key == 4) {
            this.requireRedstone = !this.requireRedstone;
            this.updateGuiField("requireRedstone");
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("color")) {
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    public AABB getRenderBoundingBox() {
        return new AABB(this.m_58899_().m_7918_(1, 1, 1), this.m_58899_().m_5484_(MonitorBlock.getSearchDir(this.getFacing(), false), this.width).m_5484_(MonitorBlock.getSearchDir(this.getFacing(), true), this.height));
    }

    public void setBounds(int width, int height) {
        this.width = width;
        this.height = height;
        this.updateTileFields("width", "height");
        if (this.addedToEnet) {
            EnergyNet.INSTANCE.updateTile(this);
        }
    }

    @Override
    public List<IEnergyTile> getTiles() {
        ObjectList tiles = CollectionUtils.createList();
        Direction mainDir = MonitorBlock.getSearchDir(this.getFacing(), false);
        Direction subDir = MonitorBlock.getSearchDir(this.getFacing(), true);
        for (int i = 0; i < this.width; ++i) {
            for (int j = 0; j < this.height; ++j) {
                tiles.add((LinkedSink)new LinkedSink(this.f_58857_, this.m_58899_().m_5484_(mainDir, i).m_5484_(subDir, j)));
            }
        }
        return tiles;
    }

    @Override
    public void onLoaded() {
        if (this.isSimulating()) {
            boolean master = this.isMaster();
            if (!master) {
                this.addedToEnet = true;
            }
            super.onLoaded();
            if (!master) {
                this.addedToEnet = false;
            }
            MonitorGrid.register(this);
        } else {
            super.onLoaded();
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating()) {
            MonitorGrid.unregister(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public void onTick() {
        if (this.firstTick) {
            this.firstTick = false;
            if (!((Boolean)this.m_58900_().m_61143_((Property)MonitorBlock.MASTER)).booleanValue()) {
                this.removeFromTick();
                for (int i = 0; i < 8; ++i) {
                    if (((ItemStack)this.inventory.get(i)).m_41619_()) continue;
                    Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)((ItemStack)this.inventory.get(i)));
                    this.inventory.set(i, (Object)ItemStack.f_41583_);
                }
                return;
            }
            MonitorGrid.validateMaster(this);
        }
        if (this.isSimulating() && this.energyNetDirty && !this.addedToEnet && EnergyNet.INSTANCE.getTiles(this.m_58904_(), this.m_58899_()) == null) {
            this.energyNetDirty = false;
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        this.handleRedstone();
        int eu = Math.max(this.width, this.height);
        MonitorGrid.setActive((!this.requireRedstone || this.isRedstonePowered()) && this.hasEnergy(eu), this);
        if (this.isActive()) {
            this.useEnergy(eu);
            if (this.invDirty || this.clock(5) && this.dataManager.tick(this)) {
                if (this.invDirty) {
                    this.dataManager.clear();
                }
                this.invDirty = false;
                for (int i = 0; i < 8; ++i) {
                    ItemStack stack = (ItemStack)this.inventory.get(i);
                    if (!(stack.m_41720_() instanceof IDisplayProvider)) continue;
                    ((IDisplayProvider)stack.m_41720_()).provideInfo(stack, this.dataManager::addData);
                }
                this.updateTileField("dataManager");
            }
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating()) {
            this.invDirty = true;
        }
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (this.isSimulating()) {
            this.invDirty = true;
        }
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        boolean wasMaster = this.isMaster();
        DirectionList connection = MonitorBlock.getConnections(this.m_58904_(), this.m_58899_(), this.getFacing(), this.gridId);
        this.setState((BlockState)((BlockState)this.m_58900_().m_61124_((Property)MonitorBlock.MASTER, (Comparable)Boolean.valueOf(MonitorBlock.isMaster(connection, this.getFacing())))).m_61124_((Property)MonitorBlock.CONNECTION, (Comparable)Integer.valueOf(ConnectionState.fromList(connection, this.getFacing()).getCode())));
        if (wasMaster != this.isMaster()) {
            if (this.isMaster()) {
                this.addToTick();
                MonitorGrid.validateMaster(this);
                this.energyNetDirty = true;
            } else {
                this.removeFromTick();
                for (int i = 0; i < 4; ++i) {
                    if (((ItemStack)this.inventory.get(i)).m_41619_()) continue;
                    Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)((ItemStack)this.inventory.get(i)));
                    this.inventory.set(i, (Object)ItemStack.f_41583_);
                }
                if (this.addedToEnet) {
                    this.addedToEnet = false;
                    EnergyNet.INSTANCE.removeTile(this);
                }
            }
        }
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        MonitorGrid.update(this);
    }
}

