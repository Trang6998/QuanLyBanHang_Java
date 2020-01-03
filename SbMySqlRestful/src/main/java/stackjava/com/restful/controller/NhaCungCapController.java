package stackjava.com.restful.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import stackjava.com.restful.entities.DonViTinh;
import stackjava.com.restful.entities.NhaCungCap;
import stackjava.com.restful.service.DonViTinhService;
import stackjava.com.restful.service.NhaCungCapService;
@RestController
@RequestMapping("nhacungcap")
@CrossOrigin("*")
public class NhaCungCapController {
	@Autowired
	private NhaCungCapService nhaCungCapService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "tenNhaCungCap", required = false) String tenNhaCungCap){	
    	if(tenNhaCungCap!=null) {
    		return ResponseEntity.ok(nhaCungCapService.findByName(tenNhaCungCap));
    	}
        return ResponseEntity.ok(nhaCungCapService.findAll());
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<NhaCungCap>> findById(@PathVariable int id) {
        if (nhaCungCapService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(nhaCungCapService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody NhaCungCap ncc) {
        return ResponseEntity.ok(nhaCungCapService.save(ncc));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NhaCungCap> update(@PathVariable int id, @Valid @RequestBody NhaCungCap ncc) {
        if (nhaCungCapService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(nhaCungCapService.save(ncc));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (nhaCungCapService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        nhaCungCapService.delete(id);
        return ResponseEntity.ok().build();
    }
}
