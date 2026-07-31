/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  net.minecraft.block.Block
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.BlockStateContainer$StateImplementation
 *  net.minecraft.block.state.IBlockState
 *  net.minecraftforge.common.property.IUnlistedProperty
 */
package ic2.core.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class Ic2BlockState
extends BlockStateContainer {
    private final Map<Map<IProperty<?>, Comparable<?>>, Ic2BlockStateInstance> index = this.createIndex();

    public Ic2BlockState(Block blockIn, IProperty<?> ... properties) {
        super(blockIn, properties);
    }

    protected BlockStateContainer.StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        return new Ic2BlockStateInstance(block, properties);
    }

    private Map<Map<IProperty<?>, Comparable<?>>, Ic2BlockStateInstance> createIndex() {
        HashMap ret = new HashMap(this.func_177619_a().size());
        for (IBlockState rawState : this.func_177619_a()) {
            Ic2BlockStateInstance state = (Ic2BlockStateInstance)rawState;
            ret.put(Ic2BlockState.createMap(rawState.func_177228_b()), state);
            state.clearPropertyValueTable();
        }
        return ret;
    }

    private static Map<IProperty<?>, Comparable<?>> createMap(Map<IProperty<?>, Comparable<?>> src) {
        return new HashMap(src);
    }

    public class Ic2BlockStateInstance
    extends BlockStateContainer.StateImplementation {
        private final Map<IUnlistedProperty<?>, Object> extraProperties;
        private final ThreadLocal<Map<IProperty<?>, Comparable<?>>> tlProperties;

        private Ic2BlockStateInstance(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties) {
            super(block, properties, null);
            this.tlProperties = new ThreadLocal<Map<IProperty<?>, Comparable<?>>>(){

                @Override
                protected Map<IProperty<?>, Comparable<?>> initialValue() {
                    return Ic2BlockState.createMap((Map)Ic2BlockStateInstance.this.func_177228_b());
                }
            };
            this.extraProperties = Collections.emptyMap();
        }

        private Ic2BlockStateInstance(Ic2BlockStateInstance parent, Map<IUnlistedProperty<?>, Object> extraProperties) {
            super(parent.func_177230_c(), parent.func_177228_b(), parent.field_177238_c);
            this.tlProperties = new /* invalid duplicate definition of identical inner class */;
            this.extraProperties = extraProperties;
        }

        public <T extends Comparable<T>, V extends T> Ic2BlockStateInstance withProperty(IProperty<T> property, V value) {
            Comparable prevValue = (Comparable)this.func_177228_b().get(property);
            if (prevValue == value) {
                return this;
            }
            if (prevValue == null) {
                throw new IllegalArgumentException("invalid property for this state: " + property);
            }
            if (!property.func_177700_c().contains(value)) {
                throw new IllegalArgumentException("invalid property value " + value + " for property " + property + " (" + property.func_177702_a(value) + ')');
            }
            Map<IProperty<?>, Comparable<?>> lookup = this.tlProperties.get();
            lookup.put(property, (Comparable<?>)value);
            Ic2BlockStateInstance ret = (Ic2BlockStateInstance)((Object)Ic2BlockState.this.index.get(lookup));
            lookup.put(property, prevValue);
            if (!this.extraProperties.isEmpty()) {
                ret = new Ic2BlockStateInstance(ret, this.extraProperties);
            }
            return ret;
        }

        public <T> Ic2BlockStateInstance withProperty(IUnlistedProperty<T> property, T value) {
            if (property == null) {
                throw new NullPointerException("null property");
            }
            if (this.extraProperties.get(property) == value) {
                return this;
            }
            if (value != null && !property.getType().isAssignableFrom(value.getClass())) {
                throw new IllegalArgumentException("The value " + value + " (" + value.getClass().getName() + ") is not applicable for " + property);
            }
            IdentityHashMap newExtraProperties = new IdentityHashMap(this.extraProperties);
            newExtraProperties.put(property, value);
            Ic2BlockStateInstance ret = new Ic2BlockStateInstance(this, newExtraProperties);
            return ret;
        }

        public <T> Ic2BlockStateInstance withProperties(Object ... properties) {
            if (properties.length % 2 != 0) {
                throw new IllegalArgumentException("property pairs expected");
            }
            IdentityHashMap newExtraProperties = new IdentityHashMap(this.extraProperties);
            for (int i = 0; i < properties.length; i += 2) {
                IUnlistedProperty property = (IUnlistedProperty)properties[i];
                if (property == null) {
                    throw new NullPointerException("null property");
                }
                Object value = properties[i + 1];
                if (value != null && !property.getType().isAssignableFrom(value.getClass())) {
                    throw new IllegalArgumentException("The value " + value + " (" + value.getClass().getName() + ") is not applicable for " + property);
                }
                newExtraProperties.put(property, value);
            }
            if (newExtraProperties.size() == this.extraProperties.size() && newExtraProperties.equals(this.extraProperties)) {
                return this;
            }
            Ic2BlockStateInstance ret = new Ic2BlockStateInstance(this, newExtraProperties);
            return ret;
        }

        public boolean hasValue(IUnlistedProperty<?> property) {
            return this.extraProperties.containsKey(property);
        }

        public <T> T getValue(IUnlistedProperty<T> property) {
            Object ret = this.extraProperties.get(property);
            return (T)ret;
        }

        public String toString() {
            String ret = super.toString();
            if (!this.extraProperties.isEmpty()) {
                StringBuilder sb = new StringBuilder(ret);
                sb.setCharAt(sb.length() - 1, ';');
                ArrayList entries = new ArrayList(this.extraProperties.entrySet());
                Collections.sort(entries, new Comparator<Map.Entry<IUnlistedProperty<?>, Object>>(){

                    @Override
                    public int compare(Map.Entry<IUnlistedProperty<?>, Object> a, Map.Entry<IUnlistedProperty<?>, Object> b) {
                        return a.getKey().getName().compareTo(b.getKey().getName());
                    }
                });
                for (Map.Entry entry : entries) {
                    sb.append(((IUnlistedProperty)entry.getKey()).getName());
                    sb.append('=');
                    sb.append(entry.getValue());
                    sb.append(',');
                }
                sb.setCharAt(sb.length() - 1, ']');
                ret = sb.toString();
            }
            return ret;
        }

        public boolean hasExtraProperties() {
            return !this.extraProperties.isEmpty();
        }

        private void clearPropertyValueTable() {
            this.field_177238_c = null;
        }
    }
}

