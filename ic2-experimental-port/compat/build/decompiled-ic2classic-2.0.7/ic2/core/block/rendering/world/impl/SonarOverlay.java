/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.item.base.features.ISonarProvider;
import ic2.core.platform.rendering.RenderShapes;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.platform.rendering.misc.GLUtils;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class SonarOverlay
implements IWorldOverlay {
    public static final SonarOverlay INSTANCE = new SonarOverlay();
    List<SonarInstance> providers = CollectionUtils.createList();

    public void addSonar(ISonarProvider prov, Supplier<ItemStack> stack) {
        this.providers.add(new SonarInstance(prov, stack));
    }

    @Override
    public void cleanup() {
        this.providers.clear();
    }

    @Override
    public void update(Level world, Player player) {
        if (this.providers.isEmpty()) {
            return;
        }
        this.providers.removeIf(T -> T.update(world));
    }

    @Override
    public void render(Level world, Player player, RenderLevelStageEvent event, Frustum helper) {
        if (this.providers.isEmpty()) {
            return;
        }
        GLUtils.enableHighlight(true);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderUtils.draw(event.getPoseStack(), (B, M) -> {
            B.m_166779_(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.f_85815_);
            long time = Minecraft.m_91087_().f_91073_.m_46467_();
            int m = this.providers.size();
            for (int i = 0; i < m; ++i) {
                this.providers.get(i).render((BufferBuilder)B, (PoseStack)M, helper, time);
            }
        }).m_85914_();
        GLUtils.disableHighlight(true);
    }

    static class SonarInstance {
        private static final int MAX_PROGRESS = 15;
        private static final AABB[] SONAR_EFFECT = SonarInstance.createArray(15);
        ISonarProvider provider;
        Supplier<ItemStack> item;
        List<SonarEntry> entries = new LinkedList<SonarEntry>();
        BlockPos.MutableBlockPos search = new BlockPos.MutableBlockPos();
        BlockPos startPos = null;
        int sonarColor;
        int lastRadius = 0;
        int targetRadius = 0;
        boolean alive = true;

        public SonarInstance(ISonarProvider provider, Supplier<ItemStack> item) {
            this.provider = provider;
            this.item = item;
        }

        public void render(BufferBuilder builder, PoseStack stack, Frustum helper, long time) {
            int rendered = 0;
            for (SonarEntry entry : this.entries) {
                if (!helper.m_113029_(entry.getBox())) continue;
                AABB box = SONAR_EFFECT[Math.min(entry.getAliveTime(time), 15)].m_82338_(entry.getOffset());
                int alpha = Mth.m_14167_((float)(entry.getAliveTime(time) >= 15 ? 200.0f : (float)entry.getAliveTime(time) / 15.0f * 200.0f));
                RenderShapes.renderColorCube(box, this.sonarColor | alpha << 24, (VertexConsumer)builder, stack);
                if (++rendered < 2000) continue;
                break;
            }
        }

        public boolean update(Level world) {
            long time = world.m_46467_();
            Iterator<SonarEntry> iter = this.entries.iterator();
            while (iter.hasNext() && iter.next().shouldBeRemoved(time)) {
                iter.remove();
            }
            if (!this.alive) {
                if (this.targetRadius > this.lastRadius) {
                    ++this.lastRadius;
                    this.updateRadius(this.lastRadius, world);
                }
                return this.entries.isEmpty();
            }
            ItemStack stack = this.item.get();
            if (this.startPos == null) {
                this.startPos = this.provider.getStartPosition(stack);
                this.sonarColor = this.provider.getSonarColor(stack) & 0xFFFFFF;
                this.updateRadius(0, world);
            } else {
                int newRadius = this.provider.getViewRadius(stack);
                if (newRadius > this.targetRadius) {
                    this.targetRadius = newRadius;
                }
                if (this.targetRadius > this.lastRadius) {
                    ++this.lastRadius;
                    this.updateRadius(this.lastRadius, world);
                }
            }
            if (this.provider.isDone(stack)) {
                this.alive = false;
            }
            return false;
        }

        private void updateRadius(int newRadius, Level world) {
            long time = world.m_46467_();
            for (BlockPos pos : Box.withRange(this.startPos, 0).expandSide(Direction.Axis.Y, newRadius).getHollowHorizontalIterator()) {
                this.entries.add(new SonarEntry(this.getHeight(world, pos), time));
            }
        }

        private BlockPos getHeight(Level world, BlockPos pos) {
            this.search.m_122190_((Vec3i)pos);
            Predicate state = Heightmap.Types.MOTION_BLOCKING_NO_LEAVES.m_64299_();
            if (!state.test(world.m_8055_((BlockPos)this.search))) {
                do {
                    this.search.m_122173_(Direction.DOWN);
                } while (!state.test(world.m_8055_((BlockPos)this.search)) && this.search.m_123342_() > 0);
            } else {
                do {
                    this.search.m_122173_(Direction.UP);
                } while (state.test(world.m_8055_((BlockPos)this.search)) && this.search.m_123342_() <= 255);
                this.search.m_122173_(Direction.DOWN);
            }
            return this.search.m_7949_();
        }

        private static AABB[] createArray(int progress) {
            AABB[] data = new AABB[progress + 1];
            for (int i = 0; i <= progress; ++i) {
                data[i] = new AABB(BlockPos.f_121853_).m_82400_(i == progress ? 0.0 : (double)((float)i / (float)progress * 0.5f - 0.5f));
            }
            return data;
        }
    }

    static class SonarEntry {
        long startTime;
        BlockPos offset;
        AABB box;

        public SonarEntry(BlockPos offset, long startTime) {
            this.offset = offset;
            this.startTime = startTime;
            this.box = new AABB(offset);
        }

        public boolean shouldBeRemoved(long currentTime) {
            return currentTime - this.startTime >= 25L;
        }

        public BlockPos getOffset() {
            return this.offset;
        }

        public int getAliveTime(long currentTime) {
            return (int)Math.max(0L, currentTime - this.startTime);
        }

        public AABB getBox() {
            return this.box;
        }
    }
}

