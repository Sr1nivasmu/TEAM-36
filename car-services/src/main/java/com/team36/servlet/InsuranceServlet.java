package com.team36.servlet;

import com.team36.model.InsurancePlan;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/insurance")
public class InsuranceServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        // Mock data
        List<InsurancePlan> plans = Arrays.asList(
            new InsurancePlan() {{ setPlan("Basic Plan"); setCoverage("Third-party liability"); setPrice(5000); }},
            new InsurancePlan() {{ setPlan("Standard Plan"); setCoverage("Third-party + own damage"); setPrice(10000); }},
            new InsurancePlan() {{ setPlan("Premium Plan"); setCoverage("Comprehensive coverage"); setPrice(20000); }}
        );
        objectMapper.writeValue(resp.getWriter(), plans);
    }
}