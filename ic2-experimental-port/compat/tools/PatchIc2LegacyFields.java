import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Adds fields required by the 1.19.2 classes retained from IC2 ex119 2.9.40. */
public final class PatchIc2LegacyFields {
    private record FieldSpec(String name, String descriptor, String signature) {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected the patched IC2 staging directory");
        }

        Path root = Path.of(args[0]);
        patch(root.resolve("ic2/core/ref/Ic2Items.class"), new FieldSpec[] {
                item("IRON_CUTTING_BLADE"),
                item("DIAMOND_CUTTING_BLADE"),
                item("STEEL_CUTTING_BLADE"),
                item("TOOL_BOX"),
                item("METER"),
                item("FREQUENCY_TRANSMITTER"),
                item("CROWBAR"),
                item("CHARGING_RE_BATTERY"),
                item("ADVANCED_CHARGING_RE_BATTERY"),
                item("CHARGING_ENERGY_CRYSTAL"),
                item("CHARGING_LAPOTRON_CRYSTAL"),
                item("BATPACK"),
                item("ADVANCED_BATPACK"),
                item("ENERGY_PACK"),
                item("LAPPACK"),
                item("ELECTRIC_HOE"),
                item("CROP_ANALYZER"),
                item("FOAM_SPRAYER"),
                item("ELECTRIC_JETPACK"),
                item("WEEDING_TROWEL"),
                item("SOLAR_HELMET"),
                item("STATIC_BOOTS"),
                item("IODINE_TABLET"),
                item("UPGRADE_KIT"),
                item("CONTAINMENT_BOX"),
                item("FLUID_CELL"),
                item("DYNAMITE"),
                item("DYNAMITE_STICKY"),
                item("REMOTE"),
                item("LUMINATOR_FLAT"),
                item("REFRACTORY_BRICKS"),
                item("COKE"),
                item("COKE_KILN"),
                item("COKE_KILN_HATCH"),
                item("COKE_KILN_GRATE"),
                item("BARREL"),
                item("BOOZE_MUG"),
                item("PIPE"),
                item("COVER"),
                item("ITEM_BUFFER_2"),
                item("TRADING_TERMINAL")
        });
        patch(root.resolve("ic2/core/ref/Ic2Blocks.class"), new FieldSpec[] {
                new FieldSpec("DYNAMITE", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("LUMINATOR_FLAT", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("REFRACTORY_BRICKS", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("COKE_KILN", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("COKE_KILN_HATCH", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("COKE_KILN_GRATE", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("BARREL", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("FLUID_PIPE", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("ITEM_BUFFER_2", "Lnet/minecraft/world/level/block/Block;", null),
                new FieldSpec("TRADING_TERMINAL", "Lnet/minecraft/world/level/block/Block;", null)
        });
        patch(root.resolve("ic2/core/ref/Ic2Entities.class"), new FieldSpec[] {
                new FieldSpec("DYNAMITE", "Lnet/minecraft/world/entity/EntityType;", null)
        });
        patch(root.resolve("ic2/core/ref/Ic2BlockEntities.class"), new FieldSpec[] {
                new FieldSpec("LUMINATOR_FLAT", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("COKE_KILN", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("COKE_KILN_HATCH", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("COKE_KILN_GRATE", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("BARREL", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("FLUID_PIPE", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("ITEM_BUFFER_2", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null),
                new FieldSpec("TRADING_TERMINAL", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", null)
        });
        patch(root.resolve("ic2/core/ref/Ic2SoundEvents.class"), new FieldSpec[] {
                new FieldSpec("ITEM_CROWBAR_USE", "Lnet/minecraft/sounds/SoundEvent;", null),
                new FieldSpec("ITEM_REMOTE_USE", "Lnet/minecraft/sounds/SoundEvent;", null)
        });
        patch(root.resolve("ic2/core/ref/Ic2ScreenHandlers.class"), new FieldSpec[] {
                menu("METER", "ic2/core/item/tool/ContainerMeter"),
                menu("TOOL_BOX", "ic2/core/item/tool/ContainerToolbox"),
                menu("CROP_ANALYZER", "ru/mot/ic2exfidelity/legacy/LegacyContainerCropAnalyzer"),
                menu("CONTAINMENT_BOX", "ru/mot/ic2exfidelity/legacy/LegacyContainerContainmentBox"),
                menu("TRADING_TERMINAL", "ru/mot/ic2exfidelity/legacy/LegacyContainerTradingTerminal")
        });
        patchAdvRecipeIngredients(root.resolve("ic2/core/recipe/AdvRecipe.class"));
        patchRecipeInputBase(root.resolve("ic2/core/recipe/input/RecipeInputBase.class"));
        patchBronzeCableConductor(root.resolve(
                "ic2/core/block/wiring/AbstractCableBlock$Conductor.class"));
    }

    private static FieldSpec item(String name) {
        return new FieldSpec(name, "Lnet/minecraft/world/item/Item;", null);
    }

    private static FieldSpec menu(String name, String container) {
        return new FieldSpec(
                name,
                "Lnet/minecraft/world/inventory/MenuType;",
                "Lnet/minecraft/world/inventory/MenuType<L" + container + ";>;");
    }

    private static void patch(Path classFile, FieldSpec[] requiredFields) throws IOException {
        byte[] original = Files.readAllBytes(classFile);
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        Set<String> present = new HashSet<>();
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public FieldVisitor visitField(
                    int access, String name, String descriptor, String signature, Object value) {
                present.add(name + descriptor);
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public void visitEnd() {
                for (FieldSpec field : requiredFields) {
                    if (present.add(field.name() + field.descriptor())) {
                        FieldVisitor added = super.visitField(
                                Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                                field.name(),
                                field.descriptor(),
                                field.signature(),
                                null);
                        if (added != null) {
                            added.visitEnd();
                        }
                    }
                }
                super.visitEnd();
            }
        };
        reader.accept(visitor, 0);
        Files.write(classFile, writer.toByteArray());
    }

    private static void patchAdvRecipeIngredients(Path classFile) throws IOException {
        byte[] original = Files.readAllBytes(classFile);
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        boolean[] replaced = {false};
        boolean[] remainderReplaced = {false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions) {
                if (name.equals("m_7527_")
                        && descriptor.equals("()Lnet/minecraft/core/NonNullList;")) {
                    replaced[0] = true;
                    return null;
                }
                if (name.equals("getRemainder")
                        && descriptor.equals(
                                "(Lnet/minecraft/world/inventory/CraftingContainer;)"
                                        + "Lnet/minecraft/core/NonNullList;")) {
                    remainderReplaced[0] = true;
                    return null;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                MethodVisitor method = super.visitMethod(
                        Opcodes.ACC_PUBLIC,
                        "m_7527_",
                        "()Lnet/minecraft/core/NonNullList;",
                        "()Lnet/minecraft/core/NonNullList<Lnet/minecraft/world/item/crafting/Ingredient;>;",
                        null);
                method.visitCode();
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "ru/mot/ic2exfidelity/integration/AdvRecipeIngredientCompat",
                        "getIngredients",
                        "(Lic2/core/recipe/AdvRecipe;)Lnet/minecraft/core/NonNullList;",
                        false);
                method.visitInsn(Opcodes.ARETURN);
                method.visitMaxs(1, 1);
                method.visitEnd();
                MethodVisitor remainder = super.visitMethod(
                        Opcodes.ACC_PUBLIC,
                        "getRemainder",
                        "(Lnet/minecraft/world/inventory/CraftingContainer;)"
                                + "Lnet/minecraft/core/NonNullList;",
                        "(Lnet/minecraft/world/inventory/CraftingContainer;)"
                                + "Lnet/minecraft/core/NonNullList<"
                                + "Lnet/minecraft/world/item/ItemStack;>;",
                        null);
                remainder.visitCode();
                remainder.visitVarInsn(Opcodes.ALOAD, 0);
                remainder.visitVarInsn(Opcodes.ALOAD, 1);
                remainder.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "ru/mot/ic2exfidelity/integration/AdvRecipeRemainderCompat",
                        "getRemainder",
                        "(Lic2/core/recipe/AdvRecipe;"
                                + "Lnet/minecraft/world/inventory/CraftingContainer;)"
                                + "Lnet/minecraft/core/NonNullList;",
                        false);
                remainder.visitInsn(Opcodes.ARETURN);
                remainder.visitMaxs(2, 2);
                remainder.visitEnd();
                super.visitEnd();
            }
        };
        reader.accept(visitor, 0);
        if (!replaced[0]) {
            throw new IllegalStateException("Unable to find AdvRecipe ingredient method");
        }
        if (!remainderReplaced[0]) {
            throw new IllegalStateException("Unable to find AdvRecipe remainder method");
        }
        Files.write(classFile, writer.toByteArray());
    }

    private static void patchRecipeInputBase(Path classFile) throws IOException {
        byte[] original = Files.readAllBytes(classFile);
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        boolean[] replaced = {false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions) {
                if (name.equals("getInputs") && descriptor.equals("()Ljava/util/List;")) {
                    replaced[0] = true;
                    return null;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                MethodVisitor method = super.visitMethod(
                        Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
                        "getInputs",
                        "()Ljava/util/List;",
                        "()Ljava/util/List<Lnet/minecraft/world/item/ItemStack;>;",
                        null);
                method.visitCode();
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        "ic2/core/recipe/input/RecipeInputBase",
                        "listStacks",
                        "()Ljava/util/List;",
                        false);
                method.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "ru/mot/ic2exfidelity/integration/RecipeInputCompat",
                        "normalize",
                        "(Lic2/api/recipe/IRecipeInput;Ljava/util/List;)Ljava/util/List;",
                        false);
                method.visitInsn(Opcodes.ARETURN);
                method.visitMaxs(2, 1);
                method.visitEnd();
                super.visitEnd();
            }
        };
        reader.accept(visitor, 0);
        if (!replaced[0]) {
            throw new IllegalStateException("Unable to find RecipeInputBase.getInputs");
        }
        Files.write(classFile, writer.toByteArray());
    }

    private static void patchBronzeCableConductor(Path classFile) throws IOException {
        byte[] original = Files.readAllBytes(classFile);
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        boolean[] lossReplaced = {false};
        boolean[] absorptionReplaced = {false};
        boolean[] insulationBreakdownReplaced = {false};
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions) {
                if (name.equals("getConductionLoss") && descriptor.equals("()D")) {
                    lossReplaced[0] = true;
                    return null;
                }
                if (name.equals("getInsulationEnergyAbsorption")
                        && descriptor.equals("()D")) {
                    absorptionReplaced[0] = true;
                    return null;
                }
                if (name.equals("getInsulationBreakdownEnergy")
                        && descriptor.equals("()D")) {
                    insulationBreakdownReplaced[0] = true;
                    return null;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                writeBronzeConductionLoss(super.visitMethod(
                        Opcodes.ACC_PUBLIC, "getConductionLoss", "()D", null, null));
                writeBronzeInsulationAbsorption(super.visitMethod(
                        Opcodes.ACC_PUBLIC,
                        "getInsulationEnergyAbsorption",
                        "()D",
                        null,
                        null));
                writeLegacyInsulationBreakdown(super.visitMethod(
                        Opcodes.ACC_PUBLIC,
                        "getInsulationBreakdownEnergy",
                        "()D",
                        null,
                        null));
                super.visitEnd();
            }
        };
        reader.accept(visitor, 0);
        if (!lossReplaced[0] || !absorptionReplaced[0]
                || !insulationBreakdownReplaced[0]) {
            throw new IllegalStateException(
                    "Unable to find IC2 cable conductor energy methods");
        }
        Files.write(classFile, writer.toByteArray());
    }

    private static void writeBronzeConductionLoss(MethodVisitor method) {
        org.objectweb.asm.Label regular = new org.objectweb.asm.Label();
        org.objectweb.asm.Label one = new org.objectweb.asm.Label();
        org.objectweb.asm.Label two = new org.objectweb.asm.Label();
        method.visitCode();
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETSTATIC,
                "ic2/core/block/wiring/CableType",
                "bronze",
                "Lic2/core/block/wiring/CableType;");
        method.visitJumpInsn(Opcodes.IF_ACMPNE, regular);
        loadInsulation(method);
        method.visitInsn(Opcodes.ICONST_1);
        method.visitJumpInsn(Opcodes.IF_ICMPEQ, one);
        loadInsulation(method);
        method.visitInsn(Opcodes.ICONST_2);
        method.visitJumpInsn(Opcodes.IF_ICMPEQ, two);
        method.visitLdcInsn(0.7D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(one);
        method.visitLdcInsn(0.65D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(two);
        method.visitLdcInsn(0.6D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(regular);
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/CableType",
                "loss",
                "D");
        method.visitInsn(Opcodes.DRETURN);
        method.visitMaxs(2, 1);
        method.visitEnd();
    }

    private static void writeBronzeInsulationAbsorption(MethodVisitor method) {
        org.objectweb.asm.Label regular = new org.objectweb.asm.Label();
        org.objectweb.asm.Label bronze = new org.objectweb.asm.Label();
        org.objectweb.asm.Label one = new org.objectweb.asm.Label();
        org.objectweb.asm.Label two = new org.objectweb.asm.Label();
        method.visitCode();
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETSTATIC,
                "ic2/core/block/wiring/CableType",
                "plasma",
                "Lic2/core/block/wiring/CableType;");
        method.visitJumpInsn(Opcodes.IF_ACMPNE, bronze);
        method.visitLdcInsn(32769.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(bronze);
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETSTATIC,
                "ic2/core/block/wiring/CableType",
                "bronze",
                "Lic2/core/block/wiring/CableType;");
        method.visitJumpInsn(Opcodes.IF_ACMPNE, regular);
        loadInsulation(method);
        method.visitInsn(Opcodes.ICONST_1);
        method.visitJumpInsn(Opcodes.IF_ICMPEQ, one);
        loadInsulation(method);
        method.visitInsn(Opcodes.ICONST_2);
        method.visitJumpInsn(Opcodes.IF_ICMPEQ, two);
        method.visitLdcInsn(8.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(one);
        method.visitLdcInsn(32.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(two);
        method.visitLdcInsn(128.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(regular);
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/CableType",
                "maxInsulation",
                "I");
        org.objectweb.asm.Label hasInsulation = new org.objectweb.asm.Label();
        method.visitJumpInsn(Opcodes.IFNE, hasInsulation);
        method.visitLdcInsn(2.147483647E9D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(hasInsulation);
        method.visitFieldInsn(
                Opcodes.GETSTATIC,
                "ic2/api/energy/EnergyNet",
                "instance",
                "Lic2/api/energy/EnergyNet;");
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/CableType",
                "capacity",
                "I");
        method.visitIntInsn(Opcodes.SIPUSH, 128);
        org.objectweb.asm.Label highCapacity = new org.objectweb.asm.Label();
        method.visitJumpInsn(Opcodes.IF_ICMPGE, highCapacity);
        loadInsulation(method);
        org.objectweb.asm.Label callPower = new org.objectweb.asm.Label();
        method.visitJumpInsn(Opcodes.GOTO, callPower);
        method.visitLabel(highCapacity);
        loadInsulation(method);
        method.visitInsn(Opcodes.ICONST_1);
        method.visitInsn(Opcodes.IADD);
        method.visitLabel(callPower);
        method.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "ic2/api/energy/EnergyNet",
                "getPowerFromTier",
                "(I)D",
                false);
        method.visitInsn(Opcodes.DRETURN);
        method.visitMaxs(3, 1);
        method.visitEnd();
    }

    private static void writeLegacyInsulationBreakdown(MethodVisitor method) {
        org.objectweb.asm.Label regular = new org.objectweb.asm.Label();
        method.visitCode();
        loadCableType(method);
        method.visitFieldInsn(
                Opcodes.GETSTATIC,
                "ic2/core/block/wiring/CableType",
                "plasma",
                "Lic2/core/block/wiring/CableType;");
        method.visitJumpInsn(Opcodes.IF_ACMPNE, regular);
        method.visitLdcInsn(32769.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitLabel(regular);
        method.visitLdcInsn(9001.0D);
        method.visitInsn(Opcodes.DRETURN);
        method.visitMaxs(2, 1);
        method.visitEnd();
    }

    private static void loadCableType(MethodVisitor method) {
        method.visitVarInsn(Opcodes.ALOAD, 0);
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/AbstractCableBlock$Conductor",
                "this$0",
                "Lic2/core/block/wiring/AbstractCableBlock;");
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/AbstractCableBlock",
                "type",
                "Lic2/core/block/wiring/CableType;");
    }

    private static void loadInsulation(MethodVisitor method) {
        method.visitVarInsn(Opcodes.ALOAD, 0);
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/AbstractCableBlock$Conductor",
                "this$0",
                "Lic2/core/block/wiring/AbstractCableBlock;");
        method.visitFieldInsn(
                Opcodes.GETFIELD,
                "ic2/core/block/wiring/AbstractCableBlock",
                "insulation",
                "I");
    }
}
