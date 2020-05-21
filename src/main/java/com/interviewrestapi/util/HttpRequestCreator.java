package com.interviewrestapi.util;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class HttpRequestCreator {
    private HttpClient client;
    private Header contentTypeHeader = new BasicHeader("Content-Type", "application/json; charset=UTF-8");
    @NotNull
    private final String URI;

    public String getAll() throws IOException {
        try {
            client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(URI);
            post.addHeader(contentTypeHeader);

            HttpResponse response = client.execute(post);
            return inputStreamToString(response.getEntity().getContent());

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private String inputStreamToString(InputStream is) throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return total.toString();
    }

    public String getURI() {
        return URI;
    }

}
