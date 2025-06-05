import { IPhim } from "./IPhim";
import { IPhong } from "./IPhong";

export interface ILichChieu {
    id: number;
    phim: IPhim;  // Phim sẽ là một đối tượng của model Phim
    phong: IPhong; // Phong sẽ là một đối tượng của model Phong
    ngayChieu: string;  // Dạng ISO String (yyyy-MM-dd)
    gioBatDau: string;  // Dạng ISO String (HH:mm:ss)
    gioKetThuc: string;  // Dạng ISO String (HH:mm:ss)
    maLichChieu: string;
    giaVeMacDinh: number;  // BigDecimal chuyển thành kiểu number trong Angular
    trangThai: string;
  }