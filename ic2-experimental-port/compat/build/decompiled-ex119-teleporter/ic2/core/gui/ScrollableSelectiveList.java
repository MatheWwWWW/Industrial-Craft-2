/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.Ic2Gui;
import ic2.core.gui.AbstractScrollingList;
import ic2.core.gui.MouseButton;
import java.util.List;

public class ScrollableSelectiveList
extends AbstractScrollingList<ScrollableSelectiveList, ISelectableListItem> {
    protected ISelectableListItem currentSelected;

    public ScrollableSelectiveList(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4) {
        super(ic2Gui, n, n2, n3, n4);
    }

    public ScrollableSelectiveList(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, List<ISelectableListItem> list) {
        super(ic2Gui, n, n2, n3, n4, list);
    }

    public ScrollableSelectiveList setSelected(int n) {
        return this.setSelected((ISelectableListItem)this.items.get(n));
    }

    public ScrollableSelectiveList setSelected(ISelectableListItem iSelectableListItem) {
        if (this.currentSelected != null) {
            this.currentSelected.onDeselected();
        }
        assert (this.items.contains(iSelectableListItem));
        iSelectableListItem.onSelected();
        return this;
    }

    public ScrollableSelectiveList clearSelected() {
        if (this.currentSelected != null) {
            this.currentSelected.onDeselected();
        }
        return this;
    }

    @Override
    protected boolean onItemClick(ISelectableListItem iSelectableListItem, MouseButton mouseButton, int n, int n2) {
        switch (mouseButton) {
            case left: {
                if (this.currentSelected != iSelectableListItem) {
                    this.setSelected(iSelectableListItem);
                    return true;
                }
                return false;
            }
            case right: {
                if (this.currentSelected == iSelectableListItem) {
                    iSelectableListItem.onDeselected();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static interface ISelectableListItem
    extends AbstractScrollingList.IListItem {
        public void onSelected();

        public void onDeselected();
    }
}

