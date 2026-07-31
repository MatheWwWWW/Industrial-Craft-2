/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagLong
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import com.google.common.collect.ImmutableSet;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ChunkLoaderLogic;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerChunkLoader;
import ic2.core.block.machine.gui.GuiChunkLoader;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityChunkloader
extends TileEntityInventory
implements INetworkClientTileEntityEventListener,
IHasGui,
IUpgradableBlock {
    private ForgeChunkManager.Ticket ticket;
    private final Set<ChunkPos> loadedChunks = new HashSet<ChunkPos>();
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotDischarge dischargeSlot;
    public final Energy energy;
    private static final int defaultTier = 1;
    private static final int defaultEnergyStorage = 2500;
    private final double euPerChunk = ConfigUtil.getFloat(MainConfig.get(), "balance/euPerChunk");

    public TileEntityChunkloader() {
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.IO, 1, true, InvSlot.InvSide.ANY);
        this.energy = this.addComponent(Energy.asBasicSink(this, 2500.0, 1).addManagedSlot(this.dischargeSlot));
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        boolean active = this.energy.useEnergy((double)this.getLoadedChunks().size() * this.euPerChunk);
        if (active != this.getActive()) {
            this.setActive(active);
        }
        this.upgradeSlot.tick();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        NBTTagList list = nbt.func_150295_c("loadedChunks", 4);
        this.loadedChunks.clear();
        for (int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagLong currentNBT = (NBTTagLong)list.func_179238_g(i);
            long value = currentNBT.func_150291_c();
            this.loadedChunks.add(ChunkLoaderLogic.deserialize(value));
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        NBTTagList list = new NBTTagList();
        nbt.func_74782_a("loadedChunks", (NBTBase)list);
        for (ChunkPos chunk : this.loadedChunks) {
            list.func_74742_a((NBTBase)new NBTTagLong(ChunkLoaderLogic.serialize(chunk)));
        }
        return nbt;
    }

    @Override
    public void setActive(boolean active) {
        World world = this.func_145831_w();
        if (!world.field_72995_K && this.getActive() != active) {
            if (active) {
                if (this.ticket != null) {
                    throw new IllegalStateException("Cannot activate ChunkLoader: " + this.field_174879_c + " " + this.ticket);
                }
                this.ticket = ChunkLoaderLogic.getInstance().createTicket(world, this.field_174879_c);
                for (ChunkPos coords : this.loadedChunks) {
                    ChunkLoaderLogic.getInstance().addChunkToTicket(this.ticket, coords);
                }
            } else {
                if (this.ticket == null) {
                    throw new IllegalStateException("Cannot deactivate ChunkLoader: " + this.field_174879_c + " " + this.ticket);
                }
                ChunkLoaderLogic.getInstance().removeTicket(this.ticket);
                this.ticket = null;
            }
        }
        super.setActive(active);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        World world = this.func_145831_w();
        if (!world.field_72995_K) {
            this.ticket = ChunkLoaderLogic.getInstance().getTicket(world, this.field_174879_c, false);
            if (this.ticket != null) {
                this.loadedChunks.clear();
                this.loadedChunks.addAll((Collection<ChunkPos>)this.ticket.getChunkList());
            }
            super.setActive(this.ticket != null);
            this.setOverclockRates();
        }
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
        this.ticket = null;
    }

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        this.loadedChunks.add(ChunkLoaderLogic.getChunkCoords(this.field_174879_c));
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.func_145831_w().field_72995_K) {
            return true;
        }
        return IC2.platform.launchGui(player, this);
    }

    public ContainerBase<TileEntityChunkloader> getGuiContainer(EntityPlayer player) {
        return new ContainerChunkLoader(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiChunkLoader(new ContainerChunkLoader(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public void addChunkToLoaded(ChunkPos chunk) {
        if (this.func_145831_w().field_72995_K) {
            new RuntimeException("Something tried to change the ChunkLoaderState on the client.").printStackTrace();
            return;
        }
        if (!this.isChunkInRange(chunk)) {
            IC2.log.warn(LogCategory.Block, "Trying to add a Chunk to loaded, however the chunk is too far away. Aborting.");
            return;
        }
        if (this.getLoadedChunks().size() < ChunkLoaderLogic.getInstance().getMaxChunksPerTicket()) {
            if (this.ticket != null) {
                ChunkLoaderLogic.getInstance().addChunkToTicket(this.ticket, chunk);
            }
            this.loadedChunks.add(chunk);
            this.func_70296_d();
        }
    }

    public void removeChunkFromLoaded(ChunkPos chunk) {
        if (this.func_145831_w().field_72995_K) {
            new RuntimeException("Something tried to change the ChunkLoaderState on the client.").printStackTrace();
            return;
        }
        if (ChunkLoaderLogic.getChunkCoords(this.field_174879_c).equals((Object)chunk)) {
            return;
        }
        if (this.ticket != null) {
            ChunkLoaderLogic.getInstance().removeChunkFromTicket(this.ticket, chunk);
        }
        this.loadedChunks.remove(chunk);
        this.func_70296_d();
    }

    public ImmutableSet<ChunkPos> getLoadedChunks() {
        return ImmutableSet.copyOf(this.loadedChunks);
    }

    public boolean isChunkInRange(ChunkPos chunk) {
        ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(this.field_174879_c);
        return Math.abs(chunk.field_77276_a - mainChunk.field_77276_a) <= 4 && Math.abs(chunk.field_77275_b - mainChunk.field_77275_b) <= 4;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        int x = (event & 0xF) - 8;
        int z = (event >> 4 & 0xF) - 8;
        ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(this.field_174879_c);
        ChunkPos chunk = new ChunkPos(mainChunk.field_77276_a + x, mainChunk.field_77275_b + z);
        if (this.isChunkInRange(chunk)) {
            if (this.getLoadedChunks().contains((Object)chunk)) {
                this.removeChunkFromLoaded(chunk);
            } else {
                this.addChunkToLoaded(chunk);
            }
        } else {
            return;
        }
    }

    @Override
    protected void onBlockBreak() {
        super.onBlockBreak();
        if (this.ticket != null) {
            ChunkLoaderLogic.getInstance().removeTicket(this.ticket);
            this.ticket = null;
        }
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.Transformer);
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        int tier = this.upgradeSlot.getTier(1);
        this.energy.setSinkTier(tier);
        this.dischargeSlot.setTier(tier);
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(2500, 0, 0));
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (IC2.platform.isSimulating()) {
            this.setOverclockRates();
        }
    }
}

