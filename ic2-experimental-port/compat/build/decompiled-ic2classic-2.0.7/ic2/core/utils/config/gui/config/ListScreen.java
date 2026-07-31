/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.ElementList;
import ic2.core.utils.config.gui.config.IIgnoreSearch;
import ic2.core.utils.config.gui.config.IListOwner;
import ic2.core.utils.config.gui.widgets.CarbonEditBox;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public abstract class ListScreen
extends Screen
implements IListOwner {
    protected ElementList visibleList;
    protected List<Element> allEntries = new ObjectArrayList();
    protected List<Component> tooltips = new ObjectArrayList();
    protected AbstractWidget activeWidget;
    protected long currentTick = 0L;
    protected long lastTick = -1L;
    protected double lastScroll = -1.0;
    protected EditBox searchBox;
    BackgroundTexture customTexture;

    public ListScreen(Component name, BackgroundTexture customTexture) {
        super(name);
        this.customTexture = customTexture;
    }

    protected void m_7856_() {
        super.m_7856_();
        this.m_169413_();
        this.allEntries.clear();
        this.visibleList = new ElementList(this.f_96543_, this.f_96544_, this.getHeaderSpace(), this.f_96544_ - this.getFooterSpace(), this.getElementHeight());
        this.visibleList.setCustomBackground(this.customTexture);
        this.visibleList.setListWidth(this.getListWidth());
        this.visibleList.setScrollPadding(this.getScrollPadding());
        this.collectElements(this::addEntry);
        this.visibleList.addElements(this.sortElements(this.allEntries));
        this.m_142416_((GuiEventListener)this.visibleList);
        if (this.shouldHaveSearch()) {
            this.searchBox = new CarbonEditBox(this.f_96547_, this.f_96543_ / 2 - 100, 25, 200, 20);
            this.searchBox.m_94167_(I18n.m_118938_((String)"gui.ic2.search", (Object[])new Object[0]));
            this.searchBox.m_94151_(T -> this.onSearchChange(this.searchBox, T.toLowerCase(Locale.ROOT)));
            this.m_142416_((GuiEventListener)this.searchBox);
        }
        if (this.lastScroll >= 0.0) {
            this.visibleList.m_93410_(this.lastScroll);
        }
    }

    protected List<Element> sortElements(List<Element> list) {
        return list;
    }

    public void m_86600_() {
        ++this.currentTick;
        super.m_86600_();
        this.visibleList.tick();
    }

    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.m_7333_(stack);
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        if (!this.tooltips.isEmpty()) {
            ObjectArrayList text = new ObjectArrayList();
            for (Component entry : this.tooltips) {
                text.addAll(this.f_96547_.m_92865_().m_92414_((FormattedText)entry, Integer.MAX_VALUE, Style.f_131099_));
            }
            this.renderComponentTooltip(stack, (List)text, mouseX, mouseY, ItemStack.f_41583_);
            this.tooltips.clear();
        }
    }

    public void m_7861_() {
        this.lastScroll = this.visibleList.m_93517_();
        super.m_7861_();
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        boolean result = super.m_6375_(mouseX, mouseY, button);
        if (this.currentTick - this.lastTick >= 5L) {
            this.activeWidget = null;
        }
        return result;
    }

    protected void addInternal(Element element) {
        element.owner = this;
        element.init();
    }

    protected void addEntry(Element element) {
        element.owner = this;
        this.allEntries.add(element);
        element.init();
    }

    protected abstract void collectElements(Consumer<Element> var1);

    protected void onSearchChange(EditBox box, String value) {
        this.onSearchChange(box, value, this.allEntries);
    }

    protected boolean onSearchChange(EditBox box, String value, List<Element> allEntries) {
        if (value.isEmpty()) {
            box.m_94167_(I18n.m_118938_((String)"gui.ic2.search", (Object[])new Object[0]));
            this.visibleList.updateList(allEntries);
            this.visibleList.m_93410_(this.visibleList.m_93517_());
            return true;
        }
        ObjectArrayList validElements = new ObjectArrayList();
        String suggestion = null;
        int m = allEntries.size();
        for (int i = 0; i < m; ++i) {
            String name;
            Element el = allEntries.get(i);
            if (IIgnoreSearch.shouldIgnoreSearch(el) || !(name = el.getName().toLowerCase(Locale.ROOT)).contains(value)) continue;
            validElements.add(el);
            if (!name.startsWith(value) || suggestion != null && suggestion.length() <= name.length()) continue;
            suggestion = el.getName();
        }
        if (validElements.size() > 0 && suggestion == null) {
            int offset;
            String sub;
            ObjectArrayList sorted;
            ObjectArrayList objectArrayList = sorted = validElements.size() > 2 ? new ObjectArrayList((Collection)validElements) : validElements;
            if (validElements.size() > 2) {
                sorted.sort(Comparator.comparing(Element::getName, Comparator.comparingInt(String::length).reversed()));
            }
            int m2 = sorted.size();
            for (int i = 0; i < m2 && (suggestion = (sub = ((Element)((Object)sorted.get(i))).getName()).substring(offset = sub.toLowerCase(Locale.ROOT).indexOf(value))).length() <= value.length(); ++i) {
            }
            if (suggestion.length() <= value.length()) {
                suggestion = null;
            }
        }
        if (validElements.isEmpty()) {
            box.m_94167_("");
            this.visibleList.updateList((List<Element>)ObjectLists.emptyList());
            this.visibleList.m_93410_(this.visibleList.m_93517_());
            return false;
        }
        if (suggestion == null) {
            box.m_94167_("");
        } else {
            box.m_94167_(suggestion.substring(value.length()));
        }
        this.visibleList.updateList((List<Element>)validElements);
        this.visibleList.m_93410_(this.visibleList.m_93517_());
        return false;
    }

    protected boolean shouldHaveSearch() {
        return true;
    }

    protected boolean shouldHaveTooltips() {
        return true;
    }

    protected int getListWidth() {
        return 220;
    }

    protected int getScrollPadding() {
        return 124;
    }

    protected int getHeaderSpace() {
        return 50;
    }

    protected int getFooterSpace() {
        return 36;
    }

    protected int getElementHeight() {
        return 24;
    }

    @Override
    public void addTooltips(Component tooltip) {
        if (this.shouldHaveTooltips()) {
            this.tooltips.add(tooltip);
        }
    }

    @Override
    public boolean isInsideList(double mouseX, double mouseY) {
        return this.visibleList.m_5953_(mouseX, mouseY);
    }

    @Override
    public boolean isActiveWidget(AbstractWidget widget) {
        return this.activeWidget == widget;
    }

    @Override
    public void setActiveWidget(AbstractWidget widget) {
        this.activeWidget = widget;
        this.lastTick = this.currentTick;
    }

    @Override
    public void removeEntry(Element element) {
    }

    @Override
    public BackgroundTexture getCustomTexture() {
        return this.customTexture;
    }
}

