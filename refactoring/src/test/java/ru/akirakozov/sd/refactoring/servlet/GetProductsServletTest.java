package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.utils.DbUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetProductsServletTest {

    private static final String EMPTY_RESPONSE = "<html><body>\n</body></html>";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private GetProductsServlet servlet;

    private final ProductDAO productDAO = new ProductDAO();

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new GetProductsServlet(productDAO);
        DbUtils.initProducts();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbUtils.clearProducts();
    }

    @Test
    void testEmptyResponse() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString().trim()).isEqualTo(EMPTY_RESPONSE);
    }

    @Test
    void testAddedDataIsReturned() throws IOException, SQLException {
        DbUtils.addSampleData();
        StringWriter respWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "min\t10</br>" +
                        "middle\t25</br>" +
                        "max\t50</br>" +
                        "</body></html>");
    }
}