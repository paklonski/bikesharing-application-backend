package cz.cvut.bikesharingproject.rest.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RestUtils {

    public static HttpHeaders createLocationHeaderFromCurrentUri(String path, Object... uriVariableValues) {
        assert path != null;
        final HttpHeaders headers = new HttpHeaders();
        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(path)
                .buildAndExpand(uriVariableValues)
                .toUri();
        headers.set(HttpHeaders.LOCATION, location.toASCIIString());
        return headers;
    }
}
