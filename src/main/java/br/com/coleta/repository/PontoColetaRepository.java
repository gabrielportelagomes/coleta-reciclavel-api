package br.com.coleta.repository;

import br.com.coleta.model.PontoColeta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PontoColetaRepository implements PanacheRepository<PontoColeta> {

    public List<PontoColeta> findByStatus(String status) {
        return list("status", status);
    }
}