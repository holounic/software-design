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

class QueryServletTest {

    private static final String COMMAND = "command";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private QueryServlet servlet;

    private final ProductDAO productDAO = new ProductDAO();
    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new QueryServlet(productDAO);
        DbUtils.initProducts();
        DbUtils.addSampleData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbUtils.clearProducts();
    }

    @Test
    void testMin() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter(COMMAND)).thenReturn("min");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "<h1>Product with min price: </h1>" +
                        "min\t10</br>" +
                        "</body></html>");
    }

    @Test
    void testMax() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter(COMMAND)).thenReturn("max");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "<h1>Product with max price: </h1>" +
                        "max\t50</br>" +
                        "</body></html>");
    }

    @Test
    void testCount() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter(COMMAND)).thenReturn("count");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "Number of products: 3" +
                        "</body></html>");
    }

    @Test
    void testSum() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter(COMMAND)).thenReturn("sum");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "Summary price: 85" +
                        "</body></html>");
    }

    @Test
    void testUnknownCommand() throws IOException {
        StringWriter respWriter = new StringWriter();
        when(request.getParameter(COMMAND)).thenReturn("boo");
        when(response.getWriter()).thenReturn(new PrintWriter(respWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(respWriter.toString()).isEqualToIgnoringNewLines("Unknown command: boo");
    }
}