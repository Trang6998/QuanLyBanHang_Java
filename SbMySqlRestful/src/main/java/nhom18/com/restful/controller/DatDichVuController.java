package nhom18.com.restful.controller;
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
import nhom18.com.restful.service.DatDichVuService;
import nhom18.com.restful.service.ServiceResult;
import nhom18.com.restful.entities.DatDichVu;

@RestController
@RequestMapping("datdichvu")
@CrossOrigin("*")
public class DatDichVuController {
	@Autowired
	private DatDichVuService datDichVuService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenDichVu", required = false) String tenDichVu,
							     @RequestParam(value = "tuNgay", required = false) String tuNgay,
							     @RequestParam(value = "denNgay", required = false) String denNgay,
							     @RequestParam(value = "dichVuID", required = false) Integer dichVuID,
							     @RequestParam(value = "userID", required = false) Integer userID) throws ParseException{
   	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date tungay = null;
    	Date denngay = null;
    	if(tuNgay != null)
    		tungay = sdf.parse(tuNgay);
    	if(denNgay != null)
    		denngay = sdf.parse(denNgay);
        return ResponseEntity.ok(datDichVuService.findByProperty(tenDichVu, tungay, denngay, dichVuID, userID));
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DatDichVu dichVu) {
        return ResponseEntity.ok(datDichVuService.save(dichVu));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DatDichVu>> findById(@PathVariable int id) {
        if (datDichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(datDichVuService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatDichVu> update(@PathVariable int id, @Valid @RequestBody DatDichVu dichVu) {
        if (datDichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(datDichVuService.save(dichVu));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (datDichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        datDichVuService.delete(id);
        return ResponseEntity.ok().build();
    }
}
