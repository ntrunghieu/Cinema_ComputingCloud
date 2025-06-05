import {INguoiDung} from '../account/INguoiDung';
import { ITuoiGioiHan } from '../account/ITuoiGioiHan';
export interface IPhim {
    id?: number;
    maPhim: string;
    tenPhim: string;
  
    thoiLuong?: number;       // đơn vị phút
    moTa?: string;
    daoDien?: string;
    dienVien?: string;
    quocGia?: string;
  
    ngayPhatHanh?: string;    // ISO string format: 'YYYY-MM-DD'
    trangThai?: string;       // VD: 'Sắp chiếu', 'Đang chiếu', v.v.
    anh?: string;
    trailer?: string;           
  
    gioiHanTuoi?: ITuoiGioiHan;  // quan hệ ManyToOne
  }