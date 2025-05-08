package com.team36.servlet;

import com.team36.util.DatabaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InsurancePurchaseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String insuranceType = req.getParameter("insuranceType");
        String date = req.getParameter("date");
        double price = 0;
        if (name == null || name.trim().isEmpty()) {
            resp.getWriter().write("Name is required.");
            return;
        }
        if (date == null || date.trim().isEmpty()) {
            resp.getWriter().write("Date is required.");
            return;
        }
        if (insuranceType == null) {
            resp.getWriter().write("Insurance type is required.");
            return;
        }
        switch (insuranceType) {
            case "Basic":
                price = 3000;
                break;
            case "Inter":
                price = 7000;
                break;
            case "Premium":
                price = 15000;
                break;
            default:
                resp.getWriter().write("Invalid insurance type selected.");
                return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO insurance_purchases (name, insurance_type, price, purchase_date, insurance_date) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, insuranceType);
                stmt.setDouble(3, price);
                stmt.setString(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                stmt.setString(5, date);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    resp.getWriter().write("Insurance purchased successfully: " + insuranceType + " at ₹" + price);
                } else {
                    resp.getWriter().write("Failed to purchase insurance.");
                }
            }
        } catch (SQLException e) {
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Name parameter is required.");
            return;
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT insurance_type, price, purchase_date, insurance_date FROM insurance_purchases WHERE name = ? ORDER BY purchase_date DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                try (var rs = stmt.executeQuery()) {
                    StringBuilder json = new StringBuilder();
                    json.append("[");
                    boolean first = true;
                    while (rs.next()) {
                        if (!first) {
                            json.append(",");
                        }
                        json.append("{");
                        json.append("\"insuranceType\":\"").append(rs.getString("insurance_type")).append("\",");
                        json.append("\"price\":").append(rs.getDouble("price")).append(",");
                        json.append("\"purchaseDate\":\"").append(rs.getString("purchase_date")).append("\",");
                        json.append("\"insuranceDate\":\"").append(rs.getString("insurance_date")).append("\"");
                        json.append("}");
                        first = false;
                    }
                    json.append("]");
                    resp.setContentType("application/json");
                    resp.getWriter().write(json.toString());
                }
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }
}
