-- Table: nguoi_dung
CREATE TABLE nguoi_dung (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    mat_khau VARCHAR(255) NOT NULL,
    ten_nd VARCHAR(100),
    ma_nd VARCHAR(20) UNIQUE,
    so_dien_thoai VARCHAR(15),
    ngay_sinh DATE,
    gioi_tinh VARCHAR(20) DEFAULT NULL,
    tuoi INT,
    gioi_han_tuoi VARCHAR(20) DEFAULT 'Không giới hạn',
    do_tuoi_xac_thuc BOOLEAN DEFAULT FALSE,
    CONSTRAINT chk_tuoi CHECK (tuoi >= 0 AND tuoi <= 120)
);

COMMENT ON COLUMN nguoi_dung.do_tuoi_xac_thuc IS 'Đánh dấu xem tuổi đã được xác thực chưa';

-- Table: admin
CREATE TABLE admin (
    id BIGSERIAL PRIMARY KEY,
    ma_admin VARCHAR(20) UNIQUE,
    vai_tro VARCHAR(50),
    FOREIGN KEY (ma_admin) REFERENCES nguoi_dung(ma_nd) ON UPDATE CASCADE
);

-- Table: khach_hang
CREATE TABLE khach_hang (
    id BIGSERIAL PRIMARY KEY,
    ma_kh VARCHAR(20) UNIQUE,
    trang_thai VARCHAR(20) DEFAULT 'Hoạt động',
    FOREIGN KEY (ma_kh) REFERENCES nguoi_dung(ma_nd) ON UPDATE CASCADE
);

-- Table: dat_ve
CREATE TABLE dat_ve (
    id BIGSERIAL PRIMARY KEY,
    ma_khach_hang VARCHAR(20),
    ngay_dat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tong_tien DECIMAL(10, 2),
    ma_dat_ve VARCHAR(50) UNIQUE,
    trang_thai VARCHAR(30) DEFAULT 'Chưa thanh toán',
    FOREIGN KEY (ma_khach_hang) REFERENCES khach_hang(ma_kh) ON UPDATE CASCADE
);

-- Table: thanh_toan
CREATE TABLE thanh_toan (
    id BIGSERIAL PRIMARY KEY,
    so_tien DECIMAL(10, 2) NOT NULL,
    ngay_thanh_toan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hinh_thuc VARCHAR(50),
    ma_thanh_toan VARCHAR(50) UNIQUE,
    trang_thai VARCHAR(20) DEFAULT 'Đang xử lý',
    ma_dat_ve VARCHAR(50),
    FOREIGN KEY (ma_dat_ve) REFERENCES dat_ve(ma_dat_ve) ON UPDATE CASCADE
);

-- Table: the_loai_phim
CREATE TABLE the_loai_phim (
    id BIGSERIAL PRIMARY KEY,
    ten_the_loai VARCHAR(100) NOT NULL,
    ma_the_loai VARCHAR(20) UNIQUE
);

-- Table: tuoi_gioi_han
CREATE TABLE tuoi_gioi_han (
    id BIGSERIAL PRIMARY KEY,
    ma_gioi_han VARCHAR(10) UNIQUE NOT NULL,
    mo_ta VARCHAR(255),
    tuoi_toi_thieu INT,
    tuoi_toi_da INT,
    mo_ta_chi_tiet TEXT
);

-- Table: phim
CREATE TABLE phim (
    id BIGSERIAL PRIMARY KEY,
    ma_phim VARCHAR(20) UNIQUE,
    ten_phim VARCHAR(255) NOT NULL,
    thoi_luong INT,
    mo_ta TEXT,
    dao_dien VARCHAR(100),
    dien_vien TEXT,
    quoc_gia VARCHAR(50),
    ngay_phat_hanh DATE,
    trang_thai VARCHAR(30) DEFAULT 'Sắp chiếu',
    gioi_han_tuoi_id BIGINT,
    anh_poster VARCHAR(255),
    FOREIGN KEY (gioi_han_tuoi_id) REFERENCES tuoi_gioi_han(id) ON UPDATE CASCADE
);

COMMENT ON COLUMN phim.thoi_luong IS 'Duration in minutes';

-- Table: phim_the_loai
CREATE TABLE phim_the_loai (
    id BIGSERIAL PRIMARY KEY,
    phim_id BIGINT NOT NULL,
    the_loai_id BIGINT NOT NULL,
    UNIQUE (phim_id, the_loai_id),
    FOREIGN KEY (phim_id) REFERENCES phim(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (the_loai_id) REFERENCES the_loai_phim(id) ON DELETE CASCADE ON UPDATE CASCADE
);
