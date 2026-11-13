package br.com.coleta.config;

import br.com.coleta.model.Administrador;
import br.com.coleta.repository.AdministradorRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DatabaseInitializer {

    @Inject
    AdministradorRepository administradorRepository;

    @ConfigProperty(name = "admin.nome")
    String nomeAdmin;

    @ConfigProperty(name = "admin.email")
    String emailAdmin;

    @ConfigProperty(name = "admin.senha")
    String senhaAdmin;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        if (administradorRepository.findByEmail(emailAdmin) == null) {

            Administrador admin = new Administrador();
            admin.setNome(nomeAdmin);
            admin.setEmail(emailAdmin);

            admin.setSenha(BcryptUtil.bcryptHash(senhaAdmin));

            administradorRepository.persist(admin);

            System.out.println("✅ Administrador padrão criado com sucesso: " + emailAdmin);
        } else {
            System.out.println("⚡ O administrador já existe no banco de dados.");
        }
    }
}