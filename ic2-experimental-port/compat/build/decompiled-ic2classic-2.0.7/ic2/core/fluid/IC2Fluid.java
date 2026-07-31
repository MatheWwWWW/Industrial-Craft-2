/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.fluids.FluidType
 *  net.minecraftforge.fluids.FluidType$Properties
 */
package ic2.core.fluid;

import ic2.core.platform.rendering.features.IRenderType;
import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

public class IC2Fluid
extends Fluid
implements IRenderType {
    FluidType fluidType;

    public IC2Fluid(FluidType fluidType) {
        this.fluidType = fluidType;
    }

    public static FluidType createType(FluidType.Properties props, ResourceLocation still) {
        return IC2Fluid.createType(props, still, still, null);
    }

    public static FluidType createType(FluidType.Properties props, ResourceLocation still, Integer color) {
        return IC2Fluid.createType(props, still, still, color);
    }

    public static FluidType createType(FluidType.Properties props, ResourceLocation still, ResourceLocation flowing) {
        return IC2Fluid.createType(props, still, flowing, null);
    }

    public static FluidType createType(FluidType.Properties props, final ResourceLocation still, final ResourceLocation flowing, final Integer color) {
        return new FluidType(props){

            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new FluidInfo(still, flowing, color));
            }
        };
    }

    public FluidType getFluidType() {
        return this.fluidType;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110466_();
    }

    public Item m_6859_() {
        return Items.f_41852_;
    }

    public boolean m_5486_(FluidState p_215665_1_, BlockGetter p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return true;
    }

    public Vec3 m_7000_(BlockGetter p_215663_1_, BlockPos p_215663_2_, FluidState p_215663_3_) {
        return Vec3.f_82478_;
    }

    public int m_6718_(LevelReader p_205569_1_) {
        return 0;
    }

    protected boolean m_6759_() {
        return true;
    }

    protected float m_6752_() {
        return 0.0f;
    }

    public float m_6098_(FluidState p_215662_1_, BlockGetter p_215662_2_, BlockPos p_215662_3_) {
        return 0.0f;
    }

    public float m_7427_(FluidState p_223407_1_) {
        return 0.0f;
    }

    protected BlockState m_5804_(FluidState p_204527_1_) {
        return Blocks.f_50016_.m_49966_();
    }

    public boolean m_7444_(FluidState p_207193_1_) {
        return true;
    }

    public int m_7430_(FluidState p_207192_1_) {
        return 0;
    }

    public VoxelShape m_7999_(FluidState p_215664_1_, BlockGetter p_215664_2_, BlockPos p_215664_3_) {
        return Shapes.m_83040_();
    }

    private static class FluidInfo
    implements IClientFluidTypeExtensions {
        ResourceLocation still;
        ResourceLocation flowing;
        Integer color;

        public FluidInfo(ResourceLocation still, ResourceLocation flowing, Integer color) {
            this.still = still;
            this.flowing = flowing;
            this.color = color;
        }

        public int getTintColor() {
            return this.color != null ? this.color.intValue() : super.getTintColor();
        }

        public ResourceLocation getStillTexture() {
            return this.still;
        }

        public ResourceLocation getFlowingTexture() {
            return this.flowing;
        }
    }
}

