package pl.programirex.pokemonidzapi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import pl.programirex.pokemonidzapi.entity.User;
import pl.programirex.pokemonidzapi.service.UsersService;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/game")
public class GameController {
    @Autowired
    UsersService usersService;

    ArrayList<User> usersList = new ArrayList<User>();

    GameController()
    {
        usersList.add(new User("TestUser1", "12345678", "mail@mail.mail", "Jan", "Kowalski", 19, 7, (long) 2));
        usersList.add(new User("TestUser2", "haslohaslo", "email@email.email", "Anna", "Nowak", 3, 15, (long) 1));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserById")
    @ResponseBody
    public User getUserById(@RequestParam Long id) {
        return usersList.stream()
                .filter(user -> id.equals(user.getId()))
                .findAny()
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserByEmail")
    @ResponseBody
    public User getUserByEmail(@RequestParam String email) {
                return usersList.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny()
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    @ResponseBody
    public ArrayList<User> getAllUsers() {
        return usersList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    @ResponseBody
    public ResponseEntity<User> register(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password, @RequestParam(name = "email") String email) {
        User addedUser = new User(login, password, email);
        usersList.add(addedUser);

        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    @ResponseBody
    public ResponseEntity<User> login(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password) {
        User userLogged = usersList.stream()
                .filter(user -> login.equals(user.getUsername()) && password.equals(user.getPassword()))
                .findAny()
                .orElse(null);

        if(userLogged == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userLogged, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserPokemons")
    @ResponseBody
    public ArrayList<String> getEm(@RequestParam Long id) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Bulbasaur");
        list.add("Greninja");
        list.add("Incineroar");
        return list;
    }

}
