package providers;

import codepad.scripting.Scripter;

/**
 * Created by igormq on 04/06/14.
 */
public class ScripterProvider {

    private static Scripter scripter;

    public static Scripter getScripter()
    {
        if(scripter == null)
        {
            scripter = new Scripter();
            scripter.addAlias("Clojure", "clojure");
        }
        return scripter;
    }
}
