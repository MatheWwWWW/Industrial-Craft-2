$ErrorActionPreference = 'Stop'

$portRoot = $PSScriptRoot
$minecraftRoot = Split-Path -Parent $portRoot
$jdeps = Join-Path $minecraftRoot 'runtime\java-runtime-gamma\windows\java-runtime-gamma\bin\jdeps.exe'
$companion = Join-Path $minecraftRoot 'mods\IC2-Experimental-Fidelity-0.1.0.jar'

$dependencyPaths = @(
    'libraries\net\minecraft\client\1.19.2-20220805.130853\client-1.19.2-20220805.130853-srg.jar'
    'libraries\net\minecraftforge\forge\1.19.2-43.5.2\forge-1.19.2-43.5.2-universal.jar'
    'libraries\net\minecraftforge\fmlcore\1.19.2-43.5.2\fmlcore-1.19.2-43.5.2.jar'
    'libraries\net\minecraftforge\fmlloader\1.19.2-43.5.2\fmlloader-1.19.2-43.5.2.jar'
    'libraries\net\minecraftforge\javafmllanguage\1.19.2-43.5.2\javafmllanguage-1.19.2-43.5.2.jar'
    'libraries\net\minecraftforge\eventbus\6.0.3\eventbus-6.0.3.jar'
    'libraries\net\minecraftforge\forgespi\6.0.0\forgespi-6.0.0.jar'
    'libraries\org\spongepowered\mixin\0.8.5\mixin-0.8.5.jar'
    'libraries\com\mojang\brigadier\1.0.18\brigadier-1.0.18.jar'
    'libraries\com\mojang\datafixerupper\5.0.28\datafixerupper-5.0.28.jar'
    'libraries\com\mojang\authlib\3.11.49\authlib-3.11.49.jar'
    'libraries\com\google\guava\guava\31.0.1-jre\guava-31.0.1-jre.jar'
    'libraries\io\netty\netty-buffer\4.1.77.Final\netty-buffer-4.1.77.Final.jar'
    'libraries\io\netty\netty-common\4.1.77.Final\netty-common-4.1.77.Final.jar'
    'libraries\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar'
    'mods\industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar'
    'mods\AdditionalEnchantedMiner-1.19.2-1192.4.65.jar'
    'mods\jei-1.19.2-forge-11.8.1.1034.jar'
) | ForEach-Object {
    $candidate = Join-Path $minecraftRoot $_
    if (-not (Test-Path -LiteralPath $candidate -PathType Leaf)) {
        throw "Missing linkage dependency: $candidate"
    }
    [IO.Path]::GetFullPath($candidate)
}

if (-not (Test-Path -LiteralPath $jdeps -PathType Leaf)) {
    throw "Missing jdeps executable: $jdeps"
}
if (-not (Test-Path -LiteralPath $companion -PathType Leaf)) {
    throw "Missing companion jar: $companion"
}

$classPath = $dependencyPaths -join ';'
$result = & $jdeps --multi-release 17 --missing-deps --class-path $classPath $companion 2>&1
if ($LASTEXITCODE -ne 0) {
    throw "jdeps failed:`n$($result -join "`n")"
}
if ($result) {
    throw "Missing binary dependencies:`n$($result -join "`n")"
}

Write-Output 'Binary linkage audit: no missing class dependencies.'
