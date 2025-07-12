package com.jagt.reader.shared.security.infrastructure.service;

import com.jagt.reader.shared.security.domain.model.SecurityUser;
import com.jagt.reader.shared.security.domain.port.input.LoadUserDetailsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Otra sugerencia es implementar esto en el módulo de user, creo que es lo mejor.
public class SecurityUserJpaAdapter implements LoadUserDetailsPort {
    // Importar repo del usuario o hacer petición http al controlador del usuario

    @Override
    public SecurityUser execute(String username) {
        // Traer el usuario y retornar valor

        return SecurityUser.builder()
                .build();
    }
}
