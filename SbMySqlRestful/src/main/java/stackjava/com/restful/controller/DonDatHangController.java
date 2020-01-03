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
import stackjava.com.restful.service.DonDatHangService;
import stackjava.com.restful.service.ServiceResult;
import stackjava.com.restful.entities.ChiTietDonDatHang;
import stackjava.com.restful.entities.DonDatHang;;

@RestController
@RequestMapping("dondathang")
@CrossOrigin("*")
public class DonDatHangController {
	@Autowired
	private DonDatHangService donDatHangService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "soHieuDon", required = false) String soHieuDon,
								 @RequestParam(value = "taiKhoanDatHangID", required = false) Integer taiKhoanDatHangID,
								 @RequestParam(value = "taiKhoanNhanVienID", required = false) Integer taiKhoanNhanVienID,
							     @RequestParam(value = "tuNgay", required = false) String tuNgay,
							     @RequestParam(value = "denNgay", required = false) String denNgay,
							     @RequestParam(value = "tinhTrang", required = false) Integer tinhTrang) throws ParseException{

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date tungay = null;
    	Date denngay = null;
    	if(tuNgay != null)
    		tungay = sdf.parse(tuNgay);
    	if(denNgay != null)
    		denngay = sdf.parse(denNgay);
        return ResponseEntity.ok(donDatHangService.findByProperty(soHieuDon, taiKhoanDatHangID, taiKhoanNhanVienID, tungay, denngay, tinhTrang));
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DonDatHang donDatHang) {
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//    	Date henlaytu = null;
//    	Date henlayden = null;
//    	if(henlaytu != null)
//    		henlaytu = sdf.parse(donDatHang.hen);
//    	if(denNgay != null)
//    		denngay = sdf.parse(denNgay);
    	donDatHangService.save(donDatHang);
    	if (donDatHang.getTinhTrang()==1) {
    		donDatHang.setSoHieuDon("SHĐ" + donDatHang.getDonDatHangID().toString());
    	}
    	donDatHangService.save(donDatHang);
        return ResponseEntity.ok(donDatHang);
    }
    @GetMapping("/gioHang")
    public ResponseEntity<List<ChiTietDonDatHang>> getGioHang(@RequestParam(value = "taiKhoanKhachHangID", required = false) Integer taiKhoanKhachHangID) {
        if (donDatHangService.findByTaiKhoan(taiKhoanKhachHangID) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donDatHangService.findByTaiKhoan(taiKhoanKhachHangID));
    }
    @GetMapping("/getGioHang")
    public ResponseEntity<DonDatHang> getMoiGioHang(@RequestParam(value = "taiKhoanKhachHangID", required = false) Integer taiKhoanKhachHangID) {
        if (donDatHangService.findByTaiKhoan(taiKhoanKhachHangID) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donDatHangService.getGioHang(taiKhoanKhachHangID));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DonDatHang>> findById(@PathVariable int id) {
        if (donDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donDatHangService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonDatHang> update(@PathVariable int id, @Valid @RequestBody DonDatHang donDatHang) {
        if (donDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
    	if (donDatHang.getTinhTrang()==1) {
    		donDatHang.setSoHieuDon("SHĐ" + donDatHang.getDonDatHangID().toString());
    	}
    	donDatHangService.save(donDatHang);
        return ResponseEntity.ok().build();
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (donDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        donDatHangService.delete(id);
        return ResponseEntity.ok().build();
    }
}
