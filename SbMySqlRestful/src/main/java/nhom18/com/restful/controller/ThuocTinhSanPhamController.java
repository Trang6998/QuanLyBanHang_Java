package nhom18.com.restful.controller;

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

import nhom18.com.restful.entities.ThuocTinhSanPham;
import nhom18.com.restful.service.ThuocTinhSanPhamService;

@RestController
@RequestMapping("thuoctinhsanpham")
@CrossOrigin("*")
public class ThuocTinhSanPhamController {
	@Autowired
	private ThuocTinhSanPhamService thuocTinhSanPhamService;
    @GetMapping
	public ResponseEntity getALl(@RequestParam(value = "sanPhamID", required = false) Integer sanPhamID){	
    	if(sanPhamID!=null) {
    		return ResponseEntity.ok(thuocTinhSanPhamService.findBySanPhamId(sanPhamID));
    	}
        return ResponseEntity.ok(thuocTinhSanPhamService.findAll());
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ThuocTinhSanPham>> findById(@PathVariable int id) {
        if (thuocTinhSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(thuocTinhSanPhamService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ThuocTinhSanPham tt) {
        return ResponseEntity.ok(thuocTinhSanPhamService.save(tt));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPham> update(@PathVariable int id, @Valid @RequestBody ThuocTinhSanPham tt) {
        if (thuocTinhSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(thuocTinhSanPhamService.save(tt));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<ThuocTinhSanPham> delete(@PathVariable Integer id) {
        if (thuocTinhSanPhamService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        thuocTinhSanPhamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
