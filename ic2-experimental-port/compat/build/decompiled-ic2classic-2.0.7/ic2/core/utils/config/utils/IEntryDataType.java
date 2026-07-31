/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.utils.config.utils;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IEntryDataType {
    public boolean isCompound();

    default public SimpleDataType asDataType() {
        throw new IllegalStateException("Type isn't a Simple DataEntry");
    }

    default public CompoundDataType asCompound() {
        throw new IllegalStateException("Type isn't a Compound DataEntry");
    }

    public static final class CompoundDataType
    implements IEntryDataType {
        List<Map.Entry<String, EntryDataType>> values = new ObjectArrayList();
        Map<String, Class<?>> customVariants = new Object2ObjectOpenHashMap();
        Map<String, EntryDataType> customDisplay = new Object2ObjectOpenHashMap();

        public CompoundDataType with(String name, EntryDataType type) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(type);
            if (type == EntryDataType.CUSTOM) {
                throw new IllegalStateException("Use withCustom to add custom entries!");
            }
            this.values.add(new AbstractMap.SimpleEntry<String, EntryDataType>(name, type));
            return this;
        }

        public CompoundDataType withCustom(String name, Class<?> type, EntryDataType displayType) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(type);
            this.values.add(new AbstractMap.SimpleEntry<String, EntryDataType>(name, EntryDataType.CUSTOM));
            this.customVariants.put(name, type);
            this.customDisplay.put(name, displayType);
            return this;
        }

        public List<Map.Entry<String, EntryDataType>> getCompound() {
            return Collections.unmodifiableList(this.values);
        }

        public Class<?> getVariant(String name) {
            return this.customVariants.get(name);
        }

        public EntryDataType getDisplay(String name) {
            return this.customDisplay.get(name);
        }

        @Override
        public CompoundDataType asCompound() {
            return this;
        }

        @Override
        public boolean isCompound() {
            return true;
        }
    }

    public static enum EntryDataType {
        BOOLEAN,
        INTEGER,
        DOUBLE,
        STRING,
        CUSTOM(true);

        SimpleDataType owner;

        private EntryDataType() {
            this(false);
        }

        private EntryDataType(boolean custom) {
            this.owner = custom ? null : new SimpleDataType(this, null);
        }

        public SimpleDataType toSimpleType() {
            return this.owner;
        }
    }

    public static class SimpleDataType
    implements IEntryDataType {
        EntryDataType dataType;
        Class<?> variant;

        private SimpleDataType(EntryDataType dataType, Class<?> variant) {
            this.dataType = dataType;
            this.variant = variant;
        }

        public static SimpleDataType ofVariant(Class<?> variant) {
            return new SimpleDataType(EntryDataType.CUSTOM, variant);
        }

        @Override
        public boolean isCompound() {
            return false;
        }

        @Override
        public SimpleDataType asDataType() {
            return this;
        }

        public EntryDataType getType() {
            return this.dataType;
        }

        public Class<?> getVariant() {
            return this.variant;
        }
    }
}

