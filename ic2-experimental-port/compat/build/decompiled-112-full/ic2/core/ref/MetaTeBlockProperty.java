/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Optional
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.ref;

import com.google.common.base.Optional;
import ic2.core.block.ITeBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.ref.MetaTeBlock;
import ic2.core.ref.TeBlock;
import ic2.core.util.Tuple;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;

public class MetaTeBlockProperty
implements IProperty<MetaTeBlock> {
    private final Collection<MetaTeBlock> allowedValues;
    private final String resourceLocationName;
    private static final Map<ResourceLocation, Tuple.T2<Integer, List<MetaTePair>>> resourceToTeBlock = new HashMap<ResourceLocation, Tuple.T2<Integer, List<MetaTePair>>>();
    private static final Map<ITeBlock, MetaTePair> teResourceMapping = new IdentityHashMap<ITeBlock, MetaTePair>();
    public static final MetaTeBlock invalid;

    public MetaTeBlockProperty(final ResourceLocation identifier) {
        this.resourceLocationName = identifier.toString();
        this.allowedValues = new AbstractCollection<MetaTeBlock>(){
            private final int trueSize;
            {
                this.trueSize = (Integer)((Tuple.T2)resourceToTeBlock.get((Object)identifier)).a;
            }

            @Override
            public Iterator<MetaTeBlock> iterator() {
                return new Iterator<MetaTeBlock>(){
                    private int teBlockIdx;
                    private boolean active;
                    private final List<MetaTePair> teBlockMap;
                    private final int allTeBlockSize;
                    {
                        this.teBlockMap = (List)((Tuple.T2)resourceToTeBlock.get((Object)identifier)).b;
                        this.allTeBlockSize = this.teBlockMap.size();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.teBlockIdx < this.allTeBlockSize;
                    }

                    @Override
                    public MetaTeBlock next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        MetaTePair teBlockPair = this.teBlockMap.get(this.teBlockIdx);
                        MetaTeBlock ret = teBlockPair.getState(this.active);
                        if (!this.active && teBlockPair.hasActive()) {
                            this.active = true;
                        } else {
                            this.active = false;
                            ++this.teBlockIdx;
                        }
                        return ret;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Cannot remove a MetaTeBlock state.");
                    }
                };
            }

            @Override
            public int size() {
                return this.trueSize;
            }
        };
    }

    public String func_177701_a() {
        return "type";
    }

    public Collection<MetaTeBlock> func_177700_c() {
        return this.allowedValues;
    }

    public Class<MetaTeBlock> func_177699_b() {
        return MetaTeBlock.class;
    }

    public Optional<MetaTeBlock> func_185929_b(String value) {
        for (MetaTeBlock block : this.allowedValues) {
            if (!this.getName(block).equals(value)) continue;
            return Optional.of((Object)block);
        }
        return Optional.absent();
    }

    public String getName(MetaTeBlock value) {
        if (value.active) {
            return value.teBlock.getName() + "_active";
        }
        return value.teBlock.getName();
    }

    public String toString() {
        return "MetaTeBlockProperty{For " + this.resourceLocationName + '}';
    }

    public static List<MetaTePair> getAllStates(ResourceLocation identifier) {
        return (List)MetaTeBlockProperty.resourceToTeBlock.get((Object)identifier).b;
    }

    public static MetaTeBlock getState(ITeBlock teBlock) {
        return MetaTeBlockProperty.getState(teBlock, false);
    }

    public static MetaTeBlock getState(ITeBlock teBlock, boolean active) {
        MetaTePair state = teResourceMapping.get(teBlock);
        if (state == null) {
            return invalid;
        }
        return state.getState(active);
    }

    static {
        for (Map.Entry<ResourceLocation, Set<? extends ITeBlock>> blocks : TeBlockRegistry.getAll()) {
            ArrayList<MetaTePair> locationBlocks = new ArrayList<MetaTePair>(blocks.getValue().size());
            int states = 0;
            for (ITeBlock n2 : blocks.getValue()) {
                MetaTePair lastIn;
                if (n2.hasActive()) {
                    states += 2;
                    lastIn = new MetaTePair(n2, true);
                    locationBlocks.add(lastIn);
                } else {
                    ++states;
                    lastIn = new MetaTePair(n2, false);
                    locationBlocks.add(lastIn);
                }
                teResourceMapping.put(n2, lastIn);
            }
            resourceToTeBlock.put(blocks.getKey(), new Tuple.T2(states, locationBlocks));
        }
        MetaTePair invalidStates = teResourceMapping.get(TeBlock.invalid);
        invalid = invalidStates.inactive;
        assert (invalid != null) : "Failed to properly map ITeBlocks to MetaTeBlocks!";
        for (Map.Entry<ResourceLocation, Tuple.T2<Integer, List<MetaTePair>>> type : resourceToTeBlock.entrySet()) {
            if (type.getKey() == MetaTeBlockProperty.invalid.teBlock.getIdentifier()) continue;
            Tuple.T2<Integer, List<MetaTePair>> t2 = type.getValue();
            Integer n = (Integer)t2.a;
            t2.a = (Integer)t2.a + 1;
            Integer n2 = t2.a;
            ((List)type.getValue().b).add(invalidStates);
        }
    }

    public static class MetaTePair {
        public final MetaTeBlock inactive;
        public final MetaTeBlock active;
        private final boolean hasActive;

        public MetaTePair(ITeBlock block, boolean active) {
            this.inactive = new MetaTeBlock(block, false);
            this.active = active ? new MetaTeBlock(block, true) : null;
            this.hasActive = active;
        }

        public ITeBlock getBlock() {
            return this.inactive.teBlock;
        }

        public MetaTeBlock getState(boolean active) {
            return active && this.hasActive ? this.active : this.inactive;
        }

        boolean hasActive() {
            return this.hasActive;
        }

        public boolean hasItem() {
            return this.getBlock().hasItem();
        }

        public ResourceLocation getIdentifier() {
            return this.getBlock().getIdentifier();
        }

        public String getName() {
            return this.getBlock().getName();
        }
    }
}

