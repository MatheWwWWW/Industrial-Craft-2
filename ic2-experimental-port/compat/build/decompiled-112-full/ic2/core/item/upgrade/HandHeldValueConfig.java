/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Supplier
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.upgrade;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.gui.EnumCycleHandler;
import ic2.core.gui.GuiDefaultBackground;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.Text;
import ic2.core.gui.TextBox;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.upgrade.ComparisonSettings;
import ic2.core.item.upgrade.ComparisonType;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.HandHeldUpgradeOption;
import ic2.core.item.upgrade.MoveableButton;
import ic2.core.item.upgrade.MoveableTextBox;
import ic2.core.item.upgrade.Settings;
import ic2.core.slot.SlotHologramSlot;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandHeldValueConfig
extends HandHeldUpgradeOption {
    protected final ComparisonType initialComparisonType;
    protected final String initialNormalBox;
    protected final String initialExtraBox;
    protected final ComparisonSettings initialNormalSetting;
    protected final ComparisonSettings initialExtraSetting;

    public HandHeldValueConfig(HandHeldAdvancedUpgrade upgradeGUI, String type) {
        super(upgradeGUI, type);
        Settings settings = new Settings(this.getNBT());
        this.initialComparisonType = settings.comparison;
        this.initialNormalBox = settings.mainBox;
        this.initialExtraBox = settings.extraBox;
        this.initialNormalSetting = settings.main;
        this.initialExtraSetting = settings.extra;
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerValueConfig();
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiValueConfig();
    }

    @SideOnly(value=Side.CLIENT)
    public class GuiValueConfig
    extends GuiDefaultBackground<ContainerValueConfig> {
        public GuiValueConfig() {
            super(new ContainerValueConfig());
            this.addElement(HandHeldValueConfig.this.getBackButton(this, 10, 62));
            this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 10, 25, 75, 15, new EnumCycleHandler<ComparisonType>(ComparisonType.VALUES, ((ContainerValueConfig)this.container).comparisonType){

                @Override
                public void onClick(MouseButton button) {
                    super.onClick(button);
                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).comparisonType = (ComparisonType)((Object)this.getCurrentValue());
                    IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "comparisonType");
                }
            }).withText(new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).comparisonType.name);
                }
            })).withTooltip(new Supplier<String>(){
                private final String name;
                {
                    this.name = Localization.translate("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_());
                }

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).comparisonType.name + ".desc", this.name);
                }
            }));
            IEnableHandler rangeEnabled = new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).comparisonType == ComparisonType.RANGE;
                }
            };
            IEnableHandler filtersEnabled = new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return !((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).comparisonType.ignoreFilters();
                }
            };
            this.addElement((GuiElement<?>)((VanillaButton)((VanillaButton)new MoveableButton(this, 75, 43, 60, 43, 17, 15, new EnumCycleHandler<ComparisonSettings>(ComparisonSettings.VALUES, ((ContainerValueConfig)this.container).normalSetting){

                @Override
                public void onClick(MouseButton button) {
                    super.onClick(button);
                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalSetting = (ComparisonSettings)((Object)this.getCurrentValue());
                    IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "normalSetting");
                    switch ((ComparisonSettings)((Object)this.getCurrentValue())) {
                        case LESS: 
                        case LESS_OR_EQUAL: {
                            if (((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.LESS || ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.LESS_OR_EQUAL) break;
                            ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.LESS;
                            IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "extraSetting");
                            break;
                        }
                        case GREATER: 
                        case GREATER_OR_EQUAL: {
                            if (((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.GREATER || ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.GREATER_OR_EQUAL) break;
                            ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.GREATER;
                            IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "extraSetting");
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unexpected other setting: " + this.getCurrentValue());
                        }
                    }
                }
            }).withMoveHandler(rangeEnabled).withEnableHandler(filtersEnabled)).withText(new Supplier<String>(){

                public String get() {
                    return ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalSetting.symbol;
                }
            })).withTooltip(new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalSetting.name);
                }
            }));
            this.addElement((GuiElement<?>)((VanillaButton)((VanillaButton)new VanillaButton(this, 105, 43, 17, 15, new IClickHandler(){

                @Override
                public void onClick(MouseButton button) {
                    if (button == MouseButton.left || button == MouseButton.right) {
                        switch (((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalSetting) {
                            case LESS: 
                            case LESS_OR_EQUAL: {
                                if (((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.LESS) {
                                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.LESS_OR_EQUAL;
                                    break;
                                }
                                ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.LESS;
                                break;
                            }
                            case GREATER: 
                            case GREATER_OR_EQUAL: {
                                if (((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting == ComparisonSettings.GREATER) {
                                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.GREATER_OR_EQUAL;
                                    break;
                                }
                                ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting = ComparisonSettings.GREATER;
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Unexpected other setting: " + (Object)((Object)((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalSetting));
                            }
                        }
                        IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "extraSetting");
                    }
                }
            }).withEnableHandler(rangeEnabled)).withText(new Supplier<String>(){

                public String get() {
                    return ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting.symbol;
                }
            })).withTooltip(new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraSetting.name);
                }
            }));
            Predicate<String> numberOnly = new Predicate<String>(){

                public boolean apply(String input) {
                    try {
                        return Integer.parseInt(input) >= 0;
                    }
                    catch (NumberFormatException e) {
                        return input.isEmpty();
                    }
                }
            };
            final MoveableTextBox textBox = new MoveableTextBox(this, 40, 43, 25, 43, 30, 15, ((ContainerValueConfig)this.container).normalBox);
            this.addElement((GuiElement<?>)textBox.withMoveHandler(rangeEnabled).withTextWatcher(new TextBox.ITextBoxWatcher(){

                @Override
                public void onChanged(String oldValue, String newValue) {
                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).normalBox = newValue;
                    IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "normalBox");
                }
            }).withTextValidator(numberOnly).withEnableHandler(filtersEnabled));
            this.addElement((GuiElement<?>)new TextBox(this, 125, 43, 30, 15, ((ContainerValueConfig)this.container).extraBox).withTextWatcher(new TextBox.ITextBoxWatcher(){

                @Override
                public void onChanged(String oldValue, String newValue) {
                    ((ContainerValueConfig)((GuiValueConfig)GuiValueConfig.this).container).extraBox = newValue;
                    IC2.network.get(false).sendContainerField(GuiValueConfig.this.container, "extraBox");
                }
            }).withTextValidator(numberOnly).withEnableHandler(rangeEnabled));
            this.addElement((GuiElement<?>)Text.create(this, 100, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_()), 0x404040, false).withEnableHandler(new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return textBox.isEnabled() && !textBox.isMoved();
                }
            }));
            this.addElement((GuiElement<?>)Text.create(this, 80, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + HandHeldValueConfig.this.func_70005_c_()), 0x404040, false).withEnableHandler(new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return textBox.isEnabled() && textBox.isMoved();
                }
            }));
            this.addElement(new SlotGrid(this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 83, 9, 3, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 141, 9, 1, SlotGrid.SlotStyle.Normal));
        }
    }

    public class ContainerValueConfig
    extends ContainerHandHeldInventory<HandHeldValueConfig> {
        @ClientModifiable
        protected ComparisonType comparisonType;
        @ClientModifiable
        protected String normalBox;
        @ClientModifiable
        protected String extraBox;
        @ClientModifiable
        protected ComparisonSettings normalSetting;
        @ClientModifiable
        protected ComparisonSettings extraSetting;

        public ContainerValueConfig() {
            super(HandHeldValueConfig.this);
            this.comparisonType = HandHeldValueConfig.this.initialComparisonType;
            this.normalBox = HandHeldValueConfig.this.initialNormalBox;
            this.extraBox = HandHeldValueConfig.this.initialExtraBox;
            this.normalSetting = HandHeldValueConfig.this.initialNormalSetting;
            this.extraSetting = HandHeldValueConfig.this.initialExtraSetting;
            this.addPlayerInventorySlots(HandHeldValueConfig.this.player, 166);
            for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
                this.func_75146_a(new SlotHologramSlot(HandHeldValueConfig.this.inventory, slot, 8 + 18 * slot, 8, 1, HandHeldValueConfig.this.makeSaveCallback()));
            }
        }

        @Override
        public void func_75134_a(EntityPlayer player) {
            NBTTagCompound nbt = HandHeldValueConfig.this.getNBT();
            nbt.func_74757_a("active", this.comparisonType.enabled());
            ComparisonType saveType = this.comparisonType;
            switch (this.comparisonType) {
                case COMPARISON: {
                    if (this.normalBox.isEmpty()) {
                        saveType = ComparisonType.DIRECT;
                    } else {
                        nbt.func_74778_a("normal", this.normalBox);
                        nbt.func_74774_a("normalComp", this.normalSetting.getForNBT());
                    }
                }
                case RANGE: {
                    if (this.normalBox.isEmpty()) {
                        if (this.extraBox.isEmpty()) {
                            saveType = ComparisonType.DIRECT;
                            break;
                        }
                        saveType = ComparisonType.COMPARISON;
                        nbt.func_74778_a("normal", this.extraBox);
                        nbt.func_74774_a("normalComp", this.extraSetting.getForNBT());
                        break;
                    }
                    nbt.func_74778_a("normal", this.normalBox);
                    nbt.func_74774_a("normalComp", this.normalSetting.getForNBT());
                    if (this.extraBox.isEmpty()) {
                        saveType = ComparisonType.COMPARISON;
                        break;
                    }
                    nbt.func_74778_a("extra", this.extraBox);
                    nbt.func_74774_a("extraComp", this.extraSetting.getForNBT());
                }
            }
            nbt.func_74774_a("type", saveType.getForNBT());
            super.func_75134_a(player);
        }
    }
}

