[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

$currentDir = $PSScriptRoot
Set-Location $currentDir

Write-Host "[1/3] Đang chuẩn bị thư mục target/classes..." -ForegroundColor Cyan
if (!(Test-Path "target\classes")) {
    New-Item -ItemType Directory -Force -Path "target\classes" | Out-Null
}

Write-Host "[2/3] Đang biên dịch mã nguồn Java..." -ForegroundColor Cyan
$sources = (Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse).FullName
$classpath = "lib/*"

& javac -encoding UTF-8 -cp $classpath -d "target\classes" $sources
if ($LASTEXITCODE -ne 0) {
    Write-Host "[LỖI] Biên dịch thất bại!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Copy-Item -Path "src\main\resources\*" -Destination "target\classes" -Recurse -Force

Write-Host "[3/3] Đang khởi chạy ứng dụng School Management..." -ForegroundColor Green
Write-Host "========================================================`n" -ForegroundColor Yellow

& java "-Dfile.encoding=UTF-8" "-Dsun.stdout.encoding=UTF-8" "-Dsun.stderr.encoding=UTF-8" -cp "target\classes;lib\*" com.microservicesdemo.java.Main
