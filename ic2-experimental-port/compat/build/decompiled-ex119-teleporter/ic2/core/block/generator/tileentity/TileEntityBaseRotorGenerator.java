/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.api.tile.IRotorProvider;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityBaseRotorGenerator
extends TileEntityBaseGenerator
implements IRotorProvider {
    private static final float rotationSpeed = 0.4f;
    private static final ResourceLocation rotorTexture = new ResourceLocation("ic2", "textures/items/rotor/iron_rotor_model.png");
    private final int rotorDiameter;
    private float angle = 0.0f;
    private long lastcheck;

    public TileEntityBaseRotorGenerator(BlockEntityType<? extends TileEntityBaseRotorGenerator> blockEntityType, BlockPos blockPos, BlockState blockState, double d, int n, int n2, int n3) {
        super(blockEntityType, blockPos, blockState, d, n, n2);
        this.rotorDiameter = n3;
    }

    @Override
    public int getRotorDiameter() {
        return this.rotorDiameter;
    }

    protected abstract boolean shouldRotorRotate();

    protected float rotorSpeedFactor() {
        return 1.0f;
    }

    @Override
    public float getAngle() {
        if (this.shouldRotorRotate()) {
            this.angle += (float)(System.currentTimeMillis() - this.lastcheck) * 0.4f * this.rotorSpeedFactor();
            this.angle %= 360.0f;
        }
        this.lastcheck = System.currentTimeMillis();
        return this.angle;
    }

    @Override
    public ResourceLocation getRotorRenderTexture() {
        return rotorTexture;
    }
}

