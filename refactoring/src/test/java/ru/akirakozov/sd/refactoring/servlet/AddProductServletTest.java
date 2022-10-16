package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.DbUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

class AddProductServletTest {

    private static final String SELECT_ALL_FROM_PRODUCT_SQL = "SELECT * FROM PRODUCT";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private AddProductServlet servlet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        servlet = new AddProductServlet();
        DbUtils.initProducts();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbUtils.clearProducts();
    }

    @Test
    void testResponseOk() throws IOException {
        when(request.getParameter("name")).thenReturn("hello");
        when(request.getParameter("price")).thenReturn("200");
        StringWriter out = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(out));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertThat(out.toString()).isEqualToIgnoringNewLines("OK");
    }

    @Test
    void testEntityIsAdded() throws IOException, SQLException {
        when(request.getParameter("name")).thenReturn("hello");
        when(request.getParameter("price")).thenReturn("20");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        servlet.doGet(request, response);

        try (Connection c = DbUtils.getConnection()) {
            try (Statement s = c.createStatement()) {
                ResultSet rs = s.executeQuery(SELECT_ALL_FROM_PRODUCT_SQL);

                assertThat(rs.next()).isTrue();
                String name = rs.getString("name");
                int price = rs.getInt("price");
                assertThat(name).isEqualTo("hello");
                assertThat(price).isEqualTo(20);
            }
        }
    }
}