-- Drop database if exists and create a new one
DROP DATABASE IF EXISTS cinema_management;
CREATE DATABASE cinema_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cinema_management;

-- Create nguoi_dung (User) table
CREATE TABLE nguoi_dung (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    mat_khau VARCHAR(255) NOT NULL,
    ten_nd VARCHAR(100),
    ma_nd VARCHAR(20) UNIQUE,
    so_dien_thoai VARCHAR(15),
    ngay_sinh DATE,
    gioi_tinh varchar(20) DEFAULT NULL,
    tuoi INT,
    gioi_han_tuoi varchar(20) DEFAULT 'Không giới hạn',
    do_tuoi_xac_thuc BOOLEAN DEFAULT FALSE COMMENT 'Đánh dấu xem tuổi đã được xác thực chưa',
    CONSTRAINT chk_tuoi CHECK (tuoi >= 0 AND tuoi <= 120)
);

-- Create admin table
CREATE TABLE `admin` (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ma_admin VARCHAR(20) UNIQUE,
    vai_tro VARCHAR(50),
    FOREIGN KEY (ma_admin) REFERENCES nguoi_dung(ma_nd) on update cascade
);

-- Create khach_hang (Customer) table
CREATE TABLE khach_hang (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ma_kh VARCHAR(20) UNIQUE,
    trang_thai varchar(20) DEFAULT 'Hoạt động',
    FOREIGN KEY (ma_kh) REFERENCES nguoi_dung(ma_nd) on update cascade
);


-- Create dat_ve (Booking) table
CREATE TABLE dat_ve (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ma_khach_hang VARCHAR(20),
    ngay_dat DATETIME DEFAULT CURRENT_TIMESTAMP,
    tong_tien DECIMAL(10, 2),
    ma_dat_ve VARCHAR(50) UNIQUE,
    trang_thai varchar(30) DEFAULT 'Chưa thanh toán',
    FOREIGN KEY (ma_khach_hang) REFERENCES khach_hang(ma_kh) on update cascade
);

-- Create thanh_toan (Payment) table
CREATE TABLE thanh_toan (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    so_tien DECIMAL(10, 2) NOT NULL,
    ngay_thanh_toan DATETIME DEFAULT CURRENT_TIMESTAMP,
    hinh_thuc VARCHAR(50),
    ma_thanh_toan VARCHAR(50) UNIQUE,
    trang_thai varchar(20) DEFAULT 'Đang xử lý',
    ma_dat_ve VARCHAR(50),
    FOREIGN KEY (ma_dat_ve) REFERENCES dat_ve(ma_dat_ve) on update cascade
);

-- Create the_loai_phim (Movie Genre) table
CREATE TABLE the_loai_phim (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ten_the_loai VARCHAR(100) NOT NULL,
    ma_the_loai VARCHAR(20) UNIQUE
);

CREATE TABLE tuoi_gioi_han (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ma_gioi_han VARCHAR(10) UNIQUE NOT NULL,
    mo_ta VARCHAR(255),
    tuoi_toi_thieu INT,
    tuoi_toi_da INT,
    mo_ta_chi_tiet TEXT
);


-- Create phim (Movie) table
CREATE TABLE phim (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ma_phim VARCHAR(20) UNIQUE,
    ten_phim VARCHAR(255) NOT NULL,
    thoi_luong INT COMMENT 'Duration in minutes',
    mo_ta TEXT,
    dao_dien VARCHAR(100),
    dien_vien TEXT,
    quoc_gia VARCHAR(50),
    ngay_phat_hanh DATE,
    trang_thai varchar(30) DEFAULT 'Sắp chiếu',
    gioi_han_tuoi_id bigint,
    anh_poster varchar(255),
    FOREIGN KEY (gioi_han_tuoi_id) REFERENCES tuoi_gioi_han(id) on update cascade
);

-- Create phim_the_loai (Movie-Genre Junction) table
CREATE TABLE phim_the_loai (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phim_id bigint NOT NULL,
    the_loai_id bigint NOT NULL,
    UNIQUE (phim_id, the_loai_id),
    FOREIGN KEY (phim_id) REFERENCES phim(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (the_loai_id) REFERENCES the_loai_phim(id) ON DELETE CASCADE ON UPDATE CASCADE
);


ALTER TABLE phim_the_loai MODIFY COLUMN phim_id BIGINT  NOT NULL;
ALTER TABLE phim_the_loai MODIFY COLUMN the_loai_id BIGINT  NOT NULL;

-- Create phong (Room) table
CREATE TABLE phong (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    ten_phong VARCHAR(50) NOT NULL,
    loai_phong VARCHAR(50),
    ma_phong VARCHAR(20) UNIQUE,
    thiet_bi TEXT,
    trang_thai varchar(20) DEFAULT 'Hoạt động',
    suc_chua INT
);

-- Create ghe (Seat) table
CREATE TABLE ghe (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    phong_id bigint NOT NULL,
    ma_ghe VARCHAR(10) NOT NULL,
    loai_ghe VARCHAR(50),
    hang VARCHAR(5),
    so VARCHAR(5),
    trang_thai varchar(20) DEFAULT 'Hoạt động',
    FOREIGN KEY (phong_id) REFERENCES phong(id) ON UPDATE CASCADE,
    UNIQUE KEY (phong_id, ma_ghe)
);

-- Create lich_chieu (Showtime) table
CREATE TABLE lich_chieu (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    phim_id bigint NOT NULL,
    phong_id bigint NOT NULL,
    ngay_chieu DATE NOT NULL,
    gio_bat_dau TIME NOT NULL,
    gio_ket_thuc TIME NOT NULL,
    ma_lich_chieu VARCHAR(20) UNIQUE,
    gia_ve_mac_dinh DECIMAL(10, 2),
    trang_thai varchar(20) DEFAULT 'Đang bán vé',
    FOREIGN KEY (phim_id) REFERENCES phim(id) ON UPDATE CASCADE,
    FOREIGN KEY (phong_id) REFERENCES phong(id) ON UPDATE CASCADE
);

-- Create ve (Ticket) table
CREATE TABLE ve (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    lich_chieu_id bigint NOT NULL,
    ghe_id bigint NOT NULL,
    dat_ve_id bigint,
    ma_ve VARCHAR(20) UNIQUE,
    gia DECIMAL(10, 2) NOT NULL,
    trang_thai varchar(20) DEFAULT 'Còn trống',
    ma_loai_ve VARCHAR(20),
    FOREIGN KEY (lich_chieu_id) REFERENCES lich_chieu(id) ON UPDATE CASCADE,
    FOREIGN KEY (ghe_id) REFERENCES ghe(id) ON UPDATE CASCADE,
    FOREIGN KEY (dat_ve_id) REFERENCES dat_ve(id) ON UPDATE CASCADE,
    UNIQUE KEY (lich_chieu_id, ghe_id)
);

-- Create some indexes for better performance
CREATE INDEX idx_nguoi_dung_email ON nguoi_dung(email);
CREATE INDEX idx_phim_trang_thai ON phim(trang_thai);
CREATE INDEX idx_lich_chieu_ngay ON lich_chieu(ngay_chieu);
CREATE INDEX idx_ve_trang_thai ON ve(trang_thai);
CREATE INDEX idx_dat_ve_trang_thai ON dat_ve(trang_thai);

DELIMITER //
CREATE TRIGGER cap_nhat_tuoi 
BEFORE INSERT ON nguoi_dung
FOR EACH ROW 
BEGIN
    SET NEW.tuoi = TIMESTAMPDIFF(YEAR, NEW.ngay_sinh, CURDATE());
END;//

CREATE TRIGGER cap_nhat_tuoi_khi_sua 
BEFORE UPDATE ON nguoi_dung
FOR EACH ROW 
BEGIN
    SET NEW.tuoi = TIMESTAMPDIFF(YEAR, NEW.ngay_sinh, CURDATE());
END;//
DELIMITER ;

ALTER TABLE phim ADD COLUMN trailer varchar(255);

-- Add sample data (optional)
-- Insert sample users
INSERT INTO nguoi_dung (email, mat_khau, ten_nd, ma_nd, so_dien_thoai, ngay_sinh, gioi_tinh) VALUES
('admin@cinema.com', 'admin123', 'Admin User', 'ADM001', '0901234567', '1990-05-15', 'Nam'),
('user1@example.com', 'pass123', 'Nguyễn Văn A', 'USR001', '0912345678','1995-08-20', 'Nữ'),
('user2@example.com', 'pass123', 'Trần Thị B', 'USR002', '0923456789','1995-08-20', 'Nữ');

INSERT INTO nguoi_dung (email, mat_khau, ten_nd, ma_nd, so_dien_thoai, ngay_sinh, gioi_tinh) VALUES
('admin@example.com', '123456', 'Admin User', 'ADMIN001', '0123456789', '1990-01-01', 'Nam'),
('khach1@example.com', '123456', 'Khách Hàng 1', 'KH001', '0987654321', '1995-05-15', 'Nữ');

INSERT INTO tuoi_gioi_han (ma_gioi_han, mo_ta, tuoi_toi_thieu, tuoi_toi_da, mo_ta_chi_tiet) VALUES
('KGH', 'Không giới hạn độ tuổi', 0, 120, 'Phù hợp với mọi lứa tuổi'),
('P', 'Phổ thông', 0, 120, 'Phim phù hợp với mọi đối tượng'),
('C13', 'Cảnh báo dưới 13', 13, 120, 'Phim có nội dung phù hợp với khán giả từ 13 tuổi trở lên'),
('C16', 'Cảnh báo dưới 16', 16, 120, 'Phim có nội dung phù hợp với khán giả từ 16 tuổi trở lên'),
('C18', 'Cảnh báo dưới 18', 18, 120, 'Phim dành riêng cho khán giả từ 18 tuổi trở lên');


SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE nguoi_dung; -- hoặc DELETE FROM ten_bang;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert admin
INSERT INTO admin (ma_admin, vai_tro) VALUES
('ADMIN001', 'Quản trị viên');

-- Insert customers
INSERT INTO khach_hang (ma_kh) VALUES
('KH001');

-- Insert movie genres
INSERT INTO the_loai_phim (ten_the_loai, ma_the_loai) VALUES
('Hành động', 'ACT'),
('Tình cảm', 'ROM'),
('Kinh dị', 'HOR'),
('Hài hước', 'COM'),
('Khoa học viễn tưởng', 'SCI');

INSERT INTO the_loai_phim (ten_the_loai, ma_the_loai) VALUES
('Chính kịch', 'DRA'),('Phiêu lưu', 'ADV'),('Giả tưởng', 'FAN'),('Hoạt hình', 'ANI'),('Tội phạm', 'CRM'),
('Tài liệu', 'DOC'),('Âm nhạc', 'MUS'),('Chiến tranh', 'WAR'),('Lịch sử', 'HIS'),('Gia đình', 'FAM'),
('Thần thoại', 'MYT'),('Thể thao', 'SPO'),('Viễn Tây', 'WES'),('Bí ẩn', 'MYS'),('Thảm họa', 'DIS');


INSERT INTO phim (ma_phim, ten_phim, thoi_luong, mo_ta, dao_dien, dien_vien, quoc_gia, ngay_phat_hanh, trang_thai, gioi_han_tuoi_id) VALUES
('MOV001', 'Avengers: Endgame', 181, 'Biệt đội siêu anh hùng tập hợp để đánh bại Thanos', 'Anthony Russo, Joe Russo', 'Robert Downey Jr., Chris Evans, Mark Ruffalo', 'Mỹ', '2019-04-26', 'Đang chiếu', 3),
('MOV002', 'Parasite', 132, 'Một gia đình nghèo lần lượt xâm nhập làm việc tại gia đình giàu có', 'Bong Joon-ho', 'Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong', 'Hàn Quốc', '2019-05-30', 'Đang chiếu', 5);

INSERT INTO phim (ma_phim, ten_phim, thoi_luong, mo_ta, dao_dien, dien_vien, quoc_gia, ngay_phat_hanh, trang_thai, gioi_han_tuoi_id) VALUES
('MOV003', 'Inception', 148, 'Một tay trộm chuyên xâm nhập vào giấc mơ để đánh cắp ý tưởng.', 'Christopher Nolan', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 'Mỹ', '2010-07-16', 'Đang chiếu', 3),
('MOV004', 'Parasite', 132, 'Một gia đình nghèo xâm nhập vào cuộc sống của gia đình giàu có.', 'Bong Joon-ho', 'Song Kang-ho, Lee Sun-kyun', 'Hàn Quốc', '2019-05-30', 'Ngừng chiếu', 3),
('MOV005', 'The Dark Knight', 152, 'Batman đối đầu với Joker trong một cuộc chiến tinh thần và đạo đức.', 'Christopher Nolan', 'Christian Bale, Heath Ledger', 'Mỹ', '2008-07-18', 'Đang chiếu', 4),
('MOV006', 'Spirited Away', 125, 'Câu chuyện về một cô bé lạc vào thế giới linh hồn.', 'Hayao Miyazaki', 'Rumi Hiiragi, Miyu Irino', 'Nhật Bản', '2001-07-20', 'Ngừng chiếu', 2),
('MOV007', 'Everything Everywhere All at Once', 139, 'Một người phụ nữ bình thường bị cuốn vào đa vũ trụ để cứu thế giới.', 'Daniel Kwan, Daniel Scheinert', 'Michelle Yeoh, Ke Huy Quan', 'Mỹ', '2022-03-11', 'Đang chiếu', 4);



-- Connect movies with genres
INSERT INTO phim_the_loai (phim_id, the_loai_id) VALUES
(1, 1), -- Avengers: Endgame - Hành động
(1, 5), -- Avengers: Endgame - Khoa học viễn tưởng
(2, 4), -- Parasite - Hài hước
(2, 3); -- Parasite - Kinh dị

INSERT INTO phim_the_loai (phim_id, the_loai_id) VALUES 
(3, 1),
(3, 2);

INSERT INTO phim_the_loai (phim_id, the_loai_id) VALUES 
(5, 2),
(5, 5);

INSERT INTO phim_the_loai (phim_id, the_loai_id) VALUES 
(6, 6),
(6, 7),
(6, 8);

INSERT INTO phim_the_loai (phim_id, the_loai_id) VALUES 
(7, 2),
(7, 1),
(7, 9);

-- Insert rooms
INSERT INTO phong (ten_phong, loai_phong, ma_phong, thiet_bi, trang_thai, suc_chua) VALUES
('Phòng chiếu 1', 'Standard', 'R001', 'Máy chiếu 4K, Âm thanh Dolby Atmos', 'Hoạt động', 100),
('Phòng chiếu 2', 'VIP', 'R002', 'Ghế da, Âm thanh cao cấp', 'Hoạt động', 80);

INSERT INTO phong (ten_phong, loai_phong, ma_phong, thiet_bi, trang_thai, suc_chua) VALUES
('Phòng chiếu 3', 'Standard', 'R003', 'Máy chiếu HD, Âm thanh vòm', 'Hoạt động', 90),
('Phòng chiếu 4', 'Deluxe', 'R004', 'Máy chiếu 2K, Ghế ngả lưng', 'Đang bảo trì', 70),
('Phòng chiếu 5', 'VIP', 'R005', 'Ghế massage, Màn hình cong, Dolby Surround', 'Hoạt động', 60),
('Phòng chiếu 6', 'Standard', 'R006', 'Máy chiếu HD, Hệ thống điều hòa thông minh', 'Hoạt động', 120),
('Phòng chiếu 7', 'IMAX', 'R007', 'Màn hình siêu lớn, Hệ thống loa IMAX', 'Hoạt động', 150);

-- Insert seats (just a few examples)
INSERT INTO ghe (phong_id, ma_ghe, loai_ghe, hang, so, trang_thai) VALUES
(1, 'G1', 'Couple', 'G', '1', 'Hoạt động'), (1, 'G2', 'Couple', 'G', '2', 'Hoạt động'), (1, 'G3', 'Couple', 'G', '3', 'Hoạt động'),
(1, 'G4', 'Couple', 'G', '4', 'Hoạt động'), (1, 'G5', 'Couple', 'G', '5', 'Hoạt động'), (1, 'G6', 'Couple', 'G', '6', 'Hoạt động'),
(1, 'G7', 'Couple', 'G', '7', 'Hoạt động'), (1, 'G8', 'Couple', 'G', '8', 'Hoạt động'), (1, 'G9', 'Couple', 'G', '9', 'Hoạt động'),
(1, 'G10', 'Couple', 'G', '10', 'Hoạt động'), (1, 'G11', 'Couple', 'G', '11', 'Hoạt động'), (1, 'G12', 'Couple', 'G', '12', 'Hoạt động');



-- Insert showtimes
INSERT INTO lich_chieu (phim_id, phong_id, ngay_chieu, gio_bat_dau, gio_ket_thuc, ma_lich_chieu, gia_ve_mac_dinh, trang_thai) VALUES
(1, 1, '2025-03-22', '10:00:00', '13:01:00', 'SC001', 120000, 'Đang bán vé'),
(1, 1, '2025-03-22', '14:00:00', '17:01:00', 'SC002', 120000, 'Đang bán vé'),
(2, 2, '2025-03-22', '10:30:00', '12:42:00', 'SC003', 150000, 'Đang bán vé');

INSERT INTO lich_chieu (phim_id, phong_id, ngay_chieu, gio_bat_dau, gio_ket_thuc, ma_lich_chieu, gia_ve_mac_dinh, trang_thai) VALUES
(1, 1, '2024-03-27', '10:00:00', '13:01:00', 'SC001', 120000, 'Đang bán vé'),
(1, 1, '2024-03-27', '14:00:00', '17:01:00', 'SC002', 120000, 'Đang bán vé'),
(2, 2, '2024-03-27', '10:30:00', '12:42:00', 'SC003', 150000, 'Đang bán vé');

INSERT INTO lich_chieu (phim_id, phong_id, ngay_chieu, gio_bat_dau, gio_ket_thuc, ma_lich_chieu, gia_ve_mac_dinh, trang_thai) VALUES
-- Inception
(3, 1, '2024-03-27', '08:00:00', '10:28:00', 'SC004', 120000, 'Đang bán vé'),
(3, 1, '2024-03-27', '11:00:00', '13:28:00', 'SC005', 120000, 'Đang bán vé'),

-- The Dark Knight
(5, 1, '2024-03-27', '14:00:00', '16:32:00', 'SC006', 120000, 'Đang bán vé'),
(5, 1, '2024-03-27', '17:00:00', '19:32:00', 'SC007', 120000, 'Đang bán vé'),

-- Spirited Away
(6, 1, '2024-03-27', '20:00:00', '22:05:00', 'SC008', 100000, 'Đang bán vé'),
(6, 1, '2024-03-27', '22:30:00', '00:35:00', 'SC009', 100000, 'Đang bán vé'),

-- Everything Everywhere All at Once
(7, 1, '2024-03-27', '09:00:00', '11:19:00', 'SC010', 130000, 'Đang bán vé'),
(7, 1, '2024-03-27', '12:00:00', '14:19:00', 'SC011', 130000, 'Đang bán vé');

-- Insert tickets (available tickets for the showtimes)
INSERT INTO ve (lich_chieu_id, ghe_id, ma_ve, gia, trang_thai) VALUES
(1, 41, 'TK041', 120000, 'Còn trống'),
(1, 42, 'TK042', 120000, 'Còn trống'),
(1, 43, 'TK043', 120000, 'Còn trống'),
(1, 44, 'TK044', 120000, 'Còn trống'),
(1, 45, 'TK045', 120000, 'Còn trống'),
(1, 46, 'TK046', 120000, 'Còn trống'),
(1, 47, 'TK047', 120000, 'Còn trống'),
(1, 48, 'TK048', 120000, 'Còn trống'),
(1, 49, 'TK049', 120000, 'Còn trống'),
(1, 50, 'TK050', 120000, 'Còn trống');



-- Sample booking and payment (optional)
INSERT INTO dat_ve (ma_khach_hang, ngay_dat, tong_tien, ma_dat_ve, trang_thai)
VALUES ('KH001', '2024-03-27 15:30:00', 240000, 'BK001', 'Chưa thanh toán');

-- Update the booking status after payment
UPDATE dat_ve 
SET trang_thai = 'Đã thanh toán' 
WHERE ma_dat_ve = 'BK001';


-- Associate tickets with the booking
UPDATE ve 
SET dat_ve_id = (SELECT id FROM dat_ve WHERE ma_dat_ve = 'BK001'), 
    trang_thai = 'Đã bán'
WHERE id IN (1, 2);  -- Assuming these are two specific tickets from previous ticket insertions

INSERT INTO thanh_toan (ma_dat_ve, so_tien, ngay_thanh_toan, hinh_thuc, ma_thanh_toan, trang_thai)
VALUES ('BK001', 240000,'2024-03-27 15:45:00', 'Chuyển khoản', 'PAY001','Thành công');

SELECT 
    p.ten_phong,
    COUNT(g.id) AS tong_so_ghe,
    SUM(CASE WHEN v.trang_thai = 'Còn trống' THEN 1 ELSE 0 END) AS ghe_trong
FROM phong p
JOIN ghe g ON p.id = g.phong_id
LEFT JOIN ve v ON g.id = v.ghe_id
WHERE p.id = 1
GROUP BY p.ten_phong;

SELECT 
    p.ten_phong, 
    COUNT(g.id) AS so_ghe_trong
FROM ghe g
JOIN phong p ON g.phong_id = p.id
WHERE g.trang_thai = 'Hoạt động'
    AND g.id NOT IN (
        SELECT ve.ghe_id 
        FROM ve
        WHERE ve.trang_thai IN ('Đã đặt', 'Đã bán')
    )
GROUP BY p.ten_phong;

SET SQL_SAFE_UPDATES = 0;
UPDATE nguoi_dung SET gioi_tinh = 'NAM' WHERE gioi_tinh = 'Nam';
UPDATE nguoi_dung SET gioi_tinh = 'NU' WHERE gioi_tinh = 'Nữ';
UPDATE nguoi_dung SET gioi_tinh = 'KHAC' WHERE gioi_tinh = 'Khác';
SET SQL_SAFE_UPDATES = 1;

select gioi_han_tuoi_id from phim ;

SELECT * FROM ghe g
    WHERE g.phong_id = 1 
    AND g.id NOT IN (
        SELECT v.ghe_id FROM ve v 
        WHERE v.lich_chieu_id = 1
    );
    
SELECT ghe.ma_ghe
FROM ve
JOIN ghe ON ve.ghe_id = ghe.id
WHERE ve.lich_chieu_id = 2 AND ve.trang_thai != 'Còn trống';

SELECT *
FROM ve
WHERE lich_chieu_id = 1
  AND ghe_id = 'B1'
  AND trang_thai = 'Còn trống'
LIMIT 1;

SELECT * FROM ve WHERE lich_chieu_id = 1 AND ghe_id = 1 AND trang_thai = 'Còn trống'  LIMIT 1