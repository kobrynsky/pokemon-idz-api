package pl.programirex.pokemonidzapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.programirex.pokemonidzapi.dto.GetUserDto;
import pl.programirex.pokemonidzapi.dto.GetUserTeamDto;
import pl.programirex.pokemonidzapi.dto.RegisterDto;
import pl.programirex.pokemonidzapi.dto.SavePokemonDto;
import pl.programirex.pokemonidzapi.entity.User;
import pl.programirex.pokemonidzapi.entity.UserPokemon;
import pl.programirex.pokemonidzapi.repository.UserPokemonRepository;
import pl.programirex.pokemonidzapi.repository.UserRepository;
import pl.programirex.pokemonidzapi.service.GameService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.POST, value = "/savePokemon")
    @ResponseBody
    public ResponseEntity savePokemon(@Valid @RequestBody SavePokemonDto savePokemonDto, BindingResult result) {
        UserPokemon userPokemon = new UserPokemon();
        if (!result.hasErrors()) {
            userPokemon = gameService.savePokemon(savePokemonDto);
        }
        if (userPokemon == null || userPokemon.getId() == null) {
            return new ResponseEntity<>("Wystąpił błąd podczas zapisywania pokemona", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPokemon, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserTeam/{userId}")
    public ResponseEntity getUserTeam(@PathVariable @NotNull Long userId) {
        GetUserTeamDto userTeam = gameService.getUserTeam(userId);

        if(userTeam == null || userTeam.getLogin().isEmpty() || userTeam.getLogin() == null)
            return new ResponseEntity<>("Nie znaleziono użytkownika!", HttpStatus.BAD_REQUEST);

        return  new ResponseEntity<>(userTeam, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/fight")
    @ResponseBody
    public String calculateFight(){
        return "todo";
    }
}

