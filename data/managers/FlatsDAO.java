/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;


import PO41.Koval.wdad.data.network.Flat;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author 000
 */
public interface FlatsDAO {
    public boolean insertFlat (Flat flat);
    public boolean deleteFlat (Flat flat);
    public Flat findFlat (int id);
    public boolean updateFlat (Flat flat);
    public boolean saveOrUpdateFlat (Flat flat);
    public Collection<Flat>
    findFlatsByLastRegistrationDate (Date regDate);
}
