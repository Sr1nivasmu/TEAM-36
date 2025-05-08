package com.team36.servlet;

import com.team36.model.Booking;
import com.team36.util.DatabaseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/servicebooking")
public class ServiceBookingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServiceBookingServlet.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        LOGGER.info("Received POST request to /servicebooking");
        try {
            // Read JSON from request body
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            LOGGER.info("Request body: " + sb.toString());
            Booking booking = objectMapper.readValue(sb.toString(), Booking.class);

            // Validate input
            if (booking.getUserName() == null || booking.getCarMakeModel() == null
                    || booking.getServiceDate() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"success\": false, \"message\": \"Missing required fields\"}");
                return;
            }

            // Insert into database
            try (Connection conn = DatabaseUtil.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO bookings (user_name, car_make_model, service_date) VALUES (?, ?, ?)")) {
                stmt.setString(1, booking.getUserName());
                stmt.setString(2, booking.getCarMakeModel());
                stmt.setString(3, booking.getServiceDate());
                stmt.executeUpdate();
                LOGGER.info("Booking saved for user: " + booking.getUserName());
                resp.getWriter().write("{\"success\": true}");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error", e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"success\": false, \"message\": \"Database error: " + e.getMessage() + "\"}");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"message\": \"Invalid request: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        LOGGER.info("Received GET request to /servicebooking");
        String userName = req.getParameter("userName");
        if (userName == null || userName.trim().isEmpty()) {
            LOGGER.warning("Missing userName parameter");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"success\": false, \"message\": \"userName parameter is required\"}");
            return;
        }

        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE user_name = ?")) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setUserName(rs.getString("user_name"));
                booking.setCarMakeModel(rs.getString("car_make_model"));
                booking.setServiceDate(rs.getString("service_date"));
                bookings.add(booking);
            }
            LOGGER.info("Retrieved " + bookings.size() + " bookings for user: " + userName);
            objectMapper.writeValue(resp.getWriter(), bookings);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"success\": false, \"message\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}