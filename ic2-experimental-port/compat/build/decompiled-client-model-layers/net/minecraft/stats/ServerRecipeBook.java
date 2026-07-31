/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.stats;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

public class ServerRecipeBook
extends RecipeBook {
    public static final String f_144248_ = "recipeBook";
    private static final Logger f_12786_ = LogUtils.getLogger();

    public int m_12791_(Collection<Recipe<?>> p_12792_, ServerPlayer p_12793_) {
        ArrayList $$2 = Lists.newArrayList();
        int $$3 = 0;
        for (Recipe<?> $$4 : p_12792_) {
            ResourceLocation $$5 = $$4.m_6423_();
            if (this.f_12680_.contains($$5) || $$4.m_5598_()) continue;
            this.m_12702_($$5);
            this.m_12719_($$5);
            $$2.add($$5);
            CriteriaTriggers.f_10572_.m_63718_(p_12793_, $$4);
            ++$$3;
        }
        this.m_12801_(ClientboundRecipePacket.State.ADD, p_12793_, $$2);
        return $$3;
    }

    public int m_12806_(Collection<Recipe<?>> p_12807_, ServerPlayer p_12808_) {
        ArrayList $$2 = Lists.newArrayList();
        int $$3 = 0;
        for (Recipe<?> $$4 : p_12807_) {
            ResourceLocation $$5 = $$4.m_6423_();
            if (!this.f_12680_.contains($$5)) continue;
            this.m_12715_($$5);
            $$2.add($$5);
            ++$$3;
        }
        this.m_12801_(ClientboundRecipePacket.State.REMOVE, p_12808_, $$2);
        return $$3;
    }

    private void m_12801_(ClientboundRecipePacket.State p_12802_, ServerPlayer p_12803_, List<ResourceLocation> p_12804_) {
        p_12803_.f_8906_.m_9829_(new ClientboundRecipePacket(p_12802_, p_12804_, Collections.emptyList(), this.m_12684_()));
    }

    public CompoundTag m_12805_() {
        CompoundTag $$0 = new CompoundTag();
        this.m_12684_().m_12759_($$0);
        ListTag $$1 = new ListTag();
        for (ResourceLocation $$2 : this.f_12680_) {
            $$1.add(StringTag.m_129297_($$2.toString()));
        }
        $$0.m_128365_("recipes", $$1);
        ListTag $$3 = new ListTag();
        for (ResourceLocation $$4 : this.f_12681_) {
            $$3.add(StringTag.m_129297_($$4.toString()));
        }
        $$0.m_128365_("toBeDisplayed", $$3);
        return $$0;
    }

    public void m_12794_(CompoundTag p_12795_, RecipeManager p_12796_) {
        this.m_12687_(RecipeBookSettings.m_12741_(p_12795_));
        ListTag $$2 = p_12795_.m_128437_("recipes", 8);
        this.m_12797_($$2, this::m_12700_, p_12796_);
        ListTag $$3 = p_12795_.m_128437_("toBeDisplayed", 8);
        this.m_12797_($$3, this::m_12723_, p_12796_);
    }

    private void m_12797_(ListTag p_12798_, Consumer<Recipe<?>> p_12799_, RecipeManager p_12800_) {
        for (int $$3 = 0; $$3 < p_12798_.size(); ++$$3) {
            String $$4 = p_12798_.m_128778_($$3);
            try {
                ResourceLocation $$5 = new ResourceLocation($$4);
                Optional<Recipe<?>> $$6 = p_12800_.m_44043_($$5);
                if (!$$6.isPresent()) {
                    f_12786_.error("Tried to load unrecognized recipe: {} removed now.", (Object)$$5);
                    continue;
                }
                p_12799_.accept($$6.get());
                continue;
            }
            catch (ResourceLocationException $$7) {
                f_12786_.error("Tried to load improperly formatted recipe: {} removed now.", (Object)$$4);
            }
        }
    }

    public void m_12789_(ServerPlayer p_12790_) {
        p_12790_.f_8906_.m_9829_(new ClientboundRecipePacket(ClientboundRecipePacket.State.INIT, this.f_12680_, this.f_12681_, this.m_12684_()));
    }
}

