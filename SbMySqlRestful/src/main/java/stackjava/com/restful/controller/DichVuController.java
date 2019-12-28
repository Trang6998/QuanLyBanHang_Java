package stackjava.com.restful.controller;
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
import stackjava.com.restful.service.DichVuService;
import stackjava.com.restful.service.ServiceResult;
import stackjava.com.restful.entities.DichVu;

@RestController
@RequestMapping("dichvu")
@CrossOrigin("*")
public class DichVuController {
	@Autowired
	private DichVuService dichVuService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenDichVu", required = false) String tenDichVu,
							     @RequestParam(value = "trangThai", required = false) Boolean trangThai){
    	if(tenDichVu != null)
    	{
    		return ResponseEntity.ok(dichVuService.findByName(tenDichVu));
    	}
    	if(trangThai != null)
    	{
    		return ResponseEntity.ok(dichVuService.findByTrangThai(trangThai));
    	}    	
    	
        return ResponseEntity.ok(dichVuService.findAll());
	}
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DichVu dichVu) {
        return ResponseEntity.ok(dichVuService.save(dichVu));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DichVu>> findById(@PathVariable int id) {
        if (dichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dichVuService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DichVu> update(@PathVariable int id, @Valid @RequestBody DichVu dichVu) {
        if (dichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(dichVuService.save(dichVu));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (dichVuService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        dichVuService.delete(id);
        return ResponseEntity.ok().build();
    }
}
