package com.jagt.reader.shared.security.domain.port.output;

import com.jagt.reader.shared.security.domain.model.SecurityUser;

public interface LoadUserDetailsPort {
    SecurityUser execute(String username);
}
