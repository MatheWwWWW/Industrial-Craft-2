/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.uu;

import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.uu.ILateRecipeResolver;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import ic2.core.uu.UuIndex;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UuGraph {
    private static final List<Node> emptyList = Arrays.asList(new Node[0]);
    private static final double epsilon = 1.0E-9;
    private static final Map<LeanItemStack, Node> nodes = new HashMap<LeanItemStack, Node>();
    private static final Map<Item, Set<Node>> itemNodes = new IdentityHashMap<Item, Set<Node>>();
    private static final List<InitialValue> initialValues = new ArrayList<InitialValue>();
    private static volatile Future<?> calculation = null;

    public static void build(boolean bl) {
        if (calculation != null) {
            throw new IllegalStateException("uu graph building is already in progress.");
        }
        if (bl) {
            nodes.clear();
            itemNodes.clear();
        }
        long l = System.nanoTime();
        final ArrayList<RecipeTransformation> arrayList = new ArrayList<RecipeTransformation>();
        for (IRecipeResolver object : UuIndex.instance.resolvers) {
            arrayList.addAll(object.getTransformations());
        }
        for (RecipeTransformation recipeTransformation : arrayList) {
            for (LeanItemStack leanItemStack : recipeTransformation.outputs) {
                UuGraph.getInternal(leanItemStack);
            }
        }
        for (InitialValue initialValue : initialValues) {
            UuGraph.getInternal(initialValue.stack);
        }
        for (ILateRecipeResolver iLateRecipeResolver : UuIndex.instance.lateResolvers) {
            arrayList.addAll(iLateRecipeResolver.getTransformations(nodes.keySet()));
        }
        IC2.log.debug(LogCategory.Uu, "%d UU recipe transformations fetched after %d ms.", arrayList.size(), (System.nanoTime() - l) / 1000000L);
        calculation = IC2.threadPool.submit(new Runnable(){

            @Override
            public void run() {
                UuGraph.processRecipes(arrayList);
            }
        });
    }

    public static void set(ItemStack itemStack, double d) {
        if (calculation != null) {
            throw new IllegalStateException("setting values isn't allowed while the calculation is running, set them earlier.");
        }
        initialValues.add(new InitialValue(new LeanItemStack(itemStack), d));
    }

    public static double get(ItemStack itemStack) {
        UuGraph.finishCalculation();
        LeanItemStack leanItemStack = new LeanItemStack(itemStack, 1);
        Node node = nodes.get(leanItemStack);
        if (node == null) {
            return Double.POSITIVE_INFINITY;
        }
        return node.value;
    }

    public static ItemStack find(ItemStack itemStack) {
        UuGraph.finishCalculation();
        LeanItemStack leanItemStack = new LeanItemStack(itemStack, 1);
        Node node = nodes.get(leanItemStack);
        if (node != null) {
            return node.stack.toMcStack();
        }
        LeanItemStack leanItemStack2 = new LeanItemStack(itemStack.m_41720_(), itemStack.m_41783_(), StackUtil.getSize(itemStack));
        Collection<Node> collection = UuGraph.getAll(leanItemStack2);
        if (collection.isEmpty()) {
            return StackUtil.emptyStack;
        }
        if (collection.size() == 1) {
            return collection.iterator().next().stack.toMcStack();
        }
        return collection.iterator().next().stack.toMcStack();
    }

    public static Iterator<Map.Entry<ItemStack, Double>> iterator() {
        UuGraph.finishCalculation();
        return new ValueIterator();
    }

    private static void processRecipes(List<RecipeTransformation> list) {
        long l = System.nanoTime();
        for (RecipeTransformation object : list) {
            object.merge();
            UuGraph.registerTransform(object);
        }
        for (InitialValue initialValue : initialValues) {
            UuGraph.getInternal(initialValue.stack).setValue(initialValue.value);
        }
        initialValues.clear();
        for (Node node : nodes.values()) {
            node.provides = null;
        }
        IC2.log.debug(LogCategory.Uu, "UU graph built with %d nodes after %d ms.", nodes.size(), (System.nanoTime() - l) / 1000000L);
    }

    private static Node getInternal(LeanItemStack leanItemStack) {
        Node node = nodes.get(leanItemStack = leanItemStack.copyWithSize(1));
        if (node == null) {
            node = new Node(leanItemStack);
            nodes.put(leanItemStack, node);
            Item item = leanItemStack.getItem();
            Set<Node> set = itemNodes.get(item);
            if (set == null) {
                set = new HashSet<Node>(1);
                itemNodes.put(item, set);
            }
            set.add(node);
        }
        return node;
    }

    private static Collection<Node> getAll(LeanItemStack leanItemStack) {
        return new ArrayList<Node>(Arrays.asList(UuGraph.getInternal(leanItemStack)));
    }

    private static void registerTransform(RecipeTransformation recipeTransformation) {
        NodeTransform nodeTransform = new NodeTransform(recipeTransformation);
        for (List<LeanItemStack> object2 : recipeTransformation.inputs) {
            for (LeanItemStack leanItemStack : object2) {
                for (Node node : UuGraph.getAll(leanItemStack)) {
                    node.provides.add(nodeTransform);
                }
            }
        }
        for (LeanItemStack leanItemStack : recipeTransformation.outputs) {
            Node node = UuGraph.getInternal(leanItemStack);
            nodeTransform.out.add(node);
        }
    }

    private static void finishCalculation() {
        if (calculation != null) {
            try {
                calculation.get();
            }
            catch (Exception exception) {
                IC2.log.warn(LogCategory.Uu, exception, "Calculation failed.");
                nodes.clear();
                itemNodes.clear();
            }
            calculation = null;
        }
    }

    private static class Node {
        LeanItemStack stack;
        double value = Double.POSITIVE_INFINITY;
        Set<NodeTransform> provides = new HashSet<NodeTransform>();

        Node(LeanItemStack leanItemStack) {
            this.stack = leanItemStack;
        }

        void setValue(double d) {
            if (d >= this.value - 1.0E-9) {
                return;
            }
            this.value = d;
            for (NodeTransform nodeTransform : this.provides) {
                for (Node node : nodeTransform.out) {
                    int n = nodeTransform.getOutputSize(node.stack);
                    if (n <= 0) {
                        IC2.log.warn(LogCategory.Uu, "UU update: Invalid output size %d in recipetransform %s, expected %s.", n, nodeTransform.transform, node.stack);
                        assert (false);
                        continue;
                    }
                    if (!(node.value > d / (double)n)) continue;
                    node.updateValue(nodeTransform, n);
                }
            }
        }

        private void updateValue(NodeTransform nodeTransform, int n) {
            double d = nodeTransform.transform.transformCost;
            for (List<LeanItemStack> list : nodeTransform.transform.inputs) {
                double d2 = Double.POSITIVE_INFINITY;
                for (LeanItemStack leanItemStack : list) {
                    double d3 = Double.POSITIVE_INFINITY;
                    for (Node node : UuGraph.getAll(leanItemStack)) {
                        if (!(node.value < d3)) continue;
                        d3 = node.value;
                    }
                    if (!((d3 *= (double)leanItemStack.getSize()) < d2)) continue;
                    d2 = d3;
                }
                d += d2;
            }
            this.setValue(d / (double)n);
        }
    }

    private static class InitialValue {
        LeanItemStack stack;
        double value;

        InitialValue(LeanItemStack leanItemStack, double d) {
            this.stack = leanItemStack;
            this.value = d;
        }
    }

    private static class ValueIterator
    implements Iterator<Map.Entry<ItemStack, Double>> {
        private final Iterator<Node> parentIterator = nodes.values().iterator();

        private ValueIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.parentIterator.hasNext();
        }

        @Override
        public Map.Entry<ItemStack, Double> next() {
            Node node = this.parentIterator.next();
            return new AbstractMap.SimpleImmutableEntry<ItemStack, Double>(node.stack.toMcStack(), node.value);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class NodeTransform {
        RecipeTransformation transform;
        Set<Node> out = new HashSet<Node>();

        NodeTransform(RecipeTransformation recipeTransformation) {
            this.transform = recipeTransformation;
        }

        int getOutputSize(LeanItemStack leanItemStack) {
            for (LeanItemStack leanItemStack2 : this.transform.outputs) {
                if (!leanItemStack2.hasSameItem(leanItemStack)) continue;
                return leanItemStack2.getSize();
            }
            return 0;
        }
    }
}

