package actions;

import play.mvc.*;

public class CorsAction extends Action.Simple {

    public play.libs.F.Promise<Result> call(Http.Context context) throws Throwable{
        Http.Response response = context.response();
        response.setHeader("Access-Control-Allow-Origin", "*");
        //Handle preflight requests
        if(context.request().method().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

            return delegate.call(context);
        }

        response.setHeader("Access-Control-Allow-Headers","X-Requested-With");
        return delegate.call(context);
    }
}