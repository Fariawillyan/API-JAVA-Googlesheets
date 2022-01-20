import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleAuthorizeUtil {

    public static Credential authorize() throws IOException, GeneralSecurityException {

        /**
         * Serialização  para o arquivo credential, onde cria e lê e constrói o arquivo na
         * pasta "resources", manipulando o arquivo e retornando um file "StoreCredential" na pasta "tokens" para
         * a autorização do usuário. */

 //       metodos input "criar" para serializacao

        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/credential.json");
        assert in != null;

//        servicos google para carregar, junto com input serializacao "ler"

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load
                (JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

 //       cria uma classe para lista do spread

        List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

//        constrói e transporta para a pasta tokens, usando metodos do googlesheets

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();

//        retorna o file autorização

        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
    }


}
