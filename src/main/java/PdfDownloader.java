import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfDownloader {
    private static final String PASTA_DESTINO = "downloads";
    private static final String[] LINKS = {
            "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
            "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
    };

    public void downloadUrl() throws IOException {


        File pasta = new File(PASTA_DESTINO);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        for (String link : LINKS) {
            String nomeArquivo = link.substring(link.lastIndexOf("/") + 1);
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(PASTA_DESTINO + "/" + nomeArquivo);

            byte[] buffer = new byte[4096];
            int bytesRead;
            long tempoInicio = System.currentTimeMillis();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            long tempoFim = System.currentTimeMillis();
            System.out.println("Download do arquivo " + nomeArquivo + " finalizado em" + (tempoFim - tempoInicio) + " ms");

            inputStream.close();
            fileOutputStream.close();
        }

    }

    public void ziparAnexos(String nomeZip) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(nomeZip))) {
            for (String link : LINKS) {
                String nomeArquivo = link.substring(link.lastIndexOf("/") + 1);
                File arquivo = new File(PASTA_DESTINO + File.separator + nomeArquivo);

                if (arquivo.exists()) {
                    try (FileInputStream fis = new FileInputStream(arquivo)) {
                        ZipEntry zipEntry = new ZipEntry(nomeArquivo);
                        zipOut.putNextEntry(zipEntry);
                        byte[] bytes = new byte[1024];
                        int length;
                        while ((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                    }
                    System.out.println("Arquivo adicionado ao ZIP: " + nomeArquivo);
                } else {
                    System.err.println("Arquivo não encontrado: " + nomeArquivo);
                }
            }
            System.out.println("Compactação concluída: " + nomeZip);
        } catch (IOException e) {
            System.err.println("Erro ao compactar arquivos:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PdfDownloader downloader = new PdfDownloader();
        try {
            downloader.downloadUrl();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
