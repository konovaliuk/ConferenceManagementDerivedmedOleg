package com.derivedmed.proj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrongCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return "wrong";
    }
}