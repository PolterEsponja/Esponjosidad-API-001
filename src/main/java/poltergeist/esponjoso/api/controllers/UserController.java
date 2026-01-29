package poltergeist.esponjoso.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import poltergeist.esponjoso.api.dtos.UserBasicResponseDTO;
import poltergeist.esponjoso.api.dtos.UserRequestDTO;
import poltergeist.esponjoso.api.dtos.UserResponseDTO;
import poltergeist.esponjoso.api.services.UserService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    final private UserService userService;

    UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserBasicResponseDTO> getBasicUserInformation(@RequestParam double userId)
    {
        return ResponseEntity.ok(userService.getBasicUserInformation(userId));
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponentsBuilder)
    {        
        UserResponseDTO created = userService.createUser(userRequestDTO);
        
        URI location = uriComponentsBuilder
                        .path("/users/createUser")
                        .buildAndExpand(created.userId())
                        .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
