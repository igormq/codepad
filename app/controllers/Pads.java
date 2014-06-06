package controllers;

import actions.CorsAction;
import codepad.scripting.EngineNotFoundException;
import codepad.scripting.EvalResult;
import codepad.scripting.Scripter;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.text.json.JsonContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Block;
import models.Pad;
import play.libs.Json;
import play.mvc.*;
import providers.ScripterProvider;

import javax.script.ScriptException;
import java.util.List;

@With(CorsAction.class)
public class Pads extends Controller {

    private static ScripterProvider scripterProvider;

    @BodyParser.Of(BodyParser.Json.class)
    public static Result index() {
        return ok(Json.toJson(Pad.find.all()));
    }

    public static Result show(Long id)
    {
        Pad pad = Pad.find.byId(id);
        if (pad == null) {
            ObjectNode result = Json.newObject();
            result.put("message", "Não encontrado");
            return notFound(result);
        }
        return ok(Json.toJson(pad));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create()
    {
        Pad newPad = Json.fromJson(request().body().asJson(), Pad.class);
        newPad.save();
        return ok(Json.toJson(Pad.find.byId(newPad.id)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id)
    {
        Pad pad = Pad.find.byId(id);
        Pad upPad = Json.fromJson(request().body().asJson(), Pad.class);

        pad.title = upPad.title;


        for(Block block: pad.blocks)
        {
            block.delete();
        }

        pad.blocks.clear();

        pad.update();

        for(Block block: upPad.blocks)
        {
            block.id = null;
            pad.blocks.add(block);
        }

        pad.update(id);

        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result destroy(Long id)
    {
        Pad pad = Pad.find.byId(id);
        pad.delete();
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result evaluate() {
        Block block = Json.fromJson(request().body().asJson(), Block.class);
        EvalResult evalResult;
        ObjectNode result = Json.newObject();
        try {
            evalResult = scripterProvider.getScripter().interpret(block.type, block.content);
        }
        catch (ScriptException e) {
            result.put("exception", e.getClass().toString());
            result.put("message", e.getMessage());
            return badRequest(result);
        }
        catch (EngineNotFoundException e) {
            result.put("exception", e.getClass().toString());
            result.put("message", e.getMessage());
            return badRequest(result);
        }
        catch (Exception e) {
            result.put("exception", e.getClass().toString());
            result.put("message", e.getMessage());
            return badRequest(result);
        }
        result.put("output", evalResult.getOutput());
        result.put("returned", evalResult.getReturned());

        return ok(result);

    }

}
