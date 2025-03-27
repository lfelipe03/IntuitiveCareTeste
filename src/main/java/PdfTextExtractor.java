import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfTextExtractor {
    public static void main(String[] args) {
        try {
            String[] arquivosPDF = {
                    "C:\\curso\\IntuitiveCareTeste\\downloads\\Anexo_I.pdf",
                    "C:\\curso\\IntuitiveCareTeste\\downloads\\Anexo_II.pdf"
            };

            List<String[]> tabelaExtraida = new ArrayList<>();
            tabelaExtraida.add(new String[]{"Procedimento", "RN (Alteração)", "Vigência", "OD", "AMB", "HCO", "HSO", "REF", "PAC", "DUT", "Subgrupo", "Grupo", "Capítulo"});

            for (String caminhoPDF : arquivosPDF) {
                File file = new File(caminhoPDF);

                if (!file.exists()) {
                    System.err.println("Arquivo não encontrado: " + caminhoPDF);
                    continue;
                }

                PDDocument document = PDDocument.load(file);
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(0);
                stripper.setEndPage(document.getNumberOfPages());

                String text = stripper.getText(document);
                System.out.println(text);
                document.close();

                tabelaExtraida.addAll(processarTexto(text));
            }

            CsvWriter.salvarCsv(tabelaExtraida, "dados_extraidos.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> processarTexto(String texto) {
        List<String[]> tabela = new ArrayList<>();
        String[] linhas = texto.split("\n");

        for (String linha : linhas) {
            linha = linha.replaceAll("\\s{2,}", " ").trim();
            String[] colunas = linha.split(" ");

            if (colunas.length >= 3) {
                StringBuilder nomeProcedimentoBuilder = new StringBuilder();
                for (int i = 0; i < colunas.length - 2; i++) {
                    nomeProcedimentoBuilder.append(colunas[i]).append(" ");
                }
                String nomeProcedimento = nomeProcedimentoBuilder.toString().trim();
                String codigo = colunas[colunas.length - 2];
                String valor = colunas[colunas.length - 1];

                tabela.add(new String[]{nomeProcedimento, codigo, valor});
            }
        }
        return tabela;
    }
}