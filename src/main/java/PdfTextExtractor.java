import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfTextExtractor {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\USUARIO\\Desktop\\IntuitiveCare\\TesteNivelamentoIntuitiveCare1\\downloads\\Anexo_I.pdf");

            PDDocument document = PDDocument.load(file);

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(0);
            stripper.setEndPage(document.getNumberOfPages());

            String text = stripper.getText(document);

            System.out.println(text);

            document.close();

            List<String[]> tabelaExtraida = new ArrayList<>();
            tabelaExtraida.add(new String[] {"Nome Procedimento", "Código", "Valor"});
            tabelaExtraida.add(new String[] {"Exemplo Procedimento 1", "1234", "500"});
            tabelaExtraida.add(new String[] {"Exemplo Procedimento 2", "5678", "600"});

            CsvWriter.salvarCsv(tabelaExtraida, "dados_extraidos.csv");

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private static List<String[]> processarTexto(String texto) {
        List<String[]> tabela = new ArrayList<>();
        String[] linhas = texto.split("\n");

        tabela.add(new String[]{"Nome Procedimento", "Codigo", " Valor"});

        for (String linha : linhas) {
            String[] colunas = linha.split("\\s{2,}");

            for(int i=0; i < colunas.length; i++){
                if(colunas[i].equals("OD")) {
                    colunas[i] = "Descricao Completa de OD";
                } else if(colunas[i].equals("AMB")) {
                    colunas[i] = "Descricao completa de AMB";
                }
            }
            tabela.add(colunas);
        }
        return tabela;
    }

}
