$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Resolve-Path (Join-Path $scriptDir "..")

Push-Location $projectRoot
try {
    & powershell -ExecutionPolicy Bypass -File ".\scripts\check-env.ps1"
    & mvn spring-boot:run
} finally {
    Pop-Location
}
