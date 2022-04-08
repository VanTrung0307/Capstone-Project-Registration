/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group6.capstoneprojectregistration.controllers;

import com.group6.capstoneprojectregistration.daos.ProjectDAO;
import com.group6.capstoneprojectregistration.daos.UserDAO;
import com.group6.capstoneprojectregistration.dtos.ProjectDTO;
import com.group6.capstoneprojectregistration.dtos.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections4.ListUtils;

/**
 *
 * @author PC
 */
@WebServlet(name = "AdminRandomStudentNoGroupController", urlPatterns = {"/AdminRandomStudentNoGroupController"})
public class AdminRandomStudentNoGroupController extends HttpServlet {

    private static final String ERROR = "group.jsp";
    private static final String SUCCESS = "adminStudents.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserDAO usDao = new UserDAO();
        ProjectDAO prDao = new ProjectDAO();
        Random rand = new Random();
        HttpSession session = request.getSession();

        try {
            List<ProjectDTO> listNotSelectedProject = prDao.getAllNotSelectedProject(false);
            List<UserDTO> listUserNoGroup = usDao.getListNoGroupUser(1);
            List<List<UserDTO>> splitGroups = ListUtils.partition(listUserNoGroup, 4);
            List<ProjectDTO> listProjectRandom = prDao.getRandomProject(listNotSelectedProject, splitGroups.size());

            if (splitGroups.size() > 0 && listNotSelectedProject.size() > 0) {
                session.setAttribute("SPLIT_PROJECT", listProjectRandom);
                session.setAttribute("SPLIT_GROUP", splitGroups);
                url = SUCCESS;
            }

        } catch (Exception e) {
            log("Error at StudentNoGroupRandomController" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
