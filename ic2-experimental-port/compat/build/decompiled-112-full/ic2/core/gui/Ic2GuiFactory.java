/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.google.common.base.Joiner
 *  com.google.common.base.Predicate
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterators
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.client.IModGuiFactory
 *  net.minecraftforge.fml.client.IModGuiFactory$RuntimeOptionCategoryElement
 *  net.minecraftforge.fml.client.config.ConfigGuiType
 *  net.minecraftforge.fml.client.config.DummyConfigElement
 *  net.minecraftforge.fml.client.config.DummyConfigElement$DummyCategoryElement
 *  net.minecraftforge.fml.client.config.DummyConfigElement$DummyListElement
 *  net.minecraftforge.fml.client.config.GuiConfig
 *  net.minecraftforge.fml.client.config.GuiConfigEntries
 *  net.minecraftforge.fml.client.config.GuiConfigEntries$CategoryEntry
 *  net.minecraftforge.fml.client.config.GuiEditArray
 *  net.minecraftforge.fml.client.config.GuiEditArrayEntries
 *  net.minecraftforge.fml.client.config.GuiEditArrayEntries$StringEntry
 *  net.minecraftforge.fml.client.config.IConfigElement
 */
package ic2.core.gui;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import ic2.core.init.MainConfig;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.StackUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class Ic2GuiFactory
implements IModGuiFactory {
    public void initialize(Minecraft mc) {
    }

    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Collections.emptySet();
    }

    public boolean hasConfigGui() {
        return true;
    }

    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new IC2ConfigGuiScreen(parentScreen);
    }

    public static class IC2ConfigGuiScreen
    extends GuiConfig {
        static final int LANG_KEY_LENGTH = "ic2.config.".length();
        static final Joiner COMMA_JOINER = Joiner.on((String)", ");
        static final Pattern ITEM_PATTERN = Pattern.compile("^[A-Za-z0-9_]+:[A-Za-z0-9_]+(#[A-Za-z0-9_]+|(@(\\d+|\\*)))?$");
        private static final Pattern IS_BOOLEAN = Pattern.compile("true|false", 2);
        private static final Pattern IS_INT = Pattern.compile("\\d");
        private static final Pattern IS_DOUBLE = Pattern.compile("\\d\\.\\d");

        public IC2ConfigGuiScreen(GuiScreen parent) {
            super(parent, IC2ConfigGuiScreen.sinkCategoryLevel(MainConfig.get(), "."), "ic2", false, false, "IC2 Configuration");
        }

        private static List<IConfigElement> sinkCategoryLevel(Config config, String parentName) {
            ArrayList<IConfigElement> list = new ArrayList<IConfigElement>(config.getNumberOfSections() + config.getNumberOfConfigs());
            if (config.hasChildSection()) {
                Iterator<Config> configCategories = config.sectionIterator();
                while (configCategories.hasNext()) {
                    Config category = configCategories.next();
                    if ("predefined".equals(category.name) && ".balance.uu-values.".equals(parentName)) {
                        list.add((IConfigElement)new UUListElement());
                        continue;
                    }
                    list.add((IConfigElement)new DummyConfigElement.DummyCategoryElement(category.name, "ic2.config.sub." + category.name, IC2ConfigGuiScreen.sinkCategoryLevel(category, parentName + category.name + '.')));
                }
                if (!config.isEmptySection()) {
                    IC2ConfigGuiScreen.getConfigs(list, config.valueIterator(), parentName);
                }
            } else {
                IC2ConfigGuiScreen.getConfigs(list, config.valueIterator(), parentName);
            }
            return list;
        }

        private static void getConfigs(List<IConfigElement> list, Iterator<Config.Value> configs, String parentName) {
            while (configs.hasNext()) {
                Config.Value conf = configs.next();
                Config.Value defaultConf = MainConfig.getDefault(parentName.substring(1).replace('.', '/') + conf.name);
                if (defaultConf == null) continue;
                if (defaultConf.value.isEmpty() || defaultConf.value.contains(",") || defaultConf.comment.contains("comma")) {
                    list.add(new ListElement(conf.name, conf.value, defaultConf.value, "ic2.config" + parentName + conf.name, ITEM_PATTERN).setArrayEntryClass(ItemEntry.class));
                    continue;
                }
                ConfigGuiType type = IS_DOUBLE.matcher(conf.value).matches() ? ConfigGuiType.DOUBLE : (IS_INT.matcher(conf.value).matches() ? ConfigGuiType.INTEGER : (IS_BOOLEAN.matcher(conf.value).matches() ? ConfigGuiType.BOOLEAN : ConfigGuiType.STRING));
                list.add((IConfigElement)new ConfigElement(conf.name, conf.value, defaultConf.value, type, "ic2.config" + parentName + conf.name));
            }
        }

        public void func_146281_b() {
            for (IConfigElement config : this.configElements) {
                this.saveConfig(config);
            }
            MainConfig.save();
            super.func_146281_b();
        }

        private void saveConfig(IConfigElement config) {
            if (config.getChildElements() != null) {
                for (IConfigElement subConfig : config.getChildElements()) {
                    this.saveConfig(subConfig);
                }
            }
            if (config.isProperty()) {
                if (config.isList()) {
                    assert (config instanceof ListElement) : "Unexpected class type: " + config.getClass();
                    ((ListElement)config).save();
                } else {
                    assert (config.getClass() == ConfigElement.class) : "Unexpected class type: " + config.getClass();
                    if (!Objects.equals(config.get(), ((ConfigElement)config).previous)) {
                        MainConfig.get().set(config.getLanguageKey().substring(LANG_KEY_LENGTH).replace('.', '/'), config.get());
                    }
                }
            }
        }

        private static class UUListElement
        extends ListElement {
            private static CharSequence getValues(Iterator<Config.Value> sub) {
                return COMMA_JOINER.join(Iterators.transform(sub, (Function)new Function<Config.Value, String>(){

                    public String apply(Config.Value input) {
                        return input.name + ':' + input.value;
                    }
                }));
            }

            UUListElement() {
                super("predefined", UUListElement.getValues(MainConfig.get().getSub("balance/uu-values/predefined").valueIterator()), UUListElement.getValues(MainConfig.getDefaults("balance/uu-values/predefined")), "ic2.config.sub.predefined", ITEM_PATTERN);
                this.setConfigEntryClass(ArrayCategory.class);
                this.setArrayEntryClass(UUEntry.class);
            }

            @Override
            public void set(Object[] values) {
                super.set(values);
                this.save();
                MainConfig.save();
            }

            @Override
            protected void save() {
                Config config = MainConfig.get().getSub("balance/uu-values/predefined");
                for (Object line : this.getList()) {
                    String part = (String)line;
                    System.out.println("Trying to save part: " + part);
                    int split = part.lastIndexOf(58);
                    config.set(part.substring(0, split), part.substring(split + 1));
                }
            }

            public static class UUEntry
            extends ItemEntry {
                protected boolean hasValidStack;
                protected final GuiTextField uuValue;

                public UUEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
                    super(owningScreen, owningEntryList, configElement, value);
                    int totalSpace = this.textFieldValue.field_146218_h;
                    int textSpace = Math.round((float)(totalSpace * 3) / 4.0f);
                    int numSpace = totalSpace - textSpace;
                    this.textFieldValue.field_146218_h = textSpace - 1;
                    this.uuValue = new GuiTextField(1, owningEntryList.getMC().field_71466_p, this.textFieldValue.field_146209_f + textSpace, 0, numSpace, 16);
                    this.uuValue.func_146203_f(25);
                    this.uuValue.func_146180_a(value.toString());
                    this.uuValue.func_175205_a((Predicate)new Predicate<String>(){

                        public boolean apply(String input) {
                            try {
                                return Double.parseDouble(input) >= 0.0;
                            }
                            catch (NumberFormatException e) {
                                return input.isEmpty();
                            }
                        }
                    });
                    String val = value.toString();
                    int split = val.lastIndexOf(58);
                    if (split > -1) {
                        this.textFieldValue.func_146180_a(val.substring(0, split));
                        this.uuValue.func_146180_a(val.substring(split + 1));
                    } else {
                        assert (this.textFieldValue.func_146179_b().isEmpty()) : "Expected empty textFieldValue but found: " + this.textFieldValue.func_146179_b();
                        assert (this.uuValue.func_146179_b().isEmpty()) : "Expected empty uuValue but found: " + this.uuValue.func_146179_b();
                    }
                    assert (configElement.getValidationPattern() != null);
                    this.updateState();
                }

                protected void updateState() {
                    if (this.configElement.getValidationPattern().matcher(this.getStack()).matches()) {
                        this.updateStack();
                        this.hasValidStack = this.isValidValue;
                    } else {
                        this.hasValidStack = false;
                    }
                    this.isValidValue &= !this.uuValue.func_146179_b().trim().isEmpty();
                }

                @Override
                public void func_192634_a(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partial) {
                    boolean previous = this.isValidValue;
                    this.isValidValue = this.hasValidStack;
                    super.func_192634_a(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
                    this.isValidValue = previous;
                    this.uuValue.func_146189_e(slotIndex != this.owningEntryList.listEntries.size() - 1);
                    this.uuValue.field_146210_g = y + 1;
                    this.uuValue.func_146194_f();
                }

                @Override
                public void keyTyped(char eventChar, int eventKey) {
                    boolean enabled = this.isEnabled();
                    if (enabled || eventKey == 203 || eventKey == 205 || eventKey == 199 || eventKey == 207) {
                        this.textFieldValue.func_146201_a(enabled ? eventChar : (char)'\u0000', eventKey);
                        this.uuValue.func_146201_a(enabled ? eventChar : (char)'\u0000', eventKey);
                        if (enabled) {
                            this.updateState();
                        }
                    }
                }

                public void updateCursorCounter() {
                    super.updateCursorCounter();
                    this.uuValue.func_146178_a();
                }

                public void mouseClicked(int x, int y, int mouseEvent) {
                    super.mouseClicked(x, y, mouseEvent);
                    this.uuValue.func_146192_a(x, y, mouseEvent);
                }

                @Override
                public String getValue() {
                    return this.getStack() + ':' + this.uuValue.func_146179_b().trim();
                }

                @Override
                protected String getStack() {
                    return super.getValue();
                }
            }

            public static class ArrayCategory
            extends GuiConfigEntries.CategoryEntry {
                private int index;
                protected Object[] currentValues;
                protected final Object[] beforeValues;

                public ArrayCategory(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
                    super(owningScreen, owningEntryList, configElement);
                    this.beforeValues = configElement.getList();
                    this.currentValues = configElement.getList();
                    this.childScreen = this.buildChildScreen();
                }

                public boolean func_148278_a(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
                    this.index = index;
                    return super.func_148278_a(index, x, y, mouseEvent, relativeX, relativeY);
                }

                protected GuiScreen buildChildScreen() {
                    return new GuiEditArray((GuiScreen)this.owningScreen, this.configElement, this.index, this.currentValues, this.enabled());
                }

                public boolean isDefault() {
                    return Arrays.deepEquals(this.configElement.getDefaults(), this.currentValues);
                }

                public void setToDefault() {
                    this.currentValues = this.configElement.getDefaults();
                }

                public boolean isChanged() {
                    return !Arrays.deepEquals(this.beforeValues, this.currentValues);
                }

                public void undoChanges() {
                    this.currentValues = this.beforeValues;
                }

                public boolean saveConfigElement() {
                    if (this.isChanged()) {
                        this.configElement.set(this.currentValues);
                        return this.configElement.requiresMcRestart();
                    }
                    return false;
                }
            }
        }

        public static class ItemEntry
        extends GuiEditArrayEntries.StringEntry {
            private static final Field ENABLED = ReflectionUtil.getField(GuiEditArray.class, Boolean.TYPE);
            private static final Method TOOLTIP = ReflectionUtil.getMethod(GuiScreen.class, new String[]{"func_146285_a", "renderToolTip", "a"}, ItemStack.class, Integer.TYPE, Integer.TYPE);
            protected ItemStack stack;
            protected int stackX;
            protected int stackY;

            public ItemEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
                super(owningScreen, owningEntryList, configElement, value);
                assert (this.isValidated);
                if (this.isValidValue) {
                    this.updateStack();
                }
            }

            public String getValue() {
                return (String)super.getValue();
            }

            protected String getStack() {
                return this.getValue();
            }

            protected void updateStack() {
                try {
                    this.stack = ConfigUtil.asStack(this.getStack());
                    this.isValidValue = !StackUtil.isEmpty(this.stack);
                }
                catch (ParseException e) {
                    this.isValidValue = false;
                }
            }

            protected boolean isEnabled() {
                try {
                    return ENABLED.getBoolean(this.owningScreen);
                }
                catch (Exception e) {
                    throw new RuntimeException("Error checking owningScreen enabled!", e);
                }
            }

            public void keyTyped(char eventChar, int eventKey) {
                super.keyTyped(eventChar, eventKey);
                if (this.isValidValue && this.isEnabled()) {
                    this.updateStack();
                }
            }

            public void func_192634_a(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partial) {
                if (this.isValidValue) {
                    this.isValidated = false;
                }
                super.func_192634_a(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
                this.isValidated = true;
                assert (this.getValue() != null);
                if (this.isValidValue) {
                    RenderHelper.func_74520_c();
                    this.stackX = listWidth / 4 - 16 - 1;
                    this.stackY = y + slotHeight / 2 - 8;
                    this.owningEntryList.getMC().func_175599_af().func_175042_a(this.stack, this.stackX, this.stackY);
                    RenderHelper.func_74518_a();
                }
            }

            public void drawToolTip(int mouseX, int mouseY) {
                super.drawToolTip(mouseX, mouseY);
                if (!StackUtil.isEmpty(this.stack) && this.stackX <= mouseX && this.stackX + 16 >= mouseX && this.stackY <= mouseY && this.stackY + 16 >= mouseY) {
                    assert (((Object)((Object)this)).getClass() != ItemEntry.class || this.isValidValue);
                    try {
                        TOOLTIP.invoke(this.owningScreen, this.stack, mouseX, mouseY);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Error drawing tooltip!", e);
                    }
                }
            }
        }

        private static class ListElement
        extends DummyConfigElement.DummyListElement {
            protected Object[] previous;
            private static final Splitter COMMA_SPLITTER = Splitter.on((char)',').trimResults().omitEmptyStrings();

            protected ListElement(String name, CharSequence value, CharSequence defaultValues, String langKey, Pattern pattern) {
                super(name, COMMA_SPLITTER.splitToList(defaultValues).toArray(), ConfigGuiType.STRING, langKey, pattern);
                this.values = COMMA_SPLITTER.splitToList(value).toArray();
                this.previous = this.values;
            }

            protected void save() {
                if (!this.previous.equals(this.getList())) {
                    MainConfig.get().set(this.getLanguageKey().substring(LANG_KEY_LENGTH).replace('.', '/'), COMMA_JOINER.join(this.getList()));
                }
            }

            public void set(Object[] values) {
                this.previous = this.values;
                this.values = values;
            }

            public void setToDefault() {
                this.previous = this.values;
                super.setToDefault();
            }

            public String toString() {
                return "Config" + ((Object)((Object)this)).getClass().getSimpleName() + '<' + this.name + '>';
            }
        }

        private static class ConfigElement
        extends DummyConfigElement {
            Object previous;

            ConfigElement(String name, Object value, Object defaultValue, ConfigGuiType type, String langKey) {
                super(name, defaultValue, type, langKey);
                this.previous = this.value = value;
            }

            public void set(Object value) {
                this.previous = this.value;
                this.value = value;
            }

            public void setToDefault() {
                this.previous = this.value;
                super.setToDefault();
            }

            public String toString() {
                return "ConfigElement<" + this.name + '>';
            }
        }
    }
}

