package rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsRequest {
    private String email;
    private String password;
}
