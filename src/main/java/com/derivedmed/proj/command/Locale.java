package com.derivedmed.proj.command;

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
        if (referer.contains("=")) {
            String substring = referer.substring(referer.lastIndexOf("=") + 1);
            if ("locale".equals(substring)) {
                substring = String.valueOf(session.getAttribute("page"));
            } else {
                session.setAttribute("page", substring);
            }
            return substring;
        }
        session.setAttribute("page", "main");
        return "main";
    }
}
