package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;

public interface IUsuarioJPA {
    
    Result GetAll();
    
    Result Add(Usuario usuario);
}
