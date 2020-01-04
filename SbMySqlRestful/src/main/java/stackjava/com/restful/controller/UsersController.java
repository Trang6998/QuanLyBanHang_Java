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
import stackjava.com.restful.service.UsersService;
import stackjava.com.restful.service.ServiceResult;
import stackjava.com.restful.entities.Users;;

@RestController
@RequestMapping("auth/login")
@CrossOrigin("*")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@PostMapping
	public ResponseEntity checkUser(@Valid @RequestBody Users users){
    	Users u = usersService.findByTaiKhoan(users.getTaiKhoan(), users.getPassWord());
    	if (u == null)
    		return ResponseEntity.ok("Login failed!");
    	else
    		return ResponseEntity.ok(u);
	}
//    @PostMapping
//    public ResponseEntity create(@Valid @RequestBody Users users) {
//
//        return ResponseEntity.ok(usersService.save(users));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Users>> findById(@PathVariable int id) {
        if (usersService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(usersService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable int id, @Valid @RequestBody Users Users) {
        if (usersService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(usersService.save(Users));
    }

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
        if (usersService.findById(id) == null) {
            ResponseEntity.badRequest().build();
        }
        usersService.delete(id);
        return ResponseEntity.ok().build();
    }
}
