package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Direccion;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result DireccionGetbyId(int IdDireccion) {
        return jdbcTemplate.execute("CALL DireccionGetById(?,?)", (CallableStatementCallback<Result>)  callableStatement ->  {
           
            Result result = new Result();
            
            try {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                if (resultSet.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    
                    result.object = direccion;
                    result.correct = true;
                }
                
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            
            return result;
            
        });
    }
    
    @Override
    public Result DireccionAdd(Direccion direccion, int IdUsuario){
        return jdbcTemplate.execute("Call direccionAdd(?,?,?,?,?)", (CallableStatementCallback<Result>) callableStatement -> {
        Result result = new Result();
            try {
                
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, IdUsuario);
                callableStatement.execute();
                
                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
        
        return result;
    });
    }

    @Override
    public Result DireccionDelete(int IdDireccion) {
        return jdbcTemplate.execute("CALL DireccionDelete(?)", (CallableStatementCallback<Result>) callableStatement -> {
            
            Result result = new Result();
            
            try {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.execute();
                
                result.correct = true;
                
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
            
            return result;
        });
    }

}
