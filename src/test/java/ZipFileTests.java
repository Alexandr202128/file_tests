import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileTests {

    ClassLoader cl = ZipFileTests.class.getClassLoader();
    @Test
    void zipTest() throws Exception {
        ZipEntry entry;
        try (ZipInputStream zip = new ZipInputStream(cl.getResourceAsStream("example.zip"))) {

            while ((entry = zip.getNextEntry()) != null) {

                if (entry.getName().endsWith(".csv")) {
                    CSVReader csv = new CSVReader(new InputStreamReader(zip));
                    List<String[]> listCsv = csv.readAll();
                    Assertions.assertThat(listCsv.get(0)[1]).contains("Lesson");
                }

                if(entry.getName().endsWith(".pdf")) {
                    PDF pdfFile = new PDF(zip);
                    Assertions.assertThat(pdfFile.text).contains("PDF");
                }

                else if(entry.getName().endsWith(".xlsx")) {
                    XLS xlsxFile = new XLS(zip);
                    Assertions.assertThat(xlsxFile.excel.getSheetAt(0).getRow(1)
                            .getCell(1).getStringCellValue()).isEqualTo("Selenium");
                }
            }
        }
    }
}
