# ChattingApp

## Mục lục
- [Giới thiệu](#Giới-Thiệu)
- [Chức năng của Hệ thống](#Chức-năng-chính)
- [Cài đặt](#Thiết-lập-và-Build-local)


## **Giới Thiệu**
- ChattingApp là một ứng dụng chat đơn giản cho phép người dùng đăng ký, đăng nhập, và trò chuyện với người dùng khác trong thời gian thực. Ứng dụng được phát triển trên nền tảng Android, sử dụng Java và Firebase để quản lý người dùng, tin nhắn, và thông báo đẩy.

## Chức năng chính

### 1. **Đăng ký & Đăng nhập**
   - Người dùng có thể đăng ký bằng email và mật khẩu.
   - Người dùng đã đăng ký có thể đăng nhập bằng thông tin tài khoản đã tạo.
   - Thông tin người dùng được lưu trữ an toàn trong Firebase Authentication.

### 2. **Danh sách người dùng**
   - Sau khi đăng nhập, người dùng có thể xem danh sách các người dùng khác.
   - Người dùng có thể chọn người dùng bất kỳ để bắt đầu cuộc trò chuyện.

### 3. **Trò chuyện thời gian thực**
   - Người dùng có thể gửi tin nhắn văn bản trong thời gian thực.
   - Hỗ trợ gửi tin nhắn hình ảnh và video (nếu có).
   - Tin nhắn sẽ được lưu trữ trong Firebase Firestore.

### 4. **Thông báo đẩy (Push Notifications)**
   - Khi có tin nhắn mới, người dùng sẽ nhận được thông báo đẩy qua Firebase Cloud Messaging (FCM).
   - Thông báo xuất hiện ngay cả khi ứng dụng đang chạy nền hoặc đóng.

### 5. **Tùy chỉnh giao diện người dùng**
   - Hỗ trợ chế độ sáng/tối cho trải nghiệm người dùng tốt hơn.
   - Các tin nhắn được phân biệt bằng màu sắc dựa trên người gửi (người dùng hiện tại hoặc người dùng khác).

## Yêu cầu hệ thống

- Android Studio (phiên bản mới nhất)
- Firebase Console (để tạo project và thiết lập Firebase Authentication, Firestore và Cloud Messaging)
- Java Development Kit (JDK 1.8 hoặc cao hơn)
- Kết nối internet

## Thiết lập và Build local

### 1. **Download Android studio**
- Tải Android studio tại link https://developer.android.com/studio?gad_source=1&gclid=Cj0KCQjw99e4BhDiARIsAISE7P91JX1e9ujnznxk085ChQ_R9sbzUz0Cv9UbZXCrYZZ04r10iJpROe0aAsocEALw_wcB&gclsrc=aw.ds&hl=vi
  
### 2. **Clone project**

   Sử dụng lệnh sau để clone project về máy của bạn:

```bash
   git clone https://github.com/kha1605/Chat-App.git
```
### 3. **Thiết lập Firebase**
- Tạo một project trên Firebase Console.
- Thêm ứng dụng Android vào project Firebase bằng package name của bạn.
- Tải file google-services.json về và thêm vào thư mục app/ của project.
### 4. **Cài đặt các thư viện cần thiết**
- Mở file build.gradle (Module: app) và kiểm tra xem bạn đã cài đặt đầy đủ các thư viện Firebase hay chưa:

```bash
  implementation 'com.google.firebase:firebase-auth:21.0.1'
  implementation 'com.google.firebase:firebase-firestore:24.0.1'
  implementation 'com.google.firebase:firebase-messaging:23.0.0'
```
- Sau đó, đồng bộ hóa project (Sync Project with Gradle Files) để tải các dependencies cần thiết.
- 
### 5. **Cấu hình Firebase Authentication**
- Trên Firebase Console, vào mục Authentication, bật phương thức đăng nhập bằng Email/Password.
- Vào mục Firestore Database, tạo cơ sở dữ liệu và thiết lập các quy tắc bảo mật để cho phép đọc/ghi dữ liệu cho các người dùng đã đăng nhập.
### 5. **Cấu hình Firebase Cloud Messaging (FCM)**
- Vào mục Cloud Messaging trên Firebase Console và tạo khóa thông báo đẩy (Server key).
- Đảm bảo bạn đã cài đặt thư viện firebase-messaging và cấu hình FCM trong AndroidManifest.xml.
### 6. **Build và chạy ứng dụng**
- Kết nối thiết bị Android hoặc bật trình giả lập (Emulator).
- Nhấn nút "Run" trong Android Studio để build và chạy ứng dụng trên thiết bị của bạn.

