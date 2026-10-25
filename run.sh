#!/usr/bin/env bash
# Biên dịch và chạy chương trình trên Linux/macOS (tương đương run.bat / run.ps1 trên Windows).
set -e
cd "$(dirname "$0")"

echo "[1/3] Đang chuẩn bị thư mục target/classes..."
mkdir -p target/classes

echo "[2/3] Đang biên dịch mã nguồn Java..."
javac -encoding UTF-8 -cp "lib/*" -d target/classes $(find src/main/java -name '*.java')
cp -r src/main/resources/* target/classes/

echo "[3/3] Đang khởi chạy ứng dụng School Management..."
echo "========================================================"
java -Dfile.encoding=UTF-8 -cp "target/classes:lib/*" com.microservicesdemo.java.Main
