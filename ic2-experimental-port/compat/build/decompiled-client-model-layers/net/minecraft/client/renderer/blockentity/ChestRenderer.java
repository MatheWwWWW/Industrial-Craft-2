/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestRenderer<T extends BlockEntity>
implements BlockEntityRenderer<T> {
    private static final String f_173603_ = "bottom";
    private static final String f_173604_ = "lid";
    private static final String f_173605_ = "lock";
    private final ModelPart f_112350_;
    private final ModelPart f_112351_;
    private final ModelPart f_112352_;
    private final ModelPart f_112353_;
    private final ModelPart f_112354_;
    private final ModelPart f_112355_;
    private final ModelPart f_112356_;
    private final ModelPart f_112357_;
    private final ModelPart f_112358_;
    private boolean f_112359_;

    public ChestRenderer(BlockEntityRendererProvider.Context p_173607_) {
        Calendar $$1 = Calendar.getInstance();
        if ($$1.get(2) + 1 == 12 && $$1.get(5) >= 24 && $$1.get(5) <= 26) {
            this.f_112359_ = true;
        }
        ModelPart $$2 = p_173607_.m_173582_(ModelLayers.f_171275_);
        this.f_112351_ = $$2.m_171324_(f_173603_);
        this.f_112350_ = $$2.m_171324_(f_173604_);
        this.f_112352_ = $$2.m_171324_(f_173605_);
        ModelPart $$3 = p_173607_.m_173582_(ModelLayers.f_171133_);
        this.f_112354_ = $$3.m_171324_(f_173603_);
        this.f_112353_ = $$3.m_171324_(f_173604_);
        this.f_112355_ = $$3.m_171324_(f_173605_);
        ModelPart $$4 = p_173607_.m_173582_(ModelLayers.f_171134_);
        this.f_112357_ = $$4.m_171324_(f_173603_);
        this.f_112356_ = $$4.m_171324_(f_173604_);
        this.f_112358_ = $$4.m_171324_(f_173605_);
    }

    public static LayerDefinition m_173608_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_173603_, CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(1.0f, 0.0f, 1.0f, 14.0f, 10.0f, 14.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173604_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(1.0f, 0.0f, 0.0f, 14.0f, 5.0f, 14.0f), PartPose.m_171419_(0.0f, 9.0f, 1.0f));
        $$1.m_171599_(f_173605_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(7.0f, -1.0f, 15.0f, 2.0f, 4.0f, 1.0f), PartPose.m_171419_(0.0f, 8.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    public static LayerDefinition m_173609_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_173603_, CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(1.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173604_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(1.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f), PartPose.m_171419_(0.0f, 9.0f, 1.0f));
        $$1.m_171599_(f_173605_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(15.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f), PartPose.m_171419_(0.0f, 8.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    public static LayerDefinition m_173610_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_173603_, CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(0.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173604_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f), PartPose.m_171419_(0.0f, 9.0f, 1.0f));
        $$1.m_171599_(f_173605_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f), PartPose.m_171419_(0.0f, 8.0f, 0.0f));
        return LayerDefinition.m_171565_($$0, 64, 64);
    }

    @Override
    public void m_6922_(T p_112363_, float p_112364_, PoseStack p_112365_, MultiBufferSource p_112366_, int p_112367_, int p_112368_) {
        DoubleBlockCombiner.NeighborCombineResult<ChestBlockEntity> $$15;
        Level $$6 = ((BlockEntity)p_112363_).m_58904_();
        boolean $$7 = $$6 != null;
        BlockState $$8 = $$7 ? ((BlockEntity)p_112363_).m_58900_() : (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, Direction.SOUTH);
        ChestType $$9 = $$8.m_61138_(ChestBlock.f_51479_) ? $$8.m_61143_(ChestBlock.f_51479_) : ChestType.SINGLE;
        Block $$10 = $$8.m_60734_();
        if (!($$10 instanceof AbstractChestBlock)) {
            return;
        }
        AbstractChestBlock $$11 = (AbstractChestBlock)$$10;
        boolean $$12 = $$9 != ChestType.SINGLE;
        p_112365_.m_85836_();
        float $$13 = $$8.m_61143_(ChestBlock.f_51478_).m_122435_();
        p_112365_.m_85837_(0.5, 0.5, 0.5);
        p_112365_.m_85845_(Vector3f.f_122225_.m_122240_(-$$13));
        p_112365_.m_85837_(-0.5, -0.5, -0.5);
        if ($$7) {
            DoubleBlockCombiner.NeighborCombineResult<ChestBlockEntity> $$14 = $$11.m_5641_($$8, $$6, ((BlockEntity)p_112363_).m_58899_(), true);
        } else {
            $$15 = DoubleBlockCombiner.Combiner::m_6502_;
        }
        float $$16 = $$15.m_5649_(ChestBlock.m_51517_((LidBlockEntity)p_112363_)).get(p_112364_);
        $$16 = 1.0f - $$16;
        $$16 = 1.0f - $$16 * $$16 * $$16;
        int $$17 = ((Int2IntFunction)$$15.m_5649_(new BrightnessCombiner())).applyAsInt(p_112367_);
        Material $$18 = Sheets.m_110767_(p_112363_, $$9, this.f_112359_);
        VertexConsumer $$19 = $$18.m_119194_(p_112366_, RenderType::m_110452_);
        if ($$12) {
            if ($$9 == ChestType.LEFT) {
                this.m_112369_(p_112365_, $$19, this.f_112353_, this.f_112355_, this.f_112354_, $$16, $$17, p_112368_);
            } else {
                this.m_112369_(p_112365_, $$19, this.f_112356_, this.f_112358_, this.f_112357_, $$16, $$17, p_112368_);
            }
        } else {
            this.m_112369_(p_112365_, $$19, this.f_112350_, this.f_112352_, this.f_112351_, $$16, $$17, p_112368_);
        }
        p_112365_.m_85849_();
    }

    private void m_112369_(PoseStack p_112370_, VertexConsumer p_112371_, ModelPart p_112372_, ModelPart p_112373_, ModelPart p_112374_, float p_112375_, int p_112376_, int p_112377_) {
        p_112373_.f_104203_ = p_112372_.f_104203_ = -(p_112375_ * 1.5707964f);
        p_112372_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
        p_112373_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
        p_112374_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
    }
}

