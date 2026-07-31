/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package ic2.core.wiki.base.managers;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.base.book.BuildWiki;
import ic2.core.wiki.base.book.WikiChapter;
import ic2.core.wiki.base.book.WikiPage;
import ic2.core.wiki.base.managers.RecipeProvider;
import ic2.core.wiki.components.IWikiComponent;
import ic2.core.wiki.components.builders.CraftObj;
import ic2.core.wiki.components.builders.HeaderObj;
import ic2.core.wiki.components.builders.IWikiObj;
import ic2.core.wiki.components.builders.LinkObj;
import ic2.core.wiki.components.builders.PageEndObj;
import ic2.core.wiki.components.builders.TextObj;
import ic2.core.wiki.helper.PageVisit;
import ic2.core.wiki.recipes.providers.IItemReference;
import ic2.core.wiki.recipes.providers.IRecipeReference;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class WikiBuilder {
    final Set<String> modIds;
    Set<Item> blacklist = CollectionUtils.createSet();
    Map<ResourceLocation, List<IWikiObj>> pages = CollectionUtils.createLinkedMap();
    Map<ResourceLocation, List<IWikiObj>> chapters = CollectionUtils.createLinkedMap();
    List<Consumer<WikiBuilder>> listeners = CollectionUtils.createList();
    List<Consumer<IChapterBuilder>> chapterBuilder = CollectionUtils.createList();
    String bookmarks;
    Supplier<ResourceLocation> defaultPage;
    Supplier<ResourceLocation> mainPage;
    Supplier<Object2IntMap.Entry<ResourceLocation>> controlsPage;
    Consumer<BuildWiki> result;
    Function<Item, List<IWikiObj>> creatorFunction = this::createFor;

    public WikiBuilder(Consumer<BuildWiki> result, Supplier<ResourceLocation> defaultPage, String ... modIds) {
        this(result, defaultPage, (Set<String>)new ObjectOpenHashSet((Object[])modIds));
    }

    public WikiBuilder(Consumer<BuildWiki> result, Supplier<ResourceLocation> defaultPage, Set<String> modIds) {
        this.modIds = modIds;
        this.defaultPage = defaultPage;
        this.mainPage = defaultPage;
        this.result = result;
    }

    public WikiBuilder addBookMarkLocation(String path) {
        this.bookmarks = path;
        return this;
    }

    public WikiBuilder setControlsPage(Supplier<Object2IntMap.Entry<ResourceLocation>> controlsPage) {
        this.controlsPage = controlsPage;
        return this;
    }

    public WikiBuilder setMainPage(Supplier<ResourceLocation> mainPage) {
        this.mainPage = mainPage;
        return this;
    }

    public WikiBuilder addPageAdder(Consumer<WikiBuilder> builder) {
        this.listeners.add(builder);
        return this;
    }

    public WikiBuilder addChapterBuilder(Consumer<IChapterBuilder> builder) {
        this.chapterBuilder.add(builder);
        return this;
    }

    public WikiBuilder setDefaultPageGenerator(Function<Item, List<IWikiObj>> function) {
        this.creatorFunction = function;
        return this;
    }

    public WikiBuilder addBlacklistEntry(ItemLike ... items) {
        int i = 0;
        int m = items.length;
        while (i < m) {
            this.blacklist.add(items[i++].m_5456_());
        }
        return this;
    }

    public <T> WikiBuilder addPage(T type, IForgeRegistry<T> registry, IWikiObj ... objects) {
        return this.addPage(registry.getKey(type), objects);
    }

    public WikiBuilder addPage(ResourceLocation location, IWikiObj ... objects) {
        if (objects.length > 0) {
            this.pages.putIfAbsent(location, (List<IWikiObj>)CollectionUtils.asList(objects));
        }
        return this;
    }

    public WikiBuilder addBasicChapter(ItemLike ... provider) {
        int m = provider.length;
        for (int i = 0; i < m; ++i) {
            this.addBasicChapter(ForgeRegistries.ITEMS.getKey((Object)provider[i].m_5456_()));
        }
        return this;
    }

    public WikiBuilder addBasicChapter(ResourceLocation location) {
        return this.addChapter(new ResourceLocation(location.m_135827_(), "subpage." + location.m_135815_()), new HeaderObj("wiki." + location.m_135827_() + ".subpage." + location.m_135815_() + ".head"), new TextObj("wiki." + location.m_135827_() + ".subpage." + location.m_135815_() + ".body"));
    }

    public WikiBuilder addChapter(ItemLike provider, IWikiObj ... objects) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey((Object)provider.m_5456_());
        return this.addChapter(new ResourceLocation(location.m_135827_(), "subpage." + location.m_135815_()), objects);
    }

    public WikiBuilder addChapter(String mod, String path, IWikiObj ... objects) {
        if (objects.length > 0) {
            this.chapters.put(new ResourceLocation(mod, path), (List<IWikiObj>)CollectionUtils.asList(objects));
        }
        return this;
    }

    public WikiBuilder addChapter(ResourceLocation location, IWikiObj ... objects) {
        if (objects.length > 0) {
            this.chapters.put(location, (List<IWikiObj>)CollectionUtils.asList(objects));
        }
        return this;
    }

    public void build() {
        this.autoGenerateBasePages();
        Object2ObjectSortedMap subChapters = CollectionUtils.createLinkedMap();
        Object2ObjectSortedMap buildChapters = CollectionUtils.createLinkedMap();
        PageBuilder builder = new PageBuilder((Map<ResourceLocation, WikiChapter>)buildChapters, (Map<ResourceLocation, PageVisit>)subChapters, this.pages, this.creatorFunction);
        for (Map.Entry<ResourceLocation, List<IWikiObj>> entry : this.chapters.entrySet()) {
            builder.startBuildChapter(entry.getKey());
            for (IWikiObj obj : entry.getValue()) {
                obj.buildComponents(builder);
            }
            builder.finishBuildChapter(false);
        }
        builder.clearLastChapter();
        int i = 0;
        int m = this.chapterBuilder.size();
        while (i < m) {
            this.chapterBuilder.get(i++).accept(builder);
        }
        this.result.accept(new BuildWiki((Map<ResourceLocation, WikiChapter>)buildChapters, (Map<ResourceLocation, PageVisit>)subChapters, this.defaultPage.get(), this.mainPage.get(), this.controlsPage.get(), this.bookmarks));
        this.pages.clear();
        this.chapters.clear();
    }

    private void autoGenerateBasePages() {
        int i = 0;
        int m = this.listeners.size();
        while (i < m) {
            this.listeners.get(i++).accept(this);
        }
    }

    private List<IWikiObj> createFor(Item item) {
        ObjectList result = CollectionUtils.createList();
        result.add((HeaderObj)new HeaderObj((Component)item.m_7968_().m_41786_().m_6881_().m_130948_(Style.f_131099_.m_131136_(Boolean.valueOf(true)))));
        result.add((CraftObj)new CraftObj(new ItemLike[]{item}));
        ResourceLocation location = ForgeRegistries.ITEMS.getKey((Object)item);
        result.add((TextObj)new TextObj("wiki." + location.m_135827_() + ".preview." + location.m_135815_() + ".desc").setCutOff());
        result.add((LinkObj)new LinkObj(location.m_135827_(), "subpage." + location.m_135815_()));
        result.add((PageEndObj)new PageEndObj());
        return result;
    }

    static class PageBuilder
    implements IPageBuilder,
    IChapterBuilder {
        public static final int MAX_PAGE_SIZE = 170;
        Map<ResourceLocation, WikiChapter> buildChapters;
        Map<ResourceLocation, List<IWikiObj>> pages;
        Map<ResourceLocation, PageVisit> subChapters;
        Function<Item, List<IWikiObj>> creator;
        WikiChapter currentChapter;
        WikiChapter lastChapter;
        WikiPage page = new WikiPage();
        int used;

        public PageBuilder(Map<ResourceLocation, WikiChapter> buildChapters, Map<ResourceLocation, PageVisit> subChapters, Map<ResourceLocation, List<IWikiObj>> pages, Function<Item, List<IWikiObj>> creator) {
            this.buildChapters = buildChapters;
            this.subChapters = subChapters;
            this.pages = pages;
            this.creator = creator;
        }

        public void clearLastChapter() {
            this.lastChapter = null;
        }

        @Override
        public void clearPage() {
            this.page.getComponents().clear();
        }

        @Override
        public void disableSearch(boolean page) {
            if (page) {
                this.page.setSearchable(false);
            } else {
                this.currentChapter.setSearchable(false);
            }
        }

        @Override
        public void addComponent(IWikiComponent component) {
            if (component.getHeight() > this.getHeightLeft()) {
                this.setEndOfPage();
            }
            this.page.addComponent(component);
            this.used += component.getHeight();
        }

        @Override
        public void setPageLink(ResourceLocation location) {
            this.page.setLink(location);
        }

        @Override
        public boolean hasComponents() {
            return this.page.getComponents().size() > 0;
        }

        @Override
        public void setEndOfPage() {
            this.currentChapter.addPage(this.page);
            this.used = 0;
            this.page = new WikiPage();
        }

        @Override
        public int getHeightLeft() {
            return 170 - this.used;
        }

        private ResourceLocation getPreviewLocation(ResourceLocation location) {
            return new ResourceLocation(location.m_135827_(), "preview." + location.m_135815_());
        }

        @Override
        public void addPreviewBookMark(ResourceLocation id) {
            this.subChapters.putIfAbsent(this.getPreviewLocation(id), new PageVisit(this.currentChapter, this.currentChapter.size()));
        }

        @Override
        public IRecipeReference getRecipes(List<ItemStack> list, boolean ignoreHidden) {
            return RecipeProvider.INSTANCE.getRecipe(list, ignoreHidden);
        }

        @Override
        public IItemReference getItemList(List<IInput> inputs) {
            return RecipeProvider.INSTANCE.getItems(inputs);
        }

        @Override
        public void startBuildChapter(ResourceLocation id) {
            this.currentChapter = new WikiChapter(id);
        }

        @Override
        public void addSubChapter(ResourceLocation location) {
            this.subChapters.putIfAbsent(location, new PageVisit(this.currentChapter, this.currentChapter.size()));
        }

        @Override
        public void addSimplePage(List<IWikiObj> objects) {
            objects.forEach(T -> T.buildComponents(this));
            this.setEndOfPage();
        }

        @Override
        public void addSubPages(ItemLike ... providers) {
            for (ItemLike provider : providers) {
                List objects;
                Item item = provider.m_5456_();
                if (item == null || (objects = this.pages.computeIfAbsent(ForgeRegistries.ITEMS.getKey((Object)item), T -> this.creator.apply(item))) == null || objects.isEmpty()) continue;
                this.subChapters.put(this.getPreviewLocation(ForgeRegistries.ITEMS.getKey((Object)item)), new PageVisit(this.currentChapter, this.currentChapter.size()));
                objects.forEach(T -> T.buildComponents(this));
            }
        }

        @Override
        public void addSubPages(ResourceLocation ... locations) {
            for (ResourceLocation location : locations) {
                List<IWikiObj> objects = this.pages.get(location);
                if (objects == null || objects.isEmpty()) continue;
                this.subChapters.put(this.getPreviewLocation(location), new PageVisit(this.currentChapter, this.currentChapter.size()));
                objects.forEach(T -> T.buildComponents(this));
            }
        }

        @Override
        public void markPreview(ItemLike provider) {
            Item item = provider.m_5456_();
            if (item == null) {
                return;
            }
            this.subChapters.put(this.getPreviewLocation(ForgeRegistries.ITEMS.getKey((Object)item)), new PageVisit(this.currentChapter, this.currentChapter.size()));
        }

        @Override
        public void addPageRedirector(ResourceLocation location, ItemLike ... providers) {
            PageVisit source = this.subChapters.get(location);
            if (source == null) {
                throw new IllegalStateException("Source Page [" + location + "] doesn't exist");
            }
            for (ItemLike provider : providers) {
                Item item = provider.m_5456_();
                if (item == null) continue;
                this.subChapters.put(this.getPreviewLocation(ForgeRegistries.ITEMS.getKey((Object)item)), source);
            }
        }

        @Override
        public void addPageRedirector(ResourceLocation location, ResourceLocation ... locations) {
            PageVisit source = this.subChapters.get(location);
            if (source == null) {
                throw new IllegalStateException("Source Page [" + location + "] doesn't exist");
            }
            for (ResourceLocation provider : locations) {
                this.subChapters.put(this.getPreviewLocation(provider), source);
            }
        }

        @Override
        public void finishBuildChapter(boolean linkIntoPrevChapter) {
            if (this.lastChapter != null && linkIntoPrevChapter) {
                this.lastChapter.setNext(this.currentChapter);
            }
            this.lastChapter = this.currentChapter;
            if (this.page.getComponents().size() > 0) {
                this.setEndOfPage();
            }
            this.buildChapters.put(this.currentChapter.getId(), this.currentChapter);
            this.currentChapter = null;
        }

        @Override
        public void setEndOfDoublePage() {
            if ((this.currentChapter.size() & 1) == 0) {
                this.setEndOfPage();
            }
        }

        @Override
        public void finishSubChapter() {
            if ((this.currentChapter.size() & 1) == 1) {
                this.setEndOfPage();
            }
        }
    }
}

