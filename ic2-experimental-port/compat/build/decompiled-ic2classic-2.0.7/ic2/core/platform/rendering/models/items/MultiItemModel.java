/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.models.items;

import ic2.core.platform.rendering.features.item.IMultiItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MultiItemModel
extends BaseModel {
    List<BaseModel> models;
    IMultiItemModel lexer;
    ItemOverrides list = new MultiItemOverrides();

    public MultiItemModel(List<BaseModel> models, IMultiItemModel lexer) {
        this.models = models;
        this.lexer = lexer;
    }

    @Override
    public void init() {
        this.setParticleTexture(this.models.get(0).m_6160_());
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.list;
    }

    class MultiItemOverrides
    extends ItemOverrides {
        MultiItemOverrides() {
        }

        public BakedModel m_173464_(BakedModel model, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entityIn, int randomValue) {
            return MultiItemModel.this.models.get(Mth.m_14045_((int)MultiItemModel.this.lexer.getModelIndexForStack(stack, entityIn), (int)0, (int)(MultiItemModel.this.models.size() - 1)));
        }
    }
}

