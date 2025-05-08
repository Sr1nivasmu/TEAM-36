package com.team36.servlet;

import com.team36.model.ServicePackage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/service")
public class ServiceServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        // Mock data
        List<ServicePackage> packages = Arrays.asList(
            new ServicePackage() {{ setPackage("Basic Service"); setDetails("Oil change, filter replacement"); setPrice(2000); }},
            new ServicePackage() {{ setPackage("Comprehensive Service"); setDetails("Full inspection, oil change, tire rotation"); setPrice(5000); }},
            new ServicePackage() {{ setPackage("Premium Service"); setDetails("Complete overhaul, premium parts"); setPrice(10000); }}
        );
        objectMapper.writeValue(resp.getWriter(), packages);
    }
}