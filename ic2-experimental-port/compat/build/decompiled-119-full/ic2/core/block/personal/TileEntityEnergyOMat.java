/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotConsumableLinked;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.personal.ContainerEnergyOMatClosed;
import ic2.core.block.personal.ContainerEnergyOMatOpen;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityEnergyOMat
extends TileEntityInventory
implements IPersonalBlock,
IHasGui,
IEnergySink,
IEnergySource,
INetworkClientTileEntityEventListener,
IUpgradableBlock {
    public int euOffer = 1000;
    private GameProfile owner = null;
    private boolean addedToEnergyNet = false;
    public int paidFor;
    public double euBuffer;
    private int euBufferMax = 10000;
    private int tier = 1;
    public final InvSlot demandSlot = new InvSlot(this, "demand", InvSlot.Access.NONE, 1);
    public final InvSlotConsumableLinked inputSlot = new InvSlotConsumableLinked(this, "input", 1, this.demandSlot);
    public final InvSlotCharge chargeSlot = new InvSlotCharge(this, 1);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 1);

    public TileEntityEnergyOMat(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.ENERGY_O_MAT, blockPos, blockState);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        if (compoundTag.m_128441_("ownerGameProfile")) {
            this.owner = NbtUtils.m_129228_((CompoundTag)compoundTag.m_128469_("ownerGameProfile"));
        }
        this.euOffer = compoundTag.m_128451_("euOffer");
        this.paidFor = compoundTag.m_128451_("paidFor");
        try {
            this.euBuffer = compoundTag.m_128459_("euBuffer");
        }
        catch (Exception exception) {
            this.euBuffer = compoundTag.m_128451_("euBuffer");
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.owner != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            NbtUtils.m_129230_((CompoundTag)compoundTag2, (GameProfile)this.owner);
            compoundTag.m_128365_("ownerGameProfile", (Tag)compoundTag2);
        }
        compoundTag.m_128405_("euOffer", this.euOffer);
        compoundTag.m_128405_("paidFor", this.paidFor);
        compoundTag.m_128347_("euBuffer", this.euBuffer);
    }

    @Override
    public boolean wrenchCanRemove(Player player) {
        return this.permitsAccess(player.m_36316_());
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            EnergyNet.instance.addBlockEntityTile(this);
            this.addedToEnergyNet = true;
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isSimulating() && this.addedToEnergyNet) {
            EnergyNet.instance.removeTile(this);
            this.addedToEnergyNet = false;
        }
        super.onUnloaded();
    }

    @Override
    protected void updateEntityServer() {
        double d;
        int n;
        ItemStack itemStack;
        super.updateEntityServer();
        boolean bl = false;
        this.euBufferMax = 10000;
        this.tier = 1;
        this.chargeSlot.setTier(1);
        if (!this.upgradeSlot.isEmpty()) {
            this.euBufferMax = this.upgradeSlot.getEnergyStorage(10000, 0, 0);
            this.tier = 1 + this.upgradeSlot.extraTier;
            this.chargeSlot.setTier(this.tier);
        }
        if ((itemStack = this.inputSlot.consumeLinked(true)) != null && (n = StackUtil.distribute(this, itemStack, true)) == StackUtil.getSize(itemStack)) {
            StackUtil.distribute(this, this.inputSlot.consumeLinked(false), false);
            this.paidFor += this.euOffer;
            bl = true;
        }
        if (this.euBuffer >= 1.0 && (d = this.chargeSlot.charge(this.euBuffer)) > 0.0) {
            this.euBuffer -= d;
            bl = true;
        }
        if (bl) {
            this.m_6596_();
        }
    }

    @Override
    public boolean permitsAccess(GameProfile gameProfile) {
        return TileEntityPersonalChest.checkAccess(this, gameProfile);
    }

    @Override
    public Container getPrivilegedInventory(GameProfile gameProfile) {
        return this;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("owner");
        arrayList.addAll(super.getNetworkedFields());
        return arrayList;
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(GameProfile gameProfile) {
        this.owner = gameProfile;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return false;
    }

    @Override
    protected boolean canSetFacingWrench(Direction direction, Player player) {
        if (player == null || !this.permitsAccess(player.m_36316_())) {
            return false;
        }
        return super.canSetFacingWrench(direction, player);
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return !this.facingMatchesDirection(direction);
    }

    public boolean facingMatchesDirection(Direction direction) {
        return direction == this.getFacing();
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
        return this.facingMatchesDirection(direction);
    }

    @Override
    public double getOfferedEnergy() {
        return this.euBuffer;
    }

    @Override
    public void drawEnergy(double d) {
        this.euBuffer -= d;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.min((double)this.paidFor, (double)this.euBufferMax - this.euBuffer);
    }

    @Override
    public double injectEnergy(Direction direction, double d, double d2) {
        double d3 = Math.min(Math.min(d, (double)this.paidFor), (double)this.euBufferMax - this.euBuffer);
        this.paidFor = (int)((double)this.paidFor - d3);
        this.euBuffer += d3;
        return d - d3;
    }

    @Override
    public int getSourceTier() {
        return this.tier;
    }

    @Override
    public int getSinkTier() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        if (this.permitsAccess(player.m_36316_())) {
            return new ContainerEnergyOMatOpen(n, player.m_150109_(), this);
        }
        return new ContainerEnergyOMatClosed(n, player.m_150109_(), this);
    }

    @Override
    public void writeScreenOpenData(Player player, InteractionHand interactionHand, GrowingBuffer growingBuffer) throws IOException {
        growingBuffer.writeBoolean(this.permitsAccess(player.m_36316_()));
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        if (growingBuffer.readBoolean()) {
            return new ContainerEnergyOMatOpen(n, inventory, this);
        }
        return new ContainerEnergyOMatClosed(n, inventory, this);
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (!this.permitsAccess(player.m_36316_())) {
            return;
        }
        switch (n) {
            case 0: {
                this.attemptSet(-100000);
                break;
            }
            case 1: {
                this.attemptSet(-10000);
                break;
            }
            case 2: {
                this.attemptSet(-1000);
                break;
            }
            case 3: {
                this.attemptSet(-100);
                break;
            }
            case 4: {
                this.attemptSet(100000);
                break;
            }
            case 5: {
                this.attemptSet(10000);
                break;
            }
            case 6: {
                this.attemptSet(1000);
                break;
            }
            case 7: {
                this.attemptSet(100);
            }
        }
    }

    private void attemptSet(int n) {
        this.euOffer += n;
        if (this.euOffer < 100) {
            this.euOffer = 100;
        }
    }

    @Override
    public double getEnergy() {
        return this.euBuffer;
    }

    @Override
    public boolean useEnergy(double d) {
        if (d <= this.euBuffer) {
            d -= this.euBuffer;
            return true;
        }
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.Transformer);
    }
}

