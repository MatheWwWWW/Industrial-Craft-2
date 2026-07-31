/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.energy.prefab;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

abstract class BasicEnergyTile
implements ILocatable,
IEnergyTile {
    private final Object locationProvider;
    protected Level world;
    protected BlockPos pos;
    protected double capacity;
    protected double energyStored;
    protected boolean addedToEnet;

    protected BasicEnergyTile(BlockEntity blockEntity, double d) {
        this((Object)blockEntity, d);
    }

    protected BasicEnergyTile(ILocatable iLocatable, double d) {
        this((Object)iLocatable, d);
    }

    private BasicEnergyTile(Object object, double d) {
        this.locationProvider = object;
        this.capacity = d;
    }

    protected BasicEnergyTile(Level level, BlockPos blockPos, double d) {
        if (level == null) {
            throw new NullPointerException("null world");
        }
        if (blockPos == null) {
            throw new NullPointerException("null pos");
        }
        this.locationProvider = null;
        this.world = level;
        this.pos = blockPos;
        this.capacity = d;
    }

    public void update() {
        if (!this.addedToEnet) {
            this.onLoad();
        }
    }

    public void onLoad() {
        if (!this.addedToEnet && !this.getWorldObj().f_46443_ && Info.isIc2Available()) {
            EnergyNet.instance.addLocatableTile(this);
            this.addedToEnet = true;
        }
    }

    public void invalidate() {
        this.onChunkUnload();
    }

    public void onChunkUnload() {
        if (this.addedToEnet && !this.getWorldObj().f_46443_ && Info.isIc2Available()) {
            EnergyNet.instance.removeTile(this);
            this.addedToEnet = false;
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        CompoundTag compoundTag2 = compoundTag.m_128469_(this.getNbtTagName());
        this.setEnergyStored(compoundTag2.m_128459_("energy"));
    }

    public CompoundTag writeToNBT(CompoundTag compoundTag) {
        CompoundTag compoundTag2 = new CompoundTag();
        compoundTag2.m_128347_("energy", this.getEnergyStored());
        compoundTag.m_128365_(this.getNbtTagName(), (Tag)compoundTag2);
        return compoundTag;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public void setCapacity(double d) {
        this.capacity = d;
    }

    public double getEnergyStored() {
        return this.energyStored;
    }

    public void setEnergyStored(double d) {
        this.energyStored = d;
    }

    public double getFreeCapacity() {
        return this.getCapacity() - this.getEnergyStored();
    }

    public double addEnergy(double d) {
        if (this.getWorldObj().f_46443_) {
            return 0.0;
        }
        double d2 = this.getEnergyStored();
        double d3 = this.getCapacity();
        if (d > d3 - d2) {
            d = d3 - d2;
        }
        this.setEnergyStored(d2 + d);
        return d;
    }

    public boolean canUseEnergy(double d) {
        return this.getEnergyStored() >= d;
    }

    public boolean useEnergy(double d) {
        if (!this.canUseEnergy(d) || this.getWorldObj().f_46443_) {
            return false;
        }
        this.setEnergyStored(this.getEnergyStored() - d);
        return true;
    }

    public boolean charge(ItemStack itemStack) {
        if (itemStack == null || !Info.isIc2Available() || this.getWorldObj().f_46443_) {
            return false;
        }
        double d = this.getEnergyStored();
        double d2 = ElectricItem.manager.charge(itemStack, d, Math.max(this.getSinkTier(), this.getSourceTier()), false, false);
        this.setEnergyStored(d - d2);
        return d2 > 0.0;
    }

    public boolean discharge(ItemStack itemStack, double d) {
        if (itemStack == null || !Info.isIc2Available() || this.getWorldObj().f_46443_) {
            return false;
        }
        double d2 = this.getEnergyStored();
        double d3 = this.getCapacity() - d2;
        if (d3 <= 0.0) {
            return false;
        }
        if (d > 0.0 && d < d3) {
            d3 = d;
        }
        d3 = ElectricItem.manager.discharge(itemStack, d3, Math.max(this.getSinkTier(), this.getSourceTier()), d > 0.0, true, false);
        this.setEnergyStored(d2 + d3);
        return d3 > 0.0;
    }

    @Override
    public Level getWorldObj() {
        if (this.world == null) {
            this.initLocation();
        }
        return this.world;
    }

    @Override
    public BlockPos getPosition() {
        if (this.pos == null) {
            this.initLocation();
        }
        return this.pos;
    }

    private void initLocation() {
        if (this.locationProvider instanceof ILocatable) {
            ILocatable iLocatable = (ILocatable)this.locationProvider;
            this.world = iLocatable.getWorldObj();
            this.pos = iLocatable.getPosition();
        } else if (this.locationProvider instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)this.locationProvider;
            this.world = blockEntity.m_58904_();
            this.pos = blockEntity.m_58899_();
        } else {
            throw new IllegalStateException("no/incompatible location provider");
        }
    }

    protected abstract String getNbtTagName();

    protected int getSinkTier() {
        return 0;
    }

    protected int getSourceTier() {
        return 0;
    }
}

