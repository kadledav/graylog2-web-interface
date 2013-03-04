package controllers;

import models.LoginRequest;
import models.User;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

public class Sessions extends Controller {

	final static Form<LoginRequest> userForm = form(LoginRequest.class);
	
	public static Result index() {
		return ok(views.html.sessions.login.render(userForm));
	}
	
	public static Result create() {
		Form<LoginRequest> loginRequest = userForm.bindFromRequest();
		
		if (loginRequest.hasErrors()) {
			flash("error", "Please fill out all fields.");
			return redirect("/login");
		}
		
		LoginRequest r = loginRequest.get();
		if (User.authenticate(r.username, r.password)) {
			session("username", "LOL_SOME_USER_ID");
			return redirect("/");
		} else {
			flash("error", "Wrong username or password.");
			return redirect("/login");
		}
	}

	public static Result destroy() {
		session().clear();
		return redirect("/login");
	}
	
}