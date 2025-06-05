import { IPhong } from "./IPhong";

export interface IGhe {
    id: number;
    phong: IPhong; // Sử dụng Phong nếu đã có model cho Phong
    maGhe: string;
    loaiGhe?: string;
    hang?: string;
    so?: string;
    trangThai?: string;
  }