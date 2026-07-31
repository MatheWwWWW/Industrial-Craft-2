/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.apache.commons.lang3.StringUtils
 */
package ic2.core.wiki.base.book;

import ic2.core.IC2;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.math.MathUtils;
import ic2.core.wiki.base.book.WikiChapter;
import ic2.core.wiki.base.book.WikiPage;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.helper.BookMark;
import ic2.core.wiki.helper.PageVisit;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;

public class BuildWiki
implements IWikiProvider {
    Map<ResourceLocation, WikiChapter> chapters;
    Map<ResourceLocation, PageVisit> subChapter;
    Map<PageVisit, String> searchTree = CollectionUtils.createLinkedMap();
    WikiChapter defaultChapter;
    WikiChapter currentChapter;
    PageVisit mainChapter;
    PageVisit controls;
    int page;
    String location;
    List<BookMark> bookMarks = CollectionUtils.createList();
    ObjectArrayFIFOQueue<PageVisit> memory = new ObjectArrayFIFOQueue();
    String searchKey = "";

    public BuildWiki(Map<ResourceLocation, WikiChapter> chapters, Map<ResourceLocation, PageVisit> subChapter, ResourceLocation defaultPage, ResourceLocation mainPage, Object2IntMap.Entry<ResourceLocation> control, String location) {
        WikiChapter chapter;
        this.chapters = chapters;
        this.subChapter = subChapter;
        this.defaultChapter = chapters.get(defaultPage);
        this.currentChapter = chapters.get(defaultPage);
        this.mainChapter = subChapter.getOrDefault(mainPage, new PageVisit(this.defaultChapter, 0));
        if (this.currentChapter == null) {
            throw new IllegalStateException("Default Chapter is required");
        }
        this.location = location;
        if (control != null && (chapter = chapters.get(control.getKey())) != null) {
            this.controls = new PageVisit(chapter, control.getIntValue());
        }
        this.loadLocations();
        long time = System.nanoTime();
        ObjectSet checkedChapters = CollectionUtils.createSet();
        for (WikiChapter current : this.chapters.values()) {
            for (WikiChapter entry : current.iteratePages()) {
                if (!checkedChapters.add((WikiChapter)entry)) continue;
                entry.buildSearchTree(this.searchTree::put, chapters);
            }
        }
        IC2.LOGGER.info(MathUtils.toLagString("Search Tree Loading Time", System.nanoTime() - time));
    }

    public List<BookMark> getBookMarks() {
        return this.bookMarks;
    }

    private void loadLocations() {
        if (this.location == null) {
            return;
        }
        Path path = IC2.PLATFORM.getConfigFolder().resolve(this.location);
        if (Files.exists(path, new LinkOption[0])) {
            this.bookMarks.clear();
            try (InputStream stream = Files.newInputStream(path, new OpenOption[0]);){
                CompoundTag nbt = NbtIo.m_128939_((InputStream)stream);
                for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("marks", 10), CompoundTag.class)) {
                    BookMark mark = BookMark.read(data, this.chapters);
                    if (mark == null) continue;
                    this.bookMarks.add(mark);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void updateLocations(List<BookMark> marks) {
        this.bookMarks.clear();
        this.bookMarks.addAll(marks);
        if (this.location != null) {
            ListTag list = new ListTag();
            for (BookMark mark : this.bookMarks) {
                list.add((Object)mark.save());
            }
            CompoundTag nbt = new CompoundTag();
            nbt.m_128365_("marks", (Tag)list);
            Path path = IC2.PLATFORM.getConfigFolder().resolve(this.location);
            if (Files.notExists(path.getParent(), new LinkOption[0])) {
                try {
                    Files.createDirectories(path.getParent(), new FileAttribute[0]);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            try (OutputStream stream = Files.newOutputStream(path, new OpenOption[0]);){
                NbtIo.m_128947_((CompoundTag)nbt, (OutputStream)stream);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void setToDefault() {
        this.currentChapter = this.defaultChapter;
        this.page = 0;
    }

    public WikiChapter getCurrentChapter() {
        return this.currentChapter;
    }

    public Component getCurrentHeader() {
        Component header;
        WikiPage page = this.getCurrentPage();
        if (page != null && (header = page.getHeader()) != null) {
            return header;
        }
        for (int i = this.page; i >= 0; --i) {
            Component header2;
            page = this.currentChapter.getPage(i);
            if (page == null || (header2 = page.getHeader()) == null) continue;
            return header2;
        }
        return null;
    }

    public List<PageVisit> getPages(String searchText) {
        if (searchText.isEmpty()) {
            return Collections.emptyList();
        }
        searchText = searchText.toLowerCase(Locale.ROOT);
        ObjectList foundEntries = CollectionUtils.createList();
        for (Map.Entry<PageVisit, String> entry : this.searchTree.entrySet()) {
            if (!entry.getValue().contains(searchText)) continue;
            foundEntries.add((PageVisit)entry.getKey());
        }
        String finalText = searchText;
        foundEntries.sort(Comparator.comparing(T -> this.getWorth((PageVisit)T, finalText)));
        return foundEntries;
    }

    private int getWorth(PageVisit visit, String search) {
        String s = visit.getHeader().getString().toLowerCase(Locale.ROOT);
        int value = StringUtils.getLevenshteinDistance((CharSequence)search, (CharSequence)s) + (s.contains(search) ? 0 : 200) + (s.startsWith(search) ? 0 : 100);
        return value;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public PageVisit getControls() {
        return this.controls;
    }

    public int getPage() {
        return this.page;
    }

    public PageVisit getMainPage() {
        return this.mainChapter;
    }

    public PageVisit getDefaultPage() {
        return new PageVisit(this.defaultChapter, 0);
    }

    public PageVisit getCurrentVisit() {
        return new PageVisit(this.currentChapter, this.page);
    }

    public WikiPage getCurrentPage() {
        return this.getPage(this.page);
    }

    public WikiPage getNextPage() {
        return this.getPage(this.page + 1);
    }

    public WikiPage getPage(int index) {
        return this.currentChapter.getPage(index);
    }

    public boolean hasPrevPage() {
        return this.page > 0 || this.currentChapter.getPrev() != null;
    }

    public boolean hasNextPage() {
        return this.page + 2 < this.currentChapter.size() || this.currentChapter.getNext() != null;
    }

    public boolean hasPageLink(boolean prev) {
        WikiPage page = prev ? this.getCurrentPage() : this.getNextPage();
        return page != null && page.getLink() != null && this.hasPageLink(page.getLink(), 0);
    }

    protected int evenOut(int count) {
        return (count & 1) != 0 ? count + 1 : count;
    }

    public int getPageIndex() {
        int count = this.page;
        for (WikiChapter chapter = this.currentChapter.getPrev(); chapter != null; chapter = chapter.getPrev()) {
            count += this.evenOut(chapter.size());
        }
        return count;
    }

    public int getPageCount() {
        WikiChapter chapter;
        int count = 0;
        for (chapter = this.currentChapter.getPrev(); chapter != null; chapter = chapter.getPrev()) {
            count += this.evenOut(chapter.size());
        }
        for (chapter = this.currentChapter.getNext(); chapter != null; chapter = chapter.getNext()) {
            count += this.evenOut(chapter.size());
        }
        return count + this.evenOut(this.currentChapter.size());
    }

    @Override
    public boolean handleClickEvent(ClickEvent event) {
        if (event != null) {
            String[] action = event.m_130623_().split(";");
            if (action.length < 2) {
                return false;
            }
            if (action[0].equalsIgnoreCase("redirect")) {
                WikiChapter chapter = this.chapters.get(new ResourceLocation(action[1]));
                int offset = 0;
                if (chapter == null) {
                    PageVisit visit = this.subChapter.get(new ResourceLocation(action[1]));
                    if (visit == null) {
                        return false;
                    }
                    chapter = visit.getChapter();
                    offset = visit.getPage();
                    if (chapter == null) {
                        return false;
                    }
                } else {
                    try {
                        if (action.length > 2) {
                            offset = Integer.parseInt(action[2]);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                return this.openPage(new PageVisit(chapter, offset), true);
            }
        }
        return false;
    }

    public boolean hasPageLink(ResourceLocation location, int page) {
        WikiChapter chapter = this.chapters.get(location);
        if (chapter != null && chapter.size() > page) {
            return true;
        }
        return this.subChapter.get(location) != null;
    }

    public boolean tryOpenPage(ResourceLocation location, int page) {
        WikiChapter chapter = this.chapters.get(location);
        if (chapter != null && chapter.size() > page) {
            return this.openPage(new PageVisit(chapter, page), true);
        }
        PageVisit visit = this.subChapter.get(location);
        if (visit != null) {
            return this.openPage(visit, true);
        }
        return false;
    }

    @Override
    public boolean openPage(PageVisit visit, boolean save) {
        if (!visit.isValid()) {
            return false;
        }
        if (save) {
            this.memory.enqueue((Object)new PageVisit(this.currentChapter, this.page));
            if (this.memory.size() > 256) {
                this.memory.dequeue();
            }
        }
        this.currentChapter = visit.getChapter();
        this.setPageIndex(visit.getPage());
        return true;
    }

    protected void setPageIndex(int requestedIndex) {
        requestedIndex = Mth.m_14045_((int)requestedIndex, (int)0, (int)this.currentChapter.size());
        this.page = requestedIndex - (requestedIndex & 1);
    }

    public void flipForward(boolean shift) {
        if (this.page + 2 < this.currentChapter.size() && !shift) {
            this.openPage(new PageVisit(this.currentChapter, Math.min(this.currentChapter.size() - 1, this.page + 2)), this.memory.isEmpty());
            return;
        }
        WikiChapter next = this.currentChapter.getNext();
        if (next != null) {
            this.openPage(new PageVisit(next, 0), this.memory.isEmpty());
        } else if (shift) {
            this.openPage(new PageVisit(this.currentChapter, this.currentChapter.size() - 1), this.memory.isEmpty());
        }
    }

    public void flipBackwards(boolean shift) {
        if (this.page > 0 && !shift) {
            this.openPage(new PageVisit(this.currentChapter, Math.max(this.page - 2, 0)), this.memory.isEmpty());
            return;
        }
        if (shift) {
            if (this.page > 0) {
                this.openPage(new PageVisit(this.currentChapter, 0), this.memory.isEmpty());
            } else {
                WikiChapter prev = this.currentChapter.getPrev();
                if (prev != null) {
                    this.openPage(new PageVisit(prev, 0), this.memory.isEmpty());
                }
            }
            return;
        }
        WikiChapter prev = this.currentChapter.getPrev();
        if (prev != null) {
            this.openPage(new PageVisit(prev, Math.max(0, this.evenOut(prev.size()) - 2)), this.memory.isEmpty());
        }
    }

    public boolean pollHistory() {
        return !this.memory.isEmpty() && this.openPage((PageVisit)this.memory.dequeueLast(), false);
    }
}

