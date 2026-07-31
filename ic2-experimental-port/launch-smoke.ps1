$ErrorActionPreference = "Stop"

$portRoot = $PSScriptRoot
$minecraftRoot = Split-Path -Parent $portRoot
$instanceRoot = Join-Path $portRoot "smoke-instance"
$instanceMods = Join-Path $instanceRoot "mods"
$versionRoot = Join-Path $minecraftRoot "versions\Forge 1.19.2"
$versionJsonPath = Join-Path $versionRoot "Forge 1.19.2.json"
$versionJarPath = Join-Path $versionRoot "Forge 1.19.2.jar"
$librariesRoot = Join-Path $minecraftRoot "libraries"
$nativesRoot = Join-Path $versionRoot "natives"
$java = Join-Path $minecraftRoot "runtime\java-runtime-gamma\windows\java-runtime-gamma\bin\java.exe"

$resolvedMinecraft = [IO.Path]::GetFullPath($minecraftRoot)
$resolvedInstance = [IO.Path]::GetFullPath($instanceRoot)
if (-not $resolvedInstance.StartsWith($resolvedMinecraft, [StringComparison]::OrdinalIgnoreCase)) {
    throw "Smoke instance escaped the Minecraft workspace"
}

New-Item -ItemType Directory -Path $instanceMods -Force | Out-Null
$sourceSmokeWorld = Get-ChildItem -LiteralPath (Join-Path $minecraftRoot "saves") -Directory |
    Sort-Object LastWriteTime -Descending |
    Select-Object -First 1 -ExpandProperty FullName
$smokeWorldName = "ic2-fidelity-smoke"
$instanceSaves = Join-Path $instanceRoot "saves"
$smokeWorld = Join-Path $instanceSaves $smokeWorldName
$resolvedSmokeWorld = [IO.Path]::GetFullPath($smokeWorld)
if (-not $resolvedSmokeWorld.StartsWith($resolvedInstance, [StringComparison]::OrdinalIgnoreCase)) {
    throw "Smoke world escaped the test instance"
}
if ([string]::IsNullOrWhiteSpace($sourceSmokeWorld) -or
        -not (Test-Path -LiteralPath $sourceSmokeWorld -PathType Container)) {
    throw "Missing source world for the integrated-server recipe test: $sourceSmokeWorld"
}
New-Item -ItemType Directory -Path $instanceSaves -Force | Out-Null
if (Test-Path -LiteralPath $smokeWorld -PathType Container) {
    Remove-Item -LiteralPath $smokeWorld -Recurse -Force
}
Copy-Item -LiteralPath $sourceSmokeWorld -Destination $smokeWorld -Recurse -Force

$smokeExcluded = @(
    'Turret_1.0.5.jar',
    'Turret-Hud-Patch-1.0.0.jar'
)
$activeMods = @(Get-ChildItem -LiteralPath (Join-Path $minecraftRoot "mods") -File -Filter "*.jar")
$activeNames = @($activeMods | Select-Object -ExpandProperty Name)
foreach ($staleMod in Get-ChildItem -LiteralPath $instanceMods -File -Filter "*.jar") {
    if ($activeNames -notcontains $staleMod.Name -or $smokeExcluded -contains $staleMod.Name) {
        Remove-Item -LiteralPath $staleMod.FullName -Force
    }
}
foreach ($mod in $activeMods) {
    if ($smokeExcluded -contains $mod.Name) {
        continue
    }
    $link = Join-Path $instanceMods $mod.Name
    $resolvedLink = [IO.Path]::GetFullPath($link)
    if (-not $resolvedLink.StartsWith($resolvedInstance, [StringComparison]::OrdinalIgnoreCase)) {
        throw "Smoke mod link escaped the test instance: $resolvedLink"
    }
    if (Test-Path -LiteralPath $link -PathType Leaf) {
        Remove-Item -LiteralPath $link -Force
    }
    New-Item -ItemType HardLink -Path $link -Target $mod.FullName | Out-Null
}
foreach ($excludedName in $smokeExcluded) {
    $excludedPath = [IO.Path]::GetFullPath((Join-Path $instanceMods $excludedName))
    if (-not $excludedPath.StartsWith($resolvedInstance, [StringComparison]::OrdinalIgnoreCase)) {
        throw "Excluded smoke-test path escaped the test instance"
    }
    if (Test-Path -LiteralPath $excludedPath -PathType Leaf) {
        Remove-Item -LiteralPath $excludedPath -Force
    }
}

$version = Get-Content -Raw -LiteralPath $versionJsonPath | ConvertFrom-Json
$classpath = [Collections.Generic.List[string]]::new()
foreach ($library in $version.libraries) {
    $artifact = if ($null -ne $library.downloads -and $null -ne $library.downloads.artifact) {
        $library.downloads.artifact
    } else {
        $library.artifact
    }
    if ($null -eq $artifact -or [string]::IsNullOrWhiteSpace($artifact.path)) {
        continue
    }

    $relativePath = $artifact.path -replace '/', '\'
    $path = if ($relativePath.StartsWith('libraries\', [StringComparison]::OrdinalIgnoreCase)) {
        Join-Path $minecraftRoot $relativePath
    } else {
        Join-Path $librariesRoot $relativePath
    }
    if (Test-Path -LiteralPath $path -PathType Leaf) {
        $classpath.Add($path)
    }
}
$classpath.Add($versionJarPath)
$classpathValue = $classpath -join ";"

$replacements = @{
    '${natives_directory}' = $nativesRoot
    '${launcher_name}' = 'codex-smoke'
    '${launcher_version}' = '1'
    '${classpath}' = $classpathValue
    '${library_directory}' = $librariesRoot
    '${classpath_separator}' = ';'
    '${version_name}' = 'Forge 1.19.2'
}

function Test-WindowsRule($rules) {
    if ($null -eq $rules -or $rules.Count -eq 0) {
        return $true
    }

    $allowed = $false
    foreach ($rule in $rules) {
        $matches = $true
        if ($null -ne $rule.os -and $null -ne $rule.os.name -and $rule.os.name -ne 'windows') {
            $matches = $false
        }
        if ($null -ne $rule.features) {
            $matches = $false
        }
        if ($matches) {
            $allowed = $rule.action -eq 'allow'
        }
    }
    return $allowed
}

$jvmArgs = [Collections.Generic.List[string]]::new()
$jvmArgs.Add('-Xms512M')
$jvmArgs.Add('-Xmx4G')
$jvmArgs.Add("-Dic2.fidelity.smokeWorld=$smokeWorldName")
foreach ($entry in $version.arguments.jvm) {
    if ($entry -is [string]) {
        $values = @($entry)
        $rules = $null
    } else {
        $values = @($entry.values)
        $rules = $entry.rules
    }

    if (-not (Test-WindowsRule $rules)) {
        continue
    }

    foreach ($value in $values) {
        foreach ($key in $replacements.Keys) {
            $value = $value.Replace($key, $replacements[$key])
        }
        $jvmArgs.Add($value)
    }
}

$gameArgs = @(
    '--username', 'Ic2SmokeTest',
    '--version', 'Forge 1.19.2',
    '--gameDir', $instanceRoot,
    '--assetsDir', (Join-Path $minecraftRoot 'assets'),
    '--assetIndex', '1.19',
    '--uuid', '00000000000000000000000000000001',
    '--accessToken', '0',
    '--clientId', '0',
    '--xuid', '0',
    '--userType', 'legacy',
    '--versionType', 'modified',
    '--width', '854',
    '--height', '480',
    '--launchTarget', 'forgeclient',
    '--fml.forgeVersion', '43.5.2',
    '--fml.mcVersion', '1.19.2',
    '--fml.forgeGroup', 'net.minecraftforge',
    '--fml.mcpVersion', '20220805.130853'
)

$allArgs = @($jvmArgs) + @($version.mainClass) + $gameArgs
function ConvertTo-WindowsCommandLineArgument([string] $argument) {
    if ($argument.Length -gt 0 -and $argument -notmatch '[\s"]') {
        return $argument
    }

    $escaped = $argument -replace '(\\*)"', '$1$1\"'
    $escaped = $escaped -replace '(\\+)$', '$1$1'
    return '"' + $escaped + '"'
}

$argumentLine = (@($allArgs) | ForEach-Object { ConvertTo-WindowsCommandLineArgument $_ }) -join ' '
$stdout = Join-Path $instanceRoot 'launcher-stdout.log'
$stderr = Join-Path $instanceRoot 'launcher-stderr.log'
$pathKeys = @([Environment]::GetEnvironmentVariables().Keys | Where-Object { $_ -ieq 'path' })
if ($pathKeys.Count -gt 1 -and $pathKeys -contains 'PATH') {
    # The Codex host exports both Path and PATH. Windows PowerShell's
    # Start-Process rejects that duplicate even though Windows itself accepts it.
    [Environment]::SetEnvironmentVariable('PATH', $null, [EnvironmentVariableTarget]::Process)
}
$process = Start-Process -FilePath $java -ArgumentList $argumentLine -WorkingDirectory $minecraftRoot -WindowStyle Hidden -RedirectStandardOutput $stdout -RedirectStandardError $stderr -PassThru
Set-Content -LiteralPath (Join-Path $instanceRoot 'smoke.pid') -Value $process.Id
Write-Output "PID=$($process.Id)"
Write-Output "LOG=$(Join-Path $instanceRoot 'logs\latest.log')"
if ($process.WaitForExit(10000)) {
    Write-Output "EARLY_EXIT=$($process.ExitCode) ARGUMENT_CHARS=$($argumentLine.Length)"
}
