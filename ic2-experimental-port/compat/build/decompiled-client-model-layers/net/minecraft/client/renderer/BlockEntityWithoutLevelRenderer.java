/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Pair
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class BlockEntityWithoutLevelRenderer
implements ResourceManagerReloadListener {
    private static final ShulkerBoxBlockEntity[] f_108815_ = (ShulkerBoxBlockEntity[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).map(p_172557_ -> new ShulkerBoxBlockEntity((DyeColor)p_172557_, BlockPos.f_121853_, Blocks.f_50456_.m_49966_())).toArray(ShulkerBoxBlockEntity[]::new);
    private static final ShulkerBoxBlockEntity f_108816_ = new ShulkerBoxBlockEntity(BlockPos.f_121853_, Blocks.f_50456_.m_49966_());
    private final ChestBlockEntity f_108817_ = new ChestBlockEntity(BlockPos.f_121853_, Blocks.f_50087_.m_49966_());
    private final ChestBlockEntity f_108818_ = new TrappedChestBlockEntity(BlockPos.f_121853_, Blocks.f_50325_.m_49966_());
    private final EnderChestBlockEntity f_108819_ = new EnderChestBlockEntity(BlockPos.f_121853_, Blocks.f_50265_.m_49966_());
    private final BannerBlockEntity f_108820_ = new BannerBlockEntity(BlockPos.f_121853_, Blocks.f_50414_.m_49966_());
    private final BedBlockEntity f_108821_ = new BedBlockEntity(BlockPos.f_121853_, Blocks.f_50028_.m_49966_());
    private final ConduitBlockEntity f_108822_ = new ConduitBlockEntity(BlockPos.f_121853_, Blocks.f_50569_.m_49966_());
    private ShieldModel f_108823_;
    private TridentModel f_108824_;
    private Map<SkullBlock.Type, SkullModelBase> f_172546_;
    private final BlockEntityRenderDispatcher f_172547_;
    private final EntityModelSet f_172548_;

    public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        this.f_172547_ = p_172550_;
        this.f_172548_ = p_172551_;
    }

    @Override
    public void m_6213_(ResourceManager p_172555_) {
        this.f_108823_ = new ShieldModel(this.f_172548_.m_171103_(ModelLayers.f_171179_));
        this.f_108824_ = new TridentModel(this.f_172548_.m_171103_(ModelLayers.f_171255_));
        this.f_172546_ = SkullBlockRenderer.m_173661_(this.f_172548_);
    }

    /*
     * WARNING - void declaration
     */
    public void m_108829_(ItemStack p_108830_, ItemTransforms.TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
        Item $$6 = p_108830_.m_41720_();
        if ($$6 instanceof BlockItem) {
            void $$23;
            Block $$7 = ((BlockItem)$$6).m_40614_();
            if ($$7 instanceof AbstractSkullBlock) {
                GameProfile $$8 = null;
                if (p_108830_.m_41782_()) {
                    CompoundTag $$9 = p_108830_.m_41783_();
                    if ($$9.m_128425_("SkullOwner", 10)) {
                        $$8 = NbtUtils.m_129228_($$9.m_128469_("SkullOwner"));
                    } else if ($$9.m_128425_("SkullOwner", 8) && !StringUtils.isBlank((CharSequence)$$9.m_128461_("SkullOwner"))) {
                        $$8 = new GameProfile(null, $$9.m_128461_("SkullOwner"));
                        $$9.m_128473_("SkullOwner");
                        SkullBlockEntity.m_155738_($$8, p_172560_ -> $$9.m_128365_("SkullOwner", NbtUtils.m_129230_(new CompoundTag(), p_172560_)));
                    }
                }
                SkullBlock.Type $$10 = ((AbstractSkullBlock)$$7).m_48754_();
                SkullModelBase $$11 = this.f_172546_.get($$10);
                RenderType $$12 = SkullBlockRenderer.m_112523_($$10, $$8);
                SkullBlockRenderer.m_173663_(null, 180.0f, 0.0f, p_108832_, p_108833_, p_108834_, $$11, $$12);
                return;
            }
            BlockState $$13 = $$7.m_49966_();
            if ($$7 instanceof AbstractBannerBlock) {
                this.f_108820_.m_58489_(p_108830_, ((AbstractBannerBlock)$$7).m_48674_());
                BannerBlockEntity $$14 = this.f_108820_;
            } else if ($$7 instanceof BedBlock) {
                this.f_108821_.m_58729_(((BedBlock)$$7).m_49554_());
                BedBlockEntity $$15 = this.f_108821_;
            } else if ($$13.m_60713_(Blocks.f_50569_)) {
                ConduitBlockEntity $$16 = this.f_108822_;
            } else if ($$13.m_60713_(Blocks.f_50087_)) {
                ChestBlockEntity $$17 = this.f_108817_;
            } else if ($$13.m_60713_(Blocks.f_50265_)) {
                EnderChestBlockEntity $$18 = this.f_108819_;
            } else if ($$13.m_60713_(Blocks.f_50325_)) {
                ChestBlockEntity $$19 = this.f_108818_;
            } else if ($$7 instanceof ShulkerBoxBlock) {
                DyeColor $$20 = ShulkerBoxBlock.m_56252_($$6);
                if ($$20 == null) {
                    ShulkerBoxBlockEntity $$21 = f_108816_;
                } else {
                    ShulkerBoxBlockEntity $$22 = f_108815_[$$20.m_41060_()];
                }
            } else {
                return;
            }
            this.f_172547_.m_112272_($$23, p_108832_, p_108833_, p_108834_, p_108835_);
            return;
        }
        if (p_108830_.m_150930_(Items.f_42740_)) {
            boolean $$24 = BlockItem.m_186336_(p_108830_) != null;
            p_108832_.m_85836_();
            p_108832_.m_85841_(1.0f, -1.0f, -1.0f);
            Material $$25 = $$24 ? ModelBakery.f_119225_ : ModelBakery.f_119226_;
            VertexConsumer $$26 = $$25.m_119204_().m_118381_(ItemRenderer.m_115222_(p_108833_, this.f_108823_.m_103119_($$25.m_119193_()), true, p_108830_.m_41790_()));
            this.f_108823_.m_103711_().m_104306_(p_108832_, $$26, p_108834_, p_108835_, 1.0f, 1.0f, 1.0f, 1.0f);
            if ($$24) {
                List<Pair<Holder<BannerPattern>, DyeColor>> $$27 = BannerBlockEntity.m_58484_(ShieldItem.m_43102_(p_108830_), BannerBlockEntity.m_58487_(p_108830_));
                BannerRenderer.m_112074_(p_108832_, p_108833_, p_108834_, p_108835_, this.f_108823_.m_103701_(), $$25, false, $$27, p_108830_.m_41790_());
            } else {
                this.f_108823_.m_103701_().m_104306_(p_108832_, $$26, p_108834_, p_108835_, 1.0f, 1.0f, 1.0f, 1.0f);
            }
            p_108832_.m_85849_();
        } else if (p_108830_.m_150930_(Items.f_42713_)) {
            p_108832_.m_85836_();
            p_108832_.m_85841_(1.0f, -1.0f, -1.0f);
            VertexConsumer $$28 = ItemRenderer.m_115222_(p_108833_, this.f_108824_.m_103119_(TridentModel.f_103914_), false, p_108830_.m_41790_());
            this.f_108824_.m_7695_(p_108832_, $$28, p_108834_, p_108835_, 1.0f, 1.0f, 1.0f, 1.0f);
            p_108832_.m_85849_();
        }
    }
}

