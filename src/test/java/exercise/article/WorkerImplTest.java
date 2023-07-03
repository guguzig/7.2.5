package exercise.article;

import exercise.worker.WorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkerImplTest {

    private WorkerImpl worker;

    @Mock
    private Library library;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        worker = new WorkerImpl(library);
    }

    @Test
    public void AddNewArticle() {
        List<Article> art = new ArrayList<>();

        art.add(new Article(
                "Новая тема",
                "Новое описание статьи",
                "Новый автор",
                LocalDate.of(2023, 6, 13)));

        worker.addNewArticles(art);
        verify(library).store(2023, art);
    }

    @Test
    public void getCatalogEmpty() {
        when(library.getAllTitles()).thenReturn(new ArrayList<>());
        String catalog = worker.getCatalog();
        assertFalse(catalog.isEmpty());
    }

    @Test
    public void getCatalogWithArticles() {
        List<String> titles = List.of("Статья 1", "Статья 2", "Статья 3");
        when(library.getAllTitles()).thenReturn(titles);
        String catalog = worker.getCatalog();
        for (String title : titles) {
            assertTrue(catalog.contains(title));
        }
    }

    @Test
    public void prepareArticlesRemovesArticlesWithoutTitle() {
        List<Article> art = new ArrayList<>();
        art.add(new Article(null, "Содержимое 1", "Автор 1", LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 2", "Содержимое 2", "Автор 2", LocalDate.of(2023, 6, 14)));

        List<Article> preparedArticles = worker.prepareArticles(art);
        assertEquals(1, preparedArticles.size());
        assertEquals("Статья 2", preparedArticles.get(0).getTitle());
    }

    @Test
    public void prepareArticlesRemovesArticlesWithoutContent() {
        List<Article> art = new ArrayList<>();
        art.add(new Article("Статья 1", null, "Автор 1", LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 2", "Содержимое 2", "Автор 2", LocalDate.of(2023, 6, 14)));

        List<Article> preparedArticles = worker.prepareArticles(art);
        assertEquals(1, preparedArticles.size());
        assertEquals("Статья 2", preparedArticles.get(0).getTitle());
    }

    @Test
    public void prepareArticlesSetsDefaultAuthor() {
        List<Article> art = new ArrayList<>();
        art.add(new Article("Статья 1", "Содержимое 1", null, LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 2", "Содержимое 2", "Автор 2", LocalDate.of(2023, 6, 14)));

        List<Article> preparedArticles = worker.prepareArticles(art);
        assertEquals(1, preparedArticles.size());
        assertEquals("Автор 2", preparedArticles.get(0).getAuthor());
    }

    @Test
    public void prepareArticlesSetsDefaultCreationDate() {
        List<Article> art = new ArrayList<>();
        art.add(new Article("Статья 1", "Содержимое 1", "Автор 1", LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 2", "Содержимое 2", "Автор 2", null));

        List<Article> preparedArticles = worker.prepareArticles(art);
        assertEquals(LocalDate.now(), preparedArticles.get(1).getCreationDate());
    }

    @Test
    public void prepareArticlesRemovesArticlesWithSameTitleAndDifferentCreationDate() {
        List<Article> art = new ArrayList<>();
        art.add(new Article("Статья 1", "Содержимое 1", "Автор 1", LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 1", "Содержимое 2", "Автор 2", null));

        List<Article> preparedArticles = worker.prepareArticles(art);
        assertEquals(2, preparedArticles.size());
    }

    @Test
    public void addNewArticlesCallsLibraryStore() {
        List<Article> art = new ArrayList<>();
        art.add(new Article("Статья 1", "Содержимое 1", "Автор 1", LocalDate.of(2023, 6, 13)));
        art.add(new Article("Статья 2", "Содержимое 2", "Автор 2", LocalDate.of(2023, 6, 14)));

        worker.addNewArticles(art);
        verify(library).store(2023, art);
    }
}