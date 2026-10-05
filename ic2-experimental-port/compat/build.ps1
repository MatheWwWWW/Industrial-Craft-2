param(
    [string]$DependencyRoot,
    [switch]$SkipInstall
)

$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
$portRoot = Split-Path -Parent $projectRoot
$minecraftRoot = Split-Path -Parent $portRoot
if (-not $DependencyRoot) {
    $DependencyRoot = $minecraftRoot
}
$javaBin = Join-Path $DependencyRoot "runtime\java-runtime-gamma\windows\java-runtime-gamma\bin"
$java = Join-Path $javaBin "java.exe"
$javac = Join-Path $javaBin "javac.exe"
$jar = Join-Path $javaBin "jar.exe"
$buildRoot = Join-Path $projectRoot "build"
$classesDir = Join-Path $buildRoot "classes"
$depsDir = Join-Path $buildRoot "compile-deps"
$patchedStage = Join-Path $buildRoot "patched-ic2-stage"
$patcherDir = Join-Path $buildRoot "patcher-classes"
$outputJar = Join-Path $buildRoot "IC2-Experimental-Fidelity-0.1.0.jar"
$installedJar = Join-Path $minecraftRoot "mods\IC2-Experimental-Fidelity-0.1.0.jar"
$upstreamIc2Jar = Join-Path $portRoot "upstream\industrialcraft-2-2.9.162+ex119-1.19.2-forge.jar"
$patchedIc2Jar = Join-Path $buildRoot "industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar"
$installedUpstreamIc2Jar = Join-Path $minecraftRoot "mods\industrialcraft-2-2.9.162+ex119-1.19.2-forge.jar"
$installedPatchedIc2Jar = Join-Path $minecraftRoot "mods\industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar"
$baseQuarantine = Join-Path $portRoot "quarantine\unmodified-base-not-loaded"

function Merge-SourceLanguages([string]$DestinationRoot, [string]$Namespace) {
    $sourceAssets = Join-Path $projectRoot 'src\main\resources\assets'
    $namespaceDirs = Get-ChildItem -LiteralPath $sourceAssets -Directory
    foreach ($namespaceDir in $namespaceDirs) {
        if ($Namespace -and $namespaceDir.Name -ne $Namespace) { continue }
        $sourceLangDir = Join-Path $namespaceDir.FullName 'lang'
        if (-not (Test-Path -LiteralPath $sourceLangDir)) { continue }
        foreach ($sourceLang in (Get-ChildItem -LiteralPath $sourceLangDir -Filter '*.json' -File)) {
            $targetDir = Join-Path $DestinationRoot "assets\$($namespaceDir.Name)\lang"
            New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
            $target = Join-Path $targetDir $sourceLang.Name
            $merged = [Collections.Specialized.OrderedDictionary]::new([StringComparer]::Ordinal)
            if (Test-Path -LiteralPath $target) {
                $original = Get-Content -Raw -Encoding UTF8 -LiteralPath $target | ConvertFrom-Json -AsHashtable
                foreach ($property in $original.GetEnumerator()) {
                    $merged[$property.Key] = $property.Value
                }
            }
            $overlay = Get-Content -Raw -Encoding UTF8 -LiteralPath $sourceLang.FullName | ConvertFrom-Json -AsHashtable
            foreach ($property in $overlay.GetEnumerator()) {
                $merged[$property.Key] = $property.Value
            }
            [IO.File]::WriteAllText($target, ($merged | ConvertTo-Json -Depth 4) + "`n",
                [Text.UTF8Encoding]::new($false))
        }
    }
}

$resolvedProject = [IO.Path]::GetFullPath($projectRoot)
foreach ($path in @($buildRoot, $classesDir, $depsDir, $patchedStage, $patcherDir)) {
    if (-not [IO.Path]::GetFullPath($path).StartsWith($resolvedProject, [StringComparison]::OrdinalIgnoreCase)) {
        throw "Refusing to clean a path outside the project: $path"
    }
}

foreach ($path in @($classesDir, $depsDir, $patchedStage, $patcherDir)) {
    if (Test-Path -LiteralPath $path) {
        Remove-Item -LiteralPath $path -Recurse -Force
    }
    New-Item -ItemType Directory -Path $path -Force | Out-Null
}

$sourceClasspathEntries = @(
    "libraries\net\minecraft\client\1.19.2-20220805.130853\client-1.19.2-20220805.130853-srg.jar",
    "libraries\net\minecraftforge\forge\1.19.2-43.5.2\forge-1.19.2-43.5.2-universal.jar",
    "libraries\net\minecraftforge\fmlcore\1.19.2-43.5.2\fmlcore-1.19.2-43.5.2.jar",
    "libraries\net\minecraftforge\fmlloader\1.19.2-43.5.2\fmlloader-1.19.2-43.5.2.jar",
    "libraries\net\minecraftforge\javafmllanguage\1.19.2-43.5.2\javafmllanguage-1.19.2-43.5.2.jar",
    "libraries\net\minecraftforge\eventbus\6.0.3\eventbus-6.0.3.jar",
    "libraries\net\minecraftforge\forgespi\6.0.0\forgespi-6.0.0.jar",
    "libraries\org\spongepowered\mixin\0.8.5\mixin-0.8.5.jar",
    "libraries\com\mojang\brigadier\1.0.18\brigadier-1.0.18.jar",
    "libraries\com\mojang\datafixerupper\5.0.28\datafixerupper-5.0.28.jar",
    "libraries\com\mojang\authlib\3.11.49\authlib-3.11.49.jar",
    "libraries\com\google\guava\guava\31.0.1-jre\guava-31.0.1-jre.jar",
    "libraries\io\netty\netty-buffer\4.1.77.Final\netty-buffer-4.1.77.Final.jar",
    "libraries\io\netty\netty-common\4.1.77.Final\netty-common-4.1.77.Final.jar",
    "libraries\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar",
    "mods\AdditionalEnchantedMiner-1.19.2-1192.4.65.jar",
    "mods\jei-1.19.2-forge-11.8.1.1034.jar"
) | ForEach-Object {
    $dependency = Join-Path $DependencyRoot $_
    if ($_ -like 'mods\*' -and -not (Test-Path -LiteralPath $dependency)) {
        $dependency = Join-Path (Join-Path $portRoot 'smoke-instance') $_
    }
    $dependency
}
$sourceClasspathEntries += @(
    $upstreamIc2Jar,
    (Join-Path $portRoot 'upstream\industrialcraft-2-2.9.40+ex119-1.19.2-forge.jar')
)

$classpathEntries = foreach ($dependency in $sourceClasspathEntries) {
    if (-not (Test-Path -LiteralPath $dependency -PathType Leaf)) {
        throw "Missing compile dependency: $dependency"
    }
    $localDependency = Join-Path $depsDir (Split-Path -Leaf $dependency)
    Copy-Item -LiteralPath $dependency -Destination $localDependency -Force
    $localDependency
}

$classpath = $classpathEntries -join ";"
$sources = Get-ChildItem -LiteralPath (Join-Path $projectRoot "src\main\java") -Recurse -File -Filter "*.java" |
    Select-Object -ExpandProperty FullName

& $javac -proc:none -encoding UTF-8 -source 17 -target 17 -classpath $classpath -d $classesDir $sources
if ($LASTEXITCODE -ne 0) {
    throw "javac failed with exit code $LASTEXITCODE"
}

$legacyJar = Join-Path $portRoot "upstream\industrialcraft-2-2.9.40+ex119-1.19.2-forge.jar"
$experimental112Jar = Join-Path $portRoot "quarantine\ic2-classic-only\industrialcraft-2-2.8.222-ex112.jar"
$legacyEntries = @(
    "ic2/api/block/IIdProvider.class",
    "ic2/core/item/crafting/BlockCuttingBlade.class",
    "ic2/core/item/crafting/BlockCuttingBlade`$1.class",
    "ic2/core/item/tool/ContainerMeter.class",
    "ic2/core/item/tool/ContainerMeter`$1.class",
    "ic2/core/item/tool/ContainerMeter`$Mode.class",
    "ic2/core/item/tool/ContainerToolbox.class",
    "ic2/core/item/tool/GuiToolbox.class",
    "ic2/core/item/tool/GuiToolMeter.class",
    "ic2/core/item/tool/GuiToolMeter`$1.class",
    "ic2/core/item/tool/HandHeldMeter.class",
    "ic2/core/item/tool/HandHeldToolbox.class",
    "ic2/core/item/tool/ItemFrequencyTransmitter.class",
    "ic2/core/item/tool/ItemToolbox.class",
    "ic2/core/item/tool/ItemToolCrowbar.class",
    "ic2/core/item/tool/ItemToolMeter.class",
    "ic2/core/item/type/BlockCuttingBladeType.class",
    "ic2/core/slot/SlotBoxable.class",
    "ic2/core/util/RotationUtil.class",
    "ic2/core/util/RotationUtil`$1.class",
    "assets/ic2/models/item/iron_cutting_blade.json",
    "assets/ic2/models/item/diamond_cutting_blade.json",
    "assets/ic2/models/item/steel_cutting_blade.json",
    "assets/ic2/models/item/tool_box.json",
    "assets/ic2/models/item/tool/tool_box/close.json",
    "assets/ic2/models/item/tool/tool_box/open.json",
    "assets/ic2/models/item/meter.json",
    "assets/ic2/models/item/crowbar.json",
    "assets/ic2/models/item/frequency_transmitter.json",
    "assets/ic2/sounds/tools/crowbar.ogg",
    "assets/ic2/textures/items/dynamite.png",
    "assets/ic2/textures/items/dynamite_sticky.png",
    "assets/ic2/textures/items/tool/remote.png",
    "assets/ic2/textures/blocks/explosive/dynamite.png",
    "assets/ic2/textures/blocks/explosive/dynamite_remote.png",
    "assets/ic2/textures/mob_effect/radiation.png",
    "assets/ic2/models/block/be/cf/obscured_wall.json",
    "assets/ic2/models/block/cf/wall_light_gray.json",
    "assets/ic2/models/item/coolant_bucket.json",
    "assets/ic2/models/item/creosote_bucket.json",
    "assets/ic2/models/item/heavy_water_bucket.json",
    "assets/ic2/models/item/hot_coolant_bucket.json",
    "assets/ic2/models/item/hot_water_bucket.json",
    "assets/ic2/models/item/uu_matter_bucket.json",
    "assets/ic2/models/item/weed_ex_bucket.json",
    "assets/ic2/textures/items/bucket/coolant.png",
    "assets/ic2/textures/items/bucket/creosote.png",
    "assets/ic2/textures/items/bucket/heavy_water.png",
    "assets/ic2/textures/items/bucket/hot_coolant.png",
    "assets/ic2/textures/items/bucket/hot_water.png",
    "assets/ic2/textures/items/bucket/uu_matter.png",
    "assets/ic2/textures/items/bucket/weed_ex.png",
    "ic2/sounds/Tools/eat.ogg",
    "ic2/sounds/Tools/dynamiteomote.ogg"
)
Push-Location $patchedStage
try {
    $extractArgs = @("xf", $legacyJar) + $legacyEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Unable to extract legacy IC2 compatibility classes"
    }
} finally {
    Pop-Location
}

# The final ex119 jar retained the old Compact Item Buffer and Burning Box
# models but omitted these exact 2.8.222 bitmaps.  Extract them from the
# installed Experimental jar instead of silently shipping missing textures.
$legacyMachineTextureEntries = @(
    'assets/ic2/textures/blocks/machine/misc/item_buffer_2.png',
    'assets/ic2/textures/blocks/steam/burning_box_front.png',
    'assets/ic2/textures/blocks/steam/burning_box_front_active.png',
    'assets/ic2/textures/blocks/steam/burning_box_front_active.png.mcmeta',
    'assets/ic2/textures/blocks/steam/burning_box_top.png',
    'assets/ic2/textures/blocks/steam/burning_box_top_active.png'
)
$legacyMachineTextureIndex = & $jar tf $experimental112Jar
$missingLegacyMachineTextures = $legacyMachineTextureEntries | Where-Object {
    $_ -notin $legacyMachineTextureIndex
}
if ($legacyMachineTextureEntries.Count -ne 6 -or $missingLegacyMachineTextures) {
    throw "Missing exact 2.8.222 machine textures: $($missingLegacyMachineTextures -join ', ')"
}
Push-Location $patchedStage
try {
    & $jar (@('xf', $experimental112Jar) + $legacyMachineTextureEntries)
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract exact 2.8.222 machine textures'
    }
} finally {
    Pop-Location
}

# The BE loader derives its backing model by replacing block/be/ with block/.
# Copying the loader JSON to that derived path makes the model reference itself
# and crashes model baking with StackOverflowError. The wall's persisted default
# is LIGHT_GRAY, so use the corresponding ordinary CF model as its backing mesh.
$obscuredSource = Join-Path $patchedStage 'assets\ic2\models\block\cf\wall_light_gray.json'
$obscuredTarget = Join-Path $patchedStage 'assets\ic2\models\block\cf\obscured_wall.json'
if (-not (Test-Path -LiteralPath $obscuredSource -PathType Leaf)) {
    throw "Missing obscured-wall backing model: $obscuredSource"
}
New-Item -ItemType Directory -Path (Split-Path -Parent $obscuredTarget) -Force | Out-Null
Copy-Item -LiteralPath $obscuredSource -Destination $obscuredTarget -Force

if (-not (Test-Path -LiteralPath $upstreamIc2Jar -PathType Leaf)) {
    throw "Missing unmodified IC2 base: $upstreamIc2Jar"
}
$patchedClassEntries = @(
    "ic2/core/ref/Ic2Items.class",
    "ic2/core/ref/Ic2Blocks.class",
    "ic2/core/ref/Ic2BlockEntities.class",
    "ic2/core/ref/Ic2Entities.class",
    "ic2/core/ref/Ic2SoundEvents.class",
    "ic2/core/ref/Ic2ScreenHandlers.class",
    "ic2/core/recipe/AdvRecipe.class",
    "ic2/core/recipe/input/RecipeInputBase.class",
    "ic2/core/block/wiring/AbstractCableBlock`$Conductor.class",
    "assets/ic2/lang/en_us.json",
    "assets/ic2/lang/ru_ru.json",
    "assets/ic2/sounds.json"
)
Push-Location $patchedStage
try {
    $extractArgs = @("xf", $upstreamIc2Jar) + $patchedClassEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Unable to extract IC2 registry classes for patching"
    }
} finally {
    Pop-Location
}

$soundsJsonPath = Join-Path $patchedStage 'assets\ic2\sounds.json'
$soundsJson = Get-Content -Raw -LiteralPath $soundsJsonPath | ConvertFrom-Json
$soundsJson | Add-Member -NotePropertyName 'item.crowbar.use' -NotePropertyValue ([pscustomobject]@{
    sounds = @('ic2:tools/crowbar')
}) -Force
$soundsJson | Add-Member -NotePropertyName 'item.iodine_tablet.eat' -NotePropertyValue ([pscustomobject]@{
    sounds = @('ic2:tools/eat')
}) -Force
$soundsJson | Add-Member -NotePropertyName 'item.remote.use' -NotePropertyValue ([pscustomobject]@{
    sounds = @('ic2:tools/dynamiteomote')
}) -Force
$soundsJson | Add-Member -NotePropertyName 'item.relocator.teleport' -NotePropertyValue ([pscustomobject]@{
    sounds = @('ic2:machines/teleport')
}) -Force
$soundsJsonText = $soundsJson | ConvertTo-Json -Depth 10
[IO.File]::WriteAllText($soundsJsonPath, $soundsJsonText, [Text.UTF8Encoding]::new($false))

$patchedIodineSound = Join-Path $patchedStage 'assets\ic2\sounds\tools'
New-Item -ItemType Directory -Path $patchedIodineSound -Force | Out-Null
Copy-Item -LiteralPath (Join-Path $patchedStage 'ic2\sounds\Tools\eat.ogg') `
    -Destination (Join-Path $patchedIodineSound 'eat.ogg') -Force
Copy-Item -LiteralPath (Join-Path $patchedStage 'ic2\sounds\Tools\dynamiteomote.ogg') `
    -Destination (Join-Path $patchedIodineSound 'dynamiteomote.ogg') -Force

# API- and implementation-compatible restored classes must live in the IC2
# base jar. Keeping them out of the companion avoids a split ic2 package and
# makes legacy addons resolve their original binary names.
$restoredIc2Classes = Join-Path $classesDir 'ic2'
if (Test-Path -LiteralPath $restoredIc2Classes -PathType Container) {
    Copy-Item -LiteralPath $restoredIc2Classes -Destination $patchedStage -Recurse -Force
    Remove-Item -LiteralPath $restoredIc2Classes -Recurse -Force
}

# The final upstream jar still carries dormant 1.12-era dynamite models. Since
# resources in the ic2 namespace win over the companion jar on this pack's
# load order, mirror the corrected 1.19 model paths into the patched base too.
$restoredDynamiteVisuals = @(
    'assets\ic2\blockstates\dynamite.json',
    'assets\ic2\models\block\explosive\dynamite.json',
    'assets\ic2\models\block\explosive\dynamite_remote.json',
    'assets\ic2\models\block\explosive\dynamite_wall.json',
    'assets\ic2\models\block\explosive\dynamite_remote_wall.json',
    'assets\ic2\models\item\dynamite.json',
    'assets\ic2\models\item\dynamite_sticky.json',
    'assets\ic2\models\item\remote.json',
    # GuiParser uses the IC2 classloader rather than the resource-pack manager,
    # so addon guidef XML must also be visible from the patched IC2 jar.
    'assets\advanced_solars\guidef\molecular_transformer.xml',
    'assets\ic2\guidef\solar_panel_mv.xml',
    'assets\ic2\guidef\solar_panel_hv.xml',
    'assets\ic2\guidef\plasmafier.xml',
    'assets\ic2\guidef\rare_earth_extractor.xml'
)
foreach ($relativePath in $restoredDynamiteVisuals) {
    $sourcePath = Join-Path (Join-Path $projectRoot 'src\main\resources') $relativePath
    $destinationPath = Join-Path $patchedStage $relativePath
    New-Item -ItemType Directory -Path (Split-Path -Parent $destinationPath) -Force | Out-Null
    Copy-Item -LiteralPath $sourcePath -Destination $destinationPath -Force
}

# Keep all upstream IC2 strings and make the local translations independent
# of which jar wins resource loading in the shared ic2 namespace.
Merge-SourceLanguages $patchedStage 'ic2'

$asmJar = Join-Path $DependencyRoot "libraries\org\ow2\asm\asm\9.6\asm-9.6.jar"
$patcherSource = Join-Path $projectRoot "tools\PatchIc2LegacyFields.java"
& $javac -proc:none -encoding UTF-8 -source 17 -target 17 -classpath $asmJar -d $patcherDir $patcherSource
if ($LASTEXITCODE -ne 0) {
    throw "Unable to compile the IC2 field patcher"
}
& $java -classpath "$patcherDir;$asmJar" PatchIc2LegacyFields $patchedStage
if ($LASTEXITCODE -ne 0) {
    throw "Unable to patch IC2 legacy registry fields"
}

Copy-Item -LiteralPath $upstreamIc2Jar -Destination $patchedIc2Jar -Force
& $jar uf $patchedIc2Jar -C $patchedStage .
if ($LASTEXITCODE -ne 0) {
    throw "Unable to create the patched IC2 working jar"
}

Copy-Item -Path (Join-Path $projectRoot "src\main\resources\*") -Destination $classesDir -Recurse -Force

$ic2CuuMatterJar = Join-Path $portRoot 'quarantine\ic2-classic-only\ic2cuumatter-1.19.2-1.2.2.jar'
$ic2CuuMatterAssets = @(
    'assets/ic2cuumatter/lang/en_us.json',
    'assets/ic2cuumatter/lang/no_no.json',
    'assets/ic2cuumatter/textures/gui/plasmafier.png',
    'ic2cuumatter-icon.png'
)
Push-Location $classesDir
try {
    $extractArgs = @('xf', $ic2CuuMatterJar) + $ic2CuuMatterAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 C.U.U. Matter assets'
    }
} finally {
    Pop-Location
}

# The installed Gravisuit 2.2 jar targets IC2 Classic and cannot link against
# IC2 Experimental. Its replacement keeps the same mod/registry ids and reuses
# the original author-provided assets verbatim.
$gravisuitJar = Join-Path $portRoot 'quarantine\ic2-classic-only\gravisuit-2.2.jar'
$gravisuitAssetEntries = & $jar tf $gravisuitJar | Where-Object {
    $_ -match '^assets/gravisuit/.+[^/]$'
}
if ($gravisuitAssetEntries.Count -ne 51) {
    throw "Expected 51 Gravisuit 2.2 assets, found $($gravisuitAssetEntries.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $gravisuitJar) + $gravisuitAssetEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract Gravisuit 2.2 compatibility assets'
    }
} finally {
    Pop-Location
}

# Advanced Solar Panels 2.0.2 also targets IC2 Classic.  Its replacement keeps
# the original namespace and copies the author-provided textures and language
# files verbatim.  The original custom model provider is replaced with normal
# 1.19 JSON models generated from those exact textures.
$advancedSolarsJar = Join-Path $portRoot 'quarantine\ic2-classic-only\advancedsolars-2.0.2.jar'
$advancedSolarsAssetEntries = & $jar tf $advancedSolarsJar | Where-Object {
    $_ -match '^assets/advanced_solars/.+[^/]$'
}
if ($advancedSolarsAssetEntries.Count -ne 42) {
    throw "Expected 42 Advanced Solars 2.0.2 assets, found $($advancedSolarsAssetEntries.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $advancedSolarsJar) + $advancedSolarsAssetEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract Advanced Solars 2.0.2 compatibility assets'
    }
} finally {
    Pop-Location
}

$advancedSolarsMaterials = @(
    'sunnarium',
    'sunnarium_alloy',
    'irradiant_uranium',
    'enriched_sunnarium',
    'enriched_sunnarium_alloy',
    'irradiant_glass_pane',
    'iridium_iron_plate',
    'reinforced_iridium_iron_plate',
    'irradiant_reinforced_plate',
    'sunnarium_part',
    'iridium_ingot',
    'mt_core'
)
$advancedSolarsItemModelDir = Join-Path $classesDir 'assets\advanced_solars\models\item'
New-Item -ItemType Directory -Path $advancedSolarsItemModelDir -Force | Out-Null
foreach ($id in $advancedSolarsMaterials) {
    $model = "{`n  `"parent`": `"minecraft:item/generated`",`n  `"textures`": {`n    `"layer0`": `"advanced_solars:item/materials/$id`"`n  }`n}`n"
    [IO.File]::WriteAllText(
        (Join-Path $advancedSolarsItemModelDir "$id.json"),
        $model,
        [Text.UTF8Encoding]::new($false))
}

$advancedSolarsHelmetTextures = [ordered]@{
    'advanced_solar_helmet' = 'advanced'
    'hybrid_solar_helmet' = 'hybrid'
    'ultimate_hybrid_solar_helmet' = 'ultimate_hybrid'
}
foreach ($entry in $advancedSolarsHelmetTextures.GetEnumerator()) {
    $model = "{`n  `"parent`": `"minecraft:item/generated`",`n  `"textures`": {`n    `"layer0`": `"advanced_solars:item/solar_helmets/$($entry.Value)`"`n  }`n}`n"
    [IO.File]::WriteAllText(
        (Join-Path $advancedSolarsItemModelDir "$($entry.Key).json"),
        $model,
        [Text.UTF8Encoding]::new($false))
}

$advancedSolarsPanels = @(
    'advanced_solar_panel',
    'hybrid_solar_panel',
    'ultimate_hybrid_solar_panel'
)
$advancedSolarsModelBlocks = $advancedSolarsPanels + @('molecular_transformer')
$advancedSolarsBlockStateDir = Join-Path $classesDir 'assets\advanced_solars\blockstates'
$advancedSolarsBlockModelDir = Join-Path $classesDir 'assets\advanced_solars\models\block'
New-Item -ItemType Directory -Path $advancedSolarsBlockStateDir -Force | Out-Null
New-Item -ItemType Directory -Path $advancedSolarsBlockModelDir -Force | Out-Null
foreach ($id in $advancedSolarsModelBlocks) {
    $blockModel = "{`n  `"parent`": `"minecraft:block/cube_bottom_top`",`n  `"textures`": {`n    `"side`": `"advanced_solars:block/$id/side`",`n    `"top`": `"advanced_solars:block/$id/top`",`n    `"bottom`": `"advanced_solars:block/$id/bottom`"`n  }`n}`n"
    [IO.File]::WriteAllText(
        (Join-Path $advancedSolarsBlockModelDir "$id.json"),
        $blockModel,
        [Text.UTF8Encoding]::new($false))
    $itemModel = "{`n  `"parent`": `"advanced_solars:block/$id`"`n}`n"
    [IO.File]::WriteAllText(
        (Join-Path $advancedSolarsItemModelDir "$id.json"),
        $itemModel,
        [Text.UTF8Encoding]::new($false))
    $blockState = "{`n  `"variants`": {`n    `"active=false`": { `"model`": `"advanced_solars:block/$id`" },`n    `"active=true`": { `"model`": `"advanced_solars:block/$id`" }`n  }`n}`n"
    [IO.File]::WriteAllText(
        (Join-Path $advancedSolarsBlockStateDir "$id.json"),
        $blockState,
        [Text.UTF8Encoding]::new($false))
}

# These recipes are direct JSON translations of entries present in the
# 2.8.222 shaped, shapeless and furnace configuration files.  The early ex119
# build still contains those translations; the final ex119 jar changed or
# dropped them.  Keep the list explicit so newer 1.19-only recipes (raw ores,
# mangrove, amethyst, rubber building blocks, etc.) are not imported by
# accident.
$legacyRecipeEntries = @(
    "data/ic2/recipes/shaped/advanced_circuit.json",
    "data/ic2/recipes/shaped/advanced_circuit_vertical.json",
    "data/ic2/recipes/shaped/circuit.json",
    "data/ic2/recipes/shaped/circuit_vertical.json",
    "data/ic2/recipes/shaped/ejector_upgrade.json",
    "data/ic2/recipes/shaped/ejector_upgrade_9.json",
    "data/ic2/recipes/shaped/electric_motor.json",
    "data/ic2/recipes/shaped/electric_motor_vertical.json",
    "data/ic2/recipes/shaped/fluid_ejector_upgrade.json",
    "data/ic2/recipes/shaped/fluid_ejector_upgrade_9.json",
    "data/ic2/recipes/shaped/fluid_pulling_upgrade.json",
    "data/ic2/recipes/shaped/fluid_pulling_upgrade_9.json",
    "data/ic2/recipes/shaped/forge_hammer.json",
    "data/ic2/recipes/shaped/forge_hammer_2.json",
    "data/ic2/recipes/shaped/iridium_neutron_reflector.json",
    "data/ic2/recipes/shaped/iridium_neutron_reflector_vertical.json",
    "data/ic2/recipes/shaped/iron_ingot_from_coin.json",
    "data/ic2/recipes/shaped/iron_ingot_from_machine.json",
    "data/ic2/recipes/shaped/mox_1.json",
    "data/ic2/recipes/shaped/mox_2.json",
    "data/ic2/recipes/shaped/mox_3.json",
    "data/ic2/recipes/shaped/mox_4.json",
    "data/ic2/recipes/shaped/overclocker_upgrade_12.json",
    "data/ic2/recipes/shaped/overclocker_upgrade_2.json",
    "data/ic2/recipes/shaped/overclocker_upgrade_6.json",
    "data/ic2/recipes/shaped/plant_ball_1.json",
    "data/ic2/recipes/shaped/plant_ball_2.json",
    "data/ic2/recipes/shaped/plant_ball_3.json",
    "data/ic2/recipes/shaped/plant_ball_4.json",
    "data/ic2/recipes/shaped/plant_ball_5.json",
    "data/ic2/recipes/shaped/plant_ball_6.json",
    "data/ic2/recipes/shaped/plant_ball_7.json",
    "data/ic2/recipes/shaped/pulling_upgrade.json",
    "data/ic2/recipes/shaped/pulling_upgrade_9.json",
    "data/ic2/recipes/shaped/quad_mox_fuel_rod.json",
    "data/ic2/recipes/shaped/quad_mox_fuel_rod_from_dual.json",
    "data/ic2/recipes/shaped/quad_uranium_fuel_rod.json",
    "data/ic2/recipes/shaped/quad_uranium_fuel_rod_from_dual.json",
    "data/ic2/recipes/shaped/reactor_access_hatch.json",
    "data/ic2/recipes/shaped/redstone_inverter_upgrade.json",
    "data/ic2/recipes/shaped/redstone_inverter_upgrade_9.json",
    "data/ic2/recipes/shaped/rtg_pellet.json",
    "data/ic2/recipes/shaped/rtg_pellet_2.json",
    "data/ic2/recipes/shaped/rtg_pellet_vertical.json",
    "data/ic2/recipes/shaped/rtg_pellet_vertical_2.json",
    "data/ic2/recipes/shaped/small_iron_dust.json",
    "data/ic2/recipes/shaped/small_iron_dust_2.json",
    "data/ic2/recipes/shaped/steam_repressurizer_from_iron_tank.json",
    "data/ic2/recipes/shaped/steam_repressurizer_from_tank.json",
    "data/ic2/recipes/shaped/steam_turbine.json",
    "data/ic2/recipes/shaped/steam_turbine_vertical.json",
    "data/ic2/recipes/shaped/uranium.json",
    "data/ic2/recipes/shaped/uranium_2.json",
    "data/ic2/recipes/shaped/uranium_block.json",
    "data/ic2/recipes/shaped/uranium_block_tagged.json",
    "data/ic2/recipes/shaped/wrench.json",
    "data/ic2/recipes/shaped/wrench_down.json",
    "data/ic2/recipes/shapeless/black_painter.json",
    "data/ic2/recipes/shapeless/blue_painter.json",
    "data/ic2/recipes/shapeless/brown_painter.json",
    "data/ic2/recipes/shapeless/cyan_painter.json",
    "data/ic2/recipes/shapeless/gray_painter.json",
    "data/ic2/recipes/shapeless/green_painter.json",
    "data/ic2/recipes/shapeless/light_blue_painter.json",
    "data/ic2/recipes/shapeless/light_gray_painter.json",
    "data/ic2/recipes/shapeless/lime_painter.json",
    "data/ic2/recipes/shapeless/magenta_painter.json",
    "data/ic2/recipes/shapeless/orange_painter.json",
    "data/ic2/recipes/shapeless/pink_painter.json",
    "data/ic2/recipes/shapeless/purple_painter.json",
    "data/ic2/recipes/shapeless/red_painter.json",
    "data/ic2/recipes/shapeless/white_painter.json",
    "data/ic2/recipes/shapeless/yellow_painter.json",
    "data/ic2/recipes/smelting/bronze_ingot_from_bronze_dust.json",
    "data/ic2/recipes/smelting/copper_ingot_from_copper_dust.json",
    "data/ic2/recipes/smelting/copper_ingot_from_crushed_copper.json",
    "data/ic2/recipes/smelting/copper_ingot_from_purified_copper.json",
    "data/ic2/recipes/smelting/gold_ingot_from_crushed_gold.json",
    "data/ic2/recipes/smelting/gold_ingot_from_gold_dust.json",
    "data/ic2/recipes/smelting/gold_ingot_from_purified_gold.json",
    "data/ic2/recipes/smelting/iron_ingot_from_crushed_iron.json",
    "data/ic2/recipes/smelting/iron_ingot_from_iron_dust.json",
    "data/ic2/recipes/smelting/iron_ingot_from_purified_iron.json",
    "data/ic2/recipes/smelting/lead_ingot.json",
    "data/ic2/recipes/smelting/lead_ingot_from_crushed_lead.json",
    "data/ic2/recipes/smelting/lead_ingot_from_lead_dust.json",
    "data/ic2/recipes/smelting/lead_ingot_from_purified_lead.json",
    "data/ic2/recipes/smelting/silver_ingot_from_crushed_silver.json",
    "data/ic2/recipes/smelting/silver_ingot_from_purified_silver.json",
    "data/ic2/recipes/smelting/silver_ingot_from_silver_dust.json",
    "data/ic2/recipes/smelting/tin_ingot.json",
    "data/ic2/recipes/smelting/tin_ingot_from_crushed_tin.json",
    "data/ic2/recipes/smelting/tin_ingot_from_purified_tin.json",
    "data/ic2/recipes/smelting/tin_ingot_from_tin_dust.json",
    "data/ic2/recipes/block_cutter/acacia_logs_to_acacia_planks.json",
    "data/ic2/recipes/block_cutter/birch_logs_to_birch_planks.json",
    "data/ic2/recipes/block_cutter/blocks_bronze_to_bronze_plate.json",
    "data/ic2/recipes/block_cutter/blocks_lead_to_lead_plate.json",
    "data/ic2/recipes/block_cutter/blocks_steel_to_steel_plate.json",
    "data/ic2/recipes/block_cutter/blocks_tin_to_tin_plate.json",
    "data/ic2/recipes/block_cutter/copper_block_to_copper_plate.json",
    "data/ic2/recipes/block_cutter/dark_oak_logs_to_dark_oak_planks.json",
    "data/ic2/recipes/block_cutter/gold_block_to_gold_plate.json",
    "data/ic2/recipes/block_cutter/iron_block_to_iron_plate.json",
    "data/ic2/recipes/block_cutter/jungle_logs_to_jungle_planks.json",
    "data/ic2/recipes/block_cutter/lapis_block_to_lapis_plate.json",
    "data/ic2/recipes/block_cutter/obsidian_to_obsidian_plate.json",
    "data/ic2/recipes/block_cutter/oak_logs_to_oak_planks.json",
    "data/ic2/recipes/block_cutter/planks_to_stick.json",
    "data/ic2/recipes/block_cutter/spruce_logs_to_spruce_planks.json",
    "data/ic2/recipes/extractor/netherrack_dust_to_small_sulfur_dust.json",
    "data/ic2/recipes/extractor/wool_to_white_wool.json",
    "data/ic2/recipes/macerator/chiseled_quartz_block_to_quartz.json",
    "data/ic2/recipes/macerator/packed_ice_to_ice.json",
    "data/ic2/recipes/macerator/quartz_pillar_to_quartz.json",
    "data/ic2/recipes/canner_bottle/apple_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/baked_potato_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/beef_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/beetroot_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/beetroot_soup_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/bread_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/carrot_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/chicken_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/chorus_fruit_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cod_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_beef_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_chicken_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_cod_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_mutton_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_porkchop_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_rabbit_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cooked_salmon_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/cookie_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/enchanted_golden_apple_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/golden_apple_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/golden_carrot_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/melon_slice_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/mushroom_stew_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/mutton_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/poisonous_potato_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/porkchop_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/potato_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/pumpkin_pie_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/rabbit_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/rabbit_stew_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/rotten_flesh_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/salmon_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/spider_eye_filled_tin_can.json",
    "data/ic2/recipes/canner_bottle/tropical_fish_filled_tin_can.json"
)
if ($legacyRecipeEntries.Count -ne 149) {
    throw "Expected 149 exact 2.8 recipe translations, found $($legacyRecipeEntries.Count)"
}
$legacyRecipeManifest = Join-Path $projectRoot "src\main\resources\META-INF\ic2-fidelity-legacy-recipes.txt"
$manifestRecipeEntries = Get-Content -LiteralPath $legacyRecipeManifest | Where-Object {
    $_ -and -not $_.StartsWith('#')
} | ForEach-Object {
    if (-not $_.StartsWith('ic2:')) {
        throw "Invalid recipe id in legacy manifest: $_"
    }
    "data/ic2/recipes/$($_.Substring(4)).json"
}
$legacyRecipeMismatch = Compare-Object $legacyRecipeEntries $manifestRecipeEntries
if ($legacyRecipeMismatch) {
    throw "Legacy recipe manifest and extraction list differ: $($legacyRecipeMismatch -join ', ')"
}
Push-Location $classesDir
try {
    $extractArgs = @("xf", $legacyJar) + $legacyRecipeEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Unable to extract exact legacy recipe translations"
    }
} finally {
    Pop-Location
}
$missingLegacyRecipes = $legacyRecipeEntries | Where-Object {
    -not (Test-Path -LiteralPath (Join-Path $classesDir $_) -PathType Leaf)
}
if ($missingLegacyRecipes) {
    throw "Missing extracted legacy recipes: $($missingLegacyRecipes -join ', ')"
}

# The early ex119 translation used minecraft:wool for the old 1.12 wildcard
# extractor recipe. That tag also contains the white output, which the newer
# BasicMachineRecipeManager correctly rejects as a same-input/output loop.
# Keep the old bleaching behavior by listing every coloured wool explicitly.
$woolExtractorOverride = Join-Path $projectRoot `
    'src\main\resources\data\ic2\recipes\extractor\wool_to_white_wool.json'
Copy-Item -LiteralPath $woolExtractorOverride `
    -Destination (Join-Path $classesDir 'data\ic2\recipes\extractor\wool_to_white_wool.json') `
    -Force

$classicJar = $experimental112Jar
$classicBatteryEntries = & $jar tf $classicJar | Where-Object {
    $_ -match '^assets/ic2/(models/item|textures/items)/battery/(advanced_charging_re_battery|charging_re_battery|charging_energy_crystal|charging_lapotron_crystal)_[0-4]\.(json|png)$' -or
    $_ -match '^assets/ic2/(models/item|textures/items)/armor/(advanced_batpack|batpack|energy_pack|lappack)\.(json|png)$' -or
    $_ -match '^assets/ic2/textures/armor/(advbatpack|batpack|energypack|lappack)_1\.png$' -or
    $_ -match '^assets/ic2/(models/item|textures/items)/tool/electric/electric_hoe\.(json|png)$' -or
    $_ -match '^assets/ic2/models/item/crop/cropnalyzer\.json$' -or
    $_ -match '^assets/ic2/textures/items/crop/cropnalyzer\.png$' -or
    $_ -match '^assets/ic2/textures/gui/guicropnalyzer\.png$' -or
    $_ -match '^assets/ic2/(models/item|textures/items)/tool/foam_sprayer\.(json|png)$' -or
    $_ -match '^assets/ic2/models/item/armor/jetpack_electric\.json$' -or
    $_ -match '^assets/ic2/textures/armor/jetpack_1\.png$' -or
    $_ -match '^assets/ic2/models/item/crop/weeding_trowel\.json$' -or
    $_ -match '^assets/ic2/textures/items/crop/weeding_trowel\.png$' -or
    $_ -match '^assets/ic2/models/item/(armor/(solar_helmet|static_boots)|iodine_tablet)\.json$' -or
    $_ -match '^assets/ic2/textures/armor/(solar|rubber)_1\.png$' -or
    $_ -match '^assets/ic2/(models/item|textures/items)/crafting/mfsu_upgrade_kit\.(json|png)$' -or
      $_ -match '^assets/ic2/(models/item|textures/items)/tool/containment_box\.(json|png)$' -or
      $_ -match '^assets/ic2/textures/gui/guicontainmentbox\.png$' -or
      $_ -match '^assets/ic2/(models/item|textures/items)/cell/fluid_cell(_case|_window)?\.(json|png)$' -or
      $_ -match '^assets/ic2/textures/blocks/steam/(coke_kiln(_active|_hatch|_grate)?|refractory_bricks)\.png$' -or
      $_ -match '^assets/ic2/textures/blocks/barrel_(bottomtop|sides|tap)\.png$' -or
      $_ -match '^assets/ic2/textures/items/brewing/barrel\.png$' -or
      $_ -match '^assets/ic2/textures/items/brewing/booze_mug_(beer_(brew|youngster|beer|ale|dragon_blood|black_stuff)|rum)\.png$'
  }
  if ($classicBatteryEntries.Count -ne 93) {
      throw "Expected 93 restored battery/energy-pack/tool/coke-kiln/barrel/jetpack assets, found $($classicBatteryEntries.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @("xf", $classicJar) + $classicBatteryEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Unable to extract classic charging-battery assets"
    }
} finally {
    Pop-Location
}

# GraviSuite recipes also depend on three IC2 Classic jetpack variants that
# IC2 Experimental never registered. Preserve their exact 2.0.7 bitmaps.
$classic119Jar = Join-Path $portRoot 'upstream\IC2Classic-1.19.2-2.0.7-reference.jar'
$classic119TeleportSound = 'assets/ic2/sounds/machines/teleport.ogg'
$classic119Index = & $jar tf $classic119Jar
if ($classic119TeleportSound -notin $classic119Index) {
    throw "Missing exact IC2 Classic teleporter sound: $classic119TeleportSound"
}
Push-Location $classesDir
try {
    & $jar xf $classic119Jar $classic119TeleportSound
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract exact IC2 Classic teleporter sound'
    }
} finally {
    Pop-Location
}
$classic119JetpackAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/item/armor/jetpack/(nuclear|compacted_electric|compacted_nuclear)\.png$' -or
    $_ -match '^assets/ic2/textures/models/armor/jetpack_(nuclear|compacted_electric|compacted_nuclear)_1\.png$'
}
if ($classic119JetpackAssets.Count -ne 6) {
    throw "Expected 6 IC2 Classic 1.19.2 jetpack assets, found $($classic119JetpackAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119JetpackAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic 1.19.2 jetpack assets'
    }
} finally {
    Pop-Location
}

# GraviSuite 2.2's unchanged high-tier recipes reference a small IC2 Classic
# prerequisite slice. Extract the exact item and armour bitmaps while keeping
# the implementation in this companion, so the Classic base jar stays absent.
$classic119GravisuitPrerequisiteAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/item/battery/glowtronic_crystal_[0-4]\.png$' -or
    $_ -match '^assets/ic2/textures/item/armor/packs/quantum\.png$' -or
    $_ -match '^assets/ic2/textures/models/armor/quantumpack_1\.png$' -or
    $_ -match '^assets/ic2/textures/item/tools/(memory_stick|portable_teleporter)\.png$' -or
    $_ -match '^assets/ic2/textures/item/tools/(drill/advanced|chainsaw/advanced|wrench/precision)\.png$' -or
    $_ -match '^assets/ic2/textures/item/misc/(complex_circuit|pulsating_quartz)\.png$' -or
    $_ -match '^assets/ic2/textures/item/upgrades/(machines/(base|simple_import|efficiency)|chargepads/(basic_field_expansion|field_expansion|advanced_field_expansion))\.png$'
}
if ($classic119GravisuitPrerequisiteAssets.Count -ne 20) {
    throw "Expected 20 GraviSuite prerequisite assets, found $($classic119GravisuitPrerequisiteAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119GravisuitPrerequisiteAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract GraviSuite prerequisite assets'
    }
} finally {
    Pop-Location
}

# Advanced Solar Panels' original recipes require IC2 Classic's MV compact
# solar panel. Keep the exact Classic six-sided active/inactive textures for
# both MV and HV panels; the latter is registered now but remains deliberately
# uncraftable until its original IV-transformer prerequisite is restored.
$classic119SolarAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/block/electric/generator/(mv_solar_panel|hv_solar_panel)/(active|inactive)_(down|east|north|south|up|west)\.png$' -or
    $_ -match '^assets/ic2/textures/block/electric/generator/(mv_solar_panel|hv_solar_panel)/texture_package\.json$'
}
if ($classic119SolarAssets.Count -ne 26) {
    throw "Expected 26 IC2 Classic MV/HV solar-panel assets, found $($classic119SolarAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119SolarAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic MV/HV solar-panel assets'
    }
} finally {
    Pop-Location
}

$classicSolarBlockStateDir = Join-Path $classesDir 'assets\ic2\blockstates'
$classicSolarBlockModelDir = Join-Path $classesDir 'assets\ic2\models\block\generator\electric'
$classicSolarItemModelDir = Join-Path $classesDir 'assets\ic2\models\item'
New-Item -ItemType Directory -Path $classicSolarBlockStateDir -Force | Out-Null
New-Item -ItemType Directory -Path $classicSolarBlockModelDir -Force | Out-Null
New-Item -ItemType Directory -Path $classicSolarItemModelDir -Force | Out-Null
foreach ($id in @('solar_panel_mv', 'solar_panel_hv')) {
    $textureFolder = if ($id -eq 'solar_panel_mv') { 'mv_solar_panel' } else { 'hv_solar_panel' }
    foreach ($activity in @('inactive', 'active')) {
        $textures = [ordered]@{ particle = "ic2:block/electric/generator/$textureFolder/$activity`_up" }
        foreach ($face in @('down', 'up', 'north', 'south', 'west', 'east')) {
            $textures[$face] = "ic2:block/electric/generator/$textureFolder/$activity`_$face"
        }
        $model = [ordered]@{
            parent = 'minecraft:block/cube'
            textures = $textures
        } | ConvertTo-Json -Depth 5
        $modelName = if ($activity -eq 'active') { "$id`_active.json" } else { "$id.json" }
        [IO.File]::WriteAllText(
            (Join-Path $classicSolarBlockModelDir $modelName),
            $model,
            [Text.UTF8Encoding]::new($false))
    }

    $variants = [ordered]@{}
    foreach ($activity in @('false', 'true')) {
        $modelName = if ($activity -eq 'true') { "$id`_active" } else { $id }
        $rotations = [ordered]@{ north = 0; east = 90; south = 180; west = 270 }
        foreach ($facing in $rotations.Keys) {
            $variant = [ordered]@{ model = "ic2:block/generator/electric/$modelName" }
            if ($rotations[$facing] -ne 0) {
                $variant.y = $rotations[$facing]
            }
            $variants["active=$activity,facing=$facing"] = $variant
        }
    }
    [IO.File]::WriteAllText(
        (Join-Path $classicSolarBlockStateDir "$id.json"),
        (([ordered]@{ variants = $variants }) | ConvertTo-Json -Depth 6),
        [Text.UTF8Encoding]::new($false))
    [IO.File]::WriteAllText(
        (Join-Path $classicSolarItemModelDir "$id.json"),
        (([ordered]@{ parent = "ic2:block/generator/electric/$id" }) | ConvertTo-Json -Depth 3),
        [Text.UTF8Encoding]::new($false))
}

$classic119IvTransformerAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/block/electric/transformer/iv/(active|inactive)_(down|east|north|south|up|west)\.png$' -or
    $_ -eq 'assets/ic2/textures/block/electric/transformer/iv/texture_package.json'
}
if ($classic119IvTransformerAssets.Count -ne 13) {
    throw "Expected 13 IC2 Classic IV-transformer assets, found $($classic119IvTransformerAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119IvTransformerAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic IV-transformer assets'
    }
} finally {
    Pop-Location
}

$ivTransformerModelDir = Join-Path $classesDir 'assets\ic2\models\block\wiring\transformer'
New-Item -ItemType Directory -Path $ivTransformerModelDir -Force | Out-Null
foreach ($activity in @('inactive', 'active')) {
    $textures = [ordered]@{
        particle = "ic2:block/electric/transformer/iv/$activity`_north"
    }
    foreach ($face in @('down', 'up', 'north', 'south', 'west', 'east')) {
        $textures[$face] = "ic2:block/electric/transformer/iv/$activity`_$face"
    }
    $modelName = if ($activity -eq 'active') {
        'transformer_iv_active.json'
    } else {
        'transformer_iv.json'
    }
    [IO.File]::WriteAllText(
        (Join-Path $ivTransformerModelDir $modelName),
        (([ordered]@{ parent = 'minecraft:block/cube'; textures = $textures }) |
            ConvertTo-Json -Depth 5),
        [Text.UTF8Encoding]::new($false))
}
$ivVariants = [ordered]@{}
$ivFacings = [ordered]@{
    north = @{}
    east = @{ y = 90 }
    south = @{ y = 180 }
    west = @{ y = 270 }
    up = @{ x = -90 }
    down = @{ x = 90 }
}
foreach ($activity in @('false', 'true')) {
    $modelName = if ($activity -eq 'true') { 'transformer_iv_active' } else { 'transformer_iv' }
    foreach ($facing in $ivFacings.Keys) {
        $variant = [ordered]@{ model = "ic2:block/wiring/transformer/$modelName" }
        foreach ($rotation in $ivFacings[$facing].GetEnumerator()) {
            $variant[$rotation.Key] = $rotation.Value
        }
        $ivVariants["active=$activity,facing=$facing"] = $variant
    }
}
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\blockstates\transformer_iv.json'),
    (([ordered]@{ variants = $ivVariants }) | ConvertTo-Json -Depth 6),
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\models\item\transformer_iv.json'),
    (([ordered]@{ parent = 'ic2:block/wiring/transformer/transformer_iv' }) |
        ConvertTo-Json -Depth 3),
    [Text.UTF8Encoding]::new($false))

# Restore IC2 Classic's bronze conductor artwork verbatim. The Experimental
# renderer consumes renamed copies in its own cable-atlas layout, while item
# models continue to point at the original Classic item texture paths.
$classic119BronzeAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -eq 'assets/ic2/textures/block/electric/cable/bronze.png' -or
    $_ -match '^assets/ic2/textures/block/electric/cable/(1x_insulated_bronze|2x_insulated_bronze)/.+[^/]$' -or
    $_ -match '^assets/ic2/textures/item/cable/(bronze|insulated_bronze|double_insulated_bronze)\.png$' -or
    $_ -eq 'assets/ic2/textures/item/armor/solar_helmet/advanced.png' -or
    $_ -eq 'assets/ic2/textures/models/armor/solar_1.png'
}
if ($classic119BronzeAssets.Count -ne 42) {
    throw "Expected 42 IC2 Classic bronze-cable/advanced-helmet assets, found $($classic119BronzeAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119BronzeAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic bronze-cable/advanced-helmet assets'
    }
} finally {
    Pop-Location
}

$classic119PlasmaAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -eq 'assets/ic2/textures/block/personal/iridium_stone.png' -or
    $_ -match '^assets/ic2/textures/block/machine/ev/plasmafier/(active|inactive)_(down|east|north|south|up|west)\.png$' -or
    $_ -eq 'assets/ic2/textures/block/machine/ev/plasmafier/texture_package.json' -or
    $_ -eq 'assets/ic2/textures/gui_sprites/blocks/machines/ev/gui_plasmafier.png' -or
    $_ -eq 'assets/ic2/textures/gui_sprites/items/gui_nuclear_jetpack.png' -or
    $_ -eq 'assets/ic2/textures/gui_sprites/misc/gui_friends.png' -or
    $_ -match '^assets/ic2/textures/item/(battery/pesd|cells/plasma|misc/plasma_core)\.png$'
}
if ($classic119PlasmaAssets.Count -ne 20) {
    throw "Expected 20 IC2 Classic iridium/plasma/nuclear/friends-GUI assets, found $($classic119PlasmaAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119PlasmaAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic iridium/plasma assets'
    }
} finally {
    Pop-Location
}

$classic119RareEarthAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/block/machine/lv/rare_earth_extractor/.+[^/]$' -or
    $_ -eq 'assets/ic2/textures/gui_sprites/blocks/machines/lv/gui_rare_earth_extractor.png' -or
    $_ -match '^assets/ic2/textures/item/dust/(aluminium|rare_earth)\.png$' -or
    $_ -match '^assets/ic2/textures/item/misc/(dead_magnet|magnet|rare_earth_chunk)\.png$'
}
if ($classic119RareEarthAssets.Count -ne 21) {
    throw "Expected 21 IC2 Classic rare-earth assets, found $($classic119RareEarthAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119RareEarthAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic rare-earth assets'
    }
} finally {
    Pop-Location
}

$classic119PlasmaCableAssets = & $jar tf $classic119Jar | Where-Object {
    $_ -match '^assets/ic2/textures/block/electric/cable/plasma/.+[^/]$' -or
    $_ -eq 'assets/ic2/textures/item/cable/plasma.png'
}
if ($classic119PlasmaCableAssets.Count -ne 19) {
    throw "Expected 19 IC2 Classic plasma-cable assets, found $($classic119PlasmaCableAssets.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @('xf', $classic119Jar) + $classic119PlasmaCableAssets
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract IC2 Classic plasma-cable assets'
    }
} finally {
    Pop-Location
}

$iridiumStoneModelDir = Join-Path $classesDir 'assets\ic2\models\block\misc'
New-Item -ItemType Directory -Path $iridiumStoneModelDir -Force | Out-Null
[IO.File]::WriteAllText(
    (Join-Path $iridiumStoneModelDir 'iridium_stone.json'),
    (([ordered]@{
        parent = 'minecraft:block/cube_all'
        textures = [ordered]@{ all = 'ic2:block/personal/iridium_stone' }
    }) | ConvertTo-Json -Depth 4),
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\blockstates\iridium_stone.json'),
    (([ordered]@{ variants = [ordered]@{
        '' = [ordered]@{ model = 'ic2:block/misc/iridium_stone' }
    } }) | ConvertTo-Json -Depth 4),
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\models\item\iridium_stone.json'),
    (([ordered]@{ parent = 'ic2:block/misc/iridium_stone' }) | ConvertTo-Json),
    [Text.UTF8Encoding]::new($false))

$plasmafierModelDir = Join-Path $classesDir 'assets\ic2\models\block\machine\ev'
New-Item -ItemType Directory -Path $plasmafierModelDir -Force | Out-Null
foreach ($activity in @('inactive', 'active')) {
    $textures = [ordered]@{
        particle = "ic2:block/machine/ev/plasmafier/$activity`_north"
    }
    foreach ($face in @('down', 'up', 'north', 'south', 'west', 'east')) {
        $textures[$face] = "ic2:block/machine/ev/plasmafier/$activity`_$face"
    }
    $modelName = if ($activity -eq 'active') {
        'plasmafier_active.json'
    } else {
        'plasmafier.json'
    }
    [IO.File]::WriteAllText(
        (Join-Path $plasmafierModelDir $modelName),
        (([ordered]@{ parent = 'minecraft:block/cube'; textures = $textures }) |
            ConvertTo-Json -Depth 5),
        [Text.UTF8Encoding]::new($false))
}
$plasmafierVariants = [ordered]@{}
$plasmafierRotations = [ordered]@{ north = 0; east = 90; south = 180; west = 270 }
foreach ($activity in @('false', 'true')) {
    $modelName = if ($activity -eq 'true') { 'plasmafier_active' } else { 'plasmafier' }
    foreach ($facing in $plasmafierRotations.Keys) {
        $variant = [ordered]@{ model = "ic2:block/machine/ev/$modelName" }
        if ($plasmafierRotations[$facing] -ne 0) {
            $variant.y = $plasmafierRotations[$facing]
        }
        $plasmafierVariants["active=$activity,facing=$facing"] = $variant
    }
}
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\blockstates\plasmafier.json'),
    (([ordered]@{ variants = $plasmafierVariants }) | ConvertTo-Json -Depth 6),
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\models\item\plasmafier.json'),
    (([ordered]@{ parent = 'ic2:block/machine/ev/plasmafier' }) | ConvertTo-Json),
    [Text.UTF8Encoding]::new($false))

$plasmaItemModels = [ordered]@{
    cell_plasma = 'cells/plasma'
    plasma_core = 'misc/plasma_core'
    pesd = 'battery/pesd'
}
foreach ($entry in $plasmaItemModels.GetEnumerator()) {
    [IO.File]::WriteAllText(
        (Join-Path $classesDir "assets\ic2\models\item\$($entry.Key).json"),
        (([ordered]@{
            parent = 'minecraft:item/generated'
            textures = [ordered]@{ layer0 = "ic2:item/$($entry.Value)" }
        }) | ConvertTo-Json -Depth 4),
        [Text.UTF8Encoding]::new($false))
}

$rareEarthModelDir = Join-Path $classesDir 'assets\ic2\models\block\machine\lv'
New-Item -ItemType Directory -Path $rareEarthModelDir -Force | Out-Null
foreach ($activity in @('inactive', 'active')) {
    $textures = [ordered]@{
        particle = "ic2:block/machine/lv/rare_earth_extractor/$activity`_north"
    }
    foreach ($face in @('down', 'up', 'north', 'south', 'west', 'east')) {
        $textures[$face] = "ic2:block/machine/lv/rare_earth_extractor/$activity`_$face"
    }
    $modelName = if ($activity -eq 'active') {
        'rare_earth_extractor_active.json'
    } else {
        'rare_earth_extractor.json'
    }
    [IO.File]::WriteAllText(
        (Join-Path $rareEarthModelDir $modelName),
        (([ordered]@{ parent = 'minecraft:block/cube'; textures = $textures }) |
            ConvertTo-Json -Depth 5),
        [Text.UTF8Encoding]::new($false))
}
$rareEarthVariants = [ordered]@{}
$rareEarthRotations = [ordered]@{ north = 0; east = 90; south = 180; west = 270 }
foreach ($activity in @('false', 'true')) {
    $modelName = if ($activity -eq 'true') {
        'rare_earth_extractor_active'
    } else {
        'rare_earth_extractor'
    }
    foreach ($facing in $rareEarthRotations.Keys) {
        $variant = [ordered]@{ model = "ic2:block/machine/lv/$modelName" }
        if ($rareEarthRotations[$facing] -ne 0) {
            $variant.y = $rareEarthRotations[$facing]
        }
        $rareEarthVariants["active=$activity,facing=$facing"] = $variant
    }
}
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\blockstates\rare_earth_extractor.json'),
    (([ordered]@{ variants = $rareEarthVariants }) | ConvertTo-Json -Depth 6),
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $classesDir 'assets\ic2\models\item\rare_earth_extractor.json'),
    (([ordered]@{ parent = 'ic2:block/machine/lv/rare_earth_extractor' }) |
        ConvertTo-Json),
    [Text.UTF8Encoding]::new($false))

$rareEarthItemModels = [ordered]@{
    rare_earth_dust = 'dust/rare_earth'
    dust_aluminium = 'dust/aluminium'
    rare_earth_chunk = 'misc/rare_earth_chunk'
    dead_magnet = 'misc/dead_magnet'
    magnet = 'misc/magnet'
}
foreach ($entry in $rareEarthItemModels.GetEnumerator()) {
    [IO.File]::WriteAllText(
        (Join-Path $classesDir "assets\ic2\models\item\$($entry.Key).json"),
        (([ordered]@{
            parent = 'minecraft:item/generated'
            textures = [ordered]@{ layer0 = "ic2:item/$($entry.Value)" }
        }) | ConvertTo-Json -Depth 4),
        [Text.UTF8Encoding]::new($false))
}

$experimentalCableTextureDir = Join-Path $classesDir 'assets\ic2\textures\blocks\wiring\cable'
$experimentalCableModelDir = Join-Path $classesDir 'assets\ic2\models\block\wiring\cable'
$experimentalCableBlockStateDir = Join-Path $classesDir 'assets\ic2\blockstates'
$experimentalCableItemModelDir = Join-Path $classesDir 'assets\ic2\models\item'
New-Item -ItemType Directory -Path $experimentalCableTextureDir -Force | Out-Null
New-Item -ItemType Directory -Path $experimentalCableModelDir -Force | Out-Null
New-Item -ItemType Directory -Path $experimentalCableBlockStateDir -Force | Out-Null
New-Item -ItemType Directory -Path $experimentalCableItemModelDir -Force | Out-Null
Push-Location $classesDir
try {
    & $jar xf $upstreamIc2Jar 'assets/ic2/blockstates/copper_foam_cable.json'
    if ($LASTEXITCODE -ne 0) {
        throw 'Unable to extract the Experimental CF-cable blockstate template'
    }
} finally {
    Pop-Location
}
foreach ($foamId in @(
        'bronze_foam_cable',
        'bronze_insulated_foam_cable',
        'bronze_double_insulated_foam_cable',
        'plasma_foam_cable')) {
    Copy-Item -LiteralPath (Join-Path $experimentalCableBlockStateDir `
            'copper_foam_cable.json') `
        -Destination (Join-Path $experimentalCableBlockStateDir "$foamId.json") -Force
}
Copy-Item -LiteralPath (Join-Path $classesDir `
        'assets\ic2\textures\block\electric\cable\bronze.png') `
    -Destination (Join-Path $experimentalCableTextureDir 'bronze_cable_0.png') -Force
$dyeNames = @(
    'white', 'orange', 'magenta', 'light_blue', 'yellow', 'lime', 'pink',
    'gray', 'light_gray', 'cyan', 'purple', 'blue', 'brown', 'green', 'red', 'black'
)
for ($insulation = 1; $insulation -le 2; $insulation++) {
    $classicFolder = if ($insulation -eq 1) {
        '1x_insulated_bronze'
    } else {
        '2x_insulated_bronze'
    }
    foreach ($dye in $dyeNames) {
        Copy-Item -LiteralPath (Join-Path $classesDir `
                "assets\ic2\textures\block\electric\cable\$classicFolder\$dye.png") `
            -Destination (Join-Path $experimentalCableTextureDir `
                "bronze_cable_$insulation`_$dye.png") -Force
    }
}
Copy-Item -LiteralPath (Join-Path $classesDir `
        'assets\ic2\textures\block\electric\cable\plasma\blank.png') `
    -Destination (Join-Path $experimentalCableTextureDir 'plasma_cable_0.png') -Force
foreach ($dye in $dyeNames) {
    Copy-Item -LiteralPath (Join-Path $classesDir `
            "assets\ic2\textures\block\electric\cable\plasma\$dye.png") `
        -Destination (Join-Path $experimentalCableTextureDir `
            "plasma_cable_0_$dye.png") -Force
}

$bronzeCableBlocks = [ordered]@{
    bronze_cable = 0
    bronze_cable_insulated = 1
    bronze_cable_double_insulated = 2
}
foreach ($entry in $bronzeCableBlocks.GetEnumerator()) {
    $id = $entry.Key
    $insulation = [int]$entry.Value
    $modelName = "bronze_$insulation`_none"
    $model = [ordered]@{
        loader = 'ic2:cable'
        type = 'bronze'
        insulation = $insulation
        foam = 'none'
        active = $false
    } | ConvertTo-Json -Compress
    [IO.File]::WriteAllText(
        (Join-Path $experimentalCableModelDir "$modelName.json"),
        $model,
        [Text.UTF8Encoding]::new($false))
    [IO.File]::WriteAllText(
        (Join-Path $experimentalCableBlockStateDir "$id.json"),
        (([ordered]@{ variants = [ordered]@{
            '' = [ordered]@{ model = "ic2:block/wiring/cable/$modelName" }
        } }) | ConvertTo-Json -Depth 5),
        [Text.UTF8Encoding]::new($false))
}
$plasmaCableModel = [ordered]@{
    loader = 'ic2:cable'
    type = 'plasma'
    insulation = 0
    foam = 'none'
    active = $false
} | ConvertTo-Json -Compress
[IO.File]::WriteAllText(
    (Join-Path $experimentalCableModelDir 'plasma_0_none.json'),
    $plasmaCableModel,
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText(
    (Join-Path $experimentalCableBlockStateDir 'plasma_cable.json'),
    (([ordered]@{ variants = [ordered]@{
        '' = [ordered]@{ model = 'ic2:block/wiring/cable/plasma_0_none' }
    } }) | ConvertTo-Json -Depth 5),
    [Text.UTF8Encoding]::new($false))

$bronzeCableItemTextures = [ordered]@{
    bronze_cable_item = 'cable/bronze'
    bronze_insulated_cable_item = 'cable/insulated_bronze'
    bronze_double_insulated_cable_item = 'cable/double_insulated_bronze'
    advanced_solar_helmet = 'armor/solar_helmet/advanced'
    plasma_cable_item = 'cable/plasma'
}
foreach ($entry in $bronzeCableItemTextures.GetEnumerator()) {
    $itemModel = [ordered]@{
        parent = 'minecraft:item/generated'
        textures = [ordered]@{ layer0 = "ic2:item/$($entry.Value)" }
    } | ConvertTo-Json -Depth 4
    [IO.File]::WriteAllText(
        (Join-Path $experimentalCableItemModelDir "$($entry.Key).json"),
        $itemModel,
        [Text.UTF8Encoding]::new($false))
}

# IC2 2.8.222 defined fifteen data-driven crops that the ex119 port omitted.
# Keep their original bitmaps and item models, while translating the old
# one-based crop sizes to ex119's zero-based block-state ages.
$legacyCropSizes = [ordered]@{
    blazereed = 4
    bobs_yer_uncle_ranks_berries = 4
    corium = 4
    corpse_plant = 4
    creeper_weed = 4
    diareed = 4
    egg_plant = 3
    ender_blossom = 4
    meat_rose = 4
    milk_wart = 3
    oil_berries = 3
    slime_plant = 4
    spidernip = 4
    tearstalks = 4
    withereed = 4
}
$legacyCropRegex = ($legacyCropSizes.Keys | ForEach-Object { [regex]::Escape($_) }) -join '|'
$legacyCropAssetEntries = & $jar tf $classicJar | Where-Object {
    $_ -match "^assets/ic2/textures/blocks/crop/($legacyCropRegex)_[1-4]\.png$" -or
    $_ -match '^assets/ic2/(models/item|textures/items)/(crop/(milk_wart|oil_berry|bobs_yer_uncle_ranks_berry)|resource/dust/(ender_pearl|ender_eye|milk|emerald|small_emerald|small_diamond))\.(json|png)$'
}
if ($legacyCropAssetEntries.Count -ne 76) {
    throw "Expected 76 exact legacy crop/resource assets, found $($legacyCropAssetEntries.Count)"
}
Push-Location $classesDir
try {
    $extractArgs = @("xf", $classicJar) + $legacyCropAssetEntries
    & $jar $extractArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Unable to extract legacy crop/resource assets"
    }
} finally {
    Pop-Location
}

$legacyItemModels = [ordered]@{
    'resource\dust\ender_pearl' = 'ender_pearl_dust'
    'resource\dust\ender_eye' = 'ender_eye_dust'
    'resource\dust\milk' = 'milk_dust'
    'resource\dust\emerald' = 'emerald_dust'
    'resource\dust\small_emerald' = 'small_emerald_dust'
    'resource\dust\small_diamond' = 'small_diamond_dust'
    'crop\milk_wart' = 'milk_wart'
    'crop\oil_berry' = 'oil_berry'
    'crop\bobs_yer_uncle_ranks_berry' = 'bobs_yer_uncle_ranks_berry'
}
$flatItemModelDir = Join-Path $classesDir 'assets\ic2\models\item'
foreach ($entry in $legacyItemModels.GetEnumerator()) {
    $source = Join-Path $flatItemModelDir ($entry.Key + '.json')
    $destination = Join-Path $flatItemModelDir ($entry.Value + '.json')
    Copy-Item -LiteralPath $source -Destination $destination -Force
}

$cropModelDir = Join-Path $classesDir 'assets\ic2\models\block\crop'
$cropBlockstateDir = Join-Path $classesDir 'assets\ic2\blockstates'
New-Item -ItemType Directory -Path $cropModelDir -Force | Out-Null
New-Item -ItemType Directory -Path $cropBlockstateDir -Force | Out-Null
$utf8NoBom = [Text.UTF8Encoding]::new($false)
foreach ($entry in $legacyCropSizes.GetEnumerator()) {
    $cropId = $entry.Key
    $maxAge = [int]$entry.Value - 1
    $variants = [ordered]@{}
    for ($age = 0; $age -le $maxAge; $age++) {
        $legacySize = $age + 1
        $modelName = "$cropId`_$age"
        $model = [ordered]@{
            parent = 'block/crop'
            textures = [ordered]@{
                crop = "ic2:blocks/crop/$cropId`_$legacySize"
            }
        }
        $modelText = $model | ConvertTo-Json -Depth 4
        [IO.File]::WriteAllText(
            (Join-Path $cropModelDir ($modelName + '.json')), $modelText, $utf8NoBom)
        $variants["age=$age"] = [ordered]@{ model = "ic2:block/crop/$modelName" }
    }
    $blockstateText = ([ordered]@{ variants = $variants }) | ConvertTo-Json -Depth 5
    [IO.File]::WriteAllText(
        (Join-Path $cropBlockstateDir ($cropId + '_crop.json')), $blockstateText, $utf8NoBom)
}

# Imported addon assets include older language files. Apply local translations
# last so extraction cannot overwrite them, preserving the other locales.
Merge-SourceLanguages $classesDir

$manifest = Join-Path $projectRoot "src\main\resources\META-INF\MANIFEST.MF"
& $jar cfm $outputJar $manifest -C $classesDir .
if ($LASTEXITCODE -ne 0) {
    throw "jar failed with exit code $LASTEXITCODE"
}

$splitPackageClasses = & $jar tf $outputJar | Where-Object { $_ -match '^ic2/.+\.class$' }
if ($splitPackageClasses) {
    throw "Companion jar contains forbidden IC2 classes: $($splitPackageClasses -join ', ')"
}

if (-not $SkipInstall) {
    Copy-Item -LiteralPath $outputJar -Destination $installedJar -Force
    Copy-Item -LiteralPath $patchedIc2Jar -Destination $installedPatchedIc2Jar -Force
    if (Test-Path -LiteralPath $installedUpstreamIc2Jar -PathType Leaf) {
        New-Item -ItemType Directory -Path $baseQuarantine -Force | Out-Null
        Move-Item -LiteralPath $installedUpstreamIc2Jar -Destination (Join-Path $baseQuarantine (Split-Path -Leaf $installedUpstreamIc2Jar)) -Force
    }
    Write-Output "Installed: $installedJar"
    Write-Output "Installed patched IC2: $installedPatchedIc2Jar"
}
Remove-Item -LiteralPath $depsDir -Recurse -Force
Write-Output "Built: $outputJar"
Write-Output "Built patched IC2: $patchedIc2Jar"
