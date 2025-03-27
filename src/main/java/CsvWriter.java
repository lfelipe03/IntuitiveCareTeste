import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public static void ziparCsv(String csvFile, String zipFile) {
        try(ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        FileInputStream fis = new FileInputStream(csvFile)) {

            ZipEntry zipEntry = new ZipEntry(csvFile);
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int lenght;
            while ((lenght = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, lenght);
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
