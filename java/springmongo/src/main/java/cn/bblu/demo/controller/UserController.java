package cn.bblu.demo.controller;

import cn.bblu.demo.common.HttpResult;
import cn.bblu.demo.entity.User;
import cn.bblu.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Rest controller which is responsible for retrieving {@link User}s.
 *
 * @author Aliaksei Bahdanau
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/save")
    public HttpResult save(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email) {

        User user = new User(firstName, lastName, email);
        try {
            Object o = userRepository.save(user);
            return HttpResult.success(o);
        }catch(Exception e){
            e.printStackTrace();
            return HttpResult.ServerError(e.getMessage());
        }

    }

    @GetMapping(produces = "application/JSON")
    public HttpResult getAllUsers() {
        try {
            List<User> users = (List<User>) userRepository.findAll();
            return HttpResult.success(users);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.ServerError(e);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/JSON")
    public HttpResult findById(@PathVariable("id") String userID) {
        try {
            Optional<User> user = userRepository.findById(userID);
            return HttpResult.success(user);
        }catch(Exception e){
            e.printStackTrace();
            return HttpResult.ServerError(e);
        }

    }
}
