/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import java.util.Arrays;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class JigsawBlockEntity
extends BlockEntity {
    public static final String f_155599_ = "target";
    public static final String f_155600_ = "pool";
    public static final String f_155601_ = "joint";
    public static final String f_155602_ = "name";
    public static final String f_155603_ = "final_state";
    private ResourceLocation f_59411_ = new ResourceLocation("empty");
    private ResourceLocation f_59412_ = new ResourceLocation("empty");
    private ResourceKey<StructureTemplatePool> f_59413_ = ResourceKey.m_135785_(Registry.f_122884_, new ResourceLocation("empty"));
    private JointType f_59414_ = JointType.ROLLABLE;
    private String f_59415_ = "minecraft:air";

    public JigsawBlockEntity(BlockPos p_155605_, BlockState p_155606_) {
        super(BlockEntityType.f_58910_, p_155605_, p_155606_);
    }

    public ResourceLocation m_59442_() {
        return this.f_59411_;
    }

    public ResourceLocation m_59443_() {
        return this.f_59412_;
    }

    public ResourceKey<StructureTemplatePool> m_222765_() {
        return this.f_59413_;
    }

    public String m_59445_() {
        return this.f_59415_;
    }

    public JointType m_59446_() {
        return this.f_59414_;
    }

    public void m_59435_(ResourceLocation p_59436_) {
        this.f_59411_ = p_59436_;
    }

    public void m_59438_(ResourceLocation p_59439_) {
        this.f_59412_ = p_59439_;
    }

    public void m_222763_(ResourceKey<StructureTemplatePool> p_222764_) {
        this.f_59413_ = p_222764_;
    }

    public void m_59431_(String p_59432_) {
        this.f_59415_ = p_59432_;
    }

    public void m_59424_(JointType p_59425_) {
        this.f_59414_ = p_59425_;
    }

    @Override
    protected void m_183515_(CompoundTag p_187504_) {
        super.m_183515_(p_187504_);
        p_187504_.m_128359_(f_155602_, this.f_59411_.toString());
        p_187504_.m_128359_(f_155599_, this.f_59412_.toString());
        p_187504_.m_128359_(f_155600_, this.f_59413_.m_135782_().toString());
        p_187504_.m_128359_(f_155603_, this.f_59415_);
        p_187504_.m_128359_(f_155601_, this.f_59414_.m_7912_());
    }

    @Override
    public void m_142466_(CompoundTag p_155608_) {
        super.m_142466_(p_155608_);
        this.f_59411_ = new ResourceLocation(p_155608_.m_128461_(f_155602_));
        this.f_59412_ = new ResourceLocation(p_155608_.m_128461_(f_155599_));
        this.f_59413_ = ResourceKey.m_135785_(Registry.f_122884_, new ResourceLocation(p_155608_.m_128461_(f_155600_)));
        this.f_59415_ = p_155608_.m_128461_(f_155603_);
        this.f_59414_ = JointType.m_59457_(p_155608_.m_128461_(f_155601_)).orElseGet(() -> JigsawBlock.m_54250_(this.m_58900_()).m_122434_().m_122479_() ? JointType.ALIGNED : JointType.ROLLABLE);
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    public void m_59420_(ServerLevel p_59421_, int p_59422_, boolean p_59423_) {
        BlockPos $$3 = this.m_58899_().m_121945_(this.m_58900_().m_61143_(JigsawBlock.f_54222_).m_122625_());
        Registry<StructureTemplatePool> $$4 = p_59421_.m_5962_().m_175515_(Registry.f_122884_);
        Holder<StructureTemplatePool> $$5 = $$4.m_206081_(this.f_59413_);
        JigsawPlacement.m_227203_(p_59421_, $$5, this.f_59412_, p_59422_, $$3, p_59423_);
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }

    public static final class JointType
    extends Enum<JointType>
    implements StringRepresentable {
        public static final /* enum */ JointType ROLLABLE = new JointType("rollable");
        public static final /* enum */ JointType ALIGNED = new JointType("aligned");
        private final String f_59449_;
        private static final /* synthetic */ JointType[] $VALUES;

        public static JointType[] values() {
            return (JointType[])$VALUES.clone();
        }

        public static JointType valueOf(String p_59463_) {
            return Enum.valueOf(JointType.class, p_59463_);
        }

        private JointType(String p_59455_) {
            this.f_59449_ = p_59455_;
        }

        @Override
        public String m_7912_() {
            return this.f_59449_;
        }

        public static Optional<JointType> m_59457_(String p_59458_) {
            return Arrays.stream(JointType.values()).filter(p_59461_ -> p_59461_.m_7912_().equals(p_59458_)).findFirst();
        }

        public Component m_155610_() {
            return Component.m_237115_("jigsaw_block.joint." + this.f_59449_);
        }

        private static /* synthetic */ JointType[] m_155611_() {
            return new JointType[]{ROLLABLE, ALIGNED};
        }

        static {
            $VALUES = JointType.m_155611_();
        }
    }
}

