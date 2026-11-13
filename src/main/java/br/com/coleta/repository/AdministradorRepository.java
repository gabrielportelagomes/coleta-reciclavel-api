package br.com.coleta.repository;

import br.com.coleta.model.Administrador;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdministradorRepository implements PanacheRepository<Administrador> {

    public Administrador findByEmail(String email) {
        return find("email", email).firstResult();
    }
}