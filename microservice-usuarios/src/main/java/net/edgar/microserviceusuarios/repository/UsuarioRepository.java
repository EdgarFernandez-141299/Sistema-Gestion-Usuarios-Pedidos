package net.edgar.microserviceusuarios.repository;

import net.edgar.microserviceusuarios.entity.UsuarioEntity;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {


    @Query("select new net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO("
            + " usuario.nombre as nombre, "
            + " usuario.correoElectronico as correoElectronico "
            + ") from UsuarioEntity as usuario "
            + " where usuario.activo = true "
            + " and (:#{#criterio == null || #criterio.isEmpty() ? null : #criterio} is null "
            + " or (lower(usuario.nombre) like lower(concat('%', replace(:#{#criterio}, ' ', '%'), '%')) "
            + " or lower(usuario.correoElectronico) like lower(concat('%', replace(:#{#criterio}, ' ', '%'), '%'))))"
    )
    List<UsuarioDTO> buscarUsuarios(@Param("criterio") String criterio);

    Optional<UsuarioEntity> findByIdUsuarioAndActivoTrue(Long idUsuario);

    Optional<UsuarioEntity> findByNombre(String nombreUsuario);

    @Query("SELECT COUNT(usuarioEntity) > 0 FROM UsuarioEntity usuarioEntity WHERE usuarioEntity.nombre = :nombre")
    boolean existsByNombreUsuario(@Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE UsuarioEntity ue "
            + "SET ue.correoElectronico = :#{#correoElectronico}, "
            + "ue.claveAcceso = :#{#claveAcceso} "
            + "WHERE ue.idUsuario = :#{#idUsuario}")
    int actualizarUsuario(@Param("idUsuario") Long idUsuario, @Param("correoElectronico") String correoElectronico, @Param("claveAcceso") String claveAcceso);

    @Modifying
    @Query("UPDATE UsuarioEntity ue "
            + "SET ue.activo = :#{#activo} "
            + "WHERE ue.idUsuario = :#{#idUsuario}")
    int eliminarUsuario(@Param("idUsuario") Long idUsuario, @Param("activo") Boolean activo);

}
