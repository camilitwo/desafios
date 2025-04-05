package com.example.usuarios.service;

import com.example.usuarios.factory.UsuarioFactory;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.model.UsuarioEntity;
import com.example.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity crearUsuario(String nombre, String rol) {
        // Crea el objeto con la factory
        Usuario usuario = UsuarioFactory.crearUsuario(nombre, rol);

        // Transforma a entidad
        UsuarioEntity entity = new UsuarioEntity(
                usuario.getNombre(),
                usuario.getRol(),
                usuario.getPermisos()
        );

        // Guarda en la base de datos
        return usuarioRepository.save(entity);
    }
}
