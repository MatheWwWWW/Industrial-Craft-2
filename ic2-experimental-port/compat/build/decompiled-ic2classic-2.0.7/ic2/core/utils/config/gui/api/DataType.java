/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.config.BooleanElement;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.config.NumberElement;
import ic2.core.utils.config.gui.config.StringElement;
import ic2.core.utils.config.utils.IEntryDataType;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class DataType {
    public static final DataType BOOLEAN = new DataType(false, "false", BooleanElement::new, BooleanElement::new);
    public static final DataType INTEGER = new DataType(false, "0", NumberElement::new, NumberElement::new);
    public static final DataType DOUBLE = new DataType(false, "0.0", NumberElement::new, NumberElement::new);
    public static final DataType STRING = new DataType(true, " ", StringElement::new, StringElement::new);
    private static final Map<Class<?>, DataType> AUTO_DATA_TYPES = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());
    boolean allowsEmptyValue;
    String defaultValue;
    BiFunction<IConfigNode, IValueNode, ConfigElement> creator;
    IArrayFunction arrayCreator;

    public DataType(boolean allowsEmptyValue, String defaultValue, BiFunction<IConfigNode, IValueNode, ConfigElement> creator, IArrayFunction arrayCreator) {
        this.allowsEmptyValue = allowsEmptyValue;
        this.defaultValue = defaultValue;
        this.creator = creator;
        this.arrayCreator = arrayCreator;
    }

    public ConfigElement create(IConfigNode node) {
        return this.creator.apply(node, node.asValue());
    }

    public ConfigElement create(IConfigNode node, IValueNode value) {
        return this.creator.apply(node, value);
    }

    public ConfigElement create(IConfigNode node, IArrayNode array, int index) {
        return this.arrayCreator.create(node, array, index);
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public boolean isAllowEmptyValue() {
        return this.allowsEmptyValue;
    }

    public static DataType bySimple(IEntryDataType.SimpleDataType type) {
        return DataType.byConfig(type.getType(), type.getVariant());
    }

    public static DataType byConfig(IEntryDataType.EntryDataType type, Class<?> variant) {
        switch (type) {
            case BOOLEAN: {
                return BOOLEAN;
            }
            case INTEGER: {
                return INTEGER;
            }
            case DOUBLE: {
                return DOUBLE;
            }
            case STRING: {
                return STRING;
            }
            case CUSTOM: {
                return DataType.byClass(variant);
            }
        }
        throw new IllegalStateException("Undefined DataType shouldn't be used");
    }

    public static DataType byClass(Class<?> clz) {
        return AUTO_DATA_TYPES.getOrDefault(clz, STRING);
    }

    public static void registerType(Class<?> clz, DataType type) {
        AUTO_DATA_TYPES.putIfAbsent(clz, type);
    }

    public static interface IArrayFunction {
        public ConfigElement create(IConfigNode var1, IArrayNode var2, int var3);
    }
}

