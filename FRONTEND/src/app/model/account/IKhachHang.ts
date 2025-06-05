import {INguoiDung} from './INguoiDung';
export interface IKhachHang {
    id?: number;
    maKh: string;
    trangThai?: string;
    nguoiDung?: INguoiDung;
  }