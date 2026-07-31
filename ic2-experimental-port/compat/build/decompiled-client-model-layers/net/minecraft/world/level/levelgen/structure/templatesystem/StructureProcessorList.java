/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public class StructureProcessorList {
    private final List<StructureProcessor> f_74422_;

    public StructureProcessorList(List<StructureProcessor> p_74424_) {
        this.f_74422_ = p_74424_;
    }

    public List<StructureProcessor> m_74425_() {
        return this.f_74422_;
    }

    public String toString() {
        return "ProcessorList[" + this.f_74422_ + "]";
    }
}

