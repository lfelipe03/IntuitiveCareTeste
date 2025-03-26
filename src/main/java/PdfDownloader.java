import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PdfDownloader {
    public void downloadUrl() throws IOException {
        String[] links = {"https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
                "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
        };
        String pastaDestino = "downloads";

        File pasta = new File(pastaDestino);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        for (String link : links) {
            String nomeArquivo = link.substring(link.lastIndexOf("/") + 1);
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(pastaDestino + "/" + nomeArquivo);

            byte[] buffer = new byte[4096];
            int bytesRead;
            long tempoInicio = System.currentTimeMillis();

            while ((bytesRead = inputStream.read(buffer)) != 1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            long tempoFim = System.currentTimeMillis();
            System.out.println("Download do arquivo " + nomeArquivo + " finalizado em" + (tempoFim - tempoInicio) + " ms");

            inputStream.close();
            fileOutputStream.close();
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
