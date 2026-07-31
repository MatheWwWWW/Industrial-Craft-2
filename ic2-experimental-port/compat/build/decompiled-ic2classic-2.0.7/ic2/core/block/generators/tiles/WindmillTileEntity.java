/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.items.IWindmillBlade;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IAirSpeed;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.WindComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.generators.containers.WindmillContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.wind.WindManager;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.geometry.Box;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WindmillTileEntity
extends BaseInventoryTileEntity
implements IEnergySource,
IEUStorage,
ITickListener,
ITileGui,
IWrenchableTile,
IEUProducer,
IAirSpeed,
IAreaOfEffect {
    public static final AABBUtil.IBlockFilter FILTER = new AABBUtil.IBlockFilter(){

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60795_() || state.m_60734_() == IC2Blocks.WINDMILL;
        }
    };
    boolean addedToEnet = false;
    SubProduction production = new SubProduction();
    public int energy = 0;
    @NetworkInfo
    public float speed = 0.0f;
    @NetworkInfo
    public int lastRadius = -1;
    @NetworkInfo
    ResourceLocation texture;
    public float freeBlocks = 0.0f;
    public float airSpeed = 0.0f;
    public float rotation = 0.0f;
    int visualId = -1;

    public WindmillTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
        this.addNetworkFields("speed", "lastRadius", "texture");
        this.addComparator(new WindComparator("air_speed", ComparatorNames.AIR_SPEED, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 0);
        handler.registerInputFilter(new ClassFilter(IWindmillBlade.class), 0);
        handler.registerNamedSlot(SlotType.INPUT, 0);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_WINDMILL;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new WindmillContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "energy", this.energy, 0);
        NBTUtils.putFloat(compound, "speed", this.speed, 0.0f);
        NBTUtils.putInt(compound, "radius", this.lastRadius, 0);
        NBTUtils.putFloat(compound, "freeBlocks", this.freeBlocks, 1.0f);
        NBTUtils.putFloat(compound, "airSpeed", this.airSpeed, 0.0f);
        if (this.texture != null) {
            compound.m_128359_("texture", this.texture.toString());
        }
        this.production.save(compound);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.speed = compound.m_128457_("speed");
        this.lastRadius = compound.m_128451_("radius");
        this.freeBlocks = NBTUtils.getFloat(compound, "freeBlocks", 1.0f);
        this.airSpeed = compound.m_128457_("airSpeed");
        this.texture = compound.m_128441_("texture") ? new ResourceLocation(compound.m_128461_("texture")) : null;
        this.production.load(compound);
    }

    @Override
    public float getEUProduction() {
        return this.production.getProduction(256000.0f) * (float)IC2.CONFIG.windMillOutput.get();
    }

    @Override
    public float getCurrentSpeed() {
        return this.speed;
    }

    @Override
    public float getMaxSpeed() {
        return 1.0f;
    }

    @Override
    public AABB getAreaOfEffect() {
        return this.getAirBounds(this.lastRadius).toAxis();
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2144744676;
    }

    @Override
    public void setVisualizationId(int id) {
        this.visualId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.visualId;
    }

    @Override
    public void onTick() {
        IWindmillBlade blade = this.getBlade();
        if (blade == null) {
            if (this.lastRadius != -1) {
                this.setSpeed(0.0f);
                this.freeBlocks = 0.0f;
                this.airSpeed = 0.0f;
                this.lastRadius = -1;
                this.texture = null;
                this.updateTileFields("lastRadius", "texture");
            }
            return;
        }
        int newRadius = blade.getRadius((ItemStack)this.inventory.get(0));
        if (newRadius != this.lastRadius || this.clock(1024)) {
            if (this.lastRadius != newRadius) {
                this.updateTileField("lastRadius");
            }
            this.lastRadius = newRadius;
            Box box = this.getAirBounds(newRadius);
            AABBUtil.createOffthreadTask(this.m_58904_(), this.m_58899_(), box, FILTER, 32, DirectionList.ALL, Integer.MAX_VALUE, T -> {
                this.freeBlocks = Mth.m_14036_((float)((float)T.size() / ((float)this.getAirBounds(newRadius).size() - 150.0f)), (float)0.0f, (float)1.0f);
                this.freeBlocks /= (float)((List)T.getMappedPositions().getOrDefault((Object)IC2Blocks.WINDMILL, Collections.emptyList())).size();
            });
            this.airSpeed = this.getAirSpeed(box);
        } else if (this.clock(128)) {
            this.airSpeed = this.getAirSpeed(this.getAirBounds(newRadius));
        }
        if (this.freeBlocks <= 0.0f) {
            this.changeSpeed(-0.001f);
        } else {
            float realAir = this.airSpeed * this.freeBlocks;
            if (this.f_58857_.m_46470_()) {
                realAir *= 1.5f;
            }
            if (this.f_58857_.m_46471_()) {
                realAir *= 1.5f;
            }
            this.changeSpeed(Mth.m_14036_((float)(((realAir *= blade.getEffectiveness((ItemStack)this.inventory.get(0))) - this.speed) * 0.01f), (float)-0.01f, (float)0.01f));
            if (this.clock(64) && realAir > 0.0f && ((ItemStack)this.inventory.get(0)).m_220157_(1, this.f_58857_.f_46441_, null)) {
                ((ItemStack)this.inventory.get(0)).m_41774_(1);
            }
        }
        if (this.energy < 4000 && this.production.update(256000)) {
            this.energy += this.production.consume(256000, true) * IC2.CONFIG.windMillOutput.get();
            if (this.energy > 4000) {
                this.energy = 4000;
            }
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating()) {
            IWindmillBlade blade = this.getBlade();
            this.texture = blade == null ? null : blade.getTexture(stack);
            this.updateTileField("texture");
        }
    }

    protected void setSpeed(float newSpeed) {
        if (Float.compare(newSpeed, this.speed) == 0) {
            return;
        }
        this.speed = newSpeed;
        this.updateTileField("speed");
        this.production.setProduction((int)(this.speed * 256000.0f));
    }

    protected void changeSpeed(float change) {
        if (change == 0.0f) {
            return;
        }
        float oldSpeed = this.speed;
        this.speed = Math.max(this.speed + change, 0.0f);
        if (Float.compare(oldSpeed, this.speed) == 0) {
            return;
        }
        this.updateTileField("speed");
        this.production.setProduction((int)(this.speed * 256000.0f));
    }

    protected float getAirSpeed(Box box) {
        return (float)Math.abs(WindManager.INSTANCE.getAirSpeed(this.f_58857_, box.toAxis(), this.getFacing().m_122435_(), 90.0f)) / 100.0f;
    }

    protected Box getAirBounds(int radius) {
        return Box.fromPos(this.m_58899_()).expandSide(this.getFacing().m_122434_(), radius / 2).expand(this.getFacing(), 20).expand(this.getFacing().m_122424_(), 5);
    }

    public IWindmillBlade getBlade() {
        ItemStack stack = (ItemStack)this.inventory.get(0);
        return stack.m_41720_() instanceof IWindmillBlade ? (IWindmillBlade)stack.m_41720_() : null;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir.m_122434_().m_122479_() && dir != this.getFacing();
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85 - 0.05 * (double)this.getTier();
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return this.getFacing() != side;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return 4000;
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public int getSourceTier() {
        return 2;
    }

    @Override
    public int getMaxEnergyOutput() {
        return IC2.CONFIG.windMillOutput.get();
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.energy, 512);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy -= consumed;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public int getRadius() {
        return this.lastRadius;
    }

    public AABB getRenderBoundingBox() {
        return new AABB(this.m_58899_()).m_82400_((double)(this.lastRadius * 8 + 14) / 16.0);
    }
}

