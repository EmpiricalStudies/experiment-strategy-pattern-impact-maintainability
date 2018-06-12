package com.mycompany.strategyexperiment.dao.experiment.excluir;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcluirAnexo implements Excluir {

    @Override
    public void excluir(int id) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            stmt.execute("DELETE FROM anexos WHERE idanexo=" + String.valueOf(id));
        } finally {
            if ((stmt != null) && !stmt.isClosed()) {
                stmt.close();
            }
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        }
    }
}
