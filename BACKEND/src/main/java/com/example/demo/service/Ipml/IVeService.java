package com.example.demo.service.Ipml;

import com.example.demo.entity.ve.DatVe;
import com.example.demo.entity.ve.Ve;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IVeService {

    Ve taoVe(Ve ve);

    List<Ve> layDanhSachVe();

    List<Ve> timVeTheoLichChieu(Long lichChieuId);

    List<Ve> timVeTheoGhe(Long gheId);

    Optional<Ve> timVeTheoMa(String maVe);

    List<Ve> timVeTheoTrangThai(String trangThai);

    List<Ve> timVeTheoDatVe(Long datVeId);

    Ve capNhatTrangThaiVe(String maVe, String trangThai);

    Ve datVe(String maVe, Long datVeId);
}
