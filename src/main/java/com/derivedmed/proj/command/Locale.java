package com.derivedmed.proj.command;

import com.derivedmed.proj.controller.CommandResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Locale implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String loc = req.getParameter("locale");
        String referer = req.getHeader("Referer");
        HttpSession session = req.getSession();
        session.setAttribute("loc", loc);
        String redirectLink = String.valueOf(session.getAttribute("redirectLink"));
        if (referer.contains("=")) {
            String substring = referer.substring(referer.lastIndexOf("=") + 1);
            if ("locale".equals(substring)) {
                return CommandResolver.getInstance().getCommand(redirectLink).execute(req, resp);
            }
            session.setAttribute("redirectLink", substring);
            return CommandResolver.getInstance().getCommand(substring).execute(req, resp);
        }
        session.setAttribute("redirectLink", "main");
        return CommandResolver.getInstance().getCommand("main").execute(req, resp);
    }
}
