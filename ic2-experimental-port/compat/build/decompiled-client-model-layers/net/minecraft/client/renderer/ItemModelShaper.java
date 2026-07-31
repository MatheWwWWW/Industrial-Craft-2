/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemModelShaper {
    public final Int2ObjectMap<ModelResourceLocation> f_109388_ = new Int2ObjectOpenHashMap(256);
    private final Int2ObjectMap<BakedModel> f_109389_ = new Int2ObjectOpenHashMap(256);
    private final ModelManager f_109390_;

    public ItemModelShaper(ModelManager p_109392_) {
        this.f_109390_ = p_109392_;
    }

    public BakedModel m_109406_(ItemStack p_109407_) {
        BakedModel $$1 = this.m_109394_(p_109407_.m_41720_());
        return $$1 == null ? this.f_109390_.m_119409_() : $$1;
    }

    @Nullable
    public BakedModel m_109394_(Item p_109395_) {
        return (BakedModel)this.f_109389_.get(ItemModelShaper.m_109404_(p_109395_));
    }

    private static int m_109404_(Item p_109405_) {
        return Item.m_41393_(p_109405_);
    }

    public void m_109396_(Item p_109397_, ModelResourceLocation p_109398_) {
        this.f_109388_.put(ItemModelShaper.m_109404_(p_109397_), (Object)p_109398_);
    }

    public ModelManager m_109393_() {
        return this.f_109390_;
    }

    public void m_109403_() {
        this.f_109389_.clear();
        for (Map.Entry $$0 : this.f_109388_.entrySet()) {
            this.f_109389_.put((Integer)$$0.getKey(), (Object)this.f_109390_.m_119422_((ModelResourceLocation)$$0.getValue()));
        }
    }
}

