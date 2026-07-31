/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Supplier
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
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
import ic2.core.gui.TextBox;
import ic2.core.gui.TextLabel;
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
import ic2.core.item.upgrade.UpgradeSettings;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotHologramSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class HandHeldValueConfig
extends HandHeldUpgradeOption {
    protected final ComparisonType initialComparisonType;
    protected final String initialNormalBox;
    protected final String initialExtraBox;
    protected final ComparisonSettings initialNormalSetting;
    protected final ComparisonSettings initialExtraSetting;

    public HandHeldValueConfig(HandHeldAdvancedUpgrade handHeldAdvancedUpgrade, String string) {
        super(handHeldAdvancedUpgrade, string);
        UpgradeSettings upgradeSettings = new UpgradeSettings(this.getNBT());
        this.initialComparisonType = upgradeSettings.comparison;
        this.initialNormalBox = upgradeSettings.mainBox;
        this.initialExtraBox = upgradeSettings.extraBox;
        this.initialNormalSetting = upgradeSettings.main;
        this.initialExtraSetting = upgradeSettings.extra;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerValueConfig(n);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerValueConfig(n);
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

        public ContainerValueConfig(int n) {
            super(Ic2ScreenHandlers.ADVANCED_UPGRADE_VALUE_CONFIG, n, HandHeldValueConfig.this);
            this.comparisonType = HandHeldValueConfig.this.initialComparisonType;
            this.normalBox = HandHeldValueConfig.this.initialNormalBox;
            this.extraBox = HandHeldValueConfig.this.initialExtraBox;
            this.normalSetting = HandHeldValueConfig.this.initialNormalSetting;
            this.extraSetting = HandHeldValueConfig.this.initialExtraSetting;
            this.addPlayerInventorySlots(HandHeldValueConfig.this.player.m_150109_(), 166);
            for (int n2 = 0; n2 < 9; n2 = (int)((byte)(n2 + 1))) {
                this.m_38897_(new SlotHologramSlot(HandHeldValueConfig.this.inventory, n2, 8 + 18 * n2, 8, 1, HandHeldValueConfig.this.makeSaveCallback()));
            }
        }

        @Override
        public void m_6877_(Player player) {
            CompoundTag compoundTag = HandHeldValueConfig.this.getNBT();
            compoundTag.m_128379_("active", this.comparisonType.enabled());
            ComparisonType comparisonType = this.comparisonType;
            switch (this.comparisonType) {
                case COMPARISON: {
                    if (this.normalBox.isEmpty()) {
                        comparisonType = ComparisonType.DIRECT;
                    } else {
                        compoundTag.m_128359_("normal", this.normalBox);
                        compoundTag.m_128344_("normalComp", this.normalSetting.getForNBT());
                    }
                }
                case RANGE: {
                    if (this.normalBox.isEmpty()) {
                        if (this.extraBox.isEmpty()) {
                            comparisonType = ComparisonType.DIRECT;
                            break;
                        }
                        comparisonType = ComparisonType.COMPARISON;
                        compoundTag.m_128359_("normal", this.extraBox);
                        compoundTag.m_128344_("normalComp", this.extraSetting.getForNBT());
                        break;
                    }
                    compoundTag.m_128359_("normal", this.normalBox);
                    compoundTag.m_128344_("normalComp", this.normalSetting.getForNBT());
                    if (this.extraBox.isEmpty()) {
                        comparisonType = ComparisonType.COMPARISON;
                        break;
                    }
                    compoundTag.m_128359_("extra", this.extraBox);
                    compoundTag.m_128344_("extraComp", this.extraSetting.getForNBT());
                }
            }
            compoundTag.m_128344_("type", comparisonType.getForNBT());
            super.m_6877_(player);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class GuiValueConfig
    extends GuiDefaultBackground<ContainerValueConfig> {
        public GuiValueConfig(final ContainerValueConfig containerValueConfig, Inventory inventory, Component component) {
            super(containerValueConfig, inventory, component);
            this.addElement(((HandHeldValueConfig)containerValueConfig.base).getBackButton(this, 10, 62));
            this.addElement((GuiElement<?>)((VanillaButton)new VanillaButton(this, 10, 25, 75, 15, new EnumCycleHandler<ComparisonType>(ComparisonType.VALUES, containerValueConfig.comparisonType){

                @Override
                public void onClick(MouseButton mouseButton) {
                    super.onClick(mouseButton);
                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).comparisonType = (ComparisonType)((Object)this.getCurrentValue());
                    IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "comparisonType");
                }
            }).withText((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)this).f_97732_).comparisonType.name);
                }
            })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){
                private final String name;
                {
                    this.name = Localization.translate("ic2.upgrade.advancedGUI." + ((HandHeldValueConfig)containerValueConfig.base).name);
                }

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)this).f_97732_).comparisonType.name + ".desc", this.name);
                }
            }));
            IEnableHandler iEnableHandler = new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).comparisonType == ComparisonType.RANGE;
                }
            };
            IEnableHandler iEnableHandler2 = new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return !((ContainerValueConfig)((GuiValueConfig)this).f_97732_).comparisonType.ignoreFilters();
                }
            };
            this.addElement((GuiElement<?>)((VanillaButton)((VanillaButton)new MoveableButton(this, 75, 43, 60, 43, 17, 15, new EnumCycleHandler<ComparisonSettings>(ComparisonSettings.VALUES, containerValueConfig.normalSetting){

                @Override
                public void onClick(MouseButton mouseButton) {
                    super.onClick(mouseButton);
                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalSetting = (ComparisonSettings)((Object)this.getCurrentValue());
                    IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "normalSetting");
                    switch ((ComparisonSettings)((Object)this.getCurrentValue())) {
                        case LESS: 
                        case LESS_OR_EQUAL: {
                            if (((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.LESS || ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.LESS_OR_EQUAL) break;
                            ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.LESS;
                            IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "extraSetting");
                            break;
                        }
                        case GREATER: 
                        case GREATER_OR_EQUAL: {
                            if (((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.GREATER || ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.GREATER_OR_EQUAL) break;
                            ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.GREATER;
                            IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "extraSetting");
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unexpected other setting: " + this.getCurrentValue());
                        }
                    }
                }
            }).withMoveHandler(iEnableHandler).withEnableHandler(iEnableHandler2)).withText((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    return ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalSetting.symbol;
                }
            })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalSetting.name);
                }
            }));
            this.addElement((GuiElement<?>)((VanillaButton)((VanillaButton)new VanillaButton(this, 105, 43, 17, 15, new IClickHandler(){

                @Override
                public void onClick(MouseButton mouseButton) {
                    if (mouseButton == MouseButton.left || mouseButton == MouseButton.right) {
                        switch (((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalSetting) {
                            case LESS: 
                            case LESS_OR_EQUAL: {
                                if (((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.LESS) {
                                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.LESS_OR_EQUAL;
                                    break;
                                }
                                ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.LESS;
                                break;
                            }
                            case GREATER: 
                            case GREATER_OR_EQUAL: {
                                if (((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting == ComparisonSettings.GREATER) {
                                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.GREATER_OR_EQUAL;
                                    break;
                                }
                                ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting = ComparisonSettings.GREATER;
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Unexpected other setting: " + ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalSetting);
                            }
                        }
                        IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "extraSetting");
                    }
                }
            }).withEnableHandler(iEnableHandler)).withText((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    return ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting.symbol;
                }
            })).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

                public String get() {
                    return Localization.translate(((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraSetting.name);
                }
            }));
            Predicate<String> predicate = new Predicate<String>(){

                public boolean apply(String string) {
                    try {
                        return Integer.parseInt(string) >= 0;
                    }
                    catch (NumberFormatException numberFormatException) {
                        return string.isEmpty();
                    }
                }
            };
            final MoveableTextBox moveableTextBox = new MoveableTextBox(this, 40, 43, 25, 43, 30, 15, ((ContainerValueConfig)this.f_97732_).normalBox);
            this.addElement((GuiElement<?>)moveableTextBox.withMoveHandler(iEnableHandler).withTextWatcher(new TextBox.ITextBoxWatcher(){

                @Override
                public void onChanged(String string, String string2) {
                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).normalBox = string2;
                    IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "normalBox");
                }
            }).withTextValidator(predicate).withEnableHandler(iEnableHandler2));
            this.addElement((GuiElement<?>)new TextBox(this, 125, 43, 30, 15, ((ContainerValueConfig)this.f_97732_).extraBox).withTextWatcher(new TextBox.ITextBoxWatcher(){

                @Override
                public void onChanged(String string, String string2) {
                    ((ContainerValueConfig)((GuiValueConfig)this).f_97732_).extraBox = string2;
                    IC2.network.get(false).sendContainerField((ContainerBase)f_97732_, "extraBox");
                }
            }).withTextValidator(predicate).withEnableHandler(iEnableHandler));
            this.addElement((GuiElement<?>)TextLabel.create(this, 100, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + ((HandHeldValueConfig)containerValueConfig.base).name), 0x404040, false).withEnableHandler(new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return moveableTextBox.isEnabled() && !moveableTextBox.isMoved();
                }
            }));
            this.addElement((GuiElement<?>)TextLabel.create(this, 80, 47, TextProvider.ofTranslated("ic2.upgrade.advancedGUI." + ((HandHeldValueConfig)containerValueConfig.base).name), 0x404040, false).withEnableHandler(new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    return moveableTextBox.isEnabled() && moveableTextBox.isMoved();
                }
            }));
            this.addElement(new SlotGrid(this, 7, 7, 9, 1, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 83, 9, 3, SlotGrid.SlotStyle.Normal));
            this.addElement(new SlotGrid(this, 7, 141, 9, 1, SlotGrid.SlotStyle.Normal));
        }
    }
}

