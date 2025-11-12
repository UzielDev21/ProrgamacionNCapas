package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Direccion;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();

            List<Usuario> usuarios = usuariosJPA.stream().map(usuarioJPA -> modelMapper.map(usuarioJPA, Usuario.class)).collect(Collectors.toList());

            result.objects = (List<Object>) (List<?>) usuarios;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional 
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = modelMapper.map(usuario, UsuarioJPA.class);
//            usuarioJPA.DireccionesJPA.get(0).UsuarioJPA = usuarioJPA;
            List<Direccion> direcciones = usuario.Direcciones;
            usuarioJPA.DireccionesJPA = direcciones.stream().map(direccion -> modelMapper.map(direccion, DireccionJPA.class)).collect(Collectors.toList());
            usuarioJPA.DireccionesJPA.get(0).UsuarioJPA = usuarioJPA;
            entityManager.persist(usuarioJPA);

            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
