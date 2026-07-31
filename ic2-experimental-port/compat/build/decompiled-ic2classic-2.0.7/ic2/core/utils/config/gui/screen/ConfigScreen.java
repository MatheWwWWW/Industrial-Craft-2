/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.utils.config.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IModConfig;
import ic2.core.utils.config.gui.config.ArrayElement;
import ic2.core.utils.config.gui.config.CompoundElement;
import ic2.core.utils.config.gui.config.ConfigElement;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.config.FolderElement;
import ic2.core.utils.config.gui.config.ListScreen;
import ic2.core.utils.config.gui.config.SelectionElement;
import ic2.core.utils.config.gui.screen.MultiChoiceScreen;
import ic2.core.utils.config.gui.widgets.CarbonIconCheckbox;
import ic2.core.utils.config.gui.widgets.Icon;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ConfigScreen
extends ListScreen {
    private static final Comparator<Element> SORTER = (K, V) -> {
        int sort = (V instanceof FolderElement ? 1 : 0) - (K instanceof FolderElement ? 1 : 0);
        return sort != 0 ? sort : String.CASE_INSENSITIVE_ORDER.compare(K.getName(), V.getName());
    };
    Screen parent;
    IModConfig config;
    IConfigNode node;
    CarbonIconCheckbox deepSearch;
    CarbonIconCheckbox onlyChanged;
    CarbonIconCheckbox onlyNonDefault;
    boolean wasChanged = false;
    List<Element> cache = null;

    public ConfigScreen(Component name, IModConfig config, Screen parent) {
        this(name, config, parent, BackgroundTexture.DEFAULT);
    }

    public ConfigScreen(Component name, IModConfig config, Screen parent, BackgroundTexture customTexture) {
        super(name, customTexture);
        this.config = config;
        this.node = config.getRootNode();
        this.parent = parent;
    }

    public ConfigScreen(Component name, IConfigNode node, Screen parent, BackgroundTexture customTexture) {
        super(name, customTexture);
        this.node = node;
        this.parent = parent;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        int x = this.f_96543_ / 2 - 100;
        int y = this.f_96544_;
        if (this.node.isRoot()) {
            this.m_142416_((GuiEventListener)new ExtendedButton(x - 51, y - 27, 100, 20, (Component)Component.m_237115_((String)"gui.ic2.save"), this::save));
            this.m_142416_((GuiEventListener)new ExtendedButton(x + 51, y - 27, 100, 20, (Component)Component.m_237115_((String)"gui.ic2.reset"), this::reset));
            this.m_142416_((GuiEventListener)new ExtendedButton(x + 153, y - 27, 100, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), this::goBack));
        } else {
            this.m_142416_((GuiEventListener)new ExtendedButton(x + 101, y - 27, 100, 20, (Component)Component.m_237115_((String)"gui.ic2.back"), this::goBack));
            this.m_142416_((GuiEventListener)new ExtendedButton(x - 1, y - 27, 100, 20, (Component)Component.m_237115_((String)"gui.ic2.home"), this::goToRoot));
        }
        if (this.shouldHaveSearch()) {
            this.deepSearch = (CarbonIconCheckbox)this.m_142416_((GuiEventListener)new CarbonIconCheckbox(x + 205, 25, 20, 20, Icon.SEARCH_SELECTED, Icon.SEARCH, false).withListener(this::onDeepSearch).setTooltip(this, "gui.ic2.deepsearch"));
            this.onlyChanged = (CarbonIconCheckbox)this.m_142416_((GuiEventListener)new CarbonIconCheckbox(x + 227, 25, 20, 20, Icon.SET_DEFAULT, Icon.REVERT, false).withListener(this::onChangedButton).setTooltip(this, "gui.ic2.changed_only"));
            this.onlyNonDefault = (CarbonIconCheckbox)this.m_142416_((GuiEventListener)new CarbonIconCheckbox(x + 249, 25, 20, 20, Icon.NOT_DEFAULT_SELECTED, Icon.NOT_DEFAULT, false).withListener(this::onDefaultButton).setTooltip(this, "gui.ic2.default_only"));
        }
    }

    private void onDeepSearch() {
        if (this.onlyChanged.selected() || this.onlyNonDefault.selected()) {
            this.deepSearch.setSelected(false);
        } else {
            this.wasChanged = true;
        }
    }

    private void onChangedButton() {
        this.deepSearch.setSelected(false);
        this.onlyNonDefault.setSelected(false);
    }

    private void onDefaultButton() {
        this.deepSearch.setSelected(false);
        this.onlyChanged.setSelected(false);
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        if (this.shouldHaveSearch() && (this.onlyChanged.selected() || this.onlyNonDefault.selected() || this.wasChanged)) {
            this.onSearchChange(this.searchBox, this.searchBox.m_94155_().toLowerCase(Locale.ROOT));
            this.wasChanged = this.onlyChanged.selected() || this.onlyNonDefault.selected();
        }
    }

    @Override
    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
        this.f_96547_.m_92889_(stack, this.f_96539_, (float)(this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)this.f_96539_) / 2), 8.0f, -1);
    }

    @Override
    protected List<Element> sortElements(List<Element> list) {
        list.sort(SORTER);
        return list;
    }

    private void goToRoot(Button button) {
        ConfigScreen prev = this;
        ConfigScreen parent = this;
        while (parent instanceof ConfigScreen) {
            prev = parent;
            parent = parent.parent;
        }
        if (prev != this) {
            this.f_96541_.m_91152_((Screen)prev);
        }
    }

    private void reset(Button button) {
        this.f_96541_.m_91152_((Screen)new MultiChoiceScreen(T -> {
            if (T.isMain()) {
                this.processAction(IConfigNode::setDefault);
            } else if (T.isOther()) {
                this.processAction(IConfigNode::setPrevious);
            }
            this.f_96541_.m_91152_((Screen)this);
        }, (Component)Component.m_237115_((String)"gui.ic2.reset_all.title"), (Component)Component.m_237115_((String)"gui.ic2.reset_all.message").m_130940_(ChatFormatting.GRAY), (Component)Component.m_237115_((String)"gui.ic2.reset_all.default"), (Component)Component.m_237115_((String)"gui.ic2.reset_all.reset"), (Component)Component.m_237115_((String)"gui.ic2.reset_all.cancel")));
    }

    private void save(Button button) {
        List<IConfigNode> value = this.processedChanged(IConfigNode::save);
        this.config.save();
        if (this.findFirst(IConfigNode::requiresRestart, value)) {
            MultiChoiceScreen choice = new MultiChoiceScreen(T -> this.f_96541_.m_91152_(this.parent), (Component)Component.m_237115_((String)"gui.ic2.restart.title"), (Component)Component.m_237115_((String)"gui.ic2.restart.message").m_130940_(ChatFormatting.GRAY), (Component)Component.m_237115_((String)"gui.ic2.ok"));
            this.f_96541_.m_91152_((Screen)choice);
            return;
        }
        if (this.f_96541_.f_91073_ != null && this.findFirst(IConfigNode::requiresReload, value)) {
            MultiChoiceScreen choice = new MultiChoiceScreen(T -> this.f_96541_.m_91152_(this.parent), (Component)Component.m_237115_((String)"gui.ic2.reload.title"), (Component)Component.m_237115_((String)"gui.ic2.reload.message").m_130940_(ChatFormatting.GRAY), (Component)Component.m_237115_((String)"gui.ic2.ok"));
            this.f_96541_.m_91152_((Screen)choice);
            return;
        }
        this.f_96541_.m_91152_(this.parent);
    }

    private <T> boolean findFirst(Predicate<T> filter, List<T> elements) {
        int m = elements.size();
        for (int i = 0; i < m; ++i) {
            if (!filter.test(elements.get(i))) continue;
            return true;
        }
        return false;
    }

    private List<IConfigNode> processedChanged(Consumer<IConfigNode> action) {
        ObjectArrayList output = new ObjectArrayList();
        ObjectArrayFIFOQueue nodes = new ObjectArrayFIFOQueue();
        nodes.enqueue((Object)this.node);
        while (!nodes.isEmpty()) {
            IConfigNode node = (IConfigNode)nodes.dequeue();
            if (!node.isLeaf()) {
                node.getChildren().forEach(arg_0 -> ((PriorityQueue)nodes).enqueue(arg_0));
                continue;
            }
            if (!node.isChanged()) continue;
            action.accept(node);
            output.add(node);
        }
        return output;
    }

    private void processAction(Consumer<IConfigNode> action) {
        ObjectArrayFIFOQueue nodes = new ObjectArrayFIFOQueue();
        nodes.enqueue((Object)this.node);
        while (!nodes.isEmpty()) {
            IConfigNode node = (IConfigNode)nodes.dequeue();
            if (!node.isLeaf()) {
                node.getChildren().forEach(arg_0 -> ((PriorityQueue)nodes).enqueue(arg_0));
                continue;
            }
            action.accept(node);
        }
    }

    @Override
    protected void onSearchChange(EditBox box, String value) {
        if (!(this.deepSearch.selected() && !value.isEmpty() || this.onlyChanged.selected() || this.onlyNonDefault.selected())) {
            super.onSearchChange(box, value);
            return;
        }
        if (this.cache == null) {
            this.cache = this.sortElements(ConfigScreen.getAllElements(this.node));
            this.cache.forEach(x$0 -> this.addInternal((Element)((Object)x$0)));
        }
        if (this.onlyNonDefault.selected()) {
            ObjectArrayList subCache = new ObjectArrayList();
            for (Element element : this.cache) {
                if (element.isDefault()) continue;
                subCache.add(element);
            }
            super.onSearchChange(box, value, (List<Element>)subCache);
            return;
        }
        if (this.onlyChanged.selected()) {
            ObjectArrayList subCache = new ObjectArrayList();
            for (Element element : this.cache) {
                if (!element.isChanged()) continue;
                subCache.add(element);
            }
            super.onSearchChange(box, value, (List<Element>)subCache);
            return;
        }
        super.onSearchChange(box, value, this.cache);
    }

    private void goBack(Button button) {
        if (this.node.isRoot() && this.isChanged()) {
            this.f_96541_.m_91152_((Screen)new ConfirmScreen(T -> this.f_96541_.m_91152_((Screen)(T ? this.parent : this)), (Component)Component.m_237115_((String)"gui.ic2.warn.changed"), (Component)Component.m_237115_((String)"gui.ic2.warn.changed.desc").m_130940_(ChatFormatting.GRAY)));
            return;
        }
        this.f_96541_.m_91152_(this.parent);
    }

    private boolean isChanged() {
        ObjectArrayFIFOQueue nodes = new ObjectArrayFIFOQueue();
        nodes.enqueue((Object)this.node);
        while (!nodes.isEmpty()) {
            IConfigNode node = (IConfigNode)nodes.dequeue();
            if (!node.isLeaf()) {
                node.getChildren().forEach(arg_0 -> ((PriorityQueue)nodes).enqueue(arg_0));
                continue;
            }
            if (!node.isChanged()) continue;
            return true;
        }
        return false;
    }

    @Override
    protected int getListWidth() {
        return 300;
    }

    @Override
    protected int getScrollPadding() {
        return 155;
    }

    @Override
    protected void collectElements(Consumer<Element> elements) {
        for (IConfigNode child : this.node.getChildren()) {
            if (child.isLeaf()) {
                if (child.isArray()) {
                    elements.accept(new ArrayElement(child));
                    continue;
                }
                if (child.getDataType().size() > 1) {
                    elements.accept(new CompoundElement(child));
                    continue;
                }
                if (child.getValidValues().size() > 0) {
                    elements.accept(new SelectionElement(child));
                    continue;
                }
                ConfigElement element = child.getDataType().get(0).create(child);
                if (element == null) continue;
                elements.accept(element);
                continue;
            }
            elements.accept(new FolderElement(child, this.f_96539_));
        }
    }

    private static List<Element> getAllElements(IConfigNode init) {
        ObjectArrayFIFOQueue nodes = new ObjectArrayFIFOQueue();
        nodes.enqueue((Object)init);
        ObjectArrayList results = new ObjectArrayList();
        while (!nodes.isEmpty()) {
            IConfigNode node = (IConfigNode)nodes.dequeue();
            if (!node.isLeaf()) {
                node.getChildren().forEach(arg_0 -> ((PriorityQueue)nodes).enqueue(arg_0));
                continue;
            }
            if (node.isArray()) {
                results.add(new ArrayElement(node));
                continue;
            }
            if (node.getDataType().size() > 1) {
                results.add(new CompoundElement(node));
                continue;
            }
            if (node.getValidValues().size() > 0) {
                results.add(new SelectionElement(node));
                continue;
            }
            ConfigElement element = node.getDataType().get(0).create(node);
            if (element == null) continue;
            results.add(element);
        }
        return results;
    }
}

