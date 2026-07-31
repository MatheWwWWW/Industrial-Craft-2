/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.recipebook;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.slf4j.Logger;

public class ServerPlaceRecipe<C extends Container>
implements PlaceRecipe<Integer> {
    private static final Logger f_135425_ = LogUtils.getLogger();
    protected final StackedContents f_135426_ = new StackedContents();
    protected Inventory f_135427_;
    protected RecipeBookMenu<C> f_135428_;

    public ServerPlaceRecipe(RecipeBookMenu<C> p_135431_) {
        this.f_135428_ = p_135431_;
    }

    public void m_135434_(ServerPlayer p_135435_, @Nullable Recipe<C> p_135436_, boolean p_135437_) {
        if (p_135436_ == null || !p_135435_.m_8952_().m_12709_(p_135436_)) {
            return;
        }
        this.f_135427_ = p_135435_.m_150109_();
        if (!this.m_135453_() && !p_135435_.m_7500_()) {
            return;
        }
        this.f_135426_.m_36453_();
        p_135435_.m_150109_().m_36010_(this.f_135426_);
        this.f_135428_.m_5816_(this.f_135426_);
        if (this.f_135426_.m_36475_(p_135436_, null)) {
            this.m_6024_(p_135436_, p_135437_);
        } else {
            this.m_179844_(true);
            p_135435_.f_8906_.m_9829_(new ClientboundPlaceGhostRecipePacket(p_135435_.f_36096_.f_38840_, p_135436_));
        }
        p_135435_.m_150109_().m_6596_();
    }

    protected void m_179844_(boolean p_179845_) {
        for (int $$1 = 0; $$1 < this.f_135428_.m_6653_(); ++$$1) {
            if (!this.f_135428_.m_142157_($$1)) continue;
            ItemStack $$2 = this.f_135428_.m_38853_($$1).m_7993_().m_41777_();
            this.f_135427_.m_150076_($$2, false);
            this.f_135428_.m_38853_($$1).m_5852_($$2);
        }
        this.f_135428_.m_6650_();
    }

    protected void m_6024_(Recipe<C> p_135441_, boolean p_135442_) {
        int $$6;
        IntArrayList $$7;
        boolean $$2 = this.f_135428_.m_6032_(p_135441_);
        int $$3 = this.f_135426_.m_36493_(p_135441_, null);
        if ($$2) {
            for (int $$4 = 0; $$4 < this.f_135428_.m_6656_() * this.f_135428_.m_6635_() + 1; ++$$4) {
                ItemStack $$5;
                if ($$4 == this.f_135428_.m_6636_() || ($$5 = this.f_135428_.m_38853_($$4).m_7993_()).m_41619_() || Math.min($$3, $$5.m_41741_()) >= $$5.m_41613_() + 1) continue;
                return;
            }
        }
        if (this.f_135426_.m_36478_(p_135441_, (IntList)($$7 = new IntArrayList()), $$6 = this.m_135449_(p_135442_, $$3, $$2))) {
            int $$8 = $$6;
            IntListIterator intListIterator = $$7.iterator();
            while (intListIterator.hasNext()) {
                int $$9 = (Integer)intListIterator.next();
                int $$10 = StackedContents.m_36454_($$9).m_41741_();
                if ($$10 >= $$8) continue;
                $$8 = $$10;
            }
            $$6 = $$8;
            if (this.f_135426_.m_36478_(p_135441_, (IntList)$$7, $$6)) {
                this.m_179844_(false);
                this.m_135408_(this.f_135428_.m_6635_(), this.f_135428_.m_6656_(), this.f_135428_.m_6636_(), p_135441_, $$7.iterator(), $$6);
            }
        }
    }

    @Override
    public void m_5817_(Iterator<Integer> p_135444_, int p_135445_, int p_135446_, int p_135447_, int p_135448_) {
        Slot $$5 = this.f_135428_.m_38853_(p_135445_);
        ItemStack $$6 = StackedContents.m_36454_(p_135444_.next());
        if (!$$6.m_41619_()) {
            for (int $$7 = 0; $$7 < p_135446_; ++$$7) {
                this.m_135438_($$5, $$6);
            }
        }
    }

    protected int m_135449_(boolean p_135450_, int p_135451_, boolean p_135452_) {
        int $$3 = 1;
        if (p_135450_) {
            $$3 = p_135451_;
        } else if (p_135452_) {
            $$3 = 64;
            for (int $$4 = 0; $$4 < this.f_135428_.m_6635_() * this.f_135428_.m_6656_() + 1; ++$$4) {
                ItemStack $$5;
                if ($$4 == this.f_135428_.m_6636_() || ($$5 = this.f_135428_.m_38853_($$4).m_7993_()).m_41619_() || $$3 <= $$5.m_41613_()) continue;
                $$3 = $$5.m_41613_();
            }
            if ($$3 < 64) {
                ++$$3;
            }
        }
        return $$3;
    }

    protected void m_135438_(Slot p_135439_, ItemStack p_135440_) {
        int $$2 = this.f_135427_.m_36043_(p_135440_);
        if ($$2 == -1) {
            return;
        }
        ItemStack $$3 = this.f_135427_.m_8020_($$2).m_41777_();
        if ($$3.m_41619_()) {
            return;
        }
        if ($$3.m_41613_() > 1) {
            this.f_135427_.m_7407_($$2, 1);
        } else {
            this.f_135427_.m_8016_($$2);
        }
        $$3.m_41764_(1);
        if (p_135439_.m_7993_().m_41619_()) {
            p_135439_.m_5852_($$3);
        } else {
            p_135439_.m_7993_().m_41769_(1);
        }
    }

    private boolean m_135453_() {
        ArrayList $$0 = Lists.newArrayList();
        int $$1 = this.m_135454_();
        for (int $$2 = 0; $$2 < this.f_135428_.m_6635_() * this.f_135428_.m_6656_() + 1; ++$$2) {
            ItemStack $$3;
            if ($$2 == this.f_135428_.m_6636_() || ($$3 = this.f_135428_.m_38853_($$2).m_7993_().m_41777_()).m_41619_()) continue;
            int $$4 = this.f_135427_.m_36050_($$3);
            if ($$4 == -1 && $$0.size() <= $$1) {
                for (ItemStack $$5 : $$0) {
                    if (!$$5.m_41656_($$3) || $$5.m_41613_() == $$5.m_41741_() || $$5.m_41613_() + $$3.m_41613_() > $$5.m_41741_()) continue;
                    $$5.m_41769_($$3.m_41613_());
                    $$3.m_41764_(0);
                    break;
                }
                if ($$3.m_41619_()) continue;
                if ($$0.size() < $$1) {
                    $$0.add($$3);
                    continue;
                }
                return false;
            }
            if ($$4 != -1) continue;
            return false;
        }
        return true;
    }

    private int m_135454_() {
        int $$0 = 0;
        for (ItemStack $$1 : this.f_135427_.f_35974_) {
            if (!$$1.m_41619_()) continue;
            ++$$0;
        }
        return $$0;
    }
}

