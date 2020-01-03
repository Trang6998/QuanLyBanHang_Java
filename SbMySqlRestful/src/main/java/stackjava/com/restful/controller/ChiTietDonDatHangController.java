package stackjava.com.restful.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import stackjava.com.restful.service.ServiceResult;
import stackjava.com.restful.entities.ChiTietDonDatHang;;

@RestController
@RequestMapping("chitietdondathang")
@CrossOrigin("*")
public class ChiTietDonDatHangController {
	@Autowired
	private ChiTietDonDatHangService chiTietDonDatHangService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "donDatHangID", required = false) Integer donDatHangID,
								 @RequestParam(value = "sanPhamID", required = false) Integer sanPhamID,
								 @RequestParam(value = "nhaCungCapID", required = false) Integer nhaCungCapID,
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
        return ResponseEntity.ok(chiTietDonDatHangService.findByProperty(donDatHangID, sanPhamID, nhaCungCapID, tungay, denngay, tinhTrang));
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ChiTietDonDatHang chiTietDonDatHang) {
//    	 if (chiTietDonDatHangService.findById(id) == null) {
//             ResponseEntity.badRequest().build();
//         }
        return ResponseEntity.ok(chiTietDonDatHangService.save(chiTietDonDatHang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ChiTietDonDatHang>> findById(@PathVariable int id) {
        if (chiTietDonDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(chiTietDonDatHangService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietDonDatHang> update(@PathVariable int id, @Valid @RequestBody ChiTietDonDatHang chiTietDonDatHang) {
        if (chiTietDonDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(chiTietDonDatHangService.save(chiTietDonDatHang));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (chiTietDonDatHangService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        chiTietDonDatHangService.delete(id);
        return ResponseEntity.ok().build();
    }
}
