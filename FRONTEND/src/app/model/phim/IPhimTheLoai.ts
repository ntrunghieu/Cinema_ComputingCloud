import { IPhim } from './IPhim';
import { ITheLoaiPhim } from './ITheLoaiPhim';
export interface IPhimTheLoai {
    id?: number;
    phim: IPhim;
    theLoai: ITheLoaiPhim;
  }