package com.derivedmed.proj.controller;

import com.derivedmed.proj.command.Action;
import com.derivedmed.proj.command.Add;
import com.derivedmed.proj.command.Authorization;
import com.derivedmed.proj.command.EditConf;
import com.derivedmed.proj.command.EditReport;
import com.derivedmed.proj.command.LogOut;
import com.derivedmed.proj.command.Main;
import com.derivedmed.proj.command.MyBonuses;
import com.derivedmed.proj.command.MyConfs;
import com.derivedmed.proj.command.MyOffers;
import com.derivedmed.proj.command.OfferByModer;
import com.derivedmed.proj.command.OfferBySpeaker;
import com.derivedmed.proj.command.Past;
import com.derivedmed.proj.command.Ratings;
import com.derivedmed.proj.command.RegisterToReport;
import com.derivedmed.proj.command.Registration;
import com.derivedmed.proj.command.UpcomingConfs;
import com.derivedmed.proj.command.Vote;
import com.derivedmed.proj.command.WrongCommand;

import javax.servlet.http.HttpServletRequest;
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
        commands.put("past",new Past());
        commands.put("vote",new Vote());
        commands.put("myconfs",new MyConfs());
        commands.put("ratings",new Ratings());
        commands.put("offer",new OfferBySpeaker());
        commands.put("mybonuses",new MyBonuses());
        commands.put("myoffers",new MyOffers());
        commands.put("editConf",new EditConf());
        commands.put("editReport",new EditReport());
        commands.put("add_report",new Add());
        commands.put("addConf",new Add());
        commands.put("offerReport",new OfferByModer());
    }

    public static CommandResolver getInstance() {
        return instance;
    }

    public Action getCommand(HttpServletRequest request) {
        Action command = commands.get(request.getParameter("command"));
        if (command == null) {
            command = commands.get("wrong");
        }
        return command;
    }
}
