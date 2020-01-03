package stackjava.com.restful.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stackjava.com.restful.service.ChiTietDonDatHangService;
import stackjava.com.restful.service.DonDatHangService;
import stackjava.com.restful.service.SanPhamService;
import stackjava.com.restful.service.ServiceResult;
import stackjava.com.restful.entities.ChiTietDonDatHang;
import stackjava.com.restful.entities.DonDatHang;
import stackjava.com.restful.entities.SanPham;;

@RestController
@RequestMapping("sanpham")
@CrossOrigin("*")
public class SanPhamController {
	@Autowired
	private SanPhamService SanPhamService;
	@Autowired
	private ChiTietDonDatHangService chiTietDonDatHangService;
	@Autowired
	private DonDatHangService donDatHangService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenSanPham", required = false) String tenSanPham,
								 @RequestParam(value = "toiThieu", required = false) Double toiThieu,
								 @RequestParam(value = "toiDa", required = false) Double toiDa,
							     @RequestParam(value = "loaiSanPhamID", required = false) Integer loaiSanPhamID,
							     @RequestParam(value = "trangThaiAnHien", required = false) Boolean trangThaiAnHien) throws ParseException{

        return ResponseEntity.ok(SanPhamService.findByProperty(tenSanPham, toiThieu, toiDa, loaiSanPhamID, trangThaiAnHien));
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody SanPham SanPham) {
//    	 if (SanPhamService.findById(id) == null) {
//             ResponseEntity.badRequest().build();
//         }
        return ResponseEntity.ok(SanPhamService.save(SanPham));
    }
    @GetMapping("/themVaoGio")
    public ResponseEntity themVaoGio(@RequestParam(value = "sanPhamID", required = false) Integer sanPhamID,
						    		 @RequestParam(value = "soLuong", required = false) Integer soLuong,
						    		 @RequestParam(value = "giaXuat", required = false) Double giaXuat,
						    		 @RequestParam(value = "taiKhoanKhachHangID", required = false) Integer taiKhoanKhachHangID) {

    	DonDatHang gioHang = new DonDatHang();
    	gioHang	= donDatHangService.getGioHang(taiKhoanKhachHangID);
    	
    	ChiTietDonDatHang chiTietDonDatHang = new ChiTietDonDatHang();
        chiTietDonDatHang.setSanPhamID(sanPhamID);
        chiTietDonDatHang.setSoLuong(soLuong);
        chiTietDonDatHang.setGiaXuat(giaXuat);
    	if(gioHang == null) {
    		 DonDatHang donDatHang = new DonDatHang();
             donDatHang.setTaiKhoanDatHangID(taiKhoanKhachHangID);
             donDatHang.setTinhTrang(0);
             donDatHang.setChiTietDonDatHangs(chiTietDonDatHang);
             donDatHangService.save(donDatHang);
    	}
    	else {
    		chiTietDonDatHang.setDonDatHangID(gioHang.getDonDatHangID());
            ChiTietDonDatHang sanPhamDaCo = chiTietDonDatHangService.findBySanPham(gioHang.getDonDatHangID(), sanPhamID);
            if (sanPhamDaCo != null) {
                sanPhamDaCo.setSoLuong(sanPhamDaCo.getSoLuong() + soLuong);
                sanPhamDaCo.setGiaXuat(giaXuat);
                chiTietDonDatHangService.save(sanPhamDaCo);
            }
            else
                chiTietDonDatHangService.save(chiTietDonDatHang);
    	}
        return ResponseEntity.ok(chiTietDonDatHang);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<SanPham>> findById(@PathVariable int id) {
        if (SanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(SanPhamService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPham> update(@PathVariable int id, @Valid @RequestBody SanPham SanPham) {
        if (SanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(SanPhamService.save(SanPham));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (SanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        SanPhamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
