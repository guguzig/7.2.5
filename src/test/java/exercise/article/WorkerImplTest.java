package exercise.article;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkerImplTest {

    private Library library;

    @BeforeEach
    public void setup() {
        library = new LibraryImpl();
    }

    @Test
    public void testGetAllTitles() {
        List<String> expectedTitles = new ArrayList<>();

        expectedTitles.add("Сколько времени нужно, чтобы выучить Java");
        expectedTitles.add("7 мифов об IT");

        List<String> actualTitles = library.getAllTitles();

        Assertions.assertEquals(expectedTitles, actualTitles);
    }

    @Test
    public void testStoreAndUpdateCatalog() {
        List<Article> newArticles = new ArrayList<>();
        newArticles.add(new Article(
                "Новая статья 1",
                "Содержимое новой статьи 1",
                "Автор 1",
                LocalDate.of(2023, 6, 14)));
        newArticles.add(new Article(
                "Новая статья 2",
                "Содержимое новой статьи 2",
                "Автор 2",
                LocalDate.of(2023, 6, 14)));

        library.store(2023, newArticles);
        library.updateCatalog();

        List<String> expectedTitles = new ArrayList<>();
        expectedTitles.add("Как правильно изучать языки программирования");
        expectedTitles.add("Как составить резюме тестировщику");
        expectedTitles.add("Почему важны soft skills?");
        expectedTitles.add("Сколько времени нужно, чтобы выучить Java");
        expectedTitles.add("7 мифов об IT");
        expectedTitles.add("Новая статья 1");
        expectedTitles.add("Новая статья 2");

        List<String> actualTitles = library.getAllTitles();

        Assertions.assertEquals(expectedTitles, actualTitles);
    }

}
