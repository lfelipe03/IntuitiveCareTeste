import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CsvWriter {
    public static void main(String[] args) {
        ziparCsv("dados_extraidos.csv", "dados_extraidos.zip");
    }

    public static void salvarCsv(List<String[]> dados, String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo), "UTF-8"))) {
            writer.write('\uFEFF');
            for (String[] linha : dados) {
                writer.write(String.join(",", linha));
                writer.newLine();
            }
            System.out.println("Arquivo CSV salvo com sucesso: " + nomeArquivo);
        }
    }

    public static void ziparCsv(String csvFile, String zipFile) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             FileInputStream fis = new FileInputStream(csvFile)) {

            ZipEntry zipEntry = new ZipEntry(csvFile);
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            System.out.println("CSV compactado com sucesso em: " + zipFile);
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo CSV não encontrado - " + csvFile);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro durante a compactação do arquivo CSV:");
            e.printStackTrace();
        }
    }
}