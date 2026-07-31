/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.MainConfig;
import ic2.core.item.type.MiscResourceType;
import ic2.core.network.GuiSynced;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TeBlock.Delegated(current=TileEntityMassFabricator.class, old=TileEntityClassicMassFabricator.class)
public class TileEntityClassicMassFabricator
extends TileEntityElectricMachine
implements IHasGui,
IExplosionPowerOverride {
    private AudioSource audioSource;
    private AudioSource audioSourceScrap;
    @GuiSynced
    public int scrap = 0;
    private double lastEnergy;
    private final int StateIdle = 0;
    private final int StateRunning = 1;
    private final int StateRunningScrap = 2;
    private int state = 0;
    private int prevState = 0;
    public final InvSlotProcessable<IRecipeInput, Integer, ItemStack> amplifierSlot = new InvSlotProcessable<IRecipeInput, Integer, ItemStack>((IInventorySlotHolder)this, "scrap", 1, Recipes.matterAmplifier){

        @Override
        protected ItemStack getInput(ItemStack stack) {
            return stack;
        }

        @Override
        protected void setInput(ItemStack input) {
            this.put(input);
        }
    };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    protected final Redstone redstone = this.addComponent(new Redstone(this));

    public TileEntityClassicMassFabricator() {
        super(Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), TileEntityMassFabricator.DEFAULT_TIER, false);
        this.redstone.subscribe(newLevel -> this.energy.setEnabled(newLevel == 0));
        this.comparator.setUpdate(() -> {
            int count = TileEntityClassicMassFabricator.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
            if (count > 0) {
                return count;
            }
            if (this.scrap > 0) {
                return 1;
            }
            return 0;
        });
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.scrap = nbt.func_74762_e("scrap");
        this.lastEnergy = nbt.func_74769_h("lastEnergy");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("scrap", this.scrap);
        nbt.func_74780_a("lastEnergy", this.lastEnergy);
        return nbt;
    }

    @Override
    protected void onUnloaded() {
        if (this.field_145850_b.field_72995_K && (this.audioSource != null || this.audioSourceScrap != null)) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
            this.audioSourceScrap = null;
        }
        super.onUnloaded();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.setState(0);
            this.setActive(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe;
            if (this.scrap > 0) {
                double bonus = Math.min((double)this.scrap, this.energy.getEnergy() - this.lastEnergy);
                if (bonus > 0.0) {
                    this.energy.forceAddEnergy(5.0 * bonus);
                    this.scrap = (int)((double)this.scrap - bonus);
                }
                this.setState(2);
            } else {
                this.setState(1);
            }
            this.setActive(true);
            if (this.scrap < 10000 && (recipe = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(recipe);
                this.scrap += recipe.getOutput().intValue();
            }
            if (this.energy.getEnergy() >= this.energy.getCapacity()) {
                needsInvUpdate = this.attemptGeneration();
            }
            this.lastEnergy = this.energy.getEnergy();
            if (needsInvUpdate) {
                this.func_70296_d();
            }
        }
    }

    public boolean amplificationIsAvailable() {
        if (this.scrap > 0) {
            return true;
        }
        MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe = this.amplifierSlot.process();
        return recipe != null && recipe.getOutput() > 0;
    }

    public boolean attemptGeneration() {
        if (this.outputSlot.add(ItemName.misc_resource.getItemStack(MiscResourceType.matter)) == 0) {
            this.energy.useEnergy(this.energy.getCapacity());
            return true;
        }
        return false;
    }

    private void setState(int aState) {
        this.state = aState;
        if (this.prevState != this.state) {
            IC2.network.get(true).updateTileEntityField(this, "state");
        }
        this.prevState = this.state;
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("state") && this.prevState != this.state) {
            switch (this.state) {
                case 0: {
                    if (this.audioSource != null) {
                        this.audioSource.stop();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.stop();
                    break;
                }
                case 1: {
                    if (this.audioSource == null) {
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSource != null) {
                        this.audioSource.play();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.stop();
                    break;
                }
                case 2: {
                    if (this.audioSource == null) {
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSourceScrap == null) {
                        this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSource != null) {
                        this.audioSource.play();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.play();
                }
            }
            this.prevState = this.state;
        }
        super.onNetworkUpdate(field);
    }

    private GuiParser.GuiNode getXML() {
        ResourceLocation loc = new ResourceLocation(this.teBlock.getIdentifier().func_110624_b(), "guidef/" + this.teBlock.getName() + "_classic.xml");
        try {
            return GuiParser.parse(loc, this.teBlock.getTeClass());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, this.getXML());
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, this.getXML());
    }

    public String getProgressAsString() {
        int p = (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
        return "" + p + "%";
    }

    @Override
    public boolean getGuiState(String name) {
        if ("scrap".equals(name)) {
            return this.scrap > 0;
        }
        if ("dev".equals(name)) {
            return Util.inDev();
        }
        return super.getGuiState(name);
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public boolean shouldExplode() {
        return true;
    }

    @Override
    public float getExplosionPower(int tier, float defaultPower) {
        return 15.0f;
    }
}

