/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
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
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityClassicMassFabricator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.item.type.MiscResourceType;
import ic2.core.network.GuiSynced;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TeBlock.Delegated(current=TileEntityMassFabricator.class, old=TileEntityClassicMassFabricator.class)
public class TileEntityMassFabricator
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IExplosionPowerOverride {
    @GuiSynced
    public int scrap = 0;
    @GuiSynced
    public int consumedScrap = 0;
    protected double maxScrapConsumption = EnergyNet.instance.getPowerFromTier(DEFAULT_TIER);
    public static final int DEFAULT_TIER = ConfigUtil.getInt(MainConfig.get(), "balance/massFabricatorTier");
    private static final int REQUIRED_SCRAP = Util.roundToNegInf(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor"));
    private static final int SCRAP_FACTOR = 10;
    private AudioSource audioSource;
    private AudioSource audioSourceScrap;
    private byte scrapCounter = 0;
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
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    protected final Redstone redstone = this.addComponent(new Redstone(this));

    public static Class<? extends TileEntityElectricMachine> delegate() {
        return IC2.version.isClassic() ? TileEntityClassicMassFabricator.class : TileEntityMassFabricator.class;
    }

    public TileEntityMassFabricator() {
        super(Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), DEFAULT_TIER, false);
        this.redstone.subscribe(newLevel -> this.energy.setEnabled(newLevel == 0));
        this.comparator.setUpdate(() -> {
            int count = TileEntityMassFabricator.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
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
        this.consumedScrap = nbt.func_74762_e("consumedScrap");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("scrap", this.scrap);
        nbt.func_74768_a("consumedScrap", this.consumedScrap);
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            this.updateUpgrades();
        }
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (!this.func_145831_w().field_72995_K) {
            this.updateUpgrades();
        }
    }

    public void updateUpgrades() {
        this.upgradeSlot.onChanged();
        int tier = this.upgradeSlot.getTier(DEFAULT_TIER);
        this.energy.setSinkTier(tier);
        this.dischargeSlot.setTier(tier);
        this.maxScrapConsumption = EnergyNet.instance.getPowerFromTier(tier);
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
        boolean needsInvUpdate = this.upgradeSlot.tickNoMark();
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.setActive(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe;
            if (this.scrap < 100000 && (recipe = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(recipe);
                this.scrap += recipe.getOutput() * 10;
            }
            assert (this.scrap >= 0);
            double scrapConversion = Math.min(Math.min((double)this.scrap, this.energy.getEnergy() - (double)this.consumedScrap), this.maxScrapConsumption);
            assert (scrapConversion >= 0.0);
            boolean newActivity = false;
            if (scrapConversion > 0.0) {
                this.consumedScrap = (int)((double)this.consumedScrap + scrapConversion);
                this.scrap = (int)((double)this.scrap - scrapConversion);
                newActivity = true;
                if (this.energy.getEnergy() >= this.energy.getCapacity() && this.consumedScrap >= REQUIRED_SCRAP) {
                    if (this.outputSlot.canAdd(ItemName.misc_resource.getItemStack(MiscResourceType.matter))) {
                        this.outputSlot.add(ItemName.misc_resource.getItemStack(MiscResourceType.matter));
                        this.energy.useEnergy(this.energy.getCapacity());
                        this.consumedScrap = 0;
                        needsInvUpdate = true;
                    } else {
                        newActivity = false;
                    }
                }
            }
            this.setActive(newActivity);
        }
        if (needsInvUpdate) {
            this.func_70296_d();
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive() && (this.scrapCounter = (byte)(this.scrapCounter + 1)) > 40) {
            this.scrapCounter = 0;
            if (this.audioSourceScrap == null) {
                this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", false, false, IC2.audioManager.getDefaultVolume());
            } else {
                this.audioSourceScrap.stop();
            }
            if (this.audioSourceScrap != null) {
                this.audioSourceScrap.play();
            }
        }
    }

    @Override
    public void onNetworkUpdate(String field) {
        if ("active".equals(field)) {
            if (this.getActive()) {
                if (this.audioSource == null) {
                    this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                }
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else {
                this.scrapCounter = 0;
                if (this.audioSource != null) {
                    this.audioSource.stop();
                }
                if (this.audioSourceScrap != null) {
                    this.audioSourceScrap.stop();
                }
            }
        }
        super.onNetworkUpdate(field);
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    public int getScrap() {
        return this.scrap / 10;
    }

    public int getScrapProgress() {
        return (int)Math.min(100.0f * ((float)this.consumedScrap / (float)REQUIRED_SCRAP), 100.0f);
    }

    public int getEnergyProgress() {
        return (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
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
    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("You probably want the " + Localization.translate(this.getBlockType().func_149739_a() + '.' + TeBlock.matter_generator.getName()));
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
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
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

