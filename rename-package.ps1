# 包名重构脚本：将 org.dromara 替换为 cn.superbase
# 使用方法：在项目根目录执行 .\rename-package.ps1

$ErrorActionPreference = "Stop"
$projectRoot = $PSScriptRoot

Write-Host "开始包名重构..." -ForegroundColor Green
Write-Host "项目路径: $projectRoot" -ForegroundColor Cyan

# 1. 替换所有Java文件中的包名和import语句
Write-Host "`n[1/3] 替换Java文件..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Path $projectRoot -Filter "*.java" -Recurse -File
$javaCount = 0
foreach ($file in $javaFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $newContent = $content -replace 'org\.dromara', 'cn.superbase'
    
    if ($content -ne $newContent) {
        Set-Content -Path $file.FullName -Value $newContent -Encoding UTF8 -NoNewline
        $javaCount++
        Write-Host "  ✓ $($file.FullName)" -ForegroundColor Gray
    }
}
Write-Host "已处理 $javaCount 个Java文件" -ForegroundColor Green

# 2. 替换所有pom.xml文件中的groupId
Write-Host "`n[2/3] 替换pom.xml文件..." -ForegroundColor Yellow
$pomFiles = Get-ChildItem -Path $projectRoot -Filter "pom.xml" -Recurse -File
$pomCount = 0
foreach ($file in $pomFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $newContent = $content -replace 'org\.dromara', 'cn.superbase'
    
    if ($content -ne $newContent) {
        Set-Content -Path $file.FullName -Value $newContent -Encoding UTF8 -NoNewline
        $pomCount++
        Write-Host "  ✓ $($file.FullName)" -ForegroundColor Gray
    }
}
Write-Host "已处理 $pomCount 个pom.xml文件" -ForegroundColor Green

# 3. 移动目录结构
Write-Host "`n[3/3] 移动目录结构..." -ForegroundColor Yellow
$orgDirs = Get-ChildItem -Path $projectRoot -Directory -Recurse | Where-Object { $_.Name -eq "org" -and (Test-Path (Join-Path $_.FullName "dromara")) }

foreach ($orgDir in $orgDirs) {
    $dromaraDir = Join-Path $orgDir.FullName "dromara"
    
    if (Test-Path $dromaraDir) {
        $parentDir = $orgDir.Parent.FullName
        $cnDir = Join-Path $parentDir "cn"
        $superbaseDir = Join-Path $cnDir "superbase"
        
        # 创建目标目录
        if (-not (Test-Path $cnDir)) {
            New-Item -Path $cnDir -ItemType Directory -Force | Out-Null
        }
        
        # 移动dromara目录到superbase
        Write-Host "  移动: $dromaraDir" -ForegroundColor Gray
        Write-Host "  到:   $superbaseDir" -ForegroundColor Gray
        Move-Item -Path $dromaraDir -Destination $superbaseDir -Force
        
        # 删除空的org目录
        if ((Get-ChildItem -Path $orgDir.FullName).Count -eq 0) {
            Remove-Item -Path $orgDir.FullName -Force
            Write-Host "  删除空目录: $($orgDir.FullName)" -ForegroundColor Gray
        }
    }
}

Write-Host "`n包名重构完成！" -ForegroundColor Green
Write-Host "已将 org.dromara 替换为 cn.superbase" -ForegroundColor Cyan
Write-Host "`n请执行以下步骤验证：" -ForegroundColor Yellow
Write-Host "1. 使用IDE重新加载项目" -ForegroundColor White
Write-Host "2. 执行 mvn clean compile 检查编译是否成功" -ForegroundColor White
Write-Host "3. 检查关键文件的import语句是否正确" -ForegroundColor White
