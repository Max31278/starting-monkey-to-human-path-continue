/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.storage;

import PO41.Koval.wdad.data.managers.*;

/**
 *
 * @author 000
 */
public abstract class DAOFactory {
     private static DAOFactory instance;

    protected DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        if (instance == null) {
            instance = new DAOFactory() {
                @Override
                public BuildingsDAO getBuildingsDAO() {
                    return new BuildingsDAOImpl();
                }

                @Override
                public FlatsDAO getFlatsDAO() {
                    return new FlatsDAOImpl();
                }

                @Override
                public RegistrationsDAO getRegistrationsDAO() {
                    return new RegistrationsDAOImpl();
                }

                @Override
                public TariffsDAO getTariffsDAO() {
                    return new TariffsDAOImpl();
                }

            };
        }
        return instance;
    }

    public abstract BuildingsDAO getBuildingsDAO();

    public abstract FlatsDAO getFlatsDAO();

    public abstract RegistrationsDAO getRegistrationsDAO();

    public abstract TariffsDAO getTariffsDAO();
}
