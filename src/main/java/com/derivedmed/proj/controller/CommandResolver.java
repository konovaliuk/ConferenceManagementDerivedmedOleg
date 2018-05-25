package com.derivedmed.proj.controller;

import com.derivedmed.proj.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CommandResolver {

    private static final HashMap<String, Action> commands = new HashMap();
    private static CommandResolver instance = new CommandResolver();

    private CommandResolver() {
        commands.put("registration", new Registration());
        commands.put("wrong", new WrongCommand());
        commands.put("upcoming", new UpcomingConfs());
        commands.put("auth", new Authorization());
        commands.put("regToRep", new RegisterToReport());
        commands.put("logOut", new LogOut());
        commands.put("main", new Main());
        commands.put("past", new Past());
        commands.put("vote", new Vote());
        commands.put("myconfs", new MyConfs());
        commands.put("ratings", new Ratings());
        commands.put("offer", new OfferBySpeaker());
        commands.put("mybonuses", new MyBonuses());
        commands.put("myoffers", new MyOffers());
        commands.put("editConf", new EditConf());
        commands.put("editReport", new EditReport());
        commands.put("add_report", new Add());
        commands.put("addConf", new Add());
        commands.put("offerReport", new OfferByModer());
        commands.put("offersControl", new OffersControl());
        commands.put("locale",new Locale());
    }

    public static CommandResolver getInstance() {
        return instance;
    }

    public Action getCommand(HttpServletRequest request, HttpServletResponse resp) {
        String commandString = request.getParameter("command");
        Action command = commands.get(commandString);
        if (request.getParameterMap().isEmpty()) {
            command = commands.get("main");
        }
        if (command == null) {
            command = commands.get("wrong");
        }
        if ("locale".equals(commandString)) {
            command = commands.get(command.execute(request, resp));
        }
        return command;
    }
}
