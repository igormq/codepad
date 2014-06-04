package controllers;

import actions.CorsAction;
import play.*;
import play.mvc.*;

import views.html.*;

@With(CorsAction.class)
public class Application extends Controller {

    public static Result index() {
       return notFound();
    }
    public static Result options(String route) { return ok(); }

}
