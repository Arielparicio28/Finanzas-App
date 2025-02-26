package com.example.backend.backend.services;

import com.example.backend.backend.dto.*;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.Profile;
import com.example.backend.backend.model.RevokedToken;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.RevokedTokenRepository;
import com.example.backend.backend.repository.TransactionRepository;
import com.example.backend.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;// Inyección de repositorio de cuentas

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    private final Map<String, String> blacklistedTokens = new ConcurrentHashMap<>();

    public UsersModel registerUser(UserDTO userDTO) {
        try {
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ Username already taken.");
            }
            UsersModel user = new UsersModel();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());

            // Encriptado de contraseña
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPasswordHash(encryptedPassword);

            // Convertir el ProfileDTO a Profile
            if (userDTO.getProfile() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ The profile cannot be null.");
            }

            Profile profile = new Profile(
                    userDTO.getProfile().getFirstName(),
                    userDTO.getProfile().getLastName(),
                    userDTO.getProfile().getAge(),
                    userDTO.getProfile().getPhone()
            );
            user.setProfile(profile);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());

         return userRepository.save(user);

        } catch (ResponseStatusException e) {
            throw e; // Mantiene la respuesta con el código correcto
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "❌ Error saving the user", e);
        }
    }

    // Obtener todos los usuarios
    public List<UsersModel> getAllUsers() {
        return userRepository.findAll();
    }

    private void authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Obtener usuario por ID
    public UserDTO getUserById(String id) {
        UsersModel user = userRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El usuario con el id: " + id + " no fue encontrado"
                ));
        // Mapeo manual de los campos que quieres devolver
        UserDTO partial = new UserDTO();
        partial.setUsername(user.getUsername());
        partial.setEmail(user.getEmail());

        if (user.getProfile() != null) {
            ProfileDTO profileDto = new ProfileDTO();
            profileDto.setFirstName(user.getProfile().getFirstName());
            profileDto.setLastName(user.getProfile().getLastName());
            profileDto.setAge(user.getProfile().getAge());
            profileDto.setPhone(user.getProfile().getPhone());
            partial.setProfile(profileDto);
        }

        return partial;
    }

    // Actualizar usuario
    public UsersModel updateUser(String id, UpdateUserDTO updateUserDTO)
    {
        try{
            // Convertir String a ObjectId
            ObjectId objectId = new ObjectId(id);

            // Verifica si el usuario existe
            UsersModel user = userRepository.findById(objectId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            user.setUsername(updateUserDTO.getUsername());

            // Convertir el ProfileDTO a Profile
            if (updateUserDTO.getProfile() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El perfil no puede ser nulo");
            }

            if(updateUserDTO.getProfile().getAge() == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La edad no puede ser nula.");
            }

            if(updateUserDTO.getProfile().getPhone() == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El telefono no puede ser nulo.");
            }
            // Obtener el perfil actual del usuario
            Profile profile = user.getProfile();
            if (profile == null) {
                // Si el usuario no tiene un perfil, lanzamos error (ya que no queremos crear uno nuevo)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene un perfil asignado");
            }
            // Actualizar solo los campos deseados: age y phone
            profile.setAge(updateUserDTO.getProfile().getAge());
            profile.setPhone(updateUserDTO.getProfile().getPhone());

            // Actualizar la fecha de modificación
            user.setUpdatedAt(new Date());
            return userRepository.save(user);

        }catch(ResponseStatusException e)
        {
            throw e;
        }

        catch (Exception e)
        {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario", e);
        }



    }

    // Eliminar usuario
    public UsersModel deleteUser(String id) {

        // Convertir String a ObjectId
        ObjectId objectId = new ObjectId(id);
        UsersModel existingUser =  userRepository.findById(objectId)// Verifica si el usuario existe
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        // Obtiene todas las cuentas asociadas al usuario
        List<AccountModel> userAccounts = accountRepository.findByUserId(existingUser.getId());

        // Extrae los IDs de las cuentas del usuario
        List<ObjectId> accountIds = userAccounts.stream()
                .map(AccountModel::getId)
                .toList();
        // Elimina todas las transacciones asociadas a esas cuentas
        transactionRepository.deleteByAccountIdIn(accountIds);
        // Elimina todas las cuentas asociadas al usuario
        accountRepository.deleteByUserId(existingUser.getId());
        // Finalmente, elimina el usuario
        userRepository.delete(existingUser);
        return existingUser;
    }
    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        String username = jwtService.extractUsername(token);
        Date expirationDate = jwtService.extractExpiration(token);

        if (isTokenBlacklisted(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token already invalidated");
        }

        RevokedToken revokedToken = new RevokedToken(token, username, expirationDate);
        revokedTokenRepository.save(revokedToken);

        SecurityContextHolder.clearContext();
    }


    // Verificar si un token está en la lista negra
    public boolean isTokenBlacklisted(String token) {
        Optional<RevokedToken> revokedToken = revokedTokenRepository.findByToken(token);
        return revokedToken.isPresent();
    }

    // Verificar si esta autentificado
    public UsersModel getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No active session");
        }

        return (UsersModel) authentication.getPrincipal();
    }


    public UsersModel loginUser(LoginRequest input) {
        try {
            // Intentar autenticar con AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            // Buscar el usuario en la base de datos
            return userRepository.findByUsername(input.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        }
        catch (BadCredentialsException e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        catch (AuthenticationException e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
        }
    }

}



