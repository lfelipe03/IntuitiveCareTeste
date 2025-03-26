import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public static void salvarCsv(List<String[]> dados, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (String[] linha : dados) {
                writer.write(String.join(",", linha));
                writer.newLine();

            }

            System.out.println("Arquivo CSV salvo com sucesso: " + nomeArquivo);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
