package com.team36.servlet;

import com.team36.model.CarResale;
import com.team36.util.DatabaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResaleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carName = req.getParameter("carName");
        String currentOwner = req.getParameter("currentOwner");
        String carType = req.getParameter("carType");
        String kilometersDrivenStr = req.getParameter("kilometersDriven");
        String serviceRecordStr = req.getParameter("serviceRecord");
        String carAgeStr = req.getParameter("carAge");
        String accidentsMajorStr = req.getParameter("accidentsMajor");
        String accidentsMinorStr = req.getParameter("accidentsMinor");
        String insuranceStr = req.getParameter("insurance");
        String numberOfOwnersStr = req.getParameter("numberOfOwners");
        String description = req.getParameter("description");

        if (carName == null || carName.trim().isEmpty() ||
                currentOwner == null || currentOwner.trim().isEmpty() ||
                carType == null || carType.trim().isEmpty() ||
                kilometersDrivenStr == null || serviceRecordStr == null || carAgeStr == null ||
                accidentsMajorStr == null || accidentsMinorStr == null || insuranceStr == null ||
                numberOfOwnersStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing required parameters.");
            return;
        }

        try {
            int kilometersDriven = Integer.parseInt(kilometersDrivenStr);
            int serviceRecord = Integer.parseInt(serviceRecordStr);
            int carAge = Integer.parseInt(carAgeStr);
            int accidentsMajor = Integer.parseInt(accidentsMajorStr);
            int accidentsMinor = Integer.parseInt(accidentsMinorStr);
            boolean insurance = "yes".equalsIgnoreCase(insuranceStr);
            int numberOfOwners = Integer.parseInt(numberOfOwnersStr);

            try (Connection conn = DatabaseUtil.getConnection()) {
                String sql = "INSERT INTO car_resale (car_name, current_owner, car_type, kilometers_driven, service_record, car_age, accidents_major, accidents_minor, insurance, number_of_owners, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, carName);
                    stmt.setString(2, currentOwner);
                    stmt.setString(3, carType);
                    stmt.setInt(4, kilometersDriven);
                    stmt.setInt(5, serviceRecord);
                    stmt.setInt(6, carAge);
                    stmt.setInt(7, accidentsMajor);
                    stmt.setInt(8, accidentsMinor);
                    stmt.setBoolean(9, insurance);
                    stmt.setInt(10, numberOfOwners);
                    stmt.setString(11, description);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        resp.getWriter().write("Car resale data saved successfully.");
                    } else {
                        resp.getWriter().write("Failed to save car resale data.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format in input.");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarResale> resaleList = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM car_resale ORDER BY id DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CarResale resale = new CarResale();
                        resale.setId(rs.getInt("id"));
                        resale.setCarName(rs.getString("car_name"));
                        resale.setCurrentOwner(rs.getString("current_owner"));
                        resale.setCarType(rs.getString("car_type"));
                        resale.setKilometersDriven(rs.getInt("kilometers_driven"));
                        resale.setServiceRecord(rs.getInt("service_record"));
                        resale.setCarAge(rs.getInt("car_age"));
                        resale.setAccidentsMajor(rs.getInt("accidents_major"));
                        resale.setAccidentsMinor(rs.getInt("accidents_minor"));
                        resale.setInsurance(rs.getBoolean("insurance"));
                        resale.setNumberOfOwners(rs.getInt("number_of_owners"));
                        resale.setDescription(rs.getString("description"));
                        resaleList.add(resale);
                    }
                }
            }
            // Convert resaleList to JSON manually
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < resaleList.size(); i++) {
                CarResale r = resaleList.get(i);
                json.append("{");
                json.append("\"id\":").append(r.getId()).append(",");
                json.append("\"carName\":\"").append(escapeJson(r.getCarName())).append("\",");
                json.append("\"currentOwner\":\"").append(escapeJson(r.getCurrentOwner())).append("\",");
                json.append("\"carType\":\"").append(escapeJson(r.getCarType())).append("\",");
                json.append("\"kilometersDriven\":").append(r.getKilometersDriven()).append(",");
                json.append("\"serviceRecord\":").append(r.getServiceRecord()).append(",");
                json.append("\"carAge\":").append(r.getCarAge()).append(",");
                json.append("\"accidentsMajor\":").append(r.getAccidentsMajor()).append(",");
                json.append("\"accidentsMinor\":").append(r.getAccidentsMinor()).append(",");
                json.append("\"insurance\":").append(r.isInsurance()).append(",");
                json.append("\"numberOfOwners\":").append(r.getNumberOfOwners()).append(",");
                json.append("\"description\":\"").append(escapeJson(r.getDescription())).append("\"");
                json.append("}");
                if (i < resaleList.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            resp.setContentType("application/json");
            resp.getWriter().write(json.toString());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    private String escapeJson(String s) {
        if (s == null)
            return "";
        return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
