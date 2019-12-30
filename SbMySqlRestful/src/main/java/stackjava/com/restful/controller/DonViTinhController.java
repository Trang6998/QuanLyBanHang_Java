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

import stackjava.com.restful.entities.DichVu;
import stackjava.com.restful.entities.DonViTinh;
import stackjava.com.restful.service.DonViTinhService;
import stackjava.com.restful.service.ServiceResult;

@RestController
@RequestMapping("donvitinh")
@CrossOrigin("*")
public class DonViTinhController {
	@Autowired
	private DonViTinhService donViTinhService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenDonVi", required = false) String tenDonVi){	
    	if(tenDonVi!=null) {
    		return ResponseEntity.ok(donViTinhService.findByName(tenDonVi));
    	}
        return ResponseEntity.ok(donViTinhService.findAll());
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DonViTinh>> findById(@PathVariable int id) {
        if (donViTinhService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(donViTinhService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody DonViTinh donViTinh) {
        return ResponseEntity.ok(donViTinhService.save(donViTinh));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DonViTinh> update(@PathVariable int id, @Valid @RequestBody DonViTinh donViTinh) {
        if (donViTinhService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(donViTinhService.save(donViTinh));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (donViTinhService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        donViTinhService.delete(id);
        return ResponseEntity.ok().build();
    }
}
