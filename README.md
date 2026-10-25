# school-management

Đồ án Lập trình hướng đối tượng – Nhóm 1: **Hệ thống quản lý trường học** chạy trên console, viết bằng Java thuần (JDK 17+), chỉ dùng thêm thư viện Jackson để đọc/ghi JSON.

## Cách chạy

| Hệ điều hành | Lệnh |
|---|---|
| Windows | `run.bat` hoặc `run.ps1` |
| Linux / macOS | `./run.sh` |

Script tự biên dịch vào `target/classes` rồi chạy `com.microservicesdemo.java.Main`.

**Dữ liệu** được lưu trong thư mục `data/` (4 file JSON). Lần chạy đầu chưa có `data/` thì chương trình lấy dữ liệu mẫu trong `src/main/resources/mock/`. Sau mỗi lần thêm/sửa/xoá, dữ liệu được ghi lại ngay nên tắt chương trình không bị mất. Muốn quay về dữ liệu mẫu thì xoá thư mục `data/`.

Quy ước nhập liệu:
- Nhập `0` ở bất kỳ bước nào để huỷ thao tác đang làm. Khi giá trị thật sự bằng 0 (điểm, GPA, số năm kinh nghiệm) thì nhập `0.0` hoặc `00`.
- Ngày nhập theo `dd-MM-yyyy`, năm đủ 4 chữ số (ví dụ `05-06-2005` hoặc `5-6-2005`); năm sinh từ 1900 trở đi.
- Số thập phân dùng dấu chấm hoặc dấu phẩy: `7.5` hoặc `7,5`.

## Chức năng

| Menu | Thao tác |
|---|---|
| 1. Quản lý sinh viên | Thêm, sửa, xoá, xem, tìm theo tên (không phân biệt dấu), thống kê xếp loại học lực, xem danh sách đã sắp xếp (theo tên / theo GPA) |
| 2. Quản lý giảng viên | Thêm, sửa, xoá, xem, tìm theo tên hoặc mã GV, thống kê theo khoa / học vị |
| 3. Quản lý nhân viên | Thêm, sửa, xoá, xem, tìm theo tên, thống kê lương theo phòng ban |
| 4. Quản lý lớp học phần | Thêm, sửa, xoá lớp; xem, tìm theo mã/tên môn; xem chi tiết lớp; ghi danh sinh viên; nhập/sửa điểm; xoá sinh viên khỏi lớp; thống kê sĩ số và điểm |
| 5. Báo cáo & thống kê | Xếp loại học lực; lương theo phòng ban; Top 5 GPA; Top 5 thu nhập; lương theo vai trò; thống kê lớp học phần (sĩ số, chỗ trống, điểm TB); nhân sự theo vai trò |
| 6. Tìm kiếm toàn trường | Tìm một lần trên cả sinh viên, giảng viên, nhân viên |

Ràng buộc nghiệp vụ:
- Không xoá được sinh viên đang có trong lớp học phần, giảng viên đang phụ trách lớp, hoặc nhân viên đang là hồ sơ nhân sự (`staffId`) của giảng viên.
- Lớp không nhận thêm khi đã đủ sĩ số. Mỗi sinh viên chỉ được ghi danh một lần vào một lớp. Không giảm được sĩ số tối đa xuống dưới số sinh viên đang học.
- Điểm trong lớp học phần lưu riêng, không tự tính lại GPA của sinh viên.

## Cấu trúc mã nguồn

```
src/main/java/com/microservicesdemo/java/
├── Main.java                 nạp dữ liệu, mở menu
├── models/                   lớp thực thể + interface
│   ├── Person (abstract) ── Student, Staff, Lecturer
│   ├── CourseClass ◆── Enrollment ──◇ Student
│   ├── Searchable, Payable   (interface)
│   └── PersonType, PersonalInfo
├── data/                     DataStore (Singleton), MockDataLoader, CourseClassRecord
├── factory/                  PersonFactory + StudentDetails/StaffDetails/LecturerDetails
├── strategy/                 SortStrategy + SortByName, SortByGpaDesc, SortBySalaryDesc
├── services/                 Dashboard (menu) + service từng nhóm chức năng + ReportService
├── exceptions/               5 exception tự định nghĩa
└── util/                     ConsoleInput (nhập liệu an toàn), IdGenerator, TextUtils
```

Phân tầng và xử lý ngoại lệ:
- **Model**: setter kiểm tra dữ liệu, sai thì ném `InvalidDataException`.
- **Service**: ném `EntityNotFoundException` / `BusinessRuleException`, bắt `InputCancelledException` để báo huỷ.
- **DataStore**: gói `IOException` thành `DataStorageException`.
- **Dashboard / Main**: bắt lỗi lưu file, lỗi hết dữ liệu nhập và lỗi bất ngờ, in thông báo rồi quay về menu.

Không có khối `catch` rỗng.

## Đối chiếu checklist OOP

| Yêu cầu | Đáp ứng ở đâu |
|---|---|
| 100% thuộc tính `private`, có getter/setter | Mọi lớp trong `models/` |
| Setter có validation (≥ 3 lớp) | `Person` (họ tên, ngày sinh, giới tính, SĐT, email), `Student` (GPA 0–10, năm nhập học), `Staff`/`Lecturer` (lương > 0, số năm KN), `CourseClass` (tín chỉ, sĩ số), `Enrollment` (điểm 0–10) |
| Mỗi lớp thực thể ≥ 2 constructor, dùng `this()` | `Person(id, PersonalInfo)`, `Student/Staff/Lecturer(id, PersonalInfo, ...)`, `CourseClass(code, subject, credits, lecturer)`, `Enrollment(student)` đều gọi `this(...)` sang constructor đầy đủ |
| ≥ 2 nhóm nạp chồng phương thức | `ConsoleInput.readInt(...)` / `readDouble(...)` / `readRequiredText(...)`; `CourseClass.enroll(Student)` / `enroll(Student, double)`; `EntityNotFoundException(...)` |
| `static` / `static final` | Hằng số `Student.MAX_GPA`, `Person.DATE_PATTERN`, `CourseClass.MAX_CAPACITY`...; `DataStore.getInstance()`; các hàm `validateXxx` |
| Aggregation (≥ 2) + Composition (≥ 1) | Aggregation: `CourseClass` → `Lecturer`, `Enrollment` → `Student`. Composition: `CourseClass` ◆ `Enrollment` (constructor package-private, xoá lớp thì mất lượt đăng ký) |
| Kế thừa: 1 cha – 3 con | `Person` → `Student`, `Staff`, `Lecturer` |
| Override `toString()`, `equals()` + ≥ 2 phương thức nghiệp vụ | `toString/equals/hashCode` ở `Person`, `CourseClass`, `Enrollment`; nghiệp vụ: `getRoleName()`, `describe()`, `searchText()`, `calculateMonthlySalary()` |
| Lớp trừu tượng có phương thức trừu tượng | `Person`: `getRoleName()`, `describe()` |
| ≥ 2 interface, có `default method` | `Searchable` (default `matches`), `Payable` (default `formatSalary`, static `validateSalary`), `SortStrategy` (default `sort`) |
| Đa hình qua collection kiểu cha | `List<Person>`: tìm kiếm toàn trường, đếm nhân sự theo vai trò. `List<Payable>`: Top thu nhập, lương theo vai trò |
| ≥ 2 custom exception, không catch rỗng | `InvalidDataException`, `EntityNotFoundException`, `BusinessRuleException`, `DataStorageException`, `InputCancelledException` |
| Design pattern (≥ 2/3) | **Singleton**: `DataStore`. **Factory**: `PersonFactory`. **Strategy**: `SortStrategy` và 3 lớp sắp xếp |
| Lưu/đọc file | `DataStore.load()` / `save()`: JSON trong `data/`, ghi qua file tạm để không hỏng file cũ |
| Menu console, 4 nhóm CRUD, ≥ 2 báo cáo | `DashboardService`: 4 nhóm quản lý (mỗi nhóm có thêm/sửa/xoá/tìm kiếm/thống kê) + 7 báo cáo |
| Không crash khi nhập sai | `ConsoleInput` hỏi lại khi nhập chữ vào ô số, số dạng lạ (`NaN`, `1e1`), ngoài khoảng, ngày sai hoặc năm viết tắt, STT không tồn tại; hết dữ liệu nhập thì thoát êm; file dữ liệu hỏng thì báo rõ file và lý do |
