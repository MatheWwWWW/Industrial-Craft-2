param([string]$MinecraftRoot = (Join-Path $env:APPDATA '.minecraft'))
$ErrorActionPreference = 'Stop'
$gameRoot = [IO.Path]::GetFullPath($MinecraftRoot).TrimEnd('\')
$modsRoot = Join-Path $gameRoot 'mods'
$buildRoot = Join-Path $PSScriptRoot 'compat\build'
$newNames = @('IC2-Experimental-Fidelity-0.1.0.jar', 'industrialcraft-2-2.9.162+ex119-fidelity-1.19.2-forge.jar')
$retiredNames = @('diamondvein-1.2.jar', 'powerutils-1.8.jar', 'quantumgenerators-1.8.jar', 'reactorplus-1.1.jar', 'simplyquarries-1.8.jar', 'wateringcan-1.0.jar', 'IndustrialUpgrade-1.19.2-3.4.0.9.jar', 'industrialcraft-2-2.9.162+ex119-1.19.2-forge.jar')
$backupRoot = Join-Path $gameRoot ('ic2-native-iu-backups\' + (Get-Date -Format 'yyyyMMdd-HHmmss'))
foreach ($name in $newNames) {
    if (-not (Test-Path -LiteralPath (Join-Path $buildRoot $name) -PathType Leaf)) { throw "Build the JAR first: $name" }
}
foreach ($name in @($newNames) + @($retiredNames)) {
    $resolvedTarget = [IO.Path]::GetFullPath((Join-Path $modsRoot $name))
    if (-not $resolvedTarget.StartsWith($gameRoot + '\', [StringComparison]::OrdinalIgnoreCase)) { throw 'Installation target escaped the Minecraft directory' }
}
if (-not [IO.Path]::GetFullPath($backupRoot).StartsWith($gameRoot + '\', [StringComparison]::OrdinalIgnoreCase)) { throw 'Backup target escaped the Minecraft directory' }
New-Item -ItemType Directory -Path $backupRoot -Force | Out-Null
$originalNames = @()
foreach ($name in @($newNames) + @($retiredNames)) {
    $old = Join-Path $modsRoot $name
    if (Test-Path -LiteralPath $old -PathType Leaf) {
        Copy-Item -LiteralPath $old -Destination (Join-Path $backupRoot $name)
        $originalNames += $name
    }
}
try {
    foreach ($name in $newNames) {
        $source = Join-Path $buildRoot $name
        $stage = Join-Path $modsRoot ($name + '.native-stage')
        Copy-Item -LiteralPath $source -Destination $stage -Force
        if ((Get-FileHash -LiteralPath $source).Hash -ne (Get-FileHash -LiteralPath $stage).Hash) { throw "Staging hash mismatch: $name" }
    }
    foreach ($name in $retiredNames) {
        $old = Join-Path $modsRoot $name
        if (Test-Path -LiteralPath $old -PathType Leaf) { Move-Item -LiteralPath $old -Destination (Join-Path $backupRoot $name) -Force }
    }
    foreach ($name in $newNames) { Move-Item -LiteralPath (Join-Path $modsRoot ($name + '.native-stage')) -Destination (Join-Path $modsRoot $name) -Force }
} catch {
    foreach ($name in $originalNames) { Copy-Item -LiteralPath (Join-Path $backupRoot $name) -Destination (Join-Path $modsRoot $name) -Force }
    throw
}
$receipt = [ordered]@{ originals = $originalNames; installed = @($newNames | ForEach-Object { [ordered]@{ name = $_; sha256 = (Get-FileHash -LiteralPath (Join-Path $modsRoot $_)).Hash } }) }
[IO.File]::WriteAllText((Join-Path $backupRoot 'installation.json'), ($receipt | ConvertTo-Json -Depth 5), [Text.UTF8Encoding]::new($false))
Write-Output "Installed native IC2 adaptations into $modsRoot"
Write-Output "Original JAR backup: $backupRoot"
