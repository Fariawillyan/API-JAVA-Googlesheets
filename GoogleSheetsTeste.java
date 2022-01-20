import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import static java.lang.System.in;

public class GoogleSheetsTeste {

//    acessa o sdk
    public static final String SPREADSHEET_ID ="1KsLjv2ZJshhvug0rq8K1Koc__pe_9oV09ikLuGwi4Zw";
    static Sheets sheetsService;

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        sheetsService = SheetsServiceUtil.getSheetsService();
        String range = "A2:G300";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("nada encontrado");

        } else {
            System.out.println("Nome | Agencia | ContaCorrente | Banco | CPF | AtivoDigital | Saldo");
            for (List row : values) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println(row);
            }
        }
        int numero;

        do {

            System.out.println("\n");
            System.out.println("DIGITE A OPCAO DESEJADA| 1 - ADICIONAR | 2 - ATUALIZAR | 3 - DELETAR | 4 - SAIR");
            Scanner escolha = new Scanner(in);

            numero = Integer.parseInt(escolha.next());

            switch (numero) {
                case 1:
                    System.out.println("Digite em sequência: Nome, Ag, CC, Banco, CPF, Ativo, Saldo");

                    ValueRange appendBody = new ValueRange()
                            .setValues(List.of(
                                    Arrays.asList(addConta.nome, addConta.agencia,
                                            addConta.contaCorrente, addConta.banco, addConta.cpf,
                                            addConta.ativoDigital, addConta.saldo)
                            ));

                    AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                            .append(SPREADSHEET_ID, range, appendBody)
                            .setValueInputOption("USER_ENTERED")
                            .setInsertDataOption("INSERT_ROWS")
                            .setIncludeValuesInResponse(true)
                            .execute();
                    System.out.println("Adicionado com Sucesso");
                    return;

                case 2:

                    System.out.println("Digite a cedula para alterar, exemplo: B3");
                    Scanner entrada = new Scanner(in);
                    String pegarange = entrada.next();
                    System.out.println("Digite o update, exemplo: nome da conta ou do banco");
                    String pegaatual = entrada.next();

                    ValueRange Body = new ValueRange()
                            .setValues(List.of(
                                    List.of(pegaatual)
                            ));

                    UpdateValuesResponse result = sheetsService.spreadsheets().values()
                            .update(SPREADSHEET_ID, pegarange, Body)
                            .setValueInputOption("RAW")
                            .execute();
                    System.out.println("Atualizado com Sucesso");
                    return;

                case 3:

                    System.out.println("Digite apartir da linha que deseja apagar");
                    Scanner entrada2 = new Scanner(in);
                    String deletelinha = entrada2.next();

                    DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                            .setRange(
                                    new DimensionRange()
                                            .setSheetId(0)
                                            .setDimension("ROWS")
                                            .setStartIndex(Integer.valueOf(deletelinha))
                            );

                    List<Request> requests = new ArrayList<>();
                    requests.add(new Request().setDeleteDimension(deleteRequest));

                    BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
                    sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
                    System.out.println("---------- APAGADA ULTIMAS LINHAS ----------");

                case 4 :
                    System.out.println("---------- API FINALIZADA ----------");
                    return;
            }
            System.out.println("Nao esperado, digite novamente!");
        } while (numero > 4);
    }
}





