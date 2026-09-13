$ErrorActionPreference = "Stop"

function Test-CommandExists {
    param([Parameter(Mandatory = $true)][string] $Name)

    return $null -ne (Get-Command $Name -ErrorAction SilentlyContinue)
}

function Write-Result {
    param(
        [Parameter(Mandatory = $true)][string] $Name,
        [Parameter(Mandatory = $true)][bool] $Passed,
        [string] $Message = ""
    )

    if ($Passed) {
        Write-Host "[OK] $Name $Message" -ForegroundColor Green
    } else {
        Write-Host "[FAIL] $Name $Message" -ForegroundColor Red
    }
}

$failed = $false

$javaExists = Test-CommandExists "java"
Write-Result "Java" $javaExists $(if ($javaExists) { "found" } else { "not found. Install Java 17 or higher." })
if (-not $javaExists) {
    $failed = $true
} else {
    & java -version
}

$mavenExists = Test-CommandExists "mvn"
Write-Result "Maven" $mavenExists $(if ($mavenExists) { "found" } else { "not found. Install Maven 3.9 or higher." })
if (-not $mavenExists) {
    $failed = $true
} else {
    & mvn -v
}

$dockerExists = Test-CommandExists "docker"
Write-Result "Docker" $dockerExists $(if ($dockerExists) { "found" } else { "not found. Optional for later PostgreSQL setup." })

if ($failed) {
    Write-Host ""
    Write-Host "Required local tools are missing. See docs/local-development.md." -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Local development environment is ready." -ForegroundColor Green
