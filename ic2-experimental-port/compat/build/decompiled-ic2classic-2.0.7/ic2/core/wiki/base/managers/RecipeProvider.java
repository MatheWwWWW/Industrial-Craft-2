/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RecipesUpdatedEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.TagsUpdatedEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.wiki.base.managers;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.IC2;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.helper.ProgressBar;
import ic2.core.wiki.recipes.providers.IItemReference;
import ic2.core.wiki.recipes.providers.IRecipeProvider;
import ic2.core.wiki.recipes.providers.IRecipeReference;
import ic2.core.wiki.recipes.providers.MachineRecipeProvider;
import ic2.core.wiki.recipes.providers.VanillaRecipeProvider;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RecipeProvider {
    public static final RecipeProvider INSTANCE = new RecipeProvider();
    List<IRecipeProvider> providers = CollectionUtils.createList();
    List<RecipeReference> references = CollectionUtils.createList();
    List<ItemReference> itemRefs = CollectionUtils.createList();

    @OnlyIn(value=Dist.CLIENT)
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.registerProvider(new VanillaRecipeProvider(RecipeType.f_44107_));
        this.registerProvider(new VanillaRecipeProvider(RecipeType.f_44108_));
        this.registerProvider(new VanillaRecipeProvider(RecipeType.f_44109_));
        this.registerProvider(new VanillaRecipeProvider(RecipeType.f_44110_));
        this.registerProvider(new VanillaRecipeProvider(RecipeType.f_44111_));
        this.registerProvider(new MachineRecipeProvider(IC2.RECIPES.get((boolean)false).macerator, new ItemStack((ItemLike)IC2Blocks.MACERATOR), new ProgressBar(234, 16, 212, 16, 22, 16)));
        this.registerProvider(new MachineRecipeProvider(IC2.RECIPES.get((boolean)false).compressor, new ItemStack((ItemLike)IC2Blocks.COMPRESSOR), new ProgressBar(234, 32, 212, 32, 22, 16)));
        this.registerProvider(new MachineRecipeProvider(IC2.RECIPES.get((boolean)false).extractor, new ItemStack((ItemLike)IC2Blocks.EXTRACTOR), new ProgressBar(234, 64, 212, 64, 22, 16)));
        this.registerProvider(new MachineRecipeProvider(IC2.RECIPES.get((boolean)false).recycler, new ItemStack((ItemLike)IC2Blocks.RECYCLER), new ProgressBar(234, 48, 212, 48, 22, 16)));
        this.registerProvider(new MachineRecipeProvider(IC2.RECIPES.get((boolean)false).sawmill, new ItemStack((ItemLike)IC2Blocks.SAWMILL), new ProgressBar(234, 80, 212, 80, 22, 16)));
    }

    public void clearReferences() {
        this.references.clear();
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onClientReloaded(RecipesUpdatedEvent event) {
        this.onReloaded();
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onClientReloaded(TagsUpdatedEvent event) {
        this.reloadItemRefs();
    }

    public void onReloaded() {
        int m = this.providers.size();
        for (int i = 0; i < m; ++i) {
            this.providers.get(i).reloadRecipes();
        }
        this.reloadReferences();
    }

    public void reloadReferences() {
        int m = this.references.size();
        for (int i = 0; i < m; ++i) {
            this.references.get(i).reload();
        }
    }

    public void reloadItemRefs() {
        int m = this.itemRefs.size();
        for (int i = 0; i < m; ++i) {
            this.itemRefs.get(i).reload();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void registerProvider(IRecipeProvider provider) {
        this.providers.add(provider);
    }

    public List<IRecipeRenderer> addRecipesFromInputs(ItemStack stack, boolean ignoreHidden) {
        ObjectList renderers = CollectionUtils.createList();
        for (IRecipeProvider prov : this.providers) {
            renderers.addAll(prov.getFromInput(stack, ignoreHidden));
        }
        return renderers;
    }

    public List<IRecipeRenderer> addRecipesFromOutputs(ItemStack stack, boolean ignoreHidden) {
        ObjectList renderers = CollectionUtils.createList();
        for (IRecipeProvider prov : this.providers) {
            renderers.addAll(prov.getFromOutput(stack, ignoreHidden));
        }
        return renderers;
    }

    public List<IRecipeRenderer> getRecipesForInput(List<ItemStack> items, boolean ignoreHidden) {
        ObjectSortedSet renders = CollectionUtils.createLinkedSet();
        for (ItemStack stack : items) {
            renders.addAll(this.addRecipesFromInputs(stack, ignoreHidden));
        }
        return new ObjectArrayList(renders);
    }

    public List<IRecipeRenderer> getRecipesForOutput(List<ItemStack> items, boolean ignoreHidden) {
        ObjectSortedSet renders = CollectionUtils.createLinkedSet();
        for (ItemStack stack : items) {
            renders.addAll(this.addRecipesFromOutputs(stack, ignoreHidden));
        }
        return new ObjectArrayList(renders);
    }

    public IRecipeReference getRecipe(List<ItemStack> items, boolean ignoreHidden) {
        RecipeReference reference = new RecipeReference(items, ignoreHidden);
        this.references.add(reference);
        return reference;
    }

    public IItemReference getItems(List<IInput> inputs) {
        ItemReference reference = new ItemReference(inputs);
        this.itemRefs.add(reference);
        return reference;
    }

    public static class RecipeReference
    implements IRecipeReference {
        List<ItemStack> items;
        boolean ignoreHidden;
        List<IRecipeRenderer> inputs = CollectionUtils.createList();
        List<IRecipeRenderer> outputs = CollectionUtils.createList();
        Runnable listener;

        public RecipeReference(List<ItemStack> items, boolean ignoreHidden) {
            this.items = items;
            this.ignoreHidden = ignoreHidden;
        }

        public void reload() {
            this.inputs = INSTANCE.getRecipesForInput(this.items, this.ignoreHidden);
            this.outputs = INSTANCE.getRecipesForOutput(this.items, this.ignoreHidden);
            if (this.listener != null) {
                this.listener.run();
            }
        }

        @Override
        public void addSeachInfo(Consumer<Component> search) {
            for (ItemStack stack : this.items) {
                search.accept(stack.m_41611_());
            }
        }

        @Override
        public void setListener(Runnable listener) {
            this.listener = listener;
        }

        @Override
        public List<IRecipeRenderer> getInputs() {
            return this.inputs;
        }

        @Override
        public List<IRecipeRenderer> getOutputs() {
            return this.outputs;
        }
    }

    public static class ItemReference
    implements IItemReference {
        List<ItemStack> items = CollectionUtils.createList();
        List<IInput> inputs;

        public ItemReference(List<IInput> inputs) {
            this.inputs = inputs;
        }

        public void reload() {
            this.items.clear();
            for (IInput input : this.inputs) {
                this.items.addAll(input.getComponents());
            }
        }

        @Override
        public List<ItemStack> getItemList() {
            return this.items;
        }
    }
}

