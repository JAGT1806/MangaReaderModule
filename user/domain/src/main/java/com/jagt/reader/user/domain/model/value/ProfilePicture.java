package com.jagt.reader.user.domain.model.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePicture {
    private String url;
    private boolean isDefault;
    private String fileName;

    private static final String DEFAULT_PICTURE = "https://thumbs.dreamstime.com/b/icono-del-perfil-del-placeholder-del-defecto-90197957.jpg";

    public static ProfilePicture defaultPicture() {
        return new ProfilePicture(DEFAULT_PICTURE, true, null);
    }

    public static ProfilePicture customPicture(String url, String fileName) {
        return new ProfilePicture(url, false, fileName);
    }

}
