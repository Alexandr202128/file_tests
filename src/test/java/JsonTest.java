import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.Book;

import java.io.InputStreamReader;

public class JsonTest {
    ClassLoader cl = JsonTest.class.getClassLoader();
    @Test
    public void checkJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try(InputStreamReader is = new InputStreamReader(cl.getResourceAsStream("example.json"))) {
            Book book = mapper.readValue(is, Book.class);
            Assertions.assertThat(book.getBook().getAuthor().get(0)).isEqualTo("Horoviz");
            Assertions.assertThat(book.getBook().getAuthor().get(1)).isEqualTo("Hill");
            Assertions.assertThat(book.getBook().getName()).isEqualTo("Circuit design");
            Assertions.assertThat(book.getBook().getPublishing()).isEqualTo("Moskva");
            Assertions.assertThat(book.getBook().getPage()).isEqualTo(350);

        }
    }

}
