import {INguoiDung} from './INguoiDung';
export interface Admin {

    id: number,
    maAdmin: string,
    vaiTro: string,
    nguoiDung?: INguoiDung
  }