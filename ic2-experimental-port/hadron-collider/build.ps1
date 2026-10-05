param(
    # Minecraft directory with libraries\, runtime\ and mods\. Defaults to the
    # same layout as compat\build.ps1: the parent of ic2-experimental-port.
    [string] $MinecraftRoot
)

$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
$portRoot = Split-Path -Parent $projectRoot
if (-not $MinecraftRoot) {
    $MinecraftRoot = Split-Path -Parent $portRoot
}
$javaBin = Join-Path $MinecraftRoot "runtime\java-runtime-gamma\windows\java-runtime-gamma\bin"
$javac = Join-Path $javaBin "javac.exe"
$jar = Join-Path $javaBin "jar.exe"
$buildRoot = Join-Path $projectRoot "build"
$classesDir = Join-Path $buildRoot "classes"
$outputJar = Join-Path $buildRoot "IC2-Hadron-Collider-1.0.0.jar"
$modsDir = Join-Path $MinecraftRoot "mods"

$resolvedProject = [IO.Path]::GetFullPath($projectRoot)
if (-not [IO.Path]::GetFullPath($classesDir).StartsWith($resolvedProject, [StringComparison]::OrdinalIgnoreCase)) {
    throw "Refusing to clean outside the project: $classesDir"
}
if (Test-Path -LiteralPath $classesDir) {
    Remove-Item -LiteralPath $classesDir -Recurse -Force
}
New-Item -ItemType Directory -Path $classesDir -Force | Out-Null

$classpathEntries = @(
    "libraries\net\minecraft\client\1.19.2-20220805.130853\client-1.19.2-20220805.130853-srg.jar",
    "libraries\net\minecraftforge\forge\1.19.2-43.5.2\forge-1.19.2-43.5.2-universal.jar",
    "libraries\net\minecraftforge\fmlcore\1.19.2-43.5.2\fmlcore-1.19.2-43.5.2.jar",
    "libraries\net\minecraftforge\javafmllanguage\1.19.2-43.5.2\javafmllanguage-1.19.2-43.5.2.jar",
    "libraries\net\minecraftforge\eventbus\6.0.3\eventbus-6.0.3.jar",
    "libraries\net\minecraftforge\forgespi\6.0.0\forgespi-6.0.0.jar",
    "libraries\com\mojang\datafixerupper\5.0.28\datafixerupper-5.0.28.jar",
    "libraries\com\mojang\brigadier\1.0.18\brigadier-1.0.18.jar",
    "libraries\com\google\guava\guava\31.0.1-jre\guava-31.0.1-jre.jar"
) | ForEach-Object { Join-Path $MinecraftRoot $_ }
$classpathEntries += Join-Path $portRoot "upstream\industrialcraft-2-2.9.162+ex119-1.19.2-forge.jar"
foreach ($dependency in $classpathEntries) {
    if (-not (Test-Path -LiteralPath $dependency -PathType Leaf)) {
        throw "Missing compile dependency: $dependency"
    }
}

$sources = Get-ChildItem -LiteralPath (Join-Path $projectRoot "src\main\java") -Recurse -File -Filter "*.java" |
    Select-Object -ExpandProperty FullName
& $javac -proc:none -encoding UTF-8 -source 17 -target 17 -Xlint:all,-processing,-serial,-rawtypes `
    -classpath ($classpathEntries -join ";") -d $classesDir $sources
if ($LASTEXITCODE -ne 0) {
    throw "javac failed with exit code $LASTEXITCODE"
}

$resourcesDir = Join-Path $projectRoot "src\main\resources"
Copy-Item -Path (Join-Path $resourcesDir "*") -Destination $classesDir -Recurse -Force
$manifest = Join-Path $classesDir "META-INF\MANIFEST.MF"
Remove-Item -LiteralPath $outputJar -Force -ErrorAction SilentlyContinue
& $jar cfm $outputJar $manifest -C $classesDir .
if ($LASTEXITCODE -ne 0) {
    throw "jar failed with exit code $LASTEXITCODE"
}
Write-Output "Built: $outputJar"

# Install only into a pack that actually loads IC2; anywhere else the addon
# fails Forge dependency resolution.
$ic2Installed = (Test-Path -LiteralPath $modsDir -PathType Container) -and
    @(Get-ChildItem -LiteralPath $modsDir -File -Filter "industrialcraft-2-*.jar").Count -gt 0
if ($ic2Installed) {
    Copy-Item -LiteralPath $outputJar -Destination $modsDir -Force
    Write-Output "Installed: $(Join-Path $modsDir (Split-Path -Leaf $outputJar))"
} else {
    Write-Output "Not installed: no IC2 jar in $modsDir"
}
