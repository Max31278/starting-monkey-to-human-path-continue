/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.servlets;

import PO41.Koval.wdad.data.managers.*;
import PO41.Koval.wdad.data.network.*;
import PO41.Koval.wdad.data.storage.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.*;

/**
 *
 * @author 000
 */
 @WebServlet("/SearchReg")
public class SearchRegServlet extends HttpServlet {
    @Override
        public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        JSONArray jsonArray = null;

        DAOFactory daoFactory = DAOFactory.getDaoFactory();
        BuildingsDAO buildingDAO = daoFactory.getBuildingsDAO();
        FlatsDAO flatsDAO = daoFactory.getFlatsDAO();
        RegistrationsDAO registrationsDAO = daoFactory.getRegistrationsDAO();
        TariffsDAO tariffsDAO = daoFactory.getTariffsDAO();

        Collection<Building> buildings = null;
        Building building = null;
        Flat flat = null;
        ArrayList<Registration> registrations = new ArrayList<>();

        if (req.getParameter("streetname") != null) {
            String name = req.getParameter("streetname");
            buildings = buildingDAO.findBuildings(name);
            pw.write(" First step");
        } else {
            pw.write("Streetname is empty...");
        }

        if (req.getParameter("buildingnumber") != null) {
            int buildingNumber = Integer.valueOf(req.getParameter("buildingnumber"));
            if (buildings != null) {
                pw.write("Buildings founded...");
                for (Building b : buildings) {
                    if (buildingNumber == b.getNumber()) {
                        pw.write("Gotcha!");
                        building = b;
                        //break;
                    }
                }
                pw.write(" Second step");
            } else {
                pw.write("Buildings is empty...");
            }
        }

        if (req.getParameter("flatnumber") != null) {
            int flatNumber = Integer.valueOf(req.getParameter("flatnumber"));
            if (building != null) {
                for (Flat f : building.getFlats()) {
                    pw.write(f.getNumber() + "XXX");
                    if (flatNumber == f.getNumber()) {
                        flat = f;
                        pw.write("Number: " + flat.getNumber());
                        //break;
                    }
                }
                pw.write(" Third step");
            } else {
                pw.write("Building is empty...");
            }
        } else {
            pw.write("Flatnumber is empty...");
        }


        if (req.getParameter("afterDate") != null) {
            Date dateReg = Date.valueOf(req.getParameter("afterDate"));
            ArrayList<Registration> result = new ArrayList<Registration>();
            if (flat != null) {
                for (Registration registration : flat.getRegistrations()) {
                    if (registration.getDate().after(dateReg)) {
                        result.add(registration);
                        pw.write("Regs added...");
                    }
                }
                registrations = result;
                pw.write(" Last step ");
            }
        } else {
            registrations = new ArrayList<>(flat.getRegistrations());
        }
        if (!registrations.isEmpty()) {
            try {
                pw.write("RegID: " + registrations.get(0).getId());
            }
            catch (Exception e) {
                pw.write(e.getMessage());
            }
            //pw.write(jsonArray.toString());
        } else {
            pw.write("Ops");
        }
    }
}
