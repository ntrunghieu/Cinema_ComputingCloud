export interface INguoiDung {
    id?: number; 
  
    email: string;
    matKhau: string;
  
    tenNd?: string;
    maNd: string;
  
    soDienThoai?: string;
    ngaySinh?: string;         // ISO string từ LocalDate
    gioiTinh?: string;
    tuoi?: number;
  
    gioiHanTuoi?: string;
    doTuoiXacThuc?: boolean;
  }